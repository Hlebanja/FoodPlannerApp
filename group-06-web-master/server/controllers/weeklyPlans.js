var express = require('express');
var router = express.Router();
var WeeklyPlan = require('../models/weeklyPlan');

// Create a new weeklyPlan
router.post('/', function (req, res, next) {
    var weeklyPlan = new WeeklyPlan(req.body);
    weeklyPlan.save(function (err) {
        if (err) { return next(err); }
        res.status(201).json(weeklyPlan);
    });
});

// Return the weeklyPlan with the given ID
router.get('/:id', function (req, res, next) {
    var id = req.params.id;
    WeeklyPlan.findById(id, function (err, weeklyPlan) {
        if (err) { return next(err); }
        if (weeklyPlan === null) {
            return res.status(404).json({ 'message': 'WeeklyPlan not found' });
        }
        res.json(weeklyPlan);
    });
});

// Return a list of all weeklyPlans
router.get('/', function (req, res, next) {
    WeeklyPlan.find(function (err, weeklyPlans) {
        if (err) { return next(err); }
        res.json({ 'weeklyPlans': weeklyPlans });
    });
});

// Partially update weeklyplan with the givin id
router.patch('/weeklyPlans/:id', function (req, res, next) {
    var id = req.params.id;
    WeeklyPlan.findById(id, function (err, weeklyPlan) {
        if (err) { return next(err); }
        if (weeklyPlan == null) {
            return res.status(404).json(
                { "message": "WeeklyPlan not found" });
        }
        weeklyPlan.days = (req.body.days || weeklyPlan.days);
        weeklyPlan.weeks = (req.body.weeks || weeklyPlan.weeks);

        weeklyPlan.save();
        res.json(weeklyPlan);
    });
});

// Updates the weeklyPlan with the given ID
router.put('/weeklyPlans/:id', function (req, res, next) {
    var id = req.params.id;
    WeeklyPlan.findById(id, function (err, weeklyPlan) {
        if (err) { return next(err); }
        if (weeklyPlan == null) {
            return res.status(404).json({ "message": "weeklyPlan not found" });
        }
        weeklyPlan.days = req.body.days;
        weeklyPlan.weeks = req.body.weeks;


        weeklyPlan.save();
        res.json(weeklyPlan);
    });
});

// Delete the weeklyPlan with the given ID
router.delete('/:id', function (req, res, next) {
    var id = req.params.id;
    WeeklyPlan.findOneAndDelete({ _id: id }, function (err, weeklyPlan) {
        if (err) { return next(err); }
        if (weeklyPlan === null) {
            return res.status(404).json({ 'message': 'WeeklyPlan not found' });
        }
        res.json(weeklyPlan);
    });
});

// Deletes all weeklyPlans
router.delete('/', function (req, res, next) {
    WeeklyPlan.remove(function (err, weeklyPlan) {
        if (err) { return next(err); }
        if (weeklyPlan === null) {
            return res.status(404).json({ 'message': 'There are no weekly plans' });
        }
        res.json(weeklyPlan);
    });
});


module.exports = router;
