<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Employee Management Application</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script>
        function validateForm() {
            var firstName = document.forms["employeeForm"]["firstName"].value;
            var lastName = document.forms["employeeForm"]["lastName"].value;
            var email = document.forms["employeeForm"]["email"].value;
            var departmentId = document.forms["employeeForm"]["department_id"].value;
            if (firstName == "" || lastName == "" || email == "" || departmentId == "") {
                alert("Please fill in all fields for the employee.");
                return false;
            }
        }
    </script>
    <style>
        /* Style personnalisé pour les bordures orange */
        .border-orange {
            border-color: #FFA500 !important; /* Couleur orange */
        }
    </style>
</head>
<body>
    <div class="container">
        <h1 class="text-center">Employee Management</h1>
        <div class="text-center">
            <h2>
                <a href="newEmployeeForm" class="btn btn-info">Add New Employee</a>
                <a href="listEmployee" class="btn btn-warning">List All Employees</a>
            </h2>
        </div>
        <div class="row justify-content-center">
            <div class="col-md-6">
                <form name="employeeForm" action="<c:if test='${employee != null}'>updateEmployee</c:if><c:if test='${employee == null}'>insertEmployee</c:if>" method="post" onsubmit="return validateForm()">
                    <div class="mb-3">
                        <h2><c:if test="${employee != null}">Edit Employee</c:if><c:if test="${employee == null}">Add New Employee</c:if></h2>
                    </div>
                    <c:if test="${employee != null}">
                        <input type="hidden" name="id" value="<c:out value='${employee.id}' />"/>
                    </c:if>
                    <div class="mb-3">
                        <label for="firstName" class="form-label">Employee First Name</label>
                        <input type="text" class="form-control border border-orange" id="firstName" name="firstName"
                               value="<c:out value='${employee.firstName}' />"/>
                    </div>
                    <div class="mb-3">
                        <label for="lastName" class="form-label">Employee Last Name</label>
                        <input type="text" class="form-control border border-orange" id="lastName" name="lastName"
                               value="<c:out value='${employee.lastName}' />"/>
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label">Employee Email</label>
                        <input type="email" class="form-control border border-orange" id="email" name="email"
                               value="<c:out value='${employee.email}' />"/>
                    </div>
                    <div class="mb-3">
                        <label for="department" class="form-label">Department</label>
                        <select class="form-select border border-orange" id="department" name="department_id">
                            <c:forEach var="department" items="${departments}">
                                <option value="<c:out value='${department.id}' />"
                                        <c:if test="${employee != null && department.id == employee.department.id}">selected</c:if>>
                                    <c:out value="${department.name}" />
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-3 text-center">
                        <button type="submit" class="btn btn-primary">Save</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
