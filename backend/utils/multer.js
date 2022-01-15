const multer = require('multer')
const path = require('path')
module.exports = multer({

    storage:multer.diskStorage({}),
    fileFilter:(req,file,cb)=>{
        let ext = path.extname(file.originalname);
        if(ext!= ".jpg" && ext!= ".jpeg" && ext != ".png")
        {
            console.log("wrong file");

            cb(new Error("File type is not supported"),false);
            return;

        }
        console.log("sending next");
        cb(null,true)
    }

})