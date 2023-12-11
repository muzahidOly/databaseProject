<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Prospective Clients</title>
    <style>
        body {
            font-family: Arial, sans-serif;
           
            margin: 0;
            padding: 0;
        }

        header {
           
            color: white;
            padding: 1em;
            text-align: center;
        }

        nav {
            background-color: #eee;
            padding: 1em;
        }

        nav a {
            text-decoration: none;
            color: #333;
            margin-right: 1em;
        }

        h1 {
            color: #333;
        }

        table {
            width: 80%;
            margin: 20px auto;
            border-collapse: collapse;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
        }

        th {
            background-color: #333;
            color: white;
        }

        p {
            color: #333;
        }

    </style>
</head>

<body>
    <header>
        <h1>Prospective Clients Clients Page</h1>
    </header>

    <nav>
        <a href="login.jsp" target="_self">Logout</a>
         <a href="root" target="_self">Root View</a>
    </nav>

    <section>
        <h2>Prospective Clients Information</h2>
		<p>List all the clients that submitted some quotes but never produced any
orders of work.</p>
        <c:choose>
            <c:when test="${quoteInfoList ne null and not empty quoteInfoList}">
                <table>
                    <tr>
                        <th>Email</th>
                        <th>Tree Price</th>
                        <th>Tree Size</th>
                        <th>Tree Number</th>
                        <th>Quote Date</th>
                        <th>Quote Response</th>
                    </tr>
                    <c:forEach var="quoteInfo" items="${quoteInfoList}">
                        <tr>
                            <td>${quoteInfo.email}</td>
                            <td>${quoteInfo.treePrice}</td>
                            <td>${quoteInfo.treeSize}</td>
                            <td>${quoteInfo.treeHeight}</td>
                            <td>${quoteInfo.quoteDate}</td>
                            <td>${quoteInfo.quoteResponse}</td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <p>No Good Clients found.</p>
            </c:otherwise>
        </c:choose>
    </section>
</body>
</html>
