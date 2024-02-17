package com.example;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Employee implements Comparable<Employee> {
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("FirstName")
    private String firstName;
    @JsonProperty("LastName")
    private String lastName;
    @JsonProperty("EmployeeID")
    private int employeeID;
    @JsonProperty("Designation")
    private String designation;

    // @OneToMany(cascade = CascadeType.ALL)
    @JsonProperty("KnownLanguages")
    private List<ProgrammingLanguage> knownLanguages;

    // Constructors

    public Employee() {
        // Default constructor
    }

    public Employee(String firstName, String lastName, int employeeID, String designation,
            List<ProgrammingLanguage> knownLanguages) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeID = employeeID;
        this.designation = designation;
        this.knownLanguages = knownLanguages;
    }

    public enum SortingAttribute {
        Employee_ID,
        FirstName,
        LastName,
        Designation

        // Add more attributes as needed
    }

    // Getters and Setters

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public List<ProgrammingLanguage> getKnownLanguages() {
        return knownLanguages;
    }

    public void setKnownLanguages(List<ProgrammingLanguage> knownLanguages) {
        this.knownLanguages = knownLanguages;
    }

    @Override
    public String toString() {
        String emp = "<" + employeeID + ", " + firstName + ", " + lastName + ", " + designation;
        for (int i = 0; i < this.knownLanguages.size(); i++) {
            emp = emp + ", " + knownLanguages.get(i).getLanguageName() + ":" + knownLanguages.get(i).getScoreOutof100();

        }
        return emp + "><br>";
    }

    private static SortingAttribute currentSortingAttribute = SortingAttribute.Employee_ID;

    private static int currentSortingScore;

    public static void setSortingAttribute(SortingAttribute attribute) {
        currentSortingAttribute = attribute;
    }

    public static void setSortingScore(int score) {
        currentSortingScore = score;
    }

    // Method to sort employees based on the score of a specific language
    public static void sortEmployeesByLanguageScore(List<Employee> employees, String languageName) {
        Collections.sort(employees, new Comparator<Employee>() {
            @Override
            public int compare(Employee emp1, Employee emp2) {
                // Find the scores for the specified language
                int score1 = getScoreForLanguage(emp1, languageName);
                int score2 = getScoreForLanguage(emp2, languageName);

                // Compare scores in descending order
                return Integer.compare(score1, score2);
            }
        });
    }

    // Helper method to get the score for a specific language from an employee's
    // KnownLanguages list
    private static int getScoreForLanguage(Employee employee, String languageName) {
        for (ProgrammingLanguage knownLanguage : employee.getKnownLanguages()) {
            if (knownLanguage.getLanguageName().equalsIgnoreCase(languageName)) {
                return knownLanguage.getScoreOutof100();
            }
        }
        // Return a default score if the language is not found
        return 0;
    }

    @Override
    public int compareTo(Employee other) {
        switch (currentSortingAttribute) {

            case Employee_ID:
                return Integer.compare(this.getEmployeeID(), other.getEmployeeID());
            case FirstName:
                return this.getFirstName().compareTo(other.getFirstName());
            case LastName:
                return this.getLastName().compareTo(other.getLastName());
            case Designation:
                return this.getDesignation().compareTo(other.getDesignation());
            default:
                return Integer.compare(this.getEmployeeID(), other.getEmployeeID());
        }
    }
}
