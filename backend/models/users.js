const mongoose = require('mongoose')

const users = mongoose.Schema({

    name: {
        type: String,
        required: true,

    },
    bio:
    {
        type : String,
        required: true
    },
    age: {
        type: Number,
        required: true,

    },
    accupation: {
        type: String,
        required: true,

    },
    profileImage: {
        type: String,
        required: true,

    },
    birthDate: {
        type: String,
        required: true,

    },
    joingDate: {
        type: Date,
        required: true,

    },

})

module.exports = mongoose.model('users', users)