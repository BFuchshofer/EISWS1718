module.exports              = function( app ){
    app.use( express.static( path.join( __dirname, 'public' )));
    
    app.use( '/', require( './routes/get' ));
    console.log( '[LOAD] index.js' );
};
