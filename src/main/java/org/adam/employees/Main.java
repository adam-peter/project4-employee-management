package org.adam.employees;

import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        String peopleText = """
                Flinstone1, Fred, 1/1/1900, Programmer, {locpd=2000,yoe=10,iq=140}
                Flinstone2, Fred, 1/1/1900, Programmer, {locpd=1300,yoe=14,iq=100}
                Flinstone3, Fred, 1/1/1900, Programmer, {locpd=2300,yoe=8,iq=105}
                Flinstone4, Fred, 1/1/1900, Programmer, {locpd=1630,yoe=3,iq=115}
                Flinstone5, Fred, 1/1/1900, Programmer, {locpd=5,yoe=10,iq=100}
                Rubble1, Barney, 2/2/1905, Manager, {orgSize=300,dr=10}
                Rubble2, Barney, 2/2/1905, Manager, {orgSize=100,dr=4}
                Rubble3, Barney, 2/2/1905, Manager, {orgSize=200,dr=2}
                Rubble4, Barney, 2/2/1905, Manager, {orgSize=500,dr=8}
                Rubble5, Barney, 2/2/1905, Manager, {orgSize=175,dr=20}
                Flinstone1, Wilma, 3/3/1910, Analyst, {projectCount=3}
                Flinstone2, Wilma, 3/3/1910, Analyst, {projectCount=4}
                Flinstone3, Wilma, 3/3/1910, Analyst, {projectCount=5}
                Flinstone4, Wilma, 3/3/1910, Analyst, {projectCount=6}
                Flinstone5, Wilma, 3/3/1910, Analyst, {projectCount=9}
                Rubble1, Betty, 4/4/1915, CEO, {avgStockPrice=300}
                """;

        String peopleRegex =
                "(?<lastName>\\w+),\\s*(?<firstName>\\w+),\\s*(?<dob>\\d{1,2}/\\d{1,2}/\\d{4}),\\s*(?<role>\\w+)(?:,\\s*\\{(?<details>.*)\\})?\\n";
        Pattern peoplePat = Pattern.compile(peopleRegex);
        Matcher peopleMat = peoplePat.matcher(peopleText);

        //Role regexes
        String analystRegex = "\\w+=(?<projectCount>\\w+)";
        Pattern analystPat = Pattern.compile(analystRegex);

        String progRegex = "\\w+=(?<locpd>\\w+),\\w+=(?<yoe>\\w+),\\w+=(?<iq>\\w+)";
        Pattern progPat = Pattern.compile(progRegex);

        String managerRegex = "\\w+=(?<orgSize>\\w+),\\w+=(?<dr>\\w+)";
        Pattern managerPat = Pattern.compile(managerRegex);

        String ceoRegex = "\\w+=(?<avgStockPrice>\\w+)";
        Pattern ceoPat = Pattern.compile(ceoRegex);

        int totalSalaries = 0;
        while (peopleMat.find()) {
            totalSalaries += switch (peopleMat.group("role")) {
                case "Analyst" -> {
                    String details = peopleMat.group("details");
                    Matcher analystMat = analystPat.matcher(details);
                    int salary = 0;
                    if (analystMat.find()) {
                        int projectCount = Integer.parseInt(analystMat.group("projectCount"));
                        salary = 2500 + 2 * projectCount;
                    } else {
                        salary = 2500;
                    }
                    System.out.printf("%s: %s%n", peopleMat.group("lastName"), NumberFormat.getCurrencyInstance().format(salary));
                    yield salary;
                }
                case "Programmer" -> {
                    String details = peopleMat.group("details");
                    Matcher progMat = progPat.matcher(details);
                    int salary = 0;
                    //our matcher wont match anything if we dont call mat.find() or .matches()
                    if (progMat.find()) {
                        int locpd = Integer.parseInt(progMat.group("locpd"));
                        int yoe = Integer.parseInt(progMat.group("yoe"));
                        int iq = Integer.parseInt(progMat.group("iq"));
                        salary = 3000 + locpd * yoe * iq; //simple salary calculation
                    } else {
                        salary = 3000;
                    }
                    System.out.printf("%s: %s%n", peopleMat.group("lastName"), NumberFormat.getCurrencyInstance().format(salary));
                    yield salary; //better to have 1 return statement
                }
                case "Manager" -> {
                    String details = peopleMat.group("details");
                    Matcher managerMat = managerPat.matcher(details);
                    int salary = 0;
                    if (managerMat.find()) {
                        int orgSize = Integer.parseInt(managerMat.group("orgSize"));
                        int directReports = Integer.parseInt(managerMat.group("dr"));
                        salary = 3500 + orgSize * directReports;
                    } else {
                        salary = 3500;
                    }
                    System.out.printf("%s: %s%n", peopleMat.group("lastName"), NumberFormat.getCurrencyInstance().format(salary));
                    yield salary;
                }
                case "CEO" -> {
                    String details = peopleMat.group("details");
                    Matcher ceoMat = ceoPat.matcher(details);
                    int salary = 0;
                    if (ceoMat.find()) {
                        int avgStockPrice = Integer.parseInt(ceoMat.group("avgStockPrice"));
                        salary = 5000 * avgStockPrice;
                    } else {
                        salary = 5000;
                    }
                    System.out.printf("%s: %s%n", peopleMat.group("lastName"), NumberFormat.getCurrencyInstance().format(salary));
                    yield salary;
                }
                default -> {
                    yield 0;
                }
            };
        }
        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance();
        System.out.printf("The total payout should be %s%n.", currencyInstance.format(totalSalaries));
    }
}
