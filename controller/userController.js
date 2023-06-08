const db = require('../config/dbconnect');   
const bcrypt = require ('bcryptjs');   
const jwt = require ('jsonwebtoken');
const fs = require('fs');

//function register
const register = async (req, res) => {                      
  const { email, password, username } = req.body            
  const salt = await bcrypt.genSalt();   
  const hashPassword = await bcrypt.hash(password, salt);                                
  
  const values = [email, hashPassword, username];                                                                       
  db.query('insert into users (email, password, username) values (?,?,?)', values, function(error, rows, fields) {      
    if (error) {  
      return res.status(500).send({
        status: 500,
        success: false,
        message: error.message 
      })
    } else {
        return res.status(200).send({
          status: 200,
          success: true,
          message: 'user created'
        })
      }
  });
};

//function login
const login = async (req, res) => {         
  const { email, password } = req.body;     

  const values = [email];                                                                   
  db.query('SELECT * FROM users WHERE email = ?', values, async function (error, rows) {  
    if (error) {
      return res.status(500).json({ error: "user belum terdaftar" });
    }

    if (rows.length === 1) {
      const match = await bcrypt.compare(password, rows[0].password);  
      if (!match) {  
        return res.status(400).json({ msg: "silahkan mengisi password yang benar" });
      }

      const token = jwt.sign({ userID: rows[0].id, email: rows[0].email }, process.env.TOKEN_SECRET);

      return res.status(200).json({
        status: 200,
        error: true,
        message: 'Login success',
        result: {
          token: token,
          userID: rows[0].id,
          email: rows[0].email,
          username: rows[0].username
        }
      });
    }
    return res.send("User tidak ditemukan, pastikan mengisi semuanya dengan benar.");
  });
};

//function cekdata
const cekData = async (req, res) => {                         
  const query = 'SELECT id, username, password, images, email FROM users';    
  
  db.query(query, (error, results) => {                                      
    if (error) {  
      console.error('Kesalahan menjalankan kueri:', error);
      res.status(500).json({ error: 'Kesalahan Server Internal' });
    } else {     
      res.status(200).json({                                   
        result: ({
          data: rows
        })                                  
      });  
    }
  });
};

//function news
const news = async (req, res) => {
  const query = 'SELECT id, images, title, description, link FROM news';    

  db.query(query, (error, results) => {  
    if (error) {
      console.error('Kesalahan menjalankan kueri:', error);
      res.status(500).json({ error: 'Kesalahan Server Internal' });
    } else {
      res.status(200).json(results);
    }
  });
};

//function quiz
const quiz = async (req, res) => {    
  const query = 'SELECT id, nomor, question, option_a, option_b, option_c, option_d FROM quiz';       

  db.query(query, (error, results) => { 
    if (error) {
      console.error('Kesalahan menjalankan kueri:', error);
      res.status(500).json({ error: 'Kesalahan Server Internal' });
    } else {
      res.status(200).json(results);
    }
  });
};

//Function profil
const profil = async (req, res) => {      
  const query = 'SELECT id, username, email FROM users';              
  
  db.query(query, (error, results) => {                               
    if (error) {   
      console.error('Kesalahan menjalankan kueri:', error);
      res.status(500).json({ error: 'Kesalahan Server Internal' });
    } else {      
      res.status(200).json(results);
    }
  });
};

//function update Photo Profil
const userImage = async (req, res) => {
  try{                                                    
    if (!req.file){
      return res.status(401).json({
          message: "gagal upload image"
      });
    }
    
    const id = req.userID;
    const imageUrl = req.file.cloudStoragePublicUrl;

    const query = 'UPDATE users SET images = ? WHERE id = ?';                   

    db.query(query, [imageUrl.toString(), id], function(error, results) {      
      if(error) { 
        res.status(500).json({ success: false, message: 'Gagal mengupdate image URL' });
      } else { 
        res.status(200).json({ success: true, message: 'Image URL berhasil diupdate' });
      }
    });
  } catch (error) {                
      return res.send(error);
    }
};

module.exports = {   
  register,
  login,
  cekData,
  news,
  quiz,
  profil,
  userImage
}