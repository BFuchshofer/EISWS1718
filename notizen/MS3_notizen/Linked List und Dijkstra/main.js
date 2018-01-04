

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





var raum                                    = [];
var treppe                                  = [];
var aufzug                                  = [];
var gebaeude                                = [];
var knoten                                  = [];


/////////////////////////////////////////////////////

var arrayWanted = [];
arrayWanted.push( room[8] );
arrayWanted.push( room[4] );
arrayWanted.push( room[3] );


var result                                  = shortestPath.shortestPath( room[0], arrayWanted );

console.log( result );