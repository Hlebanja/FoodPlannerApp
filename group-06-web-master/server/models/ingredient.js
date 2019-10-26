// Require Mongoose
var mongoose = require('mongoose');
// Define a schema
var Schema = mongoose.Schema;
// Create an object of type mongooseSchema
var ingredientSchema = new Schema({
    name: { type: String },
    description: { type: String},
    nutritionalInfo: {type: String}
});

/* Creating (compiling) model from schema
The first argument is the singular name of the collection that will be created 
for your model , and the second argument is the schema you want to use in creating the model. */
module.exports = mongoose.model('ingredient', ingredientSchema);
