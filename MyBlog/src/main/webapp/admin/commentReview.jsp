<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>评论审核页面</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>

    <script type="text/javascript">

        /*点击标题弹出用户预览界面*/
        function formatTitle(val,row) {
            return "<a target='_blank' href='${pageContext.request.contextPath}/blog/articles/"+val.id+".html'>"+val.title+"</a>";
        }

        /**审核评论*/
        function commentReview(state) {
            //获取复选框选中的行
            var selectedRows=$("#dg").datagrid("getSelections");
            if(selectedRows.length==0){
                $.messager.alert("系统提示","请选择要审核的数据！");
                return;
            }
            var strIds=[];
            for (var i=0;i<selectedRows.length;i++) {
                strIds.push(selectedRows[i].id);
            }
            //把该数组用","连接为一个字符串，方便后端接收
            var ids=strIds.join(",");
            $.messager.confirm("系统提示","确定要审核这<font color='red'>"+selectedRows.length+"</font>条数据吗？",function (ok) {
                if(ok){
                    $.post("${pageContext.request.contextPath}/admin/comment/commentReview",{ids:ids,state:state},function (result) {
                        if(result.info){
                            $.messager.alert("系统提示","操作完成！");
                            //删除成功后，重新加载列表
                            $("#dg").datagrid("reload");
                        }else {
                            $.messager.alert("系统提示","操作失败！");
                        }
                    },"json")
                }
            })
        }


    </script>

</head>
<body style="margin: 10px">
<table id="dg" title="评论审核管理" class="easyui-datagrid" fitcolumns="true"
       pagination="true" rownumbers="true" fit="true" toolbar="#tb"
       url="${pageContext.request.contextPath}/admin/comment/showAllComment?state=0">
    <thead>
    <tr>
        <th field="cb" checkbox="true" align="center"></th>
        <th field="id" width="20" align="center">编号</th>
        <th field="userIp" width="40" align="center">评论用户&nbsp;IP</th>
        <th field="content" width="180" align="center">评论内容</th>
        <th field="commentDate" width="60" align="center">评论日期</th>
        <th field="blog" width="70" align="center" formatter="formatTitle">博客标题</th>
    </tr>
    </thead>
</table>
<div id="tb">
    <div>
        <a href="javascript:commentReview(1)" class="easyui-linkbutton" iconCls="icon-ok" plain="true">审核通过</a>
        <a href="javascript:commentReview(2)" class="easyui-linkbutton" iconCls="icon-no" plain="true">审核不通过</a>
    </div>
</div>





</body>
</html>