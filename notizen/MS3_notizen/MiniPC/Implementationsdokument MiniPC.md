# Implementationsdokumentation

## Mini-PC (Prototyp)
### Systemvorraussetzungen
* Raspberry Pi 3
* Raspbian Betriebssystem
* Internetzugang
* diverse Bibliotheken/Module (s.u.)

### Installation
* Installieren Sie das Raspbian Betriebssystem auf dem Raspberry Pi
* Öffnen Sie die Konsole und führen ein "sudo apt-get update" aus um die vorhandenen Bibliotheken auf den neuesten Stand zu bringen 
* Laden Sie sich den Ordner "MiniPC" auf den Raspberry Pi
* Navigieren Sie in das Verzeichnis "MiniPC/server"
* Öffnen Sie die Konsole in diesem Ordner und geben Sie den Befehl "sudo npm install" ein um die Module zu installieren. 

### Einrichtung des Webservers
* Öffnen Sie die Datei "variables.json" und passen Sie ggf. unter "webserver" den Port und die Adresse (IP) des Webservers an. Diese finden Sie in der Konsole des Webservers.
* Passen Sie in der selben Datei ggf. den Port von "minipc" an.
* In der selben Datei befindet sich die Raumnummer in der sich der Raspberry Pi befindet. Diese können Sie ggf. ebenfalls anpassen.
* In der Datei "testData.json" im Verzeichnis "/server/data" befinden sich Testdaten die Sie bei Bedarf verändern können.

### Nutzung des Webservers
* Starten Sie den Server mit "sudo node server" in der Konsole.
* Der Server simuliert nun das Betreten und Verlassen von Gegenständen (vorhanden in testData.json) aus, bzw. in den Raum.
* Gleichzeitig wird ein Beacon simuliert der als Major ID die Raumnummer sendet. Personen die sich in der Nähe des Raspberry Pi befinden, werden diesen Beacon über die App erkennen und als Standort bei Requests an den Webserver verwenden.



### Sonstiges
* Wenn Sie schneller und einfacher die Beacons simulieren wollen, empfehlen wir die Android App "Locate Beacon" von "Radius Networks, Inc" die Sie im Google Play Store kostenlos herunterladen können. Mit ihr können Sie ihr Endgerät als Beacon simulieren, oder Beacon Signale empfangen. Achten Sie dabei beim senden darauf, dass in unserem System nur das "iBeacon-Format" unterstützt wird! Die ID1 ist die eindeutige Identifikation (UUID) des simulierten Beacons, und sollte in unserem Prototyp nur verändert werden, wenn Sie mehrere Beacons mit mehreren Endgeräten simulieren wollen. Unter ID2 tragen Sie bitte die Raumnummer ein, an dem Sie ihren Standort simulieren möchten. Benutzen Sie dabei die Raumnummern aus [der Liste]().