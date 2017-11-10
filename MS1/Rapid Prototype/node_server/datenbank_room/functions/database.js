// eisws1718_prototyp/datenbank_room/functions/database.js

/*
 *
 *
 *
 */

var suggestion_time         = 30*1000;      // 30 sec * 1000 millisec
var reservation_time        = 15*60*1000;   // 15 min * 60 sec * 1000 millisec
var booking_time            = 1*60*60*1000; // 1 h * 60 min * 60 sec * 1000 millisec

var test_rooms              = require( '../test_data/test_rooms.json' );

function roomExists( key, callback ){
    roomDB.exists( key, function( err, res ){
        if( res === 1 ){
            callback( null, true );
        } else {
            callback( null, false );
        }
    });
}
function roomExistsPromise( key ){
    return new Promise( function( resolve, reject ){
        roomDB.exists( key, function( err, res ){
            if( res === 1 ){
                resolve( true );
            } else if( res === 0 ){
                resolve( false );
            } else {
                reject();
            }
        });
    });
}

/**
 * Adds a Room to roomDB
 * @param {Object} room
 * @param {Function} callback
 */
function addRoom( room ){
    roomExistsPromise( room.key )
        .then( function( result ){
            if( !result ){
                console.log( room.key );
                roomDB.multi()
                .hmset( room.key, {
                    'number': room.number,
                    'occupied': room.occupied,
                    'suggestion_begin': room.suggestion_begin,
                    'suggestion_end': room.suggestion_end,
                    'reservation_begin': room.reservation_begin,
                    'reservation_end': room.reservation_end,
                    'booking_begin': room.booking_begin,
                    'booking_end': room.booking_end,
                    'size_min': room.size_min,
                    'size_max': room.size_max,
                    'tables': room.tables,
                    'chairs': room.chairs,
                    'pc': room.pc,
                    'beamer': room.beamer,
                    'camera': room.camera,
                    'chalkboard': room.chalkboard,
                    'whiteboard': room.whiteboard
                })
                .lpush( 'ls_rooms', room.key )
                .lpush( 'ls_empty', room.key )
                .exec( function( err, res ){
                    console.log( 'Room created! Key stored in ls_rooms && ls_empty');
                });
            } else {
                console.log( 'Room exists. ' );
            }
        })
        .catch( function(){
            console.log( '¯\_(ツ)_/¯' );
        });
    /*
    roomExists( room.key, function( err, res ){
        if( !res ){
            console.log( room.key );
            roomDB.multi()
            .hmset( room.key, {
                'number': room.number,
                'occupied': room.occupied,
                'suggestion_begin': room.suggestion_begin,
                'suggestion_end': room.suggestion_end,
                'reservation_begin': room.reservation_begin,
                'reservation_end': room.reservation_end,
                'booking_begin': room.booking_begin,
                'booking_end': room.booking_end,
                'size_min': room.size_min,
                'size_max': room.size_max,
                'tables': room.tables,
                'chairs': room.chairs,
                'pc': room.pc,
                'beamer': room.beamer,
                'camera': room.camera,
                'chalkboard': room.chalkboard,
                'whiteboard': room.whiteboard
            })
            .lpush( 'ls_rooms', room.key )
            .lpush( 'ls_empty', room.key )
            .exec( function( err, res ){
                console.log( 'Room created! Key stored in ls_rooms && ls_empty');
            });
        } else {
            console.log( 'Room exists!' );
        }
    }.bind({room:room}));*/
}

/**
 * returns a specific Room
 * @param {String} key
 * @param {Function} callack
 */
function getRoom( key, callback ){
    roomDB.hgetall( key, function( err, obj ){
        console.log( 'database.js - getRoom - obj: ' + obj );
        callback( null, obj );
    });
}
function getRoomPromise( key ){
    return new Promise( function( resolve, reject ){
        roomDB.hgetall( key, function( err, obj ){
            if( obj != null ){
                resolve( obj );
            } else {
                reject( new Error( 'Could not load from DB' ));
            }
        });
    });
}

/**
 * edit multiple fields of a room in roomDB
 * @param {String} key
 * @param {Object} room
 * @param {Function} callback
 */
function editRoom( key, room, callback ){

}


function editRoomField( key, field, value, callback ){
    roomDB.hset( key,  field, value , function( err, res ){
        console.log( 'database.js - editRoomField - res: ' + res );
    });
}

function delRoom( key, callback ){

}

/**
 * getRoomFromListByIndex
 */
function getRoomFromListByIndex( listname, index, callback ){
    roomDB.lindex( listname, index, function( err, res ){
        roomDB.hgetall( res, function( err, obj ){
            callback( null, obj );
        });
    });
}



// ROOM INTERACTIONS
function getSuggestion(){ //TODO: insert algorithm + arguments
    var suggestion = 'rm_thkoeln_0401';

    ///////////////////////////////////////////////////////////////
    /*
    var empty_rooms = 0;
    roomDB.llen( 'ls_empty', function( err, res ){
        empty_rooms = res;
        var rooms_array = [];
        console.log( 'database.js - getSuggestion - empty_rooms: ' + empty_rooms );
        if( empty_rooms >= 1 ){
            getRoomFromListByIndex( 'ls_empty', 0, function( err, obj ){
                rooms_array.push( obj );
            });
        }
        console.log( 'database.js - getSuggestion - rooms_array: ' + rooms_array.length );

    });
    */

    ///////////////////////////////////////////////////////////////



    var timerbegin          = Date.now();
    console.log( 'database.js - getSuggestion - suggestion_begin: ' + timerbegin );
    var timerend            = timerbegin + suggestion_time;
    editRoomField( suggestion, "suggestion_begin", timerbegin );
    editRoomField( suggestion, "suggestion_end", timerend );
    return { "key":suggestion, "suggestion_begin": timerbegin, "suggestion_end":timerend};
}

/**
 * sets the reservationtimer for a specific room
 */
function getReservation( key, callback ){

    var timerbegin          = Date.now();
    var timerend            = timerbegin + reservation_time;

    editRoomField( key, "reservation_begin", timerbegin );
    editRoomfield( key, "reservation_end", timerend );
    return { "reservation_begin":timerbegin, "reservation_end":timerend };
}



/**
 * setup for test roomDB ( inserts 2 test rooms )
 */

function setupTestDB(){
    // Create Rooms as Hashes and save rm_key in ls_rooms
    for( var i = 0; i< test_rooms.rooms.length; i++ ){
        addRoom( test_rooms.rooms[i] );
    }
}

// EOF

module.exports              =
    {
        setupTestDB: function(){
            setupTestDB();
        },
        roomExists: function( key, callback ){
            return roomExists( key, callback );
        },
        getRoom: function ( key, callback ){
            getRoom( key, callback );
        },
        getSuggestion: function(){
            return getSuggestion();
        },
        getRoomPromise: function( key ){
            return getRoomPromise( key );
        }
    };
