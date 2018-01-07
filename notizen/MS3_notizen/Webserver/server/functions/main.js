
var fs                                      = require( 'fs' );
global.bodyParser                           = require( 'body-parser' );
global.jsonParser                           = bodyParser.json();

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


var jsonList                                = {
    "raum":[],
    "treppe":[],
    "aufzug":[],
    "gebaeude":[],
    "knoten":[]
};


var raum                                    = [];
var treppe                                  = [];
var aufzug                                  = [];
var gebaeude                                = [];
var knoten                                  = [];


// ETAGE 2
//---// GEBAEUDE 1
knoten.push({
    "id":"rm_2100",
    "node":null,
    "arrayPos":0
});         // 0 > 2.100
knoten.push({
    "id":"rm_2101",
    "node":null,
    "arrayPos":1
});         // 1 > 2.101
knoten.push({
    "id":"rm_2102",
    "node":null,
    "arrayPos":2
});         // 2 > 2.102
knoten.push({
    "id":"rm_2103",
    "node":null,
    "arrayPos":3
});         // 3 > 2.103
knoten.push({
    "id":"rm_2104",
    "node":null,
    "arrayPos":4
});         // 4 > 2.104
knoten.push({
    "id":"rm_2106",
    "node":null,
    "arrayPos":5
});         // 5 > 2.106
knoten.push({
    "id":"rm_2107",
    "node":null,
    "arrayPos":6
});         // 6 > 2.107
knoten.push({
    "id":"rm_2108",
    "node":null,
    "arrayPos":7
});         // 7 > 2.108
knoten.push({
    "id":"rm_2109",
    "node":null,
    "arrayPos":8
});         // 8 > 2.109
knoten.push({
    "id":"rm_2110",
    "node":null,
    "arrayPos":9
});         // 9 > 2.110
knoten.push({
    "id":"rm_2111",
    "node":null,
    "arrayPos":10
});         //10 > 2.111
knoten.push({
    "id":"rm_2112",
    "node":null,
    "arrayPos":11
});         //11 > 2.112
knoten.push({
    "id":"rm_2113",
    "node":null,
    "arrayPos":12
});         //12 > 2.113
knoten.push({
    "id":"rm_2114",
    "node":null,
    "arrayPos":13
});         //13 > 2.114
//---// GEBAEUDE 2
knoten.push({
    "id":"rm_2200",
    "node":null,
    "arrayPos":14
});         //14 > 2.200
knoten.push({
    "id":"rm_2201",
    "node":null,
    "arrayPos":15
});         //15 > 2.201
knoten.push({
    "id":"rm_2202",
    "node":null,
    "arrayPos":16
});         //16 > 2.202
knoten.push({
    "id":"rm_2203",
    "node":null,
    "arrayPos":17
});         //17 > 2.203
knoten.push({
    "id":"rm_2204",
    "node":null,
    "arrayPos":18
});         //18 > 2.204
knoten.push({
    "id":"rm_2205",
    "node":null,
    "arrayPos":19
});         //19 > 2.205
knoten.push({
    "id":"rm_2206",
    "node":null,
    "arrayPos":20
});         //20 > 2.206
knoten.push({
    "id":"rm_2207",
    "node":null,
    "arrayPos":21
});         //21 > 2.207
knoten.push({
    "id":"rm_2208",
    "node":null,
    "arrayPos":22
});         //22 > 2.208
knoten.push({
    "id":"rm_2209",
    "node":null,
    "arrayPos":23
});         //23 > 2.209
// ETAGE 3
//---// GEBAEUDE 1
knoten.push({
    "id":"rm_3100",
    "node":null,
    "arrayPos":24
});         //24 > 3.100
knoten.push({
    "id":"rm_3101",
    "node":null,
    "arrayPos":25
});         //25 > 3.101
knoten.push({
    "id":"rm_3102",
    "node":null,
    "arrayPos":26
});         //26 > 3.102
knoten.push({
    "id":"rm_3103",
    "node":null,
    "arrayPos":27
});         //27 > 3.103
knoten.push({
    "id":"rm_3104",
    "node":null,
    "arrayPos":28
});         //28 > 3.104
knoten.push({
    "id":"rm_3106",
    "node":null,
    "arrayPos":29
});         //29 > 3.106
knoten.push({
    "id":"rm_3107",
    "node":null,
    "arrayPos":30
});         //30 > 3.107
knoten.push({
    "id":"rm_3108",
    "node":null,
    "arrayPos":31
});         //31 > 3.108
knoten.push({
    "id":"rm_3109",
    "node":null,
    "arrayPos":32
});         //32 > 3.109
knoten.push({
    "id":"rm_3110",
    "node":null,
    "arrayPos":33
});         //33 > 3.110
knoten.push({
    "id":"rm_3111",
    "node":null,
    "arrayPos":34
});         //34 > 3.111
knoten.push({
    "id":"rm_3112",
    "node":null,
    "arrayPos":35
});         //35 > 3.112
knoten.push({
    "id":"rm_3113",
    "node":null,
    "arrayPos":36
});         //36 > 3.113
knoten.push({
    "id":"rm_3114",
    "node":null,
    "arrayPos":37
});         //37 > 3.114
//---// GEBAEUDE 2
knoten.push({
    "id":"rm_3200",
    "node":null,
    "arrayPos":38
});         //38 > 3.200
knoten.push({
    "id":"rm_3201",
    "node":null,
    "arrayPos":39
});         //39 > 3.201
knoten.push({
    "id":"rm_3202",
    "node":null,
    "arrayPos":40
});         //40 > 3.202
knoten.push({
    "id":"rm_3203",
    "node":null,
    "arrayPos":41
});         //41 > 3.203
knoten.push({
    "id":"rm_3204",
    "node":null,
    "arrayPos":42
});         //42 > 3.204
knoten.push({
    "id":"rm_3205",
    "node":null,
    "arrayPos":43
});         //43 > 3.205
knoten.push({
    "id":"rm_3206",
    "node":null,
    "arrayPos":44
});         //44 > 3.206
knoten.push({
    "id":"rm_3207",
    "node":null,
    "arrayPos":45
});         //45 > 3.207
knoten.push({
    "id":"rm_3208",
    "node":null,
    "arrayPos":46
});         //46 > 3.208
knoten.push({
    "id":"rm_3209",
    "node":null,
    "arrayPos":47
});         //47 > 3.209

knoten.push({
    "id":"tr_t200b",
    "node":null,
    "arrayPos":48
});        //48 > T200b
knoten.push({
    "id":"tr_t201",
    "node":null,
    "arrayPos":49
});         //49 > T201
knoten.push({
    "id":"tr_t202",
    "node":null,
    "arrayPos":50
});         //50 > T202
knoten.push({
    "id":"tr_t203",
    "node":null,
    "arrayPos":51
});         //51 > T203
knoten.push({
    "id":"tr_t200a",
    "node":null,
    "arrayPos":52
});        //52 > T200a
knoten.push({
    "id":"tr_t204",
    "node":null,
    "arrayPos":53
});         //53 > T204
knoten.push({
    "id":"tr_t205",
    "node":null,
    "arrayPos":54
});         //54 > T205
knoten.push({
    "id":"tr_t300b",
    "node":null,
    "arrayPos":55
});        //55 > T300b
knoten.push({
    "id":"tr_t301",
    "node":null,
    "arrayPos":56
});         //56 > T301
knoten.push({
    "id":"tr_t302",
    "node":null,
    "arrayPos":57
});         //57 > T302
knoten.push({
    "id":"tr_t303",
    "node":null,
    "arrayPos":58
});         //58 > T303
knoten.push({
    "id":"tr_t300a",
    "node":null,
    "arrayPos":59
});        //59 > T300a
knoten.push({
    "id":"tr_t304",
    "node":null,
    "arrayPos":60
});         //60 > T304
knoten.push({
    "id":"tr_t305",
    "node":null,
    "arrayPos":61
});         //61 > T305

knoten.push({
    "id":"gb_g12",
    "node":null,
    "arrayPos":62
});          //62 > GEBAEUDE 1 2. ETAGE
knoten.push({
    "id":"gb_g22",
    "node":null,
    "arrayPos":63
});          //63 > GEBAEUDE 2 2. ETAGE
knoten.push({
    "id":"gb_g13",
    "node":null,
    "arrayPos":64
});          //64 > GEBAEUDE 1 3. ETAGE
knoten.push({
    "id":"gb_g23",
    "node":null,
    "arrayPos":65
});          //65 > GEBAEUDE 2 3. ETAGE

knoten.push({
    "id":"az_a32",
    "node":null,
    "arrayPos":66
});          //66 > Aufzug 32
knoten.push({
    "id":"az_a33",
    "node":null,
    "arrayPos":67
});          //67 > Aufzug 33



fs.writeFile( './raum.json', JSON.stringify( raum , null, 4 ));

// LINKEDLIST AUFBAU ////////////////////////////////

/*

  ETAGE 2

 */

// T201
MULTILINKEDLIST.pushHeadSouth( knoten[ 49 ], 0 );
knoten[ 49 ].node                            = MULTILINKEDLIST.getHead();
// 2.101
knoten[ 49 ].node.addItem( knoten[ 1 ], "south", 1 );
knoten[ 1 ].node                              = knoten[ 49 ].node.south;
// 2.102
knoten[ 1 ].node.addItem( knoten[ 2 ], "south", 1 );
knoten[ 2 ].node                              = knoten[ 1 ].node.south;
// 2.103
knoten[ 2 ].node.addItem( knoten[ 3 ], "south", 1 );
knoten[ 3 ].node                              = knoten[ 2 ].node.south;
// 2.100
knoten[ 3 ].node.addItem( knoten[ 0 ], "south", 1 );
knoten[ 0 ].node                              = knoten[ 3 ].node.south;
// 2.104
knoten[ 0 ].node.addItem( knoten[ 4 ], "south", 3 );
knoten[ 4 ].node                              = knoten[ 0 ].node.south;
// T200b
knoten[ 4 ].node.addItem( knoten[ 48 ], "south", 0 );
knoten[ 48 ].node                            = knoten[ 4 ].node.south;
// G12
knoten[ 48 ].node.addItem( knoten[ 62 ], "south", 5 );
knoten[ 62 ].node                          = knoten[ 48 ].node.south;
// 2.106
knoten[ 62 ].node.addItem( knoten[ 5 ], "south", 4 );
knoten[ 5 ].node                              = knoten[ 62 ].node.south;
// 2.114
knoten[ 5 ].node.addItem( knoten[ 14 ], "south", 6 );
knoten[ 14 ].node                             = knoten[ 5 ].node.south;
// T202
knoten[ 14 ].node.addItem( knoten[ 50 ], "south", 1 );
knoten[ 50 ].node                            = knoten[ 14 ].node.south;
// 2.107
knoten[ 50 ].node.addItem( knoten[ 6 ], "south", 1 );
knoten[ 6 ].node                              = knoten[ 50 ].node.south;
// 2.113
knoten[ 6 ].node.addItem( knoten[ 13 ], "south", 3 );
knoten[ 13 ].node                             = knoten[ 6 ].node.south;
// 2.108
knoten[ 13 ].node.addItem( knoten[ 7 ], "south", 4 );
knoten[ 7 ].node                              = knoten[ 13 ].node.south;
// 2.112
knoten[ 7 ].node.addItem( knoten[ 11 ], "south", 2 );
knoten[ 11 ].node                             = knoten[ 7 ].node.south;
// 2.111
knoten[ 11 ].node.addItem( knoten[ 10 ], "south", 1 );
knoten[ 10 ].node                             = knoten[ 11 ].node.south;
// 2.110
knoten[ 10 ].node.addItem( knoten[ 9 ], "south", 1 );
knoten[ 9 ].node                              = knoten[ 10 ].node.south;
// 2.109
knoten[ 9 ].node.addItem( knoten[ 8 ], "south", 3 );
knoten[ 8 ].node                              = knoten[ 9 ].node.south;
// T203
knoten[ 8 ].node.addItem( knoten[ 51 ], "south", 3 );
knoten[ 51 ].node                            = knoten[ 8 ].node.south;
// G22
knoten[ 62 ].node.addItem( knoten[ 63 ], "east" , 10 );
knoten[ 63 ].node                          = knoten[ 62 ].node.east;
// A32
knoten[ 63 ].node.addItem( knoten[ 66 ], "east" , 1 );
knoten[ 66 ].node                            = knoten[ 63 ].node.east;
// T200a
knoten[ 66 ].node.addItem( knoten[ 52 ], "north", 3 );
knoten[ 52 ].node                            = knoten[ 66 ].node.north;
// 2.206
knoten[ 52 ].node.addItem( knoten[ 20 ], "north", 0);
knoten[ 20 ].node                             = knoten[ 52 ].node.north;
// 2.207
knoten[ 20 ].node.addItem( knoten[ 21 ], "north", 3 );
knoten[ 21 ].node                             = knoten[ 20 ].node.north;
// T205
knoten[ 21 ].node.addItem( knoten[ 54 ], "north", 3 );
knoten[ 54 ].node                            = knoten[ 21 ].node.north;

// 2.205
knoten[ 66 ].node.addItem( knoten[ 19 ], "south", 3 );
knoten[ 19 ].node                             = knoten[ 66 ].node.south;
// 2.200
knoten[ 19 ].node.addItem( knoten[ 14 ], "south", 3 );
knoten[ 14 ].node                             = knoten[ 19 ].node.south;
// 2.204
knoten[ 14 ].node.addItem( knoten[ 18 ], "south", 0 );
knoten[ 18 ].node                             = knoten[ 14 ].node.south;
// 2.201
knoten[ 18 ].node.addItem( knoten[ 15 ], "south", 1 );
knoten[ 15 ].node                             = knoten[ 18 ].node.south;
// 2.203
knoten[ 15 ].node.addItem( knoten[ 17 ], "south", 1 );
knoten[ 17 ].node                             = knoten[ 15 ].node.south;
// 2.202
knoten[ 17 ].node.addItem( knoten[ 16 ], "south", 3 );
knoten[ 16 ].node                             = knoten[ 17 ].node.south;
// T204
knoten[ 16 ].node.addItem( knoten[ 53 ], "south", 0 );
knoten[ 53 ].node                            = knoten[ 16 ].node.south;


/*

  ETAGE 3

 */

// T301
knoten[ 49 ].node.addItem( knoten[ 56 ], "up", 5 );
knoten[ 56 ].node                            = knoten[ 49 ].node.up;
// 3.100
knoten[ 56 ].node.addItem( knoten[ 24 ], "south", 3 );
knoten[ 24 ].node                             = knoten[ 56 ].node.south;
// 3.101
knoten[ 24 ].node.addItem( knoten[ 25 ], "south", 0 );
knoten[ 25 ].node                             = knoten[ 24 ].node.south;
// 3.102
knoten[ 25 ].node.addItem( knoten[ 26 ], "south", 2 );
knoten[ 26 ].node                             = knoten[ 25 ].node.south;


// T300b
knoten[ 26 ].node.addItem( knoten[ 55 ], "south", 0 );
knoten[ 55 ].node                            = knoten[ 26 ].node.south;
knoten[ 55 ].node.connectAt( "down", 5, knoten[ 48 ].node );


// G 13
knoten[ 55 ].node.addItem( knoten[ 64 ], "south", 4 );
knoten[ 64 ].node                          = knoten[ 55 ].node.south;
// 3.103
knoten[ 64 ].node.addItem( knoten[ 27 ], "south", 4 );
knoten[ 27 ].node                             = knoten[ 64 ].node.south;
// 3.104
knoten[ 27 ].node.addItem( knoten[ 28 ], "south", 3 );
knoten[ 28 ].node                             = knoten[ 27 ].node.south;


// T302
knoten[ 28 ].node.addItem( knoten[ 57 ], "south", 3 );
knoten[ 57 ].node                            = knoten[ 28 ].node.south;
knoten[ 57 ].node.connectAt( "down", 5, knoten[ 50 ].node );

// 3.114
knoten[ 57 ].node.addItem( knoten[ 37 ], "south", 0 );
knoten[ 37 ].node                             = knoten[ 57 ].node.south;
// 3.106
knoten[ 37 ].node.addItem( knoten[ 29 ], "south", 1 );
knoten[ 29 ].node                             = knoten[ 37 ].node.south;
// 3.113
knoten[ 29 ].node.addItem( knoten[ 36 ], "south", 0 );
knoten[ 36 ].node                             = knoten[ 29 ].node.south;
// 3.112
knoten[ 36 ].node.addItem( knoten[ 35 ], "south", 3 );
knoten[ 35 ].node                             = knoten[ 36 ].node.south;
// 3.107
knoten[ 35 ].node.addItem( knoten[ 30 ], "south", 1 );
knoten[ 30 ].node                             = knoten[ 35 ].node.south;
// 3.111
knoten[ 30 ].node.addItem( knoten[ 34 ], "south", 2 );
knoten[ 34 ].node                             = knoten[ 30 ].node.south;
// 3.108
knoten[ 34 ].node.addItem( knoten[ 31 ], "south", 1 );
knoten[ 31 ].node                             = knoten[ 34 ].node.south;
// 3.110
knoten[ 31 ].node.addItem( knoten[ 33 ], "south", 1 );
knoten[ 33 ].node                             = knoten[ 31 ].node.south;
// 3.109
knoten[ 33 ].node.addItem( knoten[ 32 ], "south", 2 );
knoten[ 32 ].node                             = knoten[ 33 ].node.south;


// T303
knoten[ 32 ].node.addItem( knoten[ 58 ], "south", 2 );
knoten[ 58 ].node                           = knoten[ 32 ].node.south;
knoten[ 58 ].node.connectAt( "down", 5, knoten[ 51 ].node );


// G23
knoten[ 64 ].node.addItem( knoten[ 65 ], "east", 10 );
knoten[ 65 ].node                          = knoten[ 64 ].node.east;


// A33
knoten[ 65 ].node.addItem( knoten[ 67 ], "east", 1 );
knoten[ 67 ].node                            = knoten[ 65 ].node.east;
knoten[ 67 ].node.connectAt( "down", 4, knoten[ 66 ].node );


// 3.207
knoten[ 67 ].node.addItem( knoten[ 45 ], "south", 1 );
knoten[ 45 ].node                             = knoten[ 67 ].node.south;
// 3.208
knoten[ 45 ].node.addItem( knoten[ 46 ], "north", 1 );
knoten[ 46 ].node                             = knoten[ 45 ].node.north;
// T300a
knoten[ 46 ].node.addItem( knoten[ 59 ], "north", 3 );
knoten[ 59 ].node                           = knoten[ 46 ].node.north;
knoten[ 59 ].node.connectAt( knoten[ 52 ], "down", 5 );
// 3.209
knoten[ 59 ].node.addItem( knoten[ 47 ], "north", 3 );
knoten[ 47 ].node                             = knoten[ 52 ].node.north;
// T305
knoten[ 47 ].node.addItem( knoten[ 61 ], "north", 3 );
knoten[ 61 ].node                           = knoten[ 47 ].node.north;
knoten[ 61 ].node.connectAt( knoten[ 54 ], "down", 5 );
// 3.206
knoten[ 45 ].node.addItem( knoten[ 44 ], "south", 4 );
knoten[ 44 ].node                             = knoten[ 45 ].node.south;
// 3.200
knoten[ 44 ].node.addItem( knoten[ 38 ], "south", 0 );
knoten[ 38 ].node                             = knoten[ 45 ].node.south;
// 3.205
knoten[ 38 ].node.addItem( knoten[ 43 ], "south", 1 );
knoten[ 43 ].node                             = knoten[ 38 ].node.south;
// 3.201
knoten[ 43 ].node.addItem( knoten[ 39 ], "south", 1 );
knoten[ 39 ].node                             = knoten[ 43 ].node.south;
// 3.202
knoten[ 39 ].node.addItem( knoten[ 40 ], "south", 2 );
knoten[ 40 ].node                             = knoten[ 39 ].node.south;
// 3.203
knoten[ 40 ].node.addItem( knoten[ 41 ], "south", 1 );
knoten[ 41 ].node                             = knoten[ 40 ].node.south;
// 3.204
knoten[ 41 ].node.addItem( knoten[ 42 ], "south", 0 );
knoten[ 42 ].node                             = knoten[ 41 ].node.south;
// T304
knoten[ 42 ].node.addItem( knoten[ 60 ], "south", 2 );
knoten[ 60 ].node                           = knoten[ 42 ].node.south;
knoten[ 60 ].node.connectAt( "down", 5, knoten[ 53 ].node );


var jsonRaum    = [];

for( var i = 0; i< knoten.length; i++ )
{
    if( knoten[ i ].node != null )
    {
        knoten[i].arrayPos = i;
        jsonRaum[i] = {
            "data": knoten[i].id,
            /*----*/
            "north": null,
            "n_weight": 0,
            "east": null,
            "e_weight": 0,
            "south": null,
            "s_weight": 0,
            "west": null,
            "w_weight": 0,
            "up": null,
            "u_weight": 0,
            "down": null,
            "d_weight": 0
        };
        if (knoten[i].node.north != null) {
            //console.log( i + " north " + knoten[i].node.north.data.arrayPos);
            jsonRaum[i].north = knoten[i].node.north.data.arrayPos;
            jsonRaum[i].n_weight = knoten[i].node.n_weight;
        }
        if (knoten[i].node.east != null) {
            //console.log( i + " east " + knoten[i].node.east.data.arrayPos);
            jsonRaum[i].east = knoten[i].node.east.data.arrayPos;
            jsonRaum[i].e_weight = knoten[i].node.e_weight;
        }
        if (knoten[i].node.south != null) {
            //console.log( i + " south " + knoten[i].node.south.data.arrayPos);
            jsonRaum[i].south = knoten[i].node.south.data.arrayPos;
            jsonRaum[i].s_weight = knoten[i].node.s_weight;
        }
        if (knoten[i].node.west != null) {
            //console.log( i + " west " + knoten[i].node.west.data.arrayPos);
            jsonRaum[i].west = knoten[i].node.west.data.arrayPos;
            jsonRaum[i].w_weight = knoten[i].node.w_weight;
        }
        if (knoten[i].node.up != null) {
            //console.log( i + " up " + knoten[i].node.up.data.arrayPos);
            jsonRaum[i].up = knoten[i].node.up.data.arrayPos;
            jsonRaum[i].u_weight = knoten[i].node.u_weight;
        }
        if (knoten[i].node.down != null) {
            //console.log( i + " down " + knoten[i].node.down.data.arrayPos);
            jsonRaum[i].down = knoten[i].node.down.data.arrayPos;
            jsonRaum[i].d_weight = knoten[i].node.d_weight;
        }
    }
}
//console.log( jsonRaum );
fs.writeFile( './knoten.json', JSON.stringify( jsonRaum, null, 4 ));
/////////////////////////////////////////////////////
/*
var arrayWanted = [];
arrayWanted.push( knoten[42] );


var result                                  = shortestPath.shortestPath( knoten[0], arrayWanted );

//console.log( result );
*/

var emptyRooms  = [];
var arrayWanted = [];

emptyRooms.push( knoten[ 38 ] );
emptyRooms.push( knoten[ 45 ] );
emptyRooms.push( knoten[ 10 ] );
emptyRooms.push( knoten[ 26 ] );


function getFilter( filter )
{
    //TODO: Filterüberprüfung
    return true;
}
function getRoom( position, filter )
{
    for( var i = 0; i < emptyRooms.length; i++ )
    {
        if( getFilter( filter ) )
        {
            arrayWanted.push( emptyRooms[ i ] );
        } else {
            console.error( "UNMOEGLICH" );
        }
    }
    var raum_position;
    for( var i = 0; i < knoten.length; i++ )
    {
        if (knoten[i].id == position) {
            raum_position = knoten[i];
            break;
        }
    }
    return shortestPath.shortestPath( raum_position, arrayWanted );
}

//TODO: Was ist wenn die Position unbekannt ist?
//TODO: Was ist wenn kein Raum verfügbar ist?
//TODO: BeaconID zu Raum zuordnung treffen.


module.exports                              = {
    getRoom:    function( position, filter )
    {
        return getRoom( position, filter );
    }
}
