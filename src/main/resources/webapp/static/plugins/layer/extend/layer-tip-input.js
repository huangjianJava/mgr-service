	$.fn.searchParts = function(settings){
		config = { 
		       "sign": 1,
		       "position" : "center",
		       "operate" : "添加",
		       "chooseRow" : false,
		       "successFun" : function(){}
	     }; 
		
	    if (settings){ $.extend(config, settings) };
	    
	    //值改变时进行查询
		$(this).bind("input properychange",function(){
			$(this).autocomplete("search", $(this).val());
		}).bind("focus",function(){
			// 获取输入框焦点并且有值存在数据进行展示
			if (this.value&&$(".ui-autocomplete").html()) {
 			  $(".ui-autocomplete").show();
			}
			
		});
		 _this = this;
	
		this.autocomplete({
			// delay:3000,
			minLengthType : 2,
			source : function(request, response) {
				var partNum = request.term.replace(/\ +/g, "");
				var userLogin = $("#userLogin").val();
				layer.showContent({
					title : "快速添加",
					partNum : partNum,
					// 判断是什么类型的
					sign : config.sign,
					position : config.position,
					id : $(_this).attr("id"),
					viewIndex : 1,
					pageSize : 5,
					operate:config.operate,   //操作按钮名称
					chooseRow:config.chooseRow, //选择列
					complet : function(callBackParameters) {
						//if(typeof(config.successFun) == 'function'){
						 var  params= JSON.stringify(callBackParameters.data); 
						 eval(config.successFun+"("+ params +")");
						//} 
					}
				});
			},
			search : function() {
				var term = this.value.replace(/\ +/g, "");
				if (term.length < 2) {
					_clean();
					return false;
				}else if(term.length>50){
					if(settings.addType == "originalAdd"){
						
					}else{
						layer.tips("输入的字符过长，不能超过50个字符",$(_this), {
							tips : [ 1 ]
						});
					}
					return false;
				}else if (!/^[A-Za-z0-9\s\.\-\\\/]{1,50}$/.test(this.value)) {
					_clean();
					
					if(settings.addType == "originalAdd"){


					}else{
						layer.tips("零件号由-、.、\\、/、字母、空格、数字组成",$(_this), {
							tips : [ 1 ]
						});
					}
					return false;
				}

				if ($(this).hasClass("error")) {
					$(this).removeClass("error");
				}
			}
		});
	
		// 搜索按钮
		$("#partSearchSubmit").click(function() {
			var oShoppingParts = $("#shoppingParts");
			var sShoppingParts = oShoppingParts.val();
			if(sShoppingParts){
				if ($(".ui-autocomplete").html()&&$(".ui-autocomplete").is(":hidden")) {
					$(".ui-autocomplete").show();
				}else{
					oShoppingParts.autocomplete("search",sShoppingParts);
				}
			}
		});
		
		/**
		 * 清空
		 */
		function _clean() {
			$(".ui-autocomplete").hide();
			$(".ui-autocomplete").html("");
		}

	};


