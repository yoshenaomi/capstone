const jwt = require ('jsonwebtoken');   

const verifyToken = (req, res, next) => {               
    const authHeader = req.headers['authorization']; 
    const token = authHeader                   
    if (!token) {                            
        return res.status(403).send({
            error: true,
            message: 'No token provided!',     
        });
    }
    
    jwt.verify(token, process.env.TOKEN_SECRET, (err, decoded) => {   
        if (err) {
            return res.status(401).send({
                error: true,
                message: 'Unauthorized!',
            });
        }
        req.userID = decoded.userID;
        next();   
    });
};
    
module.exports = verifyToken;   