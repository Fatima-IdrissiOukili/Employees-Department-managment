<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
 	<title>Management Application</title>
 	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
 
</head>
<body>
    <div class="container mt-3">
        <div class="text-center">
            <div class="btn-group mt-3" role="group">
                <a href="/employees" class="btn btn-outline-warning">Home</a>
                <a href="newEmployeeForm" class="btn text-info btn-outline-warning">Add New Employee</a>
                <a href="listEmployee" class="btn btn-outline-warning">List All Employees</a>
            </div>
        </div>
        
        <div class="row justify-content-center mt-3" align="center">
		  	<form action="searchEmployee" method="GET" onsubmit="return validateSearch()">
		        <div class="input-group mb-3">
		            <input type="text" id="searchQuery" name="searchQuery" class="form-control" placeholder="Enter department name">
		            <div class="input-group-append">
		                <button class="btn btn-outline-primary" type="submit">Search Employee</button>
		            </div>
		        </div>
			</form>
		</div>
        
        <script>
            function validateSearch() {
                var searchQuery = document.getElementById("searchQuery").value.trim();
                if (searchQuery === "") {
                    alert("Please enter a search.");
                    return false;
                }
                return true;
            }
        </script>
        <div class="row justify-content-center mt-3">
            <c:if test="${not empty employees}">
                <table class="table table-striped">
                    <thead>
                      <tr> <h2>List of Employees</h2></tr>
                        <tr>
                            <th>ID</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Email</th>
                            <th>Department</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="employee" items="${employees}">
                            <tr>
                                <td><c:out value="${employee.id}" /></td>
                                <td><c:out value="${employee.firstName}" /></td>
                                <td><c:out value="${employee.lastName}" /></td>
                                <td><c:out value="${employee.email}" /></td>
                                <td><c:out value="${employee.department.name}" /></td>
                                <td>
                                    <a href="editEmployeeForm?id=<c:out value='${employee.id}' />" class="btn btn-warning">Edit</a>
                                    <a href="deleteEmployee?id=<c:out value='${employee.id}' />" class="btn btn-danger">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
        <div class="container text-center">
            <c:if test="${currentPage > 1}">
                <c:url var="prevUrl" value="/listEmployee">
                    <c:param name="page" value="${currentPage - 1}" />
                </c:url>
                <a href="<c:out value="${prevUrl}" />" class="btn btn-primary">Previous</a>
            </c:if>
            <c:if test="${currentPage < totalPages}">
                <c:url var="nextUrl" value="/listEmployee">
                    <c:param name="page" value="${currentPage + 1}" />
                </c:url>
                <a href="<c:out value="${nextUrl}" />" class="btn btn-primary">Next</a>
            </c:if>
        </div>
    </div>

    <!-- Bootstrap Bundle with Popper -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js" integrity="sha384-W2m7Xr2/x1fJnDKLLdC3FwO1Tx94yzD+XXKawGYMI7D8KySDavkp+s3DEexPfsyo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/js/bootstrap.min.js" integrity="sha384-u4vS7kqFto/hjqBKK6eWvJ5MOroK+6CCZPi5X8ez3XHcWipb/tz2z+Q+8gU5B2Ut" crossorigin="anonymous"></script>
</body>
</html>