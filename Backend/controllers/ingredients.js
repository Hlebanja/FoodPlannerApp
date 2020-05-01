var express = require('express');
var router = express.Router();
var Ingredient = require('../models/ingredient');


// Create a new ingredient
router.post('/', function (req, res, next) {
    var ingredient = new Ingredient(req.body);
    ingredient.save(function (err) {
        if (err) { return next(err); }
        res.status(201).json({'ingredient': [ingredient]});
    });
})

// Return the ingredient with the given ID
router.get('/:id', function (req, res, next) {
    var id = req.params.id;
    Ingredient.findById(id, function (err, ingredient) {
        if (err) { return next(err); }
        if (ingredient === null) {
            return res.status(404).json({ 'message': 'ingredient not found' });
        }
        res.json({'ingredient': [ingredient]});
    });
});

// Return all ingredients with the ingredient :name
router.get('/name/:name', function (req, res, next) {
    var IngredientName = req.params.name;
    Ingredient.find({ name: IngredientName }, function (err, ingredient) {
        if (err) { return next(err); }
        if (ingredient === null) {
            return res.status(404).json({ 'message': 'ingredient not found' });
        }
        res.json({'ingredient': [ingredient]});
    });
});

// Return a list of all ingredients
router.get('/', function (req, res, next) {
    Ingredient.find(function (err, ingredients) {
        if (err) { return next(err); }
        res.json({ 'ingredients': ingredients });
    });
});

// Updates ingredient with the given ID
router.put('/:id', function (req, res, next) {
    var id = req.params.id;
    Ingredient.findById(id, function (err, ingredient) {
        if (err) { return next(err); }
        if (ingredient == null) {
            return res.status(404).json({ "message": "Ingredient not found" });
        }
        ingredient.name = req.body.name;
        ingredient.description = req.body.description;
        ingredient.nutritionalInfo = req.body.nutritionalInfo;
        
        ingredient.save();
        res.json({'ingredient': [ingredient]});
    });
});

// Partially update ingredient with the givin id
router.patch('/:id', function (req, res, next) {
    var id = req.params.id;
    Ingredient.findById(id, function (err, ingredient) {
        if (err) { return next(err); }
        if (ingredient == null) {
            return res.status(404).json(
                { "message": "Ingredient not found" });
        }
        ingredient.name = (req.body.name || ingredient.name);
        ingredient.description = (req.body.description || ingredient.description);
        ingredient.nutritionalInfo = (req.body.nutritionalInfo || ingredient.nutritionalInfo);
        ingredient.save();
        res.json({'ingredient': [ingredient]});
    });
});

// Delete the ingredient with the given ID
router.delete('/:id', function (req, res, next) {
    var id = req.params.id;
    Ingredient.findOneAndDelete({ _id: id }, function (err, ingredient) {
        if (err) { return next(err); }
        if (ingredient === null) {
            return res.status(404).json({ 'message': 'Ingredient not found' });
        }
        res.json(ingredient);
    });
});

// Deletes all ingredients
router.delete('/', function (req, res, next) {
    Ingredient.remove(function (err, ingredient) {
        if (err) { return next(err); }
        if (ingredient === null) {
            return res.status(404).json({ 'message': 'Ingredient list is empty' });
        }
        res.json(ingredient);
    });
});

module.exports = router;
