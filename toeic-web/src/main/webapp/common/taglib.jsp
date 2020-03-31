<%@ page import="vn.myclass.core.web.common.WebConstant" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="dec" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<fmt:setBundle basename="ResourcesBundle" var="lang"/>
<c:set var="sessionUser" value="<%=WebConstant.LOGIN_NAME%>"></c:set>