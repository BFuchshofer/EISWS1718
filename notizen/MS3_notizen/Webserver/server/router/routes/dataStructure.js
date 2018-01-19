var timeReservation = 600000    // 10 Minuten in Milisekunden
var timeBooking = 3600000       // 60 Minuten in Milisekunden

/******************/
/* Einzelner Raum */
/******************/

// Request Body - Was du im Request von mir bekommst
{
    "user":data.user,                       // String
    "beacon":data.beacon,                   // String
    "person":data.person,                   // Int
    "roomSize":data.roomSize,               // ??? (vorerst Int)
    "blackboard":data.blackboard,           // Int
    "whiteboard":data.whiteboard,           // Int
    "beamer":data.beamer,                   // Int
    "chairTable":data.chairTable,           // boolean
    "token":data.token                      // String ("GET", "UPDATE", "BOOK")
}

// Response Body - Was ich im Response von dir brauche
{
    "user":data.user,                       // String
    "beacon":data.beacon,                   // String
    "person":data.person,                   // Int
    "roomSize":data.roomSize,               // ??? (vorerst Int)
    "blackboard":data.blackboard,           // Int
    "whiteboard":data.whiteboard,           // Int
    "beamer":data.beamer,                   // Int
    "chairTable":data.chairTable,           // boolean
    "token":data.token,                     // String ("GET" -> neuen Raum anfordern, "UPDATE" -> neuer Beacon erkannt/aktualisierung anfordern, "BOOK" -> Raum buchen, "CANCEL" -> Raum wieder freigeben)
    "remainingTime":time,                   // Long (timeReservation, timeBooking) -> je nach Token
    "room_id":id                            // String (o.ä.? je nachdem wie die RNummer aussieht)
}

// Bei "UPDATE-Token: nach neuen passenden Räumen suchen, wenn einer gefunden wurde die neue RaumID zurücksenden. Wenn kein neuer Raum vorliegt die alte RaumID zurücksenden



/*****************/
/* Mehrere Räume */
/*****************/

// Request Body - Was du im Request von mir bekommst
{
    "user":data.user,                       // String
    "beacon":data.beacon,                   // String
    "person":data.person,                   // Int
    "roomSize":data.roomSize,               // ??? (vorerst Int)
    "roomCount":data.roomCount,             // Int (anzahl der Räume)
    "blackboard":data.blackboard,           // Int
    "whiteboard":data.whiteboard,           // Int
    "beamer":data.beamer,                   // Int
    "chairTable":data.chairTable,           // boolean
    "token":data.token                      // String ("GET", "UPDATE", "BOOK")
}

// Response Body - Was ich im Response von dir brauche
{
    "user":data.user,                       // String
    "beacon":data.beacon,                   // String
    "person":data.person,                   // Int
    "roomSize":data.roomSize,               // ??? (vorerst Int)
    "roomCount":data.roomCount,             // Int (anzahl der Räume)
    "blackboard":data.blackboard,           // Int
    "whiteboard":data.whiteboard,           // Int
    "beamer":data.beamer,                   // Int
    "chairTable":data.chairTable,           // boolean
    "token":data.token,                     // String ("GET" -> neuen Raum anfordern, "UPDATE" -> neuer Beacon erkannt/aktualisierung anfordern, "BOOK" -> Raum buchen, "CANCEL" -> Raum wieder freigeben)
    "remainingTime":time,                   // Long (timeReservation, timeBooking) -> je nach Token
    "room_id":id                            // String (o.ä.? je nachdem wie die RNummer aussieht)
}

// Bei "UPDATE-Token: nach neuen passenden Räumen suchen, wenn einer gefunden wurde die neue RaumID zurücksenden. Wenn kein neuer Raum vorliegt die alte RaumID zurücksenden


