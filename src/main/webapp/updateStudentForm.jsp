<%@page import="com.example.Students"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
    
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Update Student Form</title>
    </head>
     <h2>Update Student Form</h2>
     <body>
         
   
   <script>
    // Function to get URL parameter by name
    function getParameterByName(name, url) {
        if (!url) url = window.location.href;
        name = name.replace(/[\[\]]/g, "\\$&");
        var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(url);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, " "));
    }

    // Get the student ID from the URL
    var studentId = getParameterByName('studentId');
    //document.getElementById('updateStudentId').value = studentId; // Set the ID from the URL
    '<% Students studentData = (Students) request.getAttribute("studentData"); %>'
    '<%-- Check if studentData is not null before trying to set values --%>'
   // <% if (studentData != null) { %>
                
        document.getElementById('updateStudentId').value = '<%= studentData.getStudentId() %>';
        document.getElementById('updateFirstName').value = '<%= studentData.getFirstName() %>';
        document.getElementById('updateLastName').value = '<%= studentData.getLastName() %>';
        document.getElementById('updateGender').value = '<%= studentData.getGender() %>';
        document.getElementById('updateGpa').value = '<%= studentData.getGPA() %>';
        document.getElementById('updateLevel').value = '<%= studentData.getLevel() %>';
        document.getElementById('updateAddress').value = '<%= studentData.getAddress() %>';
        
         console.log("JavaScript code is running");
//   <% } %>

       

</script>
         
    <form name="UpdateForm" action="studentsInfo" onsubmit="return validateUpdateForm()" method="post">
    <label for="updateStudentId">Student ID to Update:</label>
    <input type="text" id="updateStudentId" name="updateStudentId" required>
    <br>

    <label for="updateFirstName">New First Name:</label>
    <input type="text" id="updateFirstName" name="updateFirstName">
    <br>

    <label for="updateLastName">New Last Name:</label>
    <input type="text" id="updateLastName" name="updateLastName">
    <br>

    <label for="updateGender">New Gender:</label>
    <select id="updateGender" name="updateGender">
        <option value="male">Male</option>
        <option value="female">Female</option>
        <option value="other">Other</option>
    </select>
    <br>

    <label for="updateGpa">New GPA:</label>
    <input type="number" step="0.01" id="updateGpa" name="updateGpa">
    <br>

    <label for="updateLevel">New Student Level:</label>
    <input type="text" id="updateLevel" name="updateLevel">
    <br>

    <label for="updateAddress">New Address:</label>
    <input type="text" id="updateAddress" name="updateAddress">
    <br>
    
    <input type="hidden" name="update" value="true">
    <button type="submit">Update Student</button>
    
</form>

     </body><!-- comment -->
         
         
</html>

