var router                  = express.Router();


router.post( '/room/suggestion', function( req, res ){
    req.on( 'data', function( chunk ){
        var data = JSON.parse( chunk );

        //console.log( data );

        var room = data.room;

        //TODO: check for user suggestion

        /////////////// DB
        //DB_FUNCTIONS.getSuggestion( room.user, room.suggestion_begin, room.suggestion_end );
        //////////////////

        res.status(200).send( DB_FUNCTIONS.setSuggestion( room.room_nr, room.user, room.suggestion_begin, room.suggestion_end ) );
    });
});

router.post( '/room/reservation', function( req, res ){
    req.on( 'data', function( chunk ){
        var data = JSON.parse( chunk );

        //console.log( data );

        var johntitor = data.johntitor;

        //TODO: check for user suggestion

        /////////////// DB
        DB_FUNCTIONS.setReservation( johntitor.room_nr, johntitor.user, johntitor.reservation_begin, johntitor.reservation_end );
        //////////////////

        res.status(200).send( data );
    });
});

router.post( '/room/booking', function( req, res ){
    req.on( 'data', function( chunk ){
        var data = JSON.parse( chunk );
        //console.log( data );

        var johntitor = data.johntitor;

        //TODO: check for user reservation
        //TODO: if room is reserved check for same user

        /////////////// DB
        DB_FUNCTIONS.setBooking( johntitor.room_nr, johntitor.user, johntitor.booking_begin, johntitor.booking_end );
        //////////////////

        res.status( 200 ).send( data );
    });
});

router.post( '/room/cancelreservation', function( req, res ){
    req.on( 'data', function( chunk ){
        var data = JSON.parse( chunk );
        //console.log( data );

        var johntitor = data.johntitor;

        /////////////// DB
        DB_FUNCTIONS.setSuggestion( data.johntitor.room_nr, false, -1, -1 );
        DB_FUNCTIONS.setReservation( johntitor.room_nr, false, -1, -1 );
        //////////////////

        res.status( 200 ).send( data );
    });
});

router.post( '/room/cancelbooking', function( req, res ){
    req.on( 'data', function( chunk ){
        var data = JSON.parse( chunk );
        //console.log( data );

        /////////////// DB
        DB_FUNCTIONS.setSuggestion( data.johntitor.room_nr, false, -1, -1 );
        DB_FUNCTIONS.setReservation( data.johntitor.room_nr, false, -1, -1 );
        DB_FUNCTIONS.setBooking( data.johntitor.room_nr, false, -1, -1 );
        //////////////////

        res.status( 200 ).send( data );
    });
});

router.post( '/room/extendbooking', function( req, res ){
    req.on( 'data', function( chunk ){
        var data = JSON.parse( chunk );



        res.status( 200 ).send( data );
    });
});

module.exports              = router;
