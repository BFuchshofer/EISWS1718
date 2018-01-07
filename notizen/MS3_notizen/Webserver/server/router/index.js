module.exports              = function( app ){

    app.use( '/', require( './routes/get' ));
    console.log( '[LOAD] index.js' );
};
