// Datenbankserver für das EISWS1718FuchshoferFonsecaLuis Projekt

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
global.TEST_DATA                            = require( './test_data.json' );
global.FUNCTIONS                            = require( './util/functions.js' );
bluebird.promisifyAll( redis.RedisClient.prototype );
bluebird.promisifyAll( redis.Multi.prototype );
global.database                             = redis.createClient( VAR_DATABASESERVER.db_room.port );
global.database_veranstaltung               = redis.createClient( VAR_DATABASESERVER.db_veranstaltung.port );


var server                                  = express();
global.jsonParser                           = bodyParser.json();

// FUNCTIONS

function pingServer(){
    var post_data = {
      "ip":ip.address(),
      "addr":"http://" + ip.address(),
      "port":VAR_DATABASESERVER.port
    };

    var options                 = {
                    host: VAR_DATABASESERVER.webserver.ip,
                    port: VAR_DATABASESERVER.webserver.port,
                    path: '/databasePing',
                    method: 'POST',
                    headers:{
                        'Content-Type':'application/json',
                        'Content-Length':Buffer.byteLength( new Buffer( JSON.stringify( post_data )) )
                    }
                };

    var externalRequest         = http.request( options );
    externalRequest.write( new Buffer( JSON.stringify( post_data )) );
    externalRequest.end();
}


// SERVER SETUP
server.use( bodyParser.urlencoded( { extended: false } ));
server.set( 'port', VAR_DATABASESERVER.port );

// SERVER STARTUP
console.log( '[INFO] Databaseserver starting...' );
server.listen( server.get( 'port' ), function()
{
  FUNCTIONS.fillTestData();
  setTimeout( function(){
    FUNCTIONS.getUsedRooms();
  }, 10000 );

  setTimeout( function(){
    pingServer();
  }, 5000 );
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
