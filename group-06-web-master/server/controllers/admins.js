var express = require('express');
var router = express.Router();
var Admin = require('../models/admin');


// Create a new admin
router.post('/', function (req, res, next) {
    var admin = new Admin(req.body);
    admin.save(function (err) {
        if (err) { return next(err); }
        res.status(201).json(admin);
    });
});

// Return the admin with the given ID
router.get('/:id', function (req, res, next) {
    var id = req.params.id;
    Admin.findById(id, function (err, admin) {
        if (err) { return next(err); }
        if (admin === null) {
            return res.status(404).json({ 'message': 'Admin not found' });
        }
        res.json(admin);
    });
});

// Return a list of all admins
router.get('/', function (req, res, next) {
    Admin.find(function (err, admins) {
        if (err) { return next(err); }
        res.json({ 'admins': admins });
    });
});

// Delete the admin with the given ID
router.delete('/:id', function (req, res, next) {
    var id = req.params.id;
    Admin.findOneAndDelete({ _id: id }, function (err, admin) {
        if (err) { return next(err); }
        if (admin === null) {
            return res.status(404).json({ 'message': 'Admin not found' });
        }
        res.json(admin);
    });
});

module.exports = router;
