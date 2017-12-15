# Gestalltungslösung

## Einleitung
Da die Grundbausteine für die Entwicklung einer optimalen Gestaltlösung durch die Benutzer und Aufgabenmodelierung sowie die Aufführung der Contentelemente nun gegeben sind, geht es jetzt darum eine konkrete Lösung für das Nutzungsproblem zu finden. In diesem Teil der Dokumentation befassen wir uns mit der Erarbeitung und Bewertung von möglichen Technologien für einzelne Unterprobleme, die Zusammenstellung der Komponenten des Systems, ihre Abhängigkeiten und Kommunikationswege untereinander, sowie die Ausarbeitung eines User Interfaces.


## Idee
Um das Ziel, einen Raum zu finden der den Bedürfnissen und Erwartungen des Benutzers entspricht, zu erfüllen, haben wir uns in einer ersten Idee ein 3-teiliges System überlegt bestehend aus Server, Client und Datenspeicher. Der Server stellt dabei die Verarbeitungseinheit und zentrale Instanz des Systems dar. Er bezieht dabei Informationen aus einer Datenbank, sowie vom Client. Innerhalb der Datenbank sind sowohl Informationen über alle verfügbaren Räume und ihre aktuellen Rauminhalte und Belegung der Räume, sowie Informationen über die statischen, wöchentlich wiederkehrenden Veranstalltungen.
Der Client stellt dabei die Schnittstelle zwischen Benutzer und System da. Unserer Meinung nach wäre eine Anwendung auf Smartphone oder Tablet des Benutzers eine gute Möglichkeit, weil davon ausgegangen werden kann, das der Benutzer diese Geräte immer dabei hat. Laut einer Studie der [Bitkom research](https://www.bitkom.org/Presse/Anhaenge-an-PIs/2017/02-Februar/Bitkom-Pressekonferenz-Smartphone-Markt-Konjunktur-und-Trends-22-02-2017-Praesentation.pdf) benutzen 78% der befragten Personen ab 14 Jahren im Januar 2017 ein Smartphone. Mit einer Nutzerzahl von 54 Millionen Menschen in Deutschland (Tendenz steigend) bietet es sich an das Smartphone oder Tablet in den Prozess einzubeziehen. Das einbeziehen von Systemkomponenten des Benutzers hat außerdem den Vorteil das eine Standortunabhängige Nutzung des Systems möglich ist. So müssen nicht auf z.B. Terminals oder Anzeigetafeln an festen Positionen im Gebäude, Informationen über Räume bezogen werden. 
In einem Brainstorming über den groben Aufbau und Funktion des Systems haben wir mehrere Probleme identifiziert, auf die in diesem Teil der Dokumentation eingegangen werden soll. Darunter zählen z.B. eine "Standortbestimmung" der Benutzer innerhalb des Gebäudes um einen konkreten Raumvorschlag liefern zu können, oder auch die Verifizierung der Anwesenheit von Gegenständen oder Personen innerhalb von Räumen. Da es je nach Umfeld sein kann, das es Räume gibt die nicht von jeder Personengruppe genutzt werden kann oder darf, muss die Zugangsberechtigung für Räume geklärt werden, so das gewährleistet wird das nicht jeder Zugang zu allen Räumen hat. 

<!-- Liste mit Problemen machen?
* Standortbestimmung
* Gegenstände im Raum
* Personen im Raum
* Zugang zum Raum
-->

## Datenstruktur/Relevante Informationen (ARBEITSTITEL)

### Einleitung
Um im System Informationen verarbeiten zu können, müssen diese erst einmal definiert werden.
Im folgenden möchten wir kurz auflisten, welche Informationen im System eine Rolle spielen und warum.
Dabei unterscheiden wir zwischen Benutzerinformationen, Rauminformationen, Filtermöglichkeiten, Belegungspläne und sonstige relevante Eigenschaften.

### Benutzerinformationen
Eine Verifizierung eines Benutzers des Systems ist notwendig, da gewährleistet werden muss, dass nur Angestellte und Mitglieder der Lehreinrichtung Zugang zu den System- bzw. Rauminformationen erhalten. Deswegen sollte beim Systemzugriff geprüft werden ob der Benutzer eine Zugangsberechtigung für das System besitzt. Eine einfache Möglichkeit das zu lösen, wäre eine authentifizierung über eine Lehreinrichtungskennung, z.B. eine spezielle E-Mail Adresse. Diese E-Mail Adresse muss bei der Erstinstallation angegeben werden, und wird bei jeder Interaktion mit dem System übertragen um den Benutzer zu verifiziern. Der Server besitzt eine Liste mit berechtigten E-Mail Adressen, und kann so überprüfen ob die Anfrage des Benutzers bearbeitet werden darf. Aus der E-mail Adresse lassen sich dann auch, z.B. über das Format der Adresse oder anhand einer weiteren Liste im System, die Zugangsberechtigungen für bestimmte Räume prüfen. Falls die Lehreinrichtung nicht möchte das ein bestimmter Raum von jeder Benutzergruppe genutzt werden kann, lässt sich so eine Berechtigungsabfrage durchführen, ohne das der Benutzer zusätzliche Interaktionsschritte unternehmen muss. Außerdem muss der Standort des Benutzers festgelegt werden damit er durch das System bei der Raumauswahl berücksichtigt werden kann. Zu den infrage kommenden Technologien dazu, kommen wir in einem späteren Abschnitt.

### Rauminformationen
Um einen Raum im System eindeutig identifizieren zu können, muss die Raumnummer als Ausgangspunkt hinterlegt werden. Zusätzlich muss sämtliches, für das System relevante, Equipment aufgelistet werden, damit im späteren Verlauf eine Suche nach einem bestimmten Raum ermöglicht werden kann. Dieses Equipment kann je nach Raum oder Raumtyp unterschiedlich ausfallen und hängt in der Regel von seinem gedachten Verwendungszweck ab. Räume die für Präsentationszwecke gedacht sind, besitzen in der Regel ein Präsentationsmedium wie etwa Beamer, Fernseher, OHP oder Whiteboard. Zusätzlich werden, wie in fast jedem Raum, Sitzmöglichkeiten benötigt. Die Größe, und damit die Anzahl der Sitzplätze sind ebenfalls relevant, da nur so auf einen Raum für ein bestimmte Anzahl an Personen hingearbeitet werden kann. Die dynamischen Rauminhalte, und mit welchen Technologien diese aktuallisiert werden können, werden in einem späteren Abschnitt behandelt. Diese Rauminhalte sind je nach Lehreinrichtung und vorhandenem Equipment variabel und müssen sich an dem spezifischen Kontext ausrichten. Eine weitere relevante Information ist der Standort des Raums um einen kurzen Weg zwischen Benutzer und gesuchten Raum zu berechnen. Dieser lässt sich im Idealfall aus der Raumnummer ableiten, indem diese so aufgebaut ist das das Gebäude, Stockwerk und der Gang daraus ersichtlich wird. Auch diese Informationen sind variabel und müssen auf das jeweilige Umfeld angepasst werden. Alternativ lassen sich diese Informationen auch einzeln im System abspeichern.

### Filtermöglichkeiten
Damit der Benutzer nach einem Raum suchen kann, muss das System ihm die Möglichkeit bieten auf Wünsche und Möglichkeiten einzugehen. Dazu sollte der Benutzer die Möglichkeit haben durch spezielle Filter seinen Raumwunsch zu konkretisieren. Diese Filter betreffen z.B. den Rauminhalt der benötigt wird damit der Benutzer seine Arbeit verrichten kann. Der Benutzer kann durch auswählen seinen Raumwunsch eingrenzen und dem System mitteilen der diese Wünsche in seiner Raumauswahl berücksichtigt. Der Benutzer sollte aber auch grundlegendere Informationen angeben können, wie z.B. den barrierefreien Zugang zu Räumen. Da kann der Benutzer z.B. angeben das er einen Fahrstuhl benötigt um die Etage im Gebäude wechseln zu können.

### Belegungspläne
Da in einer Lehreinrichtung oft Veranstaltungen existieren die im wöchentlichen Muster wiederholt werden, existiert dazu meist ein Belegungsplan. Dieser gibt an in welchem Raum in welcher Zeitspanne eine Veranstalltung, also eine Belegung des Raumes statfindet. Diese Liste mit Informationen ist für die Ausgabe von freien Räumen an den Benutzer eine wichtige Grundlage. Mit ihr können viele Räume schon vorab ausgeschloßen werden, bevor sie in die Berechnung mit einfließen. Zusätzlich muss eine Liste existieren die Auskunft über die dynamische Belegung von Räumen aufschluß gibt. In dieser Liste sollten alle aktuellen Belegungen von Räumen verzeichnet sein, damit das System bei seinen Berechnungen unterstützt wird. Die Liste sollte mit Informationen gefüllt sein die Aufschluß über den belegten Raum, die Anzahl der Personen in diesem Raum, Start- und Endzeitpunkt der Belegung und die Benutzeridentifizierung gibt.

### Sonstige Eigenschaften
Grundlegende Informationen wie alle zur Verfügung stehenden Räume innerhalb der Lehreinrichtung, die Anzahl der Gebäude, Stockwerke und Gänge, Positionen von Ein-/Ausgängen, Treppenhäuser, Notausgänge, Fahrstühle und eventuell weitere Informationen die vom Umfeld abhängen, sind ebenfalls eine wichtige Dangrundlage für das System. Mit diesen Informationen und denen der Räume und Benutzer, können im System später Raumvorschläge für den Benutzer effizient berrechnet werden.

### Fazit - Datenstruktur/Relevante Informationen (ARBEITSTITEL)
Die benötigten Informationen im System sollten innerhalb eines persistenten Datenspeichers, bspw. einem Datenbankserver hinterlegt sein. Der Server als Verarbeitungseinheit erfragt die jeweils auf die Anfrage passenden, benötigten Daten aus dem Speicher und verarbeitet diese. Diese Auflistung an benötigten Informationen befindet sich als ausführliche Beschreibung im [Anhang](). In dieser Liste werden im Verlauf der Ausarbeitung auch die benötigten Variablen und Listen in Code aufgeführt werden.

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
Wie bereits erwähnt soll unser System aus drei Hauptkomponenten bestehen. Als Server dient uns ein in Node.js aufgesetzter Webserver der mittels HTTP-Requests mit den anderen Systemkomponenten kommunizieren kann. Der Webserver dient als zentrale Verarbeitungseinheit für das System und behandelt alle eingehenden Anfragen der Benutzer. Die Kommunikation funktioniert sowohl in Richtung Client als auch in Richtung Datenbankserver über einfache HTTP-Requests die im besten Fall über ein geschloßenes Netzwerk innerhalb der Lehreinrichtung arbeitet. Damit besteht die Vorraussetzung das der Benutzer sich im selben Netzwerk wie das System befinden muss, bspw. über eine aktive WLAN-Verbindung. Damit würde eine zusätzliche Authentifizierung der Benutzer erfolgen, da für gewöhnlich nur Personen Zugang zu einer WLAN-Verbindung haben, die dort auch Mitglied oder angestellt sind. Da aber warscheinlich nicht alle Lehreinrichtungen eine WLAN-Verbindung stellen können, sollte der Zugang zum System auch über eine Mobilfunkverbindung mit dem Internet möglich sein. Als Grundvorraussetzung um das System nutzen zu können, ist aber trotzdem eine aktive Internetverbindung an die sich der Node.js Server anknüpfen kann. In unserem Projekt gehen wir davon aus das die Lehreinrichtung sowohl einen verfügbaren Internetanschluß als auch eine WLAN Verfügbarkeit überall im Gebäude gewährleisten kann. Andernfalls ist dieses Projekt für diese spezielle Lehreinrichtung nicht relevant.
Der Datenbankserver des Systems dient als Verbindungselement zwischen Webserver und Datenbank. Dabei liest der Datenbankserver die benötigten Informationen des Webservers aus der Datenbank aus und leitet sie weiter. Außerdem ist er für das abspeichern von neuen oder aktuallisierten Informationen zuständig die ihm der Webserver vermittelt.

<!-- Wieso trennung der beiden? Begründen! -->

Wie bereits festgestellt wird es im System 2 Listen geben die Informationen über die Raumbelegung geben. Es bietet sich an diese auch in logisch getrennten Datenbanken einzuordnen um eine Wartung dieser zu vereinfachen. Auf der einen Datenbank sind nur Informationen vorhanden die bereits im Systemumfeld vorliegen, wie z.B. der reguläre Veranstalltungsplan der regelmäßig wiederkehrende Veranstalltungen speichert. Die andere Datenbank enthält die von uns dynamisch erzeugten Informationen über die aktuelle Belegung der Räume durch die Benutzer des Systems. Durch die Trennung dieser Datensätze kann je nach Bedarf  der Inhalt der Datenbanken verändert werden, ohne den Ablauf der anderen Datenbank zu beeinflussen.

Die 3. Komponente auf Clientseite ist eine Anwendung auf dem Endgerät des Benutzers. In unserem Projekt haben wir uns auf eine Applikation, basierend auf dem Android Betriebssystem fokussiert. Eine Umsetzung auf einem anderen Betriebssystem, wie z.B. iOS ist aber auch denkbar, wird in diesem Projekt aber nicht behandelt. Diese Anwendung stellt die Kommunikations-  und Interaktionsschnittstelle mit den Funktionen des Systems da. Der einfache Benutzer des Systems kann durch die Installation dieser Anwendung auf seinem Android basierten Endgerät wie z.B. Smartphone oder Tablet die Funktionalitäten des Systems nutzen. Die Anwendung kommuniziert dabei über HTTP-Request mit dem Server des Systems und sendet Informationen die zur Berechnung einer Raumauswahl benötigt werden.

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

Um die Kommunikation und die Architektur der Komponenten des Systems deutlich dazustellen, haben wir Schaubilder erstellt die es ermöglichen einen Eindruck über unser Konzept zu erhalten.

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
Anforderungen des Benutzers entspricht, als auch einen möglichst kurzen Laufweg für den Benutzer gewährleistet, muss der Standort des Benutzers innerhalb des Gebäudes bestimmt werden.
Als Methoden zur Feststellung des Standortes haben wir sowohl die Bestimmung
durch Benutzereingaben, als auch die durch GPS und Bluetooth-Beacons innerhalb des
Gebäudes betrachtet.

### Standortbestimmung durch Benutzereingabe
Die Standortbestimmung über Benutzereingaben würde dabei so funktionieren, dass der Benutzer einen Bezugspunkt innerhalb eines Gebäudes, z.B. eine Raumnummer oder die Kennzeichnung eines Treppenhauses, angibt. Wir haben uns allerdings gegen diese Methode entschieden da sie eine zusätzliche Interaktion
durch den Benutzer erfordert und das gegebenenfalls den Nutzungsfluss des Systems
stören könnte. Diese Methode kann allerdings als Fallback verwendet werden,
wenn die Standortbestimmung durch andere Methoden oder Technologien scheitert.
* Vorteile

* Nachteile


### Standortbestimmung durch GPS
Durch die Verwendung von GPS (Global Positioning System) könnte ebenfalls der Standort des Benutzers bestimmt werden. Dabei würde mittels des Endgerätes des Benutzers die Verbindung
zum "Global Positioning System" aufgebaut und somit der Standort mit einer hohen
Genauigkeit bestimmt werden. Da die Genauigkeit allerdings schwanken kann haben
wir uns gegen die Verwendung von GPS entschieden da eine fehlerhafte oder
ungenaue Messung zu Falschen Ergebnissen innerhalb des Systems führen kann.
* Vorteile

* Nachteile


### Standortbestimmung durch Bluetooth-Beacons
Bluetooth-Beacons sind das Indoor-Equivalent zum klassischen GPS und ermöglichen innerhalb von Gebäuden eine exakte Standortbestimmung auf bis zu 1m [Quelle](https://www.infsoft.de/technologie/sensorik/bluetooth-low-energy-beacons). Dazu kann eine Entfernungsbestimmung vom Endgerät des Benutzers zum Beacon aufgestellt werden, womit ein grober Standort bestimmt werden kann. Durch das hinzufügen von mehreren Beacons lässt sich dieser Standort weiter eingrenzen da durch das Aufspannen einer Fläche ein Schnittpunkt gebildet werden kann. Durch die Bluetooth Low Energy Technik (BLE) sind diese Beacons äußerst stromsparend und können dabei im Baterieberieb je nach eingestelltem Sendeintervall 2-8 Jahre [Quelle](https://www.infsoft.de/technologie/sensorik/bluetooth-low-energy-beacons) senden. Durch die Möglichkeit diese an das Stromnetz anzuschließen, lässt sich die Betriebsdauer beliebig erhöhen.
Auf dem Markt sind bereits spezielle Beacons vorhanden die für das bestimmen von Standorten wie z.B. Messegeländen oder Flughafenterminals konzipiert worden sind. [Estimote, Inc.]() bietet eine spezielle Lösung zu Standortbestimmung innerhalb von Gebäuden an. Dabei lässt sich mit Hilfe eines Tools eine Karte eines Gebäudes erstellen, auf der durch geschicktes platzieren von speziellen [Estimote Beacons]() der Standort des Benutzers auf einer [Karte](https://github.com/Estimote/Android-Indoor-SDK) angezeigt werden kann. Allerdings ist diese Funktion nur exklusiv bei [Estimote, Inc.]() verfügbar, weswegen sie im Rahmen unseres Projektes nicht anwendbar ist. Allerdings lässt sich ein Standort auch ohne interaktive Karte bestimmen, da dafür nur normale Beacons benötigt werden. Die Open Source Projekt [Android Beacon Library](http://altbeacon.github.io/android-beacon-library/index.html) von [Radius Networks](https://www.radiusnetworks.com/) biete eine Anbindung an viele verschiedene Beacon Modelle und Marken die dem [AltBeacon Standard](http://altbeacon.org/) entsprechen. Über eine Android Applikation lassen sich verschiedene Informationen von einem Beacon empfangen und auslesen.
* Vorteile
    - günstig
    - einfach zu handhaben
    - bekannte, altbewärte Technologie
    - langlebigkeit kann beliebig erhöht werden
    - 

* Nachteile
    - Bluetooth und Wifi-Signale teilen sich eine Frequenz (2,4 GHz), kann zu Störungen führen



### Fazit - Standortbestimmung des Benutzers
Nach unserer Recherche, wäre das verwenden von Beacons zur Standortbestimmung ideal für unser Projekt geeignet. Durch die günstigen Anschaffungskosten und die Langlebigkeit der einzelnen Beacons lassen sie sich auch in großen Massen innerhalb eines Gebäudes anbringen. Das die BLE Beacons sowieso schon für die Standortbestimmung eingesetzt werden, unterstützt unsere Ansicht.
Nach unseren Erkentnissen wäre das Anbringen eines Beacons an jedem Raum bzw in einem bestimmten, festgelegten Abstand von Vorteil, da dadurch eine Beacon-Raum Zuweisung erreicht werden kann. Anhand dieser Informationen, die im System hinterlegt sind, kann eine Standortbestimmung durch den Benutzer erfolgen. Das Endgerät des Benutzers scannt dabei selbstständig nach Beacons in der Nähe und speichert diese. Zusätzlich kann durch das auslesen von Entfernungen zwischen Beacon und Benutzer eine genauere Standortbestimmung erfolgen. Dafür muss das System die Position aller Beacons in Abhängigkeit zu ihrem zugehörigen Raum wissen. Um diese Zuordnung einfach zu halten, wäre es ratsam die Beacons direkt an oder über der Tür zum Raum anzubringen. So kann erreicht werden das jede Person im Gang das Signal eines Beacons empfangen kann. Um große Abstände zwischen 2 Räumen zu überbrücken, sollte in einem festgelegten Abstand ein Beacon angebracht werden, der im System als Pseudo-Raum zur Positionsbestimmung genutzt werden kann. Damit ein kurzer Weg zwischen Benutzer und gewünschten Raum errechnet werden kann, müssen alle möglichen Laufwege im System vermerkt sein. Dazu gehören auch markante Punkte wie Treppenhäuse, Ein-/Ausgänge und Fahrstühle die ebenfalls mit einem Beacon ausgestatet werden sollten, um die Standortbestimmung des Benutzers zu optimieren.


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

* Smartphone des Benutzers --> Sender und Empfänger über BLE
    - Sender
        - sendet den generierten Schlüßel an den Minicomputer
    - Empfänger
        - empfängt Raumnummer vom Minicomputer
        - empfängt Statusmeldung vom Minicomputer
* Minicomputer --> Sender und Empfänger über BLE
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

* Smartphone des Benutzers --> Sender und Empfänger über BLE
    - Sender
        - sendet den generierten Schlüßel an den Minicomputer
    - Empfänger
        - empfängt Statusmeldung vom Minicomputer
* Minicomputer --> Sender und Empfänger über BLE
    - Sender
        - sendet Status des Vergleichs des Schlüßels an den Benutzer
    - Empfänger
        - Empfängt den generierten Schlüßel vom Endgerät des Benutzers um ihn zu vergleichen

## User Interface


## Evaluation


## Fazit - 1. Gestalltungslösung