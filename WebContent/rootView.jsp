<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Root page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
            background-color: #ffffff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h1 {
            color: #333333;
            text-align: center;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #dddddd;
            padding: 10px;
            text-align: center;
        }

        th {
            background-color: #f2f2f2;
        }

        a {
            text-decoration: none;
            display: inline-block;
            padding: 10px;
            margin: 5px;
            background-color: #3498db;
            color: #ffffff;
            border-radius: 5px;
        }

        a:hover {
            background-color: #2980b9;
        }
    </style>
</head>
<body>

<div class="container">

    <form action="initialize">
        <input type="submit" value="Initialize the Database"/>
    </form>

    <a href="login.jsp" target="_self">Logout</a><br><br>

    <h1>List all users</h1>
    <table>
        <caption><h2>List of Users</h2></caption>
        <tr>
            <th>Email</th>
            <th>Password</th>
            <th>Role</th>
        </tr>
        <c:forEach var="users" items="${listUser}">
            <tr>
                <td><c:out value="${users.email}" /></td>
                <td><c:out value="${users.password}" /></td>
                <td><c:out value="${users.role}" /></td>
            </tr>
        </c:forEach>
    </table>

    <a href="bigClient" target="_self">Big Client</a><br><br>
    <a href="highestTreeCut" target="_self">Highest Tree Cut</a><br><br>
    <a href="oneTree" target="_self">One Tree Clients</a><br><br>
    <a href="badClients" target="_self">Bad Clients</a><br><br>
    <a href="goodClients" target="_self">Good Clients</a><br><br>
    <a href="stats" target="_self">Statistics</a><br><br>
    <a href="prosp" target="_self">Prospective Clients</a><br><br>
    <a href="easy" target="_self">Easy Clients</a><br><br>
    <a href="overdue" target="_self">Overdue Clients</a><br><br>

</div>

</body>
</html>
