const TasksSchema = require('../models/Task')

const catchAsyncErrors = require('../middlewares/catchAsyncErrors');

exports.getAllTasks = catchAsyncErrors(async (req,res,next)=>{
    const allTasks = await TasksSchema.find();
    if(allTasks.length==0)
        res.status(404).json({
            success:false,
            message:"Tasks Not Found"
        })
    else
        res.status(200).json({
            success:true,
            tasks: allTasks
        })    
})
