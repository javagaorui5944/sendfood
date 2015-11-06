/**
 * 数据通信模块，负责各个模块之间的临时数据的通信。
 */

'use strict';

var data = {};

exports.set = function(key, value) {
  data[key] = value;
};

exports.get = function(key) {
  return data[key];
};