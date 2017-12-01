# Anwendungsfälle (Task Modeling)

### Aufgaben

* Der Benutzer sucht einen beliebigen freien Raum den er nutzen kann.
* Der Benutzer sucht einen ruhigen Raum in dem er Arbeiten kann.
* Der Benutzer sucht einen Raum für mehrere Personen.
* Der Benutzer sucht für mehrere Personen, mehrere freie Räume, die er nutzen kann.
* Der Benutzer sucht einen freien Raum mit bestimmten Equipment den er nutzen kann.
* Der Benutzer sucht einen freien Raum der die vom Benutzer gewünschte Größe entspricht und den er nutzen kann.
* Der Benutzer möchte einen freien Raum für einen bestimmten Zeitraum für andere Benutzer sperren.
* Der Benutzer möchte einen freien Raum für eine bestimmte Zetspanne reservieren.
* Der Benutzer möchte einen freien Raum für eine bestimmte Zetspanne buchen.
* Der Benutzer möchte einen von ihm gesperrten Raum wieder freigeben.
* Der Benutzer möchte einen von ihm gefundenen freien Raum buchen.
* Der Benutzer möchte die Reservierung für einen von ihm reservierten Raum stornieren.
* Der Benutzer möchte die Buchung für einen von ihm gebuchten Raum stornieren.
* Der Benutzer möchte die Buchung für einen von ihm gebuchten Raum verlängern.
* Der Benutzer möchte wissen ob ein bestimmter Raum gerade reserviert, gebucht oder gesperrt ist.   

*__Aufgaben für den Admin?__*


### Essential Use Cases

__Der Benutzer sucht einen beliebigen freien Raum den er nutzen kann.__  

| User Intention | System Responsibility |
| ---------------| --------------------- |
|  Der Benutzer möchte einen aktuell zur Verfügung stehenden freien Raum finden. | Das System muss den Benutzer erkennen und anhand von vorhandene Daten einen freien Raum für den Benutzer finden und die Raumnummer dem Benutzer bereitstellen. |


__Der Benutzer sucht einen ruhigen Raum in dem er Arbeiten kann.__

| User Intention | System Responsibility |
| ---------------| --------------------- |
| Der Benutzer möchte die Raumnummer eines Raumes in dem er im stillen arbeiten kann. | Das System muss den Benutzer erkennen und anhand von vorhandenen Daten die Raumnummer eines passenden Raumes dem Benutzer bereitstellen. |


__Der Benutzer sucht einen Raum für mehrere Personen.__

| User Intention | System Responsibility |
| ---------------| --------------------- |
| Der Benutzer möchte die Raumnummer eines Raumes den er mit mehreren Personen nutzen kann. | Das System muss den Benutzer und seine Eingabe der Personenanzahl erkennen und anhand von vorhandenen Daten die Raumnummer eines passenden Raumes dem Benutzer bereitstellen. |


__Der Benutzer sucht für mehrere Personen, mehrere freie Räume, die er nutzen kann.__

| User Intention | System Responsibility |
| ---------------| --------------------- |
| Der Benutzer möchte die Raumnummern von mehreren freien Räumen die er mit mehreren Personen nutzen kann. | Das System muss den Benutzer und seine Eingabe der Personenanzahl erkennen und anhand von vorhandenen Daten die Raumnummern mehrere passender Räume dem Benutzer bereitstellen. |


__Der Benutzer sucht einen freien Raum mit bestimmten Equipment den er nutzen kann.__

| User Intention | System Responsibility |
| ---------------| --------------------- |
| Der Benutzer möchte die Raumnummer eines freien Raumes den er nutzen kann und der bestimmtes Equipment enthält. | Das System muss den Benutzer und seine Eingabe mit Equipmentwünschen erkennen und anhand von vorhandenen Daten die Raumnummer eines passenden Raumes dem Benutzer bereitstellen. |


__Der Benutzer sucht einen freien Raum der die vom Benutzer gewünschte Größe entspricht und den er nutzen kann.__

| User Intention | System Responsibility |
| ---------------| --------------------- |
| Der Benutzer möchte die Raumnummer eines freien Raumes den er nutzen kann und der eine bestimmte Größe hat. | Das System muss den Benutzer und seine Eingabe mit der gewünschten Raumgröße erkennen und anhand von vorhandenen Daten die Raumnummer eines passenden Raumes dem Benutzer bereitstellen. |


__Der Benutzer möchte einen freien Raum für einen bestimmten Zeitraum für andere Benutzer sperren.__

| User Intention | System Responsibility |
| ---------------| --------------------- |
| Der Benutzer möchte einen bestimmten freien Raum für einen bestimmten Zeitraum für andere Benutzer sperren. | Das System muss den Benutzer und seine Eingabe der Raumnummer und des Zeitraumes erkennen und anhand von vorhandenen Daten den Raum im System als nicht nutzbar markieren. |


__Der Benutzer möchte einen freien Raum für eine bestimmte Zetspanne reservieren.__

| User Intention | System Responsibility |
| ---------------| --------------------- |
| Der Benutzer möchte einen vom System vorgeschlagenen freien Raum für eine bestimmte Zeitspanne reservieren. | Das System muss den Benutzer und seinen vom System gelieferten Raumvorschlag erkennen und diesen für eine bestimmte Zeitspanne im System reservieren. Daraufhin ist der Raum für diese Zeitspanne  im System als nicht nutzbar markiert. |


__Der Benutzer möchte einen freien Raum für eine bestimmte Zetspanne buchen.__

| User Intention | System Responsibility |
| ---------------| --------------------- |
| Der Benutzer möchte einen vom System vorgeschlagenen freien Raum für eine bestimmte Zeitspanne buchen. | Das System muss den Benutzer und seinen vom System gelieferten Raumvorschlag erkennen und diesen für eine bestimmte Zeitspanne im System buchen. Daraufhin ist der Raum für diese Zeitspanne im System als nicht nutzbar markiert. |


__Der Benutzer möchte einen von ihm gesperrten Raum wieder freigeben.__

| User Intention | System Responsibility |
| ---------------| --------------------- |
| Der Benutzer möchte einen Raum den er im System für andere Benutzer gesperrt hat wieder freigeben. | Das System muss den Benutzer und seinen im System vorliegenden gesperrten Raum erkennen und diesen Raum im System wieder als nutzbar markieren. |


__Der Benutzer möchte einen von ihm gefundenen freien Raum buchen.__

| User Intention | System Responsibility |
| ---------------| --------------------- |
| Der Benutzer möchte einen von ihm gefundenen leeren Raum buchen. | Das System muss den Benutzer erkennen und, sofern der Raum als nutzbar markiert ist, diesen für eine bestimmte Zeitspanne im System buchen. |


__Der Benutzer möchte die Reservierung für einen von ihm reservierten Raum stornieren.__

| User Intention | System Responsibility |
| ---------------| --------------------- |
| Der Benutzer möchte die von ihm getätigte Reservierung eines Raumes stornieren. | Das System muss den Benutzer und seinen im System vorliegenden reservierten Raum erkennen und diesen Raum im System wieder als nutzbar markieren. |


__Der Benutzer möchte die Buchung für einen von ihm gebuchten Raum stornieren.__

| User Intention | System Responsibility |
| ---------------| --------------------- |
| Der Benutzer möchte die von ihm getätigte Buchung eines Raumes stornieren. | Das System muss den Benutzer und seinen im System vorliegenden gebuchten Raum erkennen und diesen Raum im System wieder als nutzbar markieren. |


__Der Benutzer möchte die Buchung für einen von ihm gebuchten Raum verlängern.__

| User Intention | System Responsibility |
| ---------------| --------------------- |
| Der Benutzer möchte die von ihm getätigte buchung eines Raumes verlängern. | Das System muss den Benutzer und seinen im System vorliegenden gebuchten Raum erkennen und diesen Raum im System um eine bestimmte Zeitspanne als gebucht erweitern. Daraufhin ist der Raum für diese Zeitspanne im System als nicht nutzbar markiert. |


__Der Benutzer möchte wissen ob ein bestimmter Raum gerade reserviert, gebucht oder gesperrt ist.__

| User Intention | System Responsibility |
| ---------------| --------------------- |
| Der Benutzer möchte wissen ob ein bestimmter Raum gerade reserviert, gebucht oder gesperrt ist. | Das System muss die Eingabe des Benutzers mit der Raumnummer erkennen und im System überprüfen ob dieser Raum als reserviert, gebucht, gesperrt oder nutzbar markiert ist. Diese Information muss daraufhin dem Benutzer bereitgestellt werden. |






















