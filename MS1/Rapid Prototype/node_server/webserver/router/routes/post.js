var router                      = express.Router();

var suggestion_time             = 30*1000;       // 30 sec * 1000 millisec
var reservation_time            = 15*60*1000;   // 15 min * 60 sec * 1000 millisec
var booking_time                = 1*60*60*1000; // 1 h * 60 min * 60 sec * 1000 millisec


router.post( '/room/suggestion', function( req, res ){
    req.on( 'data', function( chunk ){
        var data                    = JSON.parse( chunk );

        //console.log( data );

        var suggestion_begin        = Date.now();
        var suggestion_end          = suggestion_begin + suggestion_time;
        var post_data = {
            "johntitor":{
                "room_nr":null,
                "user":data.user,
                "suggestion_begin":suggestion_begin,
                "suggestion_end":suggestion_end
            }
        };

        //console.log( post_data );

        ///////////////////////////////////////// GETS FOR ALGORITHM
        suggestion_func.getSuggestion( null, function( result ){
            console.log( result );
            if( result != null ){
                post_data.johntitor.room_nr = result;

                var options                 = {
                    host: VARIABLES.roomdbaddr,
                    port: VARIABLES.roomdbport,
                    path: '/room/suggestion',
                    method: 'POST',
                    headers:{
                        'Content-Type':'application/json',
                        'Content-Length':Buffer.byteLength( new Buffer( JSON.stringify( post_data )) )
                    }
                };

                var externalRequest         = http.request( options, function( externalResponse ){
                    if( externalResponse.statusCode == 200 ){
                        externalResponse.on( 'data', function( chunk ){

                            //console.log( 'get.js     - exRes: ' +  chunk );
                            //suspend_func.suspendSuggestion( post_data.room.room_nr );
                            res.status(200).send( chunk );
                        });
                    }
                });
                externalRequest.write( new Buffer( JSON.stringify( post_data )) );
                externalRequest.end();
            } else {
                res.status( 204 ).send( 'NO ROOM AVAILABLE' );
            }



        });
        ////////////////////////////////////////////////////////////



        //res.status(200).send( 'OK' );
    });

});

router.post( '/room/reservation', function( req, res ){
    req.on( 'data', function( chunk ){
        var data                = JSON.parse( chunk );

        console.log( data );

        var reservation_begin   = Date.now();
        var reservation_end     = reservation_begin + reservation_time;
        var post_data = {
            "johntitor":{
                "room_nr":data.room_nr,
                "user":data.user,
                "reservation_begin":reservation_begin,
                "reservation_end":reservation_end
            }
        };

        //console.log( post_data );

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

                    //console.log( 'get.js     - exRes: ' + JSON.parse( chunk ));

                    res.status(200).send( chunk );
                });
            }
        });
        externalRequest.write( new Buffer( JSON.stringify( post_data )) );
        externalRequest.end();

        //res.status(200).send( 'OK' );

    });
});

router.post( '/room/booking', function( req, res ){

        req.on( 'data', function( chunk ){
            var data = JSON.parse( chunk );

            //console.log( data );

            var timer_begin = Date.now();
            var timer_end = timer_begin + booking_time;
            var post_data = {
                "johntitor":{
                    "room_nr":data.room_nr,
                    "user":data.user,
                    "booking_begin":timer_begin,
                    "booking_end":timer_end
                }
            };

            //console.log( post_data );

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
                console.log( externalResponse.statusCode );
                if( externalResponse.statusCode == 200 ){
                    externalResponse.on( 'data', function( chunk ){

                        //console.log( 'get.js     - exRes: ' + JSON.parse( chunk ));

                        res.status(200).send( chunk );
                    });
                } else if( externalResponse.statusCode == 401 ){
                    console.log( 'hi2' );
                    res.status( 401 ).send( 'UNAUTHORIZED' );
                } else if( externalResponse.statusCode == 404 ){
                    res.status( 404 ).send( 'NOT FOUND' );
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
                    "room_nr":data.room_nr,
                    "user":data.user,
                    "confirmation":true
                }
            };

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

                        //console.log( 'get.js     - exRes: ' + JSON.parse( chunk ));

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
                    "room_nr":data.room_nr,
                    "user":data.user,
                    "confirmation":true
                }
            };

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

                        //console.log( 'get.js     - exRes: ' + JSON.parse( chunk ));

                        res.status(200).send( chunk );
                    });
                } else if( externalResponse.statusCode == 401 ){
                    console.log( 'hi2' );
                    res.status( 401 ).send( 'UNAUTHORIZED' );
                } else if( externalResponse.statusCode == 404 ){
                    res.status( 404 ).send( 'NOT FOUND' );
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

        //console.log( data );

        var timer_begin = Date.now();
        var timer_end = timer_begin + booking_time;
        var post_data = {
            "johntitor":{
                "room_nr":data.room_nr,
                "user":data.user,
                "booking_begin":timer_begin,
                "booking_end":timer_end
            }
        };

        //console.log( post_data );

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

                    //console.log( 'get.js     - exRes: ' + JSON.parse( chunk ));

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
