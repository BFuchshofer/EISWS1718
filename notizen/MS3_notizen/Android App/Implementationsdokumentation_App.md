# Implementationsdokumentation

## Android App (Prototyp)
### Systemvorraussetzungen
* Smartphone oder Tablet mit einem Android Betriebssystem
* mindestens Android Version ???
* Bluetooth 4.0 Unterstützung
* Internetzugang
* Mindestens 10 MB Platz im internen Speicher für die Installation der App

### Installation
* Aktivieren Sie in den Einstellungen auf Ihrem Endgerät die Funktion "Aus unbekannten Quellen zulassen", damit diese App über andere Wege als über den Google Play Store installiert werden kann.
* Verbinden Sie Ihr Endgerät mit dem Computer.
* Erlauben Sie den Zugriff auf Gerätedaten über MTP.
* Navigieren Sie auf ihrem Computer in den Datenspeicher des Endgerätes.
* Begeben Sie sich in ein beliebiges Verzeichnis, z.B. den Download-Ordner, und kopieren sie die Roomify.apk in dieses Verzeichnis.
* Ist der Kopiervorgang abgeschloßen, wechseln sie auf Ihr Endgerät.
* Navigieren Sie in ihr File-Directory ("Eigene Dateien") und wechseln Sie in den Ordner in den Sie die Roomify.apk kopiert haben.
* Klicken Sie auf das Icon der App und installieren sie diese.
* Ist die Installation abgeschloßen, können Sie die App nutzen.

### Einrichtung der App
* Stellen Sie sicher das Sie die Bluetooth Funktion Ihres Endgerätes eingeschaltet haben.
* Überprüfen Sie, ob Sie sich im selben Netzwerk wie der Server befinden.
* Beim ersten Start werden Sie dazu aufgefordert Ihre E-Mail Adresse und die Server-Adresse einzugeben.
* Die E-Mail Adresse dient dabei zur Identifikation des Users und der mit ihm verbundenen Anfragen.
* Die Server-Adresse dient dazu eine Verbindung mit dem Server herstellen zu können. So kann die Adresse dynamisch verändert werden, falls dies nötig sein sollte.
* Tragen Sie eine E-Mail Adresse ihrer Wahl. Damit werden Ihre Anfragen an den Server verknüpft werden, so das Sie immer nur eine Anfrage gleichzeitig tätigen können.
* Tragen sie die Server-Adresse ein. Diese finden Sie beim Start des Webservers in der Konsole angegeben.

### Nutzung der App
* Sobald Sie sich verifiziert haben und im Startmenü angekommen sind, wird im Hintergrund die Beacon-Suche gestartet um ihren Standort zu bestimmen.
* Dieser Standort wird in Form der erkannten Beacon Major ID, die einen Knotenpunkt im System symbolisiert, bei einem Request an den Server mitgesendet.
* Die Funktion einen einzelnen Raum zu suchen, wurde im Startmenü vollständig modelliert und kann über den Button "EINZELNEN RAUM SUCHEN" aufgerufen werden.
* Sollten Sie Änderungen an der Server- oder E-Mail Adresse vornehmen müssen, können Sie dies über den "EINSTELLUNGEN"-Button tun.