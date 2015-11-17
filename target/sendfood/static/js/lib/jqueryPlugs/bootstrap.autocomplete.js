
define(['lib/jquery','lib/basic/bootstrap'],function(jQuery){
!function($){
    /* 基于Bootstrap Typeahead改造而来的自动完成插件
     * Author：F.L.F
     * Site: http://digdata.me
     * ================================= */
    var Autocomplete = function (element, options) {
        var self = this;
        this.$element = $(element)
        this.options = $.extend({}, $.fn.autocomplete.defaults, options)
        this.sorter = this.options.sorter || this.sorter
        this.highlighter = this.options.highlighter || this.highlighter
        this.updater = this.options.updater || this.updater
        this.source = this.options.source
        this.$menu = $(this.options.menu)
        this.shown = false
        this.formatItem = this.options.formatItem || this.formatItem
        this.setValue = this.options.setValue || this.setValue
        this.listen();
        this.muti =  this.options.muti == false  ?  false : this.options.muti; //是否是多选框,吉鹏新增
        if(this.muti == undefined){
            this.muti = false;
        }
        //选中之后的回调函数
        this.callback = this.options.callback || function(){}
        
        //找到当前的删除按钮,删除单选的框
        $(self.$element).parents(".J-select-parent").find(".J-em-delete").click(function(e){
            e.stopPropagation();
                $(self.$element).val("");
                $(self.$element).trigger('change');
                $(self.$element).attr("real-value","");
                $(this).parent().remove();
        });

        //找到当前的删除按钮,删除多选的框
        $(this.muti).find(".J-em-delete").click(function(e){
            e.stopPropagation();
            $(self.$element).val("");
            $(self.$element).trigger('change');
            $(self.$element).attr("real-value","");
            $(this).parent().remove();
        });
        $(element).keyup(function(event){
            var span = $(this).parent().find("span");
            if(event.keyCode == 8){
                if($(self.$element).val().trim() == ""){
                    if(self.muti == false){
                        $(self.$element).attr("real-value","");
                        $("#J-select-span_"+$(self.$element).attr("id")).remove();
                    }
                }
                if(span.hasClass("s-active")){
                    if(self.muti == false){
                        $(self.$element).attr("real-value","");
                        $("#J-select-span_"+$(self.$element).attr("id")).remove();
                    }
                }else{
                    $(span).addClass("s-active");
                }
            }else if(event.keyCode == 39){
                $(span).removeClass("s-active");
            }else if(event.keyCode == 37){
                $(span).addClass("s-active");
            }

        });
    }
    Autocomplete.prototype = {
        constructor: Autocomplete
        , processObj:0
        , formatItem:function(item){
            return item.toString();
        }
        , setValue:function(item){
            return {"data-value":item.toString(),"real-value":item.toString()};
        }
        ,delete:function(){

        }
        , select: function (item) {
            var self = this;
            var val = this.$menu.find('.active').attr('data-value')
            var realVal = this.$menu.find('.active').attr('real-value')
            this.$element
                .val(this.updater(val)).attr("real-value",realVal)
                .change()
            $(this.$menu).focus();
            var parent = $(this.$element).parent();

            var top = parseFloat($(this.$element).css("padding-top").replace('px', ''));
            var left = parseFloat($(this.$element).css("padding-left").replace('px', ''));
            var id = "J-select-span_"+$(this.$element).attr("id");
            if(this.muti == false){
                $("#"+id).remove();
                $("<span id='"+id+"' class='J-select-span' style='width:auto;position:absolute;left:10px;top:"+top+"px;'>"+this.updater(val)+"<em class='J-em-delete'></em></span>").appendTo($(parent));
                $("#"+id).css({"display":"inline-block"});
                //找到当前的删除按钮,删除单选的框
                $(self.$element).parents(".J-select-parent").find(".J-em-delete").click(function(e){
                    e.stopPropagation();
                    $(self.$element).val("");
                    $(self.$element).trigger('change');
                    $(self.$element).attr("real-value","");
                    $(this).parent().remove();
                });
            }else{
                $(this.muti).css({"position":"relative"});
                //计算宽度
                var selectSpan = $(this.muti).find(".J-select-span");
                var hasflag = true;
                $(selectSpan).each(function(){
                    var spanId = $(this).text().trim();
                    if(spanId ==  self.updater(val)){
                        hasflag = false;
                    }
                });
                //如果先前没有选择过
                if(hasflag){
                    var real_value = $(this.$element).attr("real-value");
                    $("<span class='J-select-span' real-value='"+real_value+"' style='width:auto;float:left;position:relative;display:inline-block;'>"+this.updater(val)+"<em class='J-em-delete'></em></span>").appendTo($(this.muti));
                    //找到当前的删除按钮,删除多选的框
                    $(this.muti).find(".J-em-delete").click(function(e){
                        e.stopPropagation();
                        $(self.$element).val("");
                        $(self.$element).trigger('change');
                         $(self.$element).attr("real-value","");
                        $(this).parent().remove();
                    });
                }

            }

            this.callback(realVal);

            return this.hide();
        }
        , updater: function (item) {
            return item
        }
        , show: function () {
            var pos = $.extend({}, this.$element.position(), {
                height: this.$element[0].offsetHeight
            })
            this.$menu
                .insertAfter(this.$element)
                .css({
                    top: pos.top + pos.height
                    , left: pos.left
                })
                .show()
            this.shown = true
            return this
        }

        , hide: function () {
            this.$menu.hide()
            this.shown = false
            return this
        }

        , lookup: function (event) {
            var items
            this.query = this.$element.val()
            if (this.query.length < this.options.minLength) {
                return this.shown ? this.hide() : this
            }
            items = $.isFunction(this.source) ? this.source(this.query, $.proxy(this.process, this)) : this.source
            return items ? this.process(items) : this
        }

        , process: function (items) {
            var that = this;
            if (!items.length) {
                return this.shown ? this.hide() : this;
            }
            return this.render(items.slice(0, this.options.items)).show();
        }

        , highlighter: function (item) {
            var that = this
            item = that.formatItem(item)
            var query = this.query.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g, '\\$&')
            return item.replace(new RegExp('(' + query + ')', 'ig'), function ($1, match) {
                return '<strong style="color:#FF6600;">' + match + '</strong>'
            })
        }

        , render: function (items) {
            var that = this
            items = $(items).map(function (i, item) {
                i = $(that.options.item).attr(that.setValue(item))
                i.find('a').html(that.highlighter(item))
                return i[0]
            })
            items.first().addClass('active')
            this.$menu.html(items)
            return this
        }

        , next: function (event) {
            var active = this.$menu.find('.active').removeClass('active')
                , next = active.next()

            if (!next.length) {
                next = $(this.$menu.find('li')[0])
            }

            next.addClass('active')
        }

        , prev: function (event) {
            var active = this.$menu.find('.active').removeClass('active')
                , prev = active.prev()

            if (!prev.length) {
                prev = this.$menu.find('li').last()
            }

            prev.addClass('active')
        }

        , listen: function() {
            this.$element.off();   //清空事件
            this.$element
                .on('focus',    $.proxy(this.focus, this))
                .on('blur',     $.proxy(this.blur, this))
                .on('keypress', $.proxy(this.keypress, this))
                .on('keyup',    $.proxy(this.keyup, this))
            if (this.eventSupported('keydown')) {
                this.$element.on('keydown', $.proxy(this.keydown, this))
            }
            this.$menu
                .on('click', $.proxy(this.click, this))
                .on('mouseenter', 'li', $.proxy(this.mouseenter, this))
                .on('mouseleave', 'li', $.proxy(this.mouseleave, this))
        }

        , eventSupported: function(eventName) {
            var isSupported = eventName in this.$element
            if (!isSupported) {
                this.$element.setAttribute(eventName, 'return;')
                isSupported = typeof this.$element[eventName] === 'function'
            }
            return isSupported
        }

        , move: function (e) {
            if (!this.shown) return
            switch(e.keyCode) {
                case 9:  // tab
                case 13: // enter
                case 27: // escape
                    e.preventDefault()
                    break

                case 38: // up arrow
                    e.preventDefault()
                    this.prev()
                    break

                case 40: // down arrow
                    e.preventDefault()
                    this.next()
                    break
            }

            e.stopPropagation()
        }

        , keydown: function (e) {
            this.suppressKeyPressRepeat = ~$.inArray(e.keyCode, [40,38,9,13,27])
            this.move(e)
        }

        , keypress: function (e) {
            if (this.suppressKeyPressRepeat) return
            this.move(e)
        }

        , keyup: function (e) {
            switch(e.keyCode) {
                case 40: // down arrow
                case 38: // up arrow
                case 16: // shift
                case 17: // ctrl
                case 18: // alt
                    break

                case 9: // tab
                case 13: // enter
                    if (!this.shown) return
                    this.select()
                    break

                case 27: // escape
                    if (!this.shown) return
                    this.hide()
                    break

                default:
                    var that = this
                    if(that.processObj){
                        clearTimeout(that.processObj)
                        that.processObj = 0
                    }
                    that.processObj = setTimeout(function(){
                        that.lookup()
                    },that.options.delay)
            }

            e.stopPropagation()
            e.preventDefault()
        }

        , focus: function (e) {
            if(this.focused != true){
                this.focused = true
                var that = this
                if(that.processObj){
                    clearTimeout(that.processObj)
                    that.processObj = 0
                }
                that.processObj = setTimeout(function(){
                    that.lookup()
                },that.options.delay)
            }
        }

        , blur: function (e) {
            this.focused = false
            if (!this.mousedover && this.shown) this.hide()
        }

        , click: function (e) {
            e.stopPropagation()
            e.preventDefault()
            this.select()
            //this.$element.focus()
        }

        , mouseenter: function (e) {
            this.mousedover = true
            this.$menu.find('.active').removeClass('active')
            $(e.currentTarget).addClass('active')
        }

        , mouseleave: function (e) {
            this.mousedover = false
            if (!this.focused && this.shown) this.hide()
        }

    }


    /* TYPEAHEAD PLUGIN DEFINITION
     * =========================== */

    var old = $.fn.autocomplete

    $.fn.autocomplete = function (option) {
        return this.each(function () {
            var $this = $(this)
                , data = $this.data('autocomplete')
                , options = typeof option == 'object' && option
            if (!data) $this.data('autocomplete', (data = new Autocomplete(this, options)))
            if (typeof option == 'string') data[option]()
        })
    }

    $.fn.autocomplete.defaults = {
        source: []
        // , items: 8
        , menu: '<ul class="typeahead dropdown-menu global-dropdown-menu"></ul>'
        , item: '<li><a href="#"></a></li>'
        , minLength: 0
        , delay: 500
    }

    $.fn.autocomplete.Constructor = Autocomplete


    /* TYPEAHEAD NO CONFLICT
     * =================== */

    $.fn.autocomplete.noConflict = function () {
        $.fn.autocomplete = old
        return this
    }


    /* TYPEAHEAD DATA-API
     * ================== */

    $(document).on('focus.autocomplete.data-api', '[data-provide="autocomplete"]', function (e) {
        var $this = $(this)
        if ($this.data('autocomplete')) return
        $this.autocomplete($this.data())
    })

}(jQuery);

});