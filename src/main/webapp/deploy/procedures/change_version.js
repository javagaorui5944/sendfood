/**
 * 改变freemarker文件中所有静态资源的版本号。

 */

'use strict';

var fs = require('fs'), async = require('async'), config = require('../config.js'), storage = require('../storage.js'), utils = require('../utils.js');

var version = storage.get('version');
var versionExpression = /("\/static)(\/[^"\?]*)(?:\?v=(\${10}))"/ig;

var changeVersion = function(path) {
  var originalContent = fs.readFileSync(path).toString();
  var hasChanged = false;
  var changedContent = originalContent.replace(versionExpression, function($0,
      $1, $2, $3) {
    utils.log('需要修改版本号的的URL' + $0);
    var target = $1 + '/' + version + $2 + '"';
    hasChanged = true;
    utils.log('替换成的目标URL' + target);
    return target;
  });
  if (hasChanged) {
    utils.log('修改目标文件' + path);
    fs.writeFileSync(path, changedContent);
  }
};

var walkFiles = function(path, callback) {
  var filesList = fs.readdirSync(path);
  filesList.forEach(function(currentFile) {
    var tPath = path + '/' + currentFile;
    if (fs.statSync(tPath).isDirectory()) {
      walkFiles(tPath);
    } else {
      changeVersion(tPath);
    }
  });
};

exports.run = function(callback) {
  try {
    utils.log('开始修改目录版本号！');
    config.freemarkerFolder.forEach(function(folder) {
      utils.log('开始遍历目录' + folder);
      walkFiles(folder, callback);
    });
    utils.log('目录版本号修改完成！\n');
    callback();
  } catch (e) {
    callback(e);
  }
};