/* ****************************************************************************************************************** *
 *
 *
 *
 * ****************************************************************************************************************** */


var MultiLinkedStack                            = ( function()
{

    function Item( data )
    {
        this.data                               = data;

        this.north                              = null;
        this.n_weight                           = 0;
        this.east                               = null;
        this.e_weight                           = 0;
        this.south                              = null;
        this.s_weight                           = 0;
        this.west                               = null;
        this.w_weight                           = 0;

        this.up                                 = null;
        this.u_weight                           = 0;
        this.down                               = null;
        this.d_weight                           = 0;
    }

    Item.prototype                              =
        {
            getNode:        function( data )                                                                            // Returns a Node by it's parameters
            {
                //TODO: Return a Node by it's saved Data like a specific name
            },

            getAt:          function( index )                                                                           // Returns a Node by it's index (Head > Tail)
            {
                //TODO: Return a Node by it's index (Head > Tail)
            },

            getAtReversed:  function( index )                                                                           // Returns a Node by it's index (Tail > Head)
            {
                //TODO: Return a Node by it's index (Tail > Head)
                //NOT SURE IF NEEDED
            },

            addItem:        function( data, direction, weight )
            {
                var tmpNode                     = new Item( data );

                switch( direction )
                {
                    case "north":
                        this.north              = tmpNode;
                        this.n_weight           = weight;
                        tmpNode.south           = this;
                        tmpNode.s_weight        = weight;
                        break;
                    case "east":
                        this.east               = tmpNode;
                        this.e_weight           = weight;
                        tmpNode.west            = this;
                        tmpNode.w_weight        = weight;
                        break;
                    case "south":
                        this.south              = tmpNode;
                        this.s_weight           = weight;
                        tmpNode.north           = this;
                        tmpNode.n_weight        = weight;
                        break;
                    case "west":
                        this.west               = tmpNode;
                        this.w_weight           = weight;
                        tmpNode.east            = this;
                        tmpNode.e_weight        = weight;
                        break;
                    case "up":
                        this.up                 = tmpNode;
                        this.u_weight           = weight;
                        tmpNode.down            = this;
                        tmpNode.d_weight        = weight;
                        break;
                    case "down":
                        this.down               = tmpNode;
                        this.d_weight           = weight;
                        tmpNode.up              = this;
                        tmpNode.u_weight        = weight;
                        break;


                }
            },


            connectAt:      function( direction, weight, node )
            {
                switch( direction )
                {
                    case "north":
                        this.north          = node;
                        this.n_weight       = weight;
                        node.south          = this;
                        node.s_weight       = weight;
                        break;
                    case "east":
                        this.east           = node;
                        this.e_weight       = weight;
                        node.west           = this;
                        break;
                    case "south":
                        this.south          = node;
                        this.s_weight       = weight;
                        node.north          = this;
                        node.n_weight       = weight;
                        break;
                    case "west":
                        this.west           = node;
                        this.w_weight       = weight;
                        node.east           = this;
                        node.e_weight       = weight;
                        break;
                    case "up":
                        this.up             = node;
                        this.u_weight       = weight;
                        node.down           = this;
                        node.d_weight       = weight;
                        break;
                    case "down":
                        this.down           = node;
                        this.d_weight       = weight;
                        node.up             = this;
                        node.u_weight       = weight;
                }
            }

            //TODO: Find other Entrypoints for the MultiLinkedList
        };

    function MultiLinkedStack()                                                                                         // Constructor
    {
        // All List Values set to 0 (Zero) or null
        //TODO: listCounter can maybe not be used in this List

        //this.listCounter                    = 0;                                                                      // Counter for available List-items

        this.ENTRYPOINT                     = null;                                                                     // Defines the entrypoint ( entrypoint == head ) for the List
    }

    MultiLinkedStack.prototype              =
        {
            //TODO: pushHead* Methods can be combined ?
            pushHeadNorth:  function( data, weight )                                                                    // Push a Node North of Head
            {
                //TODO: Push a Node North of Head


                var oldENTRY                = this.ENTRYPOINT;  // save the old HEAD (or ENTRYPOINT)
                this.ENTRYPOINT             = new Item( data );

                // ONLY IF LIST COUNTER IS AVAILABLE //
                /*
                if( listCounter == 0 )
                {
                    this.ENTRYPOINT.south   = oldENTRY;
                }
                else
                {
                    this.ENTRYPOINT.south   = oldENTRY;
                    oldENTRY.north          = this.ENTRYPOINT;
                }
                this.listCounter            = listCounter + 1;
                 */
                ///////////////////////////////////////

                this.ENTRYPOINT.south       = oldENTRY;
                if( oldENTRY != null )
                {
                    this.ENTRYPOINT.s_weight= weight;
                    oldENTRY.north          = this.ENTRYPOINT;
                    oldENTRY.n_weight       = weight;
                }
            },

            pushHeadEast:   function( data, weight )                                                                    // Push a Node East of Head
            {
                //TODO: Push a Node East of Head

                var oldENTRY                = this.ENTRYPOINT;
                this.ENTRYPOINT             = new Item( data );

                this.ENTRYPOINT.west        = oldENTRY;
                if( oldENTRY != null )
                {
                    this.ENTRYPOINT.w_weight= weight;
                    oldENTRY.east           = this.ENTRYPOINT;
                    oldENTRY.e_weight       = weight;
                }

            },

            pushHeadSouth:  function( data, weight )                                                                    // Push a Node South of Head
            {
                //TODO: Push a Node South of Head

                var oldENTRY                = this.ENTRYPOINT;
                this.ENTRYPOINT             = new Item( data );

                this.ENTRYPOINT.north       = oldENTRY;
                if( oldENTRY != null )
                {
                    this.ENTRYPOINT.n_weight= weight;
                    oldENTRY.south          = this.ENTRYPOINT;
                    oldENTRY.s_weight       = weight;
                }

            },

            pushHeadWest:   function( data, weight )                                                                    // Push a Node West of Head
            {
                //TODO: Push a Node West of Head

                var oldENTRY                = this.ENTRYPOINT;
                this.ENTRYPOINT             = new Item( data );

                this.ENTRYPOINT.east        = oldENTRY;
                if( oldENTRY != null )
                {
                    this.ENTRYPOINT.e_weight= weight;
                    oldENTRY.west           = this.ENTRYPOINT;
                    oldENTRY.w_weight       = weight;
                }

            },

            getHead:        function()
            {
                return this.ENTRYPOINT;
            },

            pushHeadUp:     function( data, weight )                                                                    // Push a Node Up of Head
            {
                //TODO: Push a Node Up of Head
            },

            pushHeadDown:   function( data, weight )                                                                    // Push a Node Down of Head
            {
                //TODO: Push a Node Down of Head
            },

            pushAt:         function( index, direction, nextHead, data, weight )                                        // Pushes a Node to DIRECTION of Node at INDEX
            {
                //TODO: Push a Node to DIRECTION of Node At INDEX
            },

            createItem:     function( data, callback )
            {
              var tmp     = new Item( data );
              callback( tmp );
            }
        };
    return MultiLinkedStack;
})();




module.exports                              = MultiLinkedStack;
