<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Client page</title>
</head>

<center><h1>Welcome ${clientName} You have been successfully logged in</h1> </center>

 
	<body>
	 <center>
		 <a href="login.jsp"target ="_self" > logout</a><br><br> 
		 <p> We will impliment other client activities here later</p>

   
</body>
</html>
	 <title>Add New Quote</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        
            margin: 0;
            padding: 0;
            text-align: center;
        }

        .container {
            max-width: 400px;
            margin: 0 auto;
            padding: 20px;
            background-color: #fff;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h1 {
            margin-top: 0;
        }

        label {
            display: block;
            margin-top: 10px;
        }

        input[type="text"],
        textarea {
            width: 100%;
            padding: 10px;
            margin: 5px 0;
            border: 1px solid #ccc;
            border-radius: 3px;
        }

        input[type="submit"] {
            background-color: #007BFF;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
      <h1>Quote Information</h1>
    
    <c:choose>
        <c:when test="${quoteInfo ne null}">
            <table>
                <tr>
                    <th>Email</th>
                    <th>Tree Price</th>
                    <th>Tree Size</th>
                    <th>Tree Number</th>
                    <th>Quote Date</th>
                    <th>Quote Response</th>
                </tr>
                <tr>
                    <td>${quoteInfo.email}</td>
                    <td>${quoteInfo.treePrice}</td>
                    <td>${quoteInfo.treeSize}</td>
                    <td>${quoteInfo.treeHeight}</td>
                    <td>${quoteInfo.quoteDate}</td>
                    <td>${quoteInfo.quoteResponse}</td>
                </tr>
            </table>
        </c:when>
        <c:otherwise>
            <p>No quote information found for the user.</p>
        </c:otherwise>
    </c:choose>
        <h1>Add/Update Quote</h1>
        <form action="newQuote" method="post">
            <input type="hidden" name="clientName" value="${clientName}">
    
            <label for="treePrice">Tree Price:</label>
            <input type="text" name="treePrice" id="treePrice" required>
            
            <label for="treeSize">Tree Size:</label>
            <input type="text" name="treeSize" id="treeSize" required>
            
            <label for="treeHeight">Tree Number:</label>
            <input type="text" name="treeHeight" id="treeHeight" required>
            
            <label for="quoteDate">Quote Date:</label>
            <input type="date" name="quoteDate" id="quoteDate" required>
            
            <label for="quoteResponse">Quote Response:</label>
            <textarea name="quoteResponse" id="quoteResponse" rows="4" required></textarea>

            <input type="submit" value="Add Quote" >
        </form>
    </div>
</body>
		 
		 
		 
		 
		 
		 
		 
		 </center>
	</body>
</html>