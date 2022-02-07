const NotesSchema = require('../models/Notes')

const catchAsyncErrors = require('../middlewares/catchAsyncErrors');

exports.getAllNotes = catchAsyncErrors(async (req,res,next)=>
{
    const allTasks = await NotesSchema.find();
    if(allTasks.length==0)
        res.status(404).json({
            success:false,
            message:"Notes Not Found"
        })
    else
        res.status(200).json({
            success:true,
            tasks: allTasks
        })    
})
