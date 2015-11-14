/**
 * 检测页面视图的配置文件是否完整，包括了进行检测必要的变量是否存在，以及文件的格式等等。

 */

'use strict';

var config = require('../config.js'), utils = require('../utils.js'), fs = require('fs'), async = require('async');

var freemarkerConfig = config.freemarkerConfig;

// 检测是否存在
var checkExists = function(callback) {
  fs.exists(freemakerConfig, callback);
};

// 检测必要的变量格式是否正确
var checkFormat = function(callback) {
  var contentExpress = '';
  var varExpress = '';
  
  try {
    var configContent = fs.readFileSync(freemakerConfig);
    
  } catch (e) {
    callback(e);
  }
};

exports.run = function(callback) {
  utils.log('开始检测配置文件 ' + freemakerConfig);
  async.series([checkExists, checkFormat], callback);
  utils.log('配置文件检测完成！\n');
}; 