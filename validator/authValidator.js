const { body } = require('express-validator');         
const valResultUtils = require('../utils/utilsValidator'); 

const register = [
  body('email')
    .notEmpty()
    .withMessage('Email tidak boleh kosong.')
    .isEmail()
    .withMessage('email tidak valid.'),
  body('password')
    .notEmpty()
    .withMessage('Password tidak boleh kosong.')
    .isLength({ min: 8 })
    .withMessage('Password harus terdiri dari minimal 8 kata.'),
  body('username')
    .notEmpty()
    .withMessage('Username tidak boleh kosong.')
    .isLength({ min: 2 })
    .withMessage('username maksimal 20 karakter.'),
  valResultUtils,
];

const login = [
  body('email')
    .notEmpty()
    .withMessage('Email tidak boleh kosong.')
    .isEmail()
    .withMessage('Email tidak valid'),
  body('password')
    .notEmpty()
    .withMessage('Password tidak boleh kosong.'),
  valResultUtils,
];

const authValidator = {  
  register,
  login
};

module.exports = authValidator;  