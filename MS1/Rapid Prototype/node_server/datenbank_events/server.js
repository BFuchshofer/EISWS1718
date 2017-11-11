// eisws1718_prototyp/datenbank_events/server.js

/*
 *
 *
 *
 */

// NODE MODULES
global.express              = require( 'express' );
global.redis                = require( 'redis' );
global.bodyParser           = require( 'body-parser' );
global.path                 = require( 'path' );
var ip                      = require( 'ip' );                                                         // node module we use to show the local system ip in server startup

global.Promise             = require( 'bluebird' );
Promise.promisifyAll( redis );
Promise.promisifyAll( redis.RedisClient.prototype );
Promise.promisifyAll( redis.Multi.prototype );

// VARIABLES
global.VARIABLES            = require( './variables.json' );
global.DB_FUNCTIONS         = require( './functions/database.js' );
var app                     = express();
global.veranstaltungDB      = redis.createClient( VARIABLES.db_port );
global.jsonParser           = bodyParser.json();



app.use( bodyParser.urlencoded( { extended: false } ));

// START SERVER
app.set( 'port', VARIABLES.port );
console.log( '[INFO] VeranstaltungDB Server starting...' );

app.listen( app.get( 'port' ), function(){
    console.log( '[INFO] All Files Loaded' );
    console.log( '[INFO] Server ready on: http://' + ip.address() + ':' + app.get( 'port' ));
});

veranstaltungDB.on( 'connect', function(){
    console.log( '[INFO] RedisDB connected on port: ' + VARIABLES.db_port );
    DB_FUNCTIONS.setupTestDB();
});


// ERROR HANDLING
app.use( function( err, req, res, next ){
    res.status( err.status || 500 );
});

// EOF
router                      = require( './router' )(app);
module.exports              = app;
