### Inhalt
* Webserver der auf einem Minicomputer (Raspberry Pi) läuft
    - erkennt über RFID (simulliert) Gegentände die im Empfangsbereich der Antenne (simulliert) auftauchen
    - verarbeitet die erhaltenen Informationen aus dem RFID-Label (simulliert)
        - aktualisiert eine JSON-Liste auf dem Minicomputer mit dem aktuellen Equipment des Raumes
        - sendet die aktualisierte Liste per HTTP-Request an den Hauptserver
        - empfängt HTTP-Requests vom Hauptserver
        
### Anwendungslogik
* automatisches aktualisieren einer Liste mit Gegenständen die sich gerade im Raum befinden
        
### TODO (stand 07.01.2018)
* Node.js Server aufsetzen
* HTTP-Requests festlegen
* Liste in Datei schreiben
* Liste aus Datei lesen

### Verwendete Module/Biblotheken
* Express

### Verwendete Software
* Node.js

### Verwendete Hardware
* Raspberry Pi 3