### Probleme
* Personenerkennung im Raum
    - Benutzererkennung beim betreten des Raumes über Chip in Multica
    - Minicomputer erkennt Handy und misst entfernung zum computer(eingang)
        - Beaconentfernung
        - große entfernung - kleine entfernung --> Benutzer bewegt sich zur Tür
        - kleine Entfernung - große Entfernung --> Benutzer entfernt sich von der Tür
* Standortbestimmung der Benutzer
    - Positionseingabe über den Nutzer
    - GPS
    - Beacons
* Aktuallisierung von Gegenständen in Räumen (Stichwort: Flexible Räume)
* Laufwegoptimierung
* Unterschiedliche Raumtypen
*


### Technologien

#### Personenzählung
Zur Findung einer Methode für effektive Personenzählung haben wir ein
Brainstorming durchgeführt und alle Methoden aufgeschrieben die uns eingefallen
sind. Darunter befinden sich Methoden wie die algorithmische Personenzählung
durch Wärmebild- und Videokamera, aber auch die Erkennung durch Lichtschranken,
Druckplatten und Bewegungsmelder.
Im Folgenden werden die einzelnen Methoden kurz erläutert und einige Vor- und
Nachteile aufgezählt.

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




#### Standortbestimmung
Damit durch unser System der Raum rausgesucht werden kann, der sowohl den
Anforderungen des Benutzers entspricht, als auch einen möglichst kurzen Laufweg
hat muss der Standort des Benutzers innerhalb des Gebäudes bestimmt werden.
Als Methoden zur Feststellung des Standortes haben wir sowohl die Bestimmung
durch Benutzereingaben, als auch die durch GPS und Beacons innerhalb des
Gebäudes betrachtet.
Die Standortbestimmung über Benutzereingaben würde dabei in der Art und Weise
funktionieren, dass ein Benutzer einen Bezugspunkt innerhalb eines Gebäudes,
also eine Raumnummer oder die Kennzeichnung eines Treppenhauses, angibt. Wir
haben uns allerdings gegen diese Methode entschieden da sie eine Interaktion
mit dem Benutzer benötigt und dies gegebenenfalls den Nutzungsfluss des Systems
stören könnte. Diese Methode kann allerdings als Fallback verwendet werden,
wenn die Standortbestimmung durch andere Methoden oder Technologien scheitert.

Durch die Verwendung von GPS könnte ebenfalls der Standort des Benutzers
bestimmt werden. Dabei würde mittels des Endgerätes des Benutzers die Verbindung
zum "Global Positioning System" aufgebaut und somit der Standort mit einer hohen
Genauigkeit bestimmt werden. Da die Genauigkeit allerdings schwanken kann haben
wir uns gegen die Verwendung von GPS entschieden da eine fehlerhafte oder
ungenaue Messung zu Falschen Ergebnissen innerhalb des Systems führen kann.

Als Weitere Methode haben wir uns die Zuordnung eines Standortes durch die
Verwendung von Bluetooth Beacons. Diese senden in festlegbaren Intervallen ein
Signal an die Empfänger womit sie ihre Kennnummer übertragen. Diese kann dann
von einem System zu einer bestimmten Position zugeordnet werden und so der
Standort des Benutzers bestimmt werden.
