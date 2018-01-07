### Inhalt
* Linked List, die wichtige Punkte innerhalb eines Gebäudes als Knoten festlegt
    - Knoten besitzen eine Gewichtung durch die der kürzeste Weg zwischen 2 Knoten berechnet werden kann
* Webserver der als Hauptserver fungiert und HTTP-Requests von Clients empfangen und an den Datenbankserver und den Minicomputer sowohl senden als auch empfangen kann
* Anbindung an einen Datenbansystem zur persistenten Datenspeicherung
        
### Anwendungslogik
* automatisches erstellen einer Liste mit freien Räumen die stetig aktualisiert wird
        
### TODO (stand 05.01.2018)
* Erweiterung der LinkedList bezüglich einiger Variablen Namen
* Code Optimierungen
* Webserver erstellen
* Datenbankserver erstellen
* Datenbanken aufsetzen
* Liste mit freien Räumen erstellen
* HTTP-Requests/Respons erstellen

### Verwendete Module/Biblotheken
* Express

### Verwendete Software
* Node.js
* Redis.io

### Verwendete Hardware
* -

### Installation
* zum Ausführen des Dijkstra-Algorithmus innerhalb der LinkedList die Datei 'main.js' mittels des Befehls: 'node main.js' ausführen
* Es müssen keine Module installiert werden
* Wenn die Anzahl der zur verfügung stehenden Räume geändert werden soll kann dies in der Datei 'main.js' ab Zeile 740 durchgeführt werden
* Es kann durch hinzufügen eines Raumes in das Array 'arrayWanted' ein weiterer Zielraum hinzugefügt werden
