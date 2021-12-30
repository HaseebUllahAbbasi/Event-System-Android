const User = require('../models/users');

const ErrorHandler = require('../utils/errorHandler')
const catchAsyncErrors = require('../middlewares/catchAsyncErrors')
exports.getAllUsers = catchAsyncErrors(async (req, res, next) => {
    const  allUsers = await User.find();
    if(allUsers.length==0)
    {
        res.status(404).json({
            success:false,
            message: "No Users Found"
        })
    }
})


