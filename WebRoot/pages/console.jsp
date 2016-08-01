<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
  <head>
    <base href="<%=basePath%>">
    
    <title>POP - 微服务</title>
    
    <meta charset="UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="keywords" content="在线开发,代码托管,应用引擎,开放服务">
	<meta http-equiv="description" content="连接代码托管与应用引擎，为程序员搭建完整的在线编程平台">
	<meta http-equiv="author" content="mass">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type='text/css' href="/static/lib/bootstrap-3.3.5-dist/css/bootstrap.min.css">
	<link rel="stylesheet" type='text/css' href="/static/lib/font-awesome-4.3.0/css/font-awesome.min.css">
  <link rel="stylesheet" type="text/css" href="/static/lib/fileinput/css/fileinput.min.css" media="all"/>
	<link rel="stylesheet" type='text/css' href="/static/custom/css/console.css">
	<link rel="icon" href="/pages/favicon.ico">
	
  </head>
  
<body>
  <div class="navbar navbar navbar-inverse navbar-fixed-top">
    <div class="container">
      <div class="navbar-header">
        <a class="navbar-brand hidden-sm" href="/openService">POP在线编程平台</a>
      </div>
      <div class="navbar-collapse collapse">
        <ul class="nav navbar-nav">
          <li><a href="#create" data-toggle="modal">新建微服务</a></li>
<!--           <li class="dropdown"><a href="#" class="dropdown-toggle"
            data-toggle="dropdown">导入项目<span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><a href="#import-bae" data-toggle="modal">BAE</a></li>
              <li><a href="#">SAE</a></li>
              <li><a href="#">Git仓库</a></li>
            </ul></li> -->
        </ul>
        <ul class="nav navbar-nav navbar-right">
          <li class="dropdown"><a href="#" class="dropdown-toggle"
            id="username" data-toggle="dropdown">${user.username }<span
              class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><a href="#">使用说明</a></li>
              <li><a href="#">常见问题</a></li>
              <li><a href="#">意见反馈</a></li>
              <li class="divider"></li>
              <li><a href="/account">账号设置</a></li>
            </ul></li>
          <li><a href="/logout" id="logout"><i
              class="fa fa-power-off"></i></a></li>
        </ul>
      </div>
    </div>
  </div>

  <div class="col-md-2">
    <div class="selector">
      <ul class="nav nav-pills nav-stacked nav-pills-stacked-example">
        <li class="active"><a href="#">所有项目</a></li>
      
      </ul>
    </div>
  </div>

  <div class="col-md-10 col-md-offset-2">
    <div class="projects"></div>
  </div>

  <!-- 新建项目 -->
  <div class="modal fade" id="create">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">
            <span>&times;</span>
          </button>
          <h4 class="modal-title">新建微服务</h4>
        </div>

        <form class="form-horizontal" id="create-form">
          <div class="modal-body">

            <!-- 微服务名-->
            <div class="form-group">
              <label for="sev-name" class="col-sm-2 control-label">微服务名*</label>
              <div class="col-sm-10">
                <input type="text" id="sev-name" name="sev-name"
                  class="col-sm-10 form-control" placeholder="请输入微服务名称" required
                  autofocus>
              </div>
            </div>

            <!-- 容器类型-->
            <div class="form-group">
              <label for="run-time" class="col-sm-2 control-label">工程环境*</label>
              
              <div class="col-sm-10">
                <select id="run-time" name="run-time" class="form-control"
                  required>
                  <option value="">请选择...</option>
                  <option value="javaweb">Java</option>
                  <option value="php">PHP</option>
                  <option value="python">Python</option>
                  <option value="nodejs">Node.js</option>
                </select>
              </div>
            </div>

            <!-- 简介-->
            <div class="form-group">
              <label for="sev-describe" class="col-sm-2 control-label">简介*</label>
              <div class="col-sm-10">
                <input type="text" id="sev-describe" name="sev-describe"
                  class="col-sm-10 form-control" placeholder="请输入简介" required
                  autofocus>
              </div>
            </div>

            <!-- 服务类型-->
            <div class="form-group">
              <label for="sev-type" class="col-sm-2 control-label">服务类型*</label>
              <div class="col-sm-10">
                <select id="sev-type" name="sev-type" class="form-control"
                  required>
                  <option value="">请选择...</option>
                  <option value="code_ana">代码分析</option>
                  <option value="code_mng">代码管理</option>
                  <option value="compile">编译</option>
                  <option value="model">建模</option>
                  <option value="test">测试</option>
                  <option value="other">其他</option>
                </select>
              </div>
            </div>

            <!-- Email-->
            <div class="form-group">
              <label for="own-email" class="col-sm-2 control-label">Email*</label>
              <div class="col-sm-10">
                <input type="text" id="own-email" name="own-email"
                  class="col-sm-10 form-control" placeholder="请输入工程联系用Email" required
                  autofocus>
              </div>
            </div>
          </div>

          <div class="modal-footer">
            <input name="rst" type="reset" hidden>
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            <button type="submit" class="btn btn-primary"
              data-loading-text="创建中，请稍等..." id="create-btn">保存</button>
          </div>
        </form>
      </div>
    </div>
  </div>

  <!-- 查看详细 -->
  <div class="modal fade" id="detailsModal" tabindex="-1">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">
            <span>&times;</span>
          </button>
          <h4 class="modal-title">查看详细</h4>
        </div>
        <form class="form-horizontal">
          <div class="modal-body">

            <div class="form-group">
              <label for="sevName" class="col-sm-3 control-label">项目名称</label>
              <div class="col-sm-8">
                <p class="form-control-static" id="sevName"></p>
              </div>
            </div>

            <div class="form-group">
              <label for="runtime" class="col-sm-3 control-label">项目类型</label>
              <div class="col-sm-8">
                <p class="form-control-static" id="runtime"></p>
              </div>
            </div>

            <div class="form-group">
              <label for="sevOwner" class="col-sm-3 control-label">项目创建人</label>
              <div class="col-sm-8">
                <p class="form-control-static" id="sevOwner"></p>
              </div>
            </div>
            
            <div class="form-group">
              <label for="address" class="col-sm-3 control-label">服务地址</label>
              <div class="col-sm-8">
                <p class="form-control-static" id="address"></p>
              </div>
            </div>

            <div class="form-group">
              <label for="ownEmail" class="col-sm-3 control-label">联系方式</label>
              <div class="col-sm-8">
                <p class="form-control-static" id="ownEmail"></p>
              </div>
            </div>

            <div class="form-group">
              <label for="paasName" class="col-sm-3 control-label">PaaS平台</label>
              <div class="col-sm-8" id="paasName"></div>
            </div>

            <div class="form-group">
              <label for="importUrl" class="col-sm-3 control-label">代码仓库</label>
              <div class="col-sm-8">
                <p class="form-control-static" id="importUrl"></p>
              </div>
            </div>

            <div class="form-group">
              <label for="date" class="col-sm-3 control-label">创建日期</label>
              <div class="col-sm-8">
                <p class="form-control-static" id="date"></p>
              </div>
            </div>
          </div>

          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
          </div>
        </form>
      </div>
    </div>
  </div>

  <!-- 上传 -->
  <div class="modal fade" id="uploadModal">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">
            <span>&times;</span>
          </button>
          <h4 class="modal-title">提交代码包</h4>
        </div>

        <form class="form-horizontal" id="upload-form">
          <div class="modal-body">

            <div class="form-group">
              <div class="col-sm-10">
                <input type="hidden" id="upload-sev-name" name="servicename" value=""
                class="col-sm-10 form-control">
                <input type="hidden" id="upload-sev-type" name="serviceContainerType" value=""
                class="col-sm-10 form-control">
              </div>
            </div>

            <!-- 代码包-->
            <div class="form-group">
              <label for="sev-file" class="col-sm-2 control-label">代码包*</label>
              <div class="col-sm-10">
                <input type="file" id="sev-file" name="sev-file[]"
                  class="col-sm-10 form-control"  
                  multiple>
              </div>
            </div>
            <div id = "deploytips" class="collapse" style="color:red">
            部署中,请稍候...(大约需要一分钟)
             </div>
           
          </div>

        </form>
      </div>
    </div>
  </div>
  
  <!-- 上传UI-->
  <div class="modal fade" id="uploadUIModal">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">
            <span>&times;</span>
          </button>
          <h4 class="modal-title">上传js代码</h4>
        </div>

        <form class="form-horizontal" id="upload-form" enctype="multipart/form-data" method="POST" action="/openService?action=uploadUI">
          <div class="modal-body">

            <div class="form-group">
              <div class="col-sm-10">
                <input type="hidden" id="uploadui-sev-name" name="servicename" value=""
                class="col-sm-10 form-control">
              </div>
            </div>

            <!-- 代码包-->
            <div class="form-group">
              <label for="ui-file" class="col-sm-2 control-label">js文件*</label>
              <div class="col-sm-10">
                <input type="file" id="ui-file" name="ui-file[]"
                  class="col-sm-10 form-control"  required
                  multiple>
              </div>
            </div>
          </div>

        </form>
      </div>
    </div>
  </div>

  <!-- 部署 -->
  <div class="modal fade" id="deploysModal" tabindex="-1">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">
            <span>&times;</span>
          </button>
          <h4 class="modal-title">提交</h4>
        </div>
        <form class="form-horizontal">
          <div class="modal-body">
            <p class="form-control-static">确定要提交吗?我们会进行审核</p>
            <p id="deploy-json"></p>
          </div>
          <input type="hidden" id="deploy-sev-name" name="deploy-sev-name" value=""
                class="col-sm-10 form-control">
          <div class="modal-footer">
            <button type="submit" class="btn btn-primary">确认</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
          </div>
        </form>
      </div>
    </div>
  </div>

  <!-- 删除项目 -->
  <div class="modal fade" id="delete">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">
            <span>&times;</span>
          </button>
          <h4 class="modal-title">删除项目</h4>
        </div>
        <div class="modal-body">项目删除后，将不可恢复，请确认操作。</div>
        <div class="modal-footer">
          <input name="rst" type="reset" hidden>
          <button type="button" class="btn btn-default" data-dismiss="modal">点错了</button>
          <button type="submit" class="btn btn-danger" data-loading-text="创建中，请稍等..." id="delete-btn">确认删除</button>
        </div>
      </div>
    </div>
  </div>

  <script src="/static/lib/jquery/jquery-1.9.1.min.js"></script>
  <script src="/static/lib/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
  <script src="/static/lib/jquery/jquery.cookie.js"></script>
  <script src="/static/lib/fileinput/js/plugins/canvas-to-blob.min.js"></script>
  <script src="/static/lib/fileinput/js/fileinput.min.js"></script>
  <script src="/static/lib/fileinput/js/locales/zh.js"></script>
  <script src="/static/custom/js/console.js"></script>
</body>
</html>
