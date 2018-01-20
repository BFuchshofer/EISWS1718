// NODE MODULES
global.redis                                = require( 'redis' );
global.bluebird                             = require( 'bluebird' );

bluebird.promisifyAll(redis.RedisClient.prototype);
bluebird.promisifyAll(redis.Multi.prototype);

// VARIABLES
var nodeArray                               = [];

// FUNCTIONS
function setup_LinkedList(){
  // LOAD TESTDATA
  saveTestData();
  // CREATE ITEMS FOR TEST DATA
  createItems()
  .then(function( bool ){
    connectItems();
  })
  .catch(function() {
    console.error( "MIST" );
  })
  // CONNECT ITEMS
  //connectItems();
}
function saveTestData(){
  var test_data                             = [];
  database.del( 'empty_rooms' );
  test_data.push({
      "id":"bn_2100",
      "data":"rm_2100",
      "north":"bn_2103",
      "n_weight":1,
      "east":"null",
      "e_weight":0,
      "south":"bn_2104",
      "s_weight":3,
      "west":"null",
      "w_weight":0,
      "up":"null",
      "u_weight":0,
      "down":"null",
      "d_weight":0,

      "beacon_id":"bn_2100",
      "type":"Raum",
      "building":"1",
      "floor":"2",
      "room":{
        "id":"2100",
        "type":"Seminar",
        "size":"50"
      },
      "content":{
        "table":{
          "amount":"50",
          "fixed":true
        }
      }
  });         // 0 > 2.100
  test_data.push({
      "id":"bn_2101",
      "data":"rm_2101",
      "north":"null",
      "n_weight":0,
      "east":"null",
      "e_weight":0,
      "south":"bn_t201",
      "s_weight":1,
      "west":"null",
      "w_weight":0,
      "up":"null",
      "u_weight":0,
      "down":"null",
      "d_weight":0,

      "beacon_id":"bn_2101",
      "type":"Raum",
      "building":"1",
      "floor":"2",
      "room":{
        "id":"2101",
        "type":"Seminar",
        "size":"50"
      },
      "content":{
        "table":{
          "amount":"50",
          "fixed":true
        }
      }
  });         // 1 > 2.101
  test_data.push({
      "id":"bn_2102",
      "data":"rm_2102",
      "north":"bn_t201",
      "n_weight":2,
      "east":"null",
      "e_weight":0,
      "south":"bn_2103",
      "s_weight":1,
      "west":"null",
      "w_weight":0,
      "up":"null",
      "u_weight":0,
      "down":"null",
      "d_weight":0,

      "beacon_id":"bn_2102",
      "type":"Raum",
      "building":"1",
      "floor":"2",
      "room":{
        "id":"2102",
        "type":"Seminar",
        "size":"50"
      },
      "content":{
        "table":{
          "amount":"50",
          "fixed":true
        }
      }
  });         // 2 > 2.102
  test_data.push({
      "id":"bn_2103",
      "data":"rm_2103",
      "north":"bn_2102",
      "n_weight":1,
      "east":"null",
      "e_weight":0,
      "south":"bn_2100",
      "s_weight":0,
      "west":"null",
      "w_weight":0,
      "up":"null",
      "u_weight":0,
      "down":"null",
      "d_weight":0,

      "beacon_id":"bn_2103",
      "type":"Raum",
      "building":"1",
      "floor":"2",
      "room":{
        "id":"2103",
        "type":"Seminar",
        "size":"50"
      },
      "content":{
        "table":{
          "amount":"50",
          "fixed":true
        }
      }
  });         // 3 > 2.103
  test_data.push({
      "id":"bn_2104",
      "data":"rm_2104",
      "north":"bn_2100",
      "n_weight":3,
      "east":"null",
      "e_weight":0,
      "south":"bn_t200b",
      "s_weight":0,
      "west":"null",
      "w_weight":0,
      "up":"null",
      "u_weight":0,
      "down":"null",
      "d_weight":0,

      "beacon_id":"bn_2104",
      "type":"Raum",
      "building":"1",
      "floor":"2",
      "room":{
        "id":"2104",
        "type":"Seminar",
        "size":"50"
      },
      "content":{
        "table":{
          "amount":"50",
          "fixed":true
        }
      }
  });         // 4 > 2.104
  test_data.push({
    "id":"bn_t200b",
    "data":"tr_t200b",
    "north":"bn_2104",
    "n_weight":0,
    "east":"null",
    "e_weight":0,
    "south":"null",
    "s_weight":0,
    "west":"null",
    "w_weight":0,
    "up":"null",
    "u_weight":0,
    "down":"null",
    "d_weight":0,

    "beacon_id":"bn_t200b",
    "type":"Treppe",
    "building":"1",
    "floor":"2"
  });         // 5 > t200b
  test_data.push({
    "id":"bn_t201",
    "data":"tr_t201",
    "north":"bn_2101",
    "n_weight":1,
    "east":"null",
    "e_weight":0,
    "south":"bn_2102",
    "s_weight":2,
    "west":"null",
    "w_weight":0,
    "up":"null",
    "u_weight":0,
    "down":"null",
    "d_weight":0,

    "beacon_id":"bn_t201",
    "type":"Treppe",
    "building":"1",
    "floor":"2"
  });

  database.set( 'timeReservation', '600000' );
  database.set( 'timeBooking', '3600000');
  for( var i = 0; i < test_data.length; i++ ){
    database.hmset( test_data[ i ].id,
    {
      'data': test_data[ i ].data,
      'arrayPos': 0,
      "north": test_data[ i ].north,
      "n_weight": test_data[ i ].n_weight,
      "east": test_data[ i ].east,
      "e_weight": test_data[ i ].e_weight,
      "south": test_data[ i ].south,
      "s_weight": test_data[ i ].s_weight,
      "west": test_data[ i ].west,
      "w_weight": test_data[ i ].w_weight,
      "up": test_data[ i ].up,
      "u_weight": test_data[ i ].u_weight,
      "down": test_data[ i ].down,
      "d_weight": test_data[ i ].d_weight
    });
    switch( test_data[ i ].type ){
      case "Treppe":
        database.hmset( test_data[ i ].data,
        {
          "beacon_id":test_data[ i ].beacon_id,
          "node_id":test_data[ i ].id,
          "type":test_data[ i ].type,
          "building":test_data[ i ].building,
          "floor":test_data[ i ].floor
        });
        break;
      case "Raum":
        database.hmset( test_data[ i ].data,
        {
          "beacon_id":test_data[ i ].beacon_id,
          "node_id":test_data[ i ].id,
          "type":test_data[ i ].type,
          "building":test_data[ i ].building,
          "floor":test_data[ i ].floor,
          "status":JSON.stringify( { "type":"Frei", "user":null, "duration":0 }),
          "room":JSON.stringify( test_data[ i ].room ),
          "content":JSON.stringify( test_data[ i ].content )
        });
        break;
    }
    if( i % 2 == 0 ){
      if( test_data[ i ].type == "Raum" ){
        database.lpush( 'empty_rooms', test_data[ i ].data );
      }
    }
  }
}
function createItems(){
  return new Promise( function( resolve, reject ){
    database.keysAsync( 'bn_*' )
    .then( function( res ){
      for( var i = 0; i < res.length; i++ ){
        database.hgetAsync( res[ i ], 'data' )
        .then(function( response ){
          MULTILINKEDLIST.createItem( response, (function( item ){
            nodeArray.push( item );
            database.hmsetAsync( res[ nodeArray.length-1 ], 'arrayPos', (nodeArray.length-1));
            if( nodeArray.length == res.length ){
              resolve( true );
            }
          }).bind({res:res}));
        });
      }
    });
  });
}
function improvisedForLoop( array, index ){
  database.hgetallAsync( array[ index ] )
  .then( function( data ){
    if( data.north != "null" ){
      database.hgetAsync( data.north, 'arrayPos' )
      .then(function( arrayPos ){
        nodeArray[ data.arrayPos ].connectAt( 'north', data.n_weight, nodeArray[ arrayPos ] );
      });
    }
    if( data.east != "null" ){
      database.hgetAsync( data.east, 'arrayPos' )
      .then(function( arrayPos ){
        nodeArray[ data.arrayPos ].connectAt( 'east', data.e_weight, nodeArray[ arrayPos ] );
      });
    }
    if( data.south != "null" ){
      database.hgetAsync( data.south, 'arrayPos' )
      .then(function( arrayPos ){
        nodeArray[ data.arrayPos ].connectAt( 'south', data.s_weight, nodeArray[ arrayPos ] );
      });
    }
    if( data.west != "null" ){
      database.hgetAsync( data.west, 'arrayPos' )
      .then(function( arrayPos ){
        nodeArray[ data.arrayPos ].connectAt( 'west', data.w_weight, nodeArray[ arrayPos ] );
      });
    }
    if( data.up != "null" ){
      database.hgetAsync( data.up, 'arrayPos' )
      .then(function( arrayPos ){
        nodeArray[ data.arrayPos ].connectAt( 'up', data.u_weight, nodeArray[ arrayPos ] );
      })
    }
    if( data.down != "null" ){
      database.hgetAsync( data.down, 'arrayPos' )
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
function connectItems(){
  return new Promise( function( resolve, reject ){
    database.keysAsync( 'bn_*' )
    .then( function( res ){
      var index = 0;
      improvisedForLoop( res, index )
    });
  })
}
//------------------------------------------//
function getNode( index ){
  return new Promise( function( resolve, reject ){
    if( index < nodeArray.length ){
      resolve( nodeArray[ index ] );
    } else {
      reject();
    }
  })
}

function set( key, data ){
  return new Promise( function( resolve, reject ){
    database.setAsync( key, data )
    .then( function( res ){
      resolve( res );
    });
  });
}
function get( key ){
  return new Promise( function( resolve, reject ){
    database.getAsync( key )
    .then( function( res ){
      resolve( res );
    });
  });
}
function setHash( key, data ){
  return new Promise( function( resolve, reject ){
    database.hmsetAsync( key, JSON.stringify( data ))
    .then( function( res ){
      resolve( res );
    });
  });
}
function setHashField( key, field, data ){
  console.log( "HIER?" );
  return new Promise( function( resolve, reject ){
    console.log( "key: " + key );
    console.log( "field:" + field );
    console.log( "data: " + data );
    database.hsetAsync( key, field, JSON.stringify( data ))
    .then( function( res ){
      resolve( res );
    });
  });
}
function getHash( key ){
  return new Promise( function( resolve, reject ){
    database.hgetallAsync( key )
    .then( function( res ){
      if( res != null ){
        resolve( res );
      } else {
        reject();
      }
    });
  })
}
function getHashField( key, field ){

}
function addToList( key, data ){

}
function removeFromList( key, data ){
  return new Promise( function( resolve, reject ){
    database.lremAsync( key, -1, data )
    .then( function( res ){
      resolve( res );
    });
  });
}
function getList( key ){
  return new Promise( function( resolve, reject ){
    database.llenAsync( key )
    .then( function( res ){
      database.lrangeAsync( key, 0, res )
      .then( function( res ){
        resolve( res );
      });
    });
  });
}
function popList( key, data ){

}
//------------------------------------------//


// DATABASE SETUP
global.database                             = redis.createClient( 6379 );

// DATABASE STARTUP
database.on( 'connect', function(){
  setup_LinkedList()
  console.log( '[INFO] Database connected on port: 6379' );
});
database.on( 'error', function( err ){
  console.error( err );
})

// ERROR HANDLING

// EOF
module.exports                              = {
  getNode:      function( index ){
    return new Promise( function( resolve, reject ){
      getNode( index )
      .then( function( res ){
        if( res != null ){
          resolve( res );
        } else {
          reject();
        }
      })
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
