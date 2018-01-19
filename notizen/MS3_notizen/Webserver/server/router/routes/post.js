var router                                  = express.Router();

// VARIABLES

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

              FUNCTIONS.suggestion( data.beacon, [] )
              // Raum für UserX reservieren
              // Raum zurück senden
              .then( function( result ){
                res.status( 200 ).send( result );
              });
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
        } else if (data.token == "CANCEL") {
            
            // TODO
            
            // Raum_id aus dem Request im System wieder freigeben
            // Unterscheidung zwischen abbrechen der Reservierung und Buchung eines Raumes auf Serverseite?
            console.log("Cancel Room");
            
            res.status(200).send(); // Keine Daten im response notwendig
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
