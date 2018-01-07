var router                  = express.Router();

router.get( '/', function( req, res ){

});

router.get( '/vorschlag', function( req, res ){
    var raum = LinkedListMain.getRoom( 'rm_3103', null );
    console.log( raum );
    res.status(200).send( "RAUM: " + raum.node.id );
});

module.exports              = router;
