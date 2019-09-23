<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>友情链接管理页面</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript">

        var url;

        /*弹出添加友情链接的对话框*/
        function openLinkAddDialog() {
            $("#dlg").dialog("open").dialog("setTitle","添加友情链接信息");
            url="${pageContext.request.contextPath}/admin/link/saveLink";
        }

        /*弹出修改链接的对话框*/
        function openLinkUpdateDialog() {
            var selectedRows=$("#dg").datagrid("getSelections");
            if(selectedRows.length!=1){
                $.messager.alert("系统提示","请选择一条要修改的数据！");
                return;
            }
            var row=selectedRows[0];
            $("#dlg").dialog("open").dialog("setTitle","修改友情链接信息");
            /*修改前回显该行数据*/
            $("#fm").form("load",row);
            url="${pageContext.request.contextPath}/admin/link/saveLink?id="+row.id;
        }

        /*保存友情链接*/
        function saveLink() {
            $("#fm").form("submit",{
                url:url,
                onSubmit:function(){
                    return $(this).form("validate");
                },
                success:function (result) {
                    var result=eval('('+result+')');
                    if(result.success){
                        $.messager.alert("系统提示","保存成功！");
                        //清空输入框
                        resetValue();
                        //关闭对话框
                        $("#dlg").dialog("close");
                        //刷新查询结果
                        $("#dg").datagrid("reload");
                    }else {
                        $.messager.alert("系统提示","保存失败！");
                        return;
                    }
                }
            });
        }

        /*删除链接*/
        function deleteLink() {
            var selectedRows=$("#dg").datagrid("getSelections");
            if(selectedRows.length==0){
                $.messager.alert("系统提示","请选择要删除的数据！");
                return;
            }
            var strIds=[];
            for (var i=0;i<selectedRows.length;i++) {
                strIds.push(selectedRows[i].id);
            }
            //将数组元素通过“,”连接成字符串
            var ids=strIds.join(",");
            $.messager.confirm("系统提示","确定要删除这<font color='red'>"+selectedRows.length+"</font>条数据吗？",function (r) {
                if(r){
                    $.post("${pageContext.request.contextPath}/admin/link/deleteLink",{ids:ids},function (result) {
                        if(result.info){
                            $.messager.alert("系统提示","删除成功！");
                            //删除成功后，重新加载列表
                            $("#dg").datagrid("reload");
                        }else {
                            $.messager.alert("系统提示","删除失败！");
                        }
                    },"json")
                }
            })
        }

        /*重置弹出进行添加的对话框*/
        function resetValue() {
            $("#linkName").val("");
            $("#linkUrl").val("");
            $("#orderNo").val("");
        }

        /*关闭对话框*/
        function closeLinkDialog() {
            $("#dlg").dialog("close");
            resetValue();
        }

    </script>

</head>
<body style="margin: 10px">
<table id="dg" title="友情链接管理" class="easyui-datagrid" fitcolumns="true"
       pagination="true" rownumbers="true" fit="true" toolbar="#tb"
       url="${pageContext.request.contextPath}/admin/link/showAllLink">
    <thead>
    <tr>
        <th field="cb" checkbox="true" align="center"></th>
        <th field="id" width="20" align="center">编号</th>
        <th field="linkName" width="100" align="center">链接名称</th>
        <th field="linkUrl" width="150" align="center">链接地址</th>
        <th field="orderNo" width="50" align="center" >序号</th>
    </tr>
    </thead>
</table>

<div id="tb">
    <a href="javascript:openLinkAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
    <a href="javascript:openLinkUpdateDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
    <a href="javascript:deleteLink()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
</div>

<div id="dlg" class="easyui-dialog" style="width: 500px;height: 180px;padding: 10px 20px"
     closed="true" buttons="#dlg-buttons">
    <form id="fm" method="post">
        <table cellspacing="8px">
            <tr>
                <td>链接名称：</td>
                <td><input type="text" id="linkName" name="linkName" class="easyui-validatebox" required="true"/></td>
            </tr>
            <tr>
                <td>链接地址：</td>
                <td><input type="text" id="linkUrl" name="linkUrl" class="easyui-validatebox" required="true"/></td>
            </tr>
            <tr>
                <td>友情链接序号：</td>
                <td><input type="text" id="orderNo" name="orderNo" class="easyui-validatebox" required="true" style="width: 60px"/></td>
            </tr>
        </table>
    </form>
</div>

<div id="dlg-buttons">
    <a href="javascript:saveLink()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:closeLinkDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>

</body>
</html>