global.WEEK                 = [ 'Sonntag', 'Montag', 'Dienstag', 'Mittwoch', 'Donnerstag', 'Freitag', 'Samstag' ];

function getVeranstaltungen( callback ){
    var veranstaltungen = [];
    /////////veranstaltungDB
    var date                    = new Date();
    var today                   = date.getDay();
    var vdb_options             = {
        host: VARIABLES.eventdbaddr,
        port: VARIABLES.eventdbport,
        path: '/veranstaltung/' + WEEK[ 1 ],
        method: 'GET',
    };

    console.log( vdb_options.path );

    var externalRequest         = http.request( vdb_options, function( externalResponse ){
        if( externalResponse.statusCode == 200 ){
            externalResponse.on( 'data', function( chunk ){
                veranstaltungen = JSON.parse( chunk );


                callback( veranstaltungen );

            });
        }
    });
    externalRequest.end();

}
function getEmptyRooms( callback ){
    var rooms = [];
    /////////roomDB
    var rdb_options             = {
        host: VARIABLES.roomdbaddr,
        port: VARIABLES.roomdbport,
        path: '/room/empty',
        method: 'GET',
    };

    console.log( rdb_options.path );

    var externalRequest         = http.request( rdb_options, function( externalResponse ){
        if( externalResponse.statusCode == 200 ){
            externalResponse.on( 'data', function( chunk ){
                rooms = JSON.parse( chunk );


                callback( rooms );

            });
        }
    });
    externalRequest.end();

}

function getSuggestion( filter, callback ){
    //console.log( 'In get Suggestion' );
    var suggestion = null;
    var date = new Date();
    //var hour = date.getHours();
    //var min  = date.getMinutes();
    var hour = 17;
    var min = 30;

    var size_max = 0;
    ////////////////////////
    getVeranstaltungen( function( result ){
        getEmptyRooms( function( rooms ){
            for( var i = 0; i < rooms.length; i++ ){
                //console.log( 'Loop: ' + i );
                //console.log( rooms[i].number );
                for( var j = 0; j < result.length; j++ ){
                    //console.log( '- ' +  result[j].room );
                    // Bricht den Schleifendurchlauf ab, wenn die momentane Zeit innerhalb einer Veranstaltungszeit liegt UND die betreffenden Räume die selben sind
                    if(( result[j].begin<=(hour*100+min) && result[j].end>=(hour*100+min) ) && rooms[i].number == result[j].room ){
                        //console.log( 'rip' );
                        break;
                    }
                    // Bricht den Scheifendurchlauf ab, wenn der betreffende Raum innerhalb der nächsten Stunde( Belegungszeit ) durch eine Veranstaltung belegt ist.
                    if( result[j].begin<=((hour+1)*100+min) && result[j].end>=((hour+1)*100+min) && rooms[i].number == result[j].room ){
                        //console.log( 'rip2' );
                        break;
                    }




                }
                if( filter == null ){
                    // Bricht den Schleifendurchlauf ab, wenn die maximale Größe des Raumes ( wie viele Sitzplätze maximal ) größer ist als vom bisher kleinsten raum
                    if( rooms[i].size_max > size_max && size_max != 0 ){
                        //console.log( rooms[i].size_max );
                        //console.log( size_max );
                        //console.log( rooms[i].number + ':/' );
                    } else {
                        //console.log( rooms[i].number + ' Keepo' );
                        //console.log( rooms[i].size_max );
                        size_max = rooms[i].size_max;
                    }
                } else {
                    //TODO: Schließe Räume anhand des 'filter' aus
                }
                suggestion = rooms[i].number;

                //TODO: Gewichtung nach Equipment

                if( i == (rooms.length - 1 )){

                    //console.log( 'Suggestion: ' + suggestion );
                    //callback( suggestion );
                }

            }
            callback( suggestion );
        });
    });
}

module.exports              = {
    getSuggestion: function( filter, callback ){
        getSuggestion( filter, callback );
    }
};
