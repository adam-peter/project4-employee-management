package org.adam.employees;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CEO extends Employee implements IPilot {
    private int avgStockPrice = 0;
    private Pilot pilot = new Pilot(1000, true);

    private final DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
    private final NumberFormat moneyFormatter = NumberFormat.getCurrencyInstance();

    String ceoRegex = "\\w+=(?<avgStockPrice>\\w+)";
    Pattern ceoPat = Pattern.compile(ceoRegex);

    public CEO(String personText) {
        super(personText);
        Matcher ceoMat = ceoPat.matcher(peopleMat.group("details"));
        if (ceoMat.find()) {
            this.avgStockPrice = Integer.parseInt(ceoMat.group("avgStockPrice"));
        }
    }

    //Delegate methods of Pilot
    public int getSalary() {
        return 5000 * avgStockPrice;
    }

    public int getHoursFlown() {
        return pilot.getHoursFlown();
    }

    public void setHoursFlown(int hoursFlown) {
        pilot.setHoursFlown(hoursFlown);
    }

    public boolean isInstrumentFlightRated() {
        return pilot.isInstrumentFlightRated();
    }

    public void setInstrumentFlightRated(boolean instrumentFlightRated) {
        pilot.setInstrumentFlightRated(instrumentFlightRated);
    }

    public void fly() {
        pilot.fly();
    }
}
