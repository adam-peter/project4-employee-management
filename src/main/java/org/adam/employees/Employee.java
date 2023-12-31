package org.adam.employees;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Employee implements IEmployee {
    //PROTECTED - private to the outside world, visible to subclasses
    protected String lastName;
    protected String firstName;
    protected LocalDate dob;

    protected final NumberFormat moneyFormatter = NumberFormat.getCurrencyInstance();
    protected final DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");

    private static final String PEOPLE_REGEX = "(?<lastName>\\w+),\\s*(?<firstName>\\w+),\\s*(?<dob>\\d{1,2}/\\d{1,2}/\\d{4}),\\s*(?<role>\\w+)(?:,\\s*\\{(?<details>.*)\\})?\\n";
    public static final Pattern PEOPLE_PAT = Pattern.compile(PEOPLE_REGEX);
    protected final Matcher peopleMat;

    protected Employee() {
        peopleMat = null;
        lastName = "N/A";
        firstName = "N/A";
        dob = null;
    }

    protected Employee(String personText) {
        peopleMat = Employee.PEOPLE_PAT.matcher(personText);
        if (peopleMat.find()) {
            this.lastName = peopleMat.group("lastName");
            this.firstName = peopleMat.group("firstName");
            this.dob = LocalDate.from(dtFormatter.parse(peopleMat.group("dob")));
        }
    }

    public abstract int getSalary();

    public double getBonus() {
        return getSalary() * 0.1;
    }

    //Flinstone5, Wilma, 3/3/1910, Analyst, {projectCount=9}
    public static IEmployee createEmployee(String employeeText) {
        Matcher peopleMat = Employee.PEOPLE_PAT.matcher(employeeText);
        IEmployee employee = null;
        if (peopleMat.find()) {
            employee = switch (peopleMat.group("role")) {
                case "Analyst" -> new Analyst(employeeText);
                case "Programmer" -> new Programmer(employeeText);
                case "Manager" -> new Manager(employeeText);
                case "CEO" -> new CEO(employeeText);
                default -> () -> {
                    return 0;
                };
            };
        }
        return employee;
    }

    @Override
    public String toString() {
        return String.format("%s, %s: %s, bonus: %s", lastName, firstName, moneyFormatter.format(getSalary()), moneyFormatter.format(getBonus()));
    }

    private static final class DummyEmployee extends Employee {
        @Override
        public int getSalary() {
            return 0;
        }
    }
}
