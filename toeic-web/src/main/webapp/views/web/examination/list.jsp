<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:url var="urlList" value="/danh-sach-bai-thi.html">

</c:url>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form action="${urlList}" method="get" id="formUrl">
    <div class="wrap">
        <div class="main">
            <div class="content">
                <div class="col span_2_of_3">
                    <div class="contact-form">
                        <div>
                        <%--<span>
                                    <input name="pojo.name" type="text" class="textbox" value="${items.pojo.name}"/>
                                </span>--%>

                        </div>
                        <div>
                            <button id="btnSearch" class="btn btn-sm btn-success">
                                <fmt:message key="label.search" bundle="${lang}"/>
                                <i class="ace-icon fa fa-arrow-right icon-on-right bigger-110"></i>
                            </button>

                        </div>
                    </div>
                </div>
                <c:forEach var="item" items="${items.listResult}">
                    <div class="image group">
                            <%-- <div class="grid images_3_of_1">
                                 <img src="<c:url value="/repository/${item.image}"/>" alt="" />
                             </div>--%>
                        <div class="grid news_desc">
                            <h3>${item.name}</h3>
                            <c:url var="detailUrl" value="/bai-thi-thuc-hanh.html">
                                <c:param name="examinationId" value="${item.examinationId}"></c:param>
                            </c:url>
                            <c:if test="${not empty login_name}">
                                <h4> <span><a href="${detailUrl}">Làm bài thi</a></span></h4>
                            </c:if>
                            <c:if test="${empty login_name}">
                                <h4> <fmt:message key="label.examination.require"  bundle="${lang}"/></h4>
                            </c:if>
                        </div>
                    </div>
                </c:forEach>
                <ul id="pagination-demo" class="pagination-sm"></ul>
            </div>
        </div>
    </div>
    <input type="hidden" name="page" id="page"/>
</form>
<script type="text/javascript">
    $(document).ready(function () {

    });
    var totalPages=${items.totalPage};
    var startPage=${items.page};
    var visiblePages=${items.maxPageItems};
    $('#pagination-demo').twbsPagination({
        totalPages: totalPages,
        startPage:startPage,
        visiblePages: visiblePages,
        onPageClick: function (event, page) {
            if(page!=startPage) {
                $('#page').val(page);
                $('#formUrl').submit();
            }
        }
    });
</script>
</body>
</html>