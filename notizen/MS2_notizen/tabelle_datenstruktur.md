# Benötigte Informationen im System

### Benutzerinformationen
- (Lehreinrichtungs-)Email
    - Eine Email-Adresse die den dazugehörigen Benutzer und seine Berechtigungen eindeutig identifiziert.
- Status im Bezug auf die Lehreinrichtung
    - Ergibt sich aus der (Lehreinrichtungs-)Email
- Standort des Benutzers
    - Der aktuelle Standort des Benutzers von dem aus das System seine berechnungen für einen freien Raum aufstellt.

### Rauminformationen
- Gebäude
    - Auflistung aller im System relevanten Gebäude und eventuell existierende Gebäudetrakte
- Gebäudetrakt
    - Auflistung aller im System relevanten Gebäudetrakt und eventuell existierende Stockwerk
- Stockwerk
    - Auflistung aller im System relevanten Stockwerk und eventuell existierende Gänge
- Gang
    - Auflistung aller im System relevanten Gänge und eventuell existierende Räume
- Raum
    - Raumnummer/RaumID
        - eindeutige Identifikation des Raumes innerhalb des Systems
    - Raumtyp
        -  wie der Raum genutzt werden kann (Präsentation/stilles arbeiten/Gruppenarbeit)
    - Raumgröße
         - max. Personenanzahl
- Rauminhalte (je nach Lehreinrichtung unterschiedlich)
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
        - weitere (je nach Lehreinrichtung variabel)
            - z.B. Spezielles Equipment (Kamera/Tonstudio/Greenscreen/Mischer/besondere Gerätschaften)
### Sonstige markante Eigenschaften
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
        - Treppenhaus/Treppenhäuser
        - Fahrstuhl/Fahrstühle
        - Ein-/Ausgang/-gänge
        - Notausgang/-gänge
        
### Filtermöglichkeiten
    - Anzahl Personen die den Raum nutzen sollen
    - Anzahl Räume die benötigt werden
    - Anzahl Sitzmöglichkeiten die benötigt werden
    - Raumtyp
        - Präsentationsraum
        - Gruppenarbeitsraum
        - Vorlesungsraum
        - PC Raum
        - stiller Arbeitsraum (Einzelarbeit)
        - Spezieller Raum  
            - Raum mit speziellem Equipment
    - Barierefreiheit
    
### Belegungsplan
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
            

# Datenstruktur

## Datenbank
{
  "roomData": {
            "room_id": "",
            "status": "",
            "building": "",
            "building_part": "",
            "floor": "",
            "corridor": "",
            "room_items": ""
            }
}


### Statische Belegung
{
  "static_events": {
            "event_name": "",
            "room_id": "",
            "start": "",
            "end": "",
            "responsible_person": ""
            }
}


### Dynamische Belegung
{
  "dynamic_events": {
            "room_id": "",
            "user_id": "",
            "start": "",
            "end": "",
            "current_persons": ""
            }
}

### JSON mit Benutzerinformationen
{
  "user": {
            "userID": "",
            "permissions": ""
            }
}

### JSON mit Gegenständen
{
  "items": {
            "itemID": "",
            "itemName": "",
            "itemPosition": ""
            }
}

### Beacons
{
  "beacon": {
            "beaconID": "",
            "attachedRoomID": ""
            }
}

## Webserver

### JSON mit freien Räumen
{
  "freeRooms": {
            "room_id": "",
            "itemList": ""
            }
}

### JSON mit nicht zugewiesenen Gegenständen
{
  "item": {
            "itemID": "",
            "itemName": "",
            "lastPosition": "",
            "timestamp": ""
            }
}

## Client
### JSON mit Benutzerinformationen
{
  "user": {
            "userID": "",
            "server_url": ""
            }
}


### JSON erkannter Beacons
{
  "beacons": {
            "beaconID": "",
            "timestamp": ""
            }
}


### JSON mit aktueller Reservierung/Buchung
{
  "order": {
            "order_status": "",
            "order_type": "",
            "timestamp": ""
            }
} 

## Minicomputer
### JSON mit Rauminformationen
{
  "roomInformation": {
            "roomID": "",
            "roomStatus": "",
            "itemList": "",
            "userList": ""
            }
}            
         
            
            
            
            
<!--
* Benutzerinformationen
    - (Lehreinrichtungs )Email
    - Status im Bezug auf die Lehreinrichtung
        - Zugangsberechtigungen
    - Standort des Benutzers
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
* Filtermöglichkeiten
    - Anzahl Personen
    - Anzahl Räume
    - Anzahl Sitzmöglichkeiten
    - Raumtyp
        - Präsentationsraum
        - Gruppenarbeitsraum
        - Vorlesungsraum
        - PC Raum
        - stiller Arbeitsraum (Einzelarbeit)
        - Spezieller Raum  
            - Raum mit speziellem Equipment
        - Barierefreiheit
        - 
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
-->