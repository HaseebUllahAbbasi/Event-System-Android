

const app = require('./app')
const dotenv = require('dotenv')
dotenv.config({path:"./config/config.env"})

const connectDatabse = require('./config/connection')

//connecting to db 
connectDatabse();

const server =  app.listen(process.env.PORT, ()=>
{
    console.log(`Server is port ${process.env.PORT} in ${process.env.NODE_ENV}`);
})
 
process.on("unhandledRejection",(err)=> {
    console.log(`Error name : ${err.name} , Error msg ${err.message}  `)
    console.log("Shutting down Server due to  Rejection Errors");
    server.close(()=>
    {
        process.exit(); 
    });
})
