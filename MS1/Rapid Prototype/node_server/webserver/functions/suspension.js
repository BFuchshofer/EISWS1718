//TODO: suspendReservation();
//TODO: suspendBooking();
//TODO: suspendSuggestion();??

function suspendSuggestion( key ){
    console.log( 'suspend suggestion in: 30 sec' );
    setTimeout( function(){
        console.log( 'suspend suggestion for: ' + key );
    }, 30*1000);

}

module.exports              = {
    suspendSuggestion: function( key ){
        suspendSuggestion( key );
    }
};
