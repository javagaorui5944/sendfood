    <content tag="title">物流系统－物流列表</content>
    <content tag="javascript">/admin/business/businessList</content>
  	<div class="padding-md">
						<div class="panel panel-default table-responsive">
					<div class="panel-heading">
						所有的物流列表
						<span class="label label-info pull-right">共50单</span>
					</div>
					<div class="padding-md clearfix">
						<table class="table table-bordered table-striped" id="dataTable">
							<thead>
								<tr>
									<th>寝室号</th>
									<th>订单号</th>
									<th>配送人员</th>
									<th>配送到达时间</th>
									<th>配送人员联系方式</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>25栋814</td>
									<td>＃12345</td>
									<td>张三</td>
									<td>2015.4.4 08:21</td>
									<td>110</td>
									<td>
									<a class="btn btn-default"><i class="fa fa-wrench"></i> 修改</a>
									<a class="btn  btn-default"><i class="fa fa-search-plus"></i>查看</a>
								
									<a class="btn btn-default"><i class="fa fa-envelope-o"></i>发送</a>
									<a class="btn btn-default"><i class="fa  fa-shopping-cart"></i>销售</a>
									</td>
								</tr>
							</tbody>
						</table>
						<div class="panel-footer clearfix">
						<ul class="pagination pagination-xs m-top-none pull-right">
							<li class="disabled"><a href="#">上一页</a></li>
							<li class="active"><a href="#">1</a></li>
							<li><a href="#">2</a></li>
							<li><a href="#">3</a></li>
							<li><a href="#">4</a></li>
							<li><a href="#">5</a></li>
							<li><a href="#">下一页</a></li>
						</ul>
					</div>
					</div><!-- /.padding-md -->
				</div><!-- /panel -->
			</div> 
<div class="modal fade" id="J-Modal">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Modal title</h4>
      </div>
      <div class="modal-body">
    	<form>
    		 <div class="form-group">
                    <label for="inputPassword3" class="col-sm-2 control-label">配送到达时间</label>
                    <div class="col-sm-10">
                        <select class="form-control">
                          <option value="-1">请选择楼栋</option>
                        </select>
                    </div>
                </div>
                 <div class="form-group">
                    <label for="inputPassword3" class="col-sm-2 control-label">配送单内容审核通过</label>
                    <div class="col-sm-10">
                        <select class="form-control">
                          <option value="-1">请选择配送人员</option>
                        </select>
                    </div>
                </div>
               <div class="form-group">
                    <label for="inputPassword3" class="col-sm-2 control-label">配送单内容审核通过</label>
                    <div class="col-sm-10">
                        <select class="form-control">
                          <option value="-1">请选择配送人员</option>
                        </select>
                    </div>
                </div>
    	</div>
      </form>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Save changes</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->