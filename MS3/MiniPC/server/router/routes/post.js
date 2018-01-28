var router = express.Router();

// Empfängt einen Buchungsauftrag vom Server für diesen Raum
router.post('/booking', function(req, res) {
    req.on('data', function(chunk) {
        var data = JSON.parse(chunk);
	FUNCTIONS.book();
        res.status(200).send(); 
    });
});


module.exports = router;