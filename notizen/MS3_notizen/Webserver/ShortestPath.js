/*
 *
 */


/*

FUNCTION findPaht( startNode, arrayWanted )
var cost = 0;
IF startNode isPartOf arrayWanted > return startNode
var node = startNode
WHILE nodes left-
IF every node has been visited > return -1
IF node.north != null > addToQueue ( with weight )
IF node.east != null > addToQueue ( with weight )
IF node.south != null > addToQueue ( with weight )
IF node.west != null > addToQueue ( with weight )

SORT Queue for lowest cost
node = nextInQueue
-----------------





EIN DURCHLAUF

queue.push(
    {
        "node":startNode,
        "cost":0
    }
);

while( queue notEmpty )
{
    currentNode = queue.pop()
    if( currentNode existsIn arrayEndNodes ) return currentNode;
    if( currentNode.node.north != null )
    {
        queue.push(
            {
                "node":currentNode.node.north,
                "cost":currentNode.cost + currentNode.node.n_weight;
            }
    }
    if( currentNode.node.east != null )
    {
        queue.push(
            {
                "node":currentNode.node.east,
                "cost":currentNode.cost + currentNode.node.e_weight;
            }
    }
    if( currentNode.node.south != null )
    {
        queue.push(
            {
                "node":currentNode.node.south,
                "cost":currentNode.cost + currentNode.node.s_weight;
            }
    }
    if( currentNode.node.west != null )
    {
        queue.push(
            {
                "node":currentNode.node.west,
                "cost":currentNode.cost + currentNode.node.w_weight;
            }
    }

    sortQueue( array )
    {
        var tmpArray[];
        while( array.length > 0 )
        {
            var lowestIndex;
            var comp_Cost = -1;
            for( var i = 0; i<array.length; i++)
            {
                if( array[i].cost <= comp_Cost || comp_Cost == -1 )
                {
                    comp_Cost = array[i].cost;
                    lowestIndex = i;
                }
            }
            tmpArray.push( array.pop( lowestIndex ));
        }

        return tmpArray;
    }

}

function sortQueue( array )
{
    var tmpArray = [];
    var tmp2Array = array;

    while( tmp2Array.length > 0 );
    {
        console.log( counter );
        var lowestIndex;
        var comp_Cost = -1;

        for( var i = 0; i < array.length; i++ )
        {
            console.log( array[ i ])
            if( array[ i ] != null )
            {
                if (array[i].cost < comp_Cost || comp_Cost == -1)
                {
                    comp_Cost = array[i].cost;
                    lowestIndex = i;
                }
            }
        }
        console.log( ">>>>> " + array[ lowestIndex ].cost );
        tmpArray.push( array[ lowestIndex ]);
        array[ lowestIndex ] = null;
        tmp2Array.pop();
    }
    return tmpArray;
}


function sortQueue( array )
{
    var tmpArray = [];

    while( array.length > 0 )
    {
        console.log( array.length );
        var lowestIndex;
        var comp_Cost = -1;

        for( var i = 0; i < array.length; i++ )
        {
            console.log( array[i] );
            if( array[i].cost <= comp_Cost || comp_Cost == -1 )
            {
                comp_Cost = array[i].cost;
                lowestIndex = i;
            }
        }
        console.log( "==>" + lowestIndex );
        tmpArray.push( array.pop( lowestIndex ));
    }
    return tmpArray;
}

*/

function sortQueue( array )
{
    var tmpArray                            = [];
    var otherArray                          = array.length;

    while( otherArray > 0 )
    {

        var lowestIndex;
        var comp_Cost                       = null;

        for( var i = 0; i < array.length; i++ )
        {

            if (array[i] != -1) {

                if (array[i].cost >= comp_Cost || comp_Cost == null) {
                    comp_Cost               = array[i].cost;
                    lowestIndex             = i;
                }

            }

        }
        tmpArray.push( array[ lowestIndex ] );
        array[ lowestIndex ]                = -1;
        otherArray--;
    }
    array                                   = tmpArray;
    return array;
}

function wasVisited( id, array )
{
    for( var i = 0; i < array.length; i++ )
    {
        if( id == array[ i ] )
        {
            return true;
        }
    }
    return false;
}


function shortestPath( startNode, arrayWanted )
{
    var result                              = null;
    var visited                             = [];
    var queue                               = [];
    queue.push(
        {
            "node":startNode,
            "cost":0
        }
    );

    while( queue.length != 0 )
    {

        var currentNode                     = queue.pop();
        for( var i = 0; i < arrayWanted.length; i++ )
        {
            if( arrayWanted[ i ].id == currentNode.node.id )
            {
                return currentNode;
            }
        }
        visited.push( currentNode.node.id );

        if( currentNode.node.node.north != null ){
            if( !wasVisited( currentNode.node.node.north.data.id, visited ) ) {
                queue.push(
                    {
                        "node": currentNode.node.node.north.data,
                        "cost": ( parseInt( currentNode.cost, 10 ) + parseInt( currentNode.node.node.n_weight, 10 ))
                    }
                );
            }
        }
        if( currentNode.node.node.east != null ){
            if( !wasVisited( currentNode.node.node.east.data.id, visited ) ) {
                queue.push(
                    {
                        "node": currentNode.node.node.east.data,
                        "cost": ( parseInt( currentNode.cost, 10 ) + parseInt( currentNode.node.node.e_weight, 10 ))
                    }
                );
            }
        }
        if( currentNode.node.node.south != null ){
            if( !wasVisited( currentNode.node.node.south.data.id, visited ) ) {
                queue.push(
                    {
                        "node": currentNode.node.node.south.data,
                        "cost": ( parseInt( currentNode.cost, 10 ) + parseInt( currentNode.node.node.s_weight, 10 ))
                    }
                );
            }
        }
        if( currentNode.node.node.west != null ){
            if( !wasVisited( currentNode.node.node.west.data.id, visited ) ) {
                queue.push(
                    {
                        "node": currentNode.node.node.west.data,
                        "cost": ( parseInt( currentNode.cost, 10 ) + parseInt( currentNode.node.node.w_weight, 10 ))
                    }
                );
            }
        }
        if( currentNode.node.node.up != null ){
            if( !wasVisited( currentNode.node.node.up.data.id, visited ) ) {
                queue.push(
                    {
                        "node": currentNode.node.node.up.data,
                        "cost": ( parseInt( currentNode.cost, 10 ) + parseInt( currentNode.node.node.u_weight, 10 ))
                    }
                )
            }
        }
        if( currentNode.node.node.down != null ){
            if( !wasVisited( currentNode.node.node.down.data.id, visited ) ) {
                queue.push(
                    {
                        "node": currentNode.node.node.down.data,
                        "cost": ( parseInt( currentNode.cost, 10 ) + parseInt( currentNode.node.node.d_weight, 10 ))
                    }
                )
            }
        }
        queue                                   = sortQueue( queue );
    }
}

module.exports                              =
    {
        sortQueue: function( array )
        {
            return sortQueue( array );
        },
        shortestPath: function( startNode, arrayWanted )
        {
            return shortestPath( startNode, arrayWanted );
        }
    };