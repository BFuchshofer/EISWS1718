var fs = require('fs');
var bodyParser = require('body-parser');
var jsonParser = bodyParser.json();
var curentRoom = './data/curentRoom.json';


var testString = "id"; // abändern in irgendeinen Key der im Array curentData (curentRoom) vorkommt

// Liefert die Liste der aktuellen Rauminhalte für den Request
function getList() {
var data = readFile();
return data;
}

// Liest die Datei aus in der sich die dokumentierten aktuellen Rauminhalte befinden
function readFile() {
var data;
fName = curentRoom;
data = fs.readFileSync(fName, 'utf8');
if (data != "") {
	return JSON.parse(data);
} else {

return data;
}
}


// Schreibt änderungen der Rauminhalte in die Datei fest
function writeFile(data) {
var testdata = data
fName = curentRoom;
var curentData = readFile();
var tmp = {"data":[]};

if (curentData.data.length > 0) {
curentData.data.push(testdata);
fs.writeFileSync(fName, JSON.stringify(curentData));
console.log('Data saved: ' + JSON.stringify(curentData));
} else {
tmp.data.push(testdata);
fs.writeFileSync(fName, JSON.stringify(tmp));
console.log('Data saved: ' + JSON.stringify(tmp));
}
}


// Reagiert auf Meldungen durch den RFID Scanner (simuliert) und aktualisiert die Liste mit Rauminhalten
function updateList(item) {
var curentData = readFile();

if (curentData.data.length == 0) {
var time = Date.now();
item = JSON.parse(item);
item.timestamp = time
curentData.data.push(JSON.stringify(item));
fs.writeFileSync(fName, JSON.stringify(curentData));
console.log("Added First: " + JSON.stringify(item));
sendList();

} else {
var check = false;
for (var i = 0; i < curentData.data.length; i++) {

if (new String(JSON.stringify(item)).valueOf() == new String(JSON.stringify(curentData.data[i])).valueOf()) {
check = true;
curentData.data.splice(i,1);
fs.writeFileSync(fName, JSON.stringify(curentData));
console.log("Removed: " + item);
sendList();
} // if
} // for
if (check === false) {
var time = Date.now();
item = JSON.parse(item);
item.timestamp = time
curentData.data.push(JSON.stringify(item));
fs.writeFileSync(fName, JSON.stringify(curentData));
console.log("Added: " + JSON.stringify(item));
sendList();
}  // if
} // else

} // function


// Sendet sobald eine aktualisierung vorliegt, die Liste mit den Rauminhalten an den Server
function sendList() {
var data = readFile();
data = JSON.stringify(data);
console.log('POST /roomList');

    var options = {
        host: VARIABLES.webserver.address,
        port: VARIABLES.webserver.port,
        path: '/roomList',
        method:'POST',
        headers:{
            accept:'application/json'
        }
    }

    var exReq = http.request(options, function(exRes){
        i
            exRes.on(data, function(chunk){
                var response = JSON.parse(chunk);

                res.end();
            });

    });
exReq.write(data);
    exReq.end();

}

// Sendet die IP des Minipcs bei Serverstart an den Server
function sendIP() {
var options = {
        host: VARIABLES.webserver.address,
        port: VARIABLES.webserver.port,
        path: '/ip',
        method:'POST',
        headers:{
            accept:'application/json'
        }
    }

var data = {
	host: VARIABLES.minipc.address,
	port: VARIABLES.minipc.port
	}

var statusCode;
// Sendet solange die IP an den Server bis diese ankommt und gespeichert wurde
while (statusCode != 200) {


    var exReq = http.request(options, function(exRes){
        i
            exRes.on(data, function(chunk){
                statusCode = exRes.statusCode;
            });

    });
	exReq.write(JSON.stringify(data));
    exReq.end();
console.log("Send IP Error");
}
console.log("IP Send");
}

// Exportiert die Funktionen in andere .js Dateien
module.exports                              = {
writeFile:    function(data){
return writeFile(data);
},
readFile:    function(){
return readFile();
},
getList: function() {
return getList();
},
updateList: function(item) {
return updateList(item);
},
sendIP: function() {
return sendIP();
}
}