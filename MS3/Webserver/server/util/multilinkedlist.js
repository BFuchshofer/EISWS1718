/* ****************************************************************************************************************** *
 *
 * Verkettete Liste mit 6 Richtungen ( Norden, Osten, Süden, Westen, Oben, Unten )
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

        };

    function MultiLinkedStack()                                                                                         // Constructor
    {
        this.ENTRYPOINT                     = null;                                                                     // Defines the entrypoint ( entrypoint == head ) for the List
    }

    MultiLinkedStack.prototype              =
        {
            createItem:     function( data, callback )
            {
              var tmp     = new Item( data );
              callback( tmp );
            }
        };
    return MultiLinkedStack;
})();




module.exports                              = MultiLinkedStack;
