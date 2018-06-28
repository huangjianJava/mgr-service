/**
 * 常用js工具类
 * haifeng.mi
 * 2015-5-22 10:00:01
 */

/**
 * 添加对string的format
 * @params source 原字符串{0}
 * @params params... 格式化字符串
 */
format = function (source, params) {
		
	if(isUndefined(params) || !isNotEmpty(params)) return source;
	
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


/*
 * 剩余字数统计
 */
 function statInputNum(textArea,numItem) {
     var max = numItem.text();
     var curLength;
     //textArea[0].setAttribute("maxlength", max);
     curLength = textArea.val().length;
     numItem.text(max - curLength);
     textArea.on('input propertychange', function () {
         numItem.text(max - $(this).val().length);
    	/* var maxlength = $(this).attr("maxlength");
    	 var text = $(this).val().replace(/\n/g,'<br/>');
    	 if(text.length > maxlength){
    		 numItem.text(max - text.substring(0, maxlength).length);
    	 }else{
    		 numItem.text(max - text.length);
    	 }*/
     });
 }

 /**
  * 格式化为货币格式
  *
  * defaults: (2, "￥", ",", ".")
  */
 Number.prototype.formatMoney = function (places, symbol, thousand, decimal) {
     places = !isNaN(places = Math.abs(places)) ? places : 2;
     symbol = symbol !== undefined ? " " + symbol +" " : " ￥ ";
     thousand = thousand || ",";
     decimal = decimal || ".";
     var number = this,
         negative = number < 0 ? "-" : "",
         i = parseInt(number = Math.abs(+number || 0).toFixed(places), 10) + "",
         j = (j = i.length) > 3 ? j % 3 : 0;
     return symbol + negative + (j ? i.substr(0, j) + thousand : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + thousand) + (places ? decimal + Math.abs(number - i).toFixed(places).slice(2) : "");
 };
 /**
  * 格式化为货币格式
  *
  * defaults: (2, "￥", ",", ".")
  */
 String.prototype.formatMoney = function (places, symbol, thousand, decimal) {
	 places = !isNaN(places = Math.abs(places)) ? places : 2;
	 symbol = symbol !== undefined ? " " + symbol +" " : " ￥ ";
	 thousand = thousand || ",";
	 decimal = decimal || ".";
	 var number = this,
	 negative = number < 0 ? "-" : "",
			 i = parseInt(number = Math.abs(+number || 0).toFixed(places), 10) + "",
			 j = (j = i.length) > 3 ? j % 3 : 0;
			 return symbol + negative + (j ? i.substr(0, j) + thousand : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + thousand) + (places ? decimal + Math.abs(number - i).toFixed(places).slice(2) : "");
 };
 
 
/**
 * 取消格式化
 */
 String.prototype.clearMoney = function () {
	 
	 if(typeof(this) == 'undefined' || this == null){ return this}
	 
	 return this.replace(/[^0-9-.]/g, '');

 };
 /**
  * 取消格式化
  */
 Number.prototype.clearMoney = function () {
	 
	 if(typeof(this) == 'undefined' || this == null){ return this}
	 
	 return this.replace(/[^0-9-.]/g, '');
	 
 };
