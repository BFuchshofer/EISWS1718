### Inhalt
* LinkedList, die wichtige Punkte innerhalb eines Gebäudes als Knoten festlegt
    - Knoten besitzen eine Gewichtung durch die der kürzeste Weg zwischen 2 Knoten berechnet werden kann
* Webserver der als Hauptserver fungiert und HTTP-Requests von Clients empfangen und an den Datenbankserver und den Minicomputer sowohl senden als auch empfangen kann
* Anbindung an einen Datenbansystem zur persistenten Datenspeicherung

### Anwendungslogik
* automatisches erstellen einer Liste mit freien Räumen die stetig aktualisiert wird

### TODO (stand 26.01.2018)
* Code Cleanup
* Installations Anleitung

### Verwendete Module/Biblotheken
* express
* path
* http
* body-parser
* ip
* bluebird
* redis
* randomstring
* semaphore

### Verwendete Software
* Node.js
* Redis.io

### Verwendete Hardware
* -

### Installation
* zum Ausführen des Webservers müssen die oben genannten Module über npm installiert werden.
* dann kann der Webserver mittels des Befehls "node server.js" gestartet werden.
* zum starten des Datenbankservers muss Redis auf dem System installiert sein und 
    2 Datenbanken mit den Ports 5660 und 5661 gestartet werden. Der port kann beliebig verändert werden.
    Dies muss allerdings in den Dateien webserver.json und databaseserver.json eingetragen werden.
* In den zuvor aufgezählten Json Dateien muss auch die IP adresse des Webservers eingetragen werden.
    Der Datenbankserver sendet seine IP automatisch beim Start an den Webserver. Daraufhin werden 
    ebenfalls die Testdaten generiert.
* Wenn die beiden Datenbanken gestartet sind und ebenfalls der Webserver online ist
    kann nun der Datenbankserver ebenfalls über den Befehl "node server.js" gestartet werden.
