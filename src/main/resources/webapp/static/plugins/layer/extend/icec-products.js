/**
 * 用来在页面上创建商品选择弹出层，依赖 layer
 * 作者：许健
 * 示例：
 * layer.showProducts("#targetId | .targetClassName | $('#targetId') | $('.targetClassName')", {
		partsNo : value,
		serviceUrl : "/portal/products",
		frame : "#frameId | .frameClassName | $('#frameId') | $('.frameClassName')", 
		autoMove : true|false (default is false),
		addCart : {"name":"添加", "action":function(productId){
			
		}},
		dispatch : {"name":"急件调货", "action":function(productId){
			
		}},
		outStock : {"name":"缺货登记", "action":function(productId){
			
		}},
	});
 */
if (typeof layer != "undefined") {
	
	layer.use("skin/icec-products.css", function() {
	    layer.layui_layer_extendlayerextjs = !0
	}), layer.showProducts = function(target, args) {
		
		initArgs();
		
		if (args.partsNo == "") return;
		
		buildLayer(1);
		
		// 构造层
		function buildLayer(viewIndex) {
			var listName =$("#listName").val();
			$.ajax({
				url : args.serviceUrl,
				type : "post",
				dataType : "html",
				data : {
					"product_view_model" : "miniSearch",
					"pageSize" : 6, "index" : viewIndex, "listName":listName,
					"p" : args.partsNo
				},
				async : false,
				success : function(response) {
					var width = 'auto';
					if ($(response).find(".no-data").size()>0) width='315px';
					var openLayerConfig = {
					    type: 1,
					    title: false,
					    shade: false,
					    closeBtn: false,
					    area: [width, '400px'],
					    fix: false, //不固定
					    content: response,
					    success: function(layero, index){
					    	$layer = $(layero);
					    	$layer.attr("id", "shoppingPartsPopup") //方便查找及关闭层
					    		.attr("tabindex", "0"); //让DIV能获得焦点，触发focus与blur事件
					    	build($layer, viewIndex);
					    }
					};
					
					$("#shoppingPartsPopup").remove();
					layer.closeAll('loading');
					layer.open(openLayerConfig);
					moveToTarget();
				},
				error : function(response) {
					console.log("error");
					console.log(response);
				}
			});
		}

		function build($layer, viewIndex) {
			if ($layer.size() > 0) {
				buildPager($layer.find(".pager-row"), viewIndex);
				buildAction($layer.find(".product-line"));
				$("body").click(function(event){
					if (event.target==target[0]) return;
					if (findParentElementByClassName(event.target, "products-list")!=null) return;
					$layer.hide();
				});
			}
		}
		//绑定分页的事件
		function buildPager(pagerContainer, viewIndex) {
			if (pagerContainer.size() > 0) {
				if (viewIndex > 1) {
					pagerContainer.find("#prevPage:not([class='disabled'])").click(function(){
						layer.load();
						buildLayer(viewIndex-1);
					});
				}
				pagerContainer.find("#nextPage:not([class='disabled'])").click(function(){
					layer.load();
					buildLayer(viewIndex+1);
				});
			}
		}
		//绑定分页的事件	
		function buildAction(productLines) {
			productLines.each(function(index, element){
				var productLine = $(element);
				if (productLine && productLine.size()>0) {
					//var productLine = findParentElementByClassName(element, "product-line");
					var productId = productLine.attr("productId");
					var productStoreId = productLine.find(".product-store").attr("productStoreId");

					var onSalePrice=0, requireInventory="Y", availableAll="N";
					var productInventoryDiv = productLine.find(".product-inventory");
					if (productInventoryDiv.size() > 0) {
						onSalePrice = productLine.find(".product-price>.onsale-price>span").attr("price");
						requireInventory = productInventoryDiv.attr("requireInventory");
						availableAll = productInventoryDiv.attr("availableAll");
						
						if (onSalePrice > 0 && requireInventory=="N" && availableAll=="N") {
							//急件调货
							var dispatchAction = productLine.find(".product-action .action-dispatch");
							dispatchAction.text(args.dispatch.name).show();
							if (args.dispatch) {
								dispatchAction.click(function(){
										args.dispatch.action(productId, productStoreId);
									});
							}
						} else if (availableAll=="N") {
							//缺货登记
							var outStockAction = productLine.find(".product-action .action-outStock");
							outStockAction.text(args.outStock.name).show();
							if (args.outStock) {
								outStockAction.click(function(){
										args.outStock.action(productId, productStoreId);
									});
							}
						} else {
							//加入购物车
							var addCartAction = productLine.find(".product-action .action-addCart");
							addCartAction.text(args.addCart.name).show();
							if (onSalePrice > 0) {
								if (args.addCart) {
									addCartAction.click(function(){
										
										args.addCart.action(productId, productStoreId);
										
									});
								}
							} else {
								addCartAction.addClass("disabled").attr("title", "暂无报价，无法加入购物车！");
							}
						}
					}else{
						var addCartAction = productLine.find(".product-action .action-addCart");
						addCartAction.text(args.addCart.name).show();
						addCartAction.addClass("disabled").attr("title", "没有登录，无法加入购物车！");
					}
				}
			});
		}
		
		function initArgs() {
			args.frame = args.frame || null;
			if (!args.frame) {
				args.frame = $("_no_frame__");
			} else {
				if (typeof(args.frame.parnet) != "function") args.frame = $(args.frame);
			}
			
			if (!target) 
				target = $("_no_target__");
			else {
				if (args.frame.size() > 0) {
					if (typeof(target.parnet) == "function") target = target.selector;
					target = args.frame.contents().find(target);
				} else {
					if (typeof(target.parnet) != "function") target = $(target);
				}
			}
			
			args = args || {};
			args.serviceUrl = args.serviceUrl || "/portal/products"; 
			args.partsNo = args.partsNo || ""; 
			args.autoMove = args.autoMove || false; 
		}
		
		if (target.size() == 1) {
			$(window).resize(function(){
				moveToTarget();
			});
		}
		
		function moveToTarget() {
			if (target.size() == 1) {
				var x = target.offset().top + target.outerHeight(true) + 1;
				var y = target.offset().left;
				if (args.frame.size()>0) {
					x += args.frame.offset().top;
					y += args.frame.offset().left;
				}
				if (args.autoMove) {
					var rightBorder = $("body>#content").offset().left + $("body>#content").width();
					if (rightBorder - $layer.width() < y) {
						y = y - $layer.width() + target.width();
					}
					var realContentHeight = $layer.find(".products-list").outerHeight(true);
					if ($(window).height()>realContentHeight*2 && $(window).height() - x + $(window).scrollTop() < realContentHeight) {
						x = x - realContentHeight - target.outerHeight(true) - 2;
					}
				}
				
				$layer.css({top:x, left:y});
			}
		}
	};
}