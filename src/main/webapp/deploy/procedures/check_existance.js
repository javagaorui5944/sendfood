/**
 * 检测配置文件中，指定的目录的完整性。

 */

'use strict';

var fs = require('fs'), async = require('async'), config = require('../config.js'), utils = require('../utils.js');

var staticFolder = config.staticFolder, freemarkerFolder = config.freemarkerFolder;

var totalLength = freemarkerFolder.length + 1;
var counter = 0, callback;
var checking = function(exists) {
  if (!exists) {
    callback(new Error('文件结构不正确！'));
  }
  utils.log('目录存在！');
  counter++;
  if (counter === totalLength) {
    utils.log('目录结构验证完成！\n');
    callback();
  }
};

exports.run = function(cb) {
  callback = cb;

  // 遍历文件列表
  var tempFolders = [];
  for ( var i in freemarkerFolder) {
    tempFolders.push(freemarkerFolder[i]);
  }
  tempFolders.push(staticFolder);
  utils.log('开始检测');
  tempFolders.forEach(function(folder) {
    utils.log('检测目录' + folder);
    fs.exists(folder, checking);
  });
};