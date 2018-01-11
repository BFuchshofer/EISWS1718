var router                  = express.Router();


router.post( '/room', function( req, res ){
    req.on( 'data', function( chunk ){
        var data                = JSON.parse( chunk );
        
        if (data.token != null) {
            console.log( "Update Room" );
            res.status(200).send( data ); 
        } else {
            var newData = {
            "data":{
                "whiteboard":data.whiteboard,
                "person":data.person,
                "beacon":data.beacon,
                "beamer":data.beamer,
                "blackboard":data.blackboard,
                "roomSize":data.roomSize,
                "token": 1234567890
            }
            };
    
            console.log( "Get New Room" );
            res.status(200).send( JSON.stringify( newData ) ); 
        }
    
    });
});




module.exports              = router;