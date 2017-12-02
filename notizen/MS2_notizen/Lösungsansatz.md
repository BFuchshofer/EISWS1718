# Lösungsansatz / 1. Gestalltungslösung

Das System das entwickelt werden soll muss dem Benutzer die Möglichkeit bieten die Raumnummer eines Raumes zu erhalten, der seinen Erwartungen und Erfordernissen entspricht. Dabei gilt zu beachten das der auszugebende Raum sich in der Nähe des Standortes des Benutzers befindet.

Es muss also eine Standortbestimmung für den Benutzer erfolgen. Diese sollte automatisch, mittels Bluetooth Beacons erfolgen. 
Die Beacons sind im idealfall an jedem Raum angebracht, so das jedes Beaconsignal die Raumnummer des Raumes enthält. Damit lässt sich eine effektive Standortbestimmung des Benutzers in Abhängigkeit von erkannten Räumen in der Umgebung des Benutzers ermitteln. Die Anwendung kann die ID des Beacon an den Server senden welcher sie mit Informationen in einer Datenbank abgleicht. In der Datenbank sind Zuordnungen von Raumnummer und zugehöriger Beacon bzw Beacon in Reichweite verzeichnet. Anhand dieser Informationen kann der Server den Standort des Benutzers ermitteln und seine Berechnungen für einen Raum in der Nähe des Benutzers festlegen. 
Für den Fall das die Anwendung beim Client gestartet, und kein Beaconsignal gefunden wird, sollte eine alternative Lösung angeboten werden. Als Beispiel ist zu nennen das dem Benutzer vorgeschlagen wird sich zu irgendeinen Raum zu begeben um ein Signal empfangen zu können. Alternativ wäre auch ein standart Pseudostandort für den Benutzer möglich, anhand dessen sich das System orientiert und seine Berechnungen anstellt. Man könnte auch, sofern vorhanden, mit den letzten empfangenen Beacondaten arbeiten, wenn diese auf Clientseite temporär gespeichert werden. 
Damit die Berechnung für einen optimierten Laufweg des Benutzers aufgestellt werden kann, muss dem System eine Gewichtung für Räume vorliegen, an der es sich in Abhängigkeit des Standortes des Benutzers orientieren kann. Hier wäre der Kruskal-Algorithmus aus der Graphentheorie eine Möglichkeit, um anhand von Gewichtungen einen minimalen Weg zu berechnen.













