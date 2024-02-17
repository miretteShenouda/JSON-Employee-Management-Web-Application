package com.example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EmployeeReader {

    public List<Employee> GetAll() {
        // Specify the path to your JSON file
        String filePath = "C:\\FCAI Year 4 2023-2024\\subjects\\versions_assignment_3\\done_alll\\mywebapp\\employee.json";

        List<Employee> allEmp = new ArrayList<>();
        try {
            // Create ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            // Read JSON from file into JsonNode
            JsonNode rootNode = objectMapper.readTree(new File(filePath));

            // Iterate over each employee
            Iterator<JsonNode> employeeIterator = rootNode.iterator();
            while (employeeIterator.hasNext()) {
                JsonNode employeeNode = employeeIterator.next();

                Employee emp = new Employee();
                // Extract employee details
                String firstName = employeeNode.get("FirstName").asText();
                String lastName = employeeNode.get("LastName").asText();
                int employeeID = employeeNode.get("EmployeeID").asInt();
                String designation = employeeNode.get("Designation").asText();

                emp.setFirstName(firstName);
                emp.setLastName(lastName);
                emp.setEmployeeID(employeeID);
                emp.setDesignation(designation);

                System.out.println("Employee Details:");
                System.out.println("FirstName: " + firstName);
                System.out.println("LastName: " + lastName);
                System.out.println("  EmployeeID: " + employeeID);
                System.out.println("  Designation: " + designation);

                // Iterate over known languages
                System.out.println("  Known Languages:");
                List<ProgrammingLanguage> knownLanguages = new ArrayList<>();

                Iterator<JsonNode> languageIterator = employeeNode.get("KnownLanguages").iterator();
                while (languageIterator.hasNext()) {
                    ProgrammingLanguage lang = new ProgrammingLanguage();
                    JsonNode languageNode = languageIterator.next();
                    String languageName = languageNode.get("LanguageName").asText();
                    int scoreOutof100 = languageNode.get("ScoreOutof100").asInt();

                    lang.setLanguageName(languageName);
                    lang.setScoreOutof100(scoreOutof100);
                    System.out.println("    LanguageName: " + languageName);
                    System.out.println("    ScoreOutof100: " + scoreOutof100);
                    knownLanguages.add(lang);
                }
                emp.setKnownLanguages(knownLanguages);
                allEmp.add(emp);
                System.out.println(); // Add a newline between employees
            }
            return allEmp;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return allEmp;
    }
}
