/**
 * 用消息机制，进行各个模块之间的通信，希望能脱离DOM进行。
 * Ex:
 *   Observer.register('signal', function(data){
 *     console.log(data);
 *   })
 *   Observer.trigger('signal');
 *
 * @date 2014.01.06
 */

define(['lib/jquery'], function($) {
  'use strict';

  // 存放本地的所有的消息。
  var events = {};
  
  // 存放所有跨模块使用的事件
  var data = {};

  return {

    /**
     * 注册某个消息，并与之相连的是一个回调函数。
     *
     * @param {Object} key 需要注册的消息名称
     * @param {Object} value 可能是一个回调函数，或者是一个字符串
     */
    register : function(key, value) {
      if ($.isFunction(value)) {
        if (!events[key]) {
          events[key] = [];
        }
        events[key].push(value);
      } else {
        data[key] = value;
      }
    },

    /**
     * 触发某个消息（事件）
     *
     * @param {Object} evt 触发的消息名称
     * @param {Object} params 传给所有回调的参数
     */
    trigger : function(evt, params) {
      if (!events[evt]) {
        return;
      }

      var callbacks = events[evt];
      for (var i = 0; i < callbacks.length; i++) {
        callbacks[i](params);
      }
    },
    
    /**
     * 获取某个注册的减值
     *
     *
     */
    get : function(key) {
      if (!key) {
        return null;
      }
      
      return data[key];
    },

    /**
     * 查看是否注册了这个事件处理函数，注意匿名函数一般是无法找到的
     * @param evt 事件名
     * @param callback 函数 不传入这个参数则返回是否有函数注册了该事件
     * @return {boolean} 找到返回true，否则返回false
     */
    has : function(evt, callback) {
      if (!events[evt])
        return false;
      if (callback == null) {
        if (events[evt].length == 0)
          return false;
        else
          return true;
      }
      var evts = events[evt];
      for (var i = 0, len = evts.length; i < len; i++) {
        if (evts[i] == callback) {
          return true;
        }
      }
      return false;
    },

    /**
     * 取消注册，匿名函数无法取消
     * @param evt
     * @param callback 不传入这个参数则会删除所有事件
     */
    unRegister : function(evt, callback) {
      if (!events[evt])
        return;
      if (callback == null) {
        events[evt] = null;
        return;
      }
      var evts = events[evt];
      var locateEvt = function() {//找到第一个事件
        for (var i = 0, len = evts.length; i < len; i++) {
          if (evts[i] == callback) {
            return i;
          }
        }
        return -1;
      };
      var loc;
      while (( loc = locateEvt()) != -1) {
        evts.splice(loc, 1);
      }
    }
  };
});
