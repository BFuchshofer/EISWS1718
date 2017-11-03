### 1. Nutzungskontext:

*1.1 Nutzungsproblem*

Die momentane das Nutzungsproblem betreffende Lage ist, dass eine Person, oder eine
Gruppe, die einen Raum für eine beliebige Tätigkeit sucht entweder den gesamten
Veranstaltungsplan sucht und anhand diesem sich einen Raum heraussucht, oder sich
auf gut Glück einen Raum sucht, den Veranstaltungsplan für diesen Raum anschaut.
Für beide Optionen ist nicht gewährleistet, dass der Raum nicht schon durch eine
andere Gruppe belegt ist, wodurch die Raumsuche von neuem beginnt.
Durch die ineffektive Raumsuche verliert die Person oder Gruppe Zeit und kommt
im schlimmsten Fall mit einem Projekt in Verzug.

*1.2 Techologieunabhängiger Lösungsvorschlag*

Um dem Problem der spontanen und unstrukturierten Raumbelegung in größeren Lehreinrichtungen entgegen zu wirken ist ein System nötig, welches sowohl die verfügbaren Räume, als auch wöchentlich wiederkehrende und spontanen Belegungen aufnimmt um damit einer Person auf Raumsuche einen freien Raum vorschlagen kann. Dabei sollte darauf geachtet werden, dass der vorgeschlagene Raum auch dem Vorhaben der Person genügt, also sollte für eine Vorlesung die Anzahl an Sitzplätzen dementsprechend groß sein.

*1.3 Ziele festlegen ( strategisch/taktisch/operativ )*

Das __strategische Ziel__ unseres Projektes ist es eine Anwendung zu entwickeln die Personen, welche sich auf Raumsuche in einer größeren Organisation befinden, einen Raumvorschlag liefert. Für diesen Vorschlag muss garantiert sein das der Raum erstens nicht durch eine wöchentlich wiederkehrende Veranstaltung belegt ist und zweitens das der Raum nicht schon einer zweiten Person vorgeschlagen wurde und es somit zu einem Konflikt bei einer Raumbuchung kommt.  
Die __taktischen Ziele__ haben wir aus den strategischen Zielen abgeleitet und mit Hilfe der Anforderungsanalyse bestärkt. So sind wir zu dem Ergebnis gelangt, dass für die Umsetzung des Projekts und damit die Entwicklung des Systems sowohl eine Datenbank als auch ein Webserver aufgesetzt werden muss. Weiterhin muss für die flexible Nutzung eine Anwendung auf dem Endgerät des Nutzers verfügbar sein und für die betreffende Lehreinrichtung konfiguriert werden. Über die Anwendung soll der Nutzer dann einen Raumvorschlag erhalten und diesen Reservieren und Buchen können.  
Die __operativen Ziele__, also die Methoden womit wir die taktischen Ziele verwirklichen sind das zum einen der Webserver durch einen NodeJS-express Server aufgesetzt wird, zum anderen kann für die Datenbank eine Redis-Datenbank mit für den Anwendungsfall definierten Hashes verwendet werden. Für den Vorschlag eines Raumes und die Reservierung wird eine Verbindung zwischen Anwendung und Webserver aufgebaut und über HTTP-GET Anfragen abgerufen, bzw. über HTTP-PUT Anfragen in das System, also die Datenbank übertragen. Um die Raumbelegung im Anschluss zu bestätigen muss ein QR-Code mittels der Anwendung eingescannt werden. Nach dem Scan wird die Bestätigung über einen HTTP-Request an den Server gesendet und in der Datenbank vermerkt. Die Anwendung wird eine vorerst nur für Android verfügbare Applikation sein welche über mehrere Aktivitäten dem Nutzer die einzelnen Arbeitsschritte vereinfacht.

### 2. Alleinstellungsmerkmal und Konkurrenz

*2.1 Vor- und Nachteile ( auch Teilfunktionalitäten )*

Die Vor- und Nachteile unseres Systems gegenüber anderen, welche wir durch Recherche und Marktanalyse identifiziert haben sind zum einen die Flexibilität die durch die Installation auf Organisationsinterner Hardware und die Organisationsinterne Wartung, Verwaltung und Erweiterung  gegeben ist, welche im Umkehrschluss allerdings auch das nötige Fachpersonal erfordert. Eine Verwaltung kann eventuell noch von nicht Fachinternen durchgeführt werden, aber eine Wartung und Erweiterung sollte, um Fehler des Systems vorzubeugen, durch Personen durchgeführt werden die gewisse Kenntnisse der Informatik haben.
Ein weiterer Vorteil der sich aus der Verwaltung des Systems durch die Lehreinrichtung ergibt ist das in einem Hardwareversagen, also wenn zum Beispiel der Webserver ausfällt die Einrichtung sich selbst um das Problem kümmern kann und nicht auf außenstehende angewiesen ist.

*2.2 Alleinstellungsmerkmale*

+ Buchung eines Raumes durch Scannen eines QR-Codes mittels mobiler Applikation möglich
+ System auf seiten des Auftraggebers ( Lehreinrichtung )
+ Ein System für Mitarbeiter / Lehrerkräfte / Lerner
+ Fokus auf Lehreinrichtungen
+ fertiges System für den Auftraggeber ( mit Konfigurationsdatei und Möglichkeit der Administration des Systems )

*2.3 Vergleichbare Produkte innerhalb der konkreten Anwendungsdomäne TH-Köln*

Die TH-Köln bietet ein Online-Tool zur Raumbelegung an, welches wie wir vermuten nicht für Studenten zugänglich ist. Dadurch kann zwar festgestellt werden, welcher Raum zu einem bestimmten Zeitpunkt nicht besetzt ist, allerdings ist damit immernoch nicht gesichert, dass der Raum nicht durch andere Studierende oder Lernende besetzt ist.
Der Studiengang Medieninformatik der TH-Köln am Campus Gummersbach bietet einen Link zu einem Belegungsplan für Medieninformatik-spezifische Räume über einen QR-Code an. Dieser Belegungsplan ist allerdings ebenfalls nicht für Studenten gedacht, da diesen die nötige Zugangsberechtigung zu den Räumen fehlen.

*2.4 Fazit zur übrigen Konkurrenz*

Es gibt viele Softwaremöglichkeiten für ein besseres Raummanagement, jedoch ist keine davon darauf ausgelegt dem einfachen Nutzer einen freien Raum anzuzeigen und diesen für ihn zu reservieren / buchen. Alle gefundenen Lösungen beziehen sich auf die Vermietung von Räumen oder Veranstaltungsorten die von einem Unternehmen verwaltet werden. Die Nutzer sind in diesem Fall die Unternehmen die eine Managementfunktion benötigen. In unserem System sind die Nutzer aber in der Regel Einzelpersonen die einen freien Raum in ihrer Lehreinrichtung suchen und die Verwaltung des Systems übernimmt die Lehreinrichtung selbst.

### 3. Anwendungsdomäne festlegen

*3.1 Welche Eigenschaften der Domäne wirken sich auf die Gestaltung aus ?*

Für dieses Projekt haben wir uns auf die Anwendungsdomäne der Lehreinrichtung festgelegt, da es in dieser durch zum Beispiel defekte Geräte sehr häufig zu einem spontanen Raumwechsel kommt.
Durch diese Anwendungsdomäne wird die Größe der Zielgruppe auf Zugehörige der Organisation  / Institution beschränkt, da Außenstehende meist keinen freien Zugriff auf Räume besitzen. Des weiteren muss durch diese das Endprodukt flexibel gestaltet werden, da unterschiedliche Lehreinrichtungen unterschiedliche Ziele verfolgen und somit auch unterschiedliche Prioritäten auf verschiedene Bereiche legen. So wäre es für eine Einrichtung die über keinerlei elektronische Hilfsmittel, zum Beispiel PC‘s, verfügt nicht sinnvoll die Raumvorschläge mit bestimmten Kriterien zu generieren  die dafür sorgen das Räume mit elektronischen Hilfsmittel zuletzt angegeben werden.

### 4. Stakeholderanalyse:

### 5. Anforderungsanalyse:

*5.1 Erfordernisse*

*5.2 Anforderungen*

### 6. Architekturmodell:

### 7. Vorgehensmodell:

*7.1 Begründete Wahl für das Vorgehensmodell "usage-centred design" (1996) von Lockwood und Constantine*

Nach spezifikation des Nutzungskontextes und der Anwendungsdomäne sind wir zu dem Entschlus gekommen das in unserem System die Nutzung im Vordergrund steht. Das Ziel des Systems ist es dem Benutzer einen freien Raum zur reservierung/buchung zur verfügung zu stellen was es zu einem nützlichen Werkzeug macht. Dabei ist weniger auf persönliche Benutzereigenschaften Rücksicht zu nehmen, sondern vielmehr ein Werkzeug zu schaffen, dass die gestellte Aufgabe des Benutzers schnell und unkompliziert zu lösen vermag. Als Ergänzung des Modells haben wir an entscheidenden Stellen Iterationen eingefügt um mögliche Fehlerquellen mangels Erfahrung möglichst früh zu beheben.

*7.2 Methoden zur Benutzermodellierung*

User profiles

*7.3 Methoden zur Aufgabenmodellierung*

Use Cases

### 8. Risiken des Projektes

*8.1 Umgang mit auftretenden Risiken*

Damit in unserem Projekt der Umgang mit auftretenden Risiken, wie zum Beispiel  das ein von uns geplantes Gestaltungsmerkmal nicht umgesetzt werden kann, geregelt ist wird für jedes Erkannte Risiko ein Exit- ( Erfolgs- ), Failkriterium ( Fehlschlagskriterium ) und ein Fallback ( Alternativ Lösung ) festgelegt wodurch bei einem Fehlschlag sofort eine Alternative bereit steht, die allerdings getestet werden muss.

- Barcode Scanner lässt sich nicht implementieren:
    - Exit:
        - Barcode Scanner ist ins System implementiert und liefert gewünschte Ergebnisse
    - Fail:
        - Barcode Scanner konnte nicht ins System implementiert werden.
        - Barcode Scanner ist ins System implementiert liefert aber nicht die gewünschten Ergebnisee.
    - Fallback:
        - Barcode wird im System durch einen Zahlencode ersetzt.
- Barcode kann nicht erkannt werden:
    - Exit:
        - Barcode wird erkannt und richtig im System angezeigt.
    - Fail:
        - Barcode kann nach mehrfachem scannen ( max = 5 ) nicht erkannt werden.
    - Fallback:
        - Barcode wird im System durch einen Zahlencode ersetzt.
- Filterung nach bestimmten Rauminhalten lässt sich nicht realisieren:
    - ( Exit:
        - Es wird innerhalb von maximal 2 Sekunden ein Raumvorschlag geliefert.
    - Fail:
        - Es wird kein Raumvorschlag geliefert )

*8.2 Möglichkeiten der Risikominimierung*



*8.3 Wie werden diese durch PoC ( Proof of Concept ) adressiert ?*

Um die einzelnen Risiken in PoC's zu adressieren wird entweder für ein einzelnes Risiko oder, sofern diese den selben oder ähnlichen Themenbereich behandeln, für mehrere Risiken ein Prototyp erstellt.
