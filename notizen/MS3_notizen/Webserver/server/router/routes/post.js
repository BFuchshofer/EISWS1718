var router                                  = express.Router();

// NODE MODULES
// VARIABLES
// FUNCTIONS

// ROUTES
router.post( '/room', function( req, res ){
  req.on( 'data', function( chunk ){
    var data                                = JSON.parse( chunk );
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
          FUNCTIONS.checkUserRoom( "rm_" + result.room.id, data.user )
          .then( function( alreadyUsed ){
            if( !alreadyUsed ){
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
          if( result ){
            FUNCTIONS.unsetUserRoom( "rm_" + data.room_id, data.user )
            .then( function( result ){
              FUNCTIONS.suggestion( data.beacon, data.filter )
              .then( function( result ){
                FUNCTIONS.checkUserRoom( "rm_" + result.room.id, data.user )
                .then( function( alreadyUsed ){
                  if( !alreadyUsed ){
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
          } else {
            res.status( 401 ).send( 'NOT YOUR ROOM' );
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
              var responseData                = {
                "token":data.token,
                "room_id": data.room_id,
                "remainingTime": status.duration,
                "door_key": status.door_key
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
            FUNCTIONS.setUserRoom( "rm_" + data.room_id, data.user, "BOOK" )
            .then( function( status ){
              var responseData              = {
                "token":data.token,
                "room_id":data.room_id,
                "remainingTime": status.duration
              };
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

router.post( '/miniPcPing', function( req, res ){
  req.on( 'data', function( chunk ){
    var data = JSON.parse( chunk );

    minipc_iparray.push({
      "pc_id":data.id,
      "pc_ip":data.ip,
      "pc_addr":data.addr,
      "pc_port":data.port
    });
    res.status( 200 ).send( minipc_iparray[ minipc_iparray.length -1 ] );
  });
});

// ERROR HANDLING

// EOF
module.exports                              = router;
