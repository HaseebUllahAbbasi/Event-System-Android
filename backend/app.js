const express  = require('express')
const cookieParser = require('cookie-parser')
const app = new express()
const errorMiddleware = require('./middlewares/errors')
const userRouter = require('./routes/UserRoutes')

app.use(express.json())
app.use(cookieParser())

app.use(userRouter)

module.exports = app;