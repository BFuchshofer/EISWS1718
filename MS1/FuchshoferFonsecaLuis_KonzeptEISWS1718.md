### 1. Nutzungskontext

*1.1 Nutzungsproblem*

Die momentane das Nutzungsproblem betreffende Lage ist, dass eine Person, oder eine
Gruppe, die einen Raum für eine beliebige Tätigkeit sucht entweder den gesamten
Veranstaltungsplan sucht und anhand diesem sich einen Raum heraussucht, oder sich
auf gut Glück einen Raum sucht, den Veranstaltungsplan für diesen Raum anschaut.
Für beide Optionen ist nicht gewährleistet, dass der Raum nicht schon durch eine
andere Gruppe belegt ist, wodurch die Raumsuche von neuem beginnt.
Durch die ineffektive Raumsuche verliert die Person oder Gruppe Zeit und kommt
im schlimmsten Fall mit einem Projekt in Verzug.

*1.2 Technologieunabhängiger Lösungsvorschlag*

Um dem Problem der spontanen und unstrukturierten Raumbelegung in größeren Lehreinrichtungen entgegen zu wirken ist ein System nötig, welches sowohl die verfügbaren Räume, als auch wöchentlich wiederkehrende und spontanen Belegungen aufnimmt um damit einer Person auf Raumsuche einen freien Raum vorschlagen kann. Dabei sollte darauf geachtet werden, dass der vorgeschlagene Raum auch dem Vorhaben der Person genügt, also sollte für eine Vorlesung die Anzahl an Sitzplätzen dementsprechend groß sein.

*1.3 Ziele festlegen ( strategisch/taktisch )*

__Strategische Ziele__   
Das fertige System in unserem Projekt __muss__ es dem Benutzer ermöglichen innerhalb von 30 Sekunden einen freien Raum der den Erfordernisen des Benutzers entspricht zu erhalten.   
Dabei __muss__ es alle zugänglichen Räume innerhalb der Lehreinrichtung in Echtzeit adressieren und verwalten können.  


__Taktische Ziele__  
Um auf unterschiedliche Rauminhalte eingehen zu können, __sollte__ jeder einzelne Raum mit zugehörigen Informationen über seine Ausstattung angereichert werden.    
Diese Rauminformationen __müssen__ innerhalb eines persistenten Datenspeichers gespeichert, und auf Anfrage an eine Verarbeitungseinheit geschickt werden.    
Diese Verarbeitungseinheit __muss__ ein Server sein der die Informationen in den persistenten Datenspeicher schreiben und lesen kann.    
Außerdem __muss__ der Server diese Informationen algorithmisch anreichern und dem Benutzer an seinem Endgerät präsentieren können.    

### 2. Anwendungsdomäne festlegen

*2.1 Welche Eigenschaften der Domäne wirken sich auf die Gestaltung aus ?*

Für dieses Projekt haben wir uns auf die Anwendungsdomäne der Lehreinrichtung festgelegt, da es in dieser durch zum Beispiel defekte Geräte oder spontane Bildung von Lerngruppen zu einem zusätzlichen Raumbedarf kommt.
Durch diese Anwendungsdomäne wird die Größe der Zielgruppe auf Zugehörige der Organisation  / Institution beschränkt, da Außenstehende meist keinen freien Zugriff auf Räume besitzen. Des weiteren muss durch diese das Endprodukt flexibel gestaltet werden, da unterschiedliche Lehreinrichtungen unterschiedliche Ziele verfolgen und somit auch unterschiedliche Prioritäten auf verschiedene Bereiche legen. So wäre es für eine Einrichtung die über keinerlei elektronische Hilfsmittel, zum Beispiel PC‘s, verfügt nicht sinnvoll die Raumvorschläge mit bestimmten Kriterien zu generieren  die dafür sorgen das Räume mit elektronischen Hilfsmittel zuletzt angegeben werden.


### 3. Alleinstellungsmerkmal und Konkurrenz

*3.1 Konkurenz*

[Locaboo](http://locaboo.com)  
+ Raum und Ressourcen Management
+ Buchung von Räumen (vermietung)
+ Kundenverwaltung

[INTIME](http://comtech-noecker.de)   
+ Verwaltung von Räumen, Terminen, Veranstalltungen
+ Buchung von Räumen und Ressourcen
+ Abrechnungsverfahren

[Kribus](http://kribus.de)   
+ Dienstplan, Belegungsplan, Prüfungsplan

[Online-Raumverwaltung](http://online-raumverwaltung.de)   
+ cloudbasiert
+ Abrechnung, Angebote, Mahnungen
+ Ressourcenplanung

*3.2 Vor- und Nachteile ( auch Teilfunktionalitäten )*

Die Vor- und Nachteile unseres Systems gegenüber anderen, welche wir durch Recherche und Marktanalyse identifiziert haben sind zum einen die Flexibilität die durch die Installation auf organisationsinterner Hardware und die organisationsinterne Wartung, Verwaltung (und Erweiterung?) gegeben ist, welche im Umkehrschluss allerdings auch das nötige Fachpersonal erfordert. Eine Verwaltung kann eventuell noch von nicht Fachinternen durchgeführt werden, aber eine Wartung und Erweiterung sollte, um Fehler des Systems vorzubeugen, durch Personen durchgeführt werden die gewisse Kenntnisse der Informatik haben.
Ein weiterer Vorteil der sich aus der Verwaltung des Systems durch die Lehreinrichtung ergibt ist das bei einem Hardwareversagen, also wenn zum Beispiel der Webserver ausfällt, die Einrichtung sich selbst um das Problem kümmern kann und nicht auf Außenstehende angewiesen ist.

*3.3 Fazit zur übrigen Konkurrenz*

Es gibt viele Softwaremöglichkeiten für ein besseres Raummanagement, jedoch ist keine davon darauf ausgelegt dem einfachen Benutzer einen freien Raum anzuzeigen und diesen für ihn zu reservieren / buchen. Alle gefundenen Lösungen beziehen sich auf die Vermietung von Räumen oder Veranstaltungsorten die von einem Unternehmen verwaltet werden. Die Benutzer sind in diesem Fall die Unternehmen die eine Managementfunktion benötigen. In unserem System sind die Benutzer aber in der Regel Einzelpersonen die einen freien Raum in ihrer Lehreinrichtung suchen und die Verwaltung des Systems übernimmt die Lehreinrichtung selbst.


*3.4 Vergleichbare Produkte innerhalb der konkreten Anwendungsdomäne TH-Köln*

Die TH-Köln bietet ein Online-Tool zur Raumbelegung an, welches wie wir vermuten nicht für Studenten zugänglich ist. Dadurch kann zwar festgestellt werden, welcher Raum zu einem bestimmten Zeitpunkt nicht besetzt ist, allerdings ist damit immernoch nicht gesichert, dass der Raum nicht durch andere Studierende oder Lernende besetzt ist.
Der Studiengang Medieninformatik der TH-Köln am Campus Gummersbach bietet einen Link zu einem Belegungsplan für Medieninformatik-spezifische Räume über einen QR-Code an. Dieser Belegungsplan ist allerdings ebenfalls nicht für Studenten gedacht, da diesen in der Regel die nötigen Zugangsberechtigungen zu den Räumen fehlen.

*3.5 Alleinstellungsmerkmale*

+ Buchung eines Raumes durch Scannen eines QR-Codes mittels mobiler Applikation möglich
+ System auf seiten des Kunden ( Lehreinrichtungen )
+ Ein System für Mitarbeiter / Lehrerkräfte / Lerner
+ Fokus auf Lehreinrichtungen
+ fertiges System für den Auftraggeber ( mit Konfigurationsdatei und Möglichkeit der Administration des Systems )


### 4. Erste Stakeholderanalyse
| Bezeichnung | Beziehung zum System | Objektbereich | Erfordernis, Erwartung | Priorität |
| ----------- | -------------------- | ------------- | ---------------------- | --------- |
| __Lehrkraft__ | Anspruch | Rauminformation | Als Lehrkraft muss ich die korrekten Rauminformationen verfügbar haben um einen Raum als für meine Arbeitsaufgabe passend klassifizieren zu können. | |  
|               | Interesse | Rauminformation | Als Lehrkraft muss ich den einfachen Zugriff auf Funktionen des Systems verfügbar haben um das System mit möglichst wenig Aufwand auf meiner Seite nutzen zu können. |  |
|| Interesse | Schnittstelle des Systems | Als Lehrkraft muss ich wissen welche Informationen ich in das System eintragen muss um das System effizient nutzen zu können | |
|| Anrecht | Reservierung des Raumes | Als Lehrkraft muss ich das alleinige Recht auf eine Reservierung für einen mir vorgeschlagenen Raum verfügbar haben um meine Arbeitsaufgabe effizient zu erledigen. | |
|| Anrecht | Belegung des Raumes | Als Lehrkraft muss ich das alleinige Recht auf die Buchung eines von mir Reservierten Raumes verfügbar haben um meine Arbeitsaufgabe effizient zu erledigen.	 |  |
|| Anspruch | Schnittstelle des Systems | Als Lehrkraft muss ich wissen das ich mit dem System einen Raum effizienter finden kann um mich für die Nutzung des Systems entscheiden zu können. | |
|| Anspruch | Reservierung des Raumes | Als Lehrkraft muss ich die Bevorzugung meiner Buchung gegenüber Studenten verfügbar haben um den Veranstaltungsfluss ohne weitere unterbrechungen fortführen zu können. | |
| __Lerner__ | Anspruch | Rauminformation | Als Lerner muss ich die korrekten Rauminformationen verfügbar haben um einen Raum als für meine Arbeitsaufgabe passend klassifizieren zu können. | |
|| Interesse | Rauminformation | Als Lerner muss ich den einfachen Zugriff auf Funktionen des Systems verfügbar haben um das System mit möglichst wenig Aufwand auf meiner Seite nutzen zu können. | |
|| Interesse | Schnittstelle des Systems | Als Lerner muss ich wissen welche Informationen ich in das System eintragen muss um das System effizient nutzen zu können |  |
|| Anrecht | Reservierung des Raumes | Als Lerner muss ich das alleinige Recht auf eine Reservierung für einen mir vorgeschlagenen Raum verfügbar haben um meine Arbeitsaufgabe effizient zu erledigen. | |
|| Anrecht | Belegung des Raumes | Als Lerner muss ich das alleinige Recht auf die Buchung eines von mir Reservierten Raumes verfügbar haben um meine Arbeitsaufgabe effizient zu erledigen.	 |  |
|| Anspruch | Schnittstelle des Systems	| Als Lerner muss ich wissen das ich mit dem System einen Raum effizienter finden kann um mich für die Nutzung des Systems entscheiden zu können. | |
| __Wissenschaftliche Mitarbeiter__ | Anspruch | Rauminformation | Als wissenschaftlicher Mitarbeiter muss ich die korrekten Rauminformationen verfügbar haben um einen Raum als für meine Arbeitsaufgabe passend klassifizieren zu können. | |
|| Interesse | Rauminformation | Als wissenschaftlicher Mitarbeiter muss ich den einfachen Zugriff auf Funktionen des Systems verfügbar haben um das System mit möglichst wenig Aufwand auf meiner Seite nutzen zu können. | |
|| Interesse | Schnittstelle des Systems | Als wissenschaftlicher Mitarbeiter muss ich wissen welche Informationen ich in das System eintragen muss um das System effizient nutzen zu können | |
|| Anrecht | Reservierung des Raumes | Als wissenschaftlicher Mitarbeiter muss ich das alleinige Recht auf eine Reservierung für einen mir vorgeschlagenen Raum verfügbar haben um meine Arbeitsaufgabe effizient zu erledigen. | |
|| Anrecht | Belegung des Raumes | Als wissenschaftlicher Mitarbeiter muss ich das alleinige Recht auf die Buchung eines von mir Reservierten Raumes verfügbar haben um meine Arbeitsaufgabe effizient zu erledigen.	 |  |
|| Anspruch | Schnittstelle des Systems | Als wissenschaftlicher Mitarbeiter muss ich wissen das ich mit dem System einen Raum effizienter finden kann um mich für die Nutzung des Systems entscheiden zu können. | |
| __Institutsverwaltung__ | Anspruch | Rauminformation | Als Institutsverwaltung muss ich die korrekten Rauminformationen verfügbar haben um einen Raum als für meine Arbeitsaufgabe passend klassifizieren zu können. | |
|| Interesse | Rauminformation | Als Institutsverwaltung muss ich den einfachen Zugriff auf Funktionen des Systems verfügbar haben um das System mit möglichst wenig Aufwand auf meiner Seite nutzen zu können. |  |
|| Interesse | Schnittstelle des Systems | Als Institutsverwaltung muss ich wissen welche Informationen ich in das System eintragen muss um das System effizient nutzen zu können | |
|| Anrecht | Reservierung des Raumes | Als Institutsverwaltung muss ich das alleinige Recht auf eine Reservierung für einen mir vorgeschlagenen Raum verfügbar haben um meine Arbeitsaufgabe effizient zu erledigen. | |
|| Anrecht | Belegung des Raumes | Als Institutsverwaltung muss ich das alleinige Recht auf die Buchung eines von mir Reservierten Raumes verfügbar haben um meine Arbeitsaufgabe effizient zu erledigen.	 |  |
|| Anspruch | Schnittstelle des Systems | Als Institutsverwaltung muss ich wissen das ich mit dem System einen Raum effizienter finden kann um mich für die Nutzung des Systems entscheiden zu können. | |
| __Administrator__ | Anspruch | System | Als Administrator muss ich die Möglichkeit der Wartung des Systems verfügbar haben um auf Institutspezifische Probleme und Wünsche eingehen zu können. |  |
|| Anspruch | Rauminformation | Als Administrator muss ich eine Möglichkeit zum Hinzufügen, Bearbeiten und Löschen von Räumen und ihren Informationen verfügbar haben um das System flexibel an das Institut anpassen zu können. | |
|| Interesse | Rauminformation | Als Administrator muss ich den einfachen Zugriff auf Rauminformationen verfügbar haben um diese auf ihre Korrektheit überprüfen |  |
|| Anteil | System | Als Administrator muss ich wissen das alle von mir erstellten Inhalte des Systems auch mir gehören um das System ohne Bedenken warten zu können. |  |
| __Angestellte__ | | | | |
| __Institut__ | Anspruch | organisationsinternen Informationen | Als Institut muss ich wissen das Informationen über das Institut oder Teile des Instituts nicht an außenstehende weitergegeben werden um mich für die Nutzung des Systems entscheiden zu können. |  |
|| Anspruch | Veranstaltungen(fixe Daten) | Als Institut muss ich wissen das wöchentlich wiederkehrende Veranstaltungen in das System integriert werden können um den reibungslosen Ablauf dieser garantieren zu können. | |
|| Interesse | System | Als Institut muss ich wissen das das System aktiv genutzt wird um mit dem System effektiv Veranstaltungen planen zu können. | |
|| Anrecht | System | Als Institut muss ich wissen das das System fehlerfrei betrieben werden kann um mich für die Nutzung/Einbindung des Systems im Institut entscheiden zu können. | |
|| Anrecht | System | Als Institut muss ich die dauerhafte Erreichbarkeit des Systems verfügbar haben um den reibungslosen Ablauf von Veranstaltungen garantieren zu können. |  |

 __Rauminformation:__ Status des Raumes (belegt/nicht belegt), Eigenschaften (Größe, Equipment, etc.)


### 5. Erste Anforderungsanalyse

*5.1 Anforderungen*   

+ __Funktionale Anforderungen__ <br/>

    - Selbstständige Systemaktivität <br/>
        - Das System muss dem Benutzer die Möglichkeit bieten Informationen präsentiert zu bekommen.
        - Das System muss Benutzereingaben verarbeiten können.
        - Das System muss Benutzereingaben auswerten können.
        - Das System muss anhand von Benutzerspezifizierten Eingaben und im System definierten Kriterien eine Raumauswahl treffen können.
        - Das System muss gewährleisten das Lerner nur einen Raum gleichzeitig reservieren können.
        - Das System muss gewährleisten das Lerner nur einen Raum gleichzeitig belegen können.
        - Das System sollte die Möglichkeit bieten das Lehrkräfte mehrere Räume reservieren können.
        - Das System sollte die Möglichkeit bieten das Lehrkräfte mehrere Räume belegen können.
        - Das System sollte die Möglichkeit bieten das die Institut-Verwaltung mehrere Räume gleichzeitig reservieren kann.
        - Das System sollte die Möglichkeit bieten das die Institut-Verwaltung mehrere Räume gleichzeitig belegen kann.

    - Benutzerinteraktion <br/>
        - Das System sollte dem Benutzer eine vordefinierte Auswahl an Raumspezifikationen zur Filterung zur Verfügung stellen.
        - Das System muss dem Benutzer die Möglichkeit bieten anhand von benutzerdefinierten Eingaben einen Raumvorschlag auszugeben.
        - Das System muss eine Verifizierung des Benutzer ermöglichen.
        - Das System muss dem Benutzer die Möglichkeit bieten seine persönlichen Informationen zu verändern.

    - Schnittstellenanforderungen <br/>
        - Das System muss fähig sein, Informationen aus einem persistenten Speicher zu beziehen.
        - Das System muss fähig sein, Informationen in einen persistenten Speicher zu schreiben.

+ __Non-Funktionale Anforderungen__
    - Qualitätsanforderungen <br/>
        - Das System muss dem Benutzer jederzeit die Korrektheit der präsentierten Informationen gewährleisten.
        - Das System sollte dem Benutzer den Zugriff in Echtzeit auf die verfügbaren Informationen ermöglichen.
        - Das System muss administrativ verwaltbar sein.
        - (Das System sollte eine Synchronisation zwischen unterschiedlichen Nutzungsschnittstellen erlauben.)
        - Das System sollte dem Benutzer eine informative Rückmeldung über getätigte Interaktionen geben.
        - Das System muss dem Benutzer eine einfache Konfiguration auf seinem Endgerät ermöglichen (weniger als 5 Arbeitsschritte).
        - Das System muss dem Benutzer die Gültigkeit einer Interaktion garantieren.

### 6. Erstes Architekturmodell

*6.1 Komponenten des Systems*

* Datenspeicher
* Webserver
* Client

*6.2 Modelle*   

* Deskriptives Kommunikationsmodell   
https://github.com/eXeLuis/EISWS1718FuchshoferFonsecaLuis/tree/master/MS1/imgs/deskriptiv_kommunikationsModell.png

* Präskriptives Kommunikationsmodell   
https://github.com/eXeLuis/EISWS1718FuchshoferFonsecaLuis/tree/master/MS1/imgs/präskriptiv_kommunikationsModell.png

Für einen ersten Entwurf des Systems haben wir uns für die Umsetzung einer Smartphone-applikation als Client, einem Webserver für den externen Zugriff und einem Datenspeicher entschieden.

### 8. Vorgehensmodell

*8.1 Begründete Wahl für das Vorgehensmodell "usage-centred design" (1996) von Lockwood und Constantine*

Nach spezifikation des Nutzungskontextes und der Anwendungsdomäne sind wir zu dem Entschlus gekommen das in unserem System die Nutzung im Vordergrund steht. Das Ziel des Systems ist es dem Benutzer einen freien Raum zur reservierung/buchung zur verfügung zu stellen was es zu einem nützlichen Werkzeug macht. Dabei ist weniger auf persönliche Benutzereigenschaften Rücksicht zu nehmen, sondern vielmehr ein Werkzeug zu schaffen, dass die gestellte Aufgabe des Benutzers schnell und unkompliziert zu lösen vermag. Als Ergänzung des Modells haben wir an entscheidenden Stellen Iterationen eingefügt um mögliche Fehlerquellen mangels Erfahrung möglichst früh zu beheben.

*8.2 Methoden zur Benutzermodellierung*

- User Roles
- User Role Maps 

*8.3 Methoden zur Aufgabenmodellierung*

- Essential Use Cases (Constantine & Lockwood)
- Concrete Use Cases (Constantine & Lockwood)
- UML Diagramme

* 8.4 Methoden zur Modellierung von UI-Inhalten
- Content Models
- Navigation Maps

### 9. Risiken des Projektes

*9.1 Risiken des Projekts*
- Das System liefert einen freien Raum nicht in absehbarer Zeit.
- Das finden eines freien Raumes dauert mit Hilfe der Anwendung länger als ohne.
- System kann nicht zur Instalation beim Kunden bereitgestellt werden.
- Das Zwischenspeichern von Informationen auf dem Client kann nicht umgesetzt werden.
- Die automatische Aufhebung des Vorschlags/Reservierung/Buchung eines Raumes kann nicht automatisch erfolgen.
- Ein QR-Code Scanner lässt sich auf seiten des Client nicht realisieren.
- Ein gescannter QR-Code lässt sich nicht eindeutig zuordnen.
- Eine Filterung nach bestimmten Rauminhalten durch den Benutzer lässt sich nicht umsetzen.


*9.2 Umgang mit auftretenden Risiken*

Damit in unserem Projekt der Umgang mit auftretenden Risiken, wie zum Beispiel  das ein von uns geplantes Gestaltungsmerkmal nicht umgesetzt werden kann, geregelt ist wird für jedes Erkannte Risiko ein Exit- ( Erfolgs- ), Failkriterium ( Fehlschlagskriterium ) und ein Fallback ( Alternativ Lösung ) festgelegt wodurch bei einem Fehlschlag sofort eine Alternative bereit steht, die allerdings getestet werden muss.

- __Das finden eines freien Raumes dauert mit Hilfe der Anwendung länger als ohne:__
    - Exit:
        - Zeit für die Raumsuche beschränkt sich auf max. 10 Sekunden.
    - Fail:
        - Zeit für die Raumsuche beträgt mehr als 10 Sekunden.
    - Fallback:
        - Funktionen kürzen um Antwortszeiten des Systems zu verringern.
- __Durch physisches Belegen eines Raumes in der Umgebung des Benutzers ohne das System zu nutzen, entstehen Konflikte zwischen Personengruppen:__
    - Exit:
        - Der Benutzer nutzt ausschließlich das System um einen freien Raum zu finden.
    - Fail:
        - Personen die einen Raum suchen nutzen nicht das System sondern suchen sich selber einen Raum der ihren Ansprüchen genügt.
        - Personen halten sich weiter in einem Raum auf, obwohl eine Reservierung vorliegt oder eine reguläre Veranstalltung statfindet.
    - Fallback:
        - Anwender des Systems haben ein Anrecht auf die Reservierung/Buchung eines Raumes gegenüber anderen Personen (muss vom Kunden entschieden werden).
- __Installation der Software lässt sich beim Kunden nicht realisieren:__
    - Exit: 
        - Software lässt sich auf bestehender Hardware des Kunden implementieren.
    - Fail:
        - Die Hardware des Kunden liefert nicht die benötigte Leistung für das System.
        - Das bestehende System des Kunden erlaubt keine zusätzliche/andere Software.
        - Installation der Software beim Kunden gestalltet sich als schwierig da in kritische Komponenten eingegriffen werden muss.
    - Fallback:
        - Das System wird auf einer von uns zur verfügung gestellten Hardware ausgeliefert.
- __Persistente zwischenspeichern von Informationen lässt sich auf Clientseite nicht umsetzen:__
    - Exit:
        - Informationen können persistent auf Clientseite gespeichert werden.
    - Fail:
        - Kein Zugriff auf die Hardware des Clients um Dateien abzulegen.
        - Erstellen von Dateien auf Clientseite nicht möglich.
        - Abrufen von Dateien auf Clientseite nicht möglich.
    - Fallback:
        - Informationen werden nicht zwischengespeichert sondern bei Bedarf vom Server angefragt.
- __Die automatische Aufhebung von Vorschlägen/Reservierungen/Buchungen lässt sich im System nicht umsetzen.__
    - Exit: 
        - Automatische Aufhebung lässt sich realisieren.
    - Fail:
        - Funktion zur automatischen Aufhebung pausiert das System da immer auf Aktualität geprüft werden muss.
        - Das System kann nicht automatisiert prüfen ob eine Aufhebung vorgenommen werden muss.
    - Fallback:
        - Bei Bedarf wird ein Countdown gestartet der im Hintergrund abläuft und bei Beendigung die Aufhebung einleitet.
- __QR-Code Scanner lässt sich nicht implementieren:__
    - Exit:
        - QR-Code Scanner ist ins System implementiert und liefert gewünschte Ergebnisse.
    - Fail:
        - QR-Code Scanner konnte nicht ins System implementiert werden.
        - QR-Code Scanner ist ins System implementiert liefert aber nicht die gewünschten Ergebnisee.
    - Fallback:
        - QR-Code wird im System durch einen Zahlencode ersetzt.
- __QR-Code kann nicht erkannt werden:__
    - Exit:
        - QR-Code wird erkannt und richtig im System angezeigt.
    - Fail:
        - QR-Code kann nach mehrfachem scannen ( max = 5 ) nicht erkannt werden.
    - Fallback:
        - QR-Code wird im System durch einen Zahlencode ersetzt.
- __Filterung nach bestimmten Rauminhalten lässt sich nicht realisieren:__
    - Exit:
        - Es wird innerhalb von maximal 2 Sekunden ein Raumvorschlag geliefert.
    - Fail:
        - Es wird kein Raumvorschlag geliefert.
    - Fallback:
        - Das System übernimmt intern die Auswahl des Raumes durch Erweiterung des Algorithmuses.


*9.3 Wie werden diese durch PoC ( Proof of Concept ) adressiert ?*

Um die einzelnen Risiken in PoC's zu adressieren wird entweder für ein einzelnes Risiko oder, sofern diese den selben oder ähnlichen Themenbereich behandeln, für mehrere Risiken ein Prototyp erstellt.   

Anhand der oben aufgelisteten Risiken haben wir festgestellt das unser größtes Problem darin besteht, die Antwortzeiten des Systems so kurz zu halten, das der Benutzer mit dem System nicht länger braucht einen freien Raum zu finden als wenn er einfach durch ausprobieren die Räume in seiner Umgebung überprüft. Dazu muss natürlich erwähnt werden das die Zeit, die ein Benutzer benötigt um ohne unser System einen Raum zu finden, immer von den örtlichen Gegebenheiten abhängt, wie z.B. Länge der Flure, Laufwege zwischen zwei Räumen oder, wenn vorhanden, der Gang zu einer Anlaufstelle innerhalb der Lehreinrichtung die Informationen über freie Räume bietet. Um dieses Problem zu lösen wurde ein PoC durchgeführt und im Rapid Prototyp simuliert.   
*__Das finden eines freien Raumes dauert mit Hilfe der Anwendung länger als ohne.__*
+ Exit Kriterium ist eingetreten
    - Die Zeit die man benötigt einen freien Raum zu finden liegt nach unseren Messungen deutlich unter der Maximalzeit (10 Sekunden). Dabei sind wir von einer optimalen Umgebung des Systems ausgegangen (gutes Internet, keine störenden Prozesse auf dem Endgerät). Dabei startet der Nutzer die Anwendung auf seinem Endgerät und bekommt einen freien Raum mit Raumnummer präsentiert. Er hat dann die Möglichkeit den Raum zu reservieren, was dem finden und sichern eines Raumes entspricht.
    - Risikominimierung durch möglichst wenig Interaktionsschritte für den Benutzer.

### 10. Quellen

* [Locaboo](http://locaboo.com) 
* [INTIME](http://comtech-noecker.de)   
* [Kribus](http://kribus.de)   
* [Online-Raumverwaltung](http://online-raumverwaltung.de)   
* http://advbs06.gm.fh-koeln.de:8080/hops/raumabfrage/   
* https://www.medieninformatik.th-koeln.de/w/Studio_Belegung


