var router                  = express.Router();

// INDEX
router.get('/', function(req, res){
    res.status(200).send('INDEX');
res.end();
});

// Liefert eine JSON Liste der aktuellen Rauminhalte
router.get('/roomList', function(req, res){
console.log('GET /roomList');
    var list = FUNCTIONS.getList();
    res.status(200).send(list);
res.end();
});

module.exports              = router;
