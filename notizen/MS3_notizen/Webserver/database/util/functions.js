
var weekday = ["Sonntag", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag"];

var test_data = require( './test_data.json' );


function forLoop( time, array, index, resultArray, callback ){
  database_veranstaltung.hgetallAsync( array[ index ])
  .then( function( result ){
    result.start = JSON.parse( result.start );
    result.end = JSON.parse( result.end );


    console.log( "-------------------------------------------------" );
    console.log( "ROOM:      " + result.room_id );
    console.log( "DAY:       " + result.day + " " + time.day );
    console.log( "HOUR0:     " + result.start.hours + " <= " + time.hours + " <= " + result.end.hours );
    console.log( "MINUTES0:  " + result.start.minutes + " <= " + time.minutes + " <= " + result.end.minutes );
    console.log( "HOUR05:    " + result.start.hours + " <= " + time.hours05 + " <= " + result.end.hours );
    console.log( "MINUTES05: " + result.start.minutes + " <= " + time.minutes05 + " <= " + result.end.minutes );
    console.log( "HOUR1:     " + result.start.hours + " <= " + time.hours1 + " <= " + result.end.hours );
    console.log( "MINUTES1:  " + result.start.minutes + " <= " + time.minutes1 + " <= " + result.end.minutes );


    if( result.day == time.day ){
      /*
      if( (result.start.hours <= time.hours && result.start.minutes <= time.minutes && result.end.hours >= time.hours && result.end.minutes >= time.minutes) &&
          (result.start.hours <= time.hours05 && result.start.minutes <= time.minutes05 && result.end.hours >= time.hours05 && result.end.minutes >= time.minutes05) &&
          (result.start.hours <= time.hours1 && result.start.minutes <= time.minutes1 && result.end.hours >= time.hours1 && result.end.minutes >= time.minutes1)){
        resultArray.push( result.room_id );
      }
      if( ( result.start.hours <= ( time.hours || time.hours05 || time.hours1 ) < result.end.hours )){
        if( result.start.hours ==  time.hours1  < result.end.hours ){
          if( result.start.minutes < time.minutes1 ){
            console.log( "Raum ist in 1 stunde belegt" );
          } else {
            console.log( "Raum ist in 1 stunde frei" );
          }
        } else {
          console.log( "Raum ist JETZT oder in 30 Minuten belegt" );
        }
      }*/
      if( result.start.hours <= time.hours && time.hours < result.end.hours ){
        console.log( result.start.hours + " " + time.hours + " " + result.end.hours );
        console.log( "Raum ist JETZT belegt" );
        resultArray.push( result.room_id )
      } else if ( result.start.hours <= time.hours1 && time.hours1 < result.end.hours ){
        if( result.start.minutes <= time.minutes1 ){
          console.log( "Raum ist in 1 Stunde belegt" );
          resultArray.push( result.room_id )
        } else {
          console.log( "Raum ist frei" );
        }
      } else {
        console.log( "Raum ist frei" );
      }
    }
  })
  .then( function(){
    if( index < array.length-1 ){
      forLoop( time, array, ( index + 1 ), resultArray, callback );
    } else {
      callback( resultArray );
    }
  });
}

function otherForLoop( array, index, resultArray, callback ){
  database.getAsync( array[ index ] )
  .then( function( result ){
    resultArray.push( result.substring(3) );
  })
  .then( function(){
    if( index < array.length-1 ){
      otherForLoop( array, (index+1), resultArray, callback );
    } else {
      callback( resultArray );
    }
  })
}

function getUsedRooms(){
  return new Promise( function( resolve, reject ){
    database_veranstaltung.keysAsync( 'ver_*' )
    .then( function( result ){
      if( result != null ){
        var time = {
          "day": weekday[ new Date().getDay() ],
          "hours":new Date().getHours(),
          "minutes": new Date().getMinutes(),
          "hours1":new Date().getHours()+1,
          "minutes1":new Date().getMinutes()
        };
        var resultArray = [];
        forLoop( time, result, 0, resultArray, function( used_rooms ){
          console.log( used_rooms );
          database.keysAsync( 'ru_*' )
          .then( function( result ){
            console.log( result );
            if( result != null ){
              if( result.length != 0 ){
                otherForLoop( result, 0, used_rooms, function( used_rooms ){
                  console.log( used_rooms );
                  resolve( used_rooms );
                });
              } else {
                resolve( used_rooms );
              }
            } else {
                resolve( used_rooms );
            }
          })
        });
      }
    })
  });
}

function fillTestData(){
  for( var i = 0; i < test_data.length; i++ ){
    var data = {
      "key":    test_data[ i ].key,
      "id":     test_data[ i ].id,
      "room_id":test_data[ i ].room_id,
      "day":    test_data[ i ].day,
      "start":  JSON.stringify( test_data[ i ].start ),
      "end":    JSON.stringify( test_data[ i ].end )
    }
    database_veranstaltung.hmsetAsync( test_data[ i ].key, data );
  }
}

module.exports = {
  fillTestData: function(){
    fillTestData();
  },
  getUsedRooms: function(){
    return new Promise( function( resolve, reject ){
      getUsedRooms()
      .then( function( result ){
        resolve( result );
      });
    });
  }
}
