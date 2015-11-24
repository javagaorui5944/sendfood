
    <content tag="title">角色权限管理</content>
    <content tag="css">/limits/manage</content>
    <content tag="javascript">/limits/manageRoleLimits</content>
 <div class="padding-md">
  <div class="panel panel-default">
   <div class="panel-heading">
     给选中的角色配置相关权限
     <br>
     <a class="btn btn-sm btn-danger" id="J-save"><i class="fa fa-plus"></i>保存</a>
   </div>
   <div class="panel-body">
   <div class="col-md-5">
    <table class="table table-bordered table-condensed table-hover table-striped ">
     <thead>
       <tr>
        <th class="text-center">角色名</th>
        <th class="text-center">角色备注</th>
        <th class="text-center">角色状态</th>
       </tr>
     </thead>
     <tbody id="J-roleList">
      <#if roles?? && roles?size gt 0 >
	      <#list roles as role>
	      <tr data-roleId="${role.role_id!'0'}">	
             <td> ${role.role_name!''}</td>
           	  <td> ${role.role_note!''}</td>
        		<td>
              	<#if role.role_status == 10>
              		有效
              	<#else>
              		无效
                </#if>
              </td>
              </tr>
	       </#list>
	    </#if>
     </tbody>
    </table>
    </div>
     <div class="col-md-7">
    <table class="table table-bordered table-condensed table-hover table-striped col-md-6">
     <thead>
       <tr>
         <th><label class="label-checkbox inline">
       	 <input type="checkbox" id="chk-all">
         <span class="custom-checkbox"></span>
    	</label>
       </th>
         <th>权限名字</th>
         <th>权限备注</th>
         <th>权限路径</th>
         <th>权限状态</th>
       </tr>
     </thead>
     <tbody id="J-Limits">
      <#if limits?? && limits?size gt 0 >
			      <#list limits as limit>
			      <tr>
			         <td>
				         <label class="label-checkbox inline">
				         <input type="checkbox" class="chk-item" limitId="${limit.limits_id!''}">
				          <span class="custom-checkbox"></span>
				          </label>
			          </td>
		             <td> ${limit.limits_name!''}</td>
		           	  <td> ${limit.limits_note!''}</td>
		           	  <td>${limit.limits_url!''}</td>
		        		<td>
		              	<#if limit.limits_status == 10>
		              		有效
		              	<#else>
		              		无效
		                </#if>
		              </td>
		              </tr>
			       </#list>
			    </#if>
     </tbody>
    </table>
   </div>
   </div>
   <!--<div class="panel-footer clearfix">
    <ul class="pagination pagination-xs m-top-none pull-right">
     <li class="disabled"><a href="#">Previous</a></li>
     <li class="active"><a href="#">1</a></li>
     <li><a href="#">2</a></li>
     <li><a href="#">3</a></li>
     <li><a href="#">4</a></li>
     <li><a href="#">5</a></li>
     <li><a href="#">Next</a></li>
    </ul>
   </div>-->
  </div>
 </div>
