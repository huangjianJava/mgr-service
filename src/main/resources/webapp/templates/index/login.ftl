<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
	    <meta content="charset=UTF-8">
	    <meta name="format-detection" content="telephone=no">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="author" content="HarryChang - hongcheng">
		<title></title>
		<link  rel="stylesheet" href="${commonStatic}/plugins/bootstrap/css/bootstrap.min.css"/>
		<link  rel="stylesheet" href="${commonStatic}/css/login/bootstrap.modify.css"/>
		<link  rel="stylesheet" href="${commonStatic}/css/login/reset.css"/>
		<link  rel="stylesheet" href="${commonStatic}/css/login/login.css"/>
		<link  rel="stylesheet" href="${commonStatic}/css/login/main.css"/>
	</head>
	<body>
		<header>
			<div class="login-top container">
				<a href="#" title="测试" class="fl"><img style="margin-top: 7px;" alt="测试" src="${commonStatic}/images/login/logo.png"></a>
				<div class="login-top-hotline">
				<i class="icon hotline-icon"></i>
				<span>客服热线：400-168-6600</span>
				</div>
			</div>
			<div class="login-top-split">
			</div>
		</header>
		<div class="login-container container-fluid text-center">
			<div class="container">
				<div class="slider-wrapper">
					<div id="PC_LAUNCH_POLLER"><img src="${commonStatic}/images/login/test.jpg" style=" height: 389px; width: 788px;"/></div>
				</div>
				<div class="login-body">
					<form id="fm1" class="fm-v clearfix" action="http://ec-test.casstime.com/passport/login" method="post" onsubmit="login();">
					<input type="hidden" name="logintype" value="PASSWORD">
					<input type="hidden" id="errorCode" value="">
					<ul class="login-form">
						<li class="title">
							<h3 class="fl">登录</h3>
							<div class="to-register-div">还没有账号？<a class="register-a" target="_self" href="#">立即注册</a></div>
						</li>
						<li class="blank-tip"></li>
						<li class="error-message-tip">
							<i class="i-error-message"></i>
							<span class="error-message"></span>
						</li>
						<li class="input li-show">
						    <input id="userName" name="username" tabindex="1" placeholder="用户名/手机号" type="text" value="" size="25">
						    <i class="i-username"></i>
						</li>
						<li class="input li-show">
							<input id="password" name="password" tabindex="2" placeholder="密码" type="password" value="" size="25" autocomplete="off">
							<i class="i-password"></i>
						</li>
						<li class="input li-hide">
							<input id="cellPhone" name="cellphone" tabindex="1" placeholder="手机号" type="text" value="" size="11" oninput="this.value = this.value.replace(/\D+/g, &#39;&#39;)" maxlength="11">
							<i class="i-cellPhone"></i>
						</li>
						<li class="input li-hide">
							<input id="verifyCode" name="verifycode" tabindex="2" placeholder="验证码" type="text" value="" size="6" autocomplete="off" style="padding-right: 102px;" maxlength="6">
							<a class="acquire-verification-code">获取验证码</a>
							<i class="i-verificationCode"></i>
						</li>
						<li class="auto-login-li">
							<ul class="fix">
							    <li class="auto-login fl" onclick="check()">
									<b id="rmbPassword" class="active"></b>
					           	    <a href="javascript:void(0)" style="color:#666666;">记住我</a>
								</li>
								<li class="look-for-pwd fr">
									<!--<a class="change-login-way" href="javascript:void(0)">手机验证码登录</a>-->
					           	    <a class="find-pwd" href="javascript:void(0)">找回密码?</a>
								</li>
							</ul>
						</li>
						<li class="fix">
							<input class="btn-submit" accesskey="l" value="登录" tabindex="4" type="submit">
						</li>
					</ul>
					</form>
				</div>
				
				
			</div>
		
		
		</div>
<footer>
	<div class="container-fluid text-center" style="margin-top:20px;">
	    <div class="copy-right">
	    	Copyright @ 2018 CassTime. <a href="http://www.miitbeian.gov.cn/" target="_blank">ICP证:粤ICP备15084413号-1</a>
	    </div>
	    <div class="copy-right increment-license">
	        增值电信业务经营许可证：&nbsp;粤B2-20160401
	    </div>
	</div>
</footer>
		
	</body>
	
	
	<script src="${commonStatic}/plugins/jquery/1.9.1/jquery.min.js"></script>
	<script src="${commonStatic}/plugins/bootstrap/js/bootstrap.js"></script>
	 
</html>
