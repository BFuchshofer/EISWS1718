var router                  = express.Router();


router.post( '/room/reservation', function( req, res ){
    req.on( 'data', function( chunk ){
        var data = JSON.parse( chunk );
        console.log( data );

        res.status(200).send( data );
    });
});


router.post( '/room/booking', function( req, res ){
    req.on( 'data', function( chunk ){
        var data = JSON.parse( chunk );
        console.log( data );

        res.status( 200 ).send( data );
    });
});

router.post( '/room/cancelreservation', function( req, res ){
    req.on( 'data', function( chunk ){
        var data = JSON.parse( chunk );
        console.log( data );

        res.status( 200 ).send( data );
    });
});

router.post( '/room/cancelbooking', function( req, res ){
    req.on( 'data', function( chunk ){
        var data = JSON.parse( chunk );
        console.log( data );

        res.status( 200 ).send( data );
    });
});

router.post( '/room/extendbooking', function( req, res ){
    req.on( 'data', function( chunk ){
        var data = JSON.parse( chunk );
        console.log( data );

        res.status( 200 ).send( data );
    });
});

module.exports              = router;
