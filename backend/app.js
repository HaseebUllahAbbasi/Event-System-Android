const express  = require('express')
const app = new express()
const errorMiddleware = require('./middlewares/errors')
const userRouter = require('./routes/PersonRoutes')
const eventRouter = require('./routes/EventRoutes')
const taskRouter = require('./routes/TaskRoutes')
const notesRouter = require('./routes/NotesRoutes')

app.use(express.json())

app.use(userRouter)
app.use(notesRouter)
app.use(eventRouter)


module.exports = app;
