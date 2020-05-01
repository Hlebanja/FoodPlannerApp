var express = require('express');
var router = express.Router();
var Feedback = require('../models/feedback');

// Create a new feedback
router.post('/', function (req, res, next) {
    var feedback = new Feedback(req.body);
    feedback.save(function (err) {
        if (err) { return next(err); }
        res.status(201).json(feedback);
    });
});

// Return the feedback with the given ID
router.get('/:id', function (req, res, next) {
    var id = req.params.id;
    Feedback.findById(id, function (err, feedback) {
        if (err) { return next(err); }
        if (feedback === null) {
            return res.status(404).json({ 'message': 'Feedback not found' });
        }
        res.json(feedback);
    });
});

// Return a list of all feedbacks
router.get('/', function (req, res, next) {
    Feedback.find(function (err, feedbacks) {
        if (err) { return next(err); }
        res.json({ 'feedbacks': feedbacks });
    });
});

// Delete the feedback with the given ID
router.delete('/:id', function (req, res, next) {
    var id = req.params.id;
    Feedback.findOneAndDelete({ _id: id }, function (err, feedback) {
        if (err) { return next(err); }
        if (feedback === null) {
            return res.status(404).json({ 'message': 'Feedback not found' });
        }
        res.json(feedback);
    });
});

module.exports = router;
