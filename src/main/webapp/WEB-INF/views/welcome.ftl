    <content tag="title">零食预售系统－登录</content>
    <content tag="css">/welcome</content>
    <content tag="javascript">/welcome</content>
   
	<div class="login-bg">
        <img src="/static/image/bg/login-bg.jpg" class="login-bg"/>
    </div>
	<div class="container" id="container">
    <div class="row">
        <div class="col-md-5">
            <form class="form-horizontal" method="post" action="/guest/placeAnOrder" id="J-submit">
                <div class="form-group">
                    <div class="page-header">
                        <h1 style="padding-left:10px">零食321,期待你的加入</h1>
                    </div>
                </div>
              
                <div class="form-group">
                    <label for="inputPassword3" class="col-sm-2 control-label">楼栋</label>
                    <div class="col-sm-5">
                        <select class="form-control">
                          <option value="-1">请选择楼栋</option>
                        </select>
                    </div>
                    <div class="col-sm-5">
                        <select class="form-control">
                          <option value="-1">请选择寝室号</option>
                        </select>
                    </div>
                </div>
                  <div class="form-group">
                    <label for="inputEmail3" class="col-sm-2 control-label">用户名</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="inputEmail3" placeholder="用户名">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputPassword3" class="col-sm-2 control-label">联系电话</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="inputPassword3" placeholder="联系电话">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputPassword3" class="col-sm-2 control-label">DIY订单</label>
                    <div class="col-sm-10">
                    <div class="panel panel-default diy-panel">
	                    <#if diyFoods?? && diyFoods?size gt 0 >
	                        <#list diyFoods as diyFood>
	                            <label class="checkbox-inline">
	                                <input type="checkbox" id="inlineCheckbox1" value="${diyFood.goodsincid!'-1'}"> ${diyFood.goodsname!''}
	                            </label>
	                        </#list>
	                    </#if>
                    </div>
                    </div>
                </div>
                 <div class="form-group">
                    <label for="inputPassword3" class="col-sm-2 control-label">备注</label>
                    <div class="col-sm-10">
                       <textarea class="form-control" placeholder="如:我们寝室希望能多点低脂牛奶，另外希望换个大点得箱子"></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default" >点击预定</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>