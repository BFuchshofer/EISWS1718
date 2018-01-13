var fs = require('fs');
var bodyParser = require('body-parser');
var jsonParser = bodyParser.json();
var curentRoom = './data/curentRoom.json';

function getList() {

return readFile();
}


function readFile() {
var data;
fName = curentRoom;
data = fs.readFileSync(fName, 'utf8');
console.log('Ergebnis: ' + data);
if (data != "") {
	return JSON.parse(data);
} else {
var tmp = [];
return tmp;
}
}

/*
* Speichern von mehreren Datensätzen hintereinander funktioniert nicht?
* JSON file sollte folgendes Format haben:
* {"data": {"id":1,"test":1},{"id":1,"test":1},{"id":1,"test":1} }
* beim schreiben sollen die bestehenden Informationen nicht überschrieben, sondern ergänzt werden
* Funktioniert über auslesen der Datei, schreiben in temporäre variable, pushen von neuen Infos in diese Variable, Variableninhalt in Datei speichern ?
*/

function writeFile(data) {
var testdata = data
fName = curentRoom;
var curentData = readFile();
console.log('Test: ' + JSON.stringify(curentData));
if (curentData.data != "") {
var tmp = {"data": {}};
for (var i = 0; i < curentData.data.length(); i++) {
tmp.data.push(curentData.data[i]);

}

	tmp.data.push(testdata);
	fs.writeFileSync(fName, JSON.stringify(tmp));
	console.log('Data saved: ' + JSON.stringify(tmp));
} else {

curentData.data.push(testdata);

	fs.writeFileSync(fName, JSON.stringify(curentData));
	console.log('Data saved: ' + JSON.stringify(curentData));
}
}


module.exports                              = {
writeFile:    function(data){
return writeFile(data);
},
readFile:    function(){
return readFile();
},
getList: function() {
return getList();
}
}