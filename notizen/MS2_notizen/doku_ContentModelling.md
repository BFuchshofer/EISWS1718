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
Im Falle dieses Projektes werden die Interaction spaces *userSpace*, *instituteSpace* and *adminSpace* mit ihren Dialogen
untergliedert ([siehe Content List]()).  
Der *userSpace* besteht zum Beispiel aus den folgenden Dialogen:

* Start Dialogue
* Filter Dialogue
* Reservation Dialogue
* Multi Reservation Dialogue
* Booking Dialogue
* Multi Booking Dialogue

Der erste Teil der Dialogbezeichnung wurde so gewählt das durch diesen
verdeutlicht wird, welche Aktion diesem Dialog vorausgegangen ist. Der Dialog
__Reservation Dialogue__ beschreibt zum Beispiel den Context den der Benutzer
sieht, nachdem er erfolgreich einen Raum reserviert hat.
Die Dialoge werden weiterhin durch benötigte Inhalte erweitert und untergliedert.
Dabei wurde in diesem Projekt darauf geachtet, dass so wenig endgültige
Designeigenschaften der Elemente vorgegeben werden.

#### Content List
__userSpace__  
    __Start Dialogue ( Gleicher Startpunkt für userSpace und instituteSpace )__
        *toolSelection*
            Eine Auswahl an verfügbaren Funktionen und Tools.
    __Filter Dialogue__
        *roomEquipment*
            Eine Auswahl an möglichem Equipment.
        *roomProperties*
            Eine Auswahl an möglichen Raumeigenschaften.
        *roomSize*
            Auswahl der Größe des Raumes.
    __Reservation Dialogue__
        *roomNumber*
            Anzeige der Identifikation des reservierten Raumes.
        *reservationDuration*
            Anzeige der restlichen Dauer der Reservierung.
    __Multi Reservation Dialogue__
        *roomNumberList*
            Anzeige der Identifikationen der reservierten Räume.
        *reservationDuration*
            Anzeige der restlichen Dauer der Reservierungen.
    __Booking Dialogue__
        *roomNumber*
            Anzeige der Identifikation des gebuchten Raumes.
        *bookingDuration*
            Anzeige der restlichen Dauer der Buchung.
    __Multi Booking Dialogue__
        *roomNumberList*
            Anzeige der Identifikationen der gebuchten Räume.
        *bookingDuration*
            Anzeige der restlichen Dauer der Reservierungen.

__instituteSpace__
    __Start Dialogue ( Gleicher Startpunkt für userSpace und instituteSpace )__
        *toolSelection*
            Eine Auswahl an verfügbaren Funktionen und Tools.
    __Search Dialogue__
    __Block Dialogue__
        *roomNumber*
            Anzeige der Identifikation des geblockten Raumes.
        *blockingDuration*
            Anzeige der restlichen Dauer der Blockung.

__adminSpace__
    __Start Dialogue__
        *toolSelection*
            Eine Auswahl der nur für den Administrator verfügbaren Funktionen und Tools.
    __Search Dialogue__
    __Add Room Dialogue__
        *roomInformationForm*
            Eine Formular mit notwendigen und optionalen Raumeigenschaften und Rauminformationen.
    __Edit Room Dialogue__
        *roomInformationForm*
            Ein Formular mit allen im System verfügbaren Rauminformationen.
    __Delete Room Dialogue__
        *roomNumber*
            Anzeige der Identifikation des zu löschenden Raumes
    __Add Equipment Dialogue__
        *equipInformationForm*
            Ein Formular mit notwendigen und optionalen Equipmenteigenschaften und Equipmentinformationen.
    __Edit Equipment Dialogue__
        *equipInformationForm*
            Ein Formular mit allen im System verfügbaren Equipmentinformationen.
    __Delete Equipment Dialogue__
        *equipID*
            Anzeige der Identifikation des zu löschenden Equipments.


#### Context Navigation Map



### Fazit - Content Modelling

Die aus dem Content Modelling entstandenen Artefakte können für das
Interfacedesign verwendet werden, da durch diese die wichtigen Informationen
und Übergänge zwischen verschiedenen Dialogen aufgezeigt werden. Die Artefakte
wurden allerdings so wenig vom Endgültigen Design vorgegeben wurde.
