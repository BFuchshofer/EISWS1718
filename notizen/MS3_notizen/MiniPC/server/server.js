// NODE MODULES
global.express              = require('express');
global.path                 = require('path');
global.http                 = require('http');
global.bodyParser           = require('body-parser');

global.querystring          = require('querystring');

var Bleacon = require('bleacon');

// VARIABLES
global.VARIABLES            	= require('./variables.json');
global.FUNCTIONS       		= require('./functions/main.js');
global.SIMULATION 		= require('./functions/rfidSim.js');

var app                     = express();
global.jsonParser           = bodyParser.json();
var ip                      = require('ip');



app.set('view engine' , 'ejs');
app.use(bodyParser.urlencoded( {extended: false}));
console.log('');
    console.log('****************************************************');
console.log('                      MINI PC');
// SERVER STARTUP
app.set('port', VARIABLES.minipc.port);

app.listen(app.get('port'), function(){
    console.log('[INFO] Webserver starting...');
    console.log('[INFO] All Files Loaded');
    console.log('[INFO] Webserver ready on: http://' + ip.address() + ':' + app.get('port'));
    console.log('****************************************************');
    console.log('' );
});


// ERROR HANDLING
app.use(function(err, req, res, next){
    res.status(err.status || 500);
});

var uuid = '2f234454-cf6d-4a0f-adf2-f4911ba9ffb2';
var major = 0; // 0 - 65535
var minor = 0; // 0 - 65535
var measuredPower = -59; // -128 - 127 (measured RSSI at 1 meter)

Bleacon.startAdvertising(uuid, major, minor, measuredPower);
console.log('start advertising');

// RFID Simulation starten (wird bei Serverstart ausgeführt).
SIMULATION.startSim();

// EOF
router                      = require('./router')(app);
module.exports              = app;
