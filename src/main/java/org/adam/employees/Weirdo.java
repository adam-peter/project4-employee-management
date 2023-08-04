package org.adam.employees;

import java.time.LocalDate;

interface Person {
    public void sayHello(Weirdo weirdo);
}

public record Weirdo(String lastName, String firstName, LocalDate dob) implements Person {
    public Weirdo(String lastName, String firstName) {
        this(lastName, firstName, LocalDate.now());
    }

    public void sayHello(Weirdo weirdo) {
        System.out.printf("Hello, %s!%n", weirdo.firstName);
    }
}
