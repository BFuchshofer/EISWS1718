1. Installation
    1.1 Downloaden Sie Redis3.0 oder Redis3.2
        - Windows:          https://github.com/MicrosoftArchive/redis/releases          [Weiter bei 1.2]
    1.2 Installation Redis:
        1.2.1 Erstellen Sie die Ordner 'datenbank_events/redis/' und 'datenbank_room/redis/'
        1.2.2 Die .zip in den beiden Ordnern entpacken.

2. Starten der Datenbanken
    2.1 Datenbank für Veranstaltungen
        2.1.1 die im Ordner 'datenbank_events/redis/' befindliche 'redis-server.exe' mit dem Befehl 'redis-server --port 5679' starten.
            2.1.1a Sollte der Port bereits vergeben sein kann ein anderer verwendet werden. Dieser muss dann ebenfalls in der Datei 'datenbank_events/variables.json' unter dem Attribut 'db_port' angepasst werden.
    2.2 Datenbank für RäUm
        2.2.1 die im Ordner 'datebank_room/redis/' befindliche 'redis-server.exe' mit dem Befehl 'redis-server --port 5677' starten.
            2.2.1a Sollte der Port bereits vergeben sein kann ein anderer verwendet werden. Dieser muss dann ebenfalls in der Datei 'datenbank_room/variables.json' unter dem Attribut 'db_port' angepasst werden.

3. Starten der NodeJS Server
    3.1 Datenbankserver für Veranstaltungen:
        3.1.1 Im Ordner 'datenbank_events/' den Befehl 'npm install' ausführen um alle benötigten node Module zu installieren.
        3.1.2 Die im Ordner 'datenbank_events/' befindliche 'server.js' über den Befehl 'node server.js' ausführen
    3.2 Datenbankserver für Räume:
        3.2.1 Im Ordner 'datenbank_room/' den Befehl 'npm install' ausführen um alle benötigten node Module zu installieren.
        3.2.2 Die im Ordner 'datenbank_room/' befindliche 'server.js' über den Befehl 'node server.js' ausführen.
    3.3 Webserver:
        3.3.1 Im Ordner 'webserver/' den Befehl 'npm install' ausführen um alle benötigten node Module zu installieren.
        3.3.2 Die im Ordner 'webserver/' befindliche 'server.js' über den Befehl 'node server.js' ausführen.

4. Verbinden mit der Applikation
    4.1 In den Konsolen der Server wird die Adresse angezeigt über die der Server zu erreichen ist.
    4.2 Um mit der Applikation Anfragen an den Webserver zu senden muss die Adresse des Webservers mit dem Port 5669 in die Anwendung eingetragen werden.
