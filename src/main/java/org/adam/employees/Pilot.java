package org.adam.employees;

public class Pilot implements IPilot {
    private int hoursFlown = 0;
    private boolean instrumentFlightRated = false;

    public Pilot(int hoursFlown, boolean instrumentFlightRated) {
        this.hoursFlown = hoursFlown;
        this.instrumentFlightRated = instrumentFlightRated;
    }

    public int getHoursFlown() {
        return hoursFlown;
    }

    public void setHoursFlown(int hoursFlown) {
        this.hoursFlown = hoursFlown;
    }

    public boolean isInstrumentFlightRated() {
        return instrumentFlightRated;
    }

    public void setInstrumentFlightRated(boolean instrumentFlightRated) {
        this.instrumentFlightRated = instrumentFlightRated;
    }

    public void fly() {
        System.out.println("Prepare for take off!");
    }
}
