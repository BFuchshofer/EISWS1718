var router                  = express.Router();

router.get( '/', function( req, res ){

});

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
