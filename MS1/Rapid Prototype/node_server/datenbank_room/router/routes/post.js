var router                  = express.Router();


router.post( '/room/suggestion', function( req, res ){
    req.on( 'data', function( chunk ){
        var data = JSON.parse( chunk );

        //console.log( data );

        var johntitor = data.johntitor;

        DB_FUNCTIONS.getOccupiedBy( johntitor.room_nr )
        .then( function( result ){
            console.log( johntitor.user + ', ' + result );
            if(  result == johntitor.user  ||  result == 'false' ){

                /////////////// DB
                DB_FUNCTIONS.remRoomFromEmptyList( johntitor.room_nr );
                DB_FUNCTIONS.setSuggestion( johntitor.room_nr, johntitor.user, johntitor.suggestion_begin, johntitor.suggestion_end );
                //////////////////

                res.status( 200 ).send( data );
            } else {
                console.log( 'hi1' );
                res.status( 401 ).send( 'UNAUTHORIZED' );
            }
        })
        .catch(function(){
            res.status(404).send( 'NOT FOUND' );
        });

    });
});

router.post( '/room/reservation', function( req, res ){
    req.on( 'data', function( chunk ){
        var data = JSON.parse( chunk );

        //console.log( data );

        var johntitor = data.johntitor;

        DB_FUNCTIONS.getOccupiedBy( johntitor.room_nr )
        .then( function( result ){
            console.log( johntitor.user + ', ' + result );
            if(  result == johntitor.user  ||  result == 'false' ){

                /////////////// DB
                DB_FUNCTIONS.remRoomFromEmptyList( johntitor.room_nr );
                DB_FUNCTIONS.setReservation( johntitor.room_nr, johntitor.user, johntitor.reservation_begin, johntitor.reservation_end );
                //////////////////

                res.status( 200 ).send( data );
            } else {
                console.log( 'hi1' );
                res.status( 401 ).send( 'UNAUTHORIZED' );
            }
        })
        .catch(function(){
            res.status(404).send( 'NOT FOUND' );
        });
    });
});

router.post( '/room/booking', function( req, res ){
    req.on( 'data', function( chunk ){
        var data = JSON.parse( chunk );
        //console.log( data );

        var johntitor = data.johntitor;

        DB_FUNCTIONS.getOccupiedBy( johntitor.room_nr )
        .then( function( result ){
            console.log( johntitor.user + ', ' + result );
            if(  result == johntitor.user  ||  result == 'false' ){

                /////////////// DB
                DB_FUNCTIONS.remRoomFromEmptyList( johntitor.room_nr );
                DB_FUNCTIONS.setBooking( johntitor.room_nr, johntitor.user, johntitor.booking_begin, johntitor.booking_end );
                //////////////////

                res.status( 200 ).send( data );
            } else {
                res.sendStatus( 401 );
            }
        })
        .catch(function(){
            res.sendStatus(404);
        });



    });
});

router.post( '/room/cancelreservation', function( req, res ){
    req.on( 'data', function( chunk ){
        var data = JSON.parse( chunk );
        //console.log( data );

        var johntitor = data.johntitor;
        DB_FUNCTIONS.getOccupiedBy( johntitor.room_nr )
        .then( function( result ){
            console.log( johntitor.user + ', ' + result );
            if(  result == johntitor.user ){

                /////////////// DB
                DB_FUNCTIONS.addRoomToEmptyList( johntitor.room_nr );
                DB_FUNCTIONS.setSuggestion( johntitor.room_nr, false, -1, -1 );
                DB_FUNCTIONS.setReservation( johntitor.room_nr, false, -1, -1 );
                //////////////////

                res.status( 200 ).send( data );
            } else {
                console.log( 'hi1' );
                res.status( 401 ).send( 'UNAUTHORIZED' );
            }
        })
        .catch(function(){
            res.status(404).send( 'NOT FOUND' );
        });

        res.status( 200 ).send( data );
    });
});

router.post( '/room/cancelbooking', function( req, res ){
    req.on( 'data', function( chunk ){
        var data = JSON.parse( chunk );
        //console.log( data );

        var johntitor = data.johntitor;

        DB_FUNCTIONS.getOccupiedBy( johntitor.room_nr )
        .then( function( result ){
            console.log( johntitor.user + ', ' + result );
            if(  result == johntitor.user ){

                /////////////// DB
                DB_FUNCTIONS.addRoomToEmptyList( johntitor.room_nr );
                DB_FUNCTIONS.setSuggestion( johntitor.room_nr, false, -1, -1 );
                DB_FUNCTIONS.setReservation( johntitor.room_nr, false, -1, -1 );
                DB_FUNCTIONS.setBooking( johntitor.room_nr, false, -1, -1 );
                //////////////////

                res.status( 200 ).send( data );
            } else {
                console.log( 'hi1' );
                res.status( 401 ).send( 'UNAUTHORIZED' );
            }
        })
        .catch(function(){
            res.status(404).send( 'NOT FOUND' );
        });
    });
});

router.post( '/room/extendbooking', function( req, res ){
    req.on( 'data', function( chunk ){
        var data = JSON.parse( chunk );



        res.status( 200 ).send( data );
    });
});

module.exports              = router;
