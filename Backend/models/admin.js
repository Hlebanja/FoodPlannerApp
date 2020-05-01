// Require Mongoose
var mongoose = require('mongoose');
// Define a schema
var Schema = mongoose.Schema;
// Create an object of type mongooseSchema
var adminSchema = new Schema({
    name: { type: String, trim:true, default:'' },
    password:{ type: String, trim:true, default:''}
    
});

/* Creating (compiling) model from schema
The first argument is the singular name of the collection that will be created 
for your model , and the second argument is the schema you want to use in creating the model. */
module.exports = mongoose.model('admin', adminSchema);


