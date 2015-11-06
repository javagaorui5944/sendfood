/*
 * 简单的 JavaScript 集成实现
 *
 * 使用 Class 来创建基类
 * 使用基类的 extend 方法实现继承
 * Demo:
 * var Foo = Class.extend({
     * name: 'foo',
     * getName: function() {
         * return this.name;
     * }
 * });
 *
 * var Bar = Foo.exntend({
     * name: 'bar',
     * getName: function() {
         * return 'name is:'+this._super();
     * }
 * });
 */

/* Simple JavaScript Inheritance
 * By John Resig http://ejohn.org/
 * MIT Licensed.
 */
// Inspired by base2 and Prototype

define(function(){
    var initializing = false;
    var fnTest = /xyz/.test(function(){xyz;}) ? /\b_super\b/ : /.*/;

    /*
     * 将 superProxy 提出来以优化性能
     * 性能优化 log 见
     * http://jsperf.com/johnresig-class-extend
     */
    function superProxy(_super, fn) {
        return function() {
            // make a copy of this._super
            var tmp = this._super;

            // Add a new ._super() method that is the same method
            // but on the super-class
            this._super = _super;

            // The method only need to be bound temporarily, so we
            // remove it when we're done executing
            var ret = fn.apply(this, arguments);        

            // revert this._super
            this._super = tmp;

            return ret;
        };
    }

    // The base Class implementation (does nothing)
    var Class = function(){};

    // Create a new Class that inherits from this class
    Class.extend = function(prop) {
        var _super = this.prototype;

        // Instantiate a base class (but only create the instance,
        // don't run the init constructor)
        initializing = true;
        var prototype = new this();
        initializing = false;

        // Copy the properties over onto the new prototype
        for (var name in prop) {
            // Check if we're overwriting an existing function
            prototype[name] = typeof prop[name] == "function" 
                              && typeof _super[name] == "function" 
                              && fnTest.test(prop[name]) 
                              ? superProxy(_super[name], prop[name]) 
                              : prop[name];
        }

        // The dummy class constructor
        function Class() {
            // All construction is actually done in the init method
            if ( !initializing && this.init )
            this.init.apply(this, arguments);
        }
        
        // Populate our constructed prototype object
        Class.prototype = prototype;

        // Enforce the constructor to be what we expect
        Class.constructor = Class;

        // And make this class extendable
        Class.extend = arguments.callee;

        return Class;
    };

    return Class;
});
