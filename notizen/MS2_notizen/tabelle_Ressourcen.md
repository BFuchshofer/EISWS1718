# Ressourcen

__Benutzer__   

| Ressource | Methode | Semantik | content-type (req) | content-type (res) | 
| --------- | ------- | -------- | ------------------ | ------------------ |
| /freeRoom | GET     | gibt einen Raum zurück der in Abhängigkeit der Informationen im Body ausgewählt wurde. | application/json | application/json |
| /room/{room_id} | GET | gibt den aktuellen Status eines Raumes aus | - | application/json |
| /room/{room_id}/{status} | PUT | ändert den Status des angegebenen Raumes in den angegebenen Status | application/json | application/json |


__Administrator__   

| Ressource | Methode | Semantik | content-type (req) | content-type (res) | 
| --------- | ------- | -------- | ------------------ | ------------------ |
| /room | PUT     | erstellt einen neuen Raum im System mit den im Body enthaltenen Informationen | application/json | application/json |
| /room/{room_id} | PUT | ändert die Informationen eines bestehenden Raumes mit den im Body enthaltenen Informationen | - | application/json |
| /room/{room_id} | DELETE | entfernt einen Raum aus dem System | - | - |
| /room/{room_id} | GET | enthält alle Informationen die zu einem Raum gespeichert wurden | - | application/json |









