// Webserver für das EISWS1718FuchshoferFonsecaLuis Projekt

// NODE MODULES
global.express                              = require( 'express' );
global.path                                 = require( 'path' );
global.http                                 = require( 'http' );
global.bodyParser                           = require( 'body-parser' );

global.ip                                   = require( 'ip' );
global.redis                                = require( 'redis' );
global.bluebird                             = require( 'bluebird' );


// VARIABLES
global.VAR_DATABASESERVER                   = require( './cfg/databaseserver.json' );
bluebird.promisifyAll( redis.RedisClient.prototype );
bluebird.promisifyAll( redis.Multi.prototype );
global.database                             = redis.createClient( 6379 );


var server                                  = express();
global.jsonParser                           = bodyParser.json();

// FUNCTIONS

// SERVER SETUP
server.use( bodyParser.urlencoded( { extended: false } ));
server.set( 'port', VAR_DATABASESERVER.port );

// SERVER STARTUP
console.log( '[INFO] Databaseserver starting...' );
server.listen( server.get( 'port' ), function()
{
  console.log( '[INFO] All Files Loaded' );
  console.log( '[INFO] Databaseserver ready on: http://' + ip.address() + ':' + server.get( 'port' ));
});

// ERROR HANDLING
server.use( function( err, req, res, next )
{
  res.status( err.status || 500 );
});

// EOF
router                                      = require( './router' )( server );
module.exports                              = server;
