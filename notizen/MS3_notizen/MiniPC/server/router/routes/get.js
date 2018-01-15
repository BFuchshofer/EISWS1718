var router                  = express.Router();

router.get('/', function(req, res){

});

router.get('/roomList', function(req, res){
console.log('GET /roomList');
    var list = FUNCTIONS.getList();
    res.status(200).send(list);
res.end();
});

module.exports              = router;
