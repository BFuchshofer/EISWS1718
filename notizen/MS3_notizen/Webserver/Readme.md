### Inhalt
* LinkedList, die wichtige Punkte innerhalb eines Gebäudes als Knoten festlegt
    - Knoten besitzen eine Gewichtung durch die der kürzeste Weg zwischen 2 Knoten berechnet werden kann
* Webserver der als Hauptserver fungiert und HTTP-Requests von Clients empfangen und an den Datenbankserver und den Minicomputer sowohl senden als auch empfangen kann
* Anbindung an einen Datenbansystem zur persistenten Datenspeicherung

### Anwendungslogik
* automatisches erstellen einer Liste mit freien Räumen die stetig aktualisiert wird


### Verwendete Module/Biblotheken
* express
* path
* http
* body-parser
* ip
* bluebird
* redis
* semaphore

### Verwendete Software
* Node.js
* Redis.io


### Installation
* Laden Sie den Ordner __Webserver/__ auf das System
* Öffnen Sie die Unterordner __server/__ und __database/__
* Führen Sie in beiden Unterordnern den Befehl *npm install* aus.
* Installieren Sie Redis auf ihrem System.
### Einrichtung
#### Webserver
* Sie befinden sich im Ordner __Webserver/server/__
* Öffnen Sie den Unterordner __cfg/__
* Setzen Sie in der Datei __webserver.json__ unter dem Feld *port* den Port den Sie für den
Webserver verwenden wollen.
* Geben Sie die IP-Adresse des Systems auf dem der Webserver läuft unter dem Feld *ip*, und
*address* ein.
* Geben Sie die IP-Adresse und den Port des Datenbankservers in den Feldern *database.addr*,
*database.ip* und *database.port* ein.
* In der Datei __Webserver/server/util/test_data.json__ befinden sich Testdaten die Sie bei
Bedarf verändern können.
#### Datenbankserver
* Sie befinden sich im Ordner __Webserver/database/__
* Öffnen Sie den Unterordner __cfg/__
* Setzen Sie in der Datei __databaseserver.json__ in dem Feld *port* den Port und in dem Feld *ip*
die IP-Adresse des Datenbankservers ein
* Geben Sie die IP-Adresse und den Port des Webservers in den Feldern *webserver.addr*,
*webserver.ip* und *webserver.port* ein
* Danach geben Sie die für Ports die sie für die beiden Datenbanken verwenden wollen in
den Feldern *db_room.port* und *db_veranstaltung.port* ein
* In der Datei __Webserver/database/util/test_data.json__ befinden sich Testdaten die Sie bei
Bedarf verändern können.


### Verfügbare Testdaten:

Hier sind die verfügbaren Räume und Beacons aufgelistet.
Die Tabelle der Räume enthält zusätzlich die für die Filter wichtigen Informationen.
Die Räume mit dem Typ Sonstiges sind im System enthalten, können aber nicht angegeben werden, da diese zum Beispiel Büroräume o.Ä. simulieren sollen.

#### Räume:

| Raumnummer | Typ    | Größe | Tische | Stühle | Blackboards | Whiteboards | Beamer |
|:-----------|--------|-------|--------|--------|-------------|-------------|-------:|
| 2105 | Arbeitsraum | 10 | 10 | 10 | 3 | 2 | 1 |
| 2106 | Arbeitsraum | 20 | 20 | 20 | 2 | 1 | 1 |
| 2114 | Seminar | 36 | 36 | 36 | 2 | 0 | 1 |
| 2115 | Sonstiges | 0 | 0 | 0 | 2 | 0 | 1 |
| 2200 | Seminar | 25 | 25 | 25 | 2 | 0 | 0 |
| 2201 | Seminar | 30 | 30 | 30 | 0 | 2 | 1 |
| 2202 | Hörsaal | 50 | 50 | 50 | 3 | 1 | 1 |
| 2203 | Seminar | 10 | 10 | 10 | 1 | 0 | 1 |
| 2204 | Hörsaal | 30 | 30 | 30 | 1 | 0 | 1 |
| 2205 | Hörsaal | 30 | 30 | 30 | 2 | 0 | 1 |
| 2206 | Arbeitsraum | 30 | 30 | 30 | 1 | 1 | 0 |
| 3103 | Arbeitsraum | 20 | 20 | 20 | 1 | 1 | 0 |
| 3104 | Seminar | 48 | 48 | 48 | 1 | 0 | 1 |
| 3105 | Arbeitsraum | 5 | 5 | 5 | 0 | 0 | 0 |
| 3114 | Hörsaal | 80 | 80 | 80 | 4 | 2 | 1 |
| 3200 | Sonstiges | 0 | 0 | 0 | 2 | 0 | 0 |
| 3201 | Hörsaal | 50 | 50 | 50 | 2 | 1 | 1 |
| 3202 | Sonstiges | 0 | 0 | 0 | 2 | 0 | 0 |
| 3203 | Sonstiges | 0 | 0 | 0 | 2 | 0 | 0 |
| 3204 | Sonstiges | 0 | 0 | 0 | 2 | 0 | 0 |
| 3205 | Seminar | 35 | 35 | 35 | 0 | 4 | 1 |
| 3206 | Arbeitsraum | 20 | 20 | 20 | 0 | 0 | 0 |
| 3207 | Sonstiges | 0 | 0 | 0 | 2 | 0 | 0 |
| 3208 | Hörsaal | 40 | 40 | 40 | 2 | 1 | 1 |


#### Beacons:

| BeaconID | Zugehörig zu: |
|:---------|--------------:|
| t200a | Treppe T200A |
| t200b | Treppe T200B |
| t202 | Treppe T202 |
| t204 | Treppe T204 |
| t300a | Treppe T300A |
| t300b | Treppe T300B |
| t302 | Treppe T302 |
| t304 | Treppe T304 |
| 2105 | Raum 2105 |
| 2106 | Raum 2106 |
| 2114 | Raum 2114 |
| 2115 | Raum 2115 |
| 2200 | Raum 2200 |
| 2201 | Raum 2201 |
| 2202 | Raum 2202 |
| 2203 | Raum 2203 |
| 2204 | Raum 2204 |
| 2205 | Raum 2205 |
| 2206 | Raum 2206 |
| 3103 | Raum 3103 |
| 3104 | Raum 3104 |
| 3105 | Raum 3105 |
| 3114 | Raum 3114 |
| 3200 | Raum 3200 |
| 3201 | Raum 3201 |
| 3202 | Raum 3202 |
| 3203 | Raum 3203 |
| 3204 | Raum 3204 |
| 3205 | Raum 3205 |
| 3206 | Raum 3206 |
| 3207 | Raum 3207 |
| 3208 | Raum 3208 |
| g12 | Ein- / Ausgang / Gebäudeübergang Gebäude 1 Etage 2 |
| g22 | Gebäude 2 Etage 2 |
| g13 | Gebäude 1 Etage 3 |
| g23 | Gebäude 2 Etage 3 |
| a32 | Aufzug 3 Etage 2 |
| a33 | Aufzug 3 Etage 3 |
