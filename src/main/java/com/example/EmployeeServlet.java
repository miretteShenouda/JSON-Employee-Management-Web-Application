package com.example;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;

@WebServlet(name = "EmployeeServlet", urlPatterns = { "/employeesInfo" })
public class EmployeeServlet extends HttpServlet {
    static String path = "C:\\FCAI Year 4 2023-2024\\subjects\\versions_assignment_3\\done_alll\\mywebapp\\employee.json";
    private EmployeeReader empReader = new EmployeeReader();
    private EmployeeManager empManager = new EmployeeManager(path);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            String employeeId = request.getParameter("employeeId");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String designation = request.getParameter("designation");
            String[] languageNames = request.getParameterValues("languageName[]");
            String[] scoreOutof100ValuesString = request.getParameterValues("scoreOutof100[]");
            int[] scoreOutof100Values = Arrays.stream(scoreOutof100ValuesString)
                                   .mapToInt(Integer::parseInt)
                                   .toArray();
        // Process each language and score
       
        if (languageNames != null && scoreOutof100Values != null) {
            for (int i = 0; i < languageNames.length; i++) {
                String languageName = languageNames[i];
                int scoreOutof100 = scoreOutof100Values[i];
                // Process language and score (you might want to store or print them)
              //  out.println("Language Name: " + languageName + ", Score: " + scoreOutof100);
            }
        }
            if (empManager.isIdAlreadyExists(Integer.parseInt(employeeId))) {
                out.println("Invalid ID. This ID already exists in the JSON file.");
            } else {
             //   out.println("hello there");
                updateJSONFile(employeeId, firstName, lastName, designation, languageNames, scoreOutof100Values);
                out.println("Data added to employees.json");
           }

        } catch (IOException ex) {
            Logger.getLogger(EmployeeServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        // EmployeeManager empManager = new EmployeeManager(path);
        try (PrintWriter out = response.getWriter()) {
            String searchType = request.getParameter("searchType");
            String sortOrder = request.getParameter("sortOrder");
            
            String sortLang = request.getParameter("sortAttributeLang");
            String sortScore = request.getParameter("sortAttributeScore");
             if (sortLang != null) {
                
                List<Employee> allemp = new ArrayList<>();
                
                allemp = empManager.searchEmployeeByLangScore(sortLang, Integer.parseInt(sortScore));
                if(allemp.size()!=0)
                {
//                    out.println("no employees found");
                    Employee.sortEmployeesByLanguageScore(allemp, sortLang);
                     for (int i = 0; i < allemp.size(); i++) {
                    out.println(allemp.get(i).toString());
                }
                }
                else
                {
                    out.println("employee not found.");
                }
                 return;
            }

            else if ("searchbyDesignation".equals(searchType)) { // Search for a student based on first name
                String employeeDesignation = request.getParameter("searchTerm");
                List<Employee> foundemployeesList = empManager.searchEmployeeByDesignation(employeeDesignation);

                if (foundemployeesList != null && !foundemployeesList.isEmpty()) {
                    request.setAttribute("searchResults", foundemployeesList);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/searchResults.jsp");
                    dispatcher.forward(request, response);
                } else {
                    out.println("employee not found.");
                }
                return;
            } else if ("searchbyId".equals(searchType)) { // Search for a student based on first name
                int empId = Integer.parseInt(request.getParameter("searchTerm"));
                List<Employee> foundemployeesList = empManager.searchEmployeeById(empId);

                if (foundemployeesList != null && !foundemployeesList.isEmpty()) {
                    request.setAttribute("searchResults", foundemployeesList);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/searchResults.jsp");
                    dispatcher.forward(request, response);
                } else {
                    out.println("employee not found.");
                }
                return;
            }else if ("deletebyid".equals(searchType)) {
                int id = Integer.parseInt(request.getParameter("deletebyid"));

                Boolean value = empManager.deleteEmployee(id);

                if (value == true) {
                    out.println("deleted successfully");
                } else {
                    out.println("employee not found to be deleted");
                }
                return;
            }
            String empId = request.getParameter("employeeIdupdate");
            String newfirstName = request.getParameter("updatedFirstName");
            String newlastName = request.getParameter("updatedLastName");
            String newDesignation = request.getParameter("updatedDesignation");
            String[] languageNames = request.getParameterValues("languageName[]");
            String[] scoreOutof100ValuesString = request.getParameterValues("scoreOutof100[]");
            int[] scoreOutof100 = Arrays.stream(scoreOutof100ValuesString)
                    .mapToInt(Integer::parseInt)
                    .toArray();
           
            // Implement a method to update the first name in your data source
            boolean success = empManager.updateEmployee(Integer.parseInt(empId), newfirstName, newlastName,
                    newDesignation, languageNames, scoreOutof100);
            if (success == true) {
                out.println("updated successfully.");
            } else {
                out.println("Failed to update.");
            }
        } catch (IOException ex) {
            Logger.getLogger(EmployeeServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        doGet(request, response);
    }

    private static void updateJSONFile(String employeeId, String firstName, String lastName, String designation,
                                    String[] languageNames, int[] scoreOutof100) throws IOException {
    try {
        System.out.println("Updating JSON file...");
        ObjectMapper objectMapper = new ObjectMapper();
        List<Employee> employeeList;

        File file = new File(path);  // Assuming 'path' is the correct file path

        // Read existing JSON file
        try (FileReader reader = new FileReader(file)) {
            CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Employee.class);
            if (reader.ready()) {
                // If the reader is ready, there is content in the file
                // Parse the existing JSON content using Jackson
                System.out.println("Parsing existing JSON content...");
                employeeList = objectMapper.readValue(reader, listType);
            } else {
                // If the reader is not ready, the file is empty
                System.out.println("JSON file is empty. Initializing with an empty list.");
                employeeList = new ArrayList<>();
            }
        }

        // Create a new Employee object
        Employee newEmployee = new Employee();
        newEmployee.setFirstName(firstName);
        newEmployee.setLastName(lastName);
        newEmployee.setEmployeeID(Integer.parseInt(employeeId));
        newEmployee.setDesignation(designation);

        // Create a list to store ProgrammingLanguages for the employee
        List<ProgrammingLanguage> knownLanguages = new ArrayList<>();

        // Add each language and score to the knownLanguages list
        for (int i = 0; i < languageNames.length; i++) {
            ProgrammingLanguage language = new ProgrammingLanguage();
            language.setLanguageName(languageNames[i]);
            language.setScoreOutof100(scoreOutof100[i]);
            knownLanguages.add(language);
        }

        // Set the knownLanguages list for the employee
        newEmployee.setKnownLanguages(knownLanguages);

        // Add the new Employee to the existing list
        employeeList.add(newEmployee);
//         for (int i = 0; i < knownLanguages.size(); i++) {
//            System.out.println(knownLanguages.get(i));
//         }
   

        // Write the updated list back to the JSON file
        try (FileWriter writer = new FileWriter(file)) {
            System.out.println("Writing updated list back to the JSON file...");
            objectMapper.writeValue(writer, employeeList);
        }

        System.out.println("Update completed successfully.");
    } catch (IOException e) {
        e.printStackTrace();
    }
}


    private boolean isIdAlreadyExists(String employeeId) throws IOException {
        List<Employee> employeeList = empReader.GetAll();
        return employeeList.stream().anyMatch(e -> String.valueOf(e.getEmployeeID()).equals(employeeId));
    }

}
