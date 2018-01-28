// NODE MODULES
global.redis                                = require( 'redis' );
global.bluebird                             = require( 'bluebird' );

bluebird.promisifyAll(redis.RedisClient.prototype);
bluebird.promisifyAll(redis.Multi.prototype);

// VARIABLES
var nodeArray                               = [];
var TEST_DATA                               = require( './test_data.json' );

// FUNCTIONS
/*
 * Erstellt die verkettete Liste
 */
function setup_LinkedList(){
  // LOAD TESTDATA
  saveTestData();
  // CREATE ITEMS FOR TEST DATA
  createItems()
  .then(function( bool ){
    connectItems();
  })
  .catch(function() {
    console.error( "SOMETHING WENT WRONG" );
  });
}
/*
 * Speichert die Testdaten
 */
function saveTestData(){

  set( 'timeReservation', '30000' );
  set( 'timeBooking', '60000');
  for( var i = 0; i < TEST_DATA.length; i++ ){
    setHash( TEST_DATA[ i ].id,
    {
      'data': TEST_DATA[ i ].data,
      'arrayPos': 0,
      "north": TEST_DATA[ i ].north,
      "n_weight": TEST_DATA[ i ].n_weight,
      "east": TEST_DATA[ i ].east,
      "e_weight": TEST_DATA[ i ].e_weight,
      "south": TEST_DATA[ i ].south,
      "s_weight": TEST_DATA[ i ].s_weight,
      "west": TEST_DATA[ i ].west,
      "w_weight": TEST_DATA[ i ].w_weight,
      "up": TEST_DATA[ i ].up,
      "u_weight": TEST_DATA[ i ].u_weight,
      "down": TEST_DATA[ i ].down,
      "d_weight": TEST_DATA[ i ].d_weight
    });
    switch( TEST_DATA[ i ].type ){
      case "Treppe":
        var tmpData = {
          "beacon_id":TEST_DATA[ i ].beacon_id,
          "node_id":TEST_DATA[ i ].id,
          "type":TEST_DATA[ i ].type,
          "building":TEST_DATA[ i ].building,
          "floor":TEST_DATA[ i ].floor
        };
        setHash( TEST_DATA[ i ].data, tmpData );
        break;
      case "Raum":
        var tmpData = {
          "beacon_id":TEST_DATA[ i ].beacon_id,
          "node_id":TEST_DATA[ i ].id,
          "minipc_id":TEST_DATA[ i ].minipc_id,
          "type":TEST_DATA[ i ].type,
          "building":TEST_DATA[ i ].building,
          "floor":TEST_DATA[ i ].floor,
          "status":JSON.stringify( { "type":"Frei", "user":null, "duration":0 }),
          "room":JSON.stringify( TEST_DATA[ i ].room ),
          "content":JSON.stringify( TEST_DATA[ i ].content )
        };
        setHash( TEST_DATA[ i ].data, tmpData );
        /*
        if( TEST_DATA[ i ].room.size != 0 ){
          addToList( 'empty_rooms', TEST_DATA[ i ].data );
        }
        */
        break;
      case "Gebäude":
        var tmpData = {
          "beacon_id":TEST_DATA[ i ].beacon_id,
          "node_id":TEST_DATA[ i ].id,
          "type":TEST_DATA[ i ].type,
          "building":TEST_DATA[ i ].building,
          "floor":TEST_DATA[ i ].floor
        };
        setHash( TEST_DATA[ i ].data, tmpData );
        break;
      case "Aufzug":
        var tmpData = {
          "beacon_id":TEST_DATA[ i ].beacon_id,
          "node_id":TEST_DATA[ i ].id,
          "type":TEST_DATA[ i ].type,
          "building":TEST_DATA[ i ].building,
          "floor":TEST_DATA[ i ].floor
        };
        setHash( TEST_DATA[ i ].data, tmpData );
        break;
    }
  }
  setTimeout( function(){
    setEmptyRooms();
  }, 5*1000 );
}
/*
 * Erstellt die Listen Items
 */
function createItems(){
  return new Promise( function( resolve, reject ){
    keys( 'bn_*' )
    .then( function( res ){
      for( var i = 0; i < res.length; i++ ){
        getHashField( res[ i ], 'data' )
        .then(function( response ){
          MULTILINKEDLIST.createItem( response , (function( item ){
            nodeArray.push( item );
            setHashField( res[ nodeArray.length -1 ], 'arrayPos', (nodeArray.length-1));
            if( nodeArray.length == res.length ){
              resolve( true );
            }
          }).bind({res:res}));
        });
      }
    });
  });
}
/*
 * Improvisierte For-Schleife
 */
function improvisedForLoop( array, index ){
  getHash( array[ index ] )
  .then( function( data ){
    if( data.north != "null" ){
      getHashField( data.north, 'arrayPos' )
      .then(function( arrayPos ){
        nodeArray[ data.arrayPos ].connectAt( 'north', data.n_weight, nodeArray[ arrayPos ] );
      });
    }
    if( data.east != "null" ){
      getHashField( data.east, 'arrayPos' )
      .then(function( arrayPos ){
        nodeArray[ data.arrayPos ].connectAt( 'east', data.e_weight, nodeArray[ arrayPos ] );
      });
    }
    if( data.south != "null" ){
      getHashField( data.south, 'arrayPos' )
      .then(function( arrayPos ){
        nodeArray[ data.arrayPos ].connectAt( 'south', data.s_weight, nodeArray[ arrayPos ] );
      });
    }
    if( data.west != "null" ){
      getHashField( data.west, 'arrayPos' )
      .then(function( arrayPos ){
        nodeArray[ data.arrayPos ].connectAt( 'west', data.w_weight, nodeArray[ arrayPos ] );
      });
    }
    if( data.up != "null" ){
      getHashField( data.up, 'arrayPos' )
      .then(function( arrayPos ){
        nodeArray[ data.arrayPos ].connectAt( 'up', data.u_weight, nodeArray[ arrayPos ] );
      });
    }
    if( data.down != "null" ){
      getHashField( data.down, 'arrayPos' )
      .then(function( arrayPos ){
        nodeArray[ data.arrayPos ].connectAt( 'down', data.d_weight, nodeArray[ arrayPos ] );
      });
    }
  })
  .then( function(){
    if( index < array.length-1 ){
      improvisedForLoop( array, (index + 1 ) );
    }
  });
}
/*
 * Verbindet die Listen Items
 */
function connectItems(){
  return new Promise( function( resolve, reject ){
    keys( 'bn_*' )
    .then( function( res ){
      var index = 0;
      improvisedForLoop( res, index );
    });
  });
}
/*
 * Improvisierte For-Schleife
 */
function forLoop( array, index, resultArray, callback ){
  getHashField( array[ index ], "room" )
  .then( function( result ){
    if( result != null ){
      if( JSON.parse(result).size != ( 0 || "0" || "null" || null )){
        resultArray.push( array[ index ] );
      }
    }
  })
  .then( function(){
    if( index < array.length-1 ){
      forLoop( array, ( index +  1 ), resultArray, callback );
    } else {
      callback( resultArray );
    }
  });
}
/*
 * Sendet die leeren Räume an den Datenbankserver
 */
function setEmptyRooms(){
  del( 'empty_rooms' );
  keys( 'rm_*' )
  .then( function( result ){
    if( result != null ){
      if( result.length != 0 ){
        var resultArray = [];
        forLoop( result, 0, resultArray, function( resultArray ){
          getUsedRooms()
          .then( function( used_rooms ){
            var used = false;
            for( var i = 0; i < resultArray.length; i++ ){
              used = false;
              for( var j = 0; j < used_rooms.length; j++ ){
                if( resultArray[ i ].substring(3) == used_rooms[ j ] ){
                  used = true;
                }
              }
              if( !used ){
                addToList( 'empty_rooms', resultArray[ i ] );
              }
            }
          });
        });
      }
    }
  })
  .then( function(){
    setTimeout(function(){
      var date = new Date().toISOString();
      date = date.split( 'T' );
      date = date[1].split( '.' );
      console.log( "[INFO] " + date[0] + " - UPDATING EMPTY ROOMS" );
      setEmptyRooms();
    }, 10*60*1000);
  });
}
//------------------------------------------//
/*
 * Bezieht die momentan Belegten Räume vom Datenbankserver
 */
function getUsedRooms(){
  return new Promise( function( resolve, reject ){
    var post_data = {
      "data":"veranstaltungen"
    };

    var options                 = {
      host: VAR_WEBSERVER.database.ip,
      port: VAR_WEBSERVER.database.port,
      path: '/getUsedRooms',
      method: 'POST',
      headers:{
        'Content-Type':'application/json',
        'Content-Length':Buffer.byteLength( new Buffer( JSON.stringify( post_data )) )
      }
    };

    var externalRequest         = http.request( options, function( externalResponse ){
        if( externalResponse.statusCode == 200 ){
            externalResponse.on( 'data', function( chunk ){
              resolve( JSON.parse( chunk ).data );
            });
        }
    });
    externalRequest.write( new Buffer( JSON.stringify( post_data )) );
    externalRequest.end();

  });
}
/*
 * Sucht ein bestimmtes Item aus der verketteten Liste
 */
function getNode( index ){
  return new Promise( function( resolve, reject ){
    if( index < nodeArray.length ){
      resolve( nodeArray[ index ] );
    } else {
      reject();
    }
  });
}
/*
 * Sendet eine Anfrage für alle Keys die mit 'key' beginnen an den Datenbankserver
 */
function keys( key ){
  return new Promise( function( resolve, reject ){
    var post_data = {
      "key":key
    };

    var options                 = {
      host: VAR_WEBSERVER.database.ip,
      port: VAR_WEBSERVER.database.port,
      path: '/keys',
      method: 'POST',
      headers:{
        'Content-Type':'application/json',
        'Content-Length':Buffer.byteLength( new Buffer( JSON.stringify( post_data )) )
      }
    };

    var externalRequest         = http.request( options, function( externalResponse ){
        if( externalResponse.statusCode == 200 ){
            externalResponse.on( 'data', function( chunk ){
              resolve( JSON.parse( chunk ));
            });
        }
    });
    externalRequest.write( new Buffer( JSON.stringify( post_data )) );
    externalRequest.end();


  });
}
/*
 * Sendet einen key mit den dazugehörigen Daten (data) an den Datenbankserver
 */
function set( key, data ){
  return new Promise( function( resolve, reject ){
    var post_data = {
      "key":key,
      "data":data
    };

    var options                 = {
                    host: VAR_WEBSERVER.database.ip,
                    port: VAR_WEBSERVER.database.port,
                    path: '/set',
                    method: 'POST',
                    headers:{
                        'Content-Type':'application/json',
                        'Content-Length':Buffer.byteLength( new Buffer( JSON.stringify( post_data )) )
                    }
                };

    var externalRequest         = http.request( options, function( externalResponse ){
        if( externalResponse.statusCode == 200 ){
            externalResponse.on( 'data', function( chunk ){
              resolve( chunk );
            });
        }
    });
    externalRequest.write( new Buffer( JSON.stringify( post_data )) );
    externalRequest.end();
  });
}
/*
 * Ruft die Daten eines bestimmten Keys im Datenbankserver ab
 */
function get( key ){
  return new Promise( function( resolve, reject ){
    var post_data = {
      "key":key
    };

    var options                 = {
                    host: VAR_WEBSERVER.database.ip,
                    port: VAR_WEBSERVER.database.port,
                    path: '/get',
                    method: 'POST',
                    headers:{
                        'Content-Type':'application/json',
                        'Content-Length':Buffer.byteLength( new Buffer( JSON.stringify( post_data )) )
                    }
                };

    var externalRequest         = http.request( options, function( externalResponse ){
        if( externalResponse.statusCode == 200 ){
            externalResponse.on( 'data', function( chunk ){
              resolve( JSON.parse( chunk ).data );
            });
        }
    });
    externalRequest.write( new Buffer( JSON.stringify( post_data )) );
    externalRequest.end();
  });
}
/*
 * Löscht einen Bestimmten Key
 */
function del( key ){
  return new Promise( function( resolve, reject ){
    var post_data = {
      "key":key
    };

    var options                 = {
                    host: VAR_WEBSERVER.database.ip,
                    port: VAR_WEBSERVER.database.port,
                    path: '/del',
                    method: 'POST',
                    headers:{
                        'Content-Type':'application/json',
                        'Content-Length':Buffer.byteLength( new Buffer( JSON.stringify( post_data )) )
                    }
                };

    var externalRequest         = http.request( options, function( externalResponse ){
        if( externalResponse.statusCode == 200 ){
            externalResponse.on( 'data', function( chunk ){
                resolve( true );
            });
        }
    });
    externalRequest.write( new Buffer( JSON.stringify( post_data )) );
    externalRequest.end();
  });
}
/*
 * Sendet einen zu speichernden Hash an den Datenbankserver
 */
function setHash( key, data ){
  return new Promise( function( resolve, reject ){
    var post_data = {
      "key":key,
      "data":data
    };

    var options                 = {
                    host: VAR_WEBSERVER.database.ip,
                    port: VAR_WEBSERVER.database.port,
                    path: '/setHash',
                    method: 'POST',
                    headers:{
                        'Content-Type':'application/json',
                        'Content-Length':Buffer.byteLength( new Buffer( JSON.stringify( post_data )) )
                    }
                };

    var externalRequest         = http.request( options, function( externalResponse ){
        if( externalResponse.statusCode == 200 ){
            externalResponse.on( 'data', function( chunk ){
                resolve( chunk );
            });
        }
    });
    externalRequest.write( new Buffer( JSON.stringify( post_data )) );
    externalRequest.end();
  });
}
/*
 * Sendet ein zu speicherndes Feld eines Hashes an den Datenbankserver
 */
function setHashField( key, field, data ){
  return new Promise( function( resolve, reject ){
    var post_data = {
      "key":key,
      "field":field,
      "data":data
    };

    var options                 = {
                    host: VAR_WEBSERVER.database.ip,
                    port: VAR_WEBSERVER.database.port,
                    path: '/setHashField',
                    method: 'POST',
                    headers:{
                        'Content-Type':'application/json',
                        'Content-Length':Buffer.byteLength( new Buffer( JSON.stringify( post_data )) )
                    }
                };

    var externalRequest         = http.request( options, function( externalResponse ){
        if( externalResponse.statusCode == 200 ){
            externalResponse.on( 'data', function( chunk ){
                resolve( JSON.parse( chunk ));
            });
        }
    });
    externalRequest.write( new Buffer( JSON.stringify( post_data )) );
    externalRequest.end();
  });
}
/*
 * Ruft ein Hash vom Datenbankserver ab
 */
function getHash( key ){
  return new Promise( function( resolve, reject ){
    var post_data = {
      "key":key
    };

    var options                 = {
                    host: VAR_WEBSERVER.database.ip,
                    port: VAR_WEBSERVER.database.port,
                    path: '/getHash',
                    method: 'POST',
                    headers:{
                        'Content-Type':'application/json',
                        'Content-Length':Buffer.byteLength( new Buffer( JSON.stringify( post_data )) )
                    }
                };
    var externalRequest         = http.request( options, function( externalResponse ){
        if( externalResponse.statusCode == 200 ){
            externalResponse.on( 'data', function( chunk ){
                resolve( JSON.parse( chunk ).data );
            });
        }
    });
    externalRequest.write( new Buffer( JSON.stringify( post_data )) );
    externalRequest.end();
  });
}
/*
 * Ruft ein Hash Feld vom Datenbankserver ab
 */
function getHashField( key, field ){
  return new Promise( function( resolve, reject ){
    var post_data = {
      "key":key,
      "field":field
    };

    var options                 = {
                    host: VAR_WEBSERVER.database.ip,
                    port: VAR_WEBSERVER.database.port,
                    path: '/getHashField',
                    method: 'POST',
                    headers:{
                        'Content-Type':'application/json',
                        'Content-Length':Buffer.byteLength( new Buffer( JSON.stringify( post_data )) )
                    }
                };

    var externalRequest         = http.request( options, function( externalResponse ){
        if( externalResponse.statusCode == 200 ){
            externalResponse.on( 'data', function( chunk ){
              resolve( JSON.parse( chunk ).data );
            });
        }
    });
    externalRequest.write( new Buffer( JSON.stringify( post_data )) );
    externalRequest.end();

  });
}
/*
 * Sendet ein in einer Liste zu speicherndes Item an den Datenbankserver
 */
function addToList( key, data ){
  return new Promise( function( resolve, reject ){
    var post_data = {
      "key":key,
      "data":data
    };

    var options                 = {
                    host: VAR_WEBSERVER.database.ip,
                    port: VAR_WEBSERVER.database.port,
                    path: '/addToList',
                    method: 'POST',
                    headers:{
                        'Content-Type':'application/json',
                        'Content-Length':Buffer.byteLength( new Buffer( JSON.stringify( post_data )) )
                    }
                };

    var externalRequest         = http.request( options, function( externalResponse ){
        if( externalResponse.statusCode == 200 ){
            externalResponse.on( 'data', function( chunk ){
                resolve( true );
            });
        }
    });
    externalRequest.write( new Buffer( JSON.stringify( post_data )) );
    externalRequest.end();
  });
}
/*
 * Sendet ein zu löschendes Item and en Datenbankserver
 */
function removeFromList( key, data ){
  return new Promise( function( resolve, reject ){
    var post_data = {
      "key":key,
      "data":data
    };

    var options                 = {
                    host: VAR_WEBSERVER.database.ip,
                    port: VAR_WEBSERVER.database.port,
                    path: '/removeFromList',
                    method: 'POST',
                    headers:{
                        'Content-Type':'application/json',
                        'Content-Length':Buffer.byteLength( new Buffer( JSON.stringify( post_data )) )
                    }
                };

    var externalRequest         = http.request( options, function( externalResponse ){
        if( externalResponse.statusCode == 200 ){
            externalResponse.on( 'data', function( chunk ){
                resolve( chunk );
            });
        }
    });
    externalRequest.write( new Buffer( JSON.stringify( post_data )) );
    externalRequest.end();
  });
}
/*
 * Ruft eine Liste vom Datenbankserver ab
 */
function getList( key ){
  return new Promise( function( resolve, reject ){
    var post_data = {
      "key":key
    };

    var options                 = {
                    host: VAR_WEBSERVER.database.ip,
                    port: VAR_WEBSERVER.database.port,
                    path: '/getList',
                    method: 'POST',
                    headers:{
                        'Content-Type':'application/json',
                        'Content-Length':Buffer.byteLength( new Buffer( JSON.stringify( post_data )) )
                    }
                };

    var externalRequest         = http.request( options, function( externalResponse ){
        if( externalResponse.statusCode == 200 ){
            externalResponse.on( 'data', function( chunk ){
                resolve( JSON.parse( chunk ) );
            });
        }
    });
    externalRequest.write( new Buffer( JSON.stringify( post_data )) );
    externalRequest.end();
  });
}
//------------------------------------------//

// EOF
module.exports                              = {
  setup_LinkedList: function(){
    setup_LinkedList();
  },
  getNode:      function( index ){
    return new Promise( function( resolve, reject ){
      getNode( index )
      .then( function( res ){
        if( res != null ){
          resolve( res );
        } else {
          reject();
        }
      });
    });
  },
  set:          function( key, data ){
    return new Promise( function( resolve, reject ){
      set( key, data )
      .then( function( res ){
        resolve( res );
      });
    });
  },
  get:          function( key ){
    return new Promise( function( resolve, reject ){
      get( key )
      .then( function( res ){
        resolve( res );
      });
    });
  },
  del:          function( key ){
    return new Promise( function( resolve, reject ){
      del( key )
      .then( function( res ){
        resolve( res );
      });
    });
  },
  setHash:      function( key, data ){
    return new Promise( function( resolve, reject ){
      setHash( key, data )
      .then( function( res ){
        resolve( res );
      });
    });
  },
  setHashField: function( key, field, data ){
    return new Promise( function( resolve, reject ){
      setHashField( key, field, data )
      .then( function( res ){
        resolve( res );
      });
    });
  },
  getHash:      function( key ){
    return new Promise( function( resolve, reject ){
      getHash( key )
      .then( function( res ){
        resolve( res );
      });
    });
  },
  getHashField: function( key, field ){
    return new Promise( function( resolve, reject ){
      getHashField( key, field )
      .then( function( res ){
        resolve( res );
      });
    });
  },
  addToList:    function( key, data ){
    return new Promise( function( resolve, reject ){
      addToList( key, data )
      .then( function( res ){
        resolve( res );
      });
    });
  },
  removeFromList: function( key, data ){
    return new Promise( function( resolve, reject ){
      removeFromList( key, data )
      .then( function( res ){
        resolve( res );
      });
    });
  },
  getList:      function( key ){
    return new Promise( function( resolve, reject ){
      getList( key )
      .then( function( res ){
        if( res != null ){
          resolve( res );
        } else {
          reject();
        }
      });
    });
  }
};
