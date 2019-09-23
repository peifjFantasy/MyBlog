<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
         isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript">
    /**
     * 显示所有历史评论
     */
    function showOtherComment() {
        $(".otherComment").show();
    }

    /**
     * 提交评论信息
     */
    function submitData(){
         var content=$("#content").val();
         var imageCode=$("#imageCode").val();
         if(content==null || content==""){
             $.messager.alert("请输入评论内容！");
             return;
         }
         if(imageCode==null || imageCode==""){
             $.messager.alert("请输入验证码！")
             return;
         }
         //使用ajax提交数据
         $.post("${pageContext.request.contextPath}/comment/saveComment",
                {"imageCode":imageCode,"content":content,"blog.id":"${blog.id}"},function(result) {
             if(result.info){
                 window.location.reload();
                 $.messager.alert("评论发表成功！审核通过后显示！");
             }else {
                 $.messager.alert(result.errorInfo);
             }
        },"json")
    }

    /**
     * 重新加载验证码
     */
    function loadImage() {
        $("#randImage").attr("src","${pageContext.request.contextPath}/image.jsp?"+new Date());
    }

    /**
     * 根据关键字查询
     */
    function queryKey(keyWord){
        $("#hq").val(keyWord);
        $("#queryForm").submit();
    }
</script>

<%--显示博客详情区域--%>
<div class="data_list">
    <div class="data_list_title">
        <img src="${pageContext.request.contextPath}/static/images/blog_show_icon.png"/>
        博客信息
    </div>
    <div>
        <div class="blog_title"><h3><strong>${blog.title}</strong></h3></div>
        <div style="padding-left: 380px;padding-bottom: 20px;padding-top: 10px">
            <div class="bshare-custom">
                <a title="分享到QQ空间" class="bshare-qzone"></a>
                <a title="分享到新浪微博" class="bshare-sinaminiblog"></a>
                <a title="更多平台" class="bshare-more bshare-more-icon more-style-addthis"></a>
                <script type="text/javascript" charset="utf-8"
                        src="http://static.bshare.cn/b/buttonLite.js#style=-1&amp;uuid=&amp;pophcol=1&amp;lang=zh">
                </script>
                <script type="text/javascript" charset="utf-8"
                        src="http://static.bshare.cn/b/bshareC0.js">
                </script>
            </div>
        </div>
        <div class="blog_info">
            发布时间：【<fmt:formatDate value="${blog.releaseDate}" type="date" pattern="yyyy-MM-dd HH:mm"/>】&nbsp;&nbsp;
            博客类别：${blog.blogType.typeName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            阅读数：${blog.clickHit}&nbsp;&nbsp;
            评论数：${blog.replyHit}
        </div>
        <div class="blog_content">
            ${blog.content}
        </div>
        <div class="blog_keyWord">
            <font><strong>关键字：</strong></font>
            <c:choose>
                <c:when test="${keyWords==null}">
                    &nbsp;&nbsp;无
                </c:when>
                <c:otherwise>
                    <form id="queryForm" action="${pageContext.request.contextPath}/blog/searchBlog.html" method="post">
                        <input type="hidden" id="hq" name="q"/>
                        <c:forEach items="${keyWords}" var="keyWord">
                            &nbsp;&nbsp;<a href="javascript:queryKey('${keyWord}')">${keyWord}</a>&nbsp;&nbsp;
                        </c:forEach>
                    </form>
                </c:otherwise>
            </c:choose>


        </div>
        <div class="blog_lastAndNextPage">
            <p>上一篇：<c:if test="${lastBlog==null}">
                        已经没有了
                   </c:if>
                   <c:if test="${lastBlog!=null}">
                        <a href="${pageContext.request.contextPath}/blog/articles/${lastBlog.id}.html">${lastBlog.title}</a>
                   </c:if>
            </p>
            <p>下一篇：<c:if test="${nextBlog==null}">
                        已经没有了
                   </c:if>
                   <c:if test="${nextBlog!=null}">
                        <a href="${pageContext.request.contextPath}/blog/articles/${nextBlog.id}.html">${nextBlog.title}</a>
                   </c:if>
            </p>
        </div>
    </div>
</div>

<%--显示用户评论区域--%>
<div class="data_list">
    <div class="data_list_title">
        <img src="${pageContext.request.contextPath}/static/images/comment_icon.png">
        评论信息
        <c:if test="${commentList.size()>10}">
            <a href="javascript:showOtherComment()" style="float: right;padding-right: 40px">显示所有评论</a>
        </c:if>
    </div>
    <div class="commentDatas">
        <c:choose>
            <c:when test="${commentList.size()==0}">
                暂无评论
            </c:when>
            <c:otherwise>
                <c:forEach items="${commentList}" var="comment" varStatus="status">
                    <c:choose>
                        <c:when test="${status.index < 10}">
                            <div class="comment">
                                <span>
                                    <strong>
                                        ${status.index + 1}楼&nbsp;&nbsp;&nbsp;&nbsp;
                                        ${comment.userIp}：&nbsp;&nbsp;
                                    </strong>
                                        ${comment.content}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        【<fmt:formatDate value="${comment.commentDate}" type="date" pattern="yyyy-MM-dd HH:mm"/>】
                                </span>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="otherComment">
                                <div class="comment">
                                <span>
                                    <strong>
                                        ${status.index + 1}楼&nbsp;&nbsp;&nbsp;&nbsp;
                                        ${comment.userIp}：&nbsp;&nbsp;
                                    </strong>
                                        ${comment.content}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        【<fmt:formatDate value="${comment.commentDate}" type="date" pattern="yyyy-MM-dd HH:mm"/>】
                                </span>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<%--用户发表评论区域--%>
<div class="data_list">
    <div class="data_list_title">
        <img src="${pageContext.request.contextPath}/static/images/publish_comment_icon.png">
        发表评论
    </div>
    <div class="publish_comment">
        <div>
            <textarea rows="3" style="width: 100%" id="content" name="content" placeholder="来说两句吧..."></textarea>
        </div>
        <div class="verCode">
            验证码：<input type="text" name="imageCode" id="imageCode" size="10"
                       onkeydown="if(event.keyCode==13) submitData()">
            &nbsp;&nbsp;
            <img src="${pageContext.request.contextPath}/image.jsp" name="randImage" id="randImage"
                 title="换一张试试" onclick="javascript:loadImage()" width="60" height="20" border="1"
                 align="absmiddle">
        </div>
        <div class="publishButton">
            <button class="btn btn-primary" type="button" onclick="submitData()">发表评论</button>
        </div>
    </div>


</div>

