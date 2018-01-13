var router                  = express.Router();

router.get( '/', function( req, res ){

});

router.get( '/roomList', function( req, res ){
console.log('GET');
    var list = FUNCTIONS.getList();
list = JSON.stringify(list);
list = JSON.parse(list);
    console.log( 'Liste: ' + list );
    res.status(200).send(list);
res.end();
});

module.exports              = router;
