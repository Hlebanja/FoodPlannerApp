var express = require('express');
var router = express.Router();
var Recipe = require('../models/recipe');
var Ingredient = require('../models/ingredient');

router.post('/:id/ingredients', function (req, res, next) {
    var id = req.params.id;
    var ingredient = new Ingredient(req.body);
    // Create an instance of model Ingredient
    Recipe.findById(id, function (err, recipe) {
        if (err) { return next(err); }
        if (recipe === null) {
            return res.status(404).json({ 'message': 'recipe not found' });
        }
        ingredient.save(function (err, newIngredient) {
            if (err) { return next(err); }
            recipe.ingredients.push(newIngredient._id);
            recipe.save();

            console.log(ingredient + " has been saved to DB");
            // res.status(201).json(recipe);
            res.status(201).json(recipe.ingredients[recipe.ingredients.length - 1]);

        });
    });
});

// Create a new recipe
router.post('/', function (req, res, next) {

    // Create an instance of model Ingredient
    var recipe = new Recipe(req.body);
    recipe.save(function (err) {
        if (err) { return next(err); }
        res.status(201).json(recipe);
    });
});

router.get('/:id/ingredients/:ingredientId', function (req, res, next) {
    var id = req.params.id;
    var ingredientId = req.params.ingredientId;
    var ingredient = { ingredient: "Not found" };
    Recipe.findById(id, function (err, recipe) {
        if (err) { return next(err); }
        if (recipe === null) {
            return res.status(404).json({ 'message': 'recipe not found' });
        }
        for (var i = 0; i < recipe.ingredients.length; i++) {
            if (recipe.ingredients[i]._id == ingredientId) {
                ingredient = recipe.ingredients[i];
            }
        }
    }).populate('ingredients').exec(function (err, recipe) {
        if (err) { return next(err); }
        res.json(ingredient);
    });
});

router.get('/:id/ingredients', function (req, res, next) {
    var id = req.params.id;
    Recipe.findById(id, function (err, recipe) {
        if (err) { return next(err); }
        if (recipe === null) {
            return res.status(404).json({ 'message': 'recipe not found' });
        }
        // recipe.ingredients.populate('name');
    }).populate('ingredients').exec(function (err, recipe) {
        if (err) { return next(err); }
        res.json(recipe.ingredients);
    });
});

// Return the recipe with the given ID
router.get('/:id', function (req, res, next) {
    var id = req.params.id;
    Recipe.findById(id, function (err, recipe) {
        if (err) { return next(err); }
        if (recipe === null) {
            return res.status(404).json({ 'message': 'recipe not found' });
        }
        res.json(recipe);
    });
});

// Return a list of all recipes
router.get('/', function (req, res, next) {
    Recipe.find(function (err, recipes) {
        if (err) { return next(err); }
    }).populate('ingredients').exec(function (err, recipes) {
        if (err) { return next(err); }
        res.json({ 'recipes': recipes });
    });
});

// Update the recipe with the given ID
router.put('/:id', function (req, res, next) {
    var id = req.params.id;
    Recipe.findById(id, function (err, recipe) {
        if (err) { return next(err); }
        if (recipe == null) {
            return res.status(404).json({ "message": "Recipe not found" });
        }
        recipe.name = req.body.name;
        recipe.description = req.body.description;
        recipe.nutritionalInfo = req.body.nutritionalInfo;
        recipe.ingredients = req.body.ingredients;
        recipe.save();

        Ingredient.create(recipe.ingredients, function (err, ingredient) {
            if (err) { return next(err); }
        });
    }).populate('ingredients').exec(function (err, recipe) {
        if (err) { return next(err); }
        res.json(recipe);
    });
});

// Partially update ingredient with the givin id
router.patch('/:id', function (req, res, next) {
    var id = req.params.id;
    var object = req.body;
    Recipe.findById(id, function (err, recipe) {
        if (err) { return next(err); }
        if (recipe == null) {
            return res.status(404).json(
                { "message": "recipe not found" });
        }

        recipe.name = (req.body.name || recipe.name);
        recipe.description = (req.body.description || recipe.description);
        recipe.nutritionalInfo = (req.body.nutritionalInfo || recipe.nutritionalInfo);
        recipe.ingredients = (recipe.ingredients);
        
        // var ingArray = req.body.ingredients;

        // if (ingArray == null || ingArray.length == 0) {
        // } else {
        //     for (var i = 0; i < ingArray.length; i++) {
        //         Ingredient.findById(ingArray[i]._id, function (err, ingFound) {
        //             if (err) { return next(err); }
        //             if (ingFound == null) {
        //                 var newIngredient = new Ingredient();
        //                 newIngredient.name = ingArray[i].name;
        //                 newIngredient.description = ingArray[i].description;
        //                 newIngredient.nutritionalInfo = ingArray[i].nutritionalInfo ;

        //                 recipe.ingredients.push(newIngredient);
        //                 newIngredient.save();
        //                 recipe.save();
        //             }
        //         });
        //     }
        // }
        
        // Ingredient.create(recipe.ingredients, function (err, ingredient) {
        //     if (err) { return next(err); }
        // });
        // var ingArray = req.body.ingredients;
        // for (var i = 0; i < ingArray.length; i++) {
        //     Ingredient.findOneAndUpdate(ingArray[i]._id, ingArray[i], { new: true }, function (err, recipe) {
        //         if (err) { return next(err); }
        //         if (recipe == null) {
        //             var newIngredient = new Ingredient(req.body);
        //             recipe.ingredients.push(newIngredient);
        //             newIngredient.save();
        //         }
        //     });
        // }

        recipe.save();
        res.json(recipe);
    });
});

// Delete the ingredient from withing recipe with the given  ID
router.delete('/:id/ingredients/:ingredientId', function (req, res, next) {
    var id = req.params.id;
    var ingredientId = req.params.ingredientId;

    //solution 1
    Recipe.findById(id, function (err, recipe) {
        if (err) { return next(err); }
        if (recipe === null) {
            return res.status(404).json({ 'message': 'recipe not found' });
        }
        for (var i = 0; i < recipe.ingredients.length; i++) {
            if (recipe.ingredients[i]._id == ingredientId) {
                recipe.ingredients.splice(i, 1);
            }
        }
        Ingredient.findOneAndDelete({ _id: ingredientId }, function (err, foundIngredient) {
            if (err) { return next(err); }

            recipe.save();
            res.status(201).json(recipe);
            // res.status(201).json(recipe.ingredients[recipe.ingredients.length - 1]);
        });
    });
});

// Delete the recipe with the given ID
router.delete('/:id', function (req, res, next) {
    var id = req.params.id;
    Recipe.findOneAndDelete({ _id: id }, function (err, recipe) {
        if (err) { return next(err); }
        if (recipe === null) {
            return res.status(404).json({ 'message': 'recipe not found' });
        }
        res.json(recipe);
    });
});

// Deletes all recipes
router.delete('/', function (req, res, next) {
    Recipe.remove(function (err, recipe) {
        if (err) { return next(err); }
        if (recipe === null) {
            return res.status(404).json({ 'message': 'Recipe list is empty' });
        }
        res.json(recipe);
    });
});

module.exports = router;