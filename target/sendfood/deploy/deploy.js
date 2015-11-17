/**
 * 前端部署（包括了对代码的混淆，压缩，和对模板文件的修改，版本号的计算，以及后续CDN的推送等等） 主文件， 负责执行流程的控制。

 */

'use strict';

var async = require('async'), fs = require('fs'), config = require('./config.js'), storage = require('./storage.js'), utils = require('./utils.js');

// 设置当前的版本号和日志文件。
var gitRevision;
console.log("程序执行");
process.argv.forEach(function (val, index, array) {
  if (val == '--rev') {
    if (process.argv.length >= (index + 2)) {
      gitRevision = process.argv[index + 1];
    }    
  }
});

var version = gitRevision || config.version || Math.round(new Date().getTime() / 1000), logfile = './log/deploy.' + version + '.log';
version = Number(version).toString(16);
storage.set('version', version);
storage.set('logfile', logfile);

var routes = {};
require('./config.js').runModules.forEach(function(module) {
  routes[module] = require('./procedures/' + module + '.js').run;
});

var resultsHandler = function(err, results) {

  // 需要对结果进行处理
  if (results) {
    ;
  }

  if (err) {
       utils.log('部署失败，因为' + err.message + '！');
  } else {
    utils.log('部署成功！');
  }
};

fs.appendFile(logfile, 'Log for static version "' + version + '" at ' + utils.getTime('yyyy-MM-dd hh:mm:ss') + '\n\n', function(err) {
  if (err) {
    utils.log(err.message);
  }
  async.series(routes, resultsHandler);
});