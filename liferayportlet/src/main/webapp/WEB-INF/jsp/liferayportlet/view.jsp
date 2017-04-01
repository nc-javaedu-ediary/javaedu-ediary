<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

This is the <b>liferayportlet</b> portlet.<br />

<c:out escapeXml="true" value="${releaseInfo}" />.
</br>USERS </br>
<table>
        <tr>
            <th>ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>University</th>
            <th>E-mail</th>
        </tr>
    <c:forEach items="${users}" var="user">
        <tr>
            <td>${user.id}</td>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.university}</td>
            <td>${user.email}</td>
        </tr>
    </c:forEach>
</table>
LECTURES </br>
<table>
    <tr>
        <th>Id</th>
        <th>Title</th>
        <th>Classroom</th>
        <th>Description</th>
        <th>Homework</th>
    </tr>
    <c:forEach items="${lectures}" var="lecture">
        <tr>
            <td>${lecture.id}</td>
            <td>${lecture.title}</td>
            <td>${lecture.classroom}</td>
            <td>${lecture.description}</td>
            <td>${lecture.homework}</td>
        </tr>
    </c:forEach>
</table>
COURSES </br>
<table>
    <tr>
        <th>Id</th>
        <th>Title</th>
    </tr>
    <c:forEach items="${courses}" var="course">
        <tr>
            <td>${course.id}</td>
            <td>${course.title}</td>
        </tr>
    </c:forEach>
</table>