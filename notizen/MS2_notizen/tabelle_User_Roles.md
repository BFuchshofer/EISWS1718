# Role Models

### User Roles
- __FreeRoomSearcher__
    - häufige Verwendung
    - einfache Nutzung
    - wenige Interaktionsschritte

        - __SilentWorkingRoomSearcher__ (specializes)
            - sucht für sofort einen freien Raum
            - sucht stille Arbeitsmöglichkeit
        - __CasualSingleRoomSearcher__ (specializes)
            - sucht für sofort einen freien Raum
            - sucht Arbeitsmöglichkeiten für mehrere Personen
        - __CasualMultiRoomSearcher__ (specializes)
            - sucht für sofort mehrere freie Räume
            - sucht Arbeitsmöglichkeiten für mehrere Personen
        - __CasualRoomWithEquipmentSearcher__ (specializes)
            - sucht für sofort einen freien Raum mit gewünschten Rauminhalten
            - sucht Arbeitsmöglichkeiten
        - __RoomWithProfessionalEquipmentSearcher__ (specializes)
            - sucht für sofort einen freien Raum mit gewünschten speziellen Rauminhalten
        - __SpecificRoomBooker__
            - direktes buchen eines Raumes
        - __RoomScheduler__ (includes)
            - sucht für einen bestimmten Zeitraum in der Zukunft einen freien Raum
            - verantwortungsbewusste Raumreservierung

- __Administrator__
    - einfache Nutzung
    - effiziente Ausführung der Arbeitsaufgaben
    - fehler analyse
    - hohe Toleranz bezüglich häufig verwendeter Formate
        - __DBAdministrator__
            - Verwaltung und Aktuallisierung von Informationen innerhalb eines persistenten Datenspeichers
        - __ServerAdministrator__
            - Verwaltung und Wartung des/der Server(s) des Systems

- __RoomStatusChecker__
    - einfache Nutzung
    - häufige Verwendung
    - wenige Interaktionsschritte
    - den Status eines bestimmten Raumes abfragen
        - __SpecificRoomBooker__
            - direktes buchen eines Raumes
        - __RoomScheduler__
            - sucht für einen bestimmten Zeitraum in der Zukunft einen freien Raum
            - verantwortungsbewusste Raumreservierung
        - __SpecificRoomBlocker__
            - temporäres blockieren von Räumen zwecks Wartungs-/Reinigungsarbeiten
                - __CleaningStaff__
                - __MaintainStaff__
            
            
            
            
_______________            
### Focal Roles
*Focal roles are those few user roles judged to be the most common or typical or that are deemed particularly important fromsomeother perspective.*

*Constantine, Larry L.; Lockwood, Lucy A.D.: Software For Use - A Practical Guide to the Models and Methods of Usage-Centred Design. ACM Press, New York, 1999. __Page 82-84__. ISBN: 0-201-92478-1*

- __CasualSingleRoomSearcher__

________________
________________
### Originalbeispiel
##### User Role
*A user role is an abstract collection of needs, interests, expectaions, behaviors and responsibilities characterizing a relationship between a class or kind of users and a system.*

*Constantine, Larry L.; Lockwood, Lucy A.D.: Software For Use - A Practical Guide to the Models and Methods of Usage-Centred Design. ACM Press, New York, 1999. __Page 79__. ISBN: 0-201-92478-1*

Beispiel für eine Nutzergruppe die Präsentationen erstellen möchte:   

__RoutineMinemalistPresenter__   
frequent use;    
rapid, easy operation;    
unsophisticated uses;    
simple, standard formats: bullet lists, bar charts, pie charts, graphs, etc.;    
standard clip-art.   

*Constantine, Larry L.; Lockwood, Lucy A.D.: Software For Use - A Practical Guide to the Models and Methods of Usage-Centred Design. ACM Press, New York, 1999. __Page 81__. ISBN: 0-201-92478-1*

#### Quelle
Constantine, Larry L.; Lockwood, Lucy A.D.: Software For Use - A Practical Guide to the Models and Methods of Usage-Centred Design. ACM Press, New York, 1999. ISBN: 0-201-92478-1
