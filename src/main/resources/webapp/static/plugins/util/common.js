window.console = window.console || (function(){  

    var c = {}; c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.profile  
    = c.clear = c.exception = c.trace = c.assert = function(){};  
    return c;  
})(); 

/*var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?daf37ac761f32913b0e0665a9b68762e";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();*/


(function ($) {
	$.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]); return null;
    }
	$.setUrlParam = function (name, value) {
		
        var url="", suffix="", str="", returnurl="", setparam="", arr, modify="0";
        
        if (window.location.href.indexOf('#')>0) {
        	url = window.location.href.split('#')[0];
        	suffix = "#" + window.location.href.split('#')[1];
        } else {
        	url = window.location.href;
        }
        
        if(url.indexOf('login') >= 0){
        	url = url.replace("login","main");
        }
        if (url.indexOf('?') != -1)
            str = url.substr(url.indexOf('?') + 1);
        else {
        	if (suffix == "")
        		return url + "?" + name + "=" + value;
        	else 
        		return url + "?" + name + "=" + value + "#" + suffix;
        }
        
        if (str.indexOf('&') != -1) {
            arr = str.split('&');

            for (i in arr) {
                if (arr[i].split('=')[0] == name) {
                    setparam = value;
                    modify = "1";
                }
                else {
                    setparam = arr[i].split('=')[1];
                }
                returnurl = returnurl + arr[i].split('=')[0] + "=" + setparam + "&";
            }

            returnurl = returnurl.substr(0, returnurl.length - 1);

            if (modify == "0")
                if (returnurl == str)
                    returnurl = returnurl + "&" + name + "=" + value;
        } else {
            if (str.indexOf('=') != -1) {
                arr = str.split('=');

                if (arr[0] == name) {
                    setparam = value;
                    modify = "1";
                } else {
                    setparam = arr[1];
                }
                returnurl = arr[0] + "=" + setparam;
                if (modify == "0")
                    if (returnurl == str)
                        returnurl = returnurl + "&" + name + "=" + value;
            }
            else
                returnurl = name + "=" + value;
        }
        if (suffix == "") 
        	return url.substr(0, url.indexOf('?')) + "?" + returnurl;
        else
        	return url.substr(0, url.indexOf('?')) + "?" + returnurl + "#" + suffix;
    }
	/*
    var $loadedJS = [];
    $.loadJS = function(uri) {
    	if (!this.contains($loadedJS, uri)) {
    		var js = document.createElement('script');
            js.setAttribute("type","text/javascript");
            js.setAttribute("src",uri);
            document.getElementsByTagName("head")[0].appendChild(js);
            $loadedJS[$loadedJS.length] = uri;
    	}
    	
    	this.contains = function(a, o) {
    		var i = a.length;
    	    while (i--) 
    	        if (a[i] === o) return true;
    	    return false;
    	}
    }*/
	
	/**
	 * 加入收藏
	 * by haifeng.mi
	 * 2016-6-20 15:01:16
	 */
	function AddFavorite(sURL, sTitle) {   
	    try {   
	        window.external.addFavorite(sURL, sTitle);   
	    } catch (e) {   
	        try {   
	            window.sidebar.addPanel(sTitle, sURL, "");   
	        } catch (e) {   
	            alert("加入收藏失败，请使用Ctrl+D进行添加");   
	        }   
	    }   
	}
})(jQuery);





//更新购物车显示数量
function setShopping_count(number){
	if (!$.getUrlParam("cartAttributeName") || $.getUrlParam("cartAttributeName")=="shoppingCart"){
		$("#shopping-amount").html(number);
		$(".shopping-amount").html(number);
		
		//动态添加修改微服务代购连接购物车数量		by haifeng.mi	2016-1-14 17:13:29
		//$("#main-nav-agentBuyInfo a").attr("href", "/agentBuy?cart="+number);
		
	}
}

function openUrl(url){
	window.open(url,"_blank");
}

/**
 * 扩展jquery对象方法 
 * 动态添加css,js文件 js文件使用一下动态缓存方法
 * 使用方法 例如 window.onload方法中 或者需调用的   
 * 例如$.include('/static/css/utils/jquery.tooltip.css');  
 * 例如加载js文件$.cachedScript("/static/javascript/utils/jquery.tooltip.js").done(function(){
 *    callbacks
 * }
 */
$.extend({  
	//设置根路径文件
	includePath: '',  
	//加载css文件
	include: function(file)  { 
	var files = typeof file == "string" ? [file] : file;  
	for (var i = 0; i < files.length; i++)  { 
		var name = files[i].replace(/^\s|\s$/g, ""); 
        var att = name.split('.');
        var ext = att[att.length - 1].toLowerCase();  
        var isCSS = ext == "css";  
        var tag = isCSS ? "link" : "script";  
        var attr = isCSS ? " type='text/css' rel='stylesheet' " : " type='text/javascript' "; 
        var link = (isCSS ? "href" : "src") + "='" + $.includePath + name + "'";  
        if ($(tag + "[" + link + "]").length == 0) 
        	$("head").append("<" + tag + attr + link + "></" + tag + ">");
        }  
	},
	//设置缓存加载动态js并缓存
    cachedScript:function(url,options){
    	options=$.extend(options||{},{
			dataType:'script',
			cache:true,
			url:url	
    	});
    	return $.ajax(options);
    }
});  


/**
 * 格式化 
 * 2016-3-29 15:23:43
 * by haifeng.mi
 */
$.format = function (source, params) {
    if (arguments.length == 1)
        return function () {
            var args = $.makeArray(arguments);
            args.unshift(source);
            return $.format.apply(this, args);
        };
    if (arguments.length > 2 && params.constructor != Array) {
        params = $.makeArray(arguments).slice(1);
    }
    if (params.constructor != Array) {
        params = [params];
    }
    $.each(params, function (i, n) {
        source = source.replace(new RegExp("\\{" + i + "\\}", "g"), n);
    });
    return source;
};
