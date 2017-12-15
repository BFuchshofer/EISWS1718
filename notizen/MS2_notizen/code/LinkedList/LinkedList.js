/*
 * Dieser Test für eine LinkedList ist auf Basis der "Linked List Javascript implementation (node.js)", zu finden unter https://gist.github.com/ggilder/3224560
 * entstanden und für dieses Projekt zu einer MultyLinkedList umgewandelt worden.
 *
 */

var assert                          = require( 'assert' );

var MultiLinkedStack               = ( function(){

    function Item( data ){
        this.data                   = data;
        this.next                   = null;
        this.prev                   = null;

        this.up                     = null;
        this.down                   = null;
    }

    Item.prototype                  = {
        getAt: function( index ){
            var result = this;
            var i = 1;
            while( i < index ){
                result = result.next;
                i++;
            }
            return result;
        },
        getAtReversed: function( index ){
            var result = this;
            var i = 1;
            while( i < index ){
                result = result.prev;
                i++;
            }
            return result;
        }
    };

    function MultiLinkedStack(){
        this.listCounter            = 0;
        this.head                   = null;
        this.tail                   = null;
    }

    MultiLinkedStack.prototype     = {
        pushHead: function( data ){
            var oldHead             = this.head;
            this.head               = new Item( data );
            if( this.listCounter == 0 ){
                this.tail           = this.head;
                this.head.next      = oldHead;
            } else {
                this.head.next      = oldHead;
                oldHead.prev        = this.head;
            }
            this.listCounter++;
        },
        pushTail: function( data ){
            var oldTail             = this.tail;
            this.tail               = new Item( data );
            if( this.listCounter == 0 ){
                this.head           = this.tail;
                this.tail.prev      = oldTail;
            } else {
                this.tail.prev      = oldTail;
                oldTail.next        = this.tail;
            }
            this.listCounter++;
        },
        pushUp: function( node ){
            var upNode              = new Item( node.data );
            upNode.next             = node.next;
            upNode.prev             = node.prev;
            this.head.up            = upNode;
            upNode.down             = this.head;
        },
        pushDown: function( data ){

        },
        pushAt: function( index, direction, data ){
            if( this.head ){
                var position        = this.head.getAt( index );
                var prevNode        = position;
                var nextNode        = position.next;
                var newNode         = new Item( data );
                newNode.prev        = prevNode;
                newNode.next        = nextNode;
                if( nextNode != null ){
                    prevNode.next   = newNode;
                    nextNode.prev   = newNode;
                }
                this.listCounter++;
            }
        },
        popHead: function(){
            if( this.head ){
                var popped          = this.head;
                this.head           = popped.next;
                this.head.next.prev = this.head;
                return popped.data;
            } else {
                return null;
            }
        },
        popTail: function(){
            if( this.tail ){
                var popped          = this.tail;
                this.tail           = popped.prev;
                this.tail.prev.next = this.tail;
                return popped.data;
            } else {
                return null;
            }
        }
    };

    return MultiLinkedStack;
})();

describe( 'MultiLinkedStack', function(){
    var stack;
    beforeEach( function(){
        stack = new MultiLinkedStack();
    });

    describe( '#head & tail', function(){
        it( 'should return null for an empty stack', function(){
            assert.equal( null, stack.head );
            assert.equal( null, stack.tail );
        });
    });

    describe( '#head == tail && listcount == 1', function(){
        it( 'should be true when listCount == 1', function(){
            stack.pushHead( 'Hello' );
            assert.equal(stack.head.data, stack.tail.data );
        });
    });

    describe( '#pushHead', function(){
        it( 'should push an item onto the top of the stack', function(){
            stack.pushHead( 'Hello' );
            assert.equal( 'Hello', stack.head.data );
            stack.pushHead( 'Hi' );
            assert.equal( 'Hi', stack.head.data );
        });
    });

    describe( '#pushTail', function(){
        it( 'should push an item onto the bottom of the stack', function(){
            stack.pushTail( 'Hello' );
            assert.equal( 'Hello', stack.tail.data );
            stack.pushTail( 'Hi' );
            assert.equal( 'Hi', stack.tail.data );
        });
    });

    describe( '#pushAt', function(){
        it( 'should push an item into the stack at a specific position', function(){
            stack.pushTail( 'Hello' );  // 1
            assert.equal( 1, stack.listCounter );
            assert.equal( 'Hello', stack.head.getAt(1).data );

            stack.pushTail( 'Hi' );     // 2
            assert.equal( 2, stack.listCounter );
            assert.equal( 'Hi', stack.head.getAt(2).data );

            stack.pushTail( 'Olá' );    // 3
            assert.equal( 3, stack.listCounter );
            assert.equal( 'Olá', stack.head.getAt(3).data );

            stack.pushAt( 2, 0, 'Bonjour' ); // supposed to be new 3
            assert.equal( 4, stack.listCounter );


            assert.equal( 'Hello', stack.head.getAt(1).data );
            assert.equal( 'Hi', stack.head.getAt(2).data );
            assert.equal( 'Bonjour', stack.head.getAt(3).data );
            assert.equal( 'Olá', stack.head.getAt(4).data );
        });
    });

    describe( '#getAtReversed', function(){
        it( 'should search through a List from Tail to Head', function(){
            stack.pushTail( 'Hello' );
            stack.pushTail( 'Hi' );
            stack.pushTail( 'Bonjour' );
            stack.pushTail( 'Olá' );

            assert.equal( 'Hello', stack.tail.getAtReversed(4).data );
            assert.equal( 'Hi', stack.tail.getAtReversed(3).data );
            assert.equal( 'Bonjour', stack.tail.getAtReversed(2).data );
            assert.equal( 'Olá', stack.tail.getAtReversed(1).data );
        });
    });

    //TODO: test popHead
    //TODO: test popTail
    //TODO: test popAt

    describe( '#pushUp', function(){
        it( 'should push an item from another List onto the current list', function(){
            stack.pushHead( 'Hello' );
            stack.pushHead( 'Hi' );

            stack_two = new MultiLinkedStack();
            stack_two.pushHead( 'Olá' );
            stack_two.pushHead( 'Bonjour' );

            stack.pushUp( stack_two.head );
            assert.equal( 'Bonjour', stack.head.up.data );
            assert.equal( 'Olá', stack.head.up.next.data );
        });
    });
});
