var router                  = express.Router();

router.post( '/room/reservation', function( req, res ){
    req.on( 'data', function( chunk ){
        var data = JSON.parse( chunk );
        console.log( data );
        //TODO: put-request to roomDB

        var timer_begin = Date.now();
        var timer_end = timer_begin + 15*60*1000;
        var post_data = {
            "johntitor":{
                "key":data.key,
                "user":data.user,
                "reservation_begin":timer_begin,
                "reservation_end":timer_end
            }
        };
        console.log( post_data );
        var options             = {
            host: VARIABLES.roomdbaddr,
            port: VARIABLES.roomdbport,
            path: '/room/reservation',
            method: 'POST',
            headers:{
                'Content-Type':'application/json',
                'Content-Length':Buffer.byteLength( new Buffer( JSON.stringify( post_data )) )
            }
        };

        var externalRequest     = http.request( options, function( externalResponse ){
            if( externalResponse.statusCode == 200 ){
                externalResponse.on( 'data', function( chunk ){
                    console.log( 'get.js     - exRes: ' + JSON.parse( chunk ));
                    res.status(200).send( chunk );
                });
            }
        });
        externalRequest.write( new Buffer( JSON.stringify( post_data )) );
        externalRequest.end();

        //res.status(200).send( 'OK' );

    });
});
/* old
router.post( '/room/booking', function( req, res ){
    console.log( 'IN POST /room/booking' );
    req.on( 'data', function( chunk ){
        var data = JSON.parse( chunk );
        console.log( data );
        //TODO: check if room is already suggested to or reserved or booked by another user
        //TODO: put-request to roomDB set timer to system time

        var timer_begin = Date.now();
        var timer_end = timer_begin + 1*60*60*1000;
        res.status(200).send( { 'booking_begin': timer_begin, "booking_end":timer_end } );
    });
});
router.post( '/room/cancelbooking', function( req, res ){
    req.on( 'data', function( chunk ){
        var data = JSON.parse( chunk );
        console.log( data );
        //TODO: check if room is already suggested to or reserved or booked by another user
        //TODO: put-request to roomDB set timer to -1

        res.status(200).send( 'OK' );
    });
});
router.post( '/room/extendbooking', function( req, res ){
    req.on( 'data', function( chunk ){
        var data = JSON.parse( chunk );
        console.log( data );


        var timer_end = Date.now() + 1*60*60*1000;
        res.status(200).send( { "endTimeBooking":timer_end } );
    });
});
*/
router.post( '/room/booking', function( req, res ){

        req.on( 'data', function( chunk ){
            var data = JSON.parse( chunk );
            console.log( data );

            var timer_begin = Date.now();
            var timer_end = timer_begin + 1*60*60*1000;
            var post_data = {
                "johntitor":{
                    "key":data.key,
                    "user":data.user,
                    "booking_begin":timer_begin,
                    "booking_end":timer_end
                }
            };
            console.log( post_data );
            var options             = {
                host: VARIABLES.roomdbaddr,
                port: VARIABLES.roomdbport,
                path: '/room/booking',
                method: 'POST',
                headers:{
                    'Content-Type':'application/json',
                    'Content-Length':Buffer.byteLength( new Buffer( JSON.stringify( post_data )) )
                }
            };

            var externalRequest     = http.request( options, function( externalResponse ){
                if( externalResponse.statusCode == 200 ){
                    externalResponse.on( 'data', function( chunk ){
                        console.log( 'get.js     - exRes: ' + JSON.parse( chunk ));
                        res.status(200).send( chunk );
                    });
                }
            });
            externalRequest.write( new Buffer( JSON.stringify( post_data )) );
            externalRequest.end();

            //res.status(200).send( 'OK' );

        });
});

router.post( '/room/cancelreservation', function( req, res ){

        req.on( 'data', function( chunk ){
            var data = JSON.parse( chunk );

            var post_data = {
                "johntitor":{
                    "key":data.key,
                    "user":data.user,
                    "confirmation":true
                }
            };
            //TODO: change in roomDB
            var options             = {
                host: VARIABLES.roomdbaddr,
                port: VARIABLES.roomdbport,
                path: '/room/cancelreservation',
                method: 'POST',
                headers:{
                    'Content-Type':'application/json',
                    'Content-Length':Buffer.byteLength( new Buffer( JSON.stringify( post_data )) )
                }
            };

            var externalRequest     = http.request( options, function( externalResponse ){
                if( externalResponse.statusCode == 200 ){
                    externalResponse.on( 'data', function( chunk ){
                        console.log( 'get.js     - exRes: ' + JSON.parse( chunk ));
                        res.status(200).send( chunk );
                    });
                }
            });
            externalRequest.write( new Buffer( JSON.stringify( post_data )) );
            externalRequest.end();

            //res.status(200).send( 'OK' );

        });
});

router.post( '/room/cancelbooking', function( req, res ){

        req.on( 'data', function( chunk ){
            var data = JSON.parse( chunk );

            var post_data = {
                "johntitor":{
                    "key":data.key,
                    "user":data.user,
                    "confirmation":true
                }
            };
            //TODO: change in roomDB
            var options             = {
                host: VARIABLES.roomdbaddr,
                port: VARIABLES.roomdbport,
                path: '/room/cancelbooking',
                method: 'POST',
                headers:{
                    'Content-Type':'application/json',
                    'Content-Length':Buffer.byteLength( new Buffer( JSON.stringify( post_data )) )
                }
            };

            var externalRequest     = http.request( options, function( externalResponse ){
                if( externalResponse.statusCode == 200 ){
                    externalResponse.on( 'data', function( chunk ){
                        console.log( 'get.js     - exRes: ' + JSON.parse( chunk ));
                        res.status(200).send( chunk );
                    });
                }
            });
            externalRequest.write( new Buffer( JSON.stringify( post_data )) );
            externalRequest.end();

            //res.status(200).send( 'OK' );

        });
});

router.post( '/room/extendbooking', function( req, res ){
    req.on( 'data', function( chunk ){
        var data = JSON.parse( chunk );
        console.log( data );
        //TODO: put-request to roomDB

        var timer_begin = Date.now();
        var timer_end = timer_begin + 1*60*60*1000;
        var post_data = {
            "johntitor":{
                "key":data.key,
                "user":data.user,
                "booking_begin":timer_begin,
                "booking_end":timer_end
            }
        };
        console.log( post_data );
        var options             = {
            host: VARIABLES.roomdbaddr,
            port: VARIABLES.roomdbport,
            path: '/room/extendbooking',
            method: 'POST',
            headers:{
                'Content-Type':'application/json',
                'Content-Length':Buffer.byteLength( new Buffer( JSON.stringify( post_data )) )
            }
        };

        var externalRequest     = http.request( options, function( externalResponse ){
            if( externalResponse.statusCode == 200 ){
                externalResponse.on( 'data', function( chunk ){
                    console.log( 'get.js     - exRes: ' + JSON.parse( chunk ));
                    res.status(200).send( chunk );
                });
            }
        });
        externalRequest.write( new Buffer( JSON.stringify( post_data )) );
        externalRequest.end();

        //res.status(200).send( 'OK' );

    });
});

module.exports              = router;
