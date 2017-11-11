// eisws1718_prototyp/datenbank_events/functions/database.js

/*
 *
 *
 *
 *
 * repeating event keys: re_key
 * one-time event  keys: oe_key
 */


var test_veranstaltung      = require( '../test_data/test_veranstaltung.json' );

function veranstaltungExists( key, callback ){
    veranstaltungDB.exists( key, function( err, res ){
        if( res === 1 ){
            callback( null, true );
        } else {
            callback( null, false );
        }
    });
}

function addVeranstaltung( veranstaltung ){
    console.log( 'setVeranstaltung' );
    veranstaltungExists( veranstaltung.key, function( err, res ){
        if( !res ){
            console.log( veranstaltung.key );
            veranstaltungDB.multi()
                .hmset( veranstaltung.key, {
                    'name':veranstaltung.name,
                    'day':veranstaltung.day,
                    'begin': veranstaltung.begin,
                    'end':veranstaltung.end,
                    'user':veranstaltung.user,       //TODO: Inhaber? Besitzer? Lehrer? Dozent?
                    'room':veranstaltung.room,
                    'type':veranstaltung.type
                } )
                .lpush( 'ls_veranstaltungen', veranstaltung.key )
                .lpush( 'ls_' + veranstaltung.day, veranstaltung.key )
                .exec( function( err, res ){
                    console.log( 'Event created! Key stored in ls_veranstaltungen');
                });
        } else {
            console.log( 'Event exists!' );
        }
    }.bind({veranstaltung:veranstaltung}));
}

function getVeranstaltung( key, callback ){
    console.log( 'MOTHERFUCKER' );
    veranstaltungDB.hgetall( key, function( err, obj ){
        console.log( 'databas.js - getVeranstaltung() - result: ' + JSON.stringify( obj ));
        callback( null, obj );
    }.bind({callback:callback}) );
}
function getVeranstaltungField( key, field, callback ){

}

function getVeranstaltungen(){
    return new Promise( function( resolve, reject ){
        veranstaltungDB.keys( 'veranstaltung_*', function( err, res ){
            if( res != null ){
                resolve( res );
            } else {
                reject( new Error( 'Could not get' ));
            }
        });
    });
}

function getVeranstaltungenForDay( day, callback ){
    var veranstaltung = [];
    veranstaltungDB.llenAsync( 'ls_' + day ).
    then( function( data ){
        for( var i = 0; i < data; i++ ){
            veranstaltungDB.lindexAsync( 'ls_' + day, i ).
            then(function( key ){
                veranstaltungDB.hgetallAsync( key ).
                then( function( result ){
                    veranstaltung.push( result );
                    if( veranstaltung.length == i ){
                        callback( veranstaltung );
                    }
                });
            });
        }
    });
}
function getVeranstaltungenForRoom( number, callback ){
    var veranstaltungen = [];
}


function editVeranstaltung( key, veranstaltung, callback ){
}
function editVeranstaltungField( key, field, value ){
        veranstaltungDB.hset( key, field, value, function( err, res ){
            console.log( 'database.js - editVeranstaltung - res: ' + res );
        });
}

function delVeranstaltung( key, callback ){

}










function setupTestDB(){
    // Create Rooms as Hashes and save rm_key in ls_rooms
    console.log( 'setubTestDB' );
    addVeranstaltung( test_veranstaltung.veranstaltungen[0] );
    addVeranstaltung( test_veranstaltung.veranstaltungen[1] );
}




// EOF

module.exports              =
    {
        setupTestDB: function(){
            setupTestDB();
        },
        veranstaltungExists: function( key, callback ){
            veranstaltungExists( key, callback );
        },
        getVeranstaltung: function( key, callback ){
            getVeranstaltung( key, callback );
        },
        getVeranstaltungField: function( key, field, callback ){
            getVeranstaltungField( key, field, callback );
        },
        getVeranstaltungenForDay: function( day, callback ){
            getVeranstaltungenForDay( day, callback );
        }
    };
