/**
 * 对下拉框中的text转拼音，拼接首字母
 * 并且设置拼音属性
 * @param root	下拉框Id
 * @param sort	是否排序
 * @param firstLetter	是否显示
 */
function filterOption(root, sort, firstLetter) {
	
    var tempul = $("#" + root);
    tempul.children().each(function() {
        var htmword = $(this).html();
        var pyword = $(this).toPinyin();
        var supperword = "";
        pyword.replace(/[A-Z]/g, function(word) { supperword += word });
        if(supperword == ""){
        	pyword.toUpperCase().replace(/[A-Z]/g, function(word) { supperword += word });
        }
        if(firstLetter){
        	if(!$(this).attr("sort")){
        		$(this).html($.trim(supperword.substr(0,1) + "：" + htmword));
        	}
        }
        $(this).prop("mka", (htmword).toLowerCase());
        $(this).prop("mkb", (pyword).toLowerCase());
        $(this).prop("mkc", (supperword).toLowerCase());
    });

    if(sort){
    	selectSort(root);
    }

    $.include(['/sellerMgr/static/css/bootstrap-select.css']); 
    $.cachedScript('/sellerMgr/static/plugins/util/bootstrap-select.js').done(function(){
    		$('.selectpicker').selectpicker();
	});

}

/**
 * 对下拉框中的text进行排序
 * @param selectId	下拉框Id
 */
function selectSort(selectId){
	var select = $("#" + selectId);
	var selectedVal = select.find("option:selected").val();
	select.find("option").sort(function(a,b){  
	    var aText = $(a).text().toUpperCase();  
	    var bText = $(b).text().toUpperCase();  
	    //先判断是否需要排序
	    if($(a).attr("sort") == "top" ){
	    	return -1;
	    }else if($(b).attr("sort") == "top"){
	    	return 1;
	    }
	    
	    if(aText>bText){ return 1 };  
	    if(aText<bText){ return -1};  
	    return 0;  
	}).appendTo(select) ;
	select.val(selectedVal);
}
