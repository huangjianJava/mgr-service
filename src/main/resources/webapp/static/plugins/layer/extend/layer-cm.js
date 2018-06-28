/**
 * 用来在页面上创建车型选择弹出层，依赖 layer
 * 作者：许健
 * 示例：
 * layer.showCarModel({            
        title : "车型",
        cmServiceUrl : "……",
        vinServiceUrl : "……",
		complet : function() {
			do some thing after complet
		}, 
		close : function() {
			do some thing after close
		}
 * });
 */
if (typeof layer != "undefined") {

	layer.use("skin/layer-cm.css", function() {
		layer.layui_layer_extendlayerextjs = !0
	}), layer.showCarModel = function(args) {
		var cmLayer;
		// 存储车型ID
		var arrayObj = new Array();

		// 默认不过滤掉没有专业图形的车型
		if (typeof(args.epcFliter) != "boolean" ) args.epcFliter = false;

		//布局
		function layout(layerIndex) {
			args.container = $("<div />")
			.addClass("cm-container")
			.appendTo($("#layui-layer" + layerIndex + " .layui-layer-content"));

			if(args.multiple == 1){

				$("<div />")
				.addClass("vin-row")
				.append($("<input />").attr("type", "input").attr("placeholder", "请输入车型"))
				.append(
						$("<input />")
						.attr("type", "button")
						.val("查询")
						.click(findCarModelByCarModel)
				)
				.append($("<div />").addClass("msg-line"))
				.appendTo(args.container);

			}
			else{

				$("<div />")
				.addClass("vin-row")
				.append($("<input />").attr("type", "input").attr("placeholder", "请输入17位VIN码"))
				.append(
						$("<input />")
						.attr("type", "button")
						.val("查询")
						.click(findCarModelByVIN)
				)
				.append($("<div />").addClass("msg-line"))
				.appendTo(args.container);
			}




			$("<div />")
			.addClass("cm-row")
			.append($("<ul />").addClass("cm-title"))
			.append($("<div />").addClass("cm-content"))
			.appendTo(args.container);
		}
		// 初始化品牌
		function init() {
			$(".cm-title").empty();
			$(".cm-content").empty();
			$("<li />").addClass("active").text("品牌").appendTo($(".cm-title"));

			loadCarModel("type:Brand", "childCount desc, id asc", function(data) {

				var jsonResult = JSON.parse(data.result);
				//var numFound = jsonResult.response.numFound;
				$(jsonResult.response.docs).each(function(){ 

					var cmId = this.id;
					var brandName = this.carBrandName;

					var carBrandImageUrl = this.carBrandImageUrl;
					if (STATIC_ROOT) carBrandImageUrl = STATIC_ROOT + "/.." + carBrandImageUrl;

					var item = $("<div />").addClass("item-box").addClass("brand-item")
					.append($("<img />").attr("src", carBrandImageUrl))
					.append($("<div />").addClass("brand-name").text(brandName))
					.appendTo($(".cm-content"));

					if (this.childCount <= 0) {
						item.addClass("disabled");
						//item.css("display","none");
					} else {
						item.click(function(){ selectBrand(cmId, brandName); });
					}
				});

			});
		}

		// 选定品牌的动作
		function selectBrand(cmId, brandName) {
			if (!cmId) {
				cmId = $(".cm-title li:first").attr("cmId");
				brandName = $(".cm-title li:first").text();
			}
			$(".cm-title").empty();
			$(".cm-content").empty();
			$("<li />").text(brandName).attr("cmId", cmId)
			.click(function(){ init(); }).appendTo($(".cm-title"));
			$("<li />").addClass("active").text("选择车系").appendTo($(".cm-title"));
			var queryStr = "type:Series AND parentId:\"" + cmId + "\"";
			if (args.epcFliter)
				queryStr += " AND hasEPC:true";

			loadCarModel(queryStr, "manufacturer asc, id asc", function(data) {
				var manufacturerName = "", mDiv=null;
				var jsonResult = JSON.parse(data.result);
				$(jsonResult.response.docs).each(function(){ 
					var cmId = this.id;
					var setName = this.carSetName;
					if (manufacturerName == "" || manufacturerName != this.manufacturer) {
						manufacturerName = this.manufacturer;
						$("<h5 />").text(manufacturerName).appendTo($(".cm-content"));
						mDiv = $("<div />").addClass("fix").appendTo($(".cm-content"));
					}

					var item = $("<div />").addClass("item-box").addClass("text-item")
					.addClass("series-item").text(setName).appendTo(mDiv);

					if (this.childCount <= 0) {
						item.addClass("disabled");
					} else {
						item.click(function(){ selectSet(cmId, setName); });
					}

				});

			});
		}
		//选定车系的动作
		function selectSet(cmId, setName) {
			if (!cmId) {
				cmId = $(".cm-title li:eq(1)").attr("cmId");
				setName = $(".cm-title li:eq(1)").text();
			}
			$(".cm-title li:gt(0)").remove();
			$(".cm-content").empty();
			$("<li />").text(setName).attr("cmId", cmId).click(function(){ selectBrand(); }).appendTo($(".cm-title"));
			$("<li />").addClass("active").text("选择年份").appendTo($(".cm-title"));

			var queryStr = "type:Models AND parentId:\"" + cmId + "\"";
			if (args.epcFliter)
				queryStr += " AND hasEPC:true";
			loadCarModel(queryStr, "year asc", function(data) {
				var jsonResult = JSON.parse(data.result);
				$(jsonResult.response.docs).each(function(){ 
					var cmId = this.parentId;
					var year = this.year;

					if ($(".cm-content div:contains('"+year+"')").size() == 0) {
						$("<div />").addClass("item-box")
						.addClass("text-item").addClass("year-item")
						.click(function(){ selectYear(cmId, year); })
						.text(year).appendTo($(".cm-content"));
					}

				});

			});
		}
		//选定年份的动作
		function selectYear(cmId, year) {
			if (!cmId) {
				cmId = $(".cm-title li:eq(2)").attr("cmId");
				year = $(".cm-title li:eq(2)").text();
			}
			$(".cm-title li:gt(1)").remove();
			$(".cm-content").empty();
			$("<li />").text(year).attr("cmId", cmId).click(function(){ selectSet(); }).appendTo($(".cm-title"));
			$("<li />").addClass("active").text("排量").appendTo($(".cm-title"));

			var queryStr = "type:Models AND parentId:\"" + cmId + 
			"\" AND year:\"" + year + "\"";
			if (args.epcFliter)
				queryStr += " AND hasEPC:true";

			loadCarModel(queryStr, "sweptvolume asc", function(data) {

				var jsonResult = JSON.parse(data.result);
				$(jsonResult.response.docs).each(function(){ 
					var cmId = this.parentId;
					var power = this.sweptvolume;

					if ($(".cm-content div:contains('"+power+"')").size() == 0) {
						$("<div />").addClass("item-box")
						.addClass("text-item").text(power)
						.click(function(){ selectPower(cmId, year, power); })
						.appendTo($(".cm-content"));
					}
				});
			});
		}

		//选定排量的动作
		function selectPower(cmId, year, power) {

			var selectedCarModel = args.selectedCarModel;
			if(args.multiple == 1){
				if (!cmId) {

					cmId = $(".cm-title li:eq(3)").attr("cmId");
					power = $(".cm-title li:eq(3)").text();
				}
				$(".cm-title li:gt(2)").remove();
				$(".cm-content").empty();

				$(".sumit-item").remove();

				$("<li />").text(power).attr("cmId", cmId).click(function(){ selectYear(); }).appendTo($(".cm-title"));
				$("<li />").addClass("active").text("选择车型").appendTo($(".cm-title"));

				var queryStr = "type:PRODUCT_CAT_MODEL AND parentId:\"" + cmId + 
				"\" AND year:\"" + year + 
				"\" AND sweptvolume:\"" + power + "\"";

				if (args.epcFliter){
					queryStr += " AND hasEPC:true";
				}
				loadCarModel(queryStr, "id asc", function(data) {
					var jsonResult = JSON.parse(data.result);
					$(jsonResult.response.docs).each(function(){ 
						var cmId = this.id;
						var description = this.carModelDescription;
						var carBrandName = this.carBrandName;
						//取出数组中的数据arrayObj与cmId比较，如果有相同的就直接添加已经选择的样式
						//for(var i=0;i<posx.length;i++)
						$("<div />").addClass("item-box").addClass("text-item").addClass("cm-item").addClass(cmId)
						.text(this.carModelName).attr("title", this.carModelDescription).click(function(){
							//用于点击的时候，添加样式
							if($(this).filter(".multiple").length > 0)
							{
								$(this).removeClass("multiple");
								arrayObj.pop();
							}
							else{
								$(this).addClass("multiple").addClass(cmId);
								var obj={cmId:cmId,description:description,carBrandName:carBrandName};
								arrayObj.push(obj);	
							}
							//回调函数
							//selectCarModel(cmId);
						}).appendTo($(".cm-content"));

						//给已经选中的车型添加样式
						//用于一个对话中已经选择的情况
						if(arrayObj.length > 0){
							for(var i = 0;i < arrayObj.length;i++){
								$("."+arrayObj[i].cmId).addClass("multiple");
							}
						}
						//如果主页上有选定的情况应该这样走
						//判断是否已经在主页面有选定
						if(selectedCarModel.length>0)
						{
							for(i=0;i<selectedCarModel.length;i++){
								//如果该车型与主页已经选择的车型相同那么，添加样式
								if(cmId == selectedCarModel[i])
								{
									$("."+cmId).addClass("multiple").addClass(cmId);
								}
							}
						}
					});
					//--each循环
					$("<div />").text("确认").addClass("sumit-item").click(function(){
						selectCarModel_2(arrayObj);
					}).appendTo($(".cm-container"));

				});				

//				加载车型			
			}
			//下面的用于选择车型之后，跳转到爆炸图的方式
			else{

				if (!cmId) {
					cmId = $(".cm-title li:eq(3)").attr("cmId");
					power = $(".cm-title li:eq(3)").text();
				}
				$(".cm-title li:gt(2)").remove();
				$(".cm-content").empty();
				$("<li />").text(power).attr("cmId", cmId).click(function(){ selectYear(); }).appendTo($(".cm-title"));
				$("<li />").addClass("active").text("选择车型").appendTo($(".cm-title"));

				var queryStr = "type:Models AND parentId:\"" + cmId + 
				"\" AND year:\"" + year + 
				"\" AND sweptvolume:\"" + power + "\"";
				if (args.epcFliter)
					queryStr += " AND hasEPC:true";
				loadCarModel(queryStr, "id asc", function(data) {
					var jsonResult = JSON.parse(data.result);
					$(jsonResult.response.docs).each(function(){ 
						var $this = this;
						$("<div />").addClass("item-box").addClass("text-item").addClass("cm-item")
						.text(this.carModelName).attr("title", this.carModelDescription)
						.click(function(){ selectCarModel_2($this); })
						.appendTo($(".cm-content"));

					});

				});
			}

		}

		//选定车型的动作
		function selectCarModel(cmId,vin) {
			if (args.complet) args.complet(cmId, vin);
			if (cmLayer) layer.close(cmLayer);
		}


		function selectCarModel_2(arrayObj) {
			if (args.complet) args.complet(arrayObj);
			if (cmLayer) layer.close(cmLayer);
		}


		function loadCarModel(query, sort, callback) {
			var loader = layer.load();
			$.ajax({     
				url:args.cmServiceUrl,
				data: {
					"serviceName":"solrSearchCarModel",
					"q": query,
					"sort":sort
				},
				type:'get',
				dataType:'json',
				async : false, //默认为true 异步
				success:function(data){
					callback(data);
					$(".cm-container .vin-row .msg-line").text("");
					layer.close(loader);
				}
			});
		}

		function findCarModelByVIN() {
			var vinId = $(".cm-container .vin-row input[type=input]").val();
			if(vinId.length != 17) {
				$(".cm-container .vin-row .msg-line").text("VIN码必须为17位");
				return;
			}
			var loader = layer.load();
			$.ajax({     

				url:args.vinServiceUrl,
				data: {
					"vinId": vinId
				},
				type:'get',
				dataType:'json',
				async : false, //默认为true 异步
				success:function(data){
					if(data.hasEPC){
						$(".cm-title").empty();
						$(".cm-content").empty();
						$(".cm-container .vin-row .msg-line").text("");
						$("<li />").text(data.carBrandName).attr("cmId", data.carBrandId).click(init).appendTo($(".cm-title"));
						$("<li />").text(data.carSetName).attr("cmId", data.carSetId).click(function(){ selectBrand(); }).appendTo($(".cm-title"));
						$("<li />").text(data.year).attr("cmId", data.carSetId).click(function(){ selectSet(); }).appendTo($(".cm-title"));
						$("<li />").text(data.sweptvolume).attr("cmId", data.carSetId).click(function(){ selectYear(); }).appendTo($(".cm-title"));
						$("<li />").addClass("active").text("选择车型").appendTo($(".cm-title"));

						$("<div />").addClass("item-box").addClass("text-item").addClass("cm-item")
						.text(data.carModelName).click(function(){ selectCarModel_2(data); })
						.appendTo($(".cm-content"));
					}else{
						$(".cm-container .vin-row .msg-line").text("暂时无法查询到 "+vinId+" 的车辆信息。");
					}

				},
				error:function() { 
					$(".cm-container .vin-row .msg-line").text("暂时无法查询到 "+vinId+" 的车辆信息。");
				}
			});

			layer.close(loader);
		}

		function findCarModelByCarModel(){
			//表示已经选择的车型
			var selectedCarModel = args.selectedCarModel;
			var carModel = $(".cm-container .vin-row input[type=input]").val();
			if(carModel==0){
				$(".cm-container .vin-row .msg-line").text("请输入车型");
				return;
			}
			var loader = layer.load();
			var	arrayobj_1 = new Array();
			$.ajax({     
				url:args.carModelServiceUrl,
				data: {
					"carModel": carModel
				},
				type:'post',
				dataType:'json',
				async : false, //默认为true 异步
				success:function(data){
					if(data.carModels.length > 0)
					{
						for(i=0;i<data.carModels.length;i++){
							var obj={cmId:data.carModels[i].productCategoryId,categoryName:data.carModels[i].categoryName,description:data.carModels[i].description,carBrandName:"宝马"};
							arrayobj_1.push(obj);
						}

						/** 下面的用于画出图形**/   
						$(".sumit-item").remove();
						$(".cm-title").empty();
						$(".cm-content").empty();
						$(".cm-container .vin-row .msg-line").text("");
						$("<li />").addClass("active").text("选择车型").appendTo($(".cm-title"));

						$(arrayobj_1).each(function(){
							var categoryName = this.categoryName;
							var cmId =  this.cmId;
							var description = this.description;
							var carBrandName = this.carBrandName;

							//表示已经选择的车型



							$("<div />").addClass("item-box").addClass("text-item").addClass("cm-item").addClass(cmId)
							.text(categoryName).click(function(){ 


								//用于判断是选中还是取消选中
								if($(this).filter(".multiple").length > 0)
								{
									$(this).removeClass("multiple");
									arrayObj.pop();
								}
								else{

									$(this).addClass("multiple").addClass(cmId);
									var obj={cmId:cmId,description:description,carBrandName:carBrandName};
									arrayObj.push(obj);	
								}	
							})
							.appendTo($(".cm-content"));
							$(selectedCarModel).each(function(){
								if(this==cmId){
									$("."+cmId).addClass("multiple");
								}

							})


						})
						/** 上面的用于画出图形**/

						$("<div />").text("确认").addClass("sumit-item").click(function(){
							selectCarModel_2(arrayObj);
						}).appendTo($(".cm-container"));

					}else{
						$(".cm-container .vin-row .msg-line").text("暂时无法查询到该车型。");
					}

				},
				error:function(){ 
					$(".cm-container .vin-row .msg-line").text("暂时无法查询到该车型。");
				}
			});
			layer.close(loader);
		}



		var loader = layer.load();

		cmLayer = layer.open({
			type: 1,
			title: args.title,
			area: ['734px', '400px'],
			shadeClose: false, //点击遮罩关闭
			success: function(){
				layer.close(loader);
			}, 
			cancel: function() {
				if (typeof(args.close) == "function") args.close();
			}
		});

		layout(cmLayer);

		init();
	};
}