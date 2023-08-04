package org.adam.employees;

public interface IPilot {
    default void fly() {
        System.out.println("Prepare for takeoff");
    }
    int getHoursFlown();
    void setHoursFlown(int hoursFlown);
    boolean isInstrumentFlightRated();
    void setInstrumentFlightRated(boolean instrumentFlightRated);
}

