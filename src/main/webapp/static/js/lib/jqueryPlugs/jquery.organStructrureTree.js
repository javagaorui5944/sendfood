/**
 * 用于匹配城市权限结构得树形图jquery插件Tree
 * author jipeng@meituan.com
 * date 2105/04/15
 */
/***
 *
 * options ＝{
            data:[],//接点数据
            nodeType:"",//接点类型，不同的节点类型有不同的模版（因为后台数据比较复杂，就没有用一套模版）当前就2种，城市节点和人员节点
            events:[] //所有的自定义事件列表，会依次执行
        }
 *
 *
 注意这些例子可以在目前的插件代码中正确运行，并不是所有的插件都使用同样的代码结构
 // 为每个类名为 ".className" 的元素执行init方法
 $('.className').pluginName();
 $('.className').pluginName('init');
 $('.className').pluginName('init', {}); // 向init方法传入“{}”对象作为函数参数
 $('.className').pluginName({}); // 向init方法传入“{}”对象作为函数参数
 // 为每个类名为 “.className” 的元素执行destroy方法
 $('.className').pluginName('destroy');
 $('.className').pluginName('destroy', {}); // 向destroy方法传入“{}”对象作为函数参数
 // 所有代码都可以正常运行
 $('.className').pluginName('init', 'argument1', 'argument2'); // 把 "argument 1" 和 "argument 2" 传入 "init"

 // 不正确的使用
 $('.className').pluginName('nonexistantMethod');
 $('.className').pluginName('nonexistantMethod', {});
 $('.className').pluginName('argument 1'); // 会尝试调用 "argument 1" 方法
 $('.className').pluginName('argument 1', 'argument 2'); // 会尝试调用 "argument 1" ，“argument 2”方法
 $('.className').pluginName('privateFunction'); // 'privateFunction' 不是一个方法
 ***/
define(['lib/jquery','tpl/organStructrureTree'],function($,organStructrureTree){
    var privateFunction = function() {
        // 执行代码
    }
    var methods = {
        _toggleClass:"J-treetaggle",
        init: function(options) {

            // 在每个元素上执行方法
            return this.each(function() {
                var $this = $(this);
                    var defaults = {
                        data:[],
                        nodeType:"",
                        events:[]
                    }
                    var settings = $.extend({}, defaults, options);

                   //表示加载的是员工数据(目前是硬编码，以后再来扩展，没时间，呜呜)
                    if(settings.nodeType == "employ"){
                        var tpl = organStructrureTree.initEmployThree(settings,methods._toggleClass);
                        $this.html(tpl);
                    }else{
                        var tpl  =  organStructrureTree.initThree(settings,methods._toggleClass);
                        $this.html(tpl);
                    }
                 //加进去之后再给＋号按钮taggle加事件
                 methods._treeToggle();
                 //执行自定义事件events
                 $(settings.events).each(function(){
                     this();
                 });


            });
        },
        //最后一行的taggle显示效果
        _treeToggle:function(){
            $(".btn-addtree").click(function(){

                if($(this).next().css("display") == "none"){
//                    setTimeout(function(){
                    $(this).next().css({"display":"block"})
                }else{


//                    },1000);


                    $(".inner-box1").scrollLeft(0);
                    $(this).next().css({"display":"none"})
                    var left = $(this).prev().offset().left-600;
                    $(".inner-box1").scrollLeft(left);
                }

            });
        },
        destroy: function(options) {
            // 在每个元素中执行代码
            return $(this).each(function() {
                var $this = $(this);
                // 执行代码
                // 删除元素对应的数据
                $this.removeData('canvesTree');
            });
        },
        val: function(options) {
            // 这里的代码通过.eq(0)来获取选择器中的第一个元素的，我们或获取它的HTML内容作为我们的返回值
            var someValue = this.eq(0).html();

            // 返回值
            return someValue;
        }
    };

    $.fn.organStructrureTree = function() {
        var method = arguments[0];
        if(methods[method]) {
            method = methods[method];
            arguments = Array.prototype.slice.call(arguments, 1);
        } else if( typeof(method) == 'object' || !method ) {
            method = methods.init;
        } else {
            $.error( 'Method ' +  method + ' does not exist on jQuery.pluginName' );
            return this;
        }
        return method.apply(this, arguments);

    }

});
