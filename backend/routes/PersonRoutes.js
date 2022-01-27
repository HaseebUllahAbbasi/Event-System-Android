const express = require('express');
// const multer = require('../utils/multer');

const multer = require('multer');
var storage = multer.diskStorage({
    destination: function (req, file, cb) {
      cb(null, 'uploads')
    },
    filename: function (req, file, cb) {
      cb(null, file.fieldname + '-' + Date.now())
    }
  })
  
  var upload = multer({ storage: storage })


const { addPerson, updatePerson,getAllNames,changePassword,completeTasks,cancelRequest,requestsDetailsById,myEvents,getEventByUserId,getTasksByUser,findByName,requestsById,acceptRequest,login,deletePerson,getAllpersons, getPersonByID, getCompletedTasksByUser, getUnCompletedTasksByUser, changePic, changeDescription, changeData } = require('../controllers/PersonController');
const router = express.Router();
router.route('/persons').get(getAllpersons);
router.route('/person').put(updatePerson);
router.route('/person/changeDesc').post(changeDescription);
router.route('/person/changeData').post(changeData);
router.route('/person/changePassword').post(changePassword);
router.route('/person').post(addPerson);
router.route('/person').delete(deletePerson);
router.route('/person/:id').get(getPersonByID);
router.route('/login').post(login)
router.route('/personByName/:name').get(findByName)
router.route('/acceptRequest').post(acceptRequest)
router.route('/cancelRequest').post(cancelRequest)
router.route('/requests/:userId').get(requestsById)
router.route('/requestsDetails/:userId').get(requestsDetailsById)

router.route('/changePic/:id').post(changePic);

router.route('/tasksByID/:userId').get(getTasksByUser)
router.route('/completedTasks/:userId').get(getCompletedTasksByUser)
router.route('/unCompletedTasks/:userId').get(getUnCompletedTasksByUser)
router.route('/completeTask').post(completeTasks);
router.route('/getEventByUser/:id').get(getEventByUserId);
router.route('/myEvents').post(myEvents);
router.route('/getAllName').get(getAllNames);

module.exports = router;
