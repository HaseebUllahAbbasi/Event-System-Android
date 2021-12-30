const express = require('express');
const router = express.Router();
const { getAllUsers } = require('../controllers/UserController');

router.route('/users').get(getAllUsers);

module.exports = router ;
