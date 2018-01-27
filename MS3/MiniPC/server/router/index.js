module.exports = function(app) {
    app.use('/', require('./routes/get'));
    app.use('/', require('./routes/post'));
    console.log('[LOAD] index.js');
};
