require("dotenv").config()                           
const express = require ('express');                
const userRoute = require('./router/userRoutes');    

const app = express();
app.use(express.json());
app.use('/api', userRoute);  

app.get("/home", (req ,res)=>{
  res.send("Selamat Datang di Halaman Utama Click")
});

const port = process.env.PORT || 8000
app.listen(port,()=>{
  console.log(`Server berjalan pada PORT : ${port}`);
});