// eisws1718_prototyp/webserver/server.js

// NODE MODULES
global.express              = require( 'express' );
global.path                 = require( 'path' );
global.http                 = require( 'http' );
global.bodyParser           = require( 'body-parser' );

global.querystring          = require( 'querystring' );

// VARIABLES
global.VARIABLES            = require( './variables.json' );
global.suggestion_func      = require( './functions/suggestion.js' );
global.suspend_func         = require( './functions/suspension.js' );

var app                     = express();
global.jsonParser           = bodyParser.json();
var ip                      = require( 'ip' );



app.set( 'view engine' , 'ejs' );
app.use( bodyParser.urlencoded( { extended: false } ));

// SERVER STARTUP
app.set( 'port', VARIABLES.port );

console.log( '[INFO] Webserver starting...' );

app.listen( app.get( 'port' ), function(){
    console.log( '[INFO] All Files Loaded' );
    console.log( '[INFO] Webserver ready on: http://' + ip.address() + ':' + app.get( 'port' ));
});


// ERROR HANDLING
app.use( function( err, req, res, next ){
    res.status( err.status || 500 );
});

// EOF
router                      = require( './router' )( app );
module.exports              = app;
