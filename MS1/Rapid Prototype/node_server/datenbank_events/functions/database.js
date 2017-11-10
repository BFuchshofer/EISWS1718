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

function setVeranstaltung( veranstaltung, callback ){
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
                .exec( function( err, res ){
                    console.log( 'Event created! Key stored in ls_veranstaltungen');
                });
        } else {
            console.log( 'Event exists!' );
        }
    }.bind({veranstaltung:veranstaltung}));
}

function setupTestDB(){
    // Create Rooms as Hashes and save rm_key in ls_rooms
    console.log( 'setubTestDB' );
    setVeranstaltung( test_veranstaltung.veranstaltungen[0] );
}

function getVeranstaltung( key, callback ){
    console.log( 'MOTHERFUCKER' );
    veranstaltungDB.hgetall( key, function( err, obj ){
        console.log( 'databas.js - getVeranstaltung() - result: ' + JSON.stringify( obj ));
        callback( null, obj );
    }.bind({callback:callback}) );
}
function editVeranstaltung( key, veranstaltung, callback ){

}
function delVeranstaltung( key, callback ){

}



// EOF

module.exports              =
    {
        veranstaltungExists: function( key, callback ){
            return veranstaltungExists( key, callback );
        },
        setupTestDB: function(){
            setupTestDB();
        },
        getVeranstaltung: function( key, callback ){
            return getVeranstaltung( key, callback );
        }
    };
