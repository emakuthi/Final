
var selDiv = "";

document.addEventListener("DOMContentLoaded", init, false);

function init() {
    document.getElementbyId('#files').addEventListener('change', handleFileSelect, false);
    selDiv = document.getElementbyId("#selectedFiles");
}

function handleFileSelect(e) {

    if(!e.target.files) return;

    selDiv.innerHTML = "";

    var files = e.target.files;
    for(var i=0; i<files.length; i++) {
        var f = files[i];

        selDiv.innerHTML += f.name + "<br/>";

    }

}
//(function () {
//	var fileCatcher = document.getElementById('file-catcher');
//  var fileInput = document.getElementById('file-input');
//  var fileListDisplay = document.getElementById('file-list-display');
//
//  var fileList = [];
//  var renderFileList, sendFile;
//
//  fileCatcher.addEventListener('submit', function (evnt) {
//  	evnt.preventDefault();
//    fileList.forEach(function (file) {
//    	sendFile(file);
//    });
//  });
//
//  fileInput.addEventListener('change', function (evnt) {
// 		fileList = [];
//  	for (var i = 0; i < fileInput.files.length; i++) {
//    	fileList.push(fileInput.files[i]);
//    }
//    renderFileList();
//  });
//
//  renderFileList = function () {
//  	fileListDisplay.innerHTML = '';
//    fileList.forEach(function (file, index) {
//    	var fileDisplayEl = document.createElement('p');
//      fileDisplayEl.innerHTML = (index + 1) + ': ' + file.name;
//      fileListDisplay.appendChild(fileDisplayEl);
//    });
//  };
//  sendFile = function (file) {
//    	var formData = new FormData();
//      var request = new XMLHttpRequest();
//
//      formData.set('file', file);
//      request.open("POST", 'https://cloudinary.com/console/media_library/folders/all/content');
//      request.send(formData);
//    };
//})();

