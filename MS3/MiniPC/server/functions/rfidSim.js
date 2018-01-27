var fs = require('fs');
var testData = './data/testData.json';
var data = readFile();
var dataLength = data.data.length;
var index;
var random;
const timer;


// Startet die Simulation der RFID Erkennung
function startSim() {
    console.log("RFID simulation started");
    dataLength = data.data.length;
    console.log("DataLength: " + dataLength);
    //console.log("Data: " + JSON.stringify(data));
    sendSim();
}

// Sendet zufällig im Intervall 1-10 Sekunden einen zufälligen Eintrag aus der Test Liste an die Update Funktion
function sendSim() {
    random = Math.floor((Math.random() * 10) +1)*1000; //Zufällige Zeit zwischen 1000-10000 Millisekunden
    index = Math.floor((Math.random() * dataLength));
    FUNCTIONS.updateList(JSON.stringify(data.data[index]));
    timer = setTimeout(sendSim, random, "");
}


// Stopt die Simulation der RFID Erkennung
function stopSim() {
    clearTimeout(timer);
}

// Liest die Testdaten aus dem Testfile aus
function readFile() {
    var data = fs.readFileSync(testData, 'utf8');
    if (data != "") {
	   return JSON.parse(data);
    } else {
        return data;
    }
}



// Exportiert die Funktionen in andere .js Dateien
module.exports = {
    startSim: function() {
        return startSim();
    },
    stopSim: function() {
        return stopSim();
    },
    updateList: function(data){
        return updateList(data);
    }
}

