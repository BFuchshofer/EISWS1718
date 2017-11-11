var router                  = express.Router();

router.get( '/', function( req, res ){

});

/*
router.get( '/room/:key', function( req, res ){
    console.log( req.params.key );
    var options             = {
        host: VARIABLES.roomdbaddr,
        port: VARIABLES.roomdbport,
        path: '/room/' + req.params.key,
        method: 'GET',
        headers:{
            accept:'application/json'
        }
    };

    var externalRequest     = http.request( options, function( externalResponse ){
        if( externalResponse.statusCode == 200 ){
            externalResponse.on( 'data', function( chunk ){
                console.log( 'exRes: ' + chunk );
                res.status(200).send( chunk );
            });
        }
    });
    externalRequest.end();
});*/

/* old suggestion GET now in POST
router.get( '/room/suggestion', function( req, res ){
    var options             = {
        host: VARIABLES.roomdbaddr,
        port: VARIABLES.roomdbport,
        path: '/room/suggestion',
        method: 'GET',
        headers:{
            accept:'application/json'
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
    externalRequest.end();
});
*/
router.get( '/veranstaltung', function( req, res ){
    var options             = {
        host: VARIABLES.eventdbaddr,
        port: VARIABLES.eventdbport,
        path: '/veranstaltung',
        method: 'GET',
        headers:{
            accept:'application/json'
        }
    };

    var externalRequest     = http.request( options, function( externalResponse ){
        if( externalResponse.statusCode == 200 ){
            externalResponse.on( 'data', function( chunk ){
                console.log( 'exRes: ' + chunk );
                res.status(200).send( chunk );
            });
        }
    });
    externalRequest.end();
});

module.exports              = router;
