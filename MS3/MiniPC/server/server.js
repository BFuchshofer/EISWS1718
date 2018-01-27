// NODE MODULES
global.express = require('express');
global.path = require('path');
global.http = require('http');
global.bodyParser = require('body-parser');
global.querystring = require('querystring');

var Bleacon = require('bleacon');
var fs = require('fs');

// VARIABLES
global.VARIABLES = require('./variables.json');
global.FUNCTIONS = require('./functions/main.js');
global.SIMULATION = require('./functions/rfidSim.js');

var app = express();
global.jsonParser = bodyParser.json();
global.IP = require('ip');

// Aktuelle Rauminhalte werden bei jedem Serverstart auf 0 gesetzt
var newData = {
    "data":[],
    "room_id":VARIABLES.room.room_id
};

fs.writeFileSync('./data/curentRoom.json', JSON.stringify(newData));


app.use(bodyParser.urlencoded({extended: false}));
console.log('');
console.log('****************************************************');
console.log('                      MINI PC'                       );
// SERVER STARTUP
app.set('port', VARIABLES.minipc.port);

app.listen(app.get('port'), function() {
    console.log('[INFO] Webserver starting...');
    console.log('[INFO] All Files Loaded');
    console.log('[INFO] Webserver ready on: http://' + IP.address() + ':' + app.get('port'));
    console.log('[INFO] Raumnummer: ' + VARIABLES.room.room_id);

// Sendet die IP des Minipcs an den Server
FUNCTIONS.sendIP();

// Simuliert einen Beacon der vom Client gefunden werden kann
var uuid = '2f234454-cf6d-4a0f-adf2-f4911ba9ffb2'; // Eindeutige UUID des Beacons, bei Bedarf ändern
var major = VARIABLES.room.room_id; // Steht für die Raumnummer des Raspberry Pi
var minor = 0; 
var measuredPower = -59; // -128 - 127 (measured RSSI at 1 meter)
Bleacon.startAdvertising(uuid, major, minor, measuredPower);
console.log('[INFO] Start beacon advertising with room_id: ' + major);
console.log('****************************************************');
console.log('' );

// RFID Simulation starten (wird bei Serverstart ausgeführt).
SIMULATION.startSim();

});


// ERROR HANDLING
app.use(function(err, req, res, next) {
    res.status(err.status || 500);
});


// EOF
router = require('./router')(app);
module.exports  = app;
