const { createPool } = require('mysql');  

const db = createPool({                  
  host:process.env.DB_HOSTNAME,           
  user:process.env.DB_USERNAME,
  password:process.env.DB_PASSWORD,
  database:process.env.DB_NAME,
  connectionLimit:10
});

db.getConnection((error, connection) => {
  if (error) {
    console.error("Koneksi gagal: ", error);    
  } else {
    console.log("db terhubung!");              
    connection.release();
  }
});

module.exports = db;