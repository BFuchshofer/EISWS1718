// NODE MODULES
var sem                                     = require( 'semaphore' )(0);

// VARIABLES

// SHORTESTPATH FUNCTIONS
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

function shortestPath( startNode, arrayWanted ){
    var result                              = null;
    var visited                             = [];
    var queue                               = [];
    queue.push(
        {
            "node":startNode,
            "cost":0
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
                )
            }
        }
        if( currentNode.node.down != null ){
            if( !wasVisited( currentNode.node.down.data, visited ) ) {
                queue.push(
                    {
                        "node": currentNode.node.down,
                        "cost": ( parseInt( currentNode.cost, 10 ) + parseInt( currentNode.node.d_weight, 10 ))
                    }
                )
            }
        }
        queue                                   = sortQueue( queue );
    }
}

// FUNCTIONS
function useFilter( array, rooms ){
  if( array != null ){
    //TODO FOR LOOP GETTING ALL ROOM INFORMATION AND CHECKING FOR THE SET FILTER
    return( rooms );
  } else {
    return rooms;
  }
}

// TODO: Weniger Freie Räume als angefragt verfügbar.
function suggestion( beacon_id, filter ){
  return new Promise( function( resolve, reject ){
    DATABASE.getHash( 'bn_' + beacon_id )
    .then( function( res ){
      DATABASE.getNode( parseInt( res.arrayPos, 10 ) )
      .then( function( result ){
        return result;
      })
      .then( function( res ){
        sem.take( function(){
          DATABASE.getList( 'empty_rooms' )
          .then( function( arrayWanted ){
            var result = shortestPath( res, useFilter( filter, arrayWanted ));
            DATABASE.removeFromList( 'empty_rooms', result );
            sem.leave();
            return result
          })
          .then( function( result ){
            DATABASE.getHash( result )
            .then( function( room ){
              room.room = JSON.parse( room.room );
              room.content = JSON.parse( room.content );
              room.status = JSON.parse( room.status );
              resolve( room );
            })
          });
        });
      });
    })
    .catch( function(){
      console.error( 'COULDNT FIND RESOURCE' );
    });
  });
}

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
        tmp.door_key                        = randomstring.generate(10);
        break;
    }
    DATABASE.get( timeID )
    .then(function( result ){
      var date = new Date().getTime();
      tmp.duration                          = (parseInt( result, 10 ) + date);
      DATABASE.setHashField( room_id, "status", tmp )
      .then( function( res ){
        DATABASE.removeFromList( 'empty_rooms', room_id );
        resolve( JSON.parse( res ) );
      });
    });
  });
}

function unsetUserRoom( room_id, user_id ){
  return new Promise( function( resolve, reject ){
    DATABASE.del( 'ru_' + user_id )
    .then(function( result ){
      var tmp = {
        "type":"Frei",
        "user":null,
        "duration": 0,
        "door_key":null
      };
      DATABASE.setHashField( room_id, "status", tmp )
      .then( function( res ){
        DATABASE.addToList( 'empty_rooms', room_id );
        resolve( tmp );
      });
    });
  });
}

function checkUserRoom( room_id, user_id ){
  return new Promise( function( resolve, reject ){
      DATABASE.get( 'ru_' + user_id )
      .then( function( result ){
        if( result == room_id ){
          resolve( true );
        } else {
          resolve( false );
        }
      });
  });
}

function userAction( action, user_id, room_id ){
  return new Promise( function( resolve, reject ){
    /*
      userActions:
        - Reservierung
        - neue Reservierung
        - Buchung
        - Abbruch
    */
    switch( action ){
      case "GET":
        // save RoomY <=> UserX
        checkUserRoom( room_id, user_id )
        .then( function( result ){
          setUserRoom( room_id, user_id )
          .then( function( res ){
            resolve( res );
          });
        })
        .catch( function(){
          reject( 'USER ALREADY BOOKED A ROOM')
        })
        // save RoomY.status
        break;
      case "UPDATE":
        checkUserRoom( room_id, user_id )
        .then( function( res ){

        })
        .catch( function(){
          reject();
        });
        break;
      case "BOOK":

        break;
      case "CANCEL":

        break;
      }
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
  userAction:  function( action, user_id, room_id ){
    return new Promise( function( resolve, reject ){
      userAction( action, user_id, room_id )
      .then( function( res ){
        resolve( res );
      });
    });
  }
}