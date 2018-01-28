var router                                  = express.Router();

// NODE MODULES
// VARIABLES
var testData = false;
// FUNCTIONS

// ROUTES
/* *************************************************************************** *
 * /room:
 *  Über diese Route werden alle durch das Endgerät des Benutzers angeforderten
 *  Aufgaben erfüllt. Dafür wird im Feld token einer der folgenden Werte benötigt:
 *
 *  GET: für diesen Teil der Route werden weiterhin folgende Felder benötigt:
 *    user, beacon, person, roomType, blackboard, whiteboard, beamer, chairTable
 *
 *    In diesem Teil wird für den Benutzer ein neuer Raumvorschlag auf basis der
 *    ausgewählten Filter und des mitgesendeten Beacon zurückgegeben.
 *    Es kann eine der folgenden RESPONSES zurückgegeben werden:
 *   CODE  | Bedeutung                              | Gesendete Daten
 * --------+----------------------------------------+-------------------------------
 *    200  | Aktion erfolgreich                     | token, room_id, remainingTime, user
 *    400  | User hat den Raum bereits              | ---
 *    400  | Ein benötigtes Feld ist nicht gesetzt  | ---
 *    401  | User hat einen anderen Raum            | ---
 *    404  | Es wurde kein passender Raum gefunden  | ---
 *    416  | BeaconID existiert nicht               | ---
 *
 *
 *  UPDATE: für diesen Teil der Route werden weiterhin folgende Felder benötigt:
 *    user, beacon, room_id, person, roomType, blackboard, whiteboard, beamer, chairTable
 *
 *    In diesem Teil wird für den Benutzer ein neuer Raumvorschlag auf basis der
 *    ausgewählten Filter, des mitgesendeten Beacon und des vorherig zugewiesenen
 *    Raumes ausgewählt.
 *    Es kann eine der folgenden RESPONSES zurückgegeben werden:
 *   CODE  | Bedeutung                              | Gesendete Daten
 * --------+----------------------------------------+-------------------------------
 *    200  | Aktion erfolgreich                     | token, room_id, remainingTime, user
 *    401  | User hat hat eine andere RaumID als    | ---
 *         |  als die mitgesendete.                 |
 *    404  | User hat keinen Raum reserviert        | ---
 *    416  | Die BeaconID existiert nicht           | ---
 *
 *
 *  BOOK: für diesen Teil der Route werden weiterhin folgende Felder benötigt:
 *    user, beacon, room_id
 *
 *    In diesem Teil wird ein Raum für einen Benutzer anhand der mitgesendeten Daten gebucht.
 *    Es kann eine der folgenden RESPONSES zurückgegeben werden:
 *   CODE  | Bedeutung                              | Gesendete Daten
 * --------+----------------------------------------+-------------------------------
 *    200  | Aktion erfolgreich                     | token, room_id, remainingTime, user, door_key
 *    400  | User hat keinen Raum Reserviert        | ---
 *    401  | User hat einen anderen Raum reserviert | ---
 *
 *
 *  EXTEND: für diesen Teil der Route werden weiterhin folgende Felder benötigt:
 *    user, beacon, room_id
 *
 *    In diesem Teil wird die Buchung für einen von einem User gebuchten Raum verlängert.
 *    Es kann eine der folgenden RESPONSES zurückgegeben werden:
 *   CODE  | Bedeutung                              | Gesendete Daten
 * --------+----------------------------------------+-------------------------------
 *    200  | Aktion erfolgreich                     | token, room_id, remainingTime, user
 *    400  | User hat keinen Raum Reserviert        | ---
 *    401  | User hat einen anderen Raum reserviert | ---
 *
 *
 *  CANCEL: für diesen Teil der Route werden weiterhin folgende Felder benötigt:
 *    user, room_id
 *
 *    In diesem Teil wird eine Reservierung oder Buchung einese Users aufgehoben
 *    Es kann eine der folgenden RESPONSES zurückgegeben werden:
 *   CODE  | Bedeutung                              | Gesendete Daten
 * --------+----------------------------------------+-------------------------------
 *    200  | Aktion erfolgreich                     | token, room_id, remainingTime, user
 *    200  | User hat keinen Raum Reserviert        | token, room_id, remainingTime, user
 *    401  | User hat einen anderen Raum reserviert | ---
 *
 * *************************************************************************** */
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
        };

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
                    };
                      // RESPONSE wenn alles geklappt hat
                      console.log( '[RES-] /room - GET - sending "OK ( 200 )"');
                      res.status( 200 ).send( responseData );
                    });
                  } else if( alreadyUsed == true ){
                    // RESPONSE wenn der User den angegebenen Raum bereits reserviert hat
                    console.log( '[RES-] /room - GET - checkUserRoom - sending "BAD REQUEST ( 400 )"' );
                    res.status( 400 ).send( "This User has already reserved this room" );
                  } else {
                    // RESPONSE wenn der User bereits eine RaumID besitzt,
                    //    diese allerdings nicht mit der gesendeten Übereinstimmt.
                    console.log( '[RES-] /room - GET - checkUserRoom - sending "UNAUTHORIZED ( 401 )"' );
                    res.status( 401 ).send( 'UNAUTHORIZED' );
                  }
                });
              } else {
                // RESPONSE wenn BeaconID nicht existiert
                console.log( '[RES-] /room - GET - suggestion - sending "REQUESTED RANGE NOT SATISFIABLE ( 416 )"' );
                res.status( 416 ).send( "REQUESTED RANGE NOT SATISFIABLE" );
              }
            } else {
              // RESPONSE wenn kein passender Raum gefunden wurde
              console.log( '[RES-] /room - GET - suggestion - sending "NOT FOUND ( 404 )"');
              res.status( 404 ).send( "NOT FOUND" );
            }
          });
        } else {
          // RESPONSE wenn BeaconID oder UserID nicht gesetzt oder im falle
          //    der BeaconID mit 'location error' gesetzt wurden
          console.log( '[RES-] /room - GET - sending "BAD REQUEST ( 400 )"' );
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
        };

        if( data.beacon != null && data.beacon != "location error" && data.user != null && data.token != null ){
          FUNCTIONS.checkUserRoom( "rm_" + data.room_id, data.user )
          .then( function( result ){
            if( result == true ){
              FUNCTIONS.unsetUserRoom( "rm_" + data.room_id, data.user )
              .then( function( unset_result ){
                if( unset_result != "null" );
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
                            };
                            // RESPONSE wenn alles geklappt hat
                            console.log( '[RES-] /room - UPDATE - sending "OK ( 200 )"');
                            res.status( 200 ).send( responseData );
                          });
                        } else {
                          // RESPONSE wenn der User bereits eine RaumID besitzt,
                          //    diese allerdings nicht mit der gesendeten Übereinstimmt.
                          console.log( '[RES-] /room - UPDATE - checkUserRoom - sending "UNAUTHORIZED ( 401 )"' );
                          res.status( 401 ).send( 'UNAUTHORIZED' );
                        }
                      });
                    } else {
                      // RESPONSE wenn BeaconID nicht existiert
                      console.log( '[RES-] /room - UPDATE - suggestion - sending "REQUESTED RANGE NOT SATISFIABLE ( 416 )"' );
                      res.status( 416 ).send( "REQUESTED RANGE NOT SATISFIABLE" );
                    }
                  }
                });
              });
            } else if( result == -1 ){
              // RESPONSE wenn der User keinen Raum reserviert hat.
              console.log( '[RES-] /room - UPDATE - checkUserRoom - sending "NOT FOUND ( 404 )"' );
              res.sendStatus( 404 );
            } else {
              // RESPONSE wenn der vorherige Raum nicht dem User zugewiesen ist
              console.log( '[RES-] /room - UPDATE - checkUserRoom - sending "UNAUTHORIZED ( 401 )"');
              res.status( 401 ).send( 'UNAUTHORIZED' );
            }
          });
        } else {
          // RESPONSE wenn BeaconID oder UserID nicht gesetzt oder im falle
          //    der BeaconID mit 'location error' gesetzt wurden
          console.log( '[RES-] /room - UPDATE - sending "BAD REQUEST ( 400 )"' );
          res.status( 400 ).send( "BAD REQUEST" );
        }
        break;
      case "BOOK":

        data                                    = {
          "user": data.user,
          "room_id": data.room_id,
          "token": data.token
        };

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
                    };

                  // RESPONSE wenn alles geklappt hat
                  console.log( '[RES-] /room - BOOK - sending "OK ( 200 )"');
                  res.status( 200 ).send( responseData );
                });
              }
            });
          } else if( result == -1 ){
            // RESPONSE wenn der User keinen raum reserviert hat
            console.log( '[RES-] /room - BOOK - checkUserRoom - sending "BAD REQUEST ( 400 )"' );
            res.status( 400 ).send( "This user has no reserved room to be booked" );
          } else {
            // RESPONSE wenn der User bereits eine RaumID besitzt,
            //    diese allerdings nicht mit der gesendeten Übereinstimmt.
            console.log( '[RES-] /room - BOOK - checkUserRoom - sending "UNAUTHORIZED ( 401 )"' );
            res.status( 401 ).send( 'UNAUTHORIZED' );
          }
        });
        break;
      case "EXTEND":

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
                  console.log( '[RES-] /room - EXTEND - sending "OK ( 200 )"');
                  res.status( 200 ).send( responseData );
                });
              }
            });
          } else if( result == -1 ){
            // RESPONSE wenn der User keinen raum reserviert hat
            console.log( '[RES-] /room - EXTEND - checkUserRoom - sending "BAD REQUEST ( 400 )"' );
            res.status( 400 ).send( "This user has no booked room to be extended" );
          } else {
            // RESPONSE wenn der User bereits eine RaumID besitzt,
            //    diese allerdings nicht mit der gesendeten Übereinstimmt.
            console.log( '[RES-] /room - EXTEND - checkUserRoom - sending "UNAUTHORIZED ( 401 )"' );
            res.status( 401 ).send( 'UNAUTHORIZED' );
          }
        });
        break;
      case "CANCEL":

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
            console.log( '[RES-] /room - CANCEL - sending "OK ( 200 )"' );
            res.status( 200 ).send( responseData );
          } else if( result == true ){
            FUNCTIONS.unsetUserRoom( "rm_" + data.room_id, data.user )
            .then( function( status ){
              var responseData                = {
                "token":data.token,
                "room_id": data.room_id,
                "remainingTime": status.duration,
                "user":data.user
              };
              // RESPONSE wenn alles geklappt hat
              console.log( '[RES-] /room - CANCEL - sending "OK ( 200 )" > Room didnt exist');
              res.status( 200 ).send( responseData );
            });
          } else {
            // RESPONSE wenn der User bereits eine RaumID besitzt,
            //    diese allerdings nicht mit der gesendeten Übereinstimmt.
            console.log( '[RES-] /room - CANCEL - checkUserRoom - sending "UNAUTHORIZED ( 401 )"' );
            res.status( 401 ).send( 'UNAUTHORIZED' );
          }
        });
        break;
    }
  });
});

/* *************************************************************************** *
 * /miniPcPing
 *  Über diese Route empfängt der Webserver die IP-Adresse und den Port eines
 *  MiniPCs.
 *
 *  Die benötigten Felder sind host, room_id und port.
 *  Es wird eine RESPONSE mit 200 zurückgesendet
 * *************************************************************************** */
router.post( '/miniPcPing', function( req, res ){
  req.on( 'data', function( chunk ){
    var data = JSON.parse( chunk );

    console.log( "[INFO] MiniPC Room: " + data.room_id + " ready on: http://" + data.host + ":" + data.port );

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
    FUNCTIONS.setMiniPC( minipc_iparray[ minipc_iparray.length-1]);
    res.status( 200 ).send( minipc_iparray[ minipc_iparray.length -1 ] );
  });
});

/* *************************************************************************** *
 * /roomList
 *  Über diese Route empfängt der Webserver die aktualisierte Liste der Rauminhalte
 *  von einem MiniPC.
 *
 *  Die benötigten Felder sind room_id und data. data enthält ein Array mit allen
 *  Rauminhalten.
 *  Es wird eine RESPONSE mit 200 zurückgesendet.
 * *************************************************************************** */
router.post( '/roomList', function( req, res ){
  req.on( 'data', function( chunk ){
    var data = JSON.parse( chunk );

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
    DATABASE.setHashField( "rm_" + data.room_id, 'content', tmpContent);

    console.log( '[RES-] /roomList - sending "OK ( 200 )"' );
    res.status( 200 ).send( "OK" );
  });
});

/* *************************************************************************** *
 * /databasePing
 *  Über diese Route sendet der Datenbankserver seine IP-Adresse und Port an
 *  den Webserver.
 *
 *  Die benötigten Felder sind ip, addr und port.
 *  Es wird eine RESPONSE mit 200 zurückgegeben
 * *************************************************************************** */
router.post( '/databasePing', function( req, res ){
  req.on( 'data', function( chunk ){
    var data = JSON.parse( chunk );
    if( !testData ){
      testData = true;
      console.log( "[INFO] Databaseserver ready on: " + data.addr + ":" + data.port );

      VAR_WEBSERVER.database.ip = data.ip;
      VAR_WEBSERVER.database.addr = data.addr;
      VAR_WEBSERVER.database.port = data.port;

      DATABASE.setup_LinkedList();
    }


    res.status( 200 ).send( "OK" );
  });
});

// ERROR HANDLING

// EOF
module.exports                              = router;
