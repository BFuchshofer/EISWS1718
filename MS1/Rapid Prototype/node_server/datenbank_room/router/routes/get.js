var router                  = express.Router();

router.get( '/', function( req, res ){

});
//TODO: get /room

router.get( '/room/empty', function( req, res ){
    DB_FUNCTIONS.getEmptyRooms( function( result ){
        res.status(200).send( result );
    });
});

/* old suggestion GET now in POST
router.get( '/room/suggestion/promise', function( req, res ){
    console.log( 'GET ROOM SUGGESTION PROMISE' );
    var suggestion = DB_FUNCTIONS.getSuggestion();
    DB_FUNCTIONS.getRoomPromise( 'rm_thkoeln_0401' )
        .then( function( result ){
            console.log( 'RESULT: ' + result );
            var result_json = {
                "room":null
            };
            result.suggestion_begin = suggestion.suggestion_begin;
            result.suggestion_end = suggestion.suggestion_end;
            console.log( 'get.js      - /room/suggestion: ' + result );
            result_json.room = result;
            console.log( 'RESULTJSON: ' + result_json );
            res.status(200).send( result_json );
        })
        .catch( function(){
            console.log( '¯\_(ツ)_/¯' );
            res.status(404).send();
        });
});
router.get( '/room/suggestion', function( req, res ){
    console.log( 'GET ROOM SUGGESTION' );
    var suggestion = DB_FUNCTIONS.getSuggestion();
    DB_FUNCTIONS.getRoom( suggestion.key, function( err, result ){
        if( err ) console.error( err );
        var result_json = {
            "room":null
        };
        result.suggestion_begin = suggestion.suggestion_begin;
        result.suggestion_end = suggestion.suggestion_end;
        //console.log( 'get.js      - /room/suggestion: ' + result );
        result_json.room = result;
        res.status(200).send( result_json );
    }.bind({suggestion:suggestion}));
});
*/
module.exports              = router;
