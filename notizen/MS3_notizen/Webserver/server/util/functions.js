// NODE MODULES
var sem                                     = require( 'semaphore' )(0);

// VARIABLES
var timeouts = [];

// SHORTESTPATH FUNCTIONS

/*
 * sortQueue:
 * Sortiert die Warteschlange sodass das Item mit den geringsten Kosten als erstes
 * steht.
 *
 * Parameter: Das zu sortierende Array ( array )
 * Return   : Das sortierte Array
 */
function sortQueue( array ){
    var tmpArray                            = [];
    var otherArray                          = array.length;

    while( otherArray > 0 )
    {

        var lowestIndex;
        var comp_Cost                       = null;

        for( var i = 0; i < array.length; i++ )
        {

            if (array[i] != -1) {

                if (array[i].cost >= comp_Cost || comp_Cost == null) {
                    comp_Cost               = array[i].cost;
                    lowestIndex             = i;
                }

            }

        }
        tmpArray.push( array[ lowestIndex ] );
        array[ lowestIndex ]                = -1;
        otherArray--;
    }
    array                                   = tmpArray;
    return array;
}

/*
 * wasVisited:
 * überprüft ob ein Item bereits in einem Array ist

 * Parameter: Item (id), Array (array)
 * Return: Item existiert (TRUE) Item existiert nicht (FALSE)
 */
function wasVisited( id, array ){
    for( var i = 0; i < array.length; i++ )
    {
        if( id == array[ i ] )
        {
            return true;
        }
    }
    return false;
}

/*
 * shortestPath:
 * gibt dem zum Startpunkt am kürzesten entfernten Zielpunkt in einem gerichteten
 * Graphen.
 *
 * Parameter: Startpunkt (startNode), Zielpunkt/e (arrayWanted)
 * Return: kein Zielpunkt gefunden (-1), Zielpunkt gefunden (Zielpunkt)
 */
function shortestPath( startNode, arrayWanted ){
    var result                              = null;
    var visited                             = [];
    var queue                               = [];

    queue.push(
        {
            "node":startNode,
            "cost":0.0
        }
    );

    while( queue.length != 0 )
    {
        var currentNode                     = queue.pop();

        for( var i = 0; i < arrayWanted.length; i++ )
        {
            if( arrayWanted[ i ] == currentNode.node.data )
            {
              return currentNode.node.data;
            }
        }
        visited.push( currentNode.node.data );

        if( currentNode.node.north != null ){
            if( !wasVisited( currentNode.node.north.data, visited ) ) {
                queue.push(
                    {
                        "node": currentNode.node.north,
                        "cost": ( parseInt( currentNode.cost, 10 ) + parseInt( currentNode.node.n_weight, 10 ))
                    }
                );
            }
        }
        if( currentNode.node.east != null ){
            if( !wasVisited( currentNode.node.east.data, visited ) ) {
                queue.push(
                    {
                        "node": currentNode.node.east,
                        "cost": ( parseInt( currentNode.cost, 10 ) + parseInt( currentNode.node.e_weight, 10 ))
                    }
                );
            }
        }
        if( currentNode.node.south != null ){
            if( !wasVisited( currentNode.node.south.data, visited ) ) {
                queue.push(
                    {
                        "node": currentNode.node.south,
                        "cost": ( parseInt( currentNode.cost, 10 ) + parseInt( currentNode.node.s_weight, 10 ))
                    }
                );
            }
        }
        if( currentNode.node.west != null ){
            if( !wasVisited( currentNode.node.west.data, visited ) ) {
                queue.push(
                    {
                        "node": currentNode.node.west,
                        "cost": ( parseInt( currentNode.cost, 10 ) + parseInt( currentNode.node.w_weight, 10 ))
                    }
                );
            }
        }
        if( currentNode.node.up != null ){
            if( !wasVisited( currentNode.node.up.data, visited ) ) {
                queue.push(
                    {
                        "node": currentNode.node.up,
                        "cost": ( parseInt( currentNode.cost, 10 ) + parseInt( currentNode.node.u_weight, 10 ))
                    }
                );
            }
        }
        if( currentNode.node.down != null ){
            if( !wasVisited( currentNode.node.down.data, visited ) ) {
                queue.push(
                    {
                        "node": currentNode.node.down,
                        "cost": ( parseInt( currentNode.cost, 10 ) + parseInt( currentNode.node.d_weight, 10 ))
                    }
                );
            }
        }
        queue                                   = sortQueue( queue );
    }
    return -1;
}

// FUNCTIONS
/*
 * filterForLoop:
 * Improvisierte For-Schleife um die in einem REQUEST mitgesendeten Filtern zu
 * überprüfen. Diese Funktion ruft sich rekursiv auf
 *
 * Parameter: Aktueller Durchlauf (index), Filter (filter), Liste mit Räumen (rooms),
 *      Ergebnis (resultArray), Callback (callback)
 * Return: Array mit Räumen auf die die Filter zutreffen (resultarray);
 *
 */
function filterForLoop( index, filter, rooms, resultArray, callback ){
  DATABASE.getHash( rooms[ index ] )
  .then( function( result ){
    if( filter.chairTable == "true" ){
      if( parseInt( filter.person, 10 ) <= parseInt( JSON.parse( result.room ).size, 10 ) &&
          filter.roomType == JSON.parse( result.room ).type &&
          parseInt( filter.blackboard, 10 ) <= parseInt( JSON.parse(result.content).Blackboard.amount, 10 ) &&
          parseInt( filter.whiteboard, 10 ) <= parseInt( JSON.parse(result.content).Whiteboard.amount, 10 ) &&
          parseInt( filter.beamer, 10 ) <= parseInt( JSON.parse(result.content).Beamer.amount, 10 ) ){


        resultArray.push( rooms[ index ] );
      }
    } else {
      if( filter.roomType == JSON.parse( result.room ).type &&
          parseInt( filter.blackboard, 10 ) <= parseInt( JSON.parse(result.content).Blackboard.amount, 10 ) &&
          parseInt( filter.whiteboard, 10 ) <= parseInt( JSON.parse(result.content).Whiteboard.amount, 10 ) &&
          parseInt( filter.beamer, 10 ) <= parseInt( JSON.parse(result.content).Beamer.amount, 10 ) ){


        resultArray.push( rooms[ index ] );
      }
    }
  })
  .then( function(){
    if( index < rooms.length-1 ){
      filterForLoop( (index + 1), filter, rooms, resultArray, callback );
    } else {
      callback( resultArray );
    }
  });
}

/*
 * useFilter:
 * Überprüft ein Array mit Räumen auf die im REQUEST mitgelieferten Filter
 *
 * Parameter: Filter (filter), Räume (rooms)
 * Return: Gefilterte Räume (filtered), Alle Räume falls keine Filter (rooms)
 */
function useFilter( filter, rooms ){
  return new Promise( function( resolve, reject ){
    var filteredRooms = [];
    if( filter != null ){
      filterForLoop( 0, filter, rooms, filteredRooms , function( filtered ){
        resolve( filtered );
      });
    } else {
      resolve( rooms );
    }
  });
}

/*
 * suggestion:
 * Gibt anhand einer BeaconID und der Filter einen Raum zurück.
 *
 * Parameter: BeaconID (beacon_id), Filter (filter)
 * Return: keinen passenden Raum gefunden (-1), Raum gefunden (room),
 *    BeaconID existiert nicht ("null")
 */
function suggestion( beacon_id, filter ){
  return new Promise( function( resolve, reject ){
    DATABASE.getHash( 'bn_' + beacon_id )
    .then( function( res ){
      if( res != null ){
        DATABASE.getNode( parseInt( res.arrayPos, 10 ) )
        .then( function( node ){
          return node;
        })
        .then( function( res ){
          sem.take( function(){
            DATABASE.getList( 'empty_rooms' )
            .then( function( arrayWanted ){
              useFilter( filter, arrayWanted )
              .then( function( filteredRooms ){
                var result = shortestPath( res, filteredRooms );
                if( result != -1 ){
                  DATABASE.removeFromList( 'empty_rooms', result );
                }
                sem.leave();
                return result;
              })
              .then( function( result ){
                if( result != -1 ){
                  DATABASE.getHash( result )
                  .then( function( room ){
                    room.room = JSON.parse( room.room );
                    room.content = JSON.parse( room.content );
                    room.status = JSON.parse( room.status );
                    resolve( room );
                  });
                } else {
                  resolve( -1 );
                }
              });
            });
          });
        });
      } else {
        resolve( "null" );
      }
    })
    .catch( function(){
      console.error( 'COULDNT FIND RESOURCE' );
      reject();
    });
  });
}

/*
 * sendMiniPC:
 * sendet einen Request an einen MiniPC
 *
 * Parameter: RaumID (room_id), Türschlüssel (door_key)
 * Return: ---
 */
function sendMiniPC( room_id ){
  DATABASE.getHashField( "rm_" + room_id, 'minipc_id' )
  .then( function( result ){
    DATABASE.getHash( result )
    .then( function( data ){
      if( data != null ){
        var post_data = {
          "token":"OPEN"
        };

        var options                 = {
          host: data.pc_ip,
          port: data.pc_port,
          path: '/booking',
          method: 'POST',
          headers:{
              'Content-Type':'application/json',
              'Content-Length':Buffer.byteLength( new Buffer( JSON.stringify( post_data )) )
          }
        };
        var externalRequest         = http.request( options );
        externalRequest.on( 'error', function( res ){
          console.log( "[ERR-] Could't reach MiniPC" );
        });
        externalRequest.write( new Buffer( JSON.stringify( post_data )) );
        externalRequest.end();
      } else {
        console.log( '[INFO] MiniPC for room: ' + room_id + ' does not exist!' );
      }
    });


  });
}

/*
 * setMiniPc:
 * Sendet einen MiniPC an den Datenbankserver
 *
 * Parameter: Daten (data)
 * Return: ReturnValue des Datenbankservers (result)
 */
function setMiniPc( data ){
  return new Promise( function( resolve, reject ){
    DATABASE.setHash( 'mini_' + data.pc_id, {
      "pc_ip":data.pc_ip,
      "pc_addr":data.pc_addr,
      "pc_port":data.pc_port
    })
    .then( function( result ){
      resolve( result );
    });
  });
}

/*
 * setUserRoom:
 * Sendet eine Reservierung oder Buchung an den Datenbankserver
 *
 * Parameter: RaumID (room_id), UserID (user_id), Token (token)
 * Return: Staus des Raumes (res)
 */
function setUserRoom( room_id, user_id, token ){
  return new Promise( function( resolve, reject ){
    DATABASE.set( 'ru_' + user_id, room_id );
    var timeID;
    var tmp = {
      "type":"",
      "user":user_id,
      "duration": 0,
      "door_key":null
    };
    switch( token ){
      case "RESERVE":
        timeID                              = "timeReservation";
        tmp.type                            = "Reserviert";
        break;
      case "BOOK":
        timeID                              = "timeBooking";
        tmp.type                            = "Gebucht";
        break;
    }
    DATABASE.get( timeID )
    .then(function( result ){
      var date = new Date().getTime();
      tmp.duration                          = (parseInt( result, 10 ) + date);

      var exists = false;
      for( var i = 0; i < timeouts.length; i++ ){
        if( timeouts[ i ].id == room_id ){
          exists = true;
          clearTimeout( timeouts[ i ].timer );
          console.log( "[INFO] Resetting Timeout for: " + room_id + " - " + user_id );
          timeouts[ i ].timer = setTimeout( function(){
            unsetUserRoom( room_id, user_id );
          }, result);
          break;
        }
      }
      if( !exists ){
        console.log( "[INFO] Set Timeout for: " + room_id + " - " + user_id );
        timeouts.push({
          "timer":setTimeout( function(){
            unsetUserRoom( room_id, user_id );
          }, result ),
          "id":room_id
        });
      }

      DATABASE.setHashField( room_id, "status", tmp )
      .then( function( res ){
        sem.take( function(){
          DATABASE.removeFromList( 'empty_rooms', room_id );
          sem.leave();
          resolve( res );
        });
      });
    });
  });
}

/*
 * unsetUserRoom:
 * Entfernt eine Reservierung oder Buchung vom Datenbankserver
 *
 * Parameter: RaumID (room_id), UserID (user_id)
 * Return: Status des Raumes (tmp)
 */
function unsetUserRoom( room_id, user_id ){
  return new Promise( function( resolve, reject ){
    DATABASE.del( 'ru_' + user_id )
    .then(function( result ){
      var tmpTimeouts = [];
      for( var i = 0; i < timeouts.length; i++ ){
        if( timeouts[ i ].id == room_id ){
          console.log( "[INFO] Timeout Canceled for: " + room_id + " - " + user_id );
          clearTimeout( timeouts[ i ].timer );
        } else {
          tmpTimeouts.push( timeouts[ i ] );
        }
      }
      timeouts = tmpTimeouts;
      var tmp = {
        "type":"Frei",
        "user":null,
        "duration": 0,
        "door_key":null
      };
      DATABASE.setHashField( room_id, "status", tmp )
      .then( function( res ){
        sem.take( function(){
          DATABASE.addToList( 'empty_rooms', room_id );
          sem.leave();
          resolve( tmp );
        });
      });
    });
  });
}

/*
 * checkUserRoom:
 * Überprüft ob für einen User bereits eine Reservierung oder Buchung existiert
 *
 * Parameter: RoomID (room_id), UserID (user_id)
 * Return: User besitzt keinen Raum (-1), User besitzt den gesendeten Raum (true),
 *    User besitzt den gesendeten Raum nicht (false)
 */
function checkUserRoom( room_id, user_id ){
  return new Promise( function( resolve, reject ){
      DATABASE.get( 'ru_' + user_id )
      .then( function( result ){
        if( result == null ){
          resolve( -1 );                // User besitzt keinen Raum
        } else if( result == room_id ){
          resolve( true );              // User besitzt den gesendeten Raum
        } else {
          resolve( false );             // User besitzt den gesendeten Raum NICHT
        }
      });
  });
}

// ERROR HANDLING

// EOF
module.exports                              = {
  suggestion: function( beacon_id, filter ){
    return new Promise( function( resolve, reject ){
      suggestion( beacon_id, filter )
      .then( function( res ){
        resolve( res );
      });
    });
  },
  checkUserRoom: function( room_id, user_id ){
    return new Promise( function( resolve, reject ){
      checkUserRoom( room_id, user_id )
      .then( function( res ){
        resolve( res );
      });
    });
  },
  setUserRoom: function( room_id, user_id, token ){
    return new Promise( function( resolve, reject ){
      setUserRoom( room_id, user_id, token )
      .then( function( res ){
        resolve( res );
      });
    });
  },
  unsetUserRoom: function( room_id, user_id ){
    return new Promise( function( resolve, reject ){
      unsetUserRoom( room_id, user_id )
      .then( function( res ){
        resolve( res );
      });
    });
  },
  setMiniPc:    function( data ){
    return new Promise( function( resolve, reject ){
      setMiniPc( data )
      .then( function( res ){
        resolve( res );
      });
    });
  },
  sendMiniPC:   function( room_id ){
    sendMiniPC( room_id );
  }
};
