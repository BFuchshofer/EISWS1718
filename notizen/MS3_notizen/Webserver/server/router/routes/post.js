var router                                  = express.Router();

// NODE MODULES
// VARIABLES
// FUNCTIONS

// ROUTES
router.post( '/room', function( req, res ){
  req.on( 'data', function( chunk ){
    var data                                = JSON.parse( chunk );
    console.log( data );
    switch( data.token ){
      case "GET":
        data                                    = {
          "user": data.user,
          "beacon": data.beacon,
          "filter":[
            {"person": data.person},
            {"roomSize": data.roomSize},
            {"blackboard": data.blackboard},
            {"whiteboard": data.whiteboard},
            {"beamer": data.beamer},
            {"chairTable": data.chairTable}
          ],
          "token": data.token
        }
        FUNCTIONS.suggestion( data.beacon, data.filter )
        .then( function( result ){
          console.log( "RESULT: " + result );
          FUNCTIONS.checkUserRoom( "rm_" + result.room.id, data.user )
          .then( function( alreadyUsed ){
            console.log( !alreadyUsed );
            if( !alreadyUsed ){
              console.log( "WAS FALSE" );
              FUNCTIONS.setUserRoom( "rm_" + result.room.id, data.user, "RESERVE" )
              .then( function( status ){
                result.status = status;
                var responseData                = {
                  "token":data.token,
                  "room_id": result.room.id,
                  "remainingTime": result.status.duration
                }
                res.status( 200 ).send( responseData );
              });
            } else {
              console.error( '/room - GET - checkUserRoom' );
              res.status( 401 ).send( 'ERROR' );
            }
          })
          .catch( function(){
          });
        })
        .catch( function(){
          console.error( "/room - GET - suggestion" );
          res.status( 403 ).send( "ERROR" );
        });
        break;
      case "UPDATE":
        data                                    = {
          "user": data.user,
          "beacon": data.beacon,
          "filter":[
            {"person": data.person},
            {"roomSize": data.roomSize},
            {"blackboard": data.blackboard},
            {"whiteboard": data.whiteboard},
            {"beamer": data.beamer},
            {"chairTable": data.chairTable}
          ],
          "room_id": data.room_id,
          "token": data.token
        }
        FUNCTIONS.checkUserRoom( "rm_" + data.room_id, data.user )
        .then( function( result ){
          if( !result ){
            console.log( "TOTO AFRICA")
            res.status( 401 ).send( 'NOT YOUR ROOM' );
          } else {
            FUNCTIONS.unsetUserRoom( "rm_" + data.room_id, data.user )
            .then( function( result ){
              console.log( result );
              FUNCTIONS.suggestion( data.beacon, data.filter )
              .then( function( result ){
                console.log( "RESULT: " + result );
                FUNCTIONS.checkUserRoom( "rm_" + result.room.id, data.user )
                .then( function( alreadyUsed ){
                  console.log( !alreadyUsed );
                  if( !alreadyUsed ){
                    console.log( "WAS FALSE" );
                    FUNCTIONS.setUserRoom( "rm_" + result.room.id, data.user, "RESERVE" )
                    .then( function( status ){
                      result.status = status;
                      var responseData                = {
                        "token":data.token,
                        "room_id": result.room.id,
                        "remainingTime": result.status.duration
                      }
                      res.status( 200 ).send( responseData );
                    });
                  } else {
                    console.error( '/room - GET - checkUserRoom' );
                    res.status( 401 ).send( 'ERROR' );
                  }
                })
                .catch( function(){
                });
              })
              .catch( function(){
                console.error( "/room - GET - suggestion" );
                res.status( 403 ).send( "ERROR" );
              });
            });
          }
        })
        break;
      case "BOOK":
      data                                    = {
        "user": data.user,
        "beacon": data.beacon,
        "room_id": data.room_id,
        "token": data.token
      }
      FUNCTIONS.checkUserRoom( "rm_" + data.room_id, data.user )
      .then( function( result ){
        if( result ){
          FUNCTIONS.setUserRoom( 'rm_' + data.room_id, data.user, "BOOK")
          .then( function( status ){
            console.log( status );
            var responseData                = {
              "token":data.token,
              "room_id": data.room_id,
              "remainingTime": status.duration
            }
            res.status( 200 ).send( responseData );
          });
        } else {
          res.status( 401 ).send( "NOT YOUR ROOM" );
        }
      })
        break;
      case "EXTEND":
        FUNCTIONS.checkUserRoom( "rm_" + data.room_id, data.user )
        .then( function( result ){
          if( result ){
            setUserRoom( "rm_" + data.room_id, data.user, "BOOK" )
            .then( function( status ){
              var responseData              = {
                "token":data.token,
                "room_id":data.room_id,
                "remainingTime": status.duration
              },
              res.status(200).send( responseData );
            })
          } else {
            res.status( 401 ).send( "NOT YOUR ROOM" );
          }
        });
        break;
      case "CANCEL":
        FUNCTIONS.checkUserRoom( "rm_" + data.room_id, data.user )
        .then( function( result ){
          if( result ){
            FUNCTIONS.unsetUserRoom( "rm_" + data.room_id, data.user )
            .then( function( status ){
              var responseData                = {
                "token":data.token,
                "room_id": data.room_id,
                "remainingTime": status.duration
              }
              res.status( 200 ).send( responseData );
            });
          } else {
            res.status( 401 ).send( "NOT YOUR ROOM" );
          }
        })
        break;
    }
  });
})

// ERROR HANDLING

// EOF
module.exports                              = router;
