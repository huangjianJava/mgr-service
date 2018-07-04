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
        cache : false,
        type : 'GET',
        dataType : 'json',
        url : '/mgr-sys/resources/listMenus',
        error : function() {
            layer.msg('数据请求失败!', { icon: 2, time: 1000 });
        },
        success : function(data) {
            if (data && data.success) {
                zNodes = data.data;
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
            $("#resourceValue").val($("#resourceValue").val().trim());
            
             /*$.ajax({
                  cache: false,
                  type: $("#methodType").val(),
                  contentType: "application/json",
                  url: "#",
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
              });*/
        },
        rules: {
            code: {
                required: true,
                checkCode:true
            },
            name: {
                required: true,
                checkBlank:true
            },
            sequence: {
                required: true,
                checkSeq:true
            }
        },
        messages: {
            code: {
                required: "菜单编码必填"
            },
            name: {
                required: "菜单名称必填"
            },
           sequence: {
                required: "菜单序列必填",
            }
        }
    });
    
     $.validator.addMethod("checkCode", function(value, element) {
         var regex = /^[a-zA-Z0-9_]+$/;
         return this.optional(element) || (regex.test(value));
     }, "只能由数字、字母及_组成");
     
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
        area: ['650px', '400px'],
        content: $("#menuModal")
    });
    
    $(':input', '#menuSaveForm').not(':button, :submit, :reset, :hidden').val('').removeAttr('selected').removeAttr('disabled').removeAttr('readOnly');
    
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    var nodes = treeObj.getSelectedNodes();
    // 确保已经选择更改菜单项目
    if (nodes && nodes.length > 0) {
        $('#parentId').val(nodes[0].id);
    }
    
};

function openUpdateMenus(resourceId) {
    
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    var nodes = treeObj.getSelectedNodes();
    // 确保已经选择更改菜单项目
    if (nodes && nodes.length > 0) {
        
        /*$.ajax({
            url: "/systemMgr/resources/"+nodes[0].id+"/detail",
            type: 'get',
            dataType: 'json',
            success: function (data) {
                console.log(data);
                if(data.data){
                    var resource = data.data;
                    $("#id").val(resource.id);
                    $('#parentId').val(resource.parentId);
                    $('#parentId').attr("disabled","disabled").attr('readOnly','readOnly');
                    $("#code").val(resource.code).attr("disabled","disabled");
                    $("#name").val(resource.name);
                    $("#resourceValue").val(resource.resourceValue);
                    $("#sequence").val(resource.sequence);
                }
            },
            error: function (data, status) {
            }
        });*/
        
        
        $("#methodType").val("PUT");
        $(':input', '#menuSaveForm').removeClass("formerror");
        $("[id$=error]").remove();
        
        layer.open({
            type: 1,
            title: '修改菜单',
            area: ['650px', '400px'],
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