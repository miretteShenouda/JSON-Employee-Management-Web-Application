<%@page import="java.util.Arrays"%>
<%@page import="com.example.ProgrammingLanguage"%>
<%@page import="java.util.List"%>
<%@page import="com.example.Employee"%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Search Results</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4ff;
            margin: 0;
            padding: 0;
        }

        h2 {
            color: #333;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #4cafff;
            color: #fff;
        }

        td input[type="text"] {
            padding: 8px;
            box-sizing: border-box;
        }

        button {
            background-color: #4cafff;
            color: #fff;
            padding: 8px;
            cursor: pointer;
        }

        button:hover {
            background-color: #45a049;
        }

        .error-message {
            color: red;
        }

        .language-score-input {
            display: flex;
            align-items: center;
        }
    </style>
</head>
<body>
    <h2>Search Results</h2>

    <div id="searchResults"></div>
    <% List<Employee> searchResults = (List<Employee>) request.getAttribute("searchResults"); %>
    <p>Number of students found: <%= (searchResults != null) ? searchResults.size() : 0 %></p>

    <form name="updatedData" action="employeesInfo" method="post">  
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Designation</th>
                    <th>Known Languages</th>
                    <th>Update</th>
                </tr>
            </thead>
            <tbody>
                <% if (searchResults != null && !searchResults.isEmpty()) { %>
                    <% for (Employee emp : searchResults) { %>
                        <tr>
                            <td><%= emp.getEmployeeID() %></td>
                            <td><input type="text" name="updatedFirstName" value="<%= emp.getFirstName() %>"></td>
                            <td><input type="text" name="updatedLastName" value="<%= emp.getLastName() %>"></td>
                            <td><input type="text" name="updatedDesignation" value="<%= emp.getDesignation() %>"></td>
                            <td>
                                <% if (emp.getKnownLanguages() != null && !emp.getKnownLanguages().isEmpty()) { %>
                                    <ul>
                                        <% for (ProgrammingLanguage lang : emp.getKnownLanguages()) { %>
                                            <li class="language-score-input">
                                                Language: <input type="text" name="languageName[]" value="<%= lang.getLanguageName() %>">
                                                Score: <input type="text" name="scoreOutof100[]" value="<%= lang.getScoreOutof100() %>">
                                            </li>
                                        <% } %>
                                    </ul>
                                <% } else { %>
                                    No known languages
                                <% } %>
                            </td>
                            <input type="hidden" name="employeeIdupdate" value="<%= emp.getEmployeeID() %>">
                            <td><button type="submit">Update Employee</button></td>
                        </tr>
                    <% } %>
                <% } else { %>
                    <tr>
                        <td colspan="7">No results found.</td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </form>

    <script>
        // Get the search results data from the servlet
        var searchResults = '${searchResults}';

        // Check if there are search results
        if (searchResults && searchResults.length > 0) {
            // Iterate through the search results and display them
            var resultList = document.getElementById("searchResults");
            var ul = document.createElement("ul");

            searchResults.forEach(function (employee) {
                var li = document.createElement("li");
                li.appendChild(document.createTextNode(employee.toString()));
                ul.appendChild(li);
            });

            resultList.appendChild(ul);
        } else {
            // No results found
            var noResults = document.createElement("p");
            noResults.appendChild(document.createTextNode("No results found."));
            document.getElementById("searchResults").appendChild(noResults);
        }
    </script>
</body>
</html>
