/**
 *  基于bootstrap的下拉筛选器
 *  author:jipeng@meituan.com
 *  2015-03-30
 * */
define(['lib/jquery','lib/jqueryPlugs/bootstrap.autocomplete','modules/bridge'],function($,au,bridge){
    jQuery.fn.selectEmploy = function(callback) {
        var self = this;
        var oldValue;
        var dataObject;
        var muti = $(this).attr("muti");//是否是多选
        $(this).attr('autocomplete','off');
        $(this).autocomplete({
            items: 6,
            source: function(query, process){
                if(query=='' || query==null){
                    dataObject = eval('([])');
                    return dataObject;
                }

                if($.trim(query).length < 1){
                    dataObject = eval('([])');
                    return dataObject;
                }

                oldValue = $.trim(query);
                $.ajax({
                    url:"/userManage/getStuffsBySearchName?timestamp=" + new Date().valueOf(),
                    data:{
                        'staff_name': $(self).val().trim()   
                    },
                    type:"post",
                    async:false,
                    success:function(data,status){
                        if (1 == data['code']){
                            dataObject = data.data;
                        }
                    }
                });
                return dataObject;
            },
            formatItem:function(item){
                return item["staff_name"];
            },
            muti:muti,
            setValue:function(item){
                return {'data-value':item["staff_name"],'real-value':item["staff_id"]};
            },
            callback:function(real_value){
            	//根据选中的id再来选中
            	callback(real_value);
            }
        });
    };
    jQuery.fn.searchFoodByFoodName = function(school_id,callback) {
        var self = this;
        var oldValue;
        var dataObject;
        var muti = $(this).attr("muti");//是否是多选
        $(this).attr('autocomplete','off');
        $(this).autocomplete({
            items: 6,
            source: function(query, process){
                if(query=='' || query==null){
                    dataObject = eval('([])');
                    return dataObject;
                }

                if($.trim(query).length < 1){
                    dataObject = eval('([])');
                    return dataObject;
                }

                oldValue = $.trim(query);
                $.ajax({
                    url:"/OrderItem/getAllSnacksByName?timestamp=" + new Date().valueOf(),
                    data:{
                        'snacks_name': $(self).val().trim(),
                        'school_id':school_id
                    },
                    type:"post",
                    async:false,
                    success:function(data,status){
                        if (1 == data['code']){
                            dataObject = data.data;
                            bridge.register("foodObject",dataObject);
                        }
                    }
                });
                return dataObject;
            },
            formatItem:function(item){
                return item["snacks_name"];
            },
            muti:muti,
            setValue:function(item){
                return {'data-value':item["snacks_name"],'real-value':item["snacks_id"]};
            },
            callback:function(real_value){
                //根据选中的id再来选中
                callback(real_value);
            }
        });
    };


    jQuery.fn.selectEmployByRank = function(callback) {
        var self = this;
        var oldValue;
        var dataObject;
        var muti = $(this).attr("muti");//是否是多选
        $(this).attr('autocomplete','off');
        $(this).autocomplete({
            items: 6,
            source: function(query, process){
                if(query=='' || query==null){
                    dataObject = eval('([])');
                    return dataObject;
                }

                if($.trim(query).length < 1){
                    dataObject = eval('([])');
                    return dataObject;
                }

                oldValue = $.trim(query);
                $.ajax({
                    url:"/organizationManage/listAllstaff?timestamp=" + new Date().valueOf(),
                    data:{
                        'staff_name': $(self).val().trim()   
                    },
                    type:"post",
                    async:false,
                    success:function(data,status){
                        if (1 == data['code']){
                            dataObject = data.data;
                        }
                    }
                });
                return dataObject;
            },
            formatItem:function(item){
                return item["staff_name"];
            },
            muti:muti,
            setValue:function(item){
                return {'data-value':item["staff_name"],'real-value':item["staff_id"]};
            },
            callback:function(real_value){
            	//根据选中的id再来选中
            	callback(real_value);
            }
        });
    }




});