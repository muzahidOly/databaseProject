<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Client page</title>
</head>

<center><h1>Welcome ${accountName} You have been successfully logged in</h1> </center>


 
	<body>
	 <center>
	 
		 <a href="login.jsp"target ="_self" > logout</a><br><br> 
		 <p> We will impliment other admin activities here later</p>
		 </center>

    <div align="center">
  <table class="quote-table">
    <tr>
        <th>User</th>
        <th>Tree Price</th>
        <th>Tree Size</th>
        <th>Tree Height</th>
        <th>Quote Date</th>
        <th>Quote Response</th>
    </tr>

   <!-- 
   <c:forEach items="${quoteArray}" var="row">
        <tr>
            <c:forEach items="${row}" var="cell">
                <td>${cell}</td>
            </c:forEach>
        </tr>
    </c:forEach>
    -->
      <c:forEach items="${quoteArray}" var="row" varStatus="rowStatus">
            <tr>
                <c:forEach items="${row}" var="cell" varStatus="cellStatus">
                    <td>
                       <button onclick="makeEditable(this.parentElement, '${row[0]}' , ${cellStatus.index})">
    Edit
</button>
                        <span onclick="saveChanges(this.parentElement, ${rowStatus.index}, ${cellStatus.index})">
                            ${cell}
                        </span>
                    </td>
                </c:forEach>
            </tr>
        </c:forEach>
    
</table>
<script>
function makeEditable(cell,rowStatus,cellStatus) {
    const span = cell.querySelector('span');
    const input = document.createElement('input');
    input.setAttribute('type', 'text');
    input.value = span.textContent;
    input.id = 'editableInput'; // Add an id to the input element
    cell.replaceChild(input, span);
   
    // Add an event listener to the input element to listen for the Enter key press
    input.addEventListener('keydown', function (event) {
        if (event.key === 'Enter') {
            
            console.log(rowStatus);

            console.log(input.value);

            console.log(cellStatus);
            // Now you can access quoteArray in your JavaScript function
            const data = {
            	    user: rowStatus, // Replace with the actual user value
            	    newInfo: input.value, // Pass the new text
            	    position: cellStatus, // Replace with the actual position value
            	};

            	// Convert the data to a JSON string
            	const jsonData = JSON.stringify(data);

            	const xhr = new XMLHttpRequest();
            	xhr.open('POST', 'updateQuote', true);
            	xhr.setRequestHeader('Content-Type', 'application/json');
            	xhr.onreadystatechange = function () {
            	    if (xhr.readyState === 4 && xhr.status === 200) {
            	        // Handle the response from the backend here if needed
            	        console.log(xhr.responseText);
            	    }
            	};
            	xhr.send(jsonData);
        }
    });
}


</script>

<style>
    .quote-table {
        border-collapse: collapse;
        width: 80%;
    }

    .quote-table th, .quote-table td {
        border: 1px solid #000;
        padding: 8px;
        text-align: left;
    }
</style>
	</div>
	</body>
</html>