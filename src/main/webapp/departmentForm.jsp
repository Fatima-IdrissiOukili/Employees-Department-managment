<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
 	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
 	<title>Department Management </title>
 	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

 
 <script>
    function validateForm() {
        var name = document.forms["departmentForm"]["name"].value;
        var location = document.forms["departmentForm"]["location"].value;
        if (name == "" || location == "") {
            alert("Please fill in all the fields Department");
            return false;
        }
    }
 </script>
 
</head>
<body>
    <div class="container">
        <div class="text-center">
            <h1>Department Management</h1>
            <h2>
                <a href="newDepartementForm" class="btn text-info btn-outline-warning">Add New Department</a>
                <a href="listDepartement" class="btn text-info btn-outline-warning">List All Departments</a>
            </h2>
        </div>
        <div class="row justify-content-center">
            <form name="departmentForm" action="<c:if test='${departement != null}'>updateDepartement</c:if><c:if test='${departement == null}'>insertDepartement</c:if>" method="post" onsubmit="return validateForm()">
                <table class="table table-bordered">
                    <caption>
                        <c:if test="${departement != null}">Edit Department</c:if><c:if test="${departement == null}"></c:if>
                    </caption>
                    <c:if test="${departement != null}">
                        <input type="hidden" name="id" value="<c:out value='${departement.id}' />" />
                    </c:if>
                    <tr>
                        <th>Department Name :</th>
                        <td>
                            <input type="text" class="form-control" name="name" size="45" value="<c:out value='${departement.name}' />" />
                        </td>
                    </tr>
                    <tr>
                        <th>Department Location :</th>
                        <td>
                            <input type="text" class="form-control" name="location" size="45" value="<c:out value='${departement.location}' />" />
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <input type="submit" class="btn btn-info" value="Save" />
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <!-- Bootstrap Bundle with Popper -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js" integrity="sha384-W2m7Xr2/x1fJnDKLLdC3FwO1Tx94yzD+XXKawGYMI7D8KySDavkp+s3DEexPfsyo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/js/bootstrap.min.js" integrity="sha384-u4vS7kqFto/hjqBKK6eWvJ5MOroK+6CCZPi5X8ez3XHcWipb/tz2z+Q+8gU5B2Ut" crossorigin="anonymous"></script>
</body>
</html>