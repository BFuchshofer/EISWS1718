var fs = require('fs');
var bodyParser = require('body-parser');
var jsonParser = bodyParser.json();
var curentRoom = './data/curentRoom.json';

var testString = "id"; // abändern in irgendeinen Key der im Array curentData (curentRoom) vorkommt

function getList() {
var data = readFile();
return data;
}


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


// data = {"key1":"value1", "key2":"value2", ...}
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

// Reagiert auf Meldungen durch den RFID Scanner und aktualisiert die Liste mit Rauminhalten

function updateList(item) {
var curentData = readFile();

if (curentData.data.length == 0) {

curentData.data.push(item);
fs.writeFileSync(fName, JSON.stringify(curentData));
console.log("Added First: " + item);

} else {
var check = false;
for (var i = 0; i < curentData.data.length; i++) {

if (new String(JSON.stringify(item)).valueOf() == new String(JSON.stringify(curentData.data[i])).valueOf()) {
check = true;
//delete curentData.data[i];
curentData.data.splice(i,1);
fs.writeFileSync(fName, JSON.stringify(curentData));
console.log("Removed: " + item);
} // if
} // for
if (check === false) {
curentData.data.push(item);
fs.writeFileSync(fName, JSON.stringify(curentData));
console.log("Added: " + item);
}  // if
} // else

} // function


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
}
}