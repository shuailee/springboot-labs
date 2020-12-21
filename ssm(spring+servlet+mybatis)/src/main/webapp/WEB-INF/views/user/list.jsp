<%--
  Created by IntelliJ IDEA.
  User: shuai.li
  Date: 2017/12/20
  Time: 14:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>SpringMVC 用户管理</title>

    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h1>SpringMVC 博客系统-用户管理</h1>
    <hr/>

    <h3>所有用户 <a href="/admin/user/add" type="button" class="btn btn-primary btn-sm">添加</a></h3>
    <!-- 如果用户列表为空 -->
    <c:if test="${empty userList}">
        <div class="alert alert-warning" role="alert">
            <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>User表为空，请<a href="/admin/user/add" type="button" class="btn btn-primary btn-sm">添加</a>
        </div>
    </c:if>

    <!-- 如果用户列表非空 -->
    <c:if test="${!empty userList}">
        <table class="table table-bordered table-striped">
            <tr>
                <th>ID</th>
                <th>姓名</th>
                <th>地址</th>
                <th>操作</th>
            </tr>

            <c:forEach items="${userList}" var="user">
                <tr>
                    <td>${user.user_id}</td>
                    <td>${user.user_name}</td>
                    <td>${user.user_address} ${user.user_telephone}</td>
                    <td>
                        <%--/user/push/${user.user_id}--%>
                        <input  type="button" data-user_id="${user.user_id}" data-user_name="${user.user_name}" data-user_telephone="${user.user_telephone}"
                                class="btn btn-sm btn-success push" value="push">
                        <a href="/user/show/${user.user_id}" type="button" class="btn btn-sm btn-success">详情</a>
                        <a href="/user/update/${user.user_id}" type="button" class="btn btn-sm btn-warning">修改</a>
                        <a href="/user/delete/${user.user_id}" type="button" class="btn btn-sm btn-danger">删除</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>

<%--<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->--%>
<%--<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>--%>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<script>
    $(function () {
        $(".push").click(function(){
            var url='<%=request.getContextPath()%>/user/push';
            var data1={"user_name":$(this).data('user_name'),"user_age":"80","user_id":$(this).data('user_id')};
            $.ajax({
                type:'POST',
                contentType : 'application/json',
                url:url,
                dataType:"json",
                data:JSON.stringify(data1),
                async:false,
                success:function(data){
                    alert("提交成功！");
                },
                error: function(XMLHttpRequest, textStatus, errorThrown){
                    alert(XMLHttpRequest.status);
                    alert(XMLHttpRequest.readyState);
                    alert(textStatus);
                }
            })
        });
    })


</script>

</body>
</html>
