// NODE MODULES

// VARIABLES

// SHORTESTPATH FUNCTIONS
function sortQueue( array ){
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

function wasVisited( id, array ){
    for( var i = 0; i < array.length; i++ )
    {
        if( id == array[ i ] )
        {
            return true;
        }
    }
    return false;
}

function shortestPath( startNode, arrayWanted ){
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
            if( arrayWanted[ i ] == currentNode.node.data )
            {
                return currentNode.node.data;
            }
        }
        visited.push( currentNode.node.data );

        if( currentNode.node.north != null ){
            if( !wasVisited( currentNode.node.north.data, visited ) ) {
                queue.push(
                    {
                        "node": currentNode.node.north,
                        "cost": ( parseInt( currentNode.cost, 10 ) + parseInt( currentNode.node.n_weight, 10 ))
                    }
                );
            }
        }
        if( currentNode.node.east != null ){
            if( !wasVisited( currentNode.node.east.data, visited ) ) {
                queue.push(
                    {
                        "node": currentNode.node.east,
                        "cost": ( parseInt( currentNode.cost, 10 ) + parseInt( currentNode.node.e_weight, 10 ))
                    }
                );
            }
        }
        if( currentNode.node.south != null ){
            if( !wasVisited( currentNode.node.south.data, visited ) ) {
                queue.push(
                    {
                        "node": currentNode.node.south,
                        "cost": ( parseInt( currentNode.cost, 10 ) + parseInt( currentNode.node.s_weight, 10 ))
                    }
                );
            }
        }
        if( currentNode.node.west != null ){
            if( !wasVisited( currentNode.node.west.data, visited ) ) {
                queue.push(
                    {
                        "node": currentNode.node.west,
                        "cost": ( parseInt( currentNode.cost, 10 ) + parseInt( currentNode.node.w_weight, 10 ))
                    }
                );
            }
        }
        if( currentNode.node.up != null ){
            if( !wasVisited( currentNode.node.up.data, visited ) ) {
                queue.push(
                    {
                        "node": currentNode.node.up,
                        "cost": ( parseInt( currentNode.cost, 10 ) + parseInt( currentNode.node.u_weight, 10 ))
                    }
                )
            }
        }
        if( currentNode.node.down != null ){
            if( !wasVisited( currentNode.node.down.data, visited ) ) {
                queue.push(
                    {
                        "node": currentNode.node.down,
                        "cost": ( parseInt( currentNode.cost, 10 ) + parseInt( currentNode.node.d_weight, 10 ))
                    }
                )
            }
        }
        queue                                   = sortQueue( queue );
    }
}

// FUNCTIONS
function filter( filterArray, emptyRooms){
  if( filterArray.length != 0 ){

    //TODO FOR LOOP GETTING ALL ROOM INFORMATION AND CHECKING FOR THE SET FILTER

  } else {
    return emptyRooms;
  }
}

function suggestion( beacon_id, filter ){
  return new Promise( function( resolve, reject ){
    DATABASE.getHash( 'bn_' + beacon_id )
    .then( function( res ){
      DATABASE.getNode( parseInt( res.arrayPos, 10 ) )
      .then( function( result ){
        console.log("RESULT: " + result );
        return result;
      })
      .then( function( res ){
        DATABASE.getList( 'empty_rooms' )
        .then( function( arrayWanted ){
          resolve( shortestPath( res, arrayWanted ));
        });
      });
    })
    .catch( function(){
      console.error( 'COULDNT FIND RESOURCE' );
    });
  });
}

// ERROR HANDLING

// EOF
module.exports                              = {
  suggestion: function( beacon_id, filter ){
    return new Promise( function( resolve, reject ){
      suggestion( beacon_id, filter )
      .then( function( res ){
        resolve( res );
      })
    })
  }
}
