/**
 * Created by w2qiao on 2015/7/14.
 */

imgbase = '../static/custom/img/console/';
url = 'http://123.57.2.1:8000/';

sevlist = [];
sevstatus = [];
username = $("#username").text().trim();
delsev = "";

(function() {
	$.get('/openService', {
		action : 'services'
	}, function(data) {
		sevlist = $.parseJSON(data);
		var sev_panel = $('.projects');
		for (i in sevlist) {
			var panel = $('<div class="panel panel-default"/>');
			appendSev(panel, sevlist[i]);
			sev_panel.append(panel);
		}
	});
}());

(function() {
	$.get('/openService', {
		action : 'filestatus'
	}, function(data) {
		sevstatus = $.parseJSON(data);
		
	});
}());

$(function() {
	$('[data-toggle="tooltip"]').tooltip();
	$("#create-form").on('submit', function(e) {
		e.preventDefault();
		console.log($(this).serialize());
		var btn = $("#create-btn").button('loading');
		var sevname = $("#sev-name").val().trim();
		var runtime = $("#run-time").val().trim();
		var sevdesc = $("#sev-describe").val().trim();
		var sevtype = $("#sev-type").val().trim();
		var ownemail = $("#own-email").val().trim();
		$.post('/openService', {
			action : 'create',
			servicename : sevname,
			runtime : runtime,
			servicetype : sevtype,
			description : sevdesc,
			developer : username,
			qq : "123",
			wechat : ownemail,
			phone : "123"
		}, function(data, status) {
			console.log(data, status);
			var sev = $.parseJSON(data);
			sevlist.push(sev);
			$("#create").modal('hide');
			btn.button('reset');
			$("input[name='rst']").click();
			var parent = $("<div class='panel panel-default'></div>");
			appendSev(parent, sev);
			$(".projects").prepend(parent);
			var modal = $("#uploadModal");
			modal.find('#upload-sev-name').val(sev.serviceName);
			modal.find('#upload-sev-type').val(sev.serviceContainerType);
			$("#uploadModal").modal("show");
		});
		$('[data-toggle="tooltip"]').tooltip();
	});

	$("#addeveloper-form").on('submit', function(e) {
		e.preventDefault();
		var _this = $("#addeveloper-form");
		var developer_name = _this.find("#developer-name").val();
		var rights = _this.find($('input:radio:checked')).val();
		var sevname = $("#sevname-share").text();
		$.post('/openService', {
			action : 'addeveloper',
			username : username,
			developer_name : developer_name,
			rights : rights,
			sevname : sevname,
		}, function(data, status) {
			$("input[name='rst']").click();
			var sev = $.parseJSON(data);
			$("#addeveloper-error").text("");
			refreshDeveloper(sev.ownerName, sev.serviceName, developer_name);
		});
	});

	$("#developer-name").on('blur', function(e) {
		var developer_name = $(this).val();
		var sevname = $("#sevname-share").text();
		if (developer_name != "") {
			$.post('/openService', {
				action : 'checkDeveloper',
				username : username,
				developer_name : developer_name,
				sevname : sevname,
			}, function(data, status) {
				if (data != "success") {
					disform($("#developer-name"));
					$("#addeveloper-error").text(data);
				} else {
					enform($("#developer-name"));
					$("#addeveloper-error").text('');
				}
			});
		}
	});

	$("#delete").find('button[type="submit"]').on(
			'click',
			function() {
				console.log("delete");
				var btn = $("#delete-btn").button('loading');
				var _this = $(".projects").find(
						'.panel-heading a[data-sevname="' + delsev + '"] ')
						.parents('.panel');
				$.post("/openService", {
					action : 'delete',
					servicename : delsev,
					username : username
				}, function(data, status) {
					if (status == 'success') {
						btn.button('reset');
						_this.remove();
						sevlist.splice(sevIndex(delsev), 1);
						$("#delete").modal('hide');
					}
				});
			});

	$(document).on(
			'click',
			".close[data-toggle='modal']",
			function() {
				delsev = $(this).parents('.panel').find(
						'.panel-heading a[data-toggle="tooltip"]').text()
						.trim();
			});

	$(".selector").children().on('click', function(event) {
		var target = $(event.target);
		var children = $(this).find(".active").removeClass("active");
		$(target).parent().addClass("active");
		var nav_text = target.text();
		if (nav_text != null) {
			switch (nav_text) {
			case "BAE":
				showSev("bae");
				break;
			case "SAE":
				showSev("sae");
				break;
			case "Git仓库":
				showGitSev();
				break;
			default:
				showAllSev();
			}
		}
		$('[data-toggle="tooltip"]').tooltip();
	});
	$('#detailsModal').on(
			'show.bs.modal',
			function(event) {
				var button = $(event.relatedTarget); // Button that triggered
				// the modal
				var sevname = button.data('sevname');
				var sev = sevlist[sevIndex(sevname)];
				// If necessary, you could initiate an AJAX request here (and
				// then do the updating in a callback).
				// Update the modal's content. We'll use jQuery here, but you
				// could use a data binding library or other methods instead.
				var modal = $(this);
				modal.find('.modal-body #sevName').text(sevname);
				modal.find('.modal-body #runtime').text(
						sev.serviceContainerType);
				modal.find('.modal-body #address').text(
						sev.pluginAddress);
				modal.find('.modal-body #sevOwner').text(sev.ownerName);
				modal.find('.modal-body #ownEmail').text(sev.ownerWechat);
				modal.find('.modal-body #write').text(
						sev.write == true ? "读写" : "只读");
				modal.find('.modal-body #date').text(new Date(sev.createDate));
				var paas = modal.find('.modal-body #paasName');
				if (sev.paasName == null || sev.paasName == "") {
					paas.parents('.form-group').css('display', 'none');
				} else {
					paas.parents('.form-group').css('display', 'inherit');
					paas.parents('.form-group').find('p').remove();
					paas.append($('<p class="form-control-static"></p>').text(
							'平台名称：  ' + sev.paasName));
					if (sev.gitUrl != null && sev.gitUrl != "") {
						paas.append($('<p class="form-control-static"></p>')
								.text('平台Git地址：  ' + sev.gitUrl));
					} else if (sev.svnUrl != null && sev.svnUrl != "") {
						paas.append($('<p class="form-control-static"></p>')
								.text('平台SVN地址：  ' + sev.svnUrl));
					}
				}
				var importUrl = modal.find('.modal-body #importUrl');
				if (sev.importUrl == null || sev.importUrl == "") {
					importUrl.parents('.form-group').css('display', 'none');
				} else {
					importUrl.parents('.form-group').css('display', 'inherit');
					importUrl.text(sev.importUrl);
				}
			});
	
	$('#uploadModal').on(
			'show.bs.modal',
			function(event) {
				var button = $(event.relatedTarget); // Button that triggered
				// the modal
				var sevname = button.data('sevname');
				var sevtype = button.data('sevtype');
				// If necessary, you could initiate an AJAX request here (and
				// then do the updating in a callback).
				// Update the modal's content. We'll use jQuery here, but you
				// could use a data binding library or other methods instead.
				var modal = $(this);
				if(sevname!=null)
				modal.find('#upload-sev-name').val(sevname);
				if(sevtype!=null)
				modal.find('#upload-sev-type').val(sevtype);
				
				$('#sev-file').fileinput('destroy');
				$('#sev-file').fileinput({
				    language: 'zh',
				    uploadExtraData: { servicename: modal.find('#upload-sev-name').val(),serviceContainerType:modal.find('#upload-sev-type').val() },
				    uploadUrl: '/openService?action=upload',
				    enctype: 'multipart/form-data',
				    allowedFileExtensions : ['zip','war'],
				    showPreview: false,
				    slugCallback: function(filename) {
				    	
				    	
				            return filename.replace('(', '_').replace(']', '_');
				            
				    }
				});
				$("#sev-file").on("filepreupload", function(event, data, previewId, index) {
					$("#deploytips").show();
			       
				});
				$("#sev-file").on("fileuploaded", function(event, data, previewId, index) {
					$("#uploadModal").modal("hide");
			        	 //alert(data.response);
					$("#deploytips").hide();
			             var res = data.response;
			             var modal = $("#uploadUIModal");
			 			modal.find('#uploadui-sev-name').val(sevname);
			 			//modal.find('#uploadui-sev-type').val(sev.serviceContainerType);
			 			$("#uploadUIModal").modal("show");
			     		alert('访问地址:'+res.domain+':'+res.port+'\n'+
			     			'ssh地址:'+res.domain+':'+res.sshport);
			       
				});
				
				
		
				
				
				
			});
	
	
	
	$('#uploadUIModal').on(
			'show.bs.modal',
			function(event) {
				var button = $(event.relatedTarget); // Button that triggered
				// the modal
				var sevname = button.data('sevname');
				// If necessary, you could initiate an AJAX request here (and
				// then do the updating in a callback).
				// Update the modal's content. We'll use jQuery here, but you
				// could use a data binding library or other methods instead.
				var modal = $(this);
				if(sevname!=null)
				modal.find('#uploadui-sev-name').val(sevname);
				$('#ui-file').fileinput('destroy');
				$('#ui-file').fileinput({
				    language: 'zh',
				    uploadExtraData: { servicename: modal.find('#uploadui-sev-name').val() },
				    uploadUrl: '/openService?action=uploadUI',
				    enctype: 'multipart/form-data',
				    allowedFileExtensions : ['js'],
				    showPreview: false,
				    slugCallback: function(filename) {
				    	
				            return filename.replace('(', '_').replace(']', '_');
				            
				    }
				});
				$("#ui-file").on("fileuploaded", function(event, data, previewId, index) {
					$("#uploadUIModal").modal("hide");
			        	 //alert(data.response);
			             
			             var res = data.response;
			             if(res.success){
			     		    alert('上传成功');
			     		   sevstatus[sevIndex(sev.serviceName)].jsStatus=true;
			     		  //location.replace(location);
			             }
			             else
			            	alert('上传失败');
			       
				});
				
				
			});
	
	$('#deploysModal').on(
			'show.bs.modal',
			function(event) {
				var button = $(event.relatedTarget); // Button that triggered
				// the modal
				var sevname = button.data('sevname');
				// If necessary, you could initiate an AJAX request here (and
				// then do the updating in a callback).
				// Update the modal's content. We'll use jQuery here, but you
				// could use a data binding library or other methods instead.
				var modal = $(this);
				modal.find('#deploy-sev-name').val(sevname);
			});



	$('#deploysModal').on('submit', function(event) {
	   
		var sevname = $("#deploy-sev-name").val().trim();
		var sev = sevlist[sevIndex(sevname)];
		$.post('/openService', {
			action : 'deploy',
			servicename : sevname,
			serviceContainerType : sev.serviceContainerType
		}, function(data, status) {
			if (status == 'success') {
				var res = $.parseJSON(data);
				var deploys_p = $('#deploysModal').find('.modal-body #deploy-json');
				deploys_p.append(res);
				alert('访问地址:'+res.domain+':'+res.port+'\n'+
					'ssh地址:'+res.domain+':'+res.sshport);
			}
			
		});
		return false;
	});

	$('#sharesModal').on('show.bs.modal', function(event) {
		var button = $(event.relatedTarget); // Button that triggered the
		// modal
		var sevname = button.data('sevname');
		var modal = $(this);
		modal.find('#sevname-share').text(sevname);
		var username = $("#username").text().trim();
		refreshDeveloper(username, sevname);
	});
});


// function appendSev(parent, sevname, runtime, username, ownername, paasname,
// date, write, importUrl){
function appendSev(parent, sev) {
	var token = $.cookie("token");
	// token = "ce8745653745341ed642b1878ad60bb2";
	var heading_a = $('<a></a>');
	heading_a.attr({
		"href" : url + '?token=' + token + '&sevname=' + sev.serviceName,
		"data-toggle" : "tooltip",
		"title" : "点击进入开发界面",
		"data-sevname" : sev.serviceName
	});
	heading_a.text(sev.serviceName);
	var heading_close = $('<a></a>');
	heading_close.attr({
		"class" : "close",
		"data-toggle" : "modal",
		"title" : "删除项目",
		"href" : "#delete",
	});
	var statusstr = "";
	if(sev.status==0)
		statusstr = "(待审核)";
	else if(sev.status==1)
		statusstr = "(审核通过)";
	else if(sev.status==2)
		statusstr = "(审核未通过)";
	var heading_status = $('<font></font>');
	heading_status.attr({
		"color":"red",
	});
	heading_status.text(statusstr);
	var heading = $('<div class="panel-heading"></div>').append(heading_a,heading_status,
			heading_close.append("<span>&times;</span>"));

	var body = $('<div class="panel-body"></div>');
	var content_img = $('<div class="content-img"></div>');
	var content_img_ = $('<img>');
	var img_src = "http://placehold.it/94x112";
	if (sev.paasName != null && sev.importUrl != null) {
		img_src = imgbase + sev.serviceContainerType
				+ (sev.paasName != "" ? "-" + sev.paasName : "")
				+ (sev.importUrl != "" ? "-git" : "") + "-sm.png";
	} else {
		img_src = imgbase + sev.serviceContainerType + "-sm.png";
	}
	content_img_.attr({
		"src" : img_src,
	    "width": "65px",
	    "height": "75px"
	});

	var content_info = $('<div class="content-info"></div>');
	var info_date = $('<p></p>');
	date = new Date(sev.createDate);
	info_date.text("创建于 " + date.getFullYear() + "-" + (date.getMonth() + 1)
			+ '-' + date.getDate());
	var info_details = $('<p></p>');
	var info_details_a = $('<a></a>');
	info_details_a.attr({
		"href" : "#detailsModal",
		"data-toggle" : "modal",
		"data-sevname" : sev.serviceName,
	});
	info_details_a
			.html('<span class="glyphicon glyphicon-search"></span>&nbsp;查看详细');
	info_details.append(info_details_a);

	var info_uploads = $('<p></p>');
	var info_uploads_a = $('<a></a>');
	info_uploads_a.attr({
		"href" : "#uploadModal",
		"data-toggle" : "modal",
		"data-sevname" : sev.serviceName,
		"data-sevtype" : sev.serviceContainerType,
	});
	info_uploads_a
			.html('<span class="glyphicon glyphicon-arrow-up"></span>&nbsp;提交代码包');
	info_uploads_img = $('<img>');
	info_uploads_img.attr({
		"src" : imgbase+"check.png",
		"height":"20px", 
		"width":"20px" 
	});
	info_uploads.append(info_uploads_a);
	if(sevstatus[sevIndex(sev.serviceName)].serverStatus == true)
		info_uploads.append(info_uploads_img);
	var info_uploadui = $('<p></p>');
	var info_uploadui_a = $('<a></a>');
	info_uploadui_a.attr({
		"href" : "#uploadUIModal",
		"data-toggle" : "modal",
		"data-sevname" : sev.serviceName,
		"data-sevtype" : sev.serviceContainerType,
	});
	info_uploadui_a
			.html('<span class="glyphicon glyphicon-arrow-up"></span>&nbsp;上传js代码');
	info_uploadui_img = $('<img>');
	info_uploadui_img.attr({
		"src" : imgbase+"check.png",
		"height":"20px", 
		"width":"20px" 
	});
	
	info_uploadui.append(info_uploadui_a);
	if(sevstatus[sevIndex(sev.serviceName)].jsStatus == true)
		info_uploadui.append(info_uploadui_img);

	var info_deploys = $('<p></p>');
	var info_deploys_a = $('<a></a>');
	info_deploys_a.attr({
		"href" : "#deploysModal",
		"data-toggle" : "modal",
		"data-sevname" : sev.serviceName,
	});
	info_deploys_a
			.html('<span class="glyphicon glyphicon-export"></span>&nbsp;提交');
	info_deploys.append(info_deploys_a);
	
	var info_terminal = $('<p></p>');
	var info_terminal_a = $('<a></a>');
	info_terminal_a.click(function openTerminal(){
		$.get('/openService', {
			action : 'dockerid',
			servicename : sev.serviceName
		}, function(data) {
			var res = $.parseJSON(data);
			var dockerid = res.dockerid;
			$.post('http://123.57.2.1:9621/startTerminal', {
				dockerid : dockerid
			}, function(data) {
				var res = $.parseJSON(data);
				
				window.open(res.url);
			});
		});
			
		}
	);
	info_terminal_a
			.html('<span class="glyphicon glyphicon-export"></span>&nbsp;Terminal');
	info_terminal.append(info_terminal_a);

	var info_rights = $('<p></p>');
	if (sev.ownerName == sev.userName) {
		var share = $('<a></a>');
		share.attr({
			"href" : "#sharesModal",
			"data-toggle" : "modal",
			"data-sevname" : sev.serviceName
		});
		info_rights.append(share.append($('<i class="fa fa-share-alt"></i>'),
				'&nbsp;合作开发'));
	}

	body.append(content_img.append(content_img_), content_info.append(
			info_date, info_rights, info_details, info_uploads,info_uploadui,info_terminal));
	parent.append(heading, body);
}

function refreshDeveloper(ownerName, sevname) {
	$
			.get(
					'/openService',
					{
						action : 'developers',
						ownerName : ownerName,
						sevname : sevname
					},
					function(data, status) {
						console.log(data);
						var sevs = $.parseJSON(data);
						var developers_panel = $('#sharesModal').find(
								".modal-body #developers-panel");
						var developers = developers_panel.find("table");
						if (sevs != null) {
							developers.children().remove();
							developers
									.append($('<tr><th>用户名</th><th>权限</th><th>操作</th></tr>'));
							for (var i = 0; i < sevs.length; i++) {
								var sev = sevs[i];
								var row = $('<tr><td>'
										+ sev.userName
										+ '</td><td>'
										+ (sev.write == true ? "读写" : "只读")
										+ '</td><td><a href="#">删除</a></td></tr>');
								developers.append(row);
							}

							developers_panel.css('display', 'inherit');
						} else {
							developers_panel.css('display', 'none');
						}
					});
}

function sevIndex(sevname) {
	for (var i = 0; i < sevlist.length; i++) {
		var sev = sevlist[i];
		if (sev.serviceName == sevname) {
			return i;
		}
	}
	return sevlist.length;
}

function showSev(type) {
	var projects = $(".projects");
	projects.children().remove();
	for (var i = 0; i < sevlist.length; i++) {
		var sev = sevlist[i];
		if (sev.paasName == type) {
			var parent = $("<div class='panel panel-default'></div>");
			appendSev(parent, sev);
			projects.append(parent);
		}
	}
}

function showGitSev() {
	var projects = $(".projects");
	projects.children().remove();
	for (var i = 0; i < sevlist.length; i++) {
		var sev = sevlist[i];
		if (sev.importUrl != null && sev.importUrl != "") {
			var parent = $("<div class='panel panel-default'></div>");
			appendSev(parent, sev.serviceName, sev.serviceContainerType,
					sev.userName, sev.ownerName, sev.paasName, sev.createDate,
					sev.write, sev.importUrl);
			projects.append(parent);
		}
	}
}

function showAllSev() {
	var projects = $(".projects");
	projects.children().remove();
	for (var i = 0; i < sevlist.length; i++) {
		var sev = sevlist[i];
		var parent = $("<div class='panel panel-default'></div>");
		appendSev(parent, sev);
		projects.append(parent);
	}
}

function disform(e) {
	e.parents('.form-group').addClass('has-error');
	$("button[type='submit']").attr({
		"disabled" : "disabled"
	});
}

function enform(e) {
	e.parents('.form-group').removeClass('has-error');
	$("button[type='submit']").removeAttr("disabled");
}
