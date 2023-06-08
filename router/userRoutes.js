const router = require('express').Router(); 
const { uploadToGcs , multerUpload } = require('../config/uploadImage');
const { 
    register,  
    login,
    cekData,
    news,
    quiz,
    profil,
    userImage
} = require ('../controller/userController');     

const verifyToken = require ('../controller/verifyToken');    
const authValidator = require("../validator/authValidator");  

router.post("/register", authValidator.register, register);
router.post("/login", authValidator.login, login);
router.get("/privat-data", verifyToken, cekData);
router.get("/news", news);
router.get("/quiz", quiz);
router.get("/profil", profil);
router.post('/profil/images', verifyToken, multerUpload.single('image'), uploadToGcs, userImage);

module.exports = router;  
