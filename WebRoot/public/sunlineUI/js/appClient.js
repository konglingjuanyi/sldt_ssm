/**
 * 模块版本 各模块相对应用路径
 */
var app_mb = "/../mds";
var app_sm = "/../ssm";

/**
 * 集成版本 各模块相对应用路径
 */
/*
 * var app_mb = ""; var app_sm = "";
 * 
 */

/**
 * 修改密码
 */
function openDataSourceDetail(title, dsrcId, option) {
	var content = context + "/ssmLogin.do?method=updatePassword";
	parent.layer.open({
		type : 2,
		skin : 'layui-layer-rim',
		title : title,
		shadeClose : false,
		area : [ '500px', '243px' ],
		fix : false, // 不固定
		maxmin : true,
		content : content
	});
	return;
}

function closWindow() {
	var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
	/* parent.search(); */
	parent.layer.close(index);
}
// 验证原始密码正确性
function pass() {
	var pass = $("#oldPass").val();
	$.ajax({
		type : 'post',
		url : context + "/ssmLogin.do?method=pass",
		data : '&pass=' + pass,
		success : function(msg) {
			$("#password").val(msg.code);
			if (msg.message != "") {
				// layer.alert(msg.message);
				parent.layer.msg(msg.message);
			}
		},
		error : function(msg) {
			parent.layer.alert("访问服务器出错！+to");
		}
	});
}
// 提交修改
function upPass() {
	// 验证两次密码是否一致
	var pass = $("#oldPass").val();
	var password = $("#password").val();
	var newpass = $("#newPass").val();
	var newpassnext = $("#newPassNext").val();
	var a = 0;
	var b = 0;
	var c = 0;
	var d = 0;
	if (pass != password) {
		// layer.alert("原始密码不正确!");
		parent.layer.msg("原始密码不正确!");
		a = 1;
	} else {
		a = 0;
	}
	if (newpassnext == "") {
		// layer.alert("请确认新密码");
		parent.layer.msg("请确认新密码!");
		b = 1;
	} else {
		b = 0;
	}
	if (newpass != newpassnext) {
		// layer.alert("两次密码输入不一致,请重新输入新密码！");
		parent.layer.msg("两次密码输入不一致,请重新输入新密码");
		c = 1;
	} else {
		c = 0;
	}
	if (newpass == "") {
		// layer.alert("请输入新密码！");
		parent.layer.msg("请输入新密码!");
		d = 1;
	} else {
		d = 0;
	}
	if (a == 0 && b == 0 && c == 0 && d == 0) {
		$.ajax({
			type : 'post',
			url : context + "/ssmLogin.do?method=updatepass",
			data : '&newpass=' + newpass,
			success : function(msg1) {
				// alert("密码修改成功");
				$("#password").val("");
				parent.layer.msg("密码修改成功!");
				parent.window.location = context + "/logout";
				closWindow();
			},
			error : function(msg1) {
				parent.layer.alert("访问服务器出错！+to");
			}

		});
	}
}