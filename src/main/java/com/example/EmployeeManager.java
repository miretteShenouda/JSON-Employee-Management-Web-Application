package com.example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class EmployeeManager {
    private String path; // Path to your JSON file
    private ObjectMapper objectMapper;
    private EmployeeReader empReader = new EmployeeReader();

    public EmployeeManager(String path) {
        this.path = path;
        this.objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    // private List<Employee> getAllEmployees() {
    // try {
    // File file = new File(path);
    // if (file.exists()) {
    // return objectMapper.readValue(file, new TypeReference<List<Employee>>() {
    // });
    // } else {
    // return new ArrayList<>();
    // }
    // } catch (IOException e) {
    // e.printStackTrace();
    // return new ArrayList<>();
    // }
    // }

    private void saveAllEmployees(List<Employee> employees) {
        try {
            File file = new File(path);
            objectMapper.writeValue(file, employees);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
public List<Employee> sorthEmployeeByLangScore(String lang, int score) {

        List<Employee> employeesList = empReader.GetAll();
        String languageToSort = lang;

        int scoreToCompare = score;

        Employee.sortEmployeesByLanguageScore(employeesList, languageToSort);
        return employeesList;
    }
public List<Employee> searchEmployeeByLangScore(String lang, int score) {
        List<Employee> foundEmployees = new ArrayList<>();
        List<Employee> employeesList = empReader.GetAll();

        for (Employee employee : employeesList) {
            for (int i = 0; i < employee.getKnownLanguages().size(); i++) {
                if (employee.getKnownLanguages().get(i).getLanguageName().equalsIgnoreCase(lang)
                        && employee.getKnownLanguages().get(i).getScoreOutof100() >= score) {
                    foundEmployees.add(employee);

                }
            }
        }
        return foundEmployees;
    }
    public List<Employee> sortEmployees(String sortAttribute, String sortOrder) {
        List<Employee> employeesList = empReader.GetAll();
        // Sort based on the chosen attribute
        switch (sortAttribute) {
            case "id":
                Employee.setSortingAttribute(Employee.SortingAttribute.Employee_ID);
                Collections.sort(employeesList);
                break;
            case "firstName":
                Employee.setSortingAttribute(Employee.SortingAttribute.FirstName);
                Collections.sort(employeesList);
                break;
            case "lastName":
                Employee.setSortingAttribute(Employee.SortingAttribute.LastName);
                Collections.sort(employeesList);
                break;
            case "designation":
                Employee.setSortingAttribute(Employee.SortingAttribute.Designation);
                Collections.sort(employeesList);
                break;
            // Add cases for other attributes if needed

            default:
                Collections.sort(employeesList);
                break;
        }
        // Reverse the list if descending order is selected
        if ("desc".equalsIgnoreCase(sortOrder)) {
            Collections.reverse(employeesList);
        }

        // Save sorted employees to the JSON file
        saveAllEmployees(employeesList);
        return employeesList;
    }

    public List<Employee> searchEmployeeByName(String firstName) {
        List<Employee> foundEmployees = new ArrayList<>();
        List<Employee> employeesList = empReader.GetAll();

        for (Employee employee : employeesList) {
            if (employee.getFirstName().equalsIgnoreCase(firstName)) {
                foundEmployees.add(employee);
            }
        }
        return foundEmployees;
    }

    public List<Employee> searchEmployeeByLastName(String lastName) {
        List<Employee> foundEmployees = new ArrayList<>();
        List<Employee> employeesList = empReader.GetAll();

        for (Employee employee : employeesList) {
            if (employee.getLastName().equalsIgnoreCase(lastName)) {
                foundEmployees.add(employee);
            }
        }
        return foundEmployees;
    }

    public List<Employee> searchEmployeeByDesignation(String designation) {
        List<Employee> foundEmployees = new ArrayList<>();
        List<Employee> employeesList = empReader.GetAll();

        for (Employee employee : employeesList) {
            if (employee.getDesignation().equalsIgnoreCase(designation)) {
                foundEmployees.add(employee);
            }
        }
        return foundEmployees;
    }

    public List<Employee> searchEmployeeById(int employeeId) {
        List<Employee> foundEmployees = new ArrayList<>();
        List<Employee> employeesList = empReader.GetAll();

        for (Employee employee : employeesList) {
            if (employee.getEmployeeID() == employeeId) {
                foundEmployees.add(employee);
            }
        }
        return foundEmployees;
    }

    public boolean deleteEmployee(int employeeId) {
        List<Employee> employeesList = empReader.GetAll();
        boolean removed = employeesList.removeIf(e -> e.getEmployeeID() == employeeId);
        if (removed) {
            // Save the updated list to the JSON file
            saveAllEmployees(employeesList);
        }
        return removed;
    }

    public boolean isIdAlreadyExists(int employeeId) {
        List<Employee> employeesList = empReader.GetAll();
        return employeesList.stream().anyMatch(e -> e.getEmployeeID() == employeeId);
    }

     public boolean updateEmployee(int employeeId, String newFirstName, String newLastName,
        String newDesignation, String[] languageNames, int[] scoreOutof100) throws IOException {
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

                // Find the employee with the specified ID
                Employee employeeToUpdate = findEmployeeById(employeeList, employeeId);

                if (employeeToUpdate != null) {
                    // Update the employee's information
                    employeeToUpdate.setFirstName(newFirstName);
                    employeeToUpdate.setLastName(newLastName);
                    employeeToUpdate.setDesignation(newDesignation);

                    // Update known languages
                    List<ProgrammingLanguage> knownLanguages = new ArrayList<>();
                    for (int i = 0; i < languageNames.length; i++) {
                        ProgrammingLanguage language = new ProgrammingLanguage();
                        language.setLanguageName(languageNames[i]);
                        language.setScoreOutof100(scoreOutof100[i]);
                        knownLanguages.add(language);
                    }
                    employeeToUpdate.setKnownLanguages(knownLanguages);

                    // Save the updated list to the JSON file
                    objectMapper.writeValue(file, employeeList);

                    return true;
                }
            }
        }

        return false;
    }
    private Employee findEmployeeById(List<Employee> employeeList, int employeeId) {
        for (Employee employee : employeeList) {
            if (employeeId == employee.getEmployeeID()) {
                return employee;
            }
        }
        return null;
    }
}
