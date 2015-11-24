/**
 * 对前端工程中的css，js文件进行混淆，压缩和归并等等操作。

 */

'use strict';

var storage = require('../storage.js'), utils = require('../utils.js'), uglifyjs = require('uglify-js'), cleancss = require('clean-css'), fs = require('fs');

// 目标静态文件所在的临时目录
var path = storage.get('distfolder');

// 进行文件的压缩和混淆
var compress = function(path, ext) {
  if (ext == 'css') {
    var originalContent = fs.readFileSync(path).toString();
    var targetContent = new cleancss({
      keepBreaks : true
    }).minify(originalContent);
    fs.writeFileSync(path, targetContent);
  } else {
    var targetContent = uglifyjs.minify(path, {
      mangle : true
      // warnings : true
    }).code;
    fs.writeFileSync(path, targetContent);
  }
};

var walkFiles = function(path) {
  var filesList = fs.readdirSync(path);
  filesList.forEach(function(currentFile) {
    var tPath = path + '/' + currentFile;
    if (fs.statSync(tPath).isDirectory()) {
      walkFiles(tPath);
    } else {

      // 判断如果是js或者css文件，则进行压缩，否则不作处理
      var suffixExpress = /\.([^\.]+)$/;
      var results = suffixExpress.exec(tPath);
      var ext;
      try {
        ext = results[1];
      } catch (e) {
        ;
      }
      if (ext == 'css' || ext == 'js') {
        compress(tPath, ext);
      }
    }
  });
};

exports.run = function(callback) {
  try {
    utils.log('开始压缩当前目录的静态文件！');
    utils.log('处理中，请稍后...');
    walkFiles(path);
    utils.log('静态文件压缩成功！\n');
    callback();
  } catch (e) {
    callback(e);
  }
};