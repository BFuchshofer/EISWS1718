# Anforderungen (Konzept) 
## Funktionale Anforderungen
### Selbstständige Systemaktivität
* Das System muss dem Benutzer die Möglichkeit bieten Informationen präsentiert zu bekommen.
* Das System muss Benutzereingaben verarbeiten können.
* Das System muss Benutzereingaben auswerten können.
* Das System muss anhand von Benutzerspezifizierten Eingaben und im System definierten Kriterien eine Raumauswahl treffen können.
* Das System muss gewährleisten das Lerner nur einen Raum gleichzeitig reservieren können.
* Das System muss gewährleisten das Lerner nur einen Raum gleichzeitig belegen können.
* Das System sollte die Möglichkeit bieten das Lehrkräfte mehrere Räume reservieren können.
* Das System sollte die Möglichkeit bieten das Lehrkräfte mehrere Räume belegen können.
* Das System sollte die Möglichkeit bieten das die Institut-Verwaltung mehrere Räume gleichzeitig reservieren kann.
* Das System sollte die Möglichkeit bieten das die Institut-Verwaltung mehrere Räume gleichzeitig belegen kann.

### Benutzerinteraktion
* Das System sollte dem Benutzer eine vordefinierte Auswahl an Raumspezifikationen zur Filterung zur Verfügung stellen.
* Das System muss dem Benutzer die Möglichkeit bieten anhand von benutzerdefinierten Eingaben einen Raumvorschlag auszugeben.
* Das System muss eine Verifizierung des Benutzer ermöglichen.
* Das System muss dem Benutzer die Möglichkeit bieten seine persönlichen Informationen zu verändern.

### Schnittstellenanforderungen
* Das System muss fähig sein, Informationen aus einem persistenten Speicher zu beziehen.
* Das System muss fähig sein, Informationen in einen persistenten Speicher zu schreiben.

## Qualitätsanforderungen
* Das System muss dem Benutzer jederzeit die Korrektheit der präsentierten Informationen gewährleisten.
* Das System sollte dem Benutzer den Zugriff in Echtzeit auf die verfügbaren Informationen ermöglichen.
* Das System muss administrativ verwaltbar sein.
* (Das System sollte eine Synchronisation zwischen unterschiedlichen Nutzungsschnittstellen erlauben.)
* Das System sollte dem Benutzer eine informative Rückmeldung über getätigte Interaktionen geben.
* Das System muss dem Benutzer eine einfache Konfiguration auf seinem Endgerät ermöglichen (weniger als 5 Arbeitsschritte).
* Das System muss dem Benutzer die Gültigkeit einer Interaktion garantieren.



# Anforderungen (v1)
## Funktionale Anforderungen
### Selbstständige Systemaktivität
<!--* Das System muss dem Benutzer die Möglichkeit bieten Informationen präsentiert zu bekommen. -->
* Das System sollte dem Benutzer eine vordefinierte Auswahl an Raumspezifikationen zur Filterung zur Verfügung stellen.
* Das System muss Benutzereingaben verarbeiten können.
* Das System muss Benutzereingaben auswerten können.
* Das System muss anhand von ihm zur Verfügung stehenden Daten eine Raumauswahl treffen können.
* Das System muss gewährleisten das Lerner nur einen Raum gleichzeitig reservieren können.
* Das System muss gewährleisten das Lerner nur einen Raum gleichzeitig belegen können.
* Das System sollte die Möglichkeit bieten das Lehrkräfte mehrere Räume reservieren können.
* Das System sollte die Möglichkeit bieten das Lehrkräfte mehrere Räume belegen können.
* Das System sollte die Möglichkeit bieten das die Institut-Verwaltung mehrere Räume gleichzeitig reservieren kann.
* Das System sollte die Möglichkeit bieten das die Institut-Verwaltung mehrere Räume gleichzeitig belegen kann.
* Das System sollte die Möglichkeit bieten Benutzergruppen anhand von bestimmten Merkmalen zu unterscheiden.
* Das System sollte die Möglichkeit bieten Eingaben von unterschiedlichen Benutzergruppen zu verarbeiten. 
* Das System sollte die Möglichkeit bieten Benutzern eine Verlängerung der Raumbuchung nach Ablauf eines vordefinierten Zeitraums zu ermöglichen.
* Das System muss Informationen in einem persistenten Datenspeicher eindeutig zuordnen können.

### Benutzerinteraktion
* Das System muss dem Benutzer die Möglichkeit bieten anhand von benutzerdefinierten Eingaben einen Raumvorschlag auszugeben.
* Das System muss dem Benutzer die Möglichkeit bieten sich als Mitglied einer Benutzergruppe zu verifizieren.
* Das System muss dem Benutzer die Möglichkeit bieten seine persönlichen Informationen zu verändern.
* Das System muss dem Benutzer die Möglichkeit bieten einen Raum zu buchen.
* Das System muss dem Benutzer die Möglichkeit bieten einen vom System vorgegebenen Raum zu reservieren.
* Das System muss dem Benutzer die Möglichkeit bieten einen vom System vorgegebenen Raum zu buchen.
* Das System muss dem Benutzer die Möglichkeit bieten die Reservierung eines Raumes aufzuheben.
* Das System muss dem Benutzer die Möglichkeit bieten die Buchung eines Raumes aufzuheben.
* Das System muss dem Benutzer die Möglichkeit bieten die Buchung eines Raumes zu verlängern.
* Das System sollte einem Administrator die Möglichkeit bieten neue Räume im System hinzuzufügen.
* Das System sollte einem Administrator die Möglichkeit bieten bestehende Rauminformationen zu aktuallisieren.

### Schnittstellenanforderungen
* Das System muss fähig sein, Informationen aus einem persistenten Speicher zu beziehen.
* Das System muss fähig sein, Informationen in einen persistenten Speicher zu schreiben.
* Das System muss fähig sein, Informationen vom Client zu beziehen.
* Das System muss fähig sein, Informationen an den Client zu senden.
* Das System muss fähig sein, eine Verbindung zu einem Netzwerk aufzunehmen.
* Das System muss fähig sein über ein Netzwerk mit anderen Systemkomponenten zu kommunizieren.
* Das System muss fähig sein Informationen auf Clientseite zu verarbeiten. 

## Qualitative Anforderungen
* Das System muss dem Benutzer jederzeit die Korrektheit der präsentierten Informationen gewährleisten.
* Das System sollte dem Benutzer den Zugriff in Echtzeit auf die verfügbaren Informationen ermöglichen.
* Das System muss administrativ verwaltbar sein.
* Das System sollte dem Benutzer eine informative Rückmeldung über seine getätigten Interaktionen geben.
* Das System muss dem Benutzer eine einfache Konfiguration auf seinem Endgerät in weniger als 5 Arbeitsschritten ermöglichen.
* Das System muss dem Benutzer die Gültigkeit einer von ihm getätigten Interaktion garantieren.
* Das System sollte auf dem Endgerät des Benutzers maximal 100MB Speicherplatz benötigen.
* Das System muss für den Benutzer einfach zu erlernen sein.
* Das System sollte Fehlermeldungen eindeutig beschreiben.
* Das System sollte dem Benutzer alle benötigten Funktionen zur Aufgabenerledigung zur verfügung stellen.
* Das System sollte eine Benutzeroberfläche für administrative Arbeiten bereitstellen.   
* Das System muss Informationen über eine grafische Oberfläche ausgeben können.
* Das System muss Informationen über eine grafische Oberfläche einlesen können.
* Das System muss die Anfrage des Benutzers innerhalb von 10 Sekunden ausführen.
* Das System muss für den Benutzer innerhalb einer Applikation für ein mobiles Endgerät zur verfügung stehen.
* Das System sollte Informationen im JSON-Format speichern können.
* Das System sollte das JSON-Format auslesen können.
* Das System muss eine verteilte Anwendungslogik besitzen.
* Das System muss 2 oder mehr Komponenten besitzen die auf unterschiedlichen Systemen laufen.
* Das System muss auf lokal betriebenen Rechnern ausgeführt werden können.
* Das System muss in den Sprachen Java oder JavaScript geschrieben werden.
* Das System muss von den Entwicklern des Projekts geschrieben werden. 

## Organisatorische Anforderungen
* Das System muss bis zum 28.01.2018, 23:59 Uhr fertiggestellt sein.



# Ergänzende Anforderungen (v2)
## Funktionale Anforderungen
### Selbstständige Systemaktivität
* Das System muss die Möglichkeit besitzen den aktuellen Standort des Benutzers zu bestimmen.



# Ergänzende Anforderungen (v3)
## Funktionale Anforderungen
### Selbstständige Systemaktivität
* Das System muss eine Überprüfung von aktuell vorhandenem Raumequipment innerhalb eines Raumes ermöglichen.
* Das System muss Änderungen bei der Equipmentverteilung in Räumen in Echtzeit in einem persistenten Datenspeicher aktualisieren können.


