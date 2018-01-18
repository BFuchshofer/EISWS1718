// Webserver für das EISWS1718FuchshoferFonsecaLuis Projekt

// NODE MODULES
global.express                              = require( 'express' );
global.path                                 = require( 'path' );
global.http                                 = require( 'http' );
global.bodyParser                           = require( 'body-parser' );

global.ip                                   = require( 'ip' );

// VARIABLES
global.VAR_WEBSERVER                        = require( './cfg/webserver.json' );
var List                                    = require( './util/multilinkedlist.js' );
global.MULTILINKEDLIST;
global.DATABASE                             = require( './util/database.js' );
global.FUNCTIONS                            = require( './util/functions.js' );

var server                                  = express();
global.jsonParser                           = bodyParser.json();

// FUNCTIONS

// SERVER SETUP
server.set( 'view engine', 'ejs' );
server.use( bodyParser.urlencoded( { extended: false } ));
server.set( 'port', VAR_WEBSERVER.port );

// SERVER STARTUP
console.log( '[INFO] Webserver starting...' );
server.listen( server.get( 'port' ), function()
{
  // LOAD DATABASE
  // CREATE MULTLINKEDLIST
  MULTILINKEDLIST                           = new List();
  //--// CREATE FIRST NODE/ITEM FROM DB ENTRY ( get all keys and run through them in FOR-LOOP

  console.log( '[INFO] All Files Loaded' );
  console.log( '[INFO] Webserver ready on: http://' + ip.address() + ':' + server.get( 'port' ));
});

// ERROR HANDLING
server.use( function( err, req, res, next )
{
  res.status( err.status || 500 );
});

// EOF
router                                      = require( './router' )( server );
module.exports                              = server;
