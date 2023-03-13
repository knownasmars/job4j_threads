package ru.job4j.fork;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IndexFinderTest {
    @Test
    public void whenFindIndexIn10LessElementsArrayThenFound() {
        Person[] small = new Person[]{
                new Person("Andrey", 35),
                new Person("Dima", 23),
                new Person("Valera", 15)
        };
        assertThat(IndexFinder.finder(small, new Person("Valera", 15))).isEqualTo(2);
    }

    @Test
    void whenFindIndexIn10LessElementsArrayThenNotFound() {
        Person[] small = new Person[]{
                new Person("Andrey", 35),
                new Person("Dima", 23),
                new Person("Valera", 15)
        };
        Person valera = new Person("Vasyok", 15);
        assertThat(IndexFinder.finder(small, valera)).isEqualTo(-1);
    }

    @Test
    void whenFindIndexIn10MoreElementsArrayThenFound() {
        Person[] big = new Person[]{
                new Person("Andrey", 35),
                new Person("Dima", 23),
                new Person("Valera", 15),
                new Person("Oleg", 22),
                new Person("Anton", 23),
                new Person("Mitya", 59),
                new Person("Petya", 43),
                new Person("Alex", 44),
                new Person("John", 24),
                new Person("Sveta", 25),
                new Person("Katya", 33),
                new Person("Nastya", 31)
        };
        assertThat(IndexFinder.finder(big, new Person("Andrey", 35))).isEqualTo(0);
    }

    @Test
    void whenFindIndexIn10MoreElementsArrayThenNotFound() {
        Person[] big = new Person[]{
                new Person("Andrey", 35),
                new Person("Dima", 23),
                new Person("Valera", 15),
                new Person("Oleg", 22),
                new Person("Anton", 23),
                new Person("Mitya", 59),
                new Person("Petya", 43),
                new Person("Alex", 44),
                new Person("John", 24),
                new Person("Sveta", 25),
                new Person("Katya", 33),
                new Person("Nastya", 31)
        };
        assertThat(IndexFinder.finder(big, new Person("Vasyok", 35))).isEqualTo(-1);
    }

    @Test
    void whenTestWithDifferentObjects() {
        String[] array = new String[]{
                "Andrey", "Dima", "Valera",
                "Oleg", "Anton", "Mitya",
                "Petya", "Alex", "John",
                "Sveta", "Katya", "Nastya"
        };
        assertThat(IndexFinder.finder(array, "Alex")).isEqualTo(7);
    }
}