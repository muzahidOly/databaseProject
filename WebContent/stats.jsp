<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Stats Page</title>
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
        color: white; /* Change the color to white */
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
        <h1>Statistics Page</h1>
    </header>

    <nav>
        <a href="login.jsp" target="_self">Logout</a>
         <a href="root" target="_self">Root View</a>
    </nav>

   <!-- Existing code above this line remains unchanged -->

<!-- Existing code above this line remains unchanged -->

<section>
    <h2>Stats Page</h2>
    <p>This page holds a bunch of useful statistics.</p>
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
                
                <!-- New row for totals -->
                <tr>
                    <td><b>Totals:</b></td>
                    <td>${total[0]}</td>
                    <td>${total[1]}</td>
                    <td>${total[2]}</td>
                    <td></td>
                    <td></td>
                </tr>
                
                <!-- New row for averages -->
                <tr>
                    <td><b>Averages:</b></td>
                    <td>${average[0]}</td>
                    <td>${average[1]}</td>
                    <td>${average[2]}</td>
                    <td></td>
                    <td></td>
                </tr>
            </table>
        </c:when>
        <c:otherwise>
            <p>No Statistics found.</p>
        </c:otherwise>
    </c:choose>
</section>

<!-- Existing code below this line remains unchanged -->

</body>
</html>
