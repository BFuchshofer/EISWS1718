var router                                  = express.Router();

// VARIABLES
var givenRooms;

// FUNCTIONS

// ROUTES
router.post( '/room', function( req, res ){
    req.on( 'data', function( chunk ){
        var data                            = JSON.parse( chunk );

        console.log( data );
        var newData                         = {
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

            if( data.beacon != null && data.filter != null ){
              console.log( '[INFO] Room Suggestion' );

              givenRooms = [];
              for( var i = 0; i < parseInt( data.roomCount, 10 ); i++ ){
                console.log( "i" );
                FUNCTIONS.suggestion( data.beacon, [] )
                .then( function( result ){
                  console.log( ">>>>>>>1" );
                  // Raum für UserX reservieren
                  FUNCTIONS.userAction( data.token, data.person, "rm_" + result.room.id )
                  // Raum zurück senden
                  .then( function( status ){
                    console.log( ">>>>>>>2" );
                    result.status = status;
                    givenRooms.push( result );
                    return true;
                  })
                  .then( function( result ){
                    console.log( ">>>>>>>3" );
                    if( i == givenRooms.length ){
                      res.status(200).send( givenRooms );
                    }
                  });
                });
              }
            } else {
              res.status( 403 ).send( 'Missing Information' );
            }

        } else if (data.token == "UPDATE") {

            // TODO
            // neue Raumabfrage, im prinzip wie bei GET?

            // vorherigen Raum von UserX freigeben
            // Raumvorschlag aus freie Raumliste auswählen
            // neuen Raum für UserX reservieren
            // Raum zurück senden

            console.log( "Update Room" );
            res.status(200).send( data );

        } else if (data.token == "BOOK") {

            // TODO

            // vorherigen Raum von UserX für UserX buchen
            // KEY an UserX und Raum senden



            console.log("Book Room");
            res.status(200).send( data );
        } else {
            console.log("Error");
            res.status(400).send( data );
        }
    });

});

router.post( '/admin/item', function( req, res ){
  req.on( 'data', function( chunk )
  {
    console.log( '>>>> RECEIVED DATA ON /admin/item' );
    console.log( chunk );
    console.log( '>>>> END OF DATA' );

    res.status( 200 ).send( 'OK' );
  });
});

// ERROR HANDLING

// EOF
module.exports                              = router;
