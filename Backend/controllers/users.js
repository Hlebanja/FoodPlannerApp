var express = require('express');
var router = express.Router();
var User = require('../models/user');

// Create a new user
router.post('/', function (req, res, next) {
    var user = new User(req.body);
    user.save(function (err) {
        if (err) { return next(err); }
        res.status(201).json({ 'user': [user] });
    });
});

// Return the user with the given ID
router.get('/:id', function (req, res, next) {
    var id = req.params.id;
    User.findById(id, function (err, user) {
        if (err) { return next(err); }
        if (user === null) {
            return res.status(404).json({ 'message': 'User not found' });
        }
        res.json({ 'user': [user] });
    });
});

// Return a list of all users
router.get('/', function (req, res, next) {
    User.find({}, null, {sort: {name: 1}}, function (err, users) {
        if (err) { return next(err); }
        res.json({ 'users': users });
    });
});

// Update the recipe with the given ID
router.put('/:id', function (req, res, next) {
    var id = req.params.id;
    User.findById(id, function (err, user) {
        if (err) { return next(err); }
        if (user == null) {
            return res.status(404).json({ "message": "User not found" });
        }
        user.name = req.body.name;
        user.password = req.body.password;


        user.save();
        res.json({ 'user': [user] });
    });
});

// Partially update ingredient with the givin id
router.patch('/:id', function (req, res, next) {
    var id = req.params.id;
    User.findById(id, function (err, user) {
        if (err) { return next(err); }
        if (user == null) {
            return res.status(404).json(
                { "message": "user not found" });
        }
        user.name = (req.body.name || user.name);
        user.password = (req.body.password || user.password);

        user.save();
        res.json({ 'user': [user] });
    });
});

// Delete the user with the given ID
router.delete('/:id', function (req, res, next) {
    var id = req.params.id;
    User.findOneAndDelete({ _id: id }, function (err, user) {
        if (err) { return next(err); }
        if (user === null) {
            return res.status(404).json({ 'message': 'User not found' });
        }
        res.json(user);
    });
});

// Deletes all users
router.delete('/', function (req, res, next) {
    User.remove(function (err, user) {
        if (err) { return next(err); }
        if (user === null) {
            return res.status(404).json({ 'message': 'User list is empty' });
        }
        res.json(user);
    });
});

module.exports = router;