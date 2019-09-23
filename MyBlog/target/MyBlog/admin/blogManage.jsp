<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>博客管理页面</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">

    /*获取该行的博客类型名称*/
    function formatBlogtype(val,row){
        return val.typeName;
    }

    /*根据标题模糊查询博客*/
    function searchBlog() {
        $("#dg").datagrid("load",{"title":$("#select_title").val()});
    }

    /*删除博客*/
    function deleteBlog() {
        var selectedRows=$("#dg").datagrid("getSelections");
        if(selectedRows.length==0){
            $.messager.alert("系统提示","请选择要删除的数据！");
            return;
        }
        var strIds=[];
        for (var i=0;i<selectedRows.length;i++) {
            strIds.push(selectedRows[i].id);
        }
        var ids=strIds.join(",");
        $.messager.confirm("系统提示","确定要删除这<font color='red'>"+selectedRows.length+"</font>条数据吗？",function (r) {
            if(r){
                $.post("${pageContext.request.contextPath}/admin/blog/deleteBlog",{ids:ids},function (result) {
                    if(result.info){
                        $.messager.alert("系统提示","删除成功！");
                        //删除成功后，重新加载博客列表
                        $("#dg").datagrid("reload");
                    }else {
                        $.messager.alert("系统提示","删除失败！");
                    }
                },"json")
            }
        })
    }

    /*打开修改博客编辑页面，修改一条博客*/
    function openBlogUpdatePage(){
        var selectedRows=$("#dg").datagrid("getSelections");
        if(selectedRows.length!=1){
            $.messager.alert("系统提示","请选择一条要修改的数据！");
            return;
        }
        var row=selectedRows[0];
        window.parent.openTab("修改博客","modifyBlog.jsp?id="+row.id,"icon-writeBlog");
    }

    /*点击标题弹出用户预览界面*/
    function formatTitle(val,row) {
        return "<a target='_blank' href='${pageContext.request.contextPath}/blog/articles/"+row.id+".html'>"+val+"</a>";
    }


</script>

</head>
<body style="margin: 10px">
    <table id="dg" title="博客管理" class="easyui-datagrid" fitcolumns="true"
           pagination="true" rownumbers="true" fit="true" toolbar="#tb"
           url="${pageContext.request.contextPath}/admin/blog/findAllBlogByParam">
        <thead>
            <tr>
                <th field="cb" checkbox="true" align="center"></th>
                <th field="id" width="20" align="center">编号</th>
                <th field="title" width="200" align="center" formatter="formatTitle">摘要</th>
                <th field="releaseDate" width="50" align="center">发布日期</th>
                <th field="blogType" width="50" align="center" formatter="formatBlogtype">博客类别</th>
            </tr>
        </thead>
    </table>

    <div id="tb">
        <div>
            <a href="javascript:openBlogUpdatePage()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
            <a href="javascript:deleteBlog()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
        </div>
        <div>
            <%--按下回车键，也能模糊查询--%>
            &nbsp;标题：&nbsp;<input type="text" id="select_title" size="20" onkeydown="if(event.keyCode=13) searchBlog()"/>
            <a href="javascript:searchBlog()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
        </div>
    </div>

    <div id="dlg" class="easyui-dialog" style="width: 500px;height: 180px;padding: 10px 20px"
         closed="true" buttons="#dlg-buttons">
        <form id="fm" method="post">
            <table cellspacing="8px">
                <tr>
                    <td>博客标题：</td>
                    <td><input type="text" id="typeName" name="typeName" class="easyui-validatebox" required="true"/></td>
                </tr>
                <tr>
                    <td>博客类别：</td>
                    <td><input type="text" id="orderNo" name="orderNo" class="easyui-validatebox" required="true" style="width: 60px"/></td>
                </tr>
            </table>
        </form>
    </div>



</body>
</html>