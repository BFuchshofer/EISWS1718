// eisws1718_prototyp/datenbank_room/server.js

/*
 *
 *
 *
 */

// NODE MODULES
global.express              = require( 'express' );
global.redis                = require( 'redis' );
global.bodyParser           = require( 'body-parser' );
var ip                      = require( 'ip' );                                                         // node module we use to show the local system ip in server startup

global.querystring          = require( 'querystring' );

global.Promise             = require( 'bluebird' );
Promise.promisifyAll( redis );
Promise.promisifyAll( redis.RedisClient.prototype );
Promise.promisifyAll( redis.Multi.prototype );

// VARIABLES
global.VARIABLES            = require( './variables.json' );
global.DB_FUNCTIONS         = require( './functions/database.js' );
var app                     = express();
global.roomDB               = redis.createClient( VARIABLES.db_port );
global.jsonParser           = bodyParser.json();



app.use( bodyParser.urlencoded( { extended: false } ));

// START SERVER
app.set( 'port', VARIABLES.port );
console.log( '[INFO] RoomDB Server starting...' );

app.listen( app.get( 'port' ), function(){
    console.log( '[INFO] All Files Loaded' );
    console.log( '[INFO] Server ready on: http://' + ip.address() + ':' + app.get( 'port' ));
});

roomDB.on( 'connect', function(){
    console.log( '[INFO] RedisDB connected on port: ' + VARIABLES.db_port );
    DB_FUNCTIONS.setupTestDB();
});


// ERROR HANDLING
//TODO: DB SHUTDOWN ERROR HANDLING
app.use( function( err, req, res, next ){
    res.status( err.status || 500 );
});

// EOF
router                      = require( './router' )( app );
module.exports              = app;
