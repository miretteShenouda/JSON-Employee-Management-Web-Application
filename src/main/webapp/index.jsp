<%@page contentType="text/html" pageEncoding="UTF-8" %>

    <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Employees</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                margin: 0;
                padding: 0;
            }

            form {
                max-width: 600px;
                margin: 20px auto;
                padding: 20px;
                background-color: #fff;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            label {
                display: block;
                margin-bottom: 8px;
                color: #333;
            }

            input,
            select,
            button {
                width: 100%;
                padding: 10px;
                margin-bottom: 15px;
                box-sizing: border-box;
                border: 1px solid #ccc;
                border-radius: 4px;
                font-size: 16px;
            }

            button,
            [name|="Submit"] {
                background-color: #4cafff;
                color: #fff;
                cursor: pointer;
            }

            button:hover,
            [name|="Submit"]:hover {
                background-color: #45a049;
            }

            .error-message {
                color: red;
            }
        </style>
    </head>

    <body>

        <form name="RegForm" action="employeesInfo" onsubmit="return validateForm()" method="get">
            <p>
                <label for="employeeId">Employee ID:</label>
                <input type="text" id="employeeId" name="employeeId" required>
                <br>


                <label for="firstName">First Name:</label>
                <input type="text" id="firstName" name="firstName" required>
                <br>

                <label for="lastName">Last Name:</label>
                <input type="text" id="lastName" name="lastName" required>
                <br>

                <label for="designation">Designation:</label>
                <input type="text" id="designation" name="designation" required>
                <br>
                
            <div id="languageContainer">
            Language Name:
            <input type="text" name="languageName[]" required>
            Score out of 100:
            <input type="text" name="scoreOutof100[]" required>
            </div>
            <p>
             <button type="button" onclick="addLanguageField()">Add Language</button>
            </p>
            <input type="submit" value="submit" name="Submit" />
        </form>
        <br />
        <form action="employeesInfo" method="post">
            <label for="deletebyid">Delete student by ID:</label>
            <input type="text" id="deletebyid" name="deletebyid" required>
            <input type="hidden" name="delete" value="true">
            <input type="hidden" name="searchType" value="deletebyid">
            <button type="submit">Delete</button>
        </form>
        <br>
        <form action="employeesInfo" method="post">
            <label for="searchType">Search Students by :</label>
            <select id="searchType" name="searchType" required>
                <option value="searchbyId"> ID</option>
                <option value="searchbyDesignation">Designation</option>
             
            </select>
            <br>
            <label for="searchTerm">Search Term:</label>
            <input type="text" id="searchTerm" name="searchTerm" required>
            <br>
            <button type="submit">Search</button>
        </form>

        <br>
        <form action="employeesInfo" method="post">
            <label for="sortAttribute">Sort employees :</label>
            <label for="sortAttribute">enter language:</label>
            <input type="text" id="sortAttributeLang" name="sortAttributeLang">
            <label for="sortAttribute">score:</label>
            <input type="text" id="sortAttributeScore" name="sortAttributeScore">
            <label for="sortOrder">Sort Order:</label>
            <select id="sortOrder" name="sortOrder" required>
                <option value="asc">Ascending</option>
                <option value="desc">Descending</option>
            </select>
            <br>
            <button type="submit">Sort</button>
        </form>
        <script>
        function addLanguageField() {
            var container = document.getElementById("languageContainer");
            
            var languageDiv = document.createElement("div");
            languageDiv.innerHTML = `
                Language Name:
                <input type="text" name="languageName[]" required>
                Score out of 100:
                <input type="text" name="scoreOutof100[]" required>
                <br>
            `;
            container.appendChild(languageDiv);
        }
        </script>
    </body>

    </html>
