define(['lib/jquery'], function($){
    var defaults = {
        pagerId 			: 'wmpager', // divID
        mode				: 'click', // 模式(link 或者 click)
        pageNum				: 1, // 当前页码
        totalPage			: 1, // 总页码
        totalRecords		: 0, // 总数据条数
        isShowFirstPageBtn	: true, // 是否显示首页按钮
        isShowLastPageBtn	: true, // 是否显示尾页按钮
        isShowPrePageBtn	: true, // 是否显示上一页按钮
        isShowNextPageBtn	: true, // 是否显示下一页按钮
        isShowTotalPage 	: true, // 是否显示总页数
        isShowTotalRecords 	: true, // 是否显示总记录数
        isGoPage 			: true,	// 是否显示页码跳转输入框
        hrefFormer			: '', // 链接前部
        hrefLatter			: '', // 链接尾部
        gopageWrapId		: 'wmpager_gopage_wrap',
        gopageButtonId		: 'wmpager_btn_go',
        gopageTextboxId		: 'wmpager_btn_go_input',
        pageNumId			: "data-pageNum",
        lang				: {
            firstPageText			: '首页',
            firstPageTipText		: '首页',
            lastPageText			: '尾页',
            lastPageTipText			: '尾页',
            prePageText				: '&laquo;',
            prePageTipText			: '&laquo;',
            nextPageText			: '&raquo;',
            nextPageTipText			: '&raquo;',
            totalPageBeforeText		: '共',
            totalPageAfterText		: '页',
            totalRecordsAfterText	: '条数据',
            gopageBeforeText		: '转到',
            gopageButtonOkText		: '确定',
            gopageAfterText			: '页',
            buttonTipBeforeText		: '第',
            buttonTipAfterText		: '页'
        },
        click : function(n,e){
            return false;
        }
    };

    var options = {};   //全局

    $.fn.wmPagination = function(opts){
        var htmlOptions = {
            pageNum:parseInt($(this).attr('data-pageNum')),
            totalPage:parseInt($(this).attr('data-totalPage')),
            totalRecords:parseInt($(this).attr('data-totalRecords'))
        };

        options = $.extend({} , defaults , htmlOptions, opts);
        generPageHtml();
    }

    function addHandler(){
        $('#'+options.pagerId).on('click','a',function(){
            var n = $(this).attr(options.pageNumId);
            _clickHandler(n);
        });
        $('#'+options.pagerId).on('click','#'+options.gopageButtonId,gopage);
        $('#'+options.pagerId).on('focus','#'+options.gopageTextboxId,focus_gopage);
        $('#'+options.pagerId).on('blur','#'+options.gopageTextboxId,blur_gopage);
        $('#'+options.pagerId).on('keypress','#'+options.gopageTextboxId,function(e){
            var event = e || window.event;

            if(event.keyCode>=48 && event.keyCode<=57){
                return true;
            }else if(event.keyCode==13){
                gopage();
                return true;
            }else{
                return false;
            }
        });
    }

    function clearHandler(){
        $('#'+options.pagerId).off('click','a');
        $('#'+options.pagerId).off('click','#'+options.gopageButtonId);
        $('#'+options.pagerId).off('focus','#'+options.gopageTextboxId);
        $('#'+options.pagerId).off('blur','#'+options.gopageTextboxId);
        $('#'+options.pagerId).off('keypress','#'+options.gopageTextboxId);
    }

    function _getHandlerStr(n){
        return 'href="'+getHref(n)+'" '+options.pageNumId+'="'+n+'"';
    }

    function _clickHandler(n){
        var res = false;
        if(options.click && typeof options.click == 'function'){
            res = options.click.call(options,n) || false;
        }
        return res;
    }

    function getHref(n){
        // 默认返回#
        return 'javascript:;';
    }


    // 跳转框得到输入焦点时
    function focus_gopage(){
        var btnGo = $('#'+options.gopageButtonId);
        $('#'+options.gopageTextboxId).attr('hideFocus',true);
        btnGo.show();
        btnGo.css('left','0px');
        $('#'+options.gopageWrapId).css('border-color','#6694E3');
        btnGo.animate({left: '+=44'}, 50,function(){
            // $('#'+options.gopageWrapId).css('width','88px');
        });
    }

    // 跳转框失去输入焦点时
    function blur_gopage(){
        setTimeout(function(){
            var btnGo = $('#'+options.gopageButtonId);
            btnGo.animate({
                left: '-=44'
            }, 100, function(){
                btnGo.css('left','0px');
                btnGo.hide();
                $('#'+options.gopageWrapId).css('border-color','#DFDFDF');
            });
        },400);
    }

    // 跳转框页面跳转
    function gopage(){
        var str_page = $('#'+options.gopageTextboxId).val();
        if(isNaN(str_page)){
            $('#'+options.gopageTextboxId).val(options.next);
            return;
        }
        var n = parseInt(str_page);
        if(n < 1) n = 1;
        if(n > options.totalPage) n = options.totalPage;
        if(options.mode == 'click'){
            _clickHandler(n);
        }else{
            window.location = options.getLink(n);
        }
    }

    function generPageHtml(enforceInit){
        if(enforceInit || !options.inited){
            init(options);
        }

        clearHandler();

        var str_first='',str_prv='',str_next='',str_last='';
        if(options.isShowFirstPageBtn){
            if(options.hasPrv){
                str_first = '<a '+_getHandlerStr(1)+' title="'
                    +(options.lang.firstPageTipText || options.lang.firstPageText)+'">'+options.lang.firstPageText+'</a>';
            }else{
                str_first = '<span class="disabled">'+options.lang.firstPageText+'</span>';
            }
        }
        if(options.isShowPrePageBtn){
            if(options.hasPrv){
                str_prv = '<a '+_getHandlerStr(options.prv)+' title="'
                    +(options.lang.prePageTipText || options.lang.prePageText)+'">'+options.lang.prePageText+'</a>';
            }else{
                str_prv = '<span class="disabled">'+options.lang.prePageText+'</span>';
            }
        }
        if(options.isShowNextPageBtn){
            if(options.hasNext){
                str_next = '<a '+_getHandlerStr(options.next)+' title="'
                    +(options.lang.nextPageTipText || options.lang.nextPageText)+'">'+options.lang.nextPageText+'</a>';
            }else{
                str_next = '<span class="disabled">'+options.lang.nextPageText+'</span>';
            }
        }
        if(options.isShowLastPageBtn){
            if(options.hasNext){
                str_last = '<a '+_getHandlerStr(options.totalPage)+' title="'
                    +(options.lang.lastPageTipText || options.lang.lastPageText)+'">'+options.lang.lastPageText+'</a>';
            }else{
                str_last = '<span class="disabled">'+options.lang.lastPageText+'</span>';
            }
        }
        var str = '';
        var dot = '<li><span>...</span></li>';
        var total_info='';
        if(options.isShowTotalPage || options.isShowTotalRecords){
            total_info = '<span class="normalsize">'+options.lang.totalPageBeforeText;
            if(options.isShowTotalPage){
                total_info +='<span class="totalPage">'+options.totalPage +'</span>'+ options.lang.totalPageAfterText;
                if(options.isShowTotalRecords){
                    total_info += '/';
                }
            }
            if(options.isShowTotalRecords){
                total_info += '<span class="totalRecords">'+options.totalRecords + '</span>' +options.lang.totalRecordsAfterText;
            }

            total_info += '</span>';
        }

        var gopage_info = '';
        if(options.isGoPage){
            gopage_info = ''+
                '<input type="button" id="'+options.gopageButtonId+'" value="'+options.lang.gopageButtonOkText+'" />'+
                '<input type="text" id="'+options.gopageTextboxId+'" value="'+options.next+'" />'+options.lang.gopageAfterText;
        }

        // 分页处理
        if(options.totalPage <= 8){
            for(var i=1;i<=options.totalPage;i++){
                if(options.pageNum == i){
                    str += '<li class="active"><span class="curr">'+i+'</span></li>';
                }else{
                    str += '<li><a '+_getHandlerStr(i)+' title="'
                        +options.lang.buttonTipBeforeText + i + options.lang.buttonTipAfterText+'">'+i+'</a></li>';
                }
            }
        }else{
            if(options.pageNum <= 5){
                for(var i=1;i<=7;i++){
                    if(options.pageNum == i){
                        str += '<li class="active"><span class="curr">'+i+'</span></li>';
                    }else{
                        str += '<li><a '+_getHandlerStr(i)+' title="'+
                            options.lang.buttonTipBeforeText + i + options.lang.buttonTipAfterText+'">'+i+'</a></li>';
                    }
                }
                str += dot;
            }else{
                str += '<li><a '+_getHandlerStr(1)+' title="'
                    +options.lang.buttonTipBeforeText + '1' + options.lang.buttonTipAfterText+'">1</a></li>';
                str += '<li><a '+_getHandlerStr(2)+' title="'
                    +options.lang.buttonTipBeforeText + '2' + options.lang.buttonTipAfterText +'">2</a></li>';
                str += dot;

                var begin = options.pageNum - 2;
                var end = options.pageNum + 2;
                if(end > options.totalPage){
                    end = options.totalPage;
                    begin = end - 4;
                    if(options.pageNum - begin < 2){
                        begin = begin-1;
                    }
                }else if(end + 1 == options.totalPage){
                    end = options.totalPage;
                }
                for(var i=begin;i<=end;i++){
                    if(options.pageNum == i){
                        str += '<li class="active"><span class="curr">'+i+'</span></li>';
                    }else{
                        str += '<li><a '+_getHandlerStr(i)+' title="'
                            +options.lang.buttonTipBeforeText + i + options.lang.buttonTipAfterText+'">'+i+'</a></li>';
                    }
                }
                if(end != options.totalPage){
                    str += dot;
                }
            }
        }

        str = "<ul class='pagination'><li>"+str_first +"</li><li>"+ str_prv + "/<li>"+str + "<li>"+str_next +"</li><li>"+ str_last+"</li><li>" + total_info +"</li><li style='position:relative;'>"+gopage_info+"</li></ul>";
        $("#"+options.pagerId).html(str);

        addHandler();
    }

    // 分页按钮控件初始化
    function init(options){
        // validate
        if(options.pageNum < 1) options.pageNum = 1;
        options.totalPage = (options.totalPage <= 1) ? 1: options.totalPage;
        if(options.pageNum > options.totalPage) options.pageNum = options.totalPage;
        options.prv = (options.pageNum<=2) ? 1 : (options.pageNum-1);
        options.next = (options.pageNum >= options.totalPage-1) ? options.totalPage : (options.pageNum + 1);
        options.hasPrv = (options.pageNum > 1);
        options.hasNext = (options.pageNum < options.totalPage);
        options.inited = true;
    }

    // 不刷新页面直接手动调用选中某一页码
    function selectPage(n){
        options.pageNum = n;
        options.generPageHtml(true);
    }
});