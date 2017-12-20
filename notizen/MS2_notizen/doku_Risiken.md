# Risiken
### Einleitung
Anhand der vorrangegangenen Recherche an Informationen zu einer möglichen Realisierung des Systems wurden erste [Risiken](Liste im Anhang?) identifiziert. Im Verlauf des Projektes wird diese Liste an Risiken bezüglich auf verwendete Technologien und neuen Erkentnissen stetig aktualisiert. Da noch keine Technologien feststehen die verwendet werden sollen, sind die Risiken aktuell nur auf das Projekt als ganzes bezogen. Sobald Technologien feststehen, und sich daraus neue Risiken ergeben, wird die Liste im Anhang vervollständigt.

### Erste Risiken (ARBEITSTITEL)
<!--
- Benutzer eines Raumes blockieren den Raum körperlich über die im System hinterlegte Zeitspanne hinaus.
- __Personenerkennung im Raum ermöglicht dynamische Überprüfung des Status:__
    - Exit:
        - Die Antwortzeit des Servers beträgt weniger als 10 Sekunden.
    - Fail:
        - Die Antwortzeit des Servers beträgt mehr als 10 Sekunden.
    - Fallback:
        - Funktionen kürzen um Antwortszeiten des Systems zu verringern.
        - Besserer Netzausbau für stabilere und verkürzte Kommunikation.
-->

- Das finden eines freien Raumes dauert mit Hilfe der Anwendung länger als ohne.
- __Die benötigte Zeit zum finden eines freien Raumes ist kürzer wenn das System benutzt wird:__
    - Exit:
        - Zeit für die Raumsuche beschränkt sich auf max. 10 Sekunden.
    - Fail:
        - Zeit für die Raumsuche beträgt mehr als 10 Sekunden.
    - Fallback:
        - Funktionen im System kürzen um Antwortszeiten des Systems zu verringern.
  
  
- Es lässt sich nicht gewährleisten, dass ein Raum auch tatsächlich frei ist wenn dem Benutzer dieser ausgegeben wird.        
- __Vor dem ausgeben des Raumes an den Benutzer muss überprüft werden ob dieser bereits belegt ist:__
    - Exit:
        - Das System überprüft den Status des Raumes anhand einer Liste mit freien Räumen und gibt ihn aus falls dieser dort vorhanden ist.
    - Fail:
        - Nach einer Überprüfung des Raumes wird festgestellt das der Raum nicht verfügbar ist.
    - Fallback:
        - Es wird nach weiteren freien Räumen gesucht.


### Risiken bei der Aufgabenbestimmung (ARBEITSTITEL)

- Eine Filterung nach bestimmten Rauminhalten durch den Benutzer lässt sich nicht umsetzen.
- __Es besteht die Möglichkeit durch auswählen von Eigenschaften die Raumsuche einzuschränken:__
    - Exit:
        - Das System bietet eine Filtermethode an, aus der der Benutzer gewünschte Raumeigenschaften auswählen kann.
    - Fail:
        - Es existiert für den Benutzer keine Auswahlmöglichkeiten um die Raumsuche einzugrenzen.
    - Fallback:
        - Das System gibt anhand von Benutzerrollen einen Raum mit vordefinierten Eigenschaften aus.
        
        
- Die automatische Aufhebung des Vorschlags/Reservierung/Buchung eines Raumes kann nicht automatisch erfolgen.
- __Nach Ablauf einer bestimmten Zeit wird der Status des Raumes automatisch von "Nicht verfügbar" auf "Verfügbar" gesetzt:__
     - Exit: 
        - Automatische Aufhebung lässt sich realisieren.
    - Fail:
        - Funktion zur automatischen Aufhebung pausiert das System da immer auf Aktualität geprüft werden muss.
        - Das System kann nicht automatisiert prüfen ob eine Aufhebung vorgenommen werden muss.
    - Fallback:
        - Bei Bedarf wird ein Countdown gestartet der im Hintergrund abläuft und bei Beendigung die Aufhebung einleitet.

### Risiken bei der Standortbestimmung (ARBEITSTITEL)

- Automatische Standortbestimmung des Benutzers innerhalb des Anwendungsfeldes ist nicht möglich.
- __Der Standort des Benutzers wird über Bluetooth Beacons innerhalb des Gebäudes ermittelt:__
     - Exit: 
        - Das Endgerät des Benutzers erkennt Beacons in der Nähe die über das Auslesen der ID zur Standortbestimmung genutzt werden können.
    - Fail:
        - Das Endgerät des Benutzers kann technisch keine Bluetooth Signale der Beacons empfangen.
        - Das Endgerät des Benutzers kann in seiner Umgebung keine Bluetooth Signale der Beacons empfangen.
    - Fallback:
        - Standortbestimmung durch manuelle Eingabe des Benutzers ermöglichen.
        - Pseudostandort festlegen der als aktueller Standort genutzt wird. Beispielsweise der Haupteingang eines Gebäudes.
        
        
- Inteferenzen stören die Standortbestimmung durch die Bluetooth Beacons.
- __Die Standortbestimmung über Bluetooth Beacons erfolgt ohne Inteferenzen:__
     - Exit: 
        - Bluetooth Beacons werden erkannt und können ausgelesen werden.
    - Fail:
        - Zuviele Bluetooth Signale stören sich gegenseitig bei der Übertragung.
        - Bluetooth Signale werden durch Hindernisse absorbiert oder vermindert.
    - Fallback:
        - Signalreichweite der einzelnen Beacons reduzieren so das eine maximale Anzahl an Signalen in einem Bereich gewährleistet wird.
        - Bluetooth Beacons gut sichtbar und in ausreichender Entfernung zu Hindernissen anbringen. Beispielsweise über den Köpfen von Personen oder an der Decke.
        
        
- Abstand zwischen 2 Räumen/Markern ist zu groß um eine effektive Standortbestimmung des Benutzers durchführen zu können.
- __Jeder Bereich im Gebäude ist durch mindestens ein Bluetooth Beacon Signal abgedeckt:__
     - Exit: 
        - Der Standort des Benutzers ist überall im Gebäude auslesbar.
    - Fail:
        - Es gibt Positionen im Gebäude die nicht durch Bluetooth Signale abgedeckt sind.
    - Fallback:
        - Signalreichweite der einzelnen Beacons erhöhen um einen größeren Bereich abzudecken.
        - Beacons in festen Abständen im Gebäude verteilen.

### Risiken bei der Laufwegbestimmung (ARBEITSTITEL)

- Dijkstra-Algorithmus/Verkettete Liste lässt sich nicht implementieren
- __Mit einer verketteten Liste wird der Dijkstra-Algorithmus simuliert und ein Raum für den Benutzer bestimmt:__
     - Exit: 
        - Die Verkettete Liste deckt alle relevanten Räume ab und liefert anhand eines Algorithmus einen Raum der sich in der Nähe des Benutzers befindet.
    - Fail:
        - Durch die Kantengewichtung entsteht ein unrealistisches Ergebnis.
        - Die verkettete Liste lässt sich nicht realisieren.
    - Fallback:
        - Vorabberechnung aller Entfernungen zwischen allen Räumen und markanten Punkten.
        
        
### Risiken bei der Markierung von Gegenständen (ARBEITSTITEL)
- RFID eines Gegenstandes wird beim verlassen des Raumes nicht erkannt.
    - wenn das Item dann aber in einem anderen Raum registriert wird muss er trotzdem aktuallisiert werden (aus alten Raum austragen, in neuen Raum eintragen).
- RFID eines Gegenstandes wird fälschlicherweise aus einem Raum ausgetragen obwohl er sich noch darin befindet (Gegenstand kommt in die Nähe des RFID Sensors).
- Equipment im Raum wird aus diesem entfernt, was nicht im System registriert wird.


### Risiken bei der Personenerkennung (ARBEITSTITEL)
- Personenerkennung im Raum ist nicht effektiv realisierbar.
- __Durch Bildanalyse wird eine Personenerkennung innerhalb von Räumen realisiert:__
     - Exit: 
        - Durch Analyse eines Bildes aus dem inneren eines Raumes kann die Anzahl an Personen ermittelt werden.
    - Fail:
        - Es können keine Personen auf dem Bild erkannt werden.
        - Die Anzahl an Personen im Raum ist nicht ersichtlich.
    - Fallback:
        - Personenerkennung durch manuelles ein- bzw. austragen durch den Benutzer.

### Risiken bei der Kommunikation (ARBEITSTITEL)
- Bluetooth Signale behindern sich gegenseitig im Ablauf
- Es kann auf Clientseite keine Verbindung mit dem Minicomputer im Raum aufgebaut werden.


### Risiken bei den Technologien (ARBEITSTITEL)
- Endgerät des Benutzers unterstützt kein Bluetooth.

<!-- auflistung von Risiken -->
     
        
### Fazit - Risiken

<!--
- Benutzer eines Raumes blockieren den Raum körperlich über die im System hinterlegte Zeitspanne hinaus.
- Das System liefert einen freien Raum nicht in absehbarer Zeit.
- Das Finden eines freien Raumes dauert mit Hilfe der Anwendung länger als ohne.
- Es lässt sich nicht gewährleisten, dass ein Raum auch tatsächlich frei ist wenn dem Benutzer dieser ausgegeben wird. (andere Benutzer blockieren den Raum z.b. weil er nicht abgeschlossen ist)
- Eine Filterung nach bestimmten Rauminhalten durch den Benutzer lässt sich nicht umsetzen.
- Die automatische Aufhebung des Vorschlags/Reservierung/Buchung eines Raumes kann nicht automatisch erfolgen.
- Automatische Standortbestimmung des Benutzers innerhalb des Anwendungsfeldes ist nicht möglich.
    - technisch nicht möglich
    - Datenschutztechnisch nicht möglich
- Inteferenzen stören die Standortbestimmung durch die Beacons
- Abstand zwischen 2 Räumen/Markern ist zu groß um eine effektive Standortbestimmung des Benutzers durchführen zu können.
- Dijkstra-Algorithmus/Verkettete Liste lässt sich nicht implementieren
- Personenerkennung im Raum durch YOLO ist nicht effektiv realisierbar.
- Bluetooth Signale behindern sich gegenseitig im Ablauf
- Es kann auf Clientseite keine Verbindung mit dem Minicomputer im Raum aufgebaut werden.
- RFID eines Gegenstandes wird beim verlassen des Raumes nicht erkannt.
    - wenn das Item dann aber in einem anderen Raum registriert wird muss er trotzdem aktuallisiert werden (aus alten Raum austragen, in neuen Raum eintragen).
- RFID eines Gegenstandes wird fälschlicherweise aus einem Raum ausgetragen obwohl er sich noch darin befindet (Gegenstand kommt in die Nähe des RFID Sensors).
- Equipment im Raum wird aus diesem entfernt, was nicht im System registriert wird.
- Endgerät des Benutzers unterstützt kein Bluetooth.
-->














