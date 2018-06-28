/*使用方法：
 * 使用之前需要引入
 *  <set field="layoutSettings.styleSheets[+1]" value="/images/jquery/css/jquery-ui.css" global="true" />
 *	<set field="layoutSettings.javaScripts[+0]" value="/images/jquery/ui/js/jquery-ui-1.10.3.min.js" global="true"/>
 *  <set field="layoutSettings.javaScripts[+1]" value="/static/javascript/utils/utils.js" global="true"/>
 *	<set field="layoutSettings.javaScripts[+2]" value="/static/javascript/utils/validateUtil.js" global="true"/>
 * layer.showContent({            
        title : "快速添加",
        partNum : "12E010001A",
        id:'huhu', //该对象用于
         //判断是什么类型的
        sign: '0',
        position:'left',//支持左对齐与居中
        operate:'购买',
        viewIndex:1,    //从第几页开始显示
        pageSize:5,		//每一页显示的数量
		complet : function(callBackParameters){
			console.log(callBackParameters);
		}
	});*/

//JS中的代码
if (typeof layer != "undefined") {
	var g_$obj;
	var g_len;
	var g_page = 1;
	var g_args;

	layer.use("skin/layer-tip.css", function() {
		layer.layui_layer_extendlayerextjs = !0
	}), layer.showContent = function(args) {
		clickTrigger(args);

	};
	/**
	 * 分页
	 */
	var pageSor = function(forNum, ul_autocomplete) {
		if (forNum > 1) { // 大于一页进行展示
			var div = $("<ul class='fast-div' style='width:100%;background-color: #fafafa;  border-top: 1px solid #ddd;'> </ul>");
			if (g_args.viewIndex == 1) {
				var upPage = $("<a class='btn btn-large disabled' id='upPage' onclick='upPageFun(this)' href='javascript:void(0);'>< 上一页</a>");
			} else {
				var upPage = $("<a class='fast-a-Page' id='upPage' onclick='upPageFun()' href='javascript:void(0);'>< 上一页</a>");
			}
			if (g_args.viewIndex == forNum) {
				var downPage = $("<a class='btn btn-large disabled' id='downPage' style='margin-left: 20px;' onclick='downPageFun()' href='javascript:void(0);'>下一页  ></a>");
			} else {
				var downPage = $("<a class='fast-a-Page' id='downPage' style='margin-left: 20px;' onclick='downPageFun()' href='javascript:void(0);'>下一页  ></a>");
			}

			div.append(upPage);
			div.append(downPage);
			ul_autocomplete.append(div);
		}
	}

	/**
	 * 下一页
	 */
	function downPageFun() {
		g_args.viewIndex++;
		clickTrigger(g_args);
	}
	;

	/**
	 * 上一页
	 */
	function upPageFun() {
		g_args.viewIndex--;
		clickTrigger(g_args);
	}
	;

	/**
	 * 计算创建的位置
	 */
	function _caretePosition(ul, width, position, args, maker) {
		if (args.id) {
			// 获取目标的位置
			var targetPosition = $("#" + args.id);
			// iframe子页面
			if (args.iframe) {
				targetPosition = $(
						document.getElementById('diagramIframe').contentWindow.document.body)
						.find("#" + args.id);
			}

			// 左对齐
			if (args.position == "left" || position == "") {
				// 获取父元素的左边的尺寸

				ul[0].style.left = targetPosition.offset().left + "px";

				ul[0].style.top = targetPosition.offset().top
						+ targetPosition.height() + 10 + "px";

				// ul[0].style.width = width+"px";

			}
			// 默认居中
			else {
				if (args.sign == 0) {
					ul[0].style.left = targetPosition.offset().left - 360
							+ "px";
				} else {
					if (maker == 0) {
						ul[0].style.left = targetPosition.offset().left + "px";

					} else {
						ul[0].style.left = targetPosition.offset().left - 320
								+ "px";
					}

				}
				ul[0].style.top = targetPosition.offset().top
						+ targetPosition.height() + 10 + "px";
				// ul[0].style.width = width+"px";
				// ifame子页面
				if (args.iframe) {
					var iframeHeight = $(
							document.getElementById('diagramIframe').contentWindow.document.body)
							.height();
					var documentHeigt = $(document.body).height();

					var iframeWidth = $(
							document.getElementById('diagramIframe').contentWindow.document.body)
							.width();
					var documentWidth = $(document.body).width();
					var contentWidth = $("#content").width();
					var partsWidth = $(".parts-list").width();
					var oCurrleft = targetPosition.offset().left;
					// console.log(ul.height()+targetPosition.offset().top
					// +":::"+(documentHeigt+$(document.body).scrollTop()-targetPosition.offset().top
					// ));
					if (maker == 0)
						oCurrleft = targetPosition.offset().left - 480;
					ul[0].style.right = (documentWidth - contentWidth) / 2
							+ (partsWidth - iframeWidth) / 2 + "px";
					ul[0].style.left = "auto";
					if ((iframeHeight - targetPosition.offset().top) > 70) {
						ul[0].style.top = targetPosition.offset().top
								+ (documentHeigt - iframeHeight) - 10 + "px";
					} else {
						ul[0].style.top = targetPosition.offset().top
								+ (documentHeigt - iframeHeight) - 100 + "px";
					}

					// console.log(ul[0].style.top)
				}
			}
		} else {
			ul[0].style.top = 300 + "px";
			ul[0].style.left = 300 + "px";
			// ul[0].style.width = width+"px";
		}
		ul.show();
	}

	/**
	 * 创建层
	 */

	function _create(resp, userLogin, numFound, args) {
		if (!args.operate) {
			args.operate = '添加';
		}

		var operate = args.operate;
		var ul_autocomplete = $(".ui-autocomplete");
		ul_autocomplete.addClass("border-solid-red");
		ul_autocomplete.addClass("biaoge");
		// 它会给内部的部件统一字体
		ul_autocomplete.removeClass("ui-widget-content");
		var respLength = resp.length;
		var maker = 0;
		if (respLength == 0) {
			
			var topLi = $("<div style='padding: 5px;'>很抱歉，没有找到相关配件。</div>");
			if (args.chooseRow) {
					//在新增原厂零件商品的时候，要防止修改
					$("input[name='productOriginalParts.originalBrandName']").val("");
					$("input[name='product.productName']").val("");
					
					topLi = $("<div style='padding: 5px;'>很抱歉，没有找到相关的配件，请联系开思汽配客服人员！</div>");
			}
			marker = 1;
			// 给该元素添加属性
			ul_autocomplete.prepend(topLi);
			_caretePosition(ul_autocomplete, 230, "left", args, maker);
			// 如果不加 return 就会一直运行下去
			return;
		}

		var topLi = $("<ul><li class='biaotou width-1'>零件号</li> <li class='biaotou width-2' style='padding-right:40px'>名称</li> <li class='biaotou width-3'>品牌</li><li class='biaotou width-4'>原厂件号</li><li class='biaotou width-5'>店铺</li><li class='biaotou width-6'>价格(&yen;)</li> <li class='biaotou width-7'>库存</li><li class='biaotou width-8'>操作</li></ul>");
		// 选择列
		if (args.chooseRow) {
			topLi = $("<ul><li class='biaotou width-T1'>零件号</li> <li class='biaotou width-2' style='padding-right:30px'>名称</li> <li class='biaotou width-3'>品牌</li><li class='biaotou width-8'>操作</li></ul>");
		}

		// 得到的是分页的页码数
		var forNum = Math.ceil(numFound / args.pageSize);
		for (var j = 0; j < respLength; j++) {
			var ul = $("<ul  class='ul_parent'></ul>");
			if (!args.chooseRow) {
				var productInventoryListMap = resp[j].productInventoryListMap;
				var availableAll = productInventoryListMap.availableAll;// 用于判断缺货状态下是否可以购买
				var inventoryJSONArray = productInventoryListMap.productInventoryList;
				var requireInventory = productInventoryListMap.requireInventory;
				if(availableAll=="N"){
					productInventory="缺货";
				}else if(inventoryJSONArray.length>1){
					var productInventory=$("<ul />").attr("id","main_box");
					var selectBoxLi=$("<li />").attr("class","select_box").appendTo(productInventory);
					var b=$("<b />").attr("class","select-down").appendTo(selectBoxLi);
					var sonUl=$("<ul />").attr("class","son_ul none").appendTo(selectBoxLi);
					var defInventory=$(".site-nav-area .ui-areamini-content span").attr("data-id");
					var data = {geoId:defInventory,storeId:resp[j].productStore.productStoreId};
					var facilityId = "";
					$.ajax({
						url : "getFacilityByGeoAndStore",
						type : "post",
						dataType : "json",
						data : data,
						async : false,
						success : function(data) {
							if(data && data.facilityId){
								facilityId = data.facilityId;
							}else{
								if(defInventory=="CN-44" || defInventory=="CN-35"){
									facilityId = "CN_GZ";
								}else{
									facilityId = "CN_ZZ";
								}
							}
						},
						error : function(data) {
							if(defInventory=="CN-44" || defInventory=="CN-35"){
								facilityId = "CN_GZ";
							}else{
								facilityId = "CN_ZZ";
							}
						}
					});
					
					for(var k=0; k<inventoryJSONArray.length; k++){
						if(inventoryJSONArray[k].facility.facilityId==facilityId){
							var span=$("<span />").text(inventoryJSONArray[k].facility.facilityName+"："+inventoryJSONArray[k].productInventory).appendTo(selectBoxLi);
						}else{
							$("<li />").text(inventoryJSONArray[k].facility.facilityName+"："+inventoryJSONArray[k].productInventory).appendTo(sonUl);
						}
					}
					$(span).hover(function(){ //鼠标移动函数
						$(this).parent().find('ul.son_ul').slideDown();  //找到ul.son_ul显示
						$(this).parent().find('li').hover(function(){
							$(this).addClass('hover')
						},function(){
							$(this).removeClass('hover')
						}); //li的hover效果
						$(this).parent().hover(function(){},
							function(){
								$(this).parent().find("ul.son_ul").slideUp(); 
							}
						);
					});
					
				}else{
					productInventory=inventoryJSONArray[0].productInventory;
				}
			}
			
			var product = resp[j].product;
			var productBrandName = resp[j].productBrandName;
			if (!product.productName) {
				product.productName = "-";
			}
			// 单次请求数据
			var liName = $(format(
					"<li class='fast-li width-3' title='{0}'>{0}</li>",
					productBrandName));
			var liAdd = $("<li class='width-8'></li>");
			var liProductName = $(format(
					"<li class='fast-li leftT width-2 text-left' title='{0}'>{0}</li>",
					product.productName));
			if (!args.chooseRow) {
				var productStore = resp[j].productStore;
				var productPrice = resp[j].productPrice;
				var liNum = $(format(
						"<li id='partNum_{0}' class='leftT width-1' storeId='{2}'><a href='/portal/CarProductShow?productId={0}' target='_blank' title='{1}'>{1}</a></li>",
						product.productId, resp[j].brandPartNum,
						productStore.productStoreId));
				if (!product.partNumberBrand
						|| product.partNumberBrand == 'NaN'
						|| product.partNumberBrand == 0) {
					product.partNumberBrand = "&nbsp";
				}
				if (resp[j].isOEM == 1) {
					var brandNum = $(format("<li  class='fast-li width-4 text-center'>--</li>"));
				} else {
					var brandNum = $(format("<li  class='fast-li leftT width-4' title='{0}'>{0}</li>",resp[j].partsAndBrand ? resp[j].partsAndBrand : "&nbsp;"));
				}
				var liStoreName = $(format(
						"<li class='fast-li leftT store width-5' storeId='{0}' title='{1}'>{1}</li>",
						productStore.productStoreId, productStore.storeName));

				var inventory = $("<li />").attr("class","fast-li width-7").append(productInventory);
//					
//					
//					$(format(
//						"<li  class='fast-li width-7'>{0}</li>",
//						productInventory));

				if (!userLogin) {
					defaultPrice = "会员可见";
				} else {
					// 价格
					var defaultPrice = productPrice.defaultPrice;
					// 有一种情况成立就会暂无报价
					if (!defaultPrice || defaultPrice == 'NaN'
							|| defaultPrice == 0) {
						defaultPrice = "暂无报价";
					} else {

						defaultPrice = defaultPrice.toFixed(2);
					}
				}
				var liPrice = $(format(
						"<li class='fast-li-money rightT width-6' title='{0}'>{0}</li>",
						defaultPrice));
				
				if(!userLogin || defaultPrice == "暂无报价" || product.statusId != "PRODUCT_ADDED"){
					if(product.statusId != "PRODUCT_ADDED"){
						operate = "已下架";
					}
					var liAdd_a = $("<a class='fast-li-add hover_N ' id='"
							+ product.productId
							+ "'  href='javascript:void(0);'>" + operate
							+ "</a>");
				}else if(availableAll == "N" && requireInventory == "Y"){
					$(".productNo").val(product.productName);
					$(".partsNo").val(product.partNumber);
					var liAdd_a = 
							$("<a class='fast-li-add'  href='javascript:lackProductRecord(\""+ product.productId+"\",\""+product.productName+"\",\""+product.partNumber +"\");'> 缺货登记 </a>");
				}else if(availableAll == "N" && requireInventory == "N"){
					var liAdd_a = $("<a class='fast-li-add' id='"
							+ product.productId
							+ "'  href='javascript:void(0);'>急件调货</a>");
					$(liAdd_a).on('click', '', resp[j], args.complet);
				} else {
					var liAdd_a = $("<a class='fast-li-add' id='"
							+ product.productId
							+ "'  href='javascript:void(0);'>" + operate
							+ "</a>");
					$(liAdd_a).on('click', '', resp[j], args.complet);
				}
				
			} else {
				var liAdd_a = $("<a class='fast-li-add' href='javascript:void(0);'>" + operate + "</a>");
				$(liAdd_a).on('click', '', resp[j], args.complet);
				var liNum = $(format("<li class='leftT width-T1'>{0}</li>",resp[j].sparePartNum));
			}
			liAdd.append(liAdd_a);
			ul.append(liNum);
			ul.append(liProductName);
			ul.append(liName);
			ul.append(brandNum);
			// 默认不选择
			if (!args.chooseRow) {

				ul.append(liStoreName);
			}
			ul.append(liPrice);
			if (!args.chooseRow) {
				ul.append(inventory);
			}
			ul.append(liAdd);
			// 形成了一个层，含有<ul></ul>
			ul_autocomplete.append(ul);
		}
		g_$obj = $('.ui-autocomplete ul');
		g_len = forNum;
		ul_autocomplete.prepend(topLi);
		// 分页的标志
		pageSor(forNum, ul_autocomplete, args);
		// 计算位置
		_caretePosition(ul_autocomplete, 826, "left", args);
	}

	// 主函数
	function clickTrigger(args) {
		g_args = args;
		var userLogin = $("#userLogin").val();
		var start = (args.viewIndex - 1) * args.pageSize;
		var data = {
			part : args.partNum,
			start : start,
			rows : args.pageSize
		};
		var url = "/portal/seachProducts";
		if (args.chooseRow) {
			url = "/storemgr/selPartsData";
		}
		$.ajax({
			url : url,
			type : "post",
			dataType : "json",
			data : data,
			async : true,
			error : function() {
				alert('error');
			},
			success : function(data) {
				var resp = data.carProducts;
				var numFound = data.numFound;
				var id = $("#" + args.id);

				$(".ui-autocomplete").empty();
				_create(resp, userLogin, numFound, args);
				var inputValue = $(".ui-autocomplete-input").val();
				if(args.chooseRow){
					if(resp.length == 1 && (inputValue == resp[0].sparePartNum)){
						$('.fast-li-add').click();
					}
				}
				// 表示的是移除掉自动加载的类，如果不移就会一直有个图转的
				if (args.sign != 0) {
					id.removeClass("ui-autocomplete-loading");
				}
			}
		});
	}
}
