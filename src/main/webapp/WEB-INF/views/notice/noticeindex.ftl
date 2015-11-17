    <content tag="title">管理公告栏</content>
    <content tag="css">/noticeManage/manageStandard</content>
    <content tag="javascript">/noticeManage/manageStandard</content>
    <div class="padding-md">
    <div class="row">
    <div class="panel panel-default table-responsive">
					<div class="panel-heading">
						公告管理						
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-md-6 col-sm-6">
								<select class="input-sm form-control inline" id="J-orderOrPerseon" style="width:100px;">
									<option value="0">所有学校</option>
									<option value="1">区县代码</option>
								</select>
								
								<input type="text" class="input-sm form-control inline" style="width:130px;"  id = "J-value" placeholder="请输入订单关键字"/>
								<a class="btn btn-default btn-sm" id="J-search" >搜索</a>
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div>
					<table class="table table-striped" id="responsiveTable">
						<thead>
							<tr>
								<th>
									序号
								</th>
								<th>区号代码</th>
								<th>学校名</th>
								<th>学校代码</th>
								<th>学校id</th>
								<th>学校状态</th>
								<th>相关公告</th>
								
							</tr>
						</thead>
						<tbody id="J-orderList">
								
						</tbody>
					</table>
					<div class="panel-footer clearfix">
						<div id="pager">
						</div>
					</div>
				</div>
    </div>
    </div>