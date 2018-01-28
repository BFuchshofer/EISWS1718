var router                                  = express.Router();

// NODE MODULES
// VARIABLES
// FUNCTIONS

// ROUTES
/*
 * /keys gibt das Ergebnis des RedisDB Befehls 'keys' zurück
 */
router.post( '/keys', function( req, res ){
  req.on( 'data', function( chunk ){
    var data = JSON.parse( chunk );
    database.keysAsync( data.key )
    .then( function( result ){
      res.status( 200 ).send( result );
    });
  });
});

/*
 * /set setzt ein neues Key-Value-Paar.
 */
router.post( '/set', function( req, res ){
  req.on( 'data', function( chunk ){
    var data = JSON.parse( chunk );
    database.setAsync( data.key, data.data )
    .then( function( result ){
      res.status( 200 ).send( data.data );
    });
  });
} );

/*
 * /get ruft ein Key-Value-Paar ab.
 */
router.post( '/get', function( req, res ){
  req.on( 'data', function( chunk ){
    var data = JSON.parse( chunk );
    database.getAsync( data.key )
    .then( function( result ){
      var responseData = {
        "data":result
      };
      res.status( 200 ).send( responseData );
    });
  });
} );

/*
 * /del löscht ein Key-Value-Paar
 */
router.post( '/del', function( req, res ){
  req.on( 'data', function( chunk ) {
    var data = JSON.parse( chunk );

    database.delAsync( data.key )
    .then( function (result ){
      res.sendStatus( 200 );
    });
  });
} );

/*
 * /setHash setzt einen neuen Hash in der Datenbank
 */
router.post( '/setHash', function( req, res ){
  req.on( 'data', function( chunk ){
    var data = JSON.parse( chunk );

    database.hmsetAsync( data.key, data.data )
    .then( function( result ){
      res.status( 200 ).send( result );
    });
  });
});

/*
 * /setHashField setzt ein Hash Field in der Datenbank
 */
router.post( '/setHashField', function( req, res ){
  req.on( 'data', function( chunk ){
    var data = JSON.parse( chunk );
    database.hsetAsync( data.key, data.field, JSON.stringify( data.data ) )
    .then( function( result ){
      res.status( 200 ).send( data );
    });
  });
}),

/*
 * /getHash ruft ein Hash aus der Datenbank ab
 */
router.post( '/getHash', function( req, res ){
  req.on( 'data', function( chunk ){
    var data = JSON.parse( chunk );
    database.hgetallAsync( data.key )
    .then( function( result ){
      var responseData = {
        "data":result
      };
      res.status( 200 ).send( responseData );
    });
  });
});

/*
 * /getHashField ruft ein Hash Field ab
 */
router.post( '/getHashField', function( req, res ){
  req.on( 'data', function( chunk ){
    var data = JSON.parse( chunk );
    database.hgetAsync( data.key, data.field )
    .then( function( result ){
      var responseData = {
        "data": result
      };
      res.status( 200 ).send( responseData );
    });
  });
});

/*
 * /addToList fügt ein Element einer Liste hinzu
 */
router.post( '/addToList', function( req, res ){
  req.on( 'data', function( chunk ){
    var data = JSON.parse( chunk );

    database.lpushAsync( data.key, data.data )
    .then( function( result ){
      res.sendStatus( 200 );
    });
  });
});

/*
 * /removeFromList entfernt ein Element von einer Liste
 */
router.post( '/removeFromList', function( req, res ){
  req.on( 'data', function( chunk ){
    var data = JSON.parse( chunk );

    database.lremAsync( data.key, -1, data.data )
    .then( function( result ){
      res.sendStatus( 200 );
    });
  });
});

/*
 * /getList ruft eine komplette Liste ab
 */
router.post( '/getList', function( req, res ){
  req.on( 'data', function( chunk ){
    var data = JSON.parse( chunk );

    database.lrangeAsync( data.key, 0, -1 )
    .then( function( result ){
      res.status( 200 ).send( result );
    });
  });
});

/*
 * /getUsedRooms gibt eine Liste mit den momentan genutzten Räumen zurück
 */
router.post( '/getUsedRooms', function( req, res ){
  req.on( 'data', function( chunk ){
    var data = JSON.parse( chunk );

    FUNCTIONS.getUsedRooms()
    .then( function( result ){
      var responseData = {
        "data":result
      };
      res.status( 200 ).send( responseData );
    });
  });
});


// ERROR HANDLING

// EOF
module.exports                              = router;
