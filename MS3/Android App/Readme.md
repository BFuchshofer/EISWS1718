### Inhalt
* Android Applikation die als UI des Systems für den einfachen Benutzer dient
* Automatisches Auslesen von Bluetooth Low Energy (BLE) Beacons und speichern dieser geordnet in eine Datei auf dem Endgerät
        
### Anwendungslogik
* Sortierung der erkannten Beacons in der Datei absteigend nach gemessener Entfernung um immer den aktuellsten Beacon (Standort) im Request an den Server senden zu können.
* Cleanup der veralteten Beacons in der Datei bei dem gespeicherte Beacons mit einem veralteten Zeitstempel gelöscht werden.
        

### Verwendete Module/Biblotheken
* [volley-http library](https://developer.android.com/training/volley/index.html)
* [Android Beacon Library](http://altbeacon.github.io/android-beacon-library/index.html)

### Verwendete Software
* [Android Studios](https://developer.android.com/studio/index.html)

### Verwendete Hardware
* Smartphone oder Tablet mit einem Android Betriebssystem