package org.adam.employees;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analyst {
    private String lastName;
    private String firstName;
    private LocalDate dob;
    private int projectCount = 0;

    private final DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
    private final NumberFormat moneyFormatter = NumberFormat.getCurrencyInstance();

    private final String peopleRegex = "(?<lastName>\\w+),\\s*(?<firstName>\\w+),\\s*(?<dob>\\d{1,2}/\\d{1,2}/\\d{4}),\\s*(?<role>\\w+)(?:,\\s*\\{(?<details>.*)\\})?\\n";
    private final Pattern peoplePat = Pattern.compile(peopleRegex);
    private final String analystRegex = "\\w+=(?<projectCount>\\w+)";
    private final Pattern analystPat = Pattern.compile(analystRegex);

    public Analyst(String personText) {
        Matcher personMat = peoplePat.matcher(personText);
        if (personMat.find()) {
            this.lastName = personMat.group("lastName");
            this.firstName = personMat.group("firstName");
            this.dob = LocalDate.from(dtFormatter.parse(personMat.group("dob")));

            Matcher analystMat = analystPat.matcher(personMat.group("details"));
            if (analystMat.find()) {
                this.projectCount = Integer.parseInt(analystMat.group("projectCount"));
            }
        }
    }

    public int getSalary() {
        return 2500 + 2 * projectCount;
    }

    @Override
    public String toString() {
        return String.format("%s, %s: %s", lastName, firstName, moneyFormatter.format(getSalary()));
    }
}
