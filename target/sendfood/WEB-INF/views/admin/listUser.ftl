<!--主页面容器开始-->
<div id="main-container">
    <div class="padding-md">
        <!--显示标准订单详情开始-->
        <div class="panel panel-default">
            <div class="panel-heading">
                    某寝室订单详情
            </div>
            <div class="panel-body">
                <span>Total User</span><span class="badge m-left-xs">365</span>
                <span>Approved</span><span class="badge badge-success m-left-xs">321</span>
                <span>Pending</span><span class="badge badge-warning m-left-xs">37</span>
                <span>Banned</span><span class="badge badge-danger m-left-xs">7</span>
            </div>
            <table class="table table-bordered table-condensed table-hover table-striped">
                <thead>
                <tr>
                    <th>名字</th>
                    <th>订单数量</th>
                    <th>删除零食</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>
                        John Doe
                    </td>
                    <td>
                        Oct 08,2013
                    </td>
                    <td>
                        <span class="label label-warning">Pending</span>
                    </td>
                </tr>
                <tr>
                    <td>
                        Jahn Doe
                    </td>
                    <td>
                        Oct 07,2013
                    </td>
                    <td>
                        <span class="label label-warning">Pending</span>
                    </td>
                </tr>
                <tr>
                    <td>
                        Bill Doe
                    </td>
                    <td>
                        29 Sep,2013
                    </td>
                    <td>
                        <span class="label label-success">Approved</span>
                    </td>
                </tr>
                <tr>
                    <td>
                        Kate Doe
                    </td>
                    <td>
                        19 July,2013
                    </td>
                    <td>
                        <span class="label label-success">Approved</span>
                    </td>
                </tr>
                <tr>
                    <td>
                        Jame Doe
                    </td>
                    <td>
                        18 July,2013
                    </td>
                    <td>
                        <span class="label label-danger">Banned</span>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <!--显示标准订单详情结束-->

        <!--添加订单开始-->
        <div class="row">
            <div class="col-md-10">
                <div class="panel panel-default">
                    <div class="panel-heading clearfix">
                        <span class="pull-left">DIY    订单详情</span>
                            <ul class="tool-bar">
                            <li><a href="#" class="refresh-widget" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="Refresh"><i class="fa fa-refresh"></i></a></li>
                            <li><a href="#collapseWidget" data-toggle="collapse"><i class="fa fa-arrows-v"></i></a></li>
                        </ul>
                    </div>
                    <div class="panel-body no-padding collapse in" id="collapseWidget">
                        <div class="padding-md">
                            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam eros nibh, viverra a dui a, gravida varius velit. Nunc vel tempor nisi. Aenean id pellentesque mi, non placerat mi. Integer luctus accumsan tellus. Vivamus quis elit sit amet nibh lacinia suscipit eu quis purus. Vivamus tristique est non ipsum dapibus lacinia sed nec metus.
                        </div>
                    </div>
                    <div class="panel-footer text-right">
                        <button class="btn btn-lg btn-success" type="button">添加订单</button>
                    </div>
                    <div class="loading-overlay">
                        <i class="loading-icon fa fa-refresh fa-spin fa-lg"></i>
                    </div>
                </div>

            </div>

        </div>
        <!--添加订单结束-->

        <!--试写下弹窗呢-->
        <div class="row">
            <div class="col-md-6">
                <div class="panel panel-default">
                    <div class="panel-heading clearfix">
                        <span class="pull-left">请输入添加商品名称</span>
                    </div>
                    <div class="panel-body no-padding collapse in" id="">
                        <div class="padding-md">
                            <form class="form-inline content-padding">
                                <div class="form-group">
                                    <label class="sr-only">Email address</label>
                                    <input type="text" class="form-control input-lg" placeholder="Email Address" id="newsletter">
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="panel-footer">
                        <button class="btn btn-sm btn-success" type="button">确定</button>
                        <button class="btn btn-sm btn-info" type="button">取消</button>
                    </div>
                </div>
            </div>
        </div>
		<a href="http://222.182.98.166:8080/sendfood/getlistbyname?name=可乐">ssss</a>
    </div>
</div>
<!--主页面容器结束-->