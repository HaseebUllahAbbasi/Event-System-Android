const express  = require('express')
const expressFile  = require('express-fileupload')

const app = new express()
const errorMiddleware = require('./middlewares/errors')
const userRouter = require('./routes/PersonRoutes')
const eventRouter = require('./routes/EventRoutes')
const taskRouter = require('./routes/TaskRoutes')
const notesRouter = require('./routes/NotesRoutes')
const bodyParser = require('body-parser')

app.use(expressFile({
    useTempFiles : true,
    tempFileDir : '/tmp/'
}));
app.use(express.json())
app.use(bodyParser.urlencoded({extended:true}));
app.use(userRouter)
app.use(notesRouter)
app.use(eventRouter)
app.use(taskRouter)
module.exports = app;
