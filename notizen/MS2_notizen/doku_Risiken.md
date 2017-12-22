# Risiken
### Einleitung
Anhand der vorrangegangenen Recherche an Informationen zu einer möglichen Realisierung des Systems wurden erste [Risiken](Liste im Anhang?) identifiziert. Im Verlauf des Projektes wird diese Liste an Risiken bezüglich auf verwendete Technologien und neuen Erkentnissen stetig aktualisiert. Da noch keine Technologien feststehen die verwendet werden sollen, sind die Risiken aktuell nur auf das Projekt als ganzes bezogen. Sobald Technologien feststehen, und sich daraus neue Risiken ergeben, wird die Liste im Anhang vervollständigt.

### Erste Risiken (ARBEITSTITEL)

- Benutzer eines Raumes blockieren den Raum körperlich über die im System hinterlegte Zeitspanne hinaus.
- __Personenerkennung im Raum ermöglicht dynamische Überprüfung des Status:__
    - Exit:
        - Die Antwortzeit des Servers beträgt weniger als 10 Sekunden.
    - Fail:
        - Die Antwortzeit des Servers beträgt mehr als 10 Sekunden.
    - Fallback:
        - Funktionen kürzen um Antwortszeiten des Systems zu verringern.
        - Besserer Netzausbau für stabilere und verkürzte Kommunikation.

- Das finden eines freien Raumes dauert mit Hilfe der Anwendung länger als ohne.
- __Die benötigte Zeit zum finden eines freien Raumes ist kürzer wenn das System benutzt wird:__
    - Exit:
        - Zeit für die Raumsuche beschränkt sich auf max. 10 Sekunden.
    - Fail:
        - Zeit für die Raumsuche beträgt mehr als 10 Sekunden.
    - Fallback:
        - Funktionen im System kürzen um Antwortszeiten des Systems zu verringern.
        
Unser größtes Problem besteht darin, die Antwortzeiten des Systems so kurz zu halten, das der Benutzer mit dem System nicht länger braucht einen freien Raum zu finden als wenn er einfach durch ausprobieren die Räume in seiner Umgebung überprüft. Dazu muss natürlich erwähnt werden das die Zeit, die ein Benutzer benötigt um ohne unser System einen Raum zu finden, immer von den örtlichen Gegebenheiten abhängt, wie z.B. Länge der Flure, Laufwege zwischen zwei Räumen oder, wenn vorhanden, der Gang zu einer Anlaufstelle innerhalb der Lehreinrichtung die Informationen über freie Räume bietet.
* Exit Kriterium ist eingetreten
    - Die Zeit die man benötigt einen freien Raum zu finden liegt nach unseren Messungen deutlich unter der Maximalzeit (10 Sekunden). Dabei sind wir von einer optimalen Umgebung des Systems ausgegangen (gutes Internet, keine störenden Prozesse auf dem Endgerät). Dabei startet der Nutzer die Anwendung auf seinem Endgerät und bekommt nach einer Anfrage am System eine Raumnummer päsentiert. Er hat dann die Möglichkeit den Raum zu reservieren, was dem finden und sichern eines Raumes entspricht.
    - Risikominimierung durch möglichst wenig Interaktionsschritte für den Benutzer. 
  
  
- Es lässt sich nicht gewährleisten, dass ein Raum auch tatsächlich frei ist wenn dem Benutzer dieser ausgegeben wird.        
- __Vor dem ausgeben des Raumes an den Benutzer muss überprüft werden ob dieser bereits belegt ist:__
    - Exit:
        - Das System überprüft den Status des Raumes anhand einer Liste mit freien Räumen und gibt ihn aus falls dieser dort vorhanden ist.
    - Fail:
        - Nach einer Überprüfung des Raumes wird festgestellt das der Raum nicht verfügbar ist.
    - Fallback:
        - Es wird nach weiteren freien Räumen gesucht.
* Exit Kriterium ist eingetreten
    - Dadurch das immer eine Liste verarbeitet wird in der sich nur freie Räume befinden, und bei einer Änderung der Raumbelegungen diese sofort aktualisiert wird, ist nach unserem Ergebnis gewährleistet das nur ein Raum ausgegeben wird der auch tatsächlich frei ist. Um mehrere gleichzeitige Anfragen zu bearbeiten werden Semaphoren genutzt die einen Wechselseitigen Ausschluss beim Bearbeiten der Liste garantieren.


### Risiken bei der Aufgabenbestimmung (ARBEITSTITEL)

- Eine Filterung nach bestimmten Rauminhalten durch den Benutzer lässt sich nicht umsetzen.
- __Es besteht die Möglichkeit durch auswählen von Eigenschaften die Raumsuche einzuschränken:__
    - Exit:
        - Das System bietet eine Filtermethode an, aus der der Benutzer gewünschte Raumeigenschaften auswählen kann.
    - Fail:
        - Es existiert für den Benutzer keine Auswahlmöglichkeiten um die Raumsuche einzugrenzen.
    - Fallback:
        - Das System gibt anhand von Benutzerrollen einen Raum mit vordefinierten Eigenschaften aus.
* Exit Kriterium ist eingetreten
    - Die Auswahl von Filtern durch den Benutzer lässt sich unkompliziert über das Endgerät des Benutzers regeln. Er greift dabei auf eine Auswahl von Filtermöglichkeiten zuzück die beim absenden im Body des HTTP-Requests mitgesendet werden. 
        

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
* Exit Kriterium ist eingetreten
    - Durch die Verwendung der Android Beacon Library konnten wir ein Android Smartphone mit BLE Unterstützung zu einem sendenden Beacon umfunktionieren. Ein anderes Android Gerät konnte den künstlich erstellten Beacon erkennen und mitgelieferte Daten wie Entfernung auslesen.
        
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
* Exit Kriterium ist eingetreten
    - In einem Test haben wir mehrere Signalgeräte wie Geräte mit aktivem WLAN, Funknetzwerke, WLAN-Repeater und Bluetooth Komponenten in einem Raum positioniert und versucht den aktiven Beacon zu empfangen. Bei diesem Test funktionierte dies Problemlos, allerdings können wir keinen Test unter realistischen Bedingungen durchführen da uns dafür die benötigten Mittel in ausreichender Zahl wie z.B. voll funktionsfähige Beacons fehlen.
        
        
- Durch einen zu großen Abstand zu einem Beacon nimmt die Genauigkeit bei der Erkennung ab und ist nicht mehr zuverlässig anwendbar.
- __Die Erkennung von Beacons funktioniert in bis zu 10 Meter zuverlässig:__
     - Exit: 
        - Der Beacon ist in bis zu 10 Meter zuverlässig erkennbar.
    - Fail:
        - Der Beacon ist innerhalb von 10 Meter nicht erkennbar.
    - Fallback:
        - Signalreichweite der einzelnen Beacons erhöhen um einen größeren Bereich abzudecken.
        - Beacons in festen Abständen im Gebäude verteilen.
* Exit Kriterium ist eingetreten
    - Der simulierte Beacon mit dem Smartphone lies sich in mehreren Testdurchläufen bis zur einer durchschnittlichen Reichweite von 14 Metern erkennen. Befinden sich Hindernisse im Weg wie etwa Wände, war immer noch eine Erkennung in ca. 9 Metern Entfernung möglich. Da aber geplant ist diese Beacons in regelmäßigen Abständen anzubringen um eine ausreichende Signalabdeckung zu gewährleisten, gilt dieser Test als bestanden.

### Risiken bei der Laufwegbestimmung (ARBEITSTITEL)

- In der verketteten Liste lassen sich nicht mehrere Richtungen angeben in die ein Benutzer gehen kann.
- __Der Einsatz einer verketteten Liste ermöglicht eine Wegbestimmung innerhalb eines Gebäudes:__
     - Exit: 
        - Die Verkettete Liste deckt alle relevanten Räume ab und liefert anhand eines Algorithmus einen Raum der sich in der Nähe des Benutzers befindet. Dabei werden verschiedene Verbindungswege wie Treppenhäuser oder Fahrstühle berücksichtigt.
    - Fail:
        - Innerhalb der verketteten Liste lässt sich nicht in mehrere Richtungen gehen die äquivalent zu möglichen Wegen zwischen verschiedenen Punkten sind.
    - Fallback:
        - Manuelle Entfernungsbestimmung zwischen allen Räumen und markanten Punkten und das eintragen in einer Tabelle.
* Exit Kriterium ist eingetreten
    - Innerhalb einer verketteten Liste lassen sich nach unserer Überprüfung mehrere Wege angeben. Dabei lässt sich modellieren das von einem Raum mehrere Knoten abzweigen können. Ein Raumknoten kann also sowohl zu anderen Räumen als auch zusätzlich zu einem Treppenhaus führen um das Stockwerk zu wechseln.
        
        
### Risiken bei der Markierung von Gegenständen (ARBEITSTITEL)
- RFID eines Gegenstandes kann nicht erkannt und einem Raum zugeordnet werden.
- __Jeder Gegenstand kann eindeutig einem Raum bzw. einem Standort zugeordnet werden:__
     - Exit: 
        - Das Erkennen von Gegenständen über RFID funktioniert sowohl beim betreten als auch beim verlassen des Raumes.
    - Fail:
        - Das Erkennen von Gegenständen über RFID kann nicht unterscheiden zwischen betreten und verlassen des Raumes.
    - Fallback:
        - Über eine Überprüfung der aktuellen Rauminhalte kann erkannt werden ob ein Gegenstand den Raum betritt oder verlässt. Ist der erkannte Gegenstand Teil des Rauminventars, kann das System davon ausgehen das der Gegenstand den Raum verlässt. Ist er nicht in der Liste vorhanden weis das System das der Gegenstand den Raum gerade betritt.
* Fail Kriterium ist eingetreten
    - Eine automatische Erkennung über ein Lesegerät ist nicht möglich da nicht erkannt werden kann ob ein Gegenstand den Raum gerade betritt oder verlässt. Um das Problem zu lösen tritt der beschriebene Fallback ein, womit eine Raumzugehörigkeit gewährleistet werden kann. Um zu verhindern das ein Gegenstand aus dem System verschwindet, wird eine Liste auf dem Server erstellt die Auskunft über den Status eines Gegenstandes gibt. Daraus ist ersichtlich ob ein Gegenstand gerade aus einem Raum aus- oder eingetragen wurde. Wurde er ausgetragen, verbleibt die Gegenstands ID solange auf dieser Liste bis der Gegenstand in einem anderen Raum registriert wird. Sollte ein Gegenstand versehentlich in zwei aufeinanderfolgenden Räumen eingetragen werden, wird immer der letzte Raum in dem der Gegenstand erkannt wurde als aktueller Raum gewählt und in das Rauminventar eingetragen. Der Server erkennt die Dopplung von Gegenständen und weist den vorletzten Raum an diesen Gegenstand aus seinem Rauminventar zu entfernen.


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
* Exit Kriterium ist eingetreten
    - Durch die Echtzeit Objekterkennung (You only look once) war es uns möglich in einem Testfall mehrere Personen in einem Raum zu identifizieren. Dazu wurde ein Bild ausgewertet das mit einer Webcam gemacht wurde. Dadurch konnte die Anzahl der Personen im Raum ermittelt werden.
















