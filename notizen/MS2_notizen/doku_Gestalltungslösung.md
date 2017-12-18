# Gestalltungslösung

## Einleitung
Da die Grundbausteine für die Entwicklung einer optimalen Gestaltlösung durch die Benutzer und Aufgabenmodelierung sowie die Aufführung der Content Elemente nun gegeben sind, geht es jetzt darum eine konkrete Lösung für das Nutzungsproblem zu finden. In diesem Teil der Dokumentation befassen wir uns mit der Erarbeitung und kritischen Diskussion von möglichen Technologien für einzelne Unterprobleme, die Zusammenstellung der Komponenten des Systems, ihre Abhängigkeiten und Kommunikationswege untereinander, sowie die Ausarbeitung eines User Interfaces.


## Idee
Um das Ziel, einen Raum zu finden der den Bedürfnissen und Erwartungen des Benutzers entspricht, zu erfüllen, haben wir uns in einer ersten Idee ein 3-teiliges System überlegt bestehend aus Server, Client und Datenspeicher. Der Server stellt dabei die Verarbeitungseinheit und zentrale Instanz des Systems dar. Er bezieht dabei Informationen aus einer Datenbank, sowie vom Client. Innerhalb der Datenbank sind sowohl Informationen über alle verfügbaren Räume und ihre aktuellen Rauminhalte und Belegung der Räume, sowie Informationen über die statischen, wöchentlich wiederkehrenden Veranstaltungen.
Der Client stellt dabei die Schnittstelle zwischen Benutzer und System da. Unserer Meinung nach wäre eine Anwendung auf Smartphone oder Tablet des Benutzers eine gute Möglichkeit, weil davon ausgegangen werden kann, dass der Benutzer diese Geräte immer dabeihat. Laut einer Studie der [Bitkom research](https://www.bitkom.org/Presse/Anhaenge-an-PIs/2017/02-Februar/Bitkom-Pressekonferenz-Smartphone-Markt-Konjunktur-und-Trends-22-02-2017-Praesentation.pdf) benutzen 78% der befragten Personen ab 14 Jahren im Januar 2017 ein Smartphone. Mit einer Nutzerzahl von 54 Millionen Menschen in Deutschland (Tendenz steigend) bietet es sich an das Smartphone oder Tablet in den Prozess einzubeziehen. Das Einbeziehen von Systemkomponenten des Benutzers hat außerdem den Vorteil das eine Standortunabhängige Nutzung des Systems ermöglicht wird. So müssen nicht auf z.B. Terminals oder Anzeigetafeln an festen Positionen im Gebäude, Informationen über Räume bezogen werden. 
In einem [Brainstorming]() über den groben Aufbau und Funktion des Systems haben wir mehrere Probleme identifiziert, auf die in diesem Teil der Dokumentation eingegangen werden soll. Darunter zählen z.B. eine "Standortbestimmung" der Benutzer innerhalb des Gebäudes um einen konkreten Raumvorschlag liefern zu können, oder auch die Verifizierung der Anwesenheit von Gegenständen oder Personen innerhalb von Räumen. Da es je nach Umfeld sein kann, dass es Räume gibt die nicht von jeder Personengruppe genutzt werden kann oder darf, muss die Zugangsberechtigung für Räume geklärt werden, so das gewährleistet wird das nicht jeder Zugang zu allen Räumen hat. 

<!-- Liste mit Problemen machen?
* Standortbestimmung
* Gegenstände im Raum
* Personen im Raum
* Zugang zum Raum
-->

## Datenstruktur/Relevante Informationen (ARBEITSTITEL)

### Einleitung
Um im System Informationen verarbeiten zu können, müssen diese erst einmal definiert werden.
Im Folgenden möchten wir kurz auflisten, welche Informationen im System eine Rolle spielen und warum.
Dabei unterscheiden wir zwischen Benutzerinformationen, Rauminformationen, Filtermöglichkeiten, Belegungspläne und sonstigen relevanten Eigenschaften.

### Benutzerinformationen
Eine Verifizierung eines Benutzers des Systems ist notwendig, da gewährleistet werden muss, dass nur Angestellte und Mitglieder der Lehreinrichtung Zugang zu den System- bzw. Rauminformationen erhalten. Deswegen sollte beim Systemzugriff geprüft werden ob der Benutzer eine Zugangsberechtigung für das System besitzt. Eine einfache Möglichkeit das zu lösen, wäre eine Authentifizierung über eine Lehreinrichtungskennung, z.B. eine spezielle E-Mail-Adresse. Diese E-Mail-Adresse muss bei der Erstinstallation angegeben werden, und wird bei jeder Interaktion mit dem System übertragen um den Benutzer zu verifizieren. Der Server besitzt einen Datensatz mit berechtigten E-Mail-Adressen, und kann so überprüfen ob die Anfrage des Benutzers bearbeitet werden darf. Aus der E-Mail-Adresse lassen sich dann auch, z.B. über das Format der Adresse oder anhand einer weiteren Liste im System, die Zugangsberechtigungen für bestimmte Räume prüfen. Falls die Lehreinrichtung nicht möchte das ein bestimmter Raum von jeder Benutzergruppe genutzt werden kann, lässt sich so eine Berechtigungsabfrage durchführen, ohne dass der Benutzer zusätzliche Interaktionsschritte unternehmen muss. Außerdem muss der Standort des Benutzers festgelegt werden damit er durch das System bei der Raumauswahl berücksichtigt werden kann. Zu den infrage kommenden Technologien dazu, kommen wir in einem [späteren Abschnitt]().

### Rauminformationen
Um einen Raum im System eindeutig identifizieren zu können, muss die Raumnummer als Ausgangspunkt hinterlegt werden. Zusätzlich muss sämtliches, für das System relevante, Equipment aufgelistet werden, damit im späteren Verlauf eine Suche nach einem bestimmten Raum ermöglicht werden kann. Dieses Equipment kann je nach Raum oder Raum typ unterschiedlich ausfallen und hängt in der Regel von seinem gedachten Verwendungszweck ab. Räume die für Präsentationszwecke gedacht sind, besitzen in der Regel ein Präsentationsmedium wie etwa Beamer, Fernseher, OHP oder Whiteboard. Zusätzlich werden, wie in fast jedem Raum, Sitzmöglichkeiten benötigt. Die Größe, und damit die Anzahl der Sitzplätze sind ebenfalls relevant, da nur so auf einen Raum für eine bestimmte Anzahl an Personen hingearbeitet werden kann. Die dynamischen Rauminhalte, und mit welchen Technologien diese aktualisiert werden können, werden im Kapitel [Flexible Räume]() behandelt. Diese Rauminhalte sind je nach Lehreinrichtung und vorhandenem Equipment variabel und müssen sich an dem spezifischen Kontext ausrichten. Eine weitere relevante Information ist der Standort des Raums um einen kurzen Weg zwischen Benutzer und gesuchten Raum zu berechnen. Dieser lässt sich im Idealfall aus der Raumnummer ableiten, indem diese so aufgebaut ist das das Gebäude, Stockwerk und der Gang daraus ersichtlich wird. Auch diese Informationen sind variabel und müssen auf das jeweilige Umfeld angepasst werden. Alternativ lassen sich diese Informationen auch einzeln im System abspeichern.

### Filtermöglichkeiten
Damit der Benutzer nach einem Raum suchen kann, muss das System ihm die Möglichkeit bieten auf Wünsche und Möglichkeiten einzugehen. Dazu sollte der Benutzer die Möglichkeit haben durch spezielle Filter seinen Raumwunsch zu konkretisieren. Diese Filter betreffen z.B. den Rauminhalt der benötigt wird damit der Benutzer seine Arbeit verrichten kann. Der Benutzer kann durch auswählen seinen Raumwunsch eingrenzen und dem System mitteilen der diese Wünsche in seiner Raumauswahl berücksichtigt. Der Benutzer sollte aber auch grundlegendere Informationen angeben können, wie z.B. den barrierefreien Zugang zu Räumen. Da kann der Benutzer z.B. angeben das er einen Fahrstuhl benötigt um die Etagen im Gebäude wechseln zu können.

### Belegungspläne
Da in einer Lehreinrichtung oft Veranstaltungen existieren die im wöchentlichen Muster wiederholt werden, existiert dazu meist ein Belegungsplan. Dieser gibt an in welchem Raum in welcher Zeitspanne eine Veranstaltung, also eine Belegung des Raumes, stattfindet. Diese Liste mit Informationen, sofern vorhanden, ist für die Ausgabe von freien Räumen an den Benutzer eine wichtige Grundlage. Mit ihr können viele Räume schon vorab ausgeschlossen werden, bevor sie in die Berechnung mit einfließen. Zusätzlich muss eine Liste existieren die Auskunft über die dynamische Belegung von Räumen Aufschluss gibt. In dieser Liste sollten alle aktuellen Belegungen von Räumen verzeichnet sein, damit das System bei seinen Berechnungen unterstützt wird. Die Liste sollte mit Informationen gefüllt sein die Aufschlüsse über den belegten Raum, die Anzahl der Personen in diesem Raum, Start- und Endzeitpunkt der Belegung sowie die Benutzeridentifizierung des Benutzers, der den Raum gebucht hat, gibt.

### Sonstige Eigenschaften
Grundlegende Informationen wie alle zur Verfügung stehenden Räume innerhalb der Lehreinrichtung, die Anzahl der Gebäude, Stockwerke und Gänge, Positionen von Ein-/Ausgängen, Treppenhäuser, Notausgänge, Fahrstühle und eventuell weitere Informationen die vom Umfeld abhängen, sind ebenfalls eine wichtige Grundlage für das System. Mit diesen Informationen und denen der Räume und Benutzer, können im System später Raumvorschläge für den Benutzer effizient berechnet werden.

### Fazit - Datenstruktur/Relevante Informationen (ARBEITSTITEL)
Die benötigten Informationen im System sollten innerhalb eines persistenten Datenspeichers, bspw. einem Datenbankserver hinterlegt sein. Der Server als Verarbeitungseinheit erfragt die jeweils auf die Anfrage passenden, benötigten Daten aus dem Speicher und verarbeitet diese. Diese Auflistung an benötigten Informationen befindet sich als ausführliche Beschreibung im [Anhang](). In dieser Liste werden im Verlauf der Ausarbeitung auch die benötigten Variablen und Listen in Code bzw. Pseudocode aufgeführt werden.


<!---
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


## Komponenten des Systems
Wie bereits erwähnt soll unser System aus drei Hauptkomponenten bestehen. Als Server dient uns ein in Node.js aufgesetzter Webserver der mittels HTTP-Requests mit den anderen Systemkomponenten kommunizieren kann. Der Webserver dient als zentrale Verarbeitungseinheit für das System und behandelt alle eingehenden Anfragen der Benutzer. Die Kommunikation funktioniert sowohl von Richtung Client als auch in Richtung Datenbankserver über einfache HTTP-Requests die im besten Fall über ein geschlossenes Netzwerk innerhalb der Lehreinrichtung arbeitet. Damit besteht die Voraussetzung das der Benutzer sich im selben Netzwerk wie das System befinden muss, bspw. über eine aktive WLAN-Verbindung. Damit würde eine zusätzliche Authentifizierung der Benutzer erfolgen, da für gewöhnlich nur Personen Zugang zu einer WLAN-Verbindung haben, die dort auch Mitglied oder angestellt sind. Da aber wahrscheinlich nicht alle Lehreinrichtungen eine WLAN-Verbindung stellen können, sollte der Zugang zum System auch über eine Mobilfunkverbindung mit dem Internet möglich sein. Als Grundvoraussetzung um das System nutzen zu können, ist aber trotzdem eine aktive Internetverbindung an die sich der Node.js Server anknüpfen kann. In unserem Projekt gehen wir davon aus das die Lehreinrichtung sowohl einen verfügbaren Internetanschluß als auch eine WLAN Verfügbarkeit überall im Gebäude gewährleisten kann. Andernfalls ist dieses Projekt für diese spezielle Lehreinrichtung nicht relevant.
Der Datenbankserver des Systems dient als Verbindungselement zwischen Webserver und Datenbank. Dabei liest der Datenbankserver die benötigten Informationen des Webservers aus der Datenbank aus und leitet sie weiter. Außerdem ist er für das abspeichern von neuen oder aktualisierten Informationen zuständig die ihm der Webserver vermittelt.

<!-- Wieso Trennung der beiden? Begründen! -->

Wie bereits festgestellt wird es im System 2 Datensätze geben die Informationen über die Raumbelegung geben. Es bietet sich an diese auch in logisch getrennten Datenbanken einzuordnen um eine Wartung dieser zu vereinfachen. Auf der einen Datenbank sind nur Informationen vorhanden die bereits im Systemumfeld vorliegen, wie z.B. der reguläre Veranstaltungsplan der regelmäßig wiederkehrenden Veranstaltungen speichert. Die andere Datenbank enthält die von uns dynamisch erzeugten Informationen über die aktuelle Belegung der Räume durch die Benutzer des Systems. Durch die Trennung dieser Datensätze kann je nach Bedarf der Inhalt der Datenbanken verändert werden, ohne den Ablauf der anderen Datenbank zu beeinflussen.

Die 3. Komponente auf Clientseite ist eine Anwendung auf dem Endgerät des Benutzers. In unserem Projekt haben wir uns auf eine Applikation, basierend auf dem Android Betriebssystem fokussiert. Eine Umsetzung auf einem anderen Betriebssystem, wie z.B. iOS ist aber auch denkbar, wird in diesem Projekt aber nicht behandelt. Diese Anwendung stellt die Kommunikations-  und Interaktionsschnittstelle mit den Funktionen des Systems da. Der einfache Benutzer des Systems kann durch die Installation dieser Anwendung auf seinem Android basierten Endgerät wie z.B. Smartphone oder Tablet die Funktionalitäten des Systems nutzen. Die Anwendung kommuniziert dabei über HTTP-Request mit dem Server des Systems und sendet Informationen die zur Berechnung einer Raumauswahl benötigt werden. Dabei sollte der Anwendung bekannt sein, unter welcher Adresse sich der Webserver befindet. Um das dynamisch und auf verschiedene Lehreinrichtungen einfach anpassen zu können, sollte unter anderem diese Adresse bei der ersten Konfiguration in der Anwendung gespeichert werden. Da sich der Benutzer sowieso durch z.B. Eingabe einer Email identifizieren muss, kann durch ein zusätzliches Eingabefeld auch die Webadresse des Servers vom Benutzer erfragt werden.


## Fazit - Komponenten des Systems
In unserem System sind wir auf folgende Komponenten gekommen, die ausführlich im [Anhang]() in Form einer grafischen Auflistung erläutert wurden.
* Webserver 
* Datenbankserver
* Datenbank_1 (regulärer Veranstalltungsplan)
* Datenbank_2 (dynamischer Veranstalltungsplan)
* Anwendung auf Endgerät des Benutzers

<!--
* Minicomputer in jedem Raum (mit Bluetooth)
* Bluetooth (BLE) Beacon an jeder Raumtür
-->

Um die Kommunikation und die Architektur der Komponenten des Systems deutlich dazustellen, haben wir Schaubilder erstellt die es ermöglichen einen Eindruck über unseren Aufbau des Systems zu erhalten.

## Architektur des Systems

<!--
#### Deskriptiv
![deskriptiv Architekturmodell](https://github.com/eXeLuis/EISWS1718FuchshoferFonsecaLuis/tree/master/notizen/MS2_notizen/imgs/deskriptiv_architektur.png)  
-->

### Präskriptives Architekturmodell
![präskiptives Architekturmodell](https://github.com/eXeLuis/EISWS1718FuchshoferFonsecaLuis/blob/master/notizen/MS2_notizen/imgs/pr%C3%A4skriptiv_architektur.PNG)  

## Kommunikation des Systems

<!--
### Kommunikation der Komponenten
#### Deskriptiv
![deskriptives Kommunikationsmodell](https://github.com/eXeLuis/EISWS1718FuchshoferFonsecaLuis/blob/master/notizen/MS2_notizen/imgs/deskriptiv_kommunikation.PNG)  
-->

### Präskriptiv
![präskiptives Kommunikationsmodell](https://github.com/eXeLuis/EISWS1718FuchshoferFonsecaLuis/blob/master/notizen/MS2_notizen/imgs/kommunikationsdiagramm_v3.png)  

### Kommunikation mit dem Benutzer
#### Deskriptiv <!-- oder deskriptiv vor den gestalltungslösungen? ändert sich ja nicht -->
#### Präskriptiv


## Standortbestimmung des Benutzers
### Einleitung
Damit durch unser System ein Raum ermittelt werden kann, der sowohl den
Erfordernissen des Benutzers entspricht, als auch einen möglichst kurzen Laufweg für den Benutzer gewährleistet, muss der Standort des Benutzers innerhalb des Gebäudes bestimmt werden.
Als Methoden zur Feststellung des Standortes haben wir sowohl die Bestimmung
durch Benutzereingaben, als auch die durch GPS und Bluetooth-Beacons innerhalb des
Gebäudes betrachtet.

### Standortbestimmung durch Benutzereingabe
Die Standortbestimmung über Benutzereingaben würde dabei so funktionieren, dass der Benutzer einen Bezugspunkt innerhalb eines Gebäudes, z.B. eine Raumnummer oder die Kennzeichnung eines Treppenhauses, angibt. Wir haben uns allerdings gegen diese Methode entschieden da sie eine zusätzliche Interaktion
durch den Benutzer erfordert und das gegebenenfalls den Nutzungsfluss des Systems
stören könnte. Diese Methode kann allerdings als Fallback verwendet werden,
wenn die Standortbestimmung durch andere Methoden oder Technologien scheitert.
* Vorteile
    - Benutzer kann selber seinen Startpunkt der Suche angeben
* Nachteile
    - Benutzereingaben können fehlerhaft sein
    - Benutzer weis eventuell nicht wo er sich befindet
    - zusätzlicher Interaktionsschritt durch den Benutzers nötig

### Standortbestimmung durch GPS
Durch die Verwendung von GPS (Global Positioning System) könnte ebenfalls der Standort des Benutzers bestimmt werden. Dabei würde mittels des Endgerätes des Benutzers die Verbindung
zum "Global Positioning System" aufgebaut und somit der Standort mit einer hohen
Genauigkeit bestimmt werden. Da die Genauigkeit allerdings schwanken kann haben
wir uns gegen die Verwendung von GPS entschieden da eine fehlerhafte oder
ungenaue Messung zu falschen Ergebnissen innerhalb des Systems führen kann.
* Vorteile
    - sehr präzise
    - sehr weit verbreitet
* Nachteile
    - innerhalb von Gebäuden können Signalstörungen auftreten
    - 

### Standortbestimmung durch Bluetooth-Beacons
Bluetooth-Beacons sind das Indoor-Äquivalent zum klassischen GPS und ermöglichen innerhalb von Gebäuden eine exakte Standortbestimmung auf bis zu 1 Meter [Quelle](https://www.infsoft.de/technologie/sensorik/bluetooth-low-energy-beacons). Dazu kann eine Entfernungsbestimmung vom Endgerät des Benutzers zum Beacon durch auslesen von Signaldaten aufgestellt werden, womit ein grober Standort bestimmt werden kann. Durch das Hinzufügen von mehreren Beacons lässt sich dieser Standort weiter eingrenzen da durch das Aufspannen einer Fläche ein Schnittpunkt gebildet werden kann. Durch die Bluetooth Low Energy Technik (BLE) die seit Version 4.0 im Bluetooth Industriestandard spezifiziert wurde, sind diese Beacons äußerst stromsparend und können dabei im Batteriebetrieb je nach eingestelltem Sendeintervall 2-8 Jahre [Quelle](https://www.infsoft.de/technologie/sensorik/bluetooth-low-energy-beacons) senden. Durch die Möglichkeit diese an das Stromnetz anzuschließen, lässt sich die Betriebsdauer beliebig erhöhen.
Auf dem Markt sind bereits spezielle Beacons vorhanden die für das bestimmen von Standorten wie z.B. Messegeländen oder Flughafenterminals konzipiert worden sind. [Estimote, Inc.]() bietet eine spezielle Lösung zu Standortbestimmung innerhalb von Gebäuden an. Dabei lässt sich mit Hilfe eines Tools eine Karte eines Gebäudes erstellen, auf der durch geschicktes platzieren von speziellen [Estimote Beacons]() der Standort des Benutzers auf einer [Karte](https://github.com/Estimote/Android-Indoor-SDK) angezeigt werden kann. Allerdings ist diese Funktion nur exklusiv bei [Estimote, Inc.]() verfügbar, weswegen sie im Rahmen unseres Projektes nicht anwendbar ist. Allerdings lässt sich ein Standort auch ohne interaktive Karte bestimmen, da dafür nur normale Beacons benötigt werden. Die Open Source Projekt [Android Beacon Library](http://altbeacon.github.io/android-beacon-library/index.html) von [Radius Networks](https://www.radiusnetworks.com/) biete eine Anbindung an viele verschiedene Beacon Modelle und Marken die dem [AltBeacon Standard](http://altbeacon.org/) entsprechen. Über z.B. eine Android Applikation lassen sich verschiedene Informationen von einem Beacon empfangen und auslesen.

* Vorteile
    - günstig
    - einfach zu handhaben
    - bekannte, altbewärte Technologie (Bluetooth)
    - Langlebigkeit kann beliebig erhöht werden
    - 

* Nachteile
    - Bluetooth und Wifi-Signale teilen sich eine Frequenz (2,4 GHz), kann zu Störungen führen
    - Signaldämpfung durch Wasser und Metall im Sendebereich ist sehr hoch



### Fazit - Standortbestimmung des Benutzers
Nach unserer Recherche, wäre das Verwenden von Beacons zur Standortbestimmung ideal für unser Projekt geeignet. Durch die günstigen Anschaffungskosten und die Langlebigkeit der einzelnen Beacons lassen sie sich auch in großen Massen innerhalb eines Gebäudes anbringen. Dass die BLE Beacons sowieso schon für die Standortbestimmung eingesetzt werden, unterstützt unsere Ansicht.
Nach unseren Erkenntnissen wäre das Anbringen eines Beacons an jedem Raum bzw. in einem bestimmten, festgelegten Abstand von Vorteil, da dadurch eine Beacon-Raum Zuweisung erreicht werden kann. Anhand dieser Informationen, die im System hinterlegt sind, kann eine Standortbestimmung durch den Benutzer erfolgen. Das Endgerät des Benutzers scannt dabei selbstständig nach Beacons in der Nähe und speichert diese. Wenn der Benutzer einen freien Raum sucht, kann der zuletzt gespeicherte Beacon bzw. seine ID, im Request mit übergeben werden. Damit kann das System den Standort des Benutzers anhand des erkannten Raumes in seiner Nähe in seine Berechnung für einen Raum mit einbeziehen. Zusätzlich kann durch das Auslesen von Entfernungen zwischen Beacon und Benutzer eine genauere Standortbestimmung erfolgen. Dafür muss das System die Position aller Beacons in Abhängigkeit zu ihrem zugehörigen Raum wissen. Um diese Zuordnung einfach zu halten, wäre es ratsam die Beacons direkt an oder über der Tür zum Raum anzubringen. So kann erreicht werden das jede Person im Gang das Signal eines Beacons empfangen kann. Damit Signaldämpfungen minimiert werden, sollten die Beacons in einer Höhe angebracht werden, die über den Köpfen der Personen liegt. Dafür würde sich der Bereich über der Tür, in ca. 2 Meter Höhe anbieten. Um große Abstände zwischen 2 Räumen zu überbrücken, sollte in einem festgelegten Abstand ein Beacon angebracht werden, der im System als Pseudo-Raum zur Positionsbestimmung genutzt werden kann. Damit ein kurzer Weg zwischen Benutzer und gewünschten Raum errechnet werden kann, müssen alle möglichen Laufwege im System vermerkt sein. Dazu gehören auch markante Punkte wie Treppenhäuser, Ein-/Ausgänge und Fahrstühle die ebenfalls mit einem Beacon ausgestattet werden sollten, um die Standortbestimmung des Benutzers zu optimieren.



<!--
* BLE Beacons im Gebäude vor jedem Raum/in bestimmten Abstand
* Endgerät (Smartphone/Tablet) dient als Empfänger
* gefundene Beacon ID und Entfernung zu diesem wird vom Endgerät des Benutzers an den Server gesand (HTTP)
* Server kann die ID zuordnen und anhand der Entfernung den ungefähren Standort des Benutzers ermitteln.
* Standort wird an das Endgerät des Benutzers gesand und bei Bedarf in einer Raumanfrage mitgesand
* Prozess iteriert nach bestimmter Zeit um den aktuellsten Standort festzustellen
* falls kein Standort gefunden werden kann, wird ein Pseudostandort vorgeschlagen (Eingangshalle, Treppenhaus C, ...)
-->


## Laufwegoptimierung (Alleinstellungsmerkmal)

### Einleitung
Das deskriptive Aufgabenmodell der Raumsuche im Kontext der Lehreinrichtungen, setzt das manuelle suchen von Räumen innerhalb des Gebäudes voraus. An diesem Punkt kann viel Zeit verschwendet werden, wenn sich zusätzliche Laufwege von Raum zu Raum ergeben, da festgestellt wird das ein gerade überprüfter Raum schon belegt ist oder nicht das Equipment beinhaltet das vom Benutzer benötigt wird. Es wird also eine Möglichkeit gesucht mit der der Benutzer nicht mit unnötigen Laufwegen belastet wird. Das Problem hängt eng mit der Standortbestimmung des Benutzers zusammen, da ein Laufweg immer von der aktuellen Position des Benutzers zum, vom System berechneten, nächsten freien Raum der den Bedürfnissen und Erwartungen des Benutzers entspricht berechnet werden muss. Im Folgenden wollen wir dieses Problem, das unserem Alleinstellungsmerkmal entspricht, versuchen zu lösen.

Damit ein Weg berechnet werden kann, werden in unserem Fall mindestens 2 Punkte benötigt. Zum einen der aktuelle Standort des Benutzers den wir über Bluetooth Beacons ermitteln werden, und zum anderen der Raum bzw. die Raumnummer des Raumes den der Benutzer benötigt. Um einen optimalen Weg berechnen zu können muss das System Abwägungen über mehrere Räume aber auch mehrere vorhandenen Laufwegen durchführen können.

Wir sind zu dem Entschluss gekommen das die einfachste Methode eine verkettete Liste wäre die im System hinterlegt ist. Dabei muss für jede Lehreinrichtung eine eigene Liste erzeugt werden die sich aus der Anzahl der Räume, ihren Standorten und Rauminhalten sowie besonderen Merkmalen innerhalb des Gebäudes zusammensetzt. Dabei sollte z.B. der Administrator des Systems der Lehreinrichtung, die Möglichkeit haben diese Liste jederzeit zu aktualisieren um auf z.B. Neuanschaffungen oder Umfunktionierung von Gebäuden oder Räumen eingehen zu können.
Jeder Raum im System wird als ein Knoten innerhalb der Liste dargestellt. Dabei sind angrenzende Knoten in der Liste gleichbedeutend mit angrenzenden Räumen im Gebäude. So lässt sich im System ein Weg von Raum zu Raum bzw. von Knoten zu Knoten berechnen. Wie schon erwähnt, sollten auch markante Punkte wie Treppenhäuser oder Fahrstühle als Knoten innerhalb der Liste angegeben werden, da sie ebenfalls als Wegstrecke zwischen zwei Punkten genutzt werden können. Als Ergebnis enthält man eine Liste in der alle Räume und markante Punkte des Gebäudes als Knoten modelliert wurden. Die einzelnen Knoten besitzen verschiedene Attribute die diesen Knoten beschreiben. Jeder Knoten enthält eine eindeutige ID die ihn innerhalb der verketteten Liste eindeutig identifiziert. Die ID lässt sich dabei frei bestimmen und sollte dementsprechend den Typ des Knoten kennzeichnen. Bspw. sollten Raumknoten mit dem Präfix "room" gekennzeichnet werden, wobei ein Treppenhaus das Präfix "stairs" beinhalten sollte. Das sorgt dafür das ein Knoten schneller und gezielter angesprochen werden kann. Zusätzlich sollte die ID die, in der Regel bereits bestehende, Raumnummer beinhalten. Existiert keine Raumnummer, muss der Raum im System mit einer eindeutigen Kennung versehen werden, durch die der Raum sich identifizieren lässt. Dabei sollte dann aber auch berücksichtigt werden das der Benutzer eine Raumnummer oder eine ähnliche Kennzeichnung benötigt um den Raum im Gebäude zu finden. Hierbei muss auf das gegebene Umfeld Rücksicht genommen werden.
Ist jeder Raum im System eindeutig identifiziert und über einen Namen oder ID in der verketteten Liste vorhanden, sollte jeder Knoten zusätzliche Eigenschaften erhalten die für die Berechnung eines Raumes für den Benutzer benötigt werden.
Damit ein Raumvorschlag ausgegeben werden kann, muss das System den kürzesten Weg zwischen Benutzer und möglichen Räumen berechnen. Dabei muss das System eine Entscheidung treffen können welcher der infrage kommenden Räume er dem Benutzer vorschlagen sollte um einen möglichst kurzen Weg zu gewährleisten. Um diesen Weg zu berechnen haben wir uns eine Gewichtung überlegt anhand dessen das System den kürzesten Weg zwischen 2 Punkten berechnen kann. Jeder Knoten besitzt die Gewichtung zu jedem seiner angrenzenden Knoten die vom Algorithmus ausgelesen wird. Wir treffen dabei die Unterscheidung zwischen Verbindungen zu Räumen, Treppenhäusern oder Aufzügen.
In [Schaubild 1]() ist so eine Gewichtung schematisch zum besseren Verständnis abgebildet. Kann das System mehrere Wege zu einem Raum berechnen, wird jeder dieser Wege berechnet und auf seinen Wert hin überprüft. Der Weg mit dem geringsten Gesamtergebnis gilt als passender Raum für den Benutzer. Sollte es noch andere Räume geben die ebenfalls den Bedürfnissen des Benutzers entsprechen, wird die Wegberechnung für diese Räume ebenfalls durchgeführt. Wurden alle infrage kommenden Räume berechnet wird der Raum der das geringste Gesamtergebnis besitzt an den Benutzer ausgegeben. 
Mit dieser Berechnungsmethode für freie Räume in der Nähe des Benutzers kann keine metergenaue Angabe gemacht werden, die Entfernung bezieht sich lediglich auf die angegebene Gewichtung jedes Knoten. Deswegen ist eine passende Gewichtungsangabe dringend erforderlich. Es gilt je nach Gebäude und Abstände der Räume untereinander eine unterschiedliche Gewichtung zu legen damit eine möglichst realistische Wegberechnung aufgestellt werden kann. Diese Gewichtung muss je nach Anwendungsumgebung individuell festgelegt werden. Z.B. müssen Stellen berücksichtigt werden wo zwischen 2 Räumen eine größere Entfernung vorhanden sind als normal. Die Gewichtung muss zwischen diesen 2 Knoten also erhöht werden um die erhöhte Entfernung zu simulieren. Ein Stockwerkwechsel sollte ebenfalls mit einer speziellen Gewichtung angegeben werden. Ein Treppenhaus bedeutet einen zusätzlichen Aufwand und je nach Umgebung auch einen erhöhten Zeitbedarf der in Form einer erhöhten Gewichtung berücksichtigt werden muss. Im Spezialfall das ein Benutzer einen barrierefreien Zugang benötigt, z.B. in Form eines Fahrstuhls, muss zuerst der Weg vom Standort des Benutzers zum Fahrstuhl und von dort aus der Weg zu einem freien Raum berechnet werden. Der Raum sollte dann von der Position des Fahrstuhls aus berechnet werden, da der Benutzer ohnehin dorthin unterwegs sein wird.


### Fazit - Laufwegoptimierung


<!--
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
-->

## Problem - Flexible Räume (Verteilte Anwendungslogik)

### Einleitung
Das innerhalb von Räumen befindliche Equipment ist bis auf wenige Ausnahmen in der Regel nicht fest installiert. So hat der Benutzer die Möglichkeit Gegenstände von Raum zu Raum zu tragen um Räume flexibel nutzen zu können. Im System müssen diese Verschiebungen von Equipment durch irgendeine Methode vermerkt werden um zu gewährleisten das immer bekannt ist was sich in einem Raum gerade an Ausrüstungen befindet. Im Folgenden sollen Möglichkeiten diskutiert werden, die zur flexiblen Raumgestaltung eingesetzt werden können.

### Aktives aktualisieren von Rauminhalten
Zur Lösung dieses Problems muss innerhalb des Systems eine Erkennung erfolgen die die dynamische Equipment Verschiebung erkennen und im System vermerken kann.
Die einfachste Möglichkeit wäre durch das Einbinden des Benutzers möglich. Dabei hat der Benutzer die Aufgabe durch eine Interaktion mit dem System Gegenstände die er aus einem Raum entfernt, aus dem Inventar des Raumes auszutragen. Im nächsten Schritt muss er das entwendete Equipment im gewünschten Raum zum Inventar hinzufügen. Die Umsetzung könnte innerhalb eines einfachen Dialogs zwischen Benutzer und System erfolgen indem er den zu entfernenden Gegenstand des Raumes auswählt und durch z.B. einen Button dem System mitteilt das dieser Gegenstand nicht länger Teil des Inventars des Raumes ist. Im nächsten Schritt trägt der Benutzer den Gegenstand im Inventar des Zielraums ein.
Um zu gewährleisten das der Gegenstand im gewünschten Zielraum auch wieder eingetragen wird sollte ein durchgehender Prozess dafür sorgen das der Benutzer nach dem Austragen eines Gegenstandes aus einem Raum als nächsten Schritt zwingend das erneute eintragen des Gegenstandes in einem Raum vornehmen muss. Andernfalls besteht die Gefahr das ein Gegenstand aus dem System verschwindet und nicht mehr auffindbar ist.
Die Möglichkeit den Benutzer für diese Aufgabe einzubeziehen halten wir allerdings nicht für optimal, da der Benutzer zusätzliche Interaktionsschritte unternehmen muss um dieses Ziel zu erreichen. Eine automatisierte Möglichkeit ohne den Benutzer zusätzlich zu belasten wäre zu bevorzugen.

### Passives aktualisieren von Rauminhalten
Nach einer Recherche zum Thema Gegenstanderkennung bzw. Zuordnung sind wir auf die RFID-Technologie (Radio Frequency Identification) gestoßen. Durch das Markieren von Gegenständen und dem Anbringen eines Lesegeräts lassen sich Gegenstände über die RFID-Technik erkennen. Dabei kommunizieren die beiden Komponenten über elektromagnetische Wellen miteinander, die in der Regel vom Lesegerät ausgesandt, und über kleine Antennen empfangen werden. Gleichzeitig wird bei manchen RFID-Transpondern der benötigte Strom zum Senden von Informationen über diese elektromagnetischen Wellen vom Lesegerät mit übertragen. Das bringt den Vorteil das die RFID Transponder keine eigene Stromquelle benötigen und somit passiv kommunizieren können. Beim Betreten des Empfangsbereiches des Lesegerätes wird sowohl der benötigte Strom übertragen, als auch Daten an das Lesegerät gesendet. Für unseren Kontext sind die passiven RFID-Transponder ideal, da eine Vielzahl an Gegenständen mit diesen Transpondern ausgestattet werden müssen.
Um dieses Prinzip umzusetzen muss jeder Gegenstand der relevant für das System ist, einen Transponder besitzen und eine eindeutige ID besitzen die ihn im System identifiziert. Es gibt verschiedene Transponder die für unterschiedliche Einsatzzwecke geeignet sind. In unserem Fall wären flache, selbstklebende Transponder ein Vorteil da diese schnell und einfach am entsprechenden Gegenstand angebracht werden können. Diese [Labels](http://www.identytag.de/rfid-labels-tags/smart-label/?gclid=CjwKCAiA693RBRAwEiwALCc3u1b_V_f_mLZsRZhpNhQOmtAnyH0GOJu_uTyqLHvTeSdN0y6WsmXX0BoCRgkQAvD_BwE) kommen auch oft in der Versand und Logistikabteilung zur Anwendung um Postsendungen oder Paletten zu kennzeichnen. Das Prinzip lässt sich auch auf unser Projekt übertragen. Um mutwilliger oder unabsichtlicher Zerstörung diese Labels zu verhindern, sollten diese an Positionen angebracht werden die möglichst nicht ersichtlich sind. Die Gefahr das ein Label beschädigt und dadurch nicht mehr erkannt wird besteht allerdings weiterhin.
Die Auswahl des entsprechenden Transponders und Lesegerätes hängt stark vom Anwendungsgebiet und dem Umfeld ab. Unterschiedliche Frequenzbereiche können unterschiedlich große Reichweiten gewährleisten. Diese reichen von einem Zentimeter bis hin zu mehreren Metern. Da in unserem Fall das Lesegerät bzw. die Antenne im Türbereich angebracht werden soll, wird eine Empfangsreichweite von ca. einem Meter benötigt. Diese Entfernung würde man in einem *Ultra hohen Frequenzbereich* (UHF) erreichen. Diese sendet zwischen 860 und 960 MHz [quelle]() und erkennt selbst passive Transponder über mehrere Meter. Die UHF RFID Tags sind dabei papierdünn, günstig und können nahezu überall angebracht werden.
Um unabsichtliches Einlesen von RFID-Transpondern im Türbereich zu vermeiden, sollte die Antenne so ausgerichtet werden, dass sie nur von einem Türrahmen zum gegenüberliegenden Türrahmen empfangen kann. Das anbringen des Lesegerätes bzw. der Antenne am Türrahmen selber wäre allerdings keine gute Idee, da es möglich ist das Personen beim Passieren der Tür die Antenne berühren oder sogar beschädigen. Unserer Meinung nach sollte diese Antenne direkt neben dem Türrahmen wie in [Bild ?]() gezeigt positioniert werden. Um den Empfangsbereich der Antenne weiter einzugrenzen und versehentliches einlesen zu vermeiden, wäre das Anbringen eines kleinen Reflektors um die Antenne herrum von Vorteil.

Um die erkannten Gegenstände bzw. RFID-Transponder im System zu erkennen und Aktualisierungen zu speichern, wird als zusätzliche Komponente eine Verarbeitungseinheit, z.B. ein Raspberry Pi 3, benötigt. Das Lesegerät empfängt dabei die erkannten Gegenstände im Empfangsbereich und sendet diese über ein Verbindungskabel an den Raspberry Pi. Dieser hat in einer Liste alle Rauminformationen inklusive aktuellem Equipment gespeichert und kann so automatisch auf Änderungen reagieren. Wird vom Lesegerät ein Gegenstand erkannt, wird die ID des Gegenstandes ausgelesen und an den Raspberry Pi gesendet. Dieser gleicht die ID mit seiner vorhandenen Liste an Equipment ab. 

Ist die ID in der Liste vorhanden, also der Gegenstand ein Teil des Inventars, trägt die Anwendung auf dem Minicomputer diesen Gegenstand aus der Inventarliste aus. Zeitgleich wird ein HTTP-Request an den Server gesendet um ihn über die Aktualisierung zu informieren. Der Server liest den Body des Requests aus in dem die Änderung (Gegenstand ausgetragen/eingetragen) und die ID des entsprechenden Gegenstandes vermerkt sind. Der entsprechende Gegenstand wird im Datensatz wo alle Gegenstände und ihre aktuelle Position vermerkt sind ausgetragen. Um zu vermeiden das ein Gegenstand nach dem Austragen im System nicht mehr vorhanden ist, wird die ID des Gegenstandes mit dem Zeitpunkt des Austragens in eine spezielle Liste geschrieben. Auf dieser bleibt er solange vorhanden, bis er in einem anderen Raum erkannt wird. Somit ist gewährleistet das ein Gegenstand immer im System bekannt ist. Er kann dabei den Status "Im Raum vorhanden" oder "Auf dem Weg zu einem Raum" besitzen. Das dient uns als zusätzliche Überprüfung über den Verbleib des Gegenstandes. Der Administrator des Systems kann so regelmäßig prüfen ob Gegenstände unterwegs abhandengekommen sind und Nachforschungen über den Verbleib anstellen.

Erkennt das Lesegerät eines Raumes einen Gegenstand der nicht Teil des aktuellen Rauminventars ist, weiß der Minicomputer das dieser Gegenstand den Raum gerade betreten hat. Bevor er den Gegenstand aber in seiner Inventarliste vermerkt, fragt er den Status des Gegenstandes im System ab. In Fehlerfällen kann es möglich sein das ein Gegenstand versehentlich Teil eines Rauminventars geworden ist, obwohl das nicht beabsichtigt war. Wird ein Gegenstand zu nahe an das Lesegerät eines Raumes getragen kann er versehentlich dort in das Inventar eingetragen werden. Das kann passieren, wenn der Gegenstand nur bis in den Empfangsbereich, aber nicht durch diesen hindurch getragen wird. Er wird also nur einmal vom Lesegerät erkannt und dann in das Inventar des Raumes eingetragen. Verlässt der Gegenstand den Empfangsbereich in Richtung Ausgang, kann er vom System nicht als Gegenstand erkannt werden der den Raum verlässt. Wird der Gegenstand dann in den eigentlichen Raum getragen den der Benutzer beabsichtigt, würde er vom Lesegerät erkannt und ebenfalls im Rauminventar des zweiten Raumes eingetragen. Damit verhindert wird das ein Gegenstand im System in 2 Räumen gleichzeitig vorhanden ist, muss eine Überprüfung erfolgen. Wenn der Gegenstand bereits Teil eines anderen Rauminventars ist, muss er aus diesem ausgetragen und in das Inventar des zweiten, neuen Raumes eingetragen werden. Dazu sendet der Server dem ersten Minicomputer über einen HTTP-Request den Befehl zum Austragen aus seinem Inventar und nach Bestätigung im Response dem 2. Minicomputer die Erlaubnis den erkannten Gegenstand in sein Inventar einzutragen.
Den Minicomputer als Verarbeitungseinheit haben wir im [Architekturmodell]() und [Kommunikationsmodell]() ergänzt.



### Fazit - Problem - Flexible Räume (Verteilte Anwendungslogik)

<!--
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
-->

## Problem - Verschiedene Eigenschaften der Räume (ARBEITSTITEL)
### Einleitung
Um im Ablauf des Systems zu gewährleisten das ein Raum auch tatsächlich frei ist wenn der Benutzer ihn erreicht, wird eine Methode benötigt die dieses Problem lösen kann. Im Folgenden beschäftigen wir uns mit der Zugangsberechtigung zu einem Raum und wie der Benutzer Zugang zu diesem bekommt. Außerdem bschäftigen wir uns mit unterschiedlichen Raumtypen. 

### Zugang zum Raum
Um gewährleisten zu können das ein Raum frei ist wenn der Benutzer ihn erreicht, muss eine Zugangsberechtigung eingerichtet werden. Bei offenen Räumen besteht die Gefahr das Personen diesen einfach belegen ohne das System zu nutzen. Der Raum wird im System als frei angezeigt obwohl das nicht der Fall ist. Um das Problem zu lösen wäre das generelle Abschließen aller Räume eine Möglichkeit. Um Zugang zum Raum zu bekommen muss das System diesen gewähren indem geprüft wird ob ein Benutzer eine aktive Reservierung für diesen Raum besitzt. Unserer Idee nach kann das öffnen über einen vom System generierten Code erfolgen der bei der Reservierung an das Endgerät des Benutzers gesendet wird. Um die Tür zu öffnen muss z.B. dem Minicomputer den wir in [Verweis]() als neue Komponente des Systems identifiziert haben, dieser Code mitgeteilt werden. Stimmt der Code mit dem im System hinterlegten Raum überein, kann die Tür geöffnet werden. Um das zu realisieren muss das System sowohl dem Benutzer als auch dem Minicomputer innerhalb des Raumes diesen Code über HTTP-Requests mitteilen. Damit Benutzer und Minicomputer im Raum miteinander Kommunizieren können haben wir Bluetooth und NFC als Möglichkeiten in betracht gezogen. Bluetooth, wie schon bei der Standortbestimmung des Benutzers verwendet, ist eine weit verbreitete Datenübertragungstechnik die über kurze Distanz gut funktioniert. Der im Endgerät des Benutzers gespeicherte Code wird dem Minicomputer im Raum über kurze Distanz mitgeteilt. Der Benutzer muss sich dabei unmitelbar vor der Tür befinden damit der Minicomputer den Code empfangen kann. Um auszuschließen das der Code von fremden Personen abgehört und missbraucht wird, sollte der Code im System dynamisch und zufällig generiert werden. Für zusätzliche Sicherheit sollte erst durch eine Bestätigung des Benutzers dieser Code per Bluetooth an den Minicomputer übertragen werden. Damit der Minicomputer das Bluetooth Signal empfangen kann muss das Endgerät des Benutzers als sendender Beacon umfunktioniert werden. Durch Bluetooth 4.0 und die Einführung der Low Energy Technik muss keine direkte Verbindung mehr hergestellt werden was uns hier zugute kommt [Quelle]().
Eine der größeren Änderungen bei dem automatischen Entsperren der Räume ist die Notwendigkeit das ein Computer das öffnen übernimmt. Dementsprechend muss der Schließmechanismus so umgerüstet werden das dies automatisch erfolgen kann. Das kann mit elektronischen Schließzylindern ermöglicht werden die in vielen Varianten angeboten werden. In unserem Fall wäre eine drahtlose Kommunikation zwischen Minicomputer und Schließsystem von Vorteil. Diese lässt sich ebenfalls über Bluetooth regeln. 
NFC als Alternative zu Bluetooth funktioniert aus der Perspektives Benutzers genauso. Allerdings muss der Benutzer sein Endgerät auf ein NFC-Sensorfeld legen da die Kommunikation nur über wenige Zentimeter funktioniert.


Die nicht-automatisierte Alternative wäre das Risiko zu akzeptieren das bei unverschloßenen Räumen Personen den Raum einfach besetzen ohne das System zu nutzen. Wie die Lehreinrichtung mit diesem Problem intern umgeht ist ihr selber überlassen.

### Raumtyp "Stiller Arbeitsraum"
Da wir in unserem System einen besonderen Raum haben in denen mehrere verschiedene Benutzer gleichzeitig und alleine arbeiten können, kann für diesen Raumtyp die bisherige Lösung zur Raumbuchung so nicht angewand werden. Die besondere Eigenschaft an diesem Raumtyp bezieht sich darauf das die Benutzer in der Regel zu unterschiedlichen Zeiten anfangen zu arbeiten und somit eine einzige Buchungszeit kontraproduktiv wäre. Um das Problem zu lösen sind wir zu dem Entschluss gekommen das dieser Raum ein besonderes Datenschema benötigt. Ein Raum der zum stillen arbeiten genutzt wird, hat eine bestimmte Slotzahl an Arbeitsplätzen zur Verfügung in die Benutzer eingetragen werden können. Bucht ein Benutzer einen Raum zum stillen arbeiten, wird er mit dem Zeitstempel seiner Buchung im System vermerkt. Der Raum wird wieder komplett freigegeben wenn der letzte Benutzer den Raum nicht weiter benutzt. So kann zusätzlich eine sinnvolle Raumbelegung erreicht werden, da dynamisch Räume zum stillen arbeiten erzeugt und wieder entfernt werden.

### Fazit - Verschiedene Eigenschaften der Räume (ARBEITSTITEL)



## Problem - Personenerkennung im Raum

### Einleitung
Zur Findung einer Methode für effektive Personenzählung haben wir ein
Brainstorming durchgeführt und alle Methoden aufgeschrieben die uns eingefallen
sind. Darunter befinden sich Methoden wie die algorithmische Personenzählung
durch Wärmebild- und Videokamera, aber auch die Erkennung durch Lichtschranken,
Druckplatten und Bewegungsmelder.
Im Folgenden werden die einzelnen Methoden kurz erläutert und einige Vor- und
Nachteile aufgezählt.

### Druckplatten
Bei der Zählung von Personen durch eine druckempfindliche Matte im Eingangs-
bereich kann durch algorithmen sowohl die Anzahl der Personen als auch die
Laufrichtung der einzelnen durch analyse der drucksensoren bestimmt werden.
Diese Matten können im Eingangsbereich zu den Räumen unter Fußmatten versteckt
werden und sind robust genug so dass keine Schäden durch Equipment welches
über diese gerollt wird entstehen sollte. Allerdings ist auf der Webseite des
[Anbieters](http://www.instacounting.com/intro.html) auf den wir uns bei dieser
Methode beziehen keiner Information dazu zu finden ob ein Gegenstand, zum
Beispiel ein Whiteboard mit rollen, beim Schieben über die Matte als Person
erkannt wird oder nicht. Hierfür müsste sofern möglich ein eigenes System
entwickelt oder das bestehende um algorithmen zur Erkennung von bestimmten
Merkmalen, wie z.B. des Reifenabstandes, und der Zuordnung dieser  erweitert
werden. Weiterhin kann es durch das Verrutschen der Matte dazu kommen das Türen
nicht mehr richtig schließen. Dies kann durch das Befestigen der Matte auf dem
Boden behoben werden. Außerdem muss die Matte eine zu ermittelnde Mindestgröße
besitzen, damit Menschen Mit größerer Schrittweite auch von dieser erkannt
werden.

### Lichtschranken und Bewegungsmelder
Einige Methoden können zu einer Gruppe zusammengeschlossen werden, da diese
in etwa ähnlich funktionieren oder ähnliche Vor- und Nachteile besitzen. So
bilden die Bewegunsmelder und Lichtschranken zum Beispiel eine solche Gruppe.
Die Vorteile dieser Methoden sind zum Einen die Datenschutz unbedenkliche
Verwendung, da durch diese Methoden weder Bild- noch Tonmaterial aufgenommen
wird und somit auch keine Personenbezogenen Daten erhoben werden können.
Der Große Nachteil dieser Methoden ist, dass durch die Verwendung eines
einzelnen Gerätes weder die Laufrichtung noch die genaue Anzahl der Personen
bestimmen kann. So kann es zum Beispiel dazu führen, dass mehrere Personen
parallel oder leicht versetzt einen Raum betreten, vom System aber nur als
eine Person erkannt werden, da die Sensoren nur einen durchgängigen Impuls
senden. Desweiteren ist der große Abdeckungsbereich des Bewegungsmelders
eher hinderlich, da es dadurch auch zu Fehldeutungen kommen kann, wenn
ein Benutzer sich der Tür nur nähert, ohne hindurchgehen zu wollen.
Dieses Problem könnte für den Bewegungsmelder gelöst werden, indem künstlich
sein Wirkungsbereich eingedämmt wird. Außerdem könnte durch die Verwendung
mehrerer Geräte sowohl eine genauere bestimmung der Anzahl sowie der
Laufrichtung von Personen bestimmt werden. Dies trifft nicht zu, wenn
mehrere Personen gleichzeitig mit Unterschiedlichen Laufrichtungen hindurch
laufen. Ein weiteres Problem tritt auf, wenn Gegenstände durch den
Wirkungsbereich geschoben werden, da der Sensor nicht zwischen Mensch und
Gegenstand unterscheiden kann. Um das Problem zu beseitigen könnte als
die Dauer des Impulses gemessen werden und anhand dessen entscheidet
das System ob es sich um einen Gegenstand oder eine Person handelt.

### Zählermechanik (ARBEITSTITEL)
Die Methode eines Zählers oder einer Mechanik im Eingangsbereich oder der
Tür haben wir als einzelne Methode ausgeschlossen, da zwar eine effektive
Erkennung einer Interaktion mit einem Raum erkannt werden kann, jedoch
nicht festgestellt werden kann, ob der Raum betreten oder verlassen wird,
oder ob nur die Tür geöffnet wird. Es könnte durch die Kombination mit
einer anderen eine effektivere Methode generiert werden. Weiterhin könnte
durch verwendung von Algorithmen bestimmt werden, ob eine Tür zum Beispiel
mehrfach aufgehalten wird und anhand der Zeit die eine Tür offen ist, wie
viele Personen möglicherweise hindurchgegangen sind. Dies führt allerdings
zu keiner genauen Anzahl an Personen im Raum.

### Wärmebildkameras
Eine effektive Methode welche auch die persöhnlichen Daten der Benutzer
schützen würde wäre die Verwendung einer Wärmebildkamera mit zugehörigem
Algorithmus zur Personenzählung. Diese erreicht abhängig vom Algorithmus
eine sehr hohe Genauigkeit und ermöglicht wie zuvor gesagt die anonyme
Zählung der Personen in einem Raum. Wir haben diese Methode allerdings in
diesem Projekt ausgeschlossen, da durch die hohen Anschaffungskosten
eine Verwendung im Nutzungskontext einer Lehreinrichtung mit mehreren
hundert Räumen zum momentanen Zeitpunkt nicht denkbar ist.
Durch die Weiterentwicklung dieser Technologie sollte die Verwendung,
falls die Anschaffungskosten wesentlich geringer ausfallen, nocheinmal
im Rahmen dieses Projektes evaluiert werden.

### Video-/Bildanalyse
Eine ähnliche und kostengünstigere Alternative bietet eine Analyse von
Videomaterial. Der wichtigste Unterschied zur Wärmebildkamere ist hierbei
allerdings die fehlende Anonymität der Benutzer, da ein Video aus dem
inneren des Raumes aufgenommen wird. Es kann ebenfalls durch Verwendung
der richtigen Algorithmen eine hohe Genauigkeit erziehlt, und so
eine effektive Personenzählung durchgeführt werden. Da die Analyse
solcher Videos sehr viel Rechenleistung benötigt und bei größeren Räumen
die Videos ebenfalls hochauflösender sein müssen könnte in diesem Projekt
eine Analyse von einzelnen Bildern, welche in festgelegten Intervallen
aufgenommen werden, durchgeführt werden. Dadurch könnte eine gute
Schätzung der Personen die sich in einem Raum befinden erreicht werden.
Die Frage des Datenschutzes müsste bei der Umsetzung des Projektes mit
dem Datenschutzbeauftragten der jeweiligen Lehreinrichtung besprochen
werden. Allerdings sollte darauf geachtet werden dass die Bilder nur
Lokal analysiert werden, danach gelöscht und nur das Ergebnis also
zum Beispiel "2 Personen" weiterverwendet wird.

### NFC
Eine weitere Möglichkeit der Benutzererkennung innerhalb von Räumen wären der Einsatz von NFC. 
Fast jedes neue Smartphone besitzt die Möglichkeit NFC einzusetzen. Bekannt ist diese Methode z.B. durch das kontaktlose Bezahlen mit einem Smartphone. Die Reichweite von NFC liegt aktuell nur bei [wenigen Zentimetern](https://developer.android.com/guide/topics/connectivity/nfc/index.html), weswegen ein passives Erkennen von Benutzern nur schwer möglich ist. Der benutzer müsste, um über NFC erkannt zu werden, sein Smartphone sehr nahe an das Lesegerät halten, was eine aktive Interaktion des Benutzers vorraussetzt. In unserem Projektkontext halten wir diese Methode für kontraproduktiv, da der Benutzer sich aktiv am Scanner verifizieren muss was eine zusätzliche Aktivität bedeutet.


### BLE Beacons
Der Einsatz von Beacons wurde bereits bei der Standortbestimmung von Benutzern angesprochen. Ebenfalls anwendbar wäre diese Methode bei der bestimmung von Benutzern innerhalb von Räumen. Es wird also nach dem Standort innerhalb eines bestimmten Bereiches gesucht. Dabei müsste das Endgerät des Benutzers als sendender Beacon umfunktioniert werden und ein Empfängerät scannt innerhalb eines Raumes nach Bluetooth Signalen. Sofern sich innerhalb des Raumes bzw. in einem bestimmten Umkreis des Empfängers Bluetooth Signale empfangen lassen, kann das System davon ausgehen das sich eine oder mehrere Personen im Raum befinden. Sobald das Signal des Benutzers eine bestimmte Entfernung überschreitet, weis das System das dieser Benutzer den Raum vermutlich verlassen hat. Falls das Signal des Benutzers wegen Störungen im Frequenzbereich vorrübergehend nicht erreicht werden kann, sollte das System den Benutzer nicht sofort als nicht mehr vorhanden , bzw. den Raum als leer kennzeichnen, sondern eine gewisse Zeitspanne einräumen in der weiter nach Benutzersignalen innerhalb eines Raumes gescannt wird. Ist nach Ablauf dieser Zeit kein Benutzer erkannt worden, wird der Raum wieder im System für andere Benutzer freigegeben. Der offensichtliche Nachteil dieser Technologie für die Benutzererkennung ist, dass nicht jeder Benutzer zwingend ein empfangsfähiges Endgerät dabei haben muss. Da immer nur ein Benutzer einen Raum für z.B. eine Gruppe von Personen bucht, besteht die Gefahr das die Anzahl der Personen im Raum nicht erkannt werden kann. Verlässt der Benutzer der den Raum gebucht hat den Raum wird er vom System nicht mehr erkannt und der Raum wird wieder freigegeben obwohl sich noch arbeitende Personen im Raum aufhalten. Ein zusätzliches Problem besteht im gelegentlichen verlassen des Raumes, z.B. für den Toilettenbesuch. Wird die im System hinterlegte Zeit überschritten, wird der Raum wieder freigegeben obwohl das vom Benutzer eigentlich nicht gewollt ist.


### Fazit - Personenerkennung im Raum


<!--
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
-->

<!--
## Raum finden - Benutzerperspektive (ARBEITSTITEL)
Wie gerade angesprochen, wollen wir das Endgerät des Benutzers in den Prozess der Raumsuche einbeziehen. Es liegt nahe das die Funktionen des Systems über eine Applikation auf dem Smartphone oder Tablet dem Benutzer zur verfügung gestellt werden. Wie in der [Benutzungsmodellierung]() festgestellt, muss das System verschiedene Aufgaben bewältigen können die, aus der Sicht des einfachen Benutzers, in der Regel mit der Raumsuche zusammenhängen. Da die ermittelten Aufgaben des Benutzers sich ledigich in der Art der Suche unterscheiden, lassen sich viele der Aufgaben durch eine Filterfunktion innerhalb der Anwendung darstellen. Der Benutzer wählt bei seiner Anfrage die Optionen aus, die seine Bedürfnisse erfüllen und die Anwendung schickt diese Informationen an den Server. Dieser kann wiederum die Anfrage auswerten und anhand der spezifizierten Filter einen Raumvorschlag ermitteln und dem Benutzer zur verfügung stellen.
## Raum finden - Systemperspektive (ARBEITSTITEL)
Damit das System einen Raumvorschlag ausgeben kann muss es verschiedene Berechnungen aufstellen. 
Wie eben begründet, kann es Verantalltungen geben die wöchentlich wiederholt werden, wo also abzusehen ist, wann welcher Raum belegt ist. Diese Informationen dienen als Grundgerüst auf denen alle weiteren Berechnungen aufbauen. Durch das einbinden von bekannten, aktuell belegten Räumen, reduziert sich die Auswahl an möglichen Räumen für den Benutzer. Der vom Benutzer abgegebene Request für einen freien Raum dient als nächste Berechnungsstufe. Dafür werden die mitgelieferten Filter des Benutzers aus dem Request ausgelesen und zwischengespeichert. Der Server kann nun die freien Räume auf gewünschte Rauminhalte abgleichen und bekommt eine Liste mit Möglichkeiten für den Benutzer. Um aus dieser Liste einen Raum für den Benutzer zu bestimmten wird noch der Standort des Benutzers benötigt, um in dessen Abhängigkeit einen Raum auszugeben, der sich in der Nähe des Benutzers befindet.
## Zugang zum Raum (ARBEITSTITEL)   
### Raum buchen - Vorgehen
__1. Der Benutzer steht vor einem vermeintlich freien Raum und möchte diesen belegen.__
* Anfrage starten indem der Benutzer einen spezifischen Button auf dem Endgerät klickt.
* Benutzer wird aufgefordert sich der Tür des entsprechenden Raumes zu nähern.
* Der Minicomputer des Raumes sendet konstant ein BLE Signal mit seiner Raumnummer.
* Das Endgerät erkennt das Signal des Minicomputer des Raumes und speichert die Raumnummer.
* Das Endgerät sendet eine Anfrage mit der Raumnummer an den Server und fragt den Status ab.
* Der Server gibt als Antwort entweder "Raum ist verfügbar" oder "Raum ist nicht verfügbar" zurück.
* Ist der Raum verfügbar kann der Benutzer über einen Button die Raumbuchung starten.
* Das Endgerät schickt einen Request an den Server mit der Anfrage auf Raumbuchung und erhhält einen einmalig generierten Schlüßel zurück. Zeitgleich schickt der Server den Schlüßel an den Minicomputer innerhalb des Raumes.
* Der Benutzer muss sein Endgerät in die Nähe der Tür bewegen, so das der Minicomputer den vom Handy gesendeten Schlüßel erkennen kann.
* Stimmen die beiden Schlüßel überein, wird der Raum entsperrt.
* Zeitgleich wird die Raumbuchung durch einen Request an den Server bestätigt und in der DB hinterlegt.
* Smartphone des Benutzers  Sender und Empfänger über BLE
    - Sender
        - sendet den generierten Schlüßel an den Minicomputer
    - Empfänger
        - empfängt Raumnummer vom Minicomputer
        - empfängt Statusmeldung vom Minicomputer
* Minicomputer  Sender und Empfänger über BLE
    - Sender
        - sendet die Raumnummer seines Raumes an Endgeräte im nahen Umfeld
        - sendet Status des Vergleichs des Schlüßels an den Benutzer
    - Empfänger
        - Empfängt den generierten Schlüßel vom Endgerät des Benutzers um ihn zu vergleichen
__2. Der Benutzer hat einen Raumvorschlag und einen Schlüßel vom System erhalten, steht nun vor dem Raum und möchte ihn buchen.__
* Buchung starten indem der Benutzer einen spezifischen Button auf dem Endgerät klickt.
* Der Benutzer muss sein Endgerät in die Nähe der Tür bewegen, so das der Minicomputer den vom Handy gesendeten Schlüßel erkennen kann.
* Stimmen die beiden Schlüßel überein, wird der Raum entsperrt.
* Zeitgleich wird die Raumbuchung vom Minicomputer durch einen Request an den Server bestätigt und in der DB hinterlegt.
* Smartphone des Benutzers  Sender und Empfänger über BLE
    - Sender
        - sendet den generierten Schlüßel an den Minicomputer
    - Empfänger
        - empfängt Statusmeldung vom Minicomputer
* Minicomputer  Sender und Empfänger über BLE
    - Sender
        - sendet Status des Vergleichs des Schlüßels an den Benutzer
    - Empfänger
        - Empfängt den generierten Schlüßel vom Endgerät des Benutzers um ihn zu vergleichen
-->
## Risiken
### Einleitung
Im Entwicklungsprozess für technologiebezogene Gestalltungslösungen sind uns verschiedene Probleme aufgefallen die noch berücksichtigt werden müssen.

### Missbrauch des Systems
* unnötige Anfragen
* nicht benutzen von Räumen trotz reservierung/buchung

### Das System wird nicht genutzt (ARBEITSTITEL)
Da in manchen Lehreinrichtungen Räume existieren die sich nicht abschließen lassen oder generell nicht abgeschloßen werden, besteht die Gefahr das Raumsuchende Personen sich einfach in einen Raum setzen der bereits schon reserviert oder gebucht worden ist. Das System wird also einfach umgangen, was den gesamten Ablauf gefährden kann.


## User Interface


## Evaluation


## Fazit - 1. Gestalltungslösung