### Inhalt
* LinkedList, die wichtige Punkte innerhalb eines Gebäudes als Knoten festlegt
    - Knoten besitzen eine Gewichtung durch die der kürzeste Weg zwischen 2 Knoten berechnet werden kann
* Webserver der als Hauptserver fungiert und HTTP-Requests von Clients empfangen und an den Datenbankserver und den Minicomputer sowohl senden als auch empfangen kann
* Anbindung an einen Datenbansystem zur persistenten Datenspeicherung

### Anwendungslogik
* automatisches erstellen einer Liste mit freien Räumen die stetig aktualisiert wird

### TODO (stand 05.01.2018)
* Erweiterung der LinkedList bezüglich einiger Variablen Namen
* Speicherung der LinkedList bzw. Neugenerierung bei Serverstart
* Code Optimierungen
* Webserver erweitern
* Datenbankserver erstellen
* Datenbanken aufsetzen
* Liste mit freien Räumen erstellen
* HTTP-Requests/Respons erstellen

### Verwendete Module/Biblotheken
* Express
* path
* http
* body-parser
* querystring
* ip

### Verwendete Software
* Node.js
* Redis.io

### Verwendete Hardware
* -

### Installation
* zum Ausführen des Webservers müssen die oben genannten Module über npm installiert werden.
* dann kann der Webserver mittels des Befehls "node server.js" gestartet werden.
* zum Abrufen des nächstgelegenen freien Raumes auf Basis einer vorgegebenen Position
    kann über einen REST-Client über die in der Serverkonsole angezeigte IP / Adresse
    und den Pfad '/vorschlag' eine RESPONSE in der form "RAUM: ...." erhalten werden.
