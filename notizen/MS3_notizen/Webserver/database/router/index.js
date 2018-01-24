module.exports                              = function( server )
{
  server.use( '/', require( './routes/post' ));

  console.log( '[LOAD] Loaded index.js' );
}
