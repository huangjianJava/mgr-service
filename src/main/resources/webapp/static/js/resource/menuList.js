var setting = {
    check : {
        enable : false
    },

    data : {
        simpleData : {
            enable : true
        }
    },
    callback : {
        onCheck : onCheck
    }

};

var zNodes;
$(document).ready(function() {
    
    $.ajax({
        async : false,
        type : 'POST',
        dataType : 'json',
        data:JSON.stringify(new Object()),
        contentType: "application/json",
        url : '/mgr-sys/menu/list',
        error : function() {
            layer.msg('数据请求失败!', { icon: 2, time: 1000 });
        },
        success : function(data) {
            if (data && data.success) {
            	var list ={};
            	for(var i=0;i<data.data.length;i++) {
            		var map = new Map();
            		map.put("id",data.data[i].id);
            		map.put("name", data.data[i].name);
        			map.put("pId", data.data[i].parentId);
        			list.push(map);
        			if(data.data[i].children.length>0) {
        				for(var j=0;j<data.data[i].children.length;j++) {
        					var childMap = new Map();
        					childMap.put("id",data.data[i].children[j].id);
            				childMap.put("name", data.data[i].children[j].name);
        					childMap.put("pId", data.data[i].children[j].parentId);
        					list.push(childMap);
        				}
        				
        			}
            	}
                zNodes = list;
            }
        }
    });
    
    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    var nodes = treeObj.getNodes();
    if (nodes.length > 0) {
        if (nodes[0].children.length > 0) 
            treeObj.expandNode(nodes[0].children[0]);
    };
    

 var _menu_validated = $("#menuSaveForm").validate({
        
        errorClass: "formerror",
        submitHandler: function (form) {
            layer.load();
            $("#name").val($("#name").val().trim());
            $("#path").val($("#path").val().trim());
            $("#perms").val($("#perms").val().trim());
            $("#iconCls").val($("#iconCls").val().trim());
            $("#orderNum").val($("#orderNum").val().trim());
            
             $.ajax({
                  cache: false,
                  type: $("#methodType").val(),
                  contentType: "application/json",
                  url: "/mgr-sys/menu/add",
                  data: JSON.stringify($('#menuSaveForm').serializeObject()),
                  success: function () {
                      layer.closeAll();
                      layer.msg('菜单保存成功!', {
                          icon: 1,
                          time: 1000
                      }, function () {
                          window.location.href="/mgr-sys/resources/pages";
                      });
                  },
                  error: function (e) {
                      layer.closeAll();
                      var msg ="菜单保存失败，请重试.";
                      if(e && e.responseText){
                          msg = JSON.parse(e.responseText).message;
                          if('No message available' == msg){
                              msg ="菜单保存失败，请重试.";
                          }
                      }
                      layer.msg(msg, {
                          icon: 2,
                          time: 3000
                      });
                  }
              });
        },
        rules: {
            perms: {
                required: true,
                checkCode:true
            },
            name: {
                required: true,
                checkBlank:true
            },
            orderNum: {
                required: true,
                checkSeq:true
            }
        },
        messages: {
            perms: {
                required: "菜单授权必填"
            },
            name: {
                required: "菜单名称必填"
            },
           orderNum: {
                required: "菜单序列必填",
            }
        }
    });
    
     $.validator.addMethod("checkCode", function(value, element) {
         var regex = /^[a-zA-Z:]+$/;
         return this.optional(element) || (regex.test(value));
     }, "只能由字母和:组成");
     
     $.validator.addMethod("checkSeq", function(value, element) {
         var regex = /^[0-9]+$/;
         return this.optional(element) || (regex.test(value));
     }, "只能输入数字");
     
     $.validator.addMethod("checkBlank", function(value, element) {
         return this.optional(element) || ('' != value.trim());
     }, "菜单名称必填");
    
});

function zTreeOnClick(event, treeId, treeNode) {
    alert("节点名称：" + treeNode.name + "----" + "节点的id值：" + treeNode.id);
};

function onCheck(e, treeId, treeNode) {
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo"), nodes = treeObj.getCheckedNodes(true), v = "";
    for (var i = 0; i < nodes.length; i++) {
        v += nodes[i].name + ",";
    }
}



function deleteMenu() {
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    var nodes = treeObj.getSelectedNodes();
    if (nodes && nodes.length > 0) {
        if (nodes[0].isParent) {
            layer.msg('不能删除有子项的菜单!', { icon: 2, time: 1000 });
        } else {
            
            layer.confirm("是否确定删除该资源？", {
                icon: 3,
                title: '提示'
            }, function (e) {

                var index = layer.load();
                /*$.ajax({
                    async: false,
                    type: 'DELETE',
                    url: '/systemMgr/resources/' + nodes[0].id ,
                    success: function (data) {
                        layer.closeAll();
                        layer.msg('操作成功!', {
                            icon: 1,
                            time: 1000
                        }, function () {
                            window.location.reload();
                        });
                    },
                    error: function () {
                        layer.close(index);
                        layer.msg('操作失败，请联系管理员.', {
                            icon: 2,
                            time: 2000
                        });
                    }
                });*/
            });
            
        }
    } else {
        layer.msg('请选择要删除的菜单节点!', { icon: 2, time: 1000 });
    }
}
function expandAllNode() {
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    treeObj.expandAll(true);
}

function unExpandAllNode() {
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    treeObj.expandAll(false);
}


function openAddMenus() {
    
    $("#methodType").val("POST");
    $(':input', '#menuSaveForm').removeClass("formerror");
    $("[id$=error]").remove();
    $("#parentId option").each(function() {  $(this).removeAttr('selected');});

    layer.open({
        type: 1,
        title: '新增菜单',
        area: ['650px', '550px'],
        content: $("#menuModal")
    });
    
    $(':input', '#menuSaveForm').not(':button, :submit, :reset, :hidden').val('').removeAttr('selected').removeAttr('disabled').removeAttr('readOnly');
    
    
};

function openUpdateMenus(resourceId) {
    
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    var nodes = treeObj.getSelectedNodes();
    // 确保已经选择更改菜单项目
    if (nodes && nodes.length > 0) {
      	var resource = nodes[0];
        $("#type").val(resource.type);
        $('#type').attr("disabled","disabled").attr('readOnly','readOnly');
        $('#parentId').val(resource.parentId);
        $('#parentId').attr("disabled","disabled").attr('readOnly','readOnly');
        $("#name").val(resource.name);
        $("#path").val(resource.path);
        $("#perms").val(resource.perms);
        $("#iconCls").val(resource.iconCls);
        $("#orderNum").val(resource.orderNum);
        $("#methodType").val("PUT");
        $(':input', '#menuSaveForm').removeClass("formerror");
        $("[id$=error]").remove();
        
        layer.open({
            type: 1,
            title: '修改菜单',
            area: ['650px', '550px'],
            content: $("#menuModal")
        });
        
    } else {
        layer.msg('请选择需要修改的菜单节点!', { icon: 2, time: 1000 });
    }
    
};

function closeMenus(){
    $('#menuModal').hide();
    layer.closeAll();
 }