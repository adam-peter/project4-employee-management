package org.adam.employees;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Programmer {
    private String lastName;
    private String firstName;
    private LocalDate dob;
    private int linesOfCode = 0;
    private int yearsOfExperience = 0;
    private int iq = 0;

    private final DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
    private final NumberFormat moneyFormatter = NumberFormat.getCurrencyInstance();

    private final String peopleRegex = "(?<lastName>\\w+),\\s*(?<firstName>\\w+),\\s*(?<dob>\\d{1,2}/\\d{1,2}/\\d{4}),\\s*(?<role>\\w+)(?:,\\s*\\{(?<details>.*)\\})?\\n";
    private final Pattern peoplePat = Pattern.compile(peopleRegex);
    private final String progRegex = "\\w+=(?<locpd>\\w+),\\w+=(?<yoe>\\w+),\\w+=(?<iq>\\w+)";
    private final Pattern progPat = Pattern.compile(progRegex);


    public Programmer(String personText) {
        Matcher personMat = peoplePat.matcher(personText);
        if (personMat.find()) {
            this.lastName = personMat.group("lastName");
            this.firstName = personMat.group("firstName");
            this.dob = LocalDate.from(dtFormatter.parse(personMat.group("dob")));

            Matcher progMat = progPat.matcher(personMat.group("details"));
            if (progMat.find()) {
                this.linesOfCode = Integer.parseInt(progMat.group("locpd"));
                this.yearsOfExperience = Integer.parseInt(progMat.group("yoe"));
                this.iq = Integer.parseInt(progMat.group("iq"));
            }
        }
    }

    public int getSalary() {
        return 3000 + linesOfCode * yearsOfExperience * iq;
    }

    @Override
    public String toString() {
        return String.format("%s, %s: %s", lastName, firstName, moneyFormatter.format(getSalary()));
    }
}
