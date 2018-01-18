/*
 *
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
