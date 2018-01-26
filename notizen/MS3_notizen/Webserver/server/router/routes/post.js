var router                                  = express.Router();

// NODE MODULES
// VARIABLES
var testData = false;
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
          "filter":{
            "person": data.person,
            "roomType": data.roomType,
            "blackboard": data.blackboard,
            "whiteboard": data.whiteboard,
            "beamer": data.beamer,
            "chairTable": data.chairTable
          },
          "token": data.token
        }

        console.log( "" );
        console.log( "GET===============================>");
        console.log( data );
        console.log( "==================================!");
        console.log( "" );

        if( data.beacon != null && data.beacon != "location error" && data.user != null && data.token != null ){
          FUNCTIONS.suggestion( data.beacon, data.filter )
          .then( function( result ){
            if( result != -1 ){
              if( result != "null" ){
                FUNCTIONS.checkUserRoom( "rm_" + result.room.id, data.user )
                .then( function( alreadyUsed ){
                  if( alreadyUsed == -1 ){
                    FUNCTIONS.setUserRoom( "rm_" + result.room.id, data.user, "RESERVE" )
                    .then( function( status ){
                      result.status = status;
                      var responseData                = {
                        "token":data.token,
                        "room_id": result.room.id,
                        "remainingTime": result.status.data.duration,
                        "user":data.user
                      }
                      // RESPONSE wenn alles geklappt hat
                      console.error( '/room - GET - sending "OK ( 200 )"')
                      res.status( 200 ).send( responseData );
                    });
                  } else if( alreadyUsed == true ){
                    // RESPONSE wenn der User den angegebenen Raum bereits reserviert hat
                    console.error( '/room - GET - checkUserRoom - sending "BAD REQUEST ( 400 )"' );
                    res.status( 400 ).send( "This User has already reserved this room" );
                  } else {
                    // RESPONSE wenn der User bereits eine RaumID besitzt,
                    //    diese allerdings nicht mit der gesendeten Übereinstimmt.
                    console.error( '/room - GET - checkUserRoom - sending "UNAUTHORIZED ( 401 )"' );
                    res.status( 401 ).send( 'UNAUTHORIZED' );
                  }
                })
              } else {
                // RESPONSE wenn BeaconID nicht existiert
                console.error( '/room - GET - suggestion - sending "REQUESTED RANGE NOT SATISFIABLE ( 416 )"' );
                res.status( 416 ).send( "REQUESTED RANGE NOT SATISFIABLE" );
              }
            } else {
              console.error( '/room - GET - suggestion - sending "NOT FOUND ( 404 )"');
              res.status( 404 ).send( "NOT FOUND" );
            }
          });
        } else {
          // RESPONSE wenn BeaconID oder UserID nicht gesetzt oder im falle
          //    der BeaconID mit 'location error' gesetzt wurden
          console.error( '/room - GET - sending "BAD REQUEST ( 400 )"' );
          res.status( 400 ).send( "BAD REQUEST" );
        }

        break;
      case "UPDATE":
        data                                    = {
          "user": data.user,
          "beacon": data.beacon,
          "filter":{
            "person": data.person,
            "roomType": data.roomType,
            "blackboard": data.blackboard,
            "whiteboard": data.whiteboard,
            "beamer": data.beamer,
            "chairTable": data.chairTable
          },
          "room_id": data.room_id,
          "token": data.token
        }

        console.log( "" );
        console.log( "UPDATE============================>");
        console.log( data );
        console.log( "==================================!");
        console.log( "" );

        if( data.beacon != null && data.beacon != "location error" && data.user != null && data.token != null ){
          FUNCTIONS.checkUserRoom( "rm_" + data.room_id, data.user )
          .then( function( result ){
            console.log( result );
            if( result == true ){
              FUNCTIONS.unsetUserRoom( "rm_" + data.room_id, data.user )
              .then( function( unset_result ){
                console.log( unset_result );
                if( unset_result != "null" );
                FUNCTIONS.suggestion( data.beacon, data.filter )
                .then( function( result ){
                  console.log( result );
                  if( result != -1 ){
                    if( result != "null" ){
                      FUNCTIONS.checkUserRoom( "rm_" + result.room.id, data.user )
                      .then( function( alreadyUsed ){
                        console.log( alreadyUsed );
                        if( alreadyUsed == -1 ){
                          FUNCTIONS.setUserRoom( "rm_" + result.room.id, data.user, "RESERVE" )
                          .then( function( status ){
                            console.log( status );
                            result.status = status;
                            var responseData                = {
                              "token":data.token,
                              "room_id": result.room.id,
                              "remainingTime": result.status.data.duration,
                              "user":data.user
                            }
                            console.log( responseData );
                            // RESPONSE wenn alles geklappt hat
                            console.error( '/room - UPDATE - sending "OK ( 200 )"')
                            res.status( 200 ).send( responseData );
                          });
                        } else {
                          // RESPONSE wenn der User bereits eine RaumID besitzt,
                          //    diese allerdings nicht mit der gesendeten Übereinstimmt.
                          console.error( '/room - UPDATE - checkUserRoom - sending "UNAUTHORIZED ( 401 )"' );
                          res.status( 401 ).send( 'UNAUTHORIZED' );
                        }
                      });
                    } else {
                      // RESPONSE wenn BeaconID nicht existiert
                      console.error( '/room - UPDATE - suggestion - sending "REQUESTED RANGE NOT SATISFIABLE ( 416 )"' );
                      res.status( 416 ).send( "REQUESTED RANGE NOT SATISFIABLE" );
                    }
                  }
                });
              });
            } else if( result == -1 ){
              console.log( "User besitzt keinen raum")
              res.sendStatus( 404 );
            } else {
              // RESPONSE wenn der vorherige Raum nicht dem User zugewiesen ist
              console.error( '/room - UPDATE - checkUserRoom - sending "UNAUTHORIZED ( 401 )"')
              res.status( 401 ).send( 'UNAUTHORIZED' );
            }
          });
        } else {
          // RESPONSE wenn BeaconID oder UserID nicht gesetzt oder im falle
          //    der BeaconID mit 'location error' gesetzt wurden
          console.error( '/room - UPDATE - sending "BAD REQUEST ( 400 )"' );
          res.status( 400 ).send( "BAD REQUEST" );
        }
        break;
      case "BOOK":

        data                                    = {
          "user": data.user,
          "room_id": data.room_id,
          "token": data.token
        }

        console.log( "" );
        console.log( "BOOK==============================>");
        console.log( data );
        console.log( "==================================!");
        console.log( "" );

        FUNCTIONS.checkUserRoom( "rm_" + data.room_id, data.user )
        .then( function( result ){
          if( result == true ){
            FUNCTIONS.unsetUserRoom( 'rm_' + data.room_id , data.user )
            .then( function( result ){
              if( result != "null" ){
                FUNCTIONS.setUserRoom( 'rm_' + data.room_id, data.user, "BOOK")
                .then( function( status ){
                  var responseData                = {
                    "token":data.token,
                    "room_id": data.room_id,
                    "remainingTime": status.data.duration,
                    "door_key": status.door_key,
                    "user":data.user
                  }

                  // RESPONSE wenn alles geklappt hat
                  console.error( '/room - BOOK - sending "OK ( 200 )"')
                  res.status( 200 ).send( responseData );
                });
              }
            });
          } else if( result == -1 ){
            // RESPONSE wenn der User keinen raum reserviert hat
            console.error( '/room - BOOK - checkUserRoom - sending "BAD REQUEST ( 400 )"' );
            res.status( 400 ).send( "This user has no reserved room to be booked" );
          } else {
            // RESPONSE wenn der User bereits eine RaumID besitzt,
            //    diese allerdings nicht mit der gesendeten Übereinstimmt.
            console.error( '/room - BOOK - checkUserRoom - sending "UNAUTHORIZED ( 401 )"' );
            res.status( 401 ).send( 'UNAUTHORIZED' );
          }
        })
        break;
      case "EXTEND":

        console.log( "" );
        console.log( "EXTEND============================>");
        console.log( data );
        console.log( "==================================!");
        console.log( "" );

        FUNCTIONS.checkUserRoom( "rm_" + data.room_id, data.user )
        .then( function( result ){
          if( result == true ){
            FUNCTIONS.unsetUserRoom( "rm_" + data.room_id, data.user )
            .then( function( result ){
              if( result != "null" ){
                FUNCTIONS.setUserRoom( "rm_" + data.room_id, data.user, "BOOK" )
                .then( function( status ){
                  var responseData              = {
                    "token":data.token,
                    "room_id":data.room_id,
                    "remainingTime": status.data.duration,
                    "user":data.user
                  };
                  // RESPONSE wenn alles geklappt hat
                  console.error( '/room - EXTEND - sending "OK ( 200 )"')
                  res.status( 200 ).send( responseData );
                });
              }
            })
          } else if( result == -1 ){
            // RESPONSE wenn der User keinen raum reserviert hat
            console.error( '/room - EXTEND - checkUserRoom - sending "BAD REQUEST ( 400 )"' );
            res.status( 400 ).send( "This user has no booked room to be extended" );
          } else {
            // RESPONSE wenn der User bereits eine RaumID besitzt,
            //    diese allerdings nicht mit der gesendeten Übereinstimmt.
            console.error( '/room - EXTEND - checkUserRoom - sending "UNAUTHORIZED ( 401 )"' );
            res.status( 401 ).send( 'UNAUTHORIZED' );
          }
        });
        break;
      case "CANCEL":

        console.log( "" );
        console.log( "CANCEL============================>");
        console.log( data );
        console.log( "==================================!");
        console.log( "" );

        FUNCTIONS.checkUserRoom( "rm_" + data.room_id, data.user )
        .then( function( result ){
          if( result == -1 ){
            var responseData = {
              "token":data.token,
              "room_id":"0",
              "remainingTime":"0",
              "user":data.user
            };
            // RESPONSE wenn der User keinen Raum gebucht oder reserviert hat
            console.error( '/room - CANCEL - sending "OK ( 200 )"' );
            res.status( 200 ).send( responseData );
          } else if( result == true ){
            FUNCTIONS.unsetUserRoom( "rm_" + data.room_id, data.user )
            .then( function( status ){
              var responseData                = {
                "token":data.token,
                "room_id": data.room_id,
                "remainingTime": status.duration,
                "user":data.user
              }
              // RESPONSE wenn alles geklappt hat
              console.error( '/room - CANCEL - sending "OK ( 200 )" > Room didnt exist');
              res.status( 200 ).send( responseData );
            });
          } else {
            // RESPONSE wenn der User bereits eine RaumID besitzt,
            //    diese allerdings nicht mit der gesendeten Übereinstimmt.
            console.error( '/room - CANCEL - checkUserRoom - sending "UNAUTHORIZED ( 401 )"' );
            res.status( 401 ).send( 'UNAUTHORIZED' );
          }
        })
        break;
    }
  });
})

router.post( '/miniPcPing', function( req, res ){
  req.on( 'data', function( chunk ){
    var data = JSON.parse( chunk );

    console.log( data );

    var exists = false;
    for( var i = 0; i < minipc_iparray.length; i++){
      if( minipc_iparray[ i ].pc_id == data.room_id ){
        exists = true;
        minipc_iparray[ i ].pc_ip = data.host;
        minipc_iparray[ i ].pc_port = data.port;
        break;
      }
    }
    if( !exists ){
      minipc_iparray.push({
        "pc_id":data.room_id,
        "pc_ip":data.host,
        "pc_port":data.port
      });
    }
    console.log( minipc_iparray );
    //FUNCTION.setMiniPc( minipc_iparray[ minipc_iparray.length -1 ] );
    res.status( 200 ).send( minipc_iparray[ minipc_iparray.length -1 ] );
  });
});

router.post( '/roomList', function( req, res ){
  req.on( 'data', function( chunk ){
    var data = JSON.parse( chunk );

    console.log( data );
    var tmpContent = {
      "TischStuhl":{
        "amount":0,
        "fixed":false
      },
      "Blackboard":{
        "amount":0,
        "fixed":true
      },
      "Whiteboard":{
        "amount":0,
        "fixed":false
      },
      "Beamer":{
        "amount":0,
        "fixed":true
      }
    };
    for( var i = 0; i < data.data.length; i++ ){
      switch( data.data[i].name ){
        case "TischStuhl":
          tmpContent.TischStuhl.amount++;
          break;
        case "Blackboard":
          tmpContent.Blackboard.amount ++;
          break;
        case "Whiteboard":
          tmpContent.Whiteboard.amount++;
          break;
        case "Beamer":
          tmpContent.Beamer.amount++;
          break;
      }
      DATABASE.setHash( 'gegenstand_' + data.data[ i ].id, { "type":data.data[i].name, "room_id":data.room_id});
    }
    console.log( tmpContent );
    DATABASE.setHashField( "rm_" + data.room_id, 'content', tmpContent);

    res.status( 200 ).send( "OK" );
  });
});

router.post( '/databasePing', function( req, res ){
  req.on( 'data', function( chunk ){
    var data = JSON.parse( chunk );
    if( !testData ){
      testData = true;
      console.log( "[INFO] Databaseserver ready on: " + data.addr + ":" + data.port );

      VAR_WEBSERVER.database.ip = data.ip;
      VAR_WEBSERVER.database.addr = data.addr
      VAR_WEBSERVER.database.port = data.port;

      DATABASE.setup_LinkedList();
    }


    res.status( 200 ).send( "OK" );
  });
})

// ERROR HANDLING

// EOF
module.exports                              = router;
