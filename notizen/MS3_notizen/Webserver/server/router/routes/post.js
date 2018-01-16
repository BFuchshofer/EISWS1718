var router                  = express.Router();

// Raum anfragen und ggf. updaten
router.post( '/room', function( req, res ){
    req.on( 'data', function( chunk ){
        var data                = JSON.parse( chunk );
        
        var newData = {
                "data":{
                    "beacon":data.beacon,
                    "person":data.person,
                    "roomSize":data.roomSize,
                    "blackboard":data.blackboard,
                    "whiteboard":data.whiteboard,
                    "beamer":data.beamer  
               }
            };
        
        if (data.token == "GET") {
            
            // TODO
            // neue Raumabfrage
            
            console.log("Get new Room");
            res.status(200).send( data ); 
            
        } else if (data.token == "UPDATE") {
            
            // TODO
            // neue Raumabfrage, im prinzip wie bei GET?
            
            console.log( "Update Room" );
            res.status(200).send( data ); 
            
        } else if (data.token == "BOOK") {
            
            
            // Raum buchen
            
            console.log("Book Room");
            res.status(200).send( data ); 
        } else {
            console.log("Error");
            res.status(400).send( data ); 
        }
    });
});

  





module.exports              = router;