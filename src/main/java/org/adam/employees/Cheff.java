package org.adam.employees;

public interface Cheff {
    String favoriteFood = "hamburger";

    default String getFavoriteFood() {
        return favoriteFood;
    }

    default void cook() {
        System.out.printf("I am now cooking %s!%n", favoriteFood);
    }

    default void cook(String food) {
        System.out.printf("I am now cooking %s!%n", food);
    }

    default String cleanUp() {
        return "I'm done cleaning up.";
    }
}
