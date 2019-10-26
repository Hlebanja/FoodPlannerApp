var express = require('express');
var router = express.Router();
var Camel = require('../models/camel');

// Return a list of all camels
router.get('/', function(req, res, next) {
    Camel.find(function(err, camels) {
        if (err) { return next(err); }
        res.json({'camels': camels});
    });
});

// Create a new camel
router.post('/', function(req, res, next) {
    var camel = new Camel(req.body);
    camel.save(function(err) {
        if (err) { return next(err); }
        res.status(201).json(camel);
    });
});

// Return the camel with the given ID
router.get('/:id', function(req, res, next) {
    var id = req.params.id;
    Camel.findById(id, function(err, camel) {
        if (err) { return next(err); }
        if (camel === null) {
            return res.status(404).json({'message': 'Camel not found'});
        }
        res.json(camel);
    });
});

// Delete the camel with the given ID
router.delete('/:id', function(req, res, next) {
    var id = req.params.id;
    Camel.findOneAndDelete({_id: id}, function(err, camel) {
        if (err) { return next(err); }
        if (camel === null) {
            return res.status(404).json({'message': 'Camel not found'});
        }
        res.json(camel);
    });
});

module.exports = router;
