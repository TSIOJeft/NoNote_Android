const fs = require('browserify-fs');
function getFile(filePath) {
    fetch(filePath)
        .then((response) => response.text())
        .then((data) => {
            vditor.setValue(data, (clearStack = false));
        });
}
function saveFile(filePath, text) {

    fs.writeFile(filePath, text, (err) => {
        if (err) {
            console.error('Error writing file:', err);
        } else {
            console.log('File saved successfully!');
        }
    });
}
exports.saveFile = saveFile
exports.getFile = getFile