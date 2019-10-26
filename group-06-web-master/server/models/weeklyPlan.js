// Require Mongoose
var mongoose = require('mongoose');
// Define a schema
var Schema = mongoose.Schema;
// Create an object of type mongooseSchema
var weeklyPlanSchema = new Schema({
    //we need to adjust the recipe type to arraylist.
    days: { type: String, trim: true, default: '' },
    weeks: { type: String, trim: true, default: '' },
    recipes: [{
        type: mongoose.Schema.Types.ObjectId, ref: "recipe"
    }]
});

/* Creating (compiling) model from schema
The first argument is the singular name of the collection that will be created 
for your model , and the second argument is the schema you want to use in creating the model. */
module.exports = mongoose.model('weeklyPlan', weeklyPlanSchema);

