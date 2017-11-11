var router                  = express.Router();



router.get( '/', function( req, res ){

});
router.get( '/veranstaltung', function( req, res ){
    console.log( 'GET VERANSTALTUNG' );
    var key = 'veranstaltung_ma1_ko_0401';
    DB_FUNCTIONS.getVeranstaltung( key, function( err, result ){
        if( err ) console.error( err );
        console.log( 'get.js - /veranstaltung: ' + JSON.stringify( result ));
        res.status(200).send( result );
    });
});
router.get( '/veranstaltung/:day', function( req, res ){
    DB_FUNCTIONS.getVeranstaltungenForDay( req.params.day, function( result ){
        res.status(200).send( result );
    });
});

module.exports              = router;
