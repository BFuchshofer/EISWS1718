# Inhaltsverzeichnis

- Einleitung
    - Nutzungsproblem
    - Projektidee
    - Ziele
    - Domänenrecherche
    - Marktrecherche
    - Alleinstellungsmerkmale
        - Begründen!
    - Risiken des Projekts
        - "PoCs im späteren Verlauf"
        - Begründen!

- Einleitung
- Nutzungsproblem
- Technologieunabhängiger Lösungsvorschlag
- Dömänenrecherche
- Marktrecherche
- Alleinstellungsmerkmale
- Ziele


### Einleitung
Der Faktor Zeit spielt in vielen Lebenslagen eine entscheidende Rolle! Sei es die Einhaltung von Fristen für Abgaben von Dokumentationen, Präsentationen oder entwickelten Produkten, die genaue Dokumentation von geleisteten Arbeitsstunden oder der Wegstrecke die zurückgelegt werden muss um von Punkt A nach Punkt B zu kommen. In vielen Bereichen ist es wichtig Zeit einzusparen, da Zeit bekanntermaßen Geld ist. Es gilt also in vielen Unternehmen Zeit einzusparen um die Kosten zu senken. In nicht gewerbstätigen Bereichen ist der Faktor Zeit dahingehend wichtig, da mit mehr Zeit auch mehr Aktivitäten ausgeführt werden können. Es liegt also nahe auch in diesen Bereichen die verfügbare Zeit zu optimieren. Ein Punkt an dem dieses Projekt anknüpfen soll, ist das schnelle und  unkomplizierte finden von Räumen die den Erfordernissen und Erwartungen einer Person oder Personengruppe entsprechen.


### Nutzungsproblem
Um die Belegungen von Räumen zu verwalten, benutzen die meisten Unternehmen und Organisationen den klassischen Belegungsplan der sich vor jedem Raum befindet, wo Mitarbeiter oder andere Personen sich über die Belegung von bestimmten Räumen informieren können. Dieser Belegungsplan ist meist eine auf Papier gedruckte Tabelle mit den Tagen und Uhrzeiten wann dieser Raum von wem offiziell belegt ist. Daraus lässt sich dann ableiten wann ein Raum nicht belegt, und theoretisch von anderen Personen genutzt werden kann. Allerdings sind in diesen Plänen in der Regel nur fest definierte Belegungen, die oft wöchentlich wiederkehren, verzeichnet. Dynamische Nutzungen eines Raumes lassen sich daraus meist nicht ableiten. Die logischste Möglichkeit herrauszufinden ob ein Raum leer ist, ist natürlich diesen einfach zu öffnen und nachzuschauen. Dies setzt allerdings vorraus das man sich bereits vor so einem entsprechenden Raum befindet. Um auch Personen die Raumsuche zu ermöglichen, die sich nicht zufällig in einem Gang mit möglichen Arbeitsräumen befinden oder vor einem entsprechenden Raum stehen, wird ein System benötigt das über Entfernung überprüft ob ein Raum zur Verfügung steht oder nicht. Um den Faktor Zeit ins Spiel zu nehmen, wird ein System benötigt das in Abhängigkeit der aktuellen Position des Benutzers einen freien Raum angibt, der sich in der Nähe des Benutzers befindet. Muss man sich erst in ein Gebäude, Stockwerk oder Gang begeben um Informationen über den Status eines Raumes einzuholen, wird Zeit in Form von zusätzlichen Laufwegen verschwendet, da die Möglichkeit besteht das kein Raum in diesem Gebäude aktuell zur Verfügung steht, und man so andere Bereiche aufsuchen muss. Neben einer Zeitersparnis eliminiert man außerdem den Störfaktor der entsteht wenn eine nach einen freien Raum suchende Personen einen Raum betritt in dem bereits gearbeitet wird. Die Möglichkeit bei der Raumsuche Zeit zu sparen, die man somit für wichtige Arbeiten verwenden kann, ist also durchaus relevant um effektiver arbeiten zu können.   
In diesem Projekt soll es um die Einsparrung von Zeit in Form von verkürzten Laufwegen bzw. einer verkürzten Zeit die zum suchen eines Raumes anfallen würde, gehen.


### Technologieunabhängiger Lösungsvorschlag
Um dem Problem der spontanen und unstrukturierten Raumbelegung entgegen zu wirken ist ein System nötig, welches sowohl die verfügbaren Räume, als auch wöchentlich wiederkehrende und spontanen Belegungen aufnimmt, um damit einer Person oder Personengruppe auf Raumsuche einen Raum vorschlagen kann. 
Der Benutzer sollte dabei die Möglichkeit haben aus der Ferne sich über den Status eines Raumes informieren zu können.
Bei Bedarf sollte die Möglichkeit bestehen diesen Raum für eine bestimmte Zeitspanne zu buchen, damit gewährleistet wird das kein anderer Benutzer diesen Raum für seine Zwecke verwendet.
Dabei sollte darauf geachtet werden, dass der vorgeschlagene Raum auch dem Vorhaben der Person genügt.


### Dömänenrecherche
Das beschriebene Problem der dynamischen Raumsuche kann so gut wie in jeder Organisation auftreten, in der es mehrere Räume gibt die für unterschiedliche Arbeiten genutzt werden können. Vorallem in Organisationen die eine Vielzahl von verschiedensten Räumen besitzen, möglicherweise sogar über mehrere Etagen oder Komplexe verteilt, ist die Gefahr groß das eine arbeitsplatzsuchende Person Zeit mit langen Laufwegen verschwendet, da ihr nicht bekannt ist wo sich der nächste frei nutzbare Raum befindet. Das Problem nimmt größere Ausmaße an, um so mehr Personen einen freien Raum suchen. Lehreinrichtungen mit vielen Schülern, Studenten oder Auszubildenden Personen sind ein guter Kandidat um eine Lösung für ein solches Nutzungsproblem zu finden. Dadurch das Lerner neben festen Veranstalltungen noch beispielsweise Gruppenarbeiten oder Stillarbeit erledigen, ergibt das eine sehr variable Anzahl an Personen die im gleichen Zeitraum einen freien Raum benötigen. Die Nutzergruppen beschränken sich also auf die Angestellten und Mitarbeiter der Lehreinrichtung, sowie alle Lerner die interessiert an einer Funktion sind, die es ihnen ermöglicht schnell und einfach einen freien Raum zu finden der ihren Ansprüchen genügt. 
Dabei ist nicht nur das finden von freien Räumen sondern, je nach Kontext, die Information ob ein Raum gerade belegt ist interessant. Das Reinigungspersonal kann somit z.B. erfahren welcher Raum aktuell belegt ist um die darin befindlichen Personen nicht zu stören. Desweiteren können Mitarbeiter die Wartungsarbeiten innerhalb eines Raumes durchführen herrausfinden, wann sie für ihre Arbeiten den Raum belegen und nutzen können. Die Laufwege und dadurch das Zeitmanagement von Mitarbeitern kann somit ebenfalls optimiert werden.
Im Verlauf dieser Dokumentation wird ein System beschrieben dessen Anwendungsdomäne sich auf Lehreinrichtungen bezieht.


### Marktrecherche
Schaut man sich im Internet nach Angeboten zu Raumplanungs Systemen um, finden sich viele Angebote die in diese Richtung gehen.   
Ein Raum und Ressourcen Management Systeme wie von [Locaboo](http://locaboo.com) angeboten, bietet die Möglichkeit der Verwaltung und Auslastung von Räumen und ganzen Veranstalltungsstätten. Allerdings ist dieses System mehr als ein digitaler Kalender zu sehen der im Prinzip die digitalisierte und standortunabhängige Version des Belegungsplanes vor jedem Raum darstellt. Es gibt zwar die Möglichkeit schnell und einfach Belegungen hinzuzufügen oder zu entfernen, jedoch liegt der Fokus mehr auf dem verwalten von Räumen aus Veranstaltersicht, und nicht aus der Benutzersicht der einzelnen Raumnutzer.   
[INTIME](http://comtech-noeker.de) von *COMTEC* bietet ebenfalls die Möglichkeit Räume und Gebäude besser verwalten zu können. Dabei bietet es auch neben zahlreichen Zusatzfunktionen die für Lehreinrichtungen weniger relevant sind, für den einzelnen Benutzer die Möglichkeit schnell einen Raum zu finden der seinen Erwartungen und Erfordernissen entspricht. Allerdings wird ihm nur die Verfügbarkeit eines solchen Raumes angezeigt. Ob sich der Raum in seiner Nähe oder am anderen Ende des Gebäudekomplexes befindet ist nicht ersichtlich. Hier wird der Faktor Zeit also nicht berücksichtigt, der Fokus liegt vielmehr auf dem Verfügbarkeitscheck.   
Die Funktionen der [Online-Raumverwaltung](http://online-raumverwaltung.de) der *OMOC interactive* bietet zwar eine praktische und cloudbasierte Möglichkeit um Räume für Veranstalltungen zu organisieren und verwalten, hier liegt der Fokus aber mehr auf der Vermietung und der Auslastung von solchen Räumen. Der Endbenutzer mit seinem Interesse Zeit zu sparen bei seiner Raumsuche ist hier nicht adressiert.   
Eine vergleichbare Lösung innerhalb der Anwendungsdomäne bietet die Technische Hochschule Köln, die ein [Online-Tool](http://advbs06.gm.fh-koeln.de:7777/plsql/sport.raum_abfrage) zur Raumbelegung zur Verfügung stellt. Dieses Tool ist allerdings nur die digitalisierte Form der Aushangpläne vor den Räumen und bietet keine Möglichkeit dynamisch Raumbelegungen zu erfassen und zu verwalten. Somit ist keine Ausgabe von freien Räumen in Echtzeit möglich.   
Es gibt viele Softwarelösungen für ein besseres Raummanagement, jedoch ist keine davon darauf ausgelegt dem einfachen Benutzer einen freien Raum in seiner Nähe anzuzeigen und diesen für ihn zu reservieren / buchen. Alle gefundenen Lösungen beziehen sich auf die Vermietung von Räumen oder Veranstaltungsorten die von einem Unternehmen verwaltet werden. Die Benutzer sind in den meisten Fällen die Unternehmen selbst die eine Managementfunktion benötigen. In unserem Problemraum sind die Benutzer aber in der Regel Einzelpersonen die einen freien Raum in ihrer Lehreinrichtung suchen.


### Alleinstellungsmerkmale
Betrachtet man die identifizierten Konkurenzprodukte, erkennt man das der Verfügbarkeitscheck von bestimmten Räumen bei den meisten Produkkten eine kleine, wenn auch nicht unbedeutende, Teilfunktion darstellt. Zeit wird nur dahingehend eingespart, da man sich schnell und einfach über einen freien Raum informieren kann. Die Möglichkeit Zeit zu sparen, indem man kürzere Laufwege und Suchzeiten berechnet, ist in keinem der Produkte namentlich erwähnt. Unser System wird also genau an diesem Punkt anknüpfen um die gefundenen Lücken zu schließen. Konkret bedeutet das, das sich folgende Alleinstellungsmerkmale für unser System im Vergleich zur gefundenen Konkurenz ergeben:
* Zeitersparnis durch verkürze Laufwege und Suchzeiten.
* Eliminierung des Störfaktors der beim betreten eines vollen Raumes entsteht.
* Ein System für einfache Benutzer die einen Raum suchen der ihren Erwartungen entspricht.


### Ziele
Anhand des identifizierten Nutzungsproblems und der ermittelten Alleinstellungsmerkmale, lassen sich konkrete Ziele aufstellen die so ein System  erfüllen muss um einen qualitativen Lösungsansatz zu erzielen.   
Die folgenden Ziele sind unterteilt in Strategische, Taktische und Operative Ziele, die zu Teilen wärend des Entwicklungsprozesses gebildet wurden.

__Strategische Ziele__   
* Das fertige System in unserem Projekt __muss__ es dem Benutzer ermöglichen innerhalb von maximal 30 Sekunden die Raumnummer eines Raumes zu erhalten der den Erfordernisen des Benutzers entspricht. 
* Außerdem __muss__ es speziellen Benutzern ermöglicht werden Informationen über den Status eines Raumes abzufragen.  
* Dabei __muss__ das System alle zugänglichen Räume innerhalb der Lehreinrichtung in Echtzeit adressieren und verwalten können. 


__Taktische Ziele__   
* Um auf unterschiedliche Rauminhalte eingehen zu können, __sollte__ jeder einzelne Raum mit zugehörigen Informationen über seine Ausstattung angereichert werden.
* Diese Rauminformationen __müssen__ innerhalb eines persistenten Datenspeichers gespeichert, und auf Anfrage an eine Verarbeitungseinheit geschickt werden.    
* Diese Verarbeitungseinheit __muss__ ein Server sein der die Informationen in den persistenten Datenspeicher schreiben und lesen kann.    
* Außerdem __muss__ der Server diese Informationen algorithmisch anreichern und dem Benutzer an einem Endgerät präsentieren können.    


__Operative Ziele__
* 





















