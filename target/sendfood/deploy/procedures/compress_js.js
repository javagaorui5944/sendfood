/**
 * 对前端工程中js文件进行混淆，压缩和归并等等操作。
 */

'use strict';

var utils = require('../utils.js'), config = require('../config.js'), storage = require('../storage.js');
var uglifyjs = require('uglify-js'), fs = require('fs'), requirejs = require('requirejs');

var distfolder = storage.get('distfolder');

// 压缩javascript时候的具体配置文件，详细可见uglifyjs文档
var compressOptions = {
  mangle : true,
  generateSourceMaps : true
};

// 压缩javascript文件
var doCompression = function(path) {
  var targetContent = uglifyjs.minify(path, compressOptions).code;
  fs.writeFileSync(path, targetContent);
};

// 按照模块的依赖关系合并所有的js文件
var doCombine = function(callback) {
  var path = pagesPath.shift();
  if (!path) {
    callback();
  }
  
  var name = path.slice((distfolder + '/js').length + 1, -3);
  var combineOptions = {
    'baseUrl' : distfolder + '/js',
    'name' : name,
    'out' : path,
    'uglifyjs2' : combineOptions
  };
  requirejs.optimize(combineOptions, function() {
    utils.log(name + ' 合并完成！');
    doCombine(callback);
  });
};

// 遍历所有的js文件，并对其进行压缩
var pagesPath = [];
var walkJavascripts = function(path) {
  var filesList = fs.readdirSync(path);

  filesList.forEach(function(currentFile) {
    var tPath = path + '/' + currentFile;
      console.log(tPath);
    if (fs.statSync(tPath).isDirectory()) {

      // 剔除test各类测试用例的目录
      if (currentFile != 'test') {
        walkJavascripts(tPath);
      }
    } else {

      // 进行一次判断，是否为就javascript文件，如果是则进行处理
      var suffixExpress = /\.([^\.]+)$/;
      var results = suffixExpress.exec(tPath);
      var ext;
      try {
        ext = results[1];
      } catch (e) {
        ;
      }
      if (ext == 'js') {
        if (tPath.indexOf(distfolder + '/js/page') != -1) {
          pagesPath.push(tPath);
        } else {
          doCompression(tPath, ext);
        }
      }
    }
  });
};

exports.run = function(callback) {
  utils.log('开始处理javascript文件！');
  utils.log('请稍后...');
  try {
    utils.log('正在压缩通用脚本...');
    walkJavascripts(distfolder + '/js');
    utils.log('压缩处理完毕！');
    utils.log('正在合并脚本...');
    utils.log('请稍后...');
    doCombine(function() {
      callback();
      utils.log('javascript文件处理完成！\n');
    });
  } catch (e) {
    callback(e);
  }
};
