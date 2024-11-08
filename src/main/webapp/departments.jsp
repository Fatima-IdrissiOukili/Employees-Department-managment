<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Management Application</title>
    <!-- Bootstrap CSS -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
  <div class="container">
    <div class="text-center mt-3">
        <div class="btn-group mt-3" role="group">
            <a href="/employees" class="btn btn-outline-warning">Home</a>
            <a href="newDepartementForm" class="btn text-info btn-outline-warning">Add New Department</a>
            <a href="listDepartement" class="btn btn-outline-warning">List All Departments</a>
        </div>
    </div>
</div>
        <hr>
        
		<div class="row justify-content-center mt-3" align="center">
		  	<form action="searchDepartement" method="GET" onsubmit="return validateSearch()">
		        <div class="input-group mb-3">
		            <input type="text" id="searchQuery" name="searchQuery" class="form-control" placeholder="Enter department name">
		            <div class="input-group-append">
		                <button class="btn btn-outline-dark" type="submit">Search</button>
		            </div>
		        </div>
			</form>
		</div>


        <hr>
        <div class="text-center">
                <table class="table table-striped">
                    
                    <thead>
                        <tr>
                        <tr><h2>List of Departments</h2></tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Location</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="department" items="${departments}">
                            <tr>
                                <td><c:out value="${department.id}" /></td>
                                <td><c:out value="${department.name}" /></td>
                                <td><c:out value="${department.location}" /></td>
                                <td>
                                    <a href="editDepartementForm?id=<c:out value='${department.id}' />" class="btn btn-warning">Edit</a>
                                    <a href="deleteDepartement?id=<c:out value='${department.id}' />" class="btn btn-danger">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            <div class="container text-center">
                <c:if test="${currentPage > 1}">
                    <c:url var="prevUrl" value="/listDepartement">
                        <c:param name="page" value="${currentPage - 1}" />
                    </c:url>
                    <a href="<c:out value="${prevUrl}" />" class="btn btn-dark">Previous</a>
                </c:if>
                <c:if test="${currentPage < totalPages}">
                    <c:url var="nextUrl" value="/listDepartement">
                        <c:param name="page" value="${currentPage + 1}" />
                    </c:url>
                    <a href="<c:out value="${nextUrl}" />" class="btn btn-dark">Next</a>
                </c:if>
            </div>
        </div>
    </div>
    <script>
        function validateSearch() {
            var searchQuery = document.getElementById("searchQuery").value.trim();
            if (searchQuery === "") {
                alert("Please enter a search name.");
                return false;
            }
            return true;
        }
    </script>
</body>
</html>


