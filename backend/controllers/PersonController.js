const PersonSchema = require('../models/Person')
const EventSchema = require('../models/Event')
const TasksSchema = require('../models/Task')
const Cloudinary = require('../utils/cloudinary').v2;;
const catchAsyncErrors = require('../middlewares/catchAsyncErrors');



exports.changePic = catchAsyncErrors(async (req, res, next) => {
    const { id } = req.params;
    try {
        const image = (req.files.image);
        const imageStatus = verifyImage(image);
        Cloudinary.config({
            cloud_name: process.env.CLOUDINARY_NAME,
            api_key: process.env.CLOUDINARY_API_KEY,
            api_secret: process.env.CLOUDINARY_API_SECRET,
            secure: true
        });


        if (imageStatus) {
            const result = await Cloudinary.uploader.upload(req.files.image.tempFilePath);
            await PersonSchema.updateOne({ _id: id }, { profile: result.url });
            res.status(200).json({
                success: true,
                public_url: result.url
            })

        }
        else {
            res.status(401).json({
                success: false,
                message: "please provide image within the range of 100kb"

            })
        }

        console.log(image);
        // res.json(req.body)
    }
    catch (err) {
        console.log(err);

    }
})

exports.requestsDetailsById = catchAsyncErrors(async (req, res, next) => {
    const { userId } = req.params;
    const foundPerson = await PersonSchema.findById(userId);
    if (foundPerson) {
        const requestDetailedData = [];
        const requestList = foundPerson.requests;
        if (requestList.length == 0) {
            res.status(401).json({
                success: false,
                msg: "has no requests"

            })
        }
        else {
            const allEvents = await EventSchema.find();
            for (let i = 0; i < allEvents.length; i++) {
                for (let j = 0; j < requestList.length; j++) {
                    if (allEvents[i]._id == requestList[j].id)
                        requestDetailedData.push(allEvents[i])
                }

            }
            res.status(200).json({
                success: true,
                requestList,
                requestDetailedData
            })
        }
    }
    else {
        res.status(402).json({
            success: false

        })
    }


})
exports.getAllNames = catchAsyncErrors(async (req, res, next) => {
    const persons = await PersonSchema.find();
    const namesList = persons.map(person => person.name);
    if (namesList.length == 0) {
        res.status(200).json(
            {
                success: false,
                message: "Not Person"
            }
        )
    }
    else {

        res.status(200).json(
            {
                success: false,
                namesList
            }
        )
    }

})
exports.myEvents = catchAsyncErrors(async (req, res, next) => {
    const { id } = req.body;
    const events = await EventSchema.find();
    const updateList = [];
    const eventsByUserId = events.filter(item => {
        if (item.userId == id) {
            updateList.push(item)
            return item;
        }
    });
    if (eventsByUserId.length != 0) {

        res.status(200).json({
            success: true,
            list: eventsByUserId
        })
    }
    else
        res.status(404).json({
            success: false,
            message: "Not Found Admin of Any Event"
        })
})
exports.getEventByUserId = catchAsyncErrors(async (req, res, next) => {
    const { id } = req.params;

    const events = await EventSchema.find();
    const teamsLists = events.filter(event => {
        const listofOneTeam = event.team;
        if (event.userId == id)
            return event;
        // console.log(listofOneTeam)  
        for (let i = 0; i < listofOneTeam.length; i++) {
            if (id == listofOneTeam[i].id)
                return event;
        }
    });
    if (teamsLists.length == 0)
        res.status(404).json({
            success: false,
            message: "not found the user in any team"
        })
    else
        res.status(200).json({
            success: true,
            events: teamsLists
        })
})

exports.deletePerson = catchAsyncErrors(async (req, res, next) => {
    const { id } = req.body;
    const deletedPerson = await PersonSchema.findByIdAndDelete(id)
    res.status(200).json({
        success: true,
        deletedPerson
    })
})
exports.findByName = catchAsyncErrors(async (req, res, next) => {

    const name = req.params.name;
    const listUsers = await PersonSchema.find();
    const foundUser = listUsers.find((user) => {
        if (user.name == name) {
            return user;
        }
        else return null;
    })
    if (foundUser)
        res.status(200).json(
            {
                success: true,
                foundUser
            }
        )
    else {
        res.status(402).json(
            {
                success: false,
                msg: "userNotFound"
            }
        )

    }
})

exports.changeData = catchAsyncErrors(async (req, res, next) => {
    const { id, name, email, number, desc } = req.body;
    const updateUserData =
    {
        name, password, email, number, desc
    };

    try {
        const updated = await PersonSchema.findByIdAndUpdate(id, updateUserData)

        if (updated) {
            res.status(200).json({
                success: true,
                updated
            })
        }
        else {
            res.status(402).json({
                success: false,
            })

        }

    }
    catch (err) {
        res.status(401).json({
            success: false,
            message: "Please Enter Different Name, number and email ",
            errMessage: err.message

        })

    }

})

exports.changeDescription = catchAsyncErrors(async (req, res, next) => {
    const { id, desc } = req.body;


    try {
        const updated = await PersonSchema.findByIdAndUpdate({ _id: id }, { desc: desc })

        if (updated) {
            res.status(200).json({
                success: true,
                updated
            })
        }
        else {
            res.status(400).json({
                success: false,
                message: "Error in updating the Description",
            })

        }

    }
    catch (err) {
        res.status(400).json({
            success: false,
            message: "Error in updating the Description",
            errMessage: err.message

        })

    }

})
exports.changePassword = catchAsyncErrors(async (req, res, next) => {
    const { id, desc } = req.body;


    try {
        const updated = await PersonSchema.findByIdAndUpdate({ _id: id }, { desc: desc })

        if (updated) {
            res.status(200).json({
                success: true,
                updated
            })
        }
        else {
            res.status(400).json({
                success: false,
                message: "Error in updating the Description",
            })

        }

    }
    catch (err) {
        res.status(400).json({
            success: false,
            message: "Error in updating the Description",
            errMessage: err.message

        })

    }

})
exports.login = catchAsyncErrors(async (req, res, next) => {
    const { name, password } = req.body;
    const listUsers = await PersonSchema.find();

    const users = listUsers.find((user) => {
        if (user.name == name && user.password == password) {

            return user;
        }
        else return null;
    })
    if (users) {

        res.status(200).json({
            success: true,
            users
        })

    }
    else {
        res.status(200).json({
            success: false,
            message: "Incorrect Email or Password"
        })
    }



})
exports.getAllpersons = catchAsyncErrors(async (req, res, next) => {
    const allPerons = await PersonSchema.find();
    if (allPerons) {
        res.status(200).json({
            success: true,
            persons: allPerons
        })
    }
    else {
        res.status(402).json({
            success: false,
        })
    }
})
exports.updatePerson = catchAsyncErrors(async (req, res, next) => {
    const { id, name, email, number, password } = req.body;
    const updateUserData =
    {
        name, password, email, number
    };

    try {
        const updated = await PersonSchema.findByIdAndUpdate(id, updateUserData)

        if (updated) {
            res.status(200).json({
                success: true,
                updated
            })
        }
        else {
            res.status(402).json({
                success: false,
            })

        }

    }
    catch (err) {
        res.status(401).json({
            success: false,
            message: "Please Enter Different Name, number and email ",
            errMessage: err.message

        })

    }

})
exports.getPersonByID = catchAsyncErrors(async (req, res, next) => {
    const { id } = req.params;

    const personFetched = await PersonSchema.findById(id);
    if (personFetched)
        res.status(200).json({
            success: true,
            personFetched
        })
    else
        res.status(401).json({
            success: false,
        })
})

exports.addPerson = catchAsyncErrors(async (req, res, next) => {
    const { name, email, number, password } = req.body;
    const profile = "null";
    try {
        const personCreated = await PersonSchema.create({
            name, password, email, number, profile
        });

        if (personCreated) {

            res.status(200).json({
                success: true,
                personCreated
            })
        }
        else {
            res.status(401).json({
                success: false,
                message: "Please Enter Different Name, number and email "
            })
        }

    }
    catch (err) {
        res.status(401).json({
            success: false,
            message: "Please Enter Different Name, number and email ",
            errMessage: err.message
        })

    }



})
exports.getTasksByUser = catchAsyncErrors(async (req, res, next) => {
    const { userId } = req.params;
    const allTasks = await TasksSchema.find();
    const filteredById = allTasks.filter((item) => {

        if (item.assignTo == userId) {
            return item;
        }

    }
    );
    res.status(200).json({
        success: true,
        filteredById,

    })
})


exports.getUnCompletedTasksByUser = catchAsyncErrors(async (req, res, next) => {
    const { userId } = req.params;
    const allTasks = await TasksSchema.find();
    const filteredById = allTasks.filter((item) => {
        if (item.assignTo == userId) {
            if (!item.taskStatus)
                return item;
        }

    }
    );
    res.status(200).json({
        success: true,
        filteredById,
    })
})
exports.getCompletedTasksByUser = catchAsyncErrors(async (req, res, next) => {
    const { userId } = req.params;
    const allTasks = await TasksSchema.find();
    const filteredById = allTasks.filter((item) => {


        if (item.assignTo == userId) {
            if (item.taskStatus)
                return item;
        }

    }
    );
    res.status(200).json({
        success: true,
        filteredById,

    })
})

exports.completeTasks = catchAsyncErrors(async (req, res, next) => {
    const { taskId, userId } = req.body;
    const foundTask = await TasksSchema.findById(taskId);
    foundTask.taskStatus = true;
    if (foundTask.assignTo == userId) {
        await TasksSchema.updateOne({ _id: taskId }, { taskStatus: true });

        res.status(200).json({
            success: true,
            message: "task completed"
        })
    }
    else {
        res.status(400).json({
            success: false,
            message: "You cant complete this"
        })
    }
})
exports.requestsById = catchAsyncErrors(async (req, res, next) => {
    const { userId } = req.params;
    const foundPerson = await PersonSchema.findById(userId);
    if (foundPerson) {
        const requestList = foundPerson.requests;

        res.status(200).json({
            success: true,
            requestList
        })
    }
    else {
        res.status(402).json({
            success: false

        })
    }


})
exports.cancelRequest = catchAsyncErrors(async (req, res, next) => {
    const { userId, eventId } = req.body;
    const foundPerson = await PersonSchema.findById(userId);
    if (foundPerson) {
        const requestList = foundPerson.requests;
        const index = searchIndex(requestList, eventId);
        if (index != -1) {
            foundPerson.requests.splice(index, 1);
            const updatedPerson = await PersonSchema.updateOne({ _id: foundPerson._id }, { member: [...foundPerson.member], requests: [...requestList] });
            res.status(200).json({
                success: true,
            })

        }
        else {

            res.status(402).json({
                success: false,
                msg: "Not Found The Event",
                requestList,
                index
            })
        }

    }
    else {
        res.status(400).json({
            success: false,
            message: "Person Not Found"
        })
    }




})

exports.acceptRequest = catchAsyncErrors(async (req, res, next) => {
    const { userId, eventId, eventName } = req.body;
    const foundPerson = await PersonSchema.findById(userId);
    if (foundPerson) {
        const requestList = foundPerson.requests;

        const index = searchIndex(requestList, eventId);

        if (index != -1) {

            const addingToEvent = foundPerson.requests.splice(index, 1);

            foundPerson.member.push(addingToEvent[0])



            const eventByID = await EventSchema.findById(eventId);

            eventByID.team.push({ id: foundPerson._id, name: foundPerson.name })

            requestList.splice(index, 1);

            // const UpdatedEvent = await EventSchema.updateOne(eventByID);
            // const updatedPerson = await PersonSchema.updateOne(foundPerson);

            const UpdatedEvent = await EventSchema.updateOne({ _id: eventByID._id }, { team: [...eventByID.team] });
            const updatedPerson = await PersonSchema.updateOne({ _id: foundPerson._id }, { member: [...foundPerson.member], requests: [...requestList] });
            res.status(200).json({
                success: true,
            })
        }
        else {

            res.status(402).json({
                success: false,
                msg: "Not Found The Event",
                requestList,
                index
            })
        }
    }
    else {
        res.status(402).json({
            success: false,
            msg: "User  Not Found"
        })
    }
})
const searchIndex = (list, id) => {
    let boolean = false;
    list.forEach((element, i) => {
        // console.log(element.id == id)
        if (element.id == id) {
            boolean = true;
            return i;
        }
    });
    if (!boolean) {
        return -1;
    }
}
const verifyImage = (image) => {
    const name = image.name;
    if (name) {
        const splitted = name.split(".");
        if (splitted.length == 2) {
            if (splitted[1] == "png" || splitted[1] == "jpg" || splitted[1] == "jpeg")
                return true;
            else
                return false;
        }
        else {
            return false;


        }

    }
    else {
        return false;
    }

};

exports.personDetails = catchAsyncErrors(async (req, res, next) => {
    const { userId } = req.params;
    let personById;
    const allTasks = await TasksSchema.find();
    try {
        personById = await PersonSchema.findById(userId);

        
        if (personById) {
            const completed = allTasks.filter((item) => {


                if (item.assignTo == userId) {
                    if (item.taskStatus)
                        return item;
                }

            }
            );
            const unCompleted = allTasks.filter((item) => {
                if (item.assignTo == userId) {
                    if (!item.taskStatus)
                        return item;
                }

            }
            );
            const events = await EventSchema.find();
            const teamsLists = events.filter(event => {
                const listofOneTeam = event.team;
                if (event.userId == userId)
                    return event;
                // console.log(listofOneTeam)  
                for (let i = 0; i < listofOneTeam.length; i++) {
                    if (userId == listofOneTeam[i].id)
                        return event;
                }
            });
            res.status(200).json({
                success: true,
                completed: completed.length,
                unCompleted: unCompleted.length,
                requests: personById.requests.length,
                events: teamsLists.length
            })


        }
        else {
            res.status(404).json({
                success: false,
                message: "User Not Found"
            })

        }
    } catch (error) 
    {
            res.status(404).json({
                success: false,
                message: "User Not Found"
            })
    }
    

})
