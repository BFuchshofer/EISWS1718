# Content Modelling (ARBEITSTITEL)
### Einleitung

Mit Hilfe der Benutzer- und Benutzungsmodellierung kann durch verschiedene
Methoden der "Interface Contents and Navigation" Thematik ein übersichtlicher
abstrakter Prototyp erstellt werden. Ergebnis dieser Vorangehensweise ist
eine "Content List" und eine "Content Navigation Map" mit deren Hilfe die
wichtigen Merkmale und Funktionen sowie die Übergänge zwischen den einzelnen
Aktionen und Dialogen innerhalb der Benutzerschnittstelle dargestellt werden
können.  
Wichtig hierbei ist das es sich nur um einen abstrakten Prototypen handelt, also
das die Ergebnisse so wenig Designmerkmale und Lösungsvorschläge beinhalten wie
möglich. Weiterhin ist zu beachten das genannte Eigenschaften wie ein
Drop-down Menü" nur ein Beispiel ist und für die fertige Gestaltungslösung noch
abgeändert werden kann.

### Interface Content Modeling (ARBEITSTITEL)

Sinn der Content-List ist es eine übersichtliche Auflistung der verschiedenen
"interaction spaces" mit ihren zugehörigen Inhalten zu erstellen.
Ein "interaction space" wird dabei in die verschiedenen Dialoge aufgeteilt.
Im Falle dieses Projektes werden die Interaction spaces *userSpace*,
*managementSpace*, *adminSpace* und *staffSpace* mit ihren Dialogen
untergliedert ([siehe Content List]()).  
Der *userSpace* besteht zum Beispiel aus den folgenden Dialogen:

* Room Suggestion Dialogue
* Room Reservation Dialogue
* Room Booking Dialogue
* Room User Verification Dialogue
* Room Cancel Reservation Dialogue
* Room Cancel Booking Dialogue

Der erste Teil der Dialogbezeichnung wurde so gewählt das durch diesen
verdeutlicht wird, welche Informationen während diesem verarbeitet werden.
So sind die Dialoge welche mit "Room" beginnen Dialoge über die ein Benutzer
mit einem im System gespeicherten Raum interagieren kann.  
Die Dialoge können wiederum durch Inhalte untergliedert werden. Dabei wurde in
diesem Projekt darauf geachtet das nur zwingend Notwendige Inhalte eingetragen
wurden, damit so wenig für das endgültige Aussehen, sowie den Aufbau des
Interfaces vorgegeben bzw. festgelegt wird.

#### Content List
__userSpace__  
    __Room Suggestion Dialogue__  
        *roomNumber*  
            Textfeld welches die Raumnummer des Vorschlages anzeigt.  
        *filterSelector*  
            Liste verfügbarer Filtereinstellungen  
    __Room Reservation Dialogue__  
        *roomNumber*  
        *reservationDuration*  
    __Room Booking Dialogue__  
        *roomNumber*  
        *bookingDuration*  
    __Room User Verification__  
        *roomNumber*  
        *userName* or *userEmail*  
    __Room Cancel Reservation Dialogue__  
        *roomNumber*  
    __Room Cancel Booking Dialogue__  
        *roomNumber*  
__managementSpace__  
    __Room Search Dialogue__  
    __Room Booking Dialogue__  
        *bookFrom*  
            Inputfeld für die Uhrzeit und den Tag  
        *bookTo*  
            Inputfeld für die Uhrzeit und den Tag  
    __Room Cancel Booking Dialogue__  
__adminSpace__  
    __Room Search Dialogue__  
    __Room Add Dialogue__  
        *roomInformation*
            Liste unbedingt notwendiger Informationen um einen validen Raum dem System hinzuzufügen.  
    __Room Edit Dialogue__  
        *roomInformation*  
            Liste der momentan im System gespeicherten Informationen über einen bestimmten Raum  
    __Room Delete Dialogue__  
        *roomNumber*  
    __Equipment Add Dialogue__  
    __Equipment Edit Dialogue__  
    __Equipment Delete Dialogue__  
__staffSpace__  
    __Room Search Dialogue__  
    __Room Block Dialogue__  
        *roomNumber*  
        *blockFrom*  
            Inputfeld für die Uhrzeit und den Tag  
        *blockTo*  
            Inputfeld für die Uhrzeit und den Tag  
    __Room Cancel Block Dialogue__  

___

__roomSuggestion__  

| User                                      | System                |
|:------------------------------------------|----------------------:|
| *optional* Raumspezifikationen definieren |                       |
| Vorschlag anfragen                        |                       |
|                                           | Vorschlag anzeigen    |

__roomReservation__  

| User                                      | System                |
|:------------------------------------------|----------------------:|
| Raum auswählen                            |                       |
| Raum reservieren                          |                       |
|                                           | Restliche Reservierungsdauer anzeigen  |
| *optional* Reservierung Abbrechen         |                       |
|                                           | verlassen             |

__roomUserVerification__  

| User                                      | System                |
|:------------------------------------------|----------------------:|
| für die Raum Nutzung verifizieren         |                       |
|                                           | Ergebnis anzeigen     |
| wiederholen bis verifiziert               |                       |
|       oder Abbrechen                      |                       |
|                                           | verlassen             |

__roomBooking__  

| User                                      | System                |
|:------------------------------------------|----------------------:|
| Raum buchen                               |                       |
|                                           | Restliche Buchungsdauer anzeigen |
| *optional* Buchung Abbrechen              |                       |
|                                           | verlassen    |

#### Context Navigation Map



### Fazit - Content Modelling
