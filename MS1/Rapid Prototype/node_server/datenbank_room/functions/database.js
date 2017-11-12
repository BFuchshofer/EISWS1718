// eisws1718_prototyp/datenbank_room/functions/database.js

/*
 *
 *
 *
 */

//TODO: put in variables.json
var institut_room_key           = 'rm_thkoeln_';
var suggestion_time             = VARIABLES.suggestion_time;        // 30 sec * 1000 millisec
var reservation_time            = VARIABLES.reservation_time;       // 15 min * 60 sec * 1000 millisec
var booking_time                = VARIABLES.booking_time;           // 1 h * 60 min * 60 sec * 1000 millisec

var test_rooms              = require( '../test_data/test_rooms.json' );

function roomExists( key, callback ){
    console.log( key );
    roomDB.exists( key, function( err, res ){
        console.log( res );
        if( res === 1 ){
            callback( null, true );
        } else {
            callback( null, false );
        }
    });
}
function roomExistsPromise( key ){
    console.log( 'key_exists?: ' + key );
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
    roomExistsPromise( room.number )
        .then( function( result ){
            if( !result ){
                //console.log( room.key );
                roomDB.multi()
                .hmset( room.number, {
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
                .lpush( 'ls_rooms', room.number )
                .lpush( 'ls_empty', room.number )
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
 *
 */
function getRoomField( key, field, callback ){
    roomDB.hget( key, field, function( err, obj ){
        console.log( obj );
        callback( null, obj );
    });
}
function getRoomFieldPromise( key, field ){

}

/**
 * edit multiple fields of a room in roomDB
 * @param {String} key
 * @param {Object} room
 * @param {Function} callback
 */
function editRoom( key, room, callback ){
    //TODO: finish editRoom
}
function editRoomPromise( key, room ){
    //TODO: finish editRoom with Promise
}

/**
 * edit a specified field of a room in roomDB
 * @param {String} key
 * @param {String} field
 * @param {String} value
 * @param {Function} callback
 */
function editRoomField( key, field, value ){
    roomDB.hset( key,  field, value , function( err, res ){
        //console.log( '[EDIT] ' + key + ', ' + field + ' ' + value );
    });
}
function editRoomFieldPromise( key, field, value ){
    return new Promise( function( resolve, reject ){
        roomDB.hset( key, field, value, function( err, res ){
            if( res != null ){
                console.log( '[EDIT] ' + key + ', ' + field + ' ' + value );
                resolve( 1 );
            } else {
                reject();
            }
        });
    });
}

/**
 * deletes a specified Room from roomDB
 * @param {String} key
 * @param {Funciton} callback
 */
function delRoom( key, callback ){
    //TODO: finish delRoom
}
function delRoomPromise( key ){
    //TODO finish delRoom with Promise
}

/**
 * getRoomFromListByIndex
 * @param {String} listname
 * @param {Integer} index
 * @param {Function} callback
 */
function getRoomFromListByIndex( listname, index, callback ){
    roomDB.lindex( listname, index, function( err, res ){
        roomDB.hgetall( res, function( err, obj ){
            callback( null, obj );
        });
    });
}

/**
 *
 */
function addRoomToEmptyList( key ){
    roomDB.lpush( 'ls_empty', key );
}
function remRoomFromEmptyList( key ){
    roomDB.lrem( 'ls_empty', -1, key );
}


function getOccupiedBy( key ){
    return new Promise( function( resolve, reject ){
        roomDB.hget( key, 'occupied', function( err, res ){
            if( res != null ){
                resolve( res );
            } else {
                reject( new Error( 'Could not read' ));
            }
        });
    });
}


function suspendSuggestion( key, time ){
    var timer = setTimeout(function(){
        console.log( 'suspend suggestion for: ' + key  );

        addRoomToEmptyList( key );
        editRoomField( key, "occupied", false );
        editRoomField( key, "suggestion_begin", -1 );
        editRoomField( key, "suggestion_end", -1 );
    }, time);
    console.log( 'TIMEOUT: ' + timer );
    setRoomTimer( key, timer );
}

function suspendReservation( key, time ){
    var timer = setTimeout(function(){
        console.log( 'suspend reservation for: ' + key  );

        addRoomToEmptyList( key );
        editRoomField( key, "occupied", false );
        editRoomField( key, "suggestion_begin", -1 );
        editRoomField( key, "suggestion_end", -1 );
    }, time);
    console.log( 'TIMEOUT: ' + timer );
    setRoomTimer( key, timer );
}

function suspendBooking( key, time ){
    var timer = setTimeout(function(){
        console.log( 'suspend booking for: ' + key  );

        addRoomToEmptyList( key );
        editRoomField( key, "occupied", false );
        editRoomField( key, "suggestion_begin", -1 );
        editRoomField( key, "suggestion_end", -1 );
    }, time);
    console.log( 'TIMEOUT: ' + timer );
    setRoomTimer( key, timer );
}

//TODO: pop item from array after use
var timers = [];
function setRoomTimer( key, timer ){
    timers.push( timer );
    roomDB.set( 'timer_' + key, (timers.length - 1), function( err, res ){
        console.log( res );
    });
    console.log( timers[ timers.length-1 ] == timer );
}


/**
 * sets the suggestiontimes for a specific room
 */

/*
function getSuggestion( user, begin, end ){ //TODO: insert algorithm + arguments
    var suggestion = 'rm_thkoeln_0401';

    ///////////////////////////////////////////////////ALGORITHM
    ////////////////////////////////////////////////////////////

    setSuggestion( suggestion, user, begin, end );
    return { "johntitor":{"number":suggestion, "user": user, "suggestion_begin": begin, "suggestion_end":end}};
}
function getSuggestionPromise(){
    return new Promise( function( resolve, reject ){
        var suggestion = 'rm_thkoeln_0401';

        //////////////////// ALGORITHM

        //////////////////////////////

        var time                = Date.now();
        var time_end            = time + suggestion_time;
        editRoomFieldPromise( suggestion, "suggestion_begin", time )
            .then( function( res ){
                //TODO: do something
            })
            .catch( function(){
                console.error( 'Could not write in roomDB' );
            });
        editRoomFieldPromise( suggestion, "suggestion_end", time_end )
            .then( function( res ){
                //TODO: do something
            })
            .catch( function(){
                console.error( 'Could not write in roomDB' );
            });
    });
}
*/

function setSuggestion( key, user, begin, end ){





    ///////////////// suspendSuggestion
    if( begin != -1 && end != -1 ){
        suspendSuggestion( key, suggestion_time );
    }
    ///////////////////////////////////

    editRoomField( key, "occupied", user );
    editRoomField( key, "suggestion_begin", begin );
    editRoomField( key, "suggestion_end", end );
    return { "johntitor":{"number":key, "user": user, "suggestion_begin": begin, "suggestion_end":end}};
}

/**
 * sets the reservationtimes for a specific room
 */
function setReservation( key, user, begin, end ){

    ///////////////// suspendReservation
    roomDB.get( 'timer_' + key, function( err, res ){
        clearTimeout( timers[ parseInt( res, 10 ) ] );
        timers.pop( parseInt( res, 10 ));
        console.log( res );
        if( begin != -1 && end != -1){
            suspendReservation( key, reservation_time );
        }
    });
    ////////////////////////////////////

    editRoomField( key, "occupied", user );
    editRoomField( key, "reservation_begin", begin );
    editRoomField( key, "reservation_end", end );
    return { "reservation_begin":begin, "reservation_end":end };
}

/**
 * sets the bookingtimes for a specific room
 */
function setBooking( key, user, begin, end ){

    ///////////////// suspendBooking
    roomDB.get( 'timer_' + key, function( err, res ){
        if( res != null ){
            clearTimeout( timers[ parseInt( res, 10 ) ] );
            timers.pop( parseInt( res, 10 ));
        }
        console.log( res );
        if( begin != -1 && end != -1){
            suspendBooking( key, booking_time );
        }
    });
    ////////////////////////////////

    editRoomField( key, "occupied", user );
    editRoomField( key, "booking_begin", begin );
    editRoomField( key, "booking_end", end );
    return { "booking_begin":begin, "booking_end":end };
}



function getEmptyRooms( callback ){
    var rooms = [];
    roomDB.llenAsync( 'ls_empty').
    then( function( data ){
        if( data != 0 ){
            for( var i = 0; i < data; i++ ){
                roomDB.lindexAsync( 'ls_empty', i ).
                then(function( key ){
                    roomDB.hgetallAsync( key ).
                    then( function( result ){
                        rooms.push( result );
                        if( rooms.length == data ){
                            callback( rooms );
                        }
                    });
                });
            }
        } else {
            callback( null );
        }
    });
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
            roomExists( key, callback );
        },
        getRoom: function ( key, callback ){
            getRoom( key, callback );
        },
        getRoomPromise: function( key ){
            return getRoomPromise( key );
        },
        getSuggestion: function( user, begin, end ){
            return getSuggestion( user, begin, end );
        },
        setSuggestion: function( key, user, begin, end ){
            return setSuggestion( key, user, begin, end );
        },
        setReservation: function( key, user, begin, end ){
            return setReservation( key, user, begin, end );
        },
        setBooking: function( key, user, begin, end ){
            return setBooking( key, user, begin, end );
        },
        getEmptyRooms: function( callback ){
            getEmptyRooms( callback );
        },
        addRoomToEmptyList: function( key ){
            addRoomToEmptyList( key );
        },
        remRoomFromEmptyList: function( key ){
            remRoomFromEmptyList( key );
        },
        getOccupiedBy: function( key ){
            return getOccupiedBy( key );
        }
    };
