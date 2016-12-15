<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page isELIgnored="false"%>

<my:pagetemplate title="${album.title}">
    <jsp:attribute name="body">

    <form method="POST" action="<c:url value='/login' />" class="form-signin">
        <h2 class="form-heading"><fmt:message key="content.login"/></h2>

        <div class="form-group ${error != null ? 'has-error' : ''}">
            <span>${message}</span>
            <input name="username" type="text" class="form-control" placeholder=<fmt:message key="content.username"/>
                   autofocus="true"/>
            <input name="password" type="password" class="form-control" placeholder=<fmt:message key="content.password"/>/>
            <span>${error}</span>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.log_in"/></button>
            <h4 class="text-center"><a href="${contextPath}/registration"><fmt:message key="content.create_account"/></a></h4>
        </div>

    </form>

    </jsp:attribute>
</my:pagetemplate>
