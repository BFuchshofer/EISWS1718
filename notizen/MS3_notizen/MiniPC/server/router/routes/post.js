var router                  = express.Router();


router.post( '/room', function( req, res ){
    req.on( 'data', function( chunk ){
        var data                = JSON.parse( chunk );
        
        if (data.token != null) {
            console.log( "Update Room" );
            res.status(200).send( data ); 
        } else {
            var newData = {
            "data":{
                "whiteboard":data.whiteboard,
                "person":data.person,
                "beacon":data.beacon,
                "beamer":data.beamer,
                "blackboard":data.blackboard,
                "roomSize":data.roomSize,
                "token": 1234567890
            }
            };
    
            console.log( "Get New Room" );
            res.status(200).send( JSON.stringify( newData ) ); 
        }
    
    });
});

// Sendet eine Liste (JSON) mit den aktuellen Rauminhalten an den Server
router.post('/list', function(req, res){

    console.log('POST /list');

    var options = {
        host: 'localhost',
        port: 3000,
        path: '/anime',
        method:'GET',
        headers:{
            accept:'application/json'
        }
    }

    var exReq = http.request(options, function(exRes){
        if(exRes.statusCode == 200){
            exRes.on('data', function(chunk){
                var animeList = JSON.parse(chunk);
                //Zusätzlich zur Animeliste wird die Vorschlagliste gerendert.
                res.render('pages/anime',{animeList:animeList,suggestions:suggestions});
                res.end();
            });

            console.log('OK');

        //Falls keine Animes vorhanden sind wird eine leere Seite ausgegeben.
        } else {
            exRes.on('data', function(chunk){
                var animeList = [];
				res.render('pages/anime',{animeList:animeList,suggestions:suggestions});
				res.end();
            });

            console.log('OK - no content');
        }
    });
    exReq.end();
});




module.exports              = router;