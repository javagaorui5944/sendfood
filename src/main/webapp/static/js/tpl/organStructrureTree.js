/**
 *
 * 组织架构图的模版
 * author jipeng
 * 初始化数据跟树形下拉菜单一样
 * [{id,pid,name...}]
 * juicer模版引擎
 * */

define(['lib/juicer'],function(){

     var tplInit ={
         tpl :'<ul>',
         deep:3,//树形菜单深度，默认3层
         init : function(trees){
             for(var i = 0;i < trees.length; i++){
                tplInit.treeInit(trees[i],trees);
             }
            return tplInit.tpl+"</ul>";
         },
         initThree:function(trees,toggleClass){
             var temp =' <ul>'+
                          '{@each data as node1,index}'+
                                 '<li>'+
                                     '<a href="javascript:;" class="btn-node" tlevel="${node1.type}"  nodeid="${node1.id}">${node1.name}</a>'+
                                     '{@if node1.children.length > 0}'+
                                     '<ul>'+
                                         '{@each node1.children as node2}'+
                                         '       <li class="bottomNode">'+
                                         '       <a href="javascript:;" class="btn-node" tlevel="${node2.type}" nodeid="${node2.id}">${node2.name}</a>'+
                                                 '{@if node2.children.length > 0}'+
                                                 '       <button class="btn-addtree">+</i></button>'+
                                                 '       <ul class="J-treetaggle" style="display:none">'+
                                                             '{@each node2.children as node3}'+
                                                             '       <li class="J-finalNode" length="${node2.children.length}">'+
                                                             '       <a href="javascript:;"  tlevel="${node3.type}"  class="btn-node"nodeid="${node3.id}">${node3.name}</a>'+
                                                             '       </li>'+
                                                             '{@/each}'+
                                                         '</ul>'+
                                                 '{@/if}'+
                                         '      </li>'+
                                         '{@/each}'+
                                     '</ul>'+
                                     '{@/if}'+
                                 '</li>'+
                           '{@/each}'+
                         '</ul>';

         return juicer(temp,trees);
         },
         initEmployThree:function(trees,toggleClass){
             var temp =' <ul>'+
                 '{@each data as node1,index}'+
                     '<li>'+
                         '<a href="javascript:;" class="btn-node" tlevel="${node1.type}"'+
                         '{@each node1.staff as staff,index1}'+
                            'nodeid="${staff.staff_id}"'+
                          '{@/each}'+
                          '>'+
                          '{@each node1.staff as staff,index1}'+
                           '${staff.staff_name}'+
                         '{@/each}'+
                         '</a>'+
                        '{@if node1.children.length > 0}'+
                         '<ul>'+
                         '{@each node1.children as node2}'+
                              '<li class="bottomNode">'+
                                  '<a href="javascript:;" class="btn-node" tlevel="${node2.type}"'+
                                    '{@each node2.staff as staff2,index2}'+
                                        'nodeid="${staff2.staff_id}"'+
                                     '{@/each}'+
                                    '>'+
                                    '{@each node2.staff as staff2,index2}'+
                                    '${staff2.staff_name}'+
                                    '{@/each}'+
                                   '</a>'+
                                 '{@if node2.children.length > 0}'+
                                 '<button class="btn-addtree">+</button>'+
                                 '<ul class="J-treetaggle" style="display:none">'+
                                     '{@each node2.children as node3}'+
                                     '<li class="J-finalNode" length="${node2.children.length}">'+
                                        '<a href="javascript:;"  tlevel="${node3.type}"'+
                                         '<a href="javascript:;" class="btn-node" tlevel="${node2.type}"'+
                                        '{@each node3.staff as staff3,index3}'+
                                            'nodeid="${staff3.staff_id}"'+
                                         '{@/each}'+
                                        '>'+
                                        '{@each node3.staff as staff3,index3}'+
                                        '${staff3.staff_name}'+
                                        '{@/each}'+
                                       '</a>'+
                                     '</li>'+
                                     '{@/each}'+
                                 ' </ul>'+
                                 '{@/if}'+
                              '</li>'+
                         '{@/each}'+
                         '</ul>'+
                       '{@/if}'+
                     '</li>'+
                 '{@/each}'+
                 '</ul>';

             return juicer(temp,trees);
         },

         //递归完成N层次的树形结构(暂时没有用)
         treeInit:function(node,trees){
             if(!node) return;
             tplInit.deep = tplInit.deep -1;

             var flag = true;
             tplInit.tpl += '<li><a href="javascript:;">'+node.name+'</a><ul>';
             for(var i = 0;i < trees.length; i++){
                 if(trees[i].pId == node.id){
                     flag = false;
                     tplInit.tpl += '<li><a href="javascript:;">'+trees[i].name+'</a>';
                     tplInit.treeInit(trees[i+1],trees);//递归处
                     tplInit.tpl +='</li>';
                 }
             }
             tplInit.tpl += "</ul></li>";
             if(flag){
                 return '';
             }

         }
     }
    return tplInit;
});
