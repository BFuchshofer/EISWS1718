# Anwendungsfälle (Task Modeling)

## Aufgaben (Brainstorming)

* Benutzeraufgaben
    - Der Benutzer identifiziert sich im System.
        - Identifikation des Benutzers durch ein Merkmal.
    - Der Benutzer sucht einen beliebigen freien Raum den er nutzen kann.
        - Ein zufälliger Raum soll ausgegeben werden da keine besonderen Präferenzen vorliegen.
    - Der Benutzer sucht einen ruhigen Raum in dem er Arbeiten kann.
        - Ein Raum zur stillen Einzelarbeit ist gesucht. Mehrere Einzelpersonen in einem Raum möglich.
    - Der Benutzer sucht einen Raum für mehrere Personen den er nutzen kann.
        - Gruppenarbeitsraum für mehrere Personen zum arbeiten wird gesucht.
    - Der Benutzer sucht für mehrere Personen, mehrere freie Räume, die er nutzen kann.
        - Anwendbar wenn z.B. ein Dozent für seine Studenten Räume zum arbeiten sucht.
    - Der Benutzer sucht einen freien Raum mit bestimmten Equipment den er nutzen kann.
        - Verschiedene Arbeitsmittel sind gesucht die der Benutzer für seine arbeiten benötigt.
    - Der Benutzer sucht einen freien Raum der die vom Benutzer gewünschte Größe entspricht und den er nutzen kann.
        - Raum für eine bestimmte Personenanzahl gesucht, bspw. bei Präsentationen.
   
* Administratoraufgaben
    - Der Administrator möchte einen Raum im System hinzufügen.
        - Neue Anbauten oder bisher nicht relevante Räume sollen dem System hinzugefügt werden.
    - Der Administrator möchte einen Raum aus dem System entfernen.
        - Ein Raum soll nicht mehr genutzt werden oder wurde umfunktioniert.
    - Der Administrator möchte einen bestehenden Raum im System mit neuen Informationen anreichern.
        - Bspw. ein Raum bekommt einen Beamer installiert.
    - Der Administrator möchte Informationen über einen bestehenden Raum im System abändern.
        - Ein Beamer in einem Raum ist defekt und muss aus der Inventarliste komplett entfernt werden.

## Essential Use Cases

#### Administratoraufgaben

__Der Administrator möchte einen Raum im System hinzufügen.__  
*addRoom* 

| User Intention | System Responsibility |
| ---------------| --------------------- |
|  dem System mitteilen welcher Raum im System hinzugefügt werden soll |  |
|   | gibt Statusmeldung über den Erfolg an den Benutzer zurück |


__Der Administrator möchte einen Raum aus dem System entfernen.__  
*deleteRoom* 

| User Intention | System Responsibility |
| ---------------| --------------------- |
|  dem System mitteilen welcher Raum aus dem System entfernt werden soll |  |
|   | gibt Statusmeldung über den Erfolg an den Benutzer zurück |


__Der Administrator möchte einen bestehenden Raum im System mit neuen Informationen anreichern.__  
*addRoomInformations* 

| User Intention | System Responsibility |
| ---------------| --------------------- |
|  dem System mitteilen welcher Raum im System welche neuen Informationen erhalten soll |  |
|   | gibt Statusmeldung über den Erfolg an den Benutzer zurück |


__Der Administrator möchte Informationen über einen bestehenden Raum im System abändern.__  
*updateRoomInformations* 

| User Intention | System Responsibility |
| ---------------| --------------------- |
|  dem System mitteilen welche Informationen eines bestimmten Raumes im System geändert werden soll |  |
|   | gibt Statusmeldung über den Erfolg an den Benutzer zurück |


#### Benutzeraufgaben

__Der Benutzer identifiziert sich im System.__
*identify*
| User Intention | System Responsibility |
| ---------------| --------------------- |
|  dem System mitteilen in welcher Beziehung er zu ihm steht |  |
|   | überprüft Berechtigungen des Benutzers |


__Der Benutzer sucht einen beliebigen freien Raum den er nutzen kann.__  
*searchingStandardRoom* 

| User Intention | System Responsibility |
| ---------------| --------------------- |
|  dem System mitteilen das er einen beliebigen freien Raum sucht |  |
|   | Raumidentifikation an den Benutzer zurückgeben |


__Der Benutzer sucht einen ruhigen Raum in dem er Arbeiten kann.__   
*searchingSilentRoom*

| User Intention | System Responsibility |
| ---------------| --------------------- |
|  dem System mitteilen das er einen ruhigen freien Raum sucht |  |
|   | Raumidentifikation an den Benutzer zurückgeben |


__Der Benutzer sucht einen Raum für mehrere Personen den er nutzen kann.__   
*searchingRoomForSeveralPersons*

| User Intention | System Responsibility |
| ---------------| --------------------- |
|  dem System mitteilen das er einen Raum für mehrere Personen sucht |  |
|   | Raumidentifikation an den Benutzer zurückgeben |


__Der Benutzer sucht für mehrere Personen, mehrere freie Räume, die er nutzen kann.__   
*searchingSeveralRoomsForSeveralPersons*

| User Intention | System Responsibility |
| ---------------| --------------------- |
|  dem System mitteilen das er mehrere Räume für mehrere Personen sucht |  |
|   | Raumidentifikationen an den Benutzer zurückgeben |


__Der Benutzer sucht einen freien Raum mit bestimmten Equipment den er nutzen kann.__   
*searchingRoomWithSpecificEquipment*

| User Intention | System Responsibility |
| ---------------| --------------------- |
|  dem System mitteilen das er einen Raum mit vom Benutzer definierten Equipment sucht |  |
|   | Raumidentifikation an den Benutzer zurückgeben |


__Der Benutzer sucht einen freien Raum der die vom Benutzer gewünschte Größe entspricht und den er nutzen kann.__   
*searchingRoomWithSpecificSize*

| User Intention | System Responsibility |
| ---------------| --------------------- |
|  dem System mitteilen das er einen Raum mit vom Benutzer definierter Größe sucht |  |
|   | Raumidentifikation an den Benutzer zurückgeben |


## Narrativ Concrete Use Cases

#### Administratoraufgaben

__Der Administrator möchte einen Raum im System hinzufügen.__  
*addRoom* 

| User action | System response |
| ---------------| --------------------- |
|  Der Benutzer interagiert mit dem System und teilt ihm mit das er einen neuen Raum im System hinzufügen möchte. Dazu übermittelt er dem System die dafür benötigten Informationen. |  |
|   | __>identify__ |
|   | Besitzt der Benutzer die benötigten Berechtigungen für diese Aktion, wertet das System die vom Benutzer spezifizierten Informationen aus, verarbeitet diese und speichert die verarbeiteten Information innerhalb des persistenten Datenspeichers des Systems. |
|   | Im Anschluss präsentiert das System dem Benutzer eine Statusmeldung über den Erfolg oder Misserfolg der Aktion.|


__Der Administrator möchte einen Raum aus dem System entfernen.__  
*deleteRoom* 

| User action | System response |
| ---------------| --------------------- |
| Der Benutzer interagiert mit dem System und teilt ihm mit das er einen im persistenten Datenspeicher vorhandenen Raum aus dem System entfernen möchte. Dazu übermittelt er dem System die dafür benötigten Informationen. |  |
|   | __>identify__ |
|   | Trifft das zu, wertet das System die vom Benutzer spezifizierten Informationen aus, verarbeitet diese und speichert die verarbeiteten Information innerhalb des persistenten Datenspeichers des Systems. |
|   | Im Anschluss präsentiert das System dem Benutzer eine Statusmeldung über den Erfolg oder Misserfolg der Aktion.|


__Der Administrator möchte einen bestehenden Raum im System mit neuen Informationen anreichern.__  
*addRoomInformations* 

| User action | System response |
| ---------------| --------------------- |
|  Der Benutzer interagiert mit dem System und teilt ihm mit das er einen im persistenten Datenspeicher vorhandenen Raum mit neuen Informationen anreichern möchte. Dazu übermittelt er dem System die dafür benötigten Informationen. |  |
|   | __>identify__ |
|   | Trifft das zu, wertet das System die vom Benutzer spezifizierten Informationen aus, verarbeitet diese und speichert die verarbeiteten Information innerhalb des persistenten Datenspeichers des Systems. | 
|   | Im Anschluss präsentiert das System dem Benutzer eine Statusmeldung über den Erfolg oder Misserfolg der Aktion. |


__Der Administrator möchte Informationen über einen bestehenden Raum im System abändern.__  
*updateRoomInformations* 

| User action | System response |
| ---------------| --------------------- |
|  Der Benutzer interagiert mit dem System und teilt ihm mit das er Informationen zu einem Raum innerhalb des persistenten Datenspeichers des Systems ändern möchte . Dazu übermittelt er dem System die dafür benötigten Informationen. |  |
|   | __>identify__ |
|   | Trifft das zu, wertet das System die vom Benutzer spezifizierten Informationen aus, verarbeitet diese und speichert die verarbeiteten Information innerhalb des persistenten Datenspeichers des Systems. |
|   | Im Anschluss präsentiert das System dem Benutzer eine Statusmeldung über den Erfolg oder Misserfolg der Aktion. |



#### Benutzeraufgaben

__Der Benutzer identifiziert sich im System.__
*identify*   

| User Intention | System Responsibility |
| ---------------| --------------------- |
|  Der Benutzer interagiert mit dem System und teil ihm mit in welcher Beziehung er zu dem System steht. Dazu übermittelt er dem System die dafür benötigten Informationen. |  |
|   | Das System identifiziert den Benutzer anhand bestimmter Merkmale und prüft somit welche Berechtigungen der Benutzer innerhalb des Systems besitzt. |


__Der Benutzer sucht einen beliebigen freien Raum den er nutzen kann.__     
*searchingStandardRoom* 

| User action | System response |
| ---------------| --------------------- |
|  Der Benutzer interagiert mit dem System und teilt ihm mit das er einen beliebigen freien Raum benötigt in dem er arbeiten kann. Dazu übermittelt der Benutzer automatisch dem System die dafür benötigten Informationen. |  |
|   | __>identify__ |
|   | Besitzt der Benutzer die benötigten Berechtigungen, wertet das System die vom Benutzer spezifizierten Informationen aus, verarbeitet diese und ermittelt die Raumnummer eines Raumes der den Wunsch des Benutzers entspricht. |
|   | Im Anschluss präsentiert das System dem Benutzer die identifizierte Raumnummer. |


__Der Benutzer sucht einen ruhigen Raum in dem er Arbeiten kann.__   
*searchingSilentRoom*

| User action | System response |
| ---------------| --------------------- |
| Der Benutzer interagiert mit dem System und teilt ihm mit das er einen ruhigen Raum benötigt in dem er arbeiten kann. Dazu übermittelt er dem System die dafür benötigten Informationen. | |
|   | __>identify__ |
|   | Besitzt der Benutzer die benötigten Berechtigungen, wertet das System die vom Benutzer spezifizierten Informationen aus, verarbeitet diese und ermittelt die Raumnummer eines Raumes der den Wunsch des Benutzers entspricht. |
|   | Im Anschluss präsentiert das System dem Benutzer die identifizierte Raumnummer. |


__Der Benutzer sucht einen Raum für mehrere Personen.__   
*searchingRoomForSeveralPersons*

| User action | System response |
| ---------------| --------------------- |
| Der Benutzer interagiert mit dem System und teilt ihm mit das er einen Raum benötigt in dem er mit mehreren Personen arbeiten kann. Dazu übermittelt er dem System die dafür benötigten Informationen. | |
|   | __>identify__ |
|   | Besitzt der Benutzer die benötigten Berechtigungen, wertet das System die vom Benutzer spezifizierten Informationen aus, verarbeitet diese und ermittelt die Raumnummer eines Raumes der den Wunsch des Benutzers entspricht. |
|   | Im Anschluss präsentiert das System dem Benutzer die identifizierte Raumnummer. |


__Der Benutzer sucht für mehrere Personen, mehrere freie Räume, die er nutzen kann.__   
*searchingSeveralRoomsForSeveralPersons*

| User action | System response |
| ---------------| --------------------- |
| Der Benutzer interagiert mit dem System und teilt ihm mit das er mehrere Räume benötigt in denen er mit mehreren Personen arbeiten kann. Dazu übermittelt er dem System die dafür benötigten Informationen. | |
|   | __>identify__ |
|   | Besitzt der Benutzer die benötigten Berechtigungen, wertet das System die vom Benutzer spezifizierten Informationen aus, verarbeitet diese und ermittelt die Raumnummern von Räumen die den Wunsch des Benutzers entsprechen. |
|   | Im Anschluss präsentiert das System dem Benutzer die identifizierten Raumnummern. |


__Der Benutzer sucht einen freien Raum mit bestimmten Equipment den er nutzen kann.__   
*searchingRoomWithSpecificEquipment*

| User action | System response |
| ---------------| --------------------- |
| Der Benutzer interagiert mit dem System und teilt ihm mit das er einen Raum benötigt in dem bestimmtes Equipment vorhanden ist das er zum arbeiten benötigt. Dazu übermittelt er dem System die dafür benötigten Informationen. | |
|   | __>identify__ |
|   | Besitzt der Benutzer die benötigten Berechtigungen, wertet das System die vom Benutzer spezifizierten Informationen aus, verarbeitet diese und ermittelt die Raumnummer eines Raumes der den Wunsch des Benutzers entspricht. |
|   | Im Anschluss präsentiert das System dem Benutzer die identifizierte Raumnummer. |


__Der Benutzer sucht einen freien Raum der die vom Benutzer gewünschte Größe entspricht und den er nutzen kann.__ 
*searchingRoomWithSpecificSize*

| User action | System response |
| ---------------| --------------------- |
| Der Benutzer interagiert mit dem System und teilt ihm mit das er einen Raum benötigt der eine bestimmte Größe besitzt um darin arbeiten zu können. Dazu übermittelt er dem System die dafür benötigten Informationen. | |
|   | __>identify__ |
|   | Besitzt der Benutzer die benötigten Berechtigungen, wertet das System die vom Benutzer spezifizierten Informationen aus, verarbeitet diese und ermittelt die Raumnummer eines Raumes der den Wunsch des Benutzers entspricht. |
|   | Im Anschluss präsentiert das System dem Benutzer die identifizierte Raumnummer. |




















