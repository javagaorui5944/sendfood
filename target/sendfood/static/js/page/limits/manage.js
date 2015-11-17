

require.config({
    baseUrl :  BEEN.STATIC_ROOT
});

require(['lib/jquery','modules/baseModule','util/request','modules/bridge'
         ,'modules/dialog/addLimits','util/Headertip','widget/AjaxPager','lib/juicer'],
    function($,baseModule,request,bridge,addLimits,Headertip,ajaxpager){

    var index = {
        init:function(){
            this._getAllLimits();
            this._addlimits();
            this._manageLimits();
        },
        //取得所有所有权限
        _getAllLimits:function(){
            var pager = $(".pagination");
            ajaxpager.init({
                elem:pager,
                pagerUrl:'/limitsManage/getlimitsList',
                current:1,
                handle:function(resp){
                    if(resp.code == 1){
                        var  tpl =  '{@each roles as limit}' +
                            '<tr limits_id="${limit.limits_id}">'+
                            '<td> ${limit.limits_id}</td>'+
                            '<td class="limits_name"> ${limit.limits_name}</td>'+
                            '<td class="limits_note"> ${limit.limits_note}</td>'+
                            '<td class="limits_url"> ${limit.limits_url}</td>'+
                            '<td>'+
                            '{@if limit.limits_status == 10}'+
                            '有效'+
                            '{@else}'+
                            '<strong style="color: red">无效</strong>'+
                            '{@/if}'+
                            '</td>'+
                            '<td data-userid= "${limit.limits_id}" >'+
                            '<a class="btn btn-sm btn-success J-manage" data-action="manage">管理</a> '+
                            '<a class="btn btn-sm btn-success  J-delete" data-id="${limit.limits_id}">冻结</a>'+
                            '</td>'+
                            '</tr>'+
                            '{@/each}';
                        tmp = juicer(tpl,resp.data);
                        $("#J-limitLists").html(tmp);
                    }
                    else{
                        Headertip.error(resp.msg);
                    }
                }
            });
        },
        //冻结权限
        _manageLimits:function(){
            $("#J-limitLists").on("click",".J-manage",function(){
                $(this).removeClass("J-manage").addClass("J-save").html("保存");
                var tr = $(this).parents("tr");
                var limits_name = tr.find(".limits_name").html().trim(),
                    limits_id = tr.attr("limits_id"),
                    limits_note = tr.find(".limits_note").html().trim();
                tr.find(".limits_name").html('<input type="text" class="form-control" value="'+limits_name+'">');
                tr.find(".limits_note").html('<input type="text" class="form-control" value="'+limits_note+'">');
            });
            $("#J-limitLists").on("click",".J-save",function(){
                $(this).removeClass("J-save").addClass("J-manage").html("管理");
                var tr = $(this).parents("tr");
                var limits_name = tr.find(".limits_name > input").val().trim(),
                    limits_id = tr.attr("limits_id"),
                    limits_note = tr.find(".limits_note> input").val().trim();

                tr.find(".limits_name").html(limits_name);
                tr.find(".limits_note").html(limits_note);
                request.post("/limitsManage/updateLimits",{
                    limits_id:limits_id,
                    limits_name:limits_name,
                    limits_note:limits_note
                },function(res){
                    if(1 == res.code){
                        Headertip.success("权限更新成功成功",true,3000);
                    }
                })
            });
        },
        //新增权限
        _addlimits:function(){
            $("#J-addLimits").click(function(){
                var opt ={
                    modalTitle:"新增权限",
                    //初始化事件
                    initFunc:function(){
                        //取得父级别的页面
                    request.post("/limitsManage/getAllLimitsParent",{},function(data){
                        if(0 == data.code){
                            Headertip.error("获取父级页面出错");
                        }else{

                            var tpl ='{@each data as limitparent,index}'+
	                                 '<option value="${limitparent.limits_pid}">${limitparent.limits_pname}</option>'+
	                                '{@/each}';
                            var tmp = juicer(tpl,data);
                            $("#J-parent").append(tmp);
                        }
                    })
            }
                }
                //显示模态框
                addLimits.init(opt);
                addLimits.save(function(){
                    var limitName = $("#J-limitsDialog > .J-limits-name").val().trim(),
                        limitUrl = $("#J-limitsDialog > .J-limits-url").val().trim(),
                        limitNote = $("#J-limitsDialog > .J-limits-note").val().trim(),
                        parentId = $("#J-limitsDialog > .J-parent-url").val();
                });

            });
        }
    };

    index.init();

});