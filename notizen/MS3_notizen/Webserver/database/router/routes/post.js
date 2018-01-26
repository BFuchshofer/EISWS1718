var router                                  = express.Router();

// NODE MODULES
// VARIABLES
// FUNCTIONS

// ROUTES
router.post( '/keys', function( req, res ){
  req.on( 'data', function( chunk ){
    var data = JSON.parse( chunk );
    database.keysAsync( data.key )
    .then( function( result ){
      res.status( 200 ).send( result );
    });
  });
});

router.post( '/set', function( req, res ){
  req.on( 'data', function( chunk ){
    var data = JSON.parse( chunk );
    database.setAsync( data.key, data.data )
    .then( function( result ){
      res.status( 200 ).send( data.data );
    });
  });
} );

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

router.post( '/del', function( req, res ){
  req.on( 'data', function( chunk ) {
    var data = JSON.parse( chunk );

    database.delAsync( data.key )
    .then( function (result ){
      res.sendStatus( 200 );
    });
  });
} );

router.post( '/setHash', function( req, res ){
  req.on( 'data', function( chunk ){
    var data = JSON.parse( chunk );

    database.hmsetAsync( data.key, data.data )
    .then( function( result ){
      res.status( 200 ).send( result );
    });
  });
});

router.post( '/setHashField', function( req, res ){
  req.on( 'data', function( chunk ){
    var data = JSON.parse( chunk );
    database.hsetAsync( data.key, data.field, JSON.stringify( data.data ) )
    .then( function( result ){
      res.status( 200 ).send( data );
    });
  });
}),

router.post( '/getHash', function( req, res ){
  req.on( 'data', function( chunk ){
    var data = JSON.parse( chunk );
    console.log( data );
    database.hgetallAsync( data.key )
    .then( function( result ){
      var responseData = {
        "data":result
      }
      res.status( 200 ).send( responseData );
    });
  });
});

router.post( '/getHashField', function( req, res ){
  req.on( 'data', function( chunk ){
    var data = JSON.parse( chunk );
    database.hgetAsync( data.key, data.field )
    .then( function( result ){
      var responseData = {
        "data": result
      }
      res.status( 200 ).send( responseData );
    });
  });
});

router.post( '/addToList', function( req, res ){
  req.on( 'data', function( chunk ){
    var data = JSON.parse( chunk );

    database.lpushAsync( data.key, data.data )
    .then( function( result ){
      res.sendStatus( 200 );
    });
  });
});

router.post( '/removeFromList', function( req, res ){
  req.on( 'data', function( chunk ){
    var data = JSON.parse( chunk );

    database.lremAsync( data.key, -1, data.data )
    .then( function( result ){
      res.sendStatus( 200 );
    });
  });
});

router.post( '/getList', function( req, res ){
  req.on( 'data', function( chunk ){
    var data = JSON.parse( chunk );

    database.lrangeAsync( data.key, 0, -1 )
    .then( function( result ){
      res.status( 200 ).send( result );
    });
  });
});


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
