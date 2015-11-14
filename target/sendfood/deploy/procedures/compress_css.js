/**
 * 对前端工程中css文件进行混淆，压缩和归并等等操作。
 */

'use strict';

var utils = require('../utils.js'), config = require('../config.js'), storage = require('../storage.js');
var requirejs = require('requirejs'), fs = require('fs'), async = require('async');

var distfolder = storage.get('distfolder');

// 合并，处理CSS文件
var combineCSS = function() {
  var targetCSS = distfolder + '/css/global.css';
  var result = fs.readFileSync(targetCSS).toString().trim();
  requirejs.optimize({
    'optimizeCss' : 'standard',
    'cssIn' : targetCSS,
    'out' : targetCSS
  });
    var targetCSS1 = distfolder + '/css/globalUser.css';
    var result1 = fs.readFileSync(targetCSS1).toString().trim();
    requirejs.optimize({
      'optimizeCss' : 'standard',
      'cssIn' : targetCSS1,
      'out' : targetCSS1
    });
};

// 压缩每个单页的css文件
var walkCSS = function(path) {
  var filesList = fs.readdirSync(path);

  filesList.forEach(function(currentFile) {
    var tPath = path + '/' + currentFile;
    if (fs.statSync(tPath).isDirectory()) {
      walkCSS(tPath);
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
      if (ext == 'css') {
        try {
          requirejs.optimize({
            'optimizeCss' : 'standard',
            'cssIn' : tPath,
            'out' : tPath
          });
        } catch (e) {
          callback(e);
        }
      }
    }
  });
};

exports.run = function(callback) {
  try {
    utils.log('开始处理CSS文件！');
    utils.log('请稍后...');
    combineCSS();
    utils.log('开始遍历page目录下的CSS文件！');
    walkCSS(distfolder + '/css');
    utils.log('CSS文件处理成功！\n');
    callback();
  } catch (e) {
    callback(e);
  }
};
