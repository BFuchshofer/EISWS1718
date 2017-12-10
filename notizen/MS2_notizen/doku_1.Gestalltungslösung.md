# 1. Gestalltungslösung

## Einleitung
### Einschränkungen/Annahmen
* eventuell müssen alle Räume von außen automatisch Auf-/Abschließbar sein um eine effektive Kontrolle zu gewährleisten

## Komponenten des Systems
* Webserver 
* Datenbankserver
* Datenbank_1 (regulärer Veranstalltungsplan)
* Datenbank_2 (dynamischer Veranstalltungsplan)
* Minicomputer in jedem Raum (mit Bluetooth)
* Bluetooth (BLE) Beacon an jeder Raumtür
* Anwendung auf Endgerät des Benutzers


## Architektur des Systems
#### Deskriptiv <!-- oder deskriptiv vor den gestalltungslösungen? ändert sich ja nicht -->
![deskriptiv Architekturmodell](https://github.com/eXeLuis/EISWS1718FuchshoferFonsecaLuis/tree/master/notizen/MS2_notizen/imgs/deskriptiv_architektur.png)  
#### Präskriptiv
![präskiptives Architekturmodell](https://github.com/eXeLuis/EISWS1718FuchshoferFonsecaLuis/blob/master/notizen/MS2_notizen/imgs/pr%C3%A4skriptiv_architektur.PNG)  

## Kommunikation des Systems
### Kommunikation der Komponenten
#### Deskriptiv <!-- oder deskriptiv vor den gestalltungslösungen? ändert sich ja nicht -->
![deskriptives Kommunikationsmodell](https://github.com/eXeLuis/EISWS1718FuchshoferFonsecaLuis/blob/master/notizen/MS2_notizen/imgs/deskriptiv_kommunikation.PNG)  
#### Präskriptiv
![präskiptives Kommunikationsmodell](https://github.com/eXeLuis/EISWS1718FuchshoferFonsecaLuis/blob/master/notizen/MS2_notizen/imgs/kommunikationsdiagramm_v3.png)  
### Kommunikation mit dem Benutzer
#### Deskriptiv <!-- oder deskriptiv vor den gestalltungslösungen? ändert sich ja nicht -->
#### Präskriptiv


## Datenstruktur/Informationen
* Benutzerinformationen
    - (Lehreinrichtungs )Email
    - Status im Bezug auf die Lehreinrichtung
        - Zugangsberechtigungen
* Rauminformationen
    - Raumnummer/RaumID
    - Gebäude
    - Gebäudetrakt
    - Stockwerk
    - Gang
    - Raumtyp (Präsentation/Stilles arbeiten/Gruppenarbeit)
    - Raumgröße (in Bezug auf max. Personenanzahl)
    - Rauminhalte
        - Tische
            - Anzahl
            - fest
            - flexibel
        - Stühle
            - Anzahl
            - fest
            - flexibel
        - Stuhltisch
            - Anzahl
            - fest
            - flexibel
        - Beamer
            - Anzahl
            - fest
            - flexibel
        - Computer
            - Anzahl
            - fest
            - flexibel
        - Whiteboard
            - Anzahl
            - fest
            - flexibel
        - Smartboard
            - Anzahl
            - fest
            - flexibel
        - Tafel
            - Anzahl
            - fest
            - flexibel
        - OHP
            - Anzahl
            - fest
            - flexibel
        - Fernseher
            - Anzahl
            - fest
            - flexibel
        - Boxen
            - Anzahl
            - fest
            - flexibel
        - etc. (je nach Lehreinrichtung variabel)
            - z.B. Spezielles Equipment (Kamera/Tonstudio/Greenscreen/Mischer/besondere Gerätschaften)
    - Max. Bestuhlung
* Sonstige Markante Eigenschaften
    - Gebäude
        - Gebäudetrakte
    - Gebäudetrakt
        - Stockwerke
    - Stockwerk
        - Gänge
    - Gang
        - Räume
        - markante Punkte/Eigenschaften
    - Räume
        - Raumeigenschaften
    - Markante Punkte/Eigenschaften
        - Position Treppenhaus/Treppenhäuser
        - Position Fahrstuhl/Fahrstühle
        - Position Ein-/Ausgang/-gänge
        - Position Notausgang/-gänge
* Belegungsplan
    - wöchentlich wiederkehrender Belegungsplan für jeden Raum
        - für jeden Tag/jede Stunde die Belegung festlegen
            - Raumnummer/RaumID
            - Start
            - Ende
            - Veranstalltungsname
            - betreuende Lehrkraft
    - dynamischer Belegungsplan für jeden Raum
        - für jeden Tag/jede Stunde die Belegung, je nach aktueller Auslastung festlegen
            - Raumnummer/RaumID
            - BenutzerID des Buchers
            - Anzahl Personen im Raum
            - Start der Buchung
            - Ende der Buchung

## Standortbestimmung des Benutzers
* BLE Beacons im Gebäude vor jedem Raum/in bestimmten Abstand
* Endgerät (Smartphone/Tablet) dient als Empfänger
* gefundene Beacon ID und Entfernung zu diesem wird vom Endgerät des Benutzers an den Server gesand (HTTP)
* Server kann die ID zuordnen und anhand der Entfernung den ungefähren Standort des Benutzers ermitteln.
* Standort wird an das Endgerät des Benutzers gesand und bei Bedarf in einer Raumanfrage mitgesand
* Prozess iteriert nach bestimmter Zeit um den aktuellsten Standort festzustellen

## Laufwegoptimierung (Alleinstellungsmerkmal)
* der Server kann anhand des Standortes des Benutzers und seinen Wünschen einen Raum ausgeben der sich in seiner Nähe befindet.
* Räume bekommen eine Gewichtung und können so für eine Wegfindung zwischen zwei Räumen eingesetzt werden.
* Startort ist der Raum der sich am nächsten zum Benutzer befindet.
* Zielort ist der der vom Benutzer gewünschte Raum
* der Weg setzt sich aus der Strecke mit der kleinsten Kantenzahl zusammen (Dijkstra-Algorithmus)
* Start und Endpunkt einer Kante sind zwei verschiedene Räume
* als Startpunkt wird der Raum gewählt der am nächsten am Benutzer liegt
* Kantenwerte
    - zwei nebeneinanderliegenede Räume haben die Kantenwertung 1
    - zwei gegenüberliegende Räume haben die Kantenwertung 1
    - ein Treppenhaus (ein Etagenwechsel) hat die Kantenwertung 3
    - Kante zwischen Treppenhaus und Raum hat die Kantenwertung 1
    - Kante zwischen Gebäudeein/ausgang und Raum/Treppenhaus hat die Wertung 1
* Kantenwertung muss je nach Gebäudeaufbau eventuell unterschiedlich sein
* eventuell lässt sich die Kantenwertung auch über Meterzahl regeln
    - dann muss jede Lehreinrichtung individuell berechnet werden
* es kann keine Metergenaue Angabe gemacht werden, immer nur ungefähre Angaben
* Zeitersparnis ist auch variabel, je nach Laufgeschwindigkeit/Schrittgröße
* System bezieht nur die position des Benutzers beim abschicken des Requests ein!

## Problem - Flexible Räume (Verteilte Anwendungslogik)
* da Räume Equipment beinhalten kann, das von einem zu einem anderen Raum transportiert wird, wird eine erkennung benötigt.
* Erkennung über passive RFID Aufkleber
* aktive Aufkleber benötigen Batterie, Auswechseln dieser wäre bei der Menge nicht vertretbar
* Jeder entfernbare, und für das System relevante, Gegenstand bekommt einen eindeutigen RFID Aufkleber
* IM Eingangsbereich des Raumes befindet sich ein Sensor der registriert ob ein Gegenstand die Tür passiert.
* Wird ein GEgenstand erkannt, wird diese Information im System gespeichert.
* Der Gegenstand wird im System in eine temporäre Tabelle gespeichert
* Es wird darauf gewartet das der Gegenstand im Eingangsbereich eines anderen Raumes registriert wird.
* Trifft das zu, wird der Gegenstand für diesen neuen Raum als Inventar eingetragen.
* Trifft das nicht zu kann man herrausfinden das ein Gegenstand entwendet wurde
* Sollte sich ein Gegenstand eigentlich in einem Raum befinden, wird dann aber in einem anderen Raum registriert, kann so dynamisch das Inventar angepasst werden.
* keine erkennung ob ein Gegenstand einen Raum verlässt oder betritt
* wird dynamisch erkannt durch einscannen an verschiedenen Orten
* __Bedingung:__ Alle relevanten Gegenstände müssen katalogisiert werden.

## Problem - Verschiedene Eigenschaften der Räume
* es kann Räume geben die nicht abgeschloßen sind, wo keine automatische Türöffnung mittels Code erfolgen kann
* alle Räume so verändern, das ein Code zum betreten benötigt wird (Räume sind von außen abschließbar)
* Alternativ das Risiko akzeptieren das ein Raum benutzt wird ohne das das System das weis

## Problem - Personenerkennung im Raum
* Druckplatten im Eingangsbereich
    - +
    - -
* Bewegungsmelder im Eingangsbereich
    - + günstig
    - - keine Anzahl erkennbar
* Lichtschranken im Eingangsbereich
    - + anzahl an Personen theoretisch möglich wenn 2 Lichtschranken hintereinander platziert werden
    - - 
* Magnetfeldsensor für Menschen
    - + Personen haben eigenes Magnetfeld
    - - keine brauchbare Lösung gefunden
* Lautstärke (keine anzahl erkennbar)
    - + günstig, nur Mikro benötigt
    - - Anzahl erkennung ist schwierig (Musik/Video im hintergrund)
* Wärmebildsensor
    - + erkennung von mehreren Objekten
    - - sehr teuer
    - - warme Gegenstände werden auch erkannt (PC, Kaffee)
* Wifi Counter
    - + 
    - - nicht realistisch, Personen können Handy und Laptop dabei haben = 2 Personen erkannt
* Großer Bewegungsmelder an der Decke in Mitte des Raumes
    - + eventuell schon vorhanden
    - - Still sitzende Personen werden nicht erkannt
* Kamera mit Bildanalyse (Foto, Live)
    - + Personenerkennung relativ genau
    - - guter PC benötigt (hohe Auslastung durch Bildanalyse)
    - Objekte können das Bild verdecken

## User Interface


## Evaluation


## Fazit - 1. Gestalltungslösung