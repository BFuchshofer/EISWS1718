

var myList                                  = require( './MultiLinkedList.js' );
var shortestPath                            = require( './ShortestPath.js' );

var MULTILINKEDLIST                         = new myList();


/* *************************************************************
 *
 * BUILDING-PLAN
 *
 * R := ROOM
 * T := STAIRS
 * A := ELEVATOR
 * E := ENTRY
 * K := NODE
 *
 * +---+---+---+                           +---+---+---+
 * | R1|   |   |                           |   |   | R5|
 * +---+---+---+                           +---+---+---+
 * |   |   |T1a|                           |T1b|   |   |
 * +---+---+---+                           +---+---+---+
 * |   |   |   |                           |   |   |   |
 * +---+---+---+                           +---+---+---+
 * |   |   |   |                           |   |   | R6|
 * +---+---+---+                       +---+---+---+---+
 * |   |   |   |                       | A1|   |   |   |
 * +---+---+---+---+---+---+---+---+---+---+---+---+---+
 * | K1|   |   | E1|   |   |   |   | E2|   |   | K2|   |
 * +---+---+---+---+---+---+---+---+---+---+---+---+---+
 * |   |   |   |                           |   |   | R7|
 * +---+---+---+                           +---+---+---+
 * | R2|   |   |                           |   |   |   |
 * +---+---+---+                           +---+---+---+
 * |   |   |   |                           |   |   |   |
 * +---+---+---+                           +---+---+---+
 * |   |   |   |                           |   |   |   |
 * +---+---+---+                           +---+---+---+
 * | R3|   | R4|                           | R9|   | R8|
 * +---+---+---+                           +---+---+---+
 *
 * ************************************************************* */
/*
var room                                    = [];

room.push({
    "id":"rm3001",
    "node":null
});         // 0
room.push({
    "id":"rm3002",
    "node":null
});         // 1
room.push({
    "id":"rm3003",
    "node":null
});         // 2
room.push({
    "id":"rm3004",
    "node":null
});         // 3
room.push({
    "id":"rm3005",
    "node":null
});         // 4
room.push({
    "id":"rm3006",
    "node":null
});         // 5
room.push({
    "id":"rm3007",
    "node":null
});         // 6
room.push({
    "id":"rm3008",
    "node":null
});         // 7
room.push({
    "id":"rm3009",
    "node":null
});         // 8

//console.log( room );

var knoten                                  = [];

knoten.push({
    "id":"kn_1",
    "node":null
});
knoten.push({
    "id":"kn_2",
    "node":null
});

//console.log( knoten );

var eingang                                 = [];

eingang.push({
    "id":"entry_1",
    "node":null
});
eingang.push({
    "id":"entry_2",
    "node":null
});

//console.log( eingang );

var aufzug                                  = [];

aufzug.push({
    "id":"a_1",
    "node":null
});

//console.log( aufzug );

var treppe                                  = [];

treppe.push({
    "id":"t1a",
    "node":null
});
treppe.push({
    "id":"t1b",
    "node":null
});

//console.log( treppe );


MULTILINKEDLIST.pushHeadSouth( room[0], 0 );
room[0].node                                = MULTILINKEDLIST.getHead();
//console.log( MULTILINKEDLIST.getHead() );

MULTILINKEDLIST.pushHeadSouth( treppe[0], 1 );
treppe[0].node                              = MULTILINKEDLIST.getHead();
//console.log( MULTILINKEDLIST.getHead() );

MULTILINKEDLIST.pushHeadSouth( knoten[0], 4 );
knoten[0].node                              = MULTILINKEDLIST.getHead();
//console.log( MULTILINKEDLIST.getHead() );

MULTILINKEDLIST.pushHeadSouth( room[1], 2 );
room[1].node                              = MULTILINKEDLIST.getHead();
//console.log( MULTILINKEDLIST.getHead() );

MULTILINKEDLIST.pushHeadSouth( room[2], 3 );
room[2].node                              = MULTILINKEDLIST.getHead();
//console.log( MULTILINKEDLIST.getHead() );

MULTILINKEDLIST.pushHeadSouth( room[3], 0 );
room[3].node                              = MULTILINKEDLIST.getHead();
//console.log( MULTILINKEDLIST.getHead() );


knoten[0].node.addItem( eingang[0], "east", 3 );
eingang[0].node                             = knoten[0].node.east;

eingang[0].node.addItem( eingang[1], "east", 5 );
eingang[1].node                             = eingang[0].node.east;

eingang[1].node.addItem( aufzug[0], "east", 1 );
aufzug[0].node                              = eingang[1].node.east;

aufzug[0].node.addItem( knoten[1], "east", 2 );
knoten[1].node                              = aufzug[0].node.east;

knoten[1].node.addItem( room[5], "north", 2 );
room[5].node                                = knoten[1].node.north;


room[5].node.addItem( treppe[1], "north", 2 );
treppe[1].node                              = room[5].node.north;

treppe[1].node.addItem( room[4], "north", 1 );
room[4].node                                = treppe[1].node.north;

knoten[1].node.addItem( room[6], "south", 1 );
room[6].node                                = knoten[1].node.south;

room[6].node.addItem( room[7], "south", 4 );
room[7].node                                = room[6].node.south;

room[7].node.addItem( room[8], "south", 0 );
room[8].node                                = room[7].node.south;
*/




var raum                                    = [];
var treppe                                  = [];
var aufzug                                  = [];
var gebaeude                                = [];
var knoten                                  = [];

// ETAGE 2

//---// GEBAEUDE 1

raum.push({
    "id":"rm_2100",
    "node":null
});         // 0 > 2.100
raum.push({
    "id":"rm_2101",
    "node":null
});         // 1 > 2.101
raum.push({
    "id":"rm_2102",
    "node":null
});         // 2 > 2.102
raum.push({
    "id":"rm_2103",
    "node":null
});         // 3 > 2.103
raum.push({
    "id":"rm_2104",
    "node":null
});         // 4 > 2.104
raum.push({
    "id":"rm_2106",
    "node":null
});         // 5 > 2.106
raum.push({
    "id":"rm_2107",
    "node":null
});         // 6 > 2.107
raum.push({
    "id":"rm_2108",
    "node":null
});         // 7 > 2.108
raum.push({
    "id":"rm_2109",
    "node":null
});         // 8 > 2.109
raum.push({
    "id":"rm_2110",
    "node":null
});         // 9 > 2.110
raum.push({
    "id":"rm_2111",
    "node":null
});         //10 > 2.111
raum.push({
    "id":"rm_2112",
    "node":null
});         //11 > 2.112
raum.push({
    "id":"rm_2113",
    "node":null
});         //12 > 2.113
raum.push({
    "id":"rm_2114",
    "node":null
});         //13 > 2.114

treppe.push({
    "id":"tr_t200b",
    "node":null
});      // 0 > T200b
treppe.push({
    "id":"tr_t201",
    "node":null
});       // 1 > T201
treppe.push({
    "id":"tr_t202",
    "node":null
});       // 2 > T202
treppe.push({
    "id":"tr_t203",
    "node":null
});       // 3 > T203

gebaeude.push({
    "id":"gb_g12",
    "node":null
});      // 0 > GEBAEUDE 1 2. ETAGE

//---// GEBAEUDE 2

raum.push({
    "id":"rm_2200",
    "node":null
});         //14 > 2.200
raum.push({
    "id":"rm_2201",
    "node":null
});         //15 > 2.201
raum.push({
    "id":"rm_2202",
    "node":null
});         //16 > 2.202
raum.push({
    "id":"rm_2203",
    "node":null
});         //17 > 2.203
raum.push({
    "id":"rm_2204",
    "node":null
});         //18 > 2.204
raum.push({
    "id":"rm_2205",
    "node":null
});         //19 > 2.205
raum.push({
    "id":"rm_2206",
    "node":null
});         //20 > 2.206
raum.push({
    "id":"rm_2207",
    "node":null
});         //21 > 2.207
raum.push({
    "id":"rm_2208",
    "node":null
});         //22 > 2.208
raum.push({
    "id":"rm_2209",
    "node":null
});         //23 > 2.209

treppe.push({
    "id":"tr_t200a",
    "node":null
});      // 4 > T200a
treppe.push({
    "id":"tr_t204",
    "node":null
});       // 5 > T204
treppe.push({
    "id":"tr_t205",
    "node":null
});       // 6 > T205

aufzug.push({
    "id":"az_a32",
    "node":null
});         // 0 > Aufzug 32

gebaeude.push({
    "id":"gb_g22",
    "node":null
});      // 1 > GEBAEUDE 2 2. ETAGE

// ETAGE 3

//---// GEBAEUDE 1

raum.push({
    "id":"rm_3100",
    "node":null
});         //24 > 3.100
raum.push({
    "id":"rm_3101",
    "node":null
});         //25 > 3.101
raum.push({
    "id":"rm_3102",
    "node":null
});         //26 > 3.102
raum.push({
    "id":"rm_3103",
    "node":null
});         //27 > 3.103
raum.push({
    "id":"rm_3104",
    "node":null
});         //28 > 3.104
raum.push({
    "id":"rm_3106",
    "node":null
});         //29 > 3.106
raum.push({
    "id":"rm_3107",
    "node":null
});         //30 > 3.107
raum.push({
    "id":"rm_3108",
    "node":null
});         //31 > 3.108
raum.push({
    "id":"rm_3109",
    "node":null
});         //32 > 3.109
raum.push({
    "id":"rm_3110",
    "node":null
});         //33 > 3.110
raum.push({
    "id":"rm_3111",
    "node":null
});         //34 > 3.111
raum.push({
    "id":"rm_3112",
    "node":null
});         //35 > 3.112
raum.push({
    "id":"rm_3113",
    "node":null
});         //36 > 3.113
raum.push({
    "id":"rm_3114",
    "node":null
});         //37 > 3.114

treppe.push({
    "id":"tr_t300b",
    "node":null
});      // 7 > T300b
treppe.push({
    "id":"tr_t301",
    "node":null
});       // 8 > T301
treppe.push({
    "id":"tr_t302",
    "node":null
});       // 9 > T302
treppe.push({
    "id":"tr_t303",
    "node":null
});       //10 > T303

gebaeude.push({
    "id":"gb_g13",
    "node":null
});      // 2 > GEBAEUDE 1 3. ETAGE

//---// GEBAEUDE 2

raum.push({
    "id":"rm_3200",
    "node":null
});         //38 > 3.200
raum.push({
    "id":"rm_3201",
    "node":null
});         //39 > 3.201
raum.push({
    "id":"rm_3202",
    "node":null
});         //40 > 3.202
raum.push({
    "id":"rm_3203",
    "node":null
});         //41 > 3.203
raum.push({
    "id":"rm_3204",
    "node":null
});         //42 > 3.204
raum.push({
    "id":"rm_3205",
    "node":null
});         //43 > 3.205
raum.push({
    "id":"rm_3206",
    "node":null
});         //44 > 3.206
raum.push({
    "id":"rm_3207",
    "node":null
});         //45 > 3.207
raum.push({
    "id":"rm_3208",
    "node":null
});         //46 > 3.208
raum.push({
    "id":"rm_3209",
    "node":null
});         //47 > 3.209

treppe.push({
    "id":"tr_t300a",
    "node":null
});      //11 > T300a
treppe.push({
    "id":"tr_t304",
    "node":null
});       //12 > T304
treppe.push({
    "id":"tr_t305",
    "node":null
});       //13 > T305

aufzug.push({
    "id":"az_a33",
    "node":null
});         // 1 > Aufzug 3

gebaeude.push({
    "id":"gb_g23",
    "node":null
});      // 3 > GEBAEUDE 2 3. ETAGE



// LINKEDLIST AUFBAU ////////////////////////////////

/*

  ETAGE 2

 */
// T201
MULTILINKEDLIST.pushHeadSouth( treppe[ 1 ], 0 );
treppe[ 1 ].node                            = MULTILINKEDLIST.getHead();
// 2.101
treppe[ 1 ].node.addItem( raum[ 1 ], "south", 1 );
raum[ 1 ].node                              = treppe[ 1 ].node.south;
// 2.102
raum[ 1 ].node.addItem( raum[ 2 ], "south", 1 );
raum[ 2 ].node                              = raum[ 1 ].node.south;
// 2.103
raum[ 2 ].node.addItem( raum[ 3 ], "south", 1 );
raum[ 3 ].node                              = raum[ 2 ].node.south;
// 2.100
raum[ 3 ].node.addItem( raum[ 0 ], "south", 1 );
raum[ 0 ].node                              = raum[ 3 ].node.south;
// 2.104
raum[ 0 ].node.addItem( raum[ 4 ], "south", 3 );
raum[ 4 ].node                              = raum[ 0 ].node.south;
// T200b
raum[ 4 ].node.addItem( treppe[ 0 ], "south", 0 );
treppe[ 0 ].node                            = raum[ 4 ].node.south;
// G12
treppe[ 0 ].node.addItem( gebaeude[ 0 ], "south", 5 );
gebaeude[ 0 ].node                          = treppe[ 0 ].node.south;
// 2.106
gebaeude[ 0 ].node.addItem( raum[ 5 ], "south", 4 );
raum[ 5 ].node                              = gebaeude[ 0 ].node.south;
// 2.114
raum[ 5 ].node.addItem( raum[ 14 ], "south", 6 );
raum[ 14 ].node                             = raum[ 5 ].node.south;
// T202
raum[ 14 ].node.addItem( treppe[ 2 ], "south", 1 );
treppe[ 2 ].node                            = raum[ 14 ].node.south;
// 2.107
treppe[ 2 ].node.addItem( raum[ 6 ], "south", 1 );
raum[ 6 ].node                              = treppe[ 2 ].node.south;
// 2.113
raum[ 6 ].node.addItem( raum[ 13 ], "south", 3 );
raum[ 13 ].node                             = raum[ 6 ].node.south;
// 2.108
raum[ 13 ].node.addItem( raum[ 7 ], "south", 4 );
raum[ 7 ].node                              = raum[ 13 ].node.south;
// 2.112
raum[ 7 ].node.addItem( raum[ 11 ], "south", 2 );
raum[ 11 ].node                             = raum[ 7 ].node.south;
// 2.111
raum[ 11 ].node.addItem( raum[ 10 ], "south", 1 );
raum[ 10 ].node                             = raum[ 11 ].node.south;
// 2.110
raum[ 10 ].node.addItem( raum[ 9 ], "south", 1 );
raum[ 9 ].node                              = raum[ 10 ].node.south;
// 2.109
raum[ 9 ].node.addItem( raum[ 8 ], "south", 3 );
raum[ 8 ].node                              = raum[ 9 ].node.south;
// T203
raum[ 8 ].node.addItem( treppe[ 3 ], "south", 3 );
treppe[ 3 ].node                            = raum[ 8 ].node.south;
// G22
gebaeude[ 0 ].node.addItem( gebaeude[ 1 ], "east" , 10 );
gebaeude[ 1 ].node                          = gebaeude[ 0 ].node.east;
// A32
gebaeude[ 1 ].node.addItem( aufzug[ 0 ], "east" , 1 );
aufzug[ 0 ].node                            = gebaeude[ 1 ].node.east;
// T200a
aufzug[ 0 ].node.addItem( treppe[ 4 ], "north", 3 );
treppe[ 4 ].node                            = aufzug[ 0 ].node.north;
// 2.206
treppe[ 4 ].node.addItem( raum[ 20 ], "north", 0);
raum[ 20 ].node                             = treppe[ 4 ].node.north;
// 2.207
raum[ 20 ].node.addItem( raum[ 21 ], "north", 3 );
raum[ 21 ].node                             = raum[ 20 ].node.north;
// T205
raum[ 21 ].node.addItem( treppe[ 6 ], "north", 3 );
treppe[ 6 ].node                            = raum[ 21 ].node.north;

// 2.205
aufzug[ 0 ].node.addItem( raum[ 19 ], "south", 3 );
raum[ 19 ].node                             = aufzug[ 0 ].node.south;
// 2.200
raum[ 19 ].node.addItem( raum[ 14 ], "south", 3 );
raum[ 14 ].node                             = raum[ 19 ].node.south;
// 2.204
raum[ 14 ].node.addItem( raum[ 18 ], "south", 0 );
raum[ 18 ].node                             = raum[ 14 ].node.south;
// 2.201
raum[ 18 ].node.addItem( raum[ 15 ], "south", 1 );
raum[ 15 ].node                             = raum[ 18 ].node.south;
// 2.203
raum[ 15 ].node.addItem( raum[ 17 ], "south", 1 );
raum[ 17 ].node                             = raum[ 15 ].node.south;
// 2.202
raum[ 17 ].node.addItem( raum[ 16 ], "south", 3 );
raum[ 16 ].node                             = raum[ 17 ].node.south;
// T204
raum[ 16 ].node.addItem( treppe[ 5 ], "south", 0 );
treppe[ 5 ].node                            = raum[ 16 ].node.south;


/*

  ETAGE 3

 */
// T301
treppe[ 1 ].node.addItem( treppe[ 8 ], "up", 5 );
console.log( treppe[ 8 ].node );
treppe[ 8 ].node                            = treppe[ 1 ].node.up;
// 3.100
treppe[ 8 ].node.addItem( raum[ 24 ], "south", 3 );
raum[ 24 ].node                             = treppe[ 8 ].node.south;
// 3.101
raum[ 24 ].node.addItem( raum[ 25 ], "south", 0 );
raum[ 25 ].node                             = raum[ 24 ].node.south;
// 3.102
raum[ 25 ].node.addItem( raum[ 26 ], "south", 2 );
raum[ 26 ].node                             = raum[ 25 ].node.south;


// T300b
raum[ 26 ].node.addItem( treppe[ 7 ], "south", 0 );
treppe[ 7 ].node                            = raum[ 26 ].node.south;
treppe[ 7 ].node.connectAt( "down", 5, treppe[ 0 ].node );


// G 13
treppe[ 7 ].node.addItem( gebaeude[ 2 ], "south", 4 );
gebaeude[ 2 ].node                          = treppe[ 7 ].node.south;
// 3.103
gebaeude[ 2 ].node.addItem( raum[ 27 ], "south", 4 );
raum[ 27 ].node                             = gebaeude[ 2 ].node.south;
// 3.104
raum[ 27 ].node.addItem( raum[ 28 ], "south", 3 );
raum[ 28 ].node                             = raum[ 27 ].node.south;


// T302
raum[ 28 ].node.addItem( treppe[ 9 ], "south", 3 );
treppe[ 9 ].node                            = raum[ 28 ].node.south;
treppe[ 9 ].node.connectAt( "down", 5, treppe[ 2 ].node );

// 3.114
treppe[ 9 ].node.addItem( raum[ 37 ], "south", 0 );
raum[ 37 ].node                             = treppe[ 9 ].node.south;
// 3.106
raum[ 37 ].node.addItem( raum[ 29 ], "south", 1 );
raum[ 29 ].node                             = raum[ 37 ].node.south;
// 3.113
raum[ 29 ].node.addItem( raum[ 36 ], "south", 0 );
raum[ 36 ].node                             = raum[ 29 ].node.south;
// 3.112
raum[ 36 ].node.addItem( raum[ 35 ], "south", 3 );
raum[ 35 ].node                             = raum[ 36 ].node.south;
// 3.107
raum[ 35 ].node.addItem( raum[ 30 ], "south", 1 );
raum[ 30 ].node                             = raum[ 35 ].node.south;
// 3.111
raum[ 30 ].node.addItem( raum[ 34 ], "south", 2 );
raum[ 34 ].node                             = raum[ 30 ].node.south;
// 3.108
raum[ 34 ].node.addItem( raum[ 31 ], "south", 1 );
raum[ 31 ].node                             = raum[ 34 ].node.south;
// 3.110
raum[ 31 ].node.addItem( raum[ 33 ], "south", 1 );
raum[ 33 ].node                             = raum[ 31 ].node.south;
// 3.109
raum[ 33 ].node.addItem( raum[ 32 ], "south", 2 );
raum[ 32 ].node                             = raum[ 33 ].node.south;


// T303
raum[ 32 ].node.addItem( treppe[ 10 ], "south", 2 );
treppe[ 10 ].node                           = raum[ 32 ].node.south;
treppe[ 10 ].node.connectAt( "down", 5, treppe[ 3 ].node );


// G23
gebaeude[ 2 ].node.addItem( gebaeude[ 3 ], "east", 10 );
gebaeude[ 3 ].node                          = gebaeude[ 2 ].node.east;


// A33
gebaeude[ 3 ].node.addItem( aufzug[ 1 ], "east", 1 );
aufzug[ 1 ].node                            = gebaeude[ 3 ].node.east;
aufzug[ 1 ].node.connectAt( "down", 4, aufzug[ 0 ].node );


// 3.207
aufzug[ 1 ].node.addItem( raum[ 45 ], "south", 1 );
raum[ 45 ].node                             = aufzug[ 1 ].node.south;
// 3.208
raum[ 45 ].node.addItem( raum[ 46 ], "north", 1 );
raum[ 46 ].node                             = raum[ 45 ].node.north;
// T300a
raum[ 46 ].node.addItem( treppe[ 11 ], "north", 3 );
treppe[ 11 ].node                           = raum[ 46 ].node.north;
// 3.209
treppe[ 11 ].node.addItem( raum[ 47 ], "north", 3 );
raum[ 47 ].node                             = treppe[ 11 ].node.north;
// T305
raum[ 47 ].node.addItem( treppe[ 13 ], "north", 3 );
treppe[ 13 ].node                           = raum[ 47 ].node.north;
// 3.206
raum[ 45 ].node.addItem( raum[ 44 ], "south", 4 );
raum[ 44 ].node                             = raum[ 45 ].node.south;
// 3.200
raum[ 44 ].node.addItem( raum[ 38 ], "south", 0 );
raum[ 38 ].node                             = raum[ 45 ].node.south;
// 3.205
raum[ 38 ].node.addItem( raum[ 43 ], "south", 1 );
raum[ 43 ].node                             = raum[ 38 ].node.south;
// 3.201
raum[ 43 ].node.addItem( raum[ 39 ], "south", 1 );
raum[ 39 ].node                             = raum[ 43 ].node.south;
// 3.202
raum[ 39 ].node.addItem( raum[ 40 ], "south", 2 );
raum[ 40 ].node                             = raum[ 39 ].node.south;
// 3.203
raum[ 40 ].node.addItem( raum[ 41 ], "south", 1 );
raum[ 41 ].node                             = raum[ 40 ].node.south;
// 3.204
raum[ 41 ].node.addItem( raum[ 42 ], "south", 0 );
raum[ 42 ].node                             = raum[ 41 ].node.south;
// T304
raum[ 42 ].node.addItem( treppe[ 12 ], "south", 2 );
treppe[ 12 ].node                           = raum[ 42 ].node.south;
treppe[ 12 ].node.connectAt( "down", 5, treppe[ 5 ].node );


/////////////////////////////////////////////////////

var arrayWanted = [];
arrayWanted.push( raum[42] );


var result                                  = shortestPath.shortestPath( raum[0], arrayWanted );

console.log( result );