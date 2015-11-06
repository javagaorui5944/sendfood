/**
 * 将静态文件移动到指定的地点，目前是和开发的静态置于同一个目录下。 如果以后有需求可以移动到CDN等其他的相关路径。

 */

'use strict';

var storage = require('../storage.js'), utils = require('../utils.js'), config = require('../config.js');
var ncp = require('ncp'), rimraf = require('rimraf'), async = require('async'), fs = require('fs');

// 目标静态文件所在的临时目录
var path = storage.get('distfolder'), staticFolder = config.staticFolder, version = storage.get('version');

// 创建目标目录
var createTarget = function(callback) {
  async.series([
  function(callback) {
    
    // 创建前端根目录
    utils.log('创建目录' + staticFolder + '/' + version);
    fs.mkdir(staticFolder + '/' + version, callback);
  },
  function(callback) {
    
    // 创建css目录
    fs.mkdir(staticFolder + '/' + version + '/css', callback);
  }], callback);
};

// 移动文件
var moveFiles = function(callback) {
  async.series([
  function(callback) {

    // 移动最终合并后的目标css文件
    ncp(path + '/css', staticFolder + '/' + version + '/css', callback);
  }, function(callback) {

    // 移动目标js文件
    ncp(path + '/js', staticFolder + '/' + version + '/js', callback);
  }, function(callback) {
    
    // 移动图片文件
    ncp(path + '/image', staticFolder + '/' + version + '/image', callback);
  }], callback);
};

// 清理文件
var cleanFiles = function(callback) {
  utils.log('清理目录' + path);
  rimraf(path, callback);
};

exports.run = function(callback) {
  utils.log('开始文件移动以及清理！');
  async.series([createTarget, moveFiles, cleanFiles], callback);
};