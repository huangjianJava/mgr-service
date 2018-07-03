
$(function(){

    enableValidateCode();
});
$(document).ready(function(){
	getRememberInfo();
	initLoginWay();
});

function login(){
	setCookie("loginType",$("input[name='logintype']").val(),168,"/passport");
	var userName = $.trim($("#userName").val());
	var cellPhone=$.trim($("#cellPhone").val());
	if(userName && userName!=""){
		userName = userName.toLowerCase();
		$("#userName").val(userName);
		setCookie("checkSettlein","true",168,"/");
		if($("#rmbPassword").hasClass("active")){
			setCookie("rmbPassword","active",168,"/passport");
			setCookie("userName",userName,168,"/passport");
			setCookie("password",$("#password").val(),168,"/passport");
		}else{
			deleteCookie("userName","/passport");
			deleteCookie("password","/passport");
			setCookie("rmbPassword","unactive",168,"/passport");
		}
	}
	else if(cellPhone && cellPhone!=""){
		$("#cellPhone").val(cellPhone);
		if($("#rmbPassword").hasClass("active")){
			setCookie("cellPhone",cellPhone,168,"/passport");
			setCookie("rmbPassword","active",168,"/passport");
		}else{
			deleteCookie("cellPhone","/passport");
			setCookie("rmbPassword","unactive",168,"/passport");
		}
	}
	return true;
}

function  lookForPassword(){
	var userName = $("#userName").val();
	if(userName && userName!=""){
		userName = userName.toLowerCase();
	}
	var v_data = {userIdOrCellphone:userName};
	initFormSubmit(v_data, "/passport/findpwd/step1");	
}

function initFormSubmit(data,actionUrl){
	if(typeof data !="object"){
		return ;
	}
	var oInput="";
	var oForm = $("<form action="+actionUrl+" method='post' id='lookForPasswordForm' style='display:none'/>");
	for(var i in data){
		var vInput = "<input type='hidden' name=\'"+i+"\' value=\'"+data[i]+"\'/>";
		oInput+=vInput;
	}
	oForm.append(oInput);
	$("body").append(oForm);
	$("#lookForPasswordForm").submit();
	
}

function check(){
	var $this = $("#rmbPassword");
	$this.toggleClass("active");
}

//获取cookie信息
function getRememberInfo() {
	try {
		var rmbPassword = getCookieValue("rmbPassword");
		var userName = $("#userName").val();
		var userPassword = $("#password").val();
		if (rmbPassword && rmbPassword == "active") {
			$("#rmbPassword").addClass("active");
			var loginType = getCookieValue("loginType");
			if (loginType == 'VERIFYCODE') {
				var cellPhone = getCookieValue("cellPhone");
				$("#cellPhone").val(cellPhone);
			} else {
				userName = getCookieValue("userName");
				userPassword = getCookieValue("password");
				$("#userName").val(userName);
				$("#password").val(userPassword);
			}
		} else if (rmbPassword == "unactive") {
			$("#rmbPassword").removeClass("active");
		}

	} catch (err) {
		console.log("NO RMB PASSWORD!");
	}
}


//新建cookie。
//hours为空字符串时,cookie的生存期至浏览器会话结束。hours为数字0时,建立的是一个失效的cookie,这个cookie会覆盖已经建立过的同名、同path的cookie（如果这个cookie存在）。
function setCookie(name, value, hours, path) {
	var name = EnEight(escape(name));
	var value = EnEight(escape(value));
	var expires = new Date();
	expires.setTime(expires.getTime() + hours * 3600000);
	path = path == "" ? "" : ";path=" + path;
	_expires = (typeof hours) == "string" ? "" : ";expires="+ expires.toUTCString();
	document.cookie = name + "=" + value + _expires + path;
}
//获取cookie值
function getCookieValue(name) {
	var name = EnEight(escape(name));
	var allcookies = document.cookie;
	name += "=";
	var pos = allcookies.indexOf(name);
	if (pos != -1) {
		var start = pos + name.length;
		var end = allcookies.indexOf(";", start);
		if (end == -1)
			end = allcookies.length;
		var value = allcookies.substring(start, end);
		return unescape(DeEight(value)); //对它解码
	} else
		return ""; //搜索失败，返回空字符串
}
//删除cookie
function deleteCookie(name, path) {
	var name = EnEight(escape(name));
	var expires = new Date(0);
	path = path == "" ? "" : ";path=" + path;
	document.cookie = name + "=" + ";expires=" + expires.toUTCString() + path;
}

/*8进制加密*/
function EnEight(value){
    var monyer = new Array();
    for(var i=0;i<value.length;i++){
    	monyer+="\\"+value.charCodeAt(i).toString(8); 
    }
    return monyer;
}
/*8进制解密*/
function DeEight(value){
    var monyer = new Array();
    var s=value.split("\\");
    for(var i=1;i<s.length;i++){
    	monyer+=String.fromCharCode(parseInt(s[i],8));
    }
    return monyer;
}

/*转换登录方式（用户名密码/手机验证码）*/
function changeLoginWay() {
	
	$(".error-message-tip").hide();
	$(".blank-tip").show();

    $(".li-hide").each(function () {
        $(this).removeClass("li-hide");
    });

	$(".li-show").each(function () {
		$(this).find("input").val("");
		$(this).removeClass("li-show").addClass("li-hide");
    });

    $(".input").not(".li-hide").each(function () {
		$(this).addClass("li-show");
    });

    if($("input[name='logintype']").val() == "VERIFYCODE"){
        $(".change-login-way").text("手机验证码登录");
        $("input[name='logintype']").val("PASSWORD");
    }
    else if ($("input[name='logintype']").val() == "PASSWORD") {
        $(".change-login-way").text("账号密码登录");
        $("input[name='logintype']").val("VERIFYCODE");
    }
}

/*初始化登录方式（用户名密码/手机验证码）*/
function initLoginWay() {
	try{
        var loginType=getCookieValue("loginType");
        if(loginType=='VERIFYCODE'){
            $("#userName").parent().removeClass("li-show").addClass("li-hide");
            $("#password").parent().removeClass("li-show").addClass("li-hide");

            $("#cellPhone").parent().removeClass("li-hide").addClass("li-show");
            $("#verifyCode").parent().removeClass("li-hide").addClass("li-show");

            $(".change-login-way").text("账号密码登录");
            $("input[name='logintype']").val("VERIFYCODE");

            showErrorInfo($("#errorCode").val());
        }
        else{
            $("#userName").parent().removeClass("li-hide").addClass("li-show");
            $("#password").parent().removeClass("li-hide").addClass("li-show");

            $("#cellPhone").parent().removeClass("li-show").addClass("li-hide");
            $("#verifyCode").parent().removeClass("li-show").addClass("li-hide");

            $(".change-login-way").text("手机验证码登录");
            $("input[name='logintype']").val("PASSWORD");

            showErrorInfo($("#errorCode").val());
        }
	}catch (err) {
        console.log("INIT LOGINWAY FAIL!");
    }

}

/* 获取验证码设置*/
function enableValidateCode() {

    $(".acquire-verification-code").removeClass("click");

    $(".acquire-verification-code").html("获取验证码");

    $(".acquire-verification-code").bind('click',function () {
    	
        var phoneNumber=$("input[name='cellphone']").val();

        if(!isPoneAvailable(phoneNumber)){
            showErrorInfo("e0");
            return;
        }

        disableValidateCode();

        $.ajax({
            url: "/passport/verifycode/login/send",
            type: "get",
            data: {cellphone: phoneNumber},
            success: function () {
            },
            error: function (e) {
            }
        });

    });
}

function isPoneAvailable(number) {
    var phonereg=/^1\d{10}$/;
    if (!phonereg.test(number)) {
        return false;
    } else {
        return true;
    }
}

function showErrorInfo(errorCode) {
	
	var info=""
	if($.isEmptyObject(errorCode)){
		return;
	}
	else if(errorCode=="e0"){
        info="请输入11位有效手机号！";
	}
	else if(errorCode=="e2"){
        info="该用户不存在！";
	}
	else if(errorCode=="e3"){
        info="账号已失效，请联系您的客户经理！";
	}
    else if(errorCode=="e1"){
		var loginType=$("input[name='logintype']").val();
        if(loginType=='PASSWORD'){
            info="登入失败，用户名或密码错误！";
		}
		else{
            info="登入失败，验证码错误！";
		}
    }

	$(".blank-tip").hide();
    $(".error-message-tip .error-message").text(info);
    $(".error-message-tip").show();
}

function disableValidateCode() {

    var times = 119;

    $(".acquire-verification-code").addClass("click");

    $(".acquire-verification-code").unbind("click");

    $(".acquire-verification-code").html("获取验证码中...");

    var _setInterval = window.setInterval(function () {
        if (times == 0) {
            enableValidateCode();
            window.clearInterval(_setInterval);
            return;
        }
        $(".acquire-verification-code").html("<span style='font-family: airal'>"+times+"s</span>"+"后重新获取");
        times = times - 1;
    }, 1000);

}