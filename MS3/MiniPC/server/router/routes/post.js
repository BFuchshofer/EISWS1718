var router                  = express.Router();


router.post( '/room', function( req, res ){
    req.on( 'data', function( chunk ){
        var data                = JSON.parse( chunk );
        
      // TODO 
	// Key auslesen und in Datei speichern?!

            res.status(200).send(); 
        
    
    });
});





module.exports              = router;