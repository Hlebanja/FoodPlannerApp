var express = require('express');
// To handel HTTP post request 
var bodyParser = require('body-parser');
// Import the mongoose module
var mongoose = require('mongoose');
// For logging request details
var morgan = require('morgan');
var path = require('path');
// to allow requests to skip the Same-Origin policy and access resources from remote hosts.
// Read more https://www.codecademy.com/articles/what-is-cors
var cors = require('cors');
var history = require('connect-history-api-fallback');

var ingredientsController = require('./controllers/ingredients');
var recipesController = require('./controllers/recipes');
var adminsController = require('./controllers/admins');
var usersController = require('./controllers/users');
var feedbacksController = require('./controllers/feedbacks');
var weeklyPlansController = require('./controllers/weeklyPlans');



var mongoURI = process.env.MONGODB_URI || 'mongodb://localhost:27017/foodAdvisor';
// Connect Mongoose to MongoDB
mongoose.connect(mongoURI, { useNewUrlParser: true }, function(err) {
    if (err) {
        console.error(`Failed to connect to MongoDB with URI: ${mongoURI}`);
        console.error(err.stack);
        process.exit(1);
    }
    console.log(`Connected to MongoDB with URI: ${mongoURI}`);
});

// Create Express app
var app = express();
// Parse requests of content-type 'application/json'
// Extract JSON data and make it readable 
app.use(bodyParser.json());
// HTTP request logger
app.use(morgan('dev'));
// Enable cross-origin resource sharing for frontend must be registered before api
app.options('*', cors());
app.use(cors());

// Define routes
app.get('/api', function(req, res) {
    res.json({'Greetings': 'Welcome to your RESTful API!'});
});
app.use('/api/ingredients', ingredientsController);
app.use('/api/recipes', recipesController);
app.use('/api/admins', adminsController);
app.use('/api/feedbacks', feedbacksController);
app.use('/api/users', usersController);
app.use('/api/weeklyPlans', weeklyPlansController);


// Catch all non-error handler for api (i.e., 404 Not Found)
// Handling error when asking for a route that doesn't exist 
app.use('/api/*', function (req, res) {
    res.status(404).json({ 'message': 'Not Found' });
});

// Configuration for serving frontend in production mode
// Support Vuejs HTML 5 history mode
app.use(history());
// Serve static assets
var root = path.normalize(__dirname + '/..');
var client = path.join(root, 'client', 'dist');
app.use(express.static(client));

// Error handler (i.e., when exception is thrown) must be registered last
var env = app.get('env');
// eslint-disable-next-line no-unused-vars
app.use(function(err, req, res, next) {
    console.error(err.stack);
    var err_res = {
        'message': err.message,
        'error': {}
    };
    if (env === 'development') {
        err_res['error'] = err;
    }
    res.status(err.status || 500);
    res.json(err_res);
});

var port = process.env.PORT || 3000;
app.listen(port, function(err) {
    if (err) throw err;
    console.log(`Express server listening on port ${port}, in ${env} mode`);
    console.log(`Backend: http://localhost:${port}/api/`);
    console.log(`Frontend (production): http://localhost:${port}/`);
});

module.exports = app;


// NOTE:
// 1. The order of routes matters
