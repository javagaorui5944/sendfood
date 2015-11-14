/**
 * 通用工具类方法，用于封装一些基本的通用方法等等。
 */

'use strict';

var fs = require('fs'), storage = require('./storage.js');

exports.getTime = function(format) {
  var now = new Date();

  var o = {
    "M+" : now.getMonth() + 1,
    "d+" : now.getDate(),
    "h+" : now.getHours(),
    "m+" : now.getMinutes(),
    "s+" : now.getSeconds(),
    "q+" : Math.floor((now.getMonth() + 3) / 3),
    "S" : now.getMilliseconds()
  };

  if (/(y+)/.test(format)) {
    format = format.replace(RegExp.$1, (now.getFullYear() + "")
        .substr(4 - RegExp.$1.length));
  }
  for ( var k in o) {
    if (new RegExp("(" + k + ")").test(format)) {
      format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
          : ("00" + o[k]).substr(("" + o[k]).length));
    }
  }
  return format;
};

exports.log = function(txt) {
  var logfile = storage.get('logfile');
  fs.appendFileSync(logfile, txt + '\n');
  console.log(txt);
  return this;
};
