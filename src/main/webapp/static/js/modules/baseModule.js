/**
 *  jipeng
 *  2015.05.26
 *  所有页面必须引入
 * **/
require.config({
    baseUrl :  BEEN.STATIC_ROOT
});
define(['lib/basic/bootstrap','modules/template/endless/endless','util/request','lib/juicer'],function(bs,en,request){
   var base = {
        init :function(){
            this._getAllLimitsPageByUser();
        },
         //取得用户对应的权限
        _getAllLimitsPageByUser:function(){
            request.post("/userManage/getStuffLimitPageByStuffId",{},function(ret){
                if(1 == ret.code){
                	 var limitParent = [];
                     var limitpage = [];
                       var data = ret;
                         for(var i  = 0; i < data.data.length; i++){
                             limitParent.push({
                                 pid:data.data[i].limits_pid,
                                 pname:data.data[i].limits_pname,
                                 childLimit:[]
                             });
                         }
                         limitpage = base.arrayUnique(limitParent);
                         for(var i  = 0; i < limitpage.length; i++) {
                             for (var j = 0; j < data.data.length; j++) {
                                 if (data.data[j].limits_pid == limitpage[i].pid) {
                                     limitpage[i].childLimit.push(data.data[j]);
                                 }
                             }
                         }
                    data.data = limitpage;
                    var url = $("#J-pages").attr("data-url");//当前页面的url
                    var tpl =  '{@each data as Item,index}'+
                                 ' <li class="openable">'+
                                  '   <a href="javascript:;" class="J-btn-toggle">'+
                                  '     <span class="menu-icon">'+
                                 '       <i class="fa fa-tag fa-lg"></i>'+
                                 '       </span>'+
                                 '       <span class="text">'+
                                 '       <%=Item.pname%>'+
                                 '        </span>'+
                                 '        <span class="menu-hover"></span>'+
                                 '     </a>'+
                                 '     <ul class="submenu"${Item.childLimit|display_bulid}>'+
                                      '{@each Item.childLimit as Item1}'+
                                       '<li><a href="<%=Item1.limits_url%>" ${Item1|links_build}><span class="submenu-label"><%=Item1.limits_name%></span></a></li>'+
                                      '{@/each}'+
                                 '     </ul>'+
                                 '   </li>'+
                                 '{@/each}';
                    
                    var links = function(data) {
                    	if(data.limits_url == url){
                    		return 'class=active';
                    	}else{
                    		return "";
                    	}
                    };
                    
                    var display = function(data){
                    	for(var i = 0; i < data.length; i++){
                    		if(data[i].limits_url == url){
                        		return 'style=display:block';
                        	}
                    	}
                    };
                    juicer.register('display_bulid', display); 
                    juicer.register('links_build', links); 
                    var temp = juicer(tpl,data);
                    $("#J-pages").append(temp);
                     $(".J-btn-toggle").click(function(){
                         var sbmenu = $(this).parent().find(".submenu").toggle();
                     });

                }

            });
        },
        arrayUnique:function(arr) {
            function has(array,data){
                for(var k = 0; k < array.length;k++){
                    if(data == array[k].pid){
                        return true;
                    }
                }
                return false;
            }
            var tmp = [];
            tmp.push(arr[0]);
            for(var i = 1; i < arr.length; i++){
                if(has(tmp,arr[i].pid) == false){
                    tmp.push(arr[i]);
                }
            }
            return tmp;
        }
   };
    base.init();
});