<!DOCTYPE html>
<html>
<head>
    
    <title>权限管理</title>
    <#include "../includes/head.ftl" />
    <link rel="stylesheet" type="text/css" href="${commonStatic}/plugins/bootstrap/css/bootstrap.min.css" />
    <link rel="stylesheet" href="${commonStatic}/css/resource/system.css" type="text/css">
    <link rel="stylesheet" href="${commonStatic}/css/resource/resource.css" type="text/css">
    <link rel="stylesheet" href="${commonStatic}/plugins/zTree/v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
    
    
    <script type="text/javascript" src="${commonStatic}/plugins/zTree/v3/js/jquery.ztree.core-3.5.min.js"></script>
    <script type="text/javascript" src="${commonStatic}/plugins/zTree/v3/js/jquery.ztree.excheck-3.5.min.js"></script>
    <script type="text/javascript" src="${commonStatic}/js/resource/menuList.js"></script>
    <style>
    	/*解决样式冲突*/
    	.content ul {
			    text-indent: 0px;
		}
    </style>
</head>
<body>
<div class="content">
    <div class="header-menu">
        <a class="active" href="/resources/pages">菜单维护</a>
        <a href="#">菜单列表</a>
        
    </div>

    <div class="row" style="margin-bottom:30px;">
    </div>
    
     <div class="col-xs-12">
          <button type="button" class="btn btn-info query-btn" onclick="openAddMenus()">新增菜单</button>
          <button type="button" class="btn btn-info query-btn" style="margin-left: 20px;"  onclick="openUpdateMenus()">修改</button>
          <button type="button" class="btn btn-info query-btn" style="margin-left: 20px;"  onclick="deleteMenu()">删除</button>
          <button type="button" class="btn btn-info query-btn" style="margin-left: 20px;"  onclick="expandAllNode()">全部展开</button>
          <button type="button" class="btn btn-info query-btn" style="margin-left: 20px;"  onclick="unExpandAllNode()">全部收缩</button>
     </div>
    
    <div class="row text-right padding-right-10">
    </div>
    
    <div class="resources-query-div col-xs-4" style="height: auto;margin-top:20px;">
	    <div class="zTreeDemoBackground" >
	        <ul id="treeDemo" class="ztree"></ul>
	    </div>
    </div>

   <div id="menuModal" class="resource-div" style="display:none;">
            <form id="menuSaveForm" action="#" method="POST">
              <input type="hidden" value="POST" name="methodType" id="methodType"/>
              <input type="hidden" value="MENU" name="resourceType"/>
              <input type="hidden" value="" name="id" id="id"/>

            <div class="form-group">
                <table style="width: 100%" style="margin-top: 30px;">
                    <tr>
                        <td class="col-xs-3" style="text-align: right; padding: 10px;">
                        <label for="parentId" class="control-label">父级菜单：</label>
                        </td>
                        <td class="col-xs-9" style="text-align: left; padding: 10px;">
                            <select class="form-input" id="parentId" name="parentId" >
                                <option value=""></option>
                            <#if menus ?? && (menus?size > 0)>
                                <#list menus as record>
                                        <option value="${(record.id)!}">${(record.name)!}</option>
                                </#list>
                            </#if>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="col-xs-3" style="text-align: right; padding: 10px;">
                        <label for="code" class=" control-label"><i  class="icon-c input-required-icon"></i>菜单编码：</label>
                        </td>
                        <td class="col-xs-9" style="text-align: left; padding: 10px;">
                            <input id="code" name="code" class="form-input" type="text" value="" maxlength="30" placeholder="菜单编码"/>
                        </td>
                    </tr>
                    <tr>
                        <td  style="text-align: right; padding: 10px;">
                        <label for="name" class="control-label"><i  class="icon-c input-required-icon"></i>菜单名称：</label>
                        </td>
                        <td  style="text-align: left; padding: 10px;">
                            <input id="name" name="name" class="form-input" type="text" placeholder="菜单名称" maxlength="30" value=""/>
                        </td>
                    </tr>
                    <tr>
                        <td  style="text-align: right; padding: 10px;">
                        <label for="resourceValue" class=" control-label">菜单URL：</label>
                        </td>
                        <td  style="text-align: left; padding: 10px;">
                            <input id="resourceValue" name="resourceValue" class="form-input" type="text" placeholder="菜单URL" maxlength="256" value=""/>
                        </td>
                    </tr>
                    <tr>
                        <td  style="text-align: right; padding: 10px;">
                        <label for="sequence" class=" control-label"><i  class="icon-c input-required-icon"></i>菜单序列：</label>
                        </td>
                        <td  style="text-align: left; padding: 10px;">
                            <input id="sequence" name="sequence" class="form-input" type="text" placeholder="菜单序列" maxlength="9" value=""/>
                        </td>
                    </tr>
                </table>
            <div align="center" style="padding-top: 10px;">
                <button class="btn btn-info query-btn" type="submit">保存</button>
                <button class="btn btn-danger query-btn" type="button" style="margin-left: 20px;" onclick="closeMenus()"> 取消 </button>
            </div>
            </form>
    </div>

    </body>
    </html>
    