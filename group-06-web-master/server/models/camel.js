var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var camelSchema = new Schema({
    color: { type: String },
    position: { type: Number }
});

module.exports = mongoose.model('camel', camelSchema);
