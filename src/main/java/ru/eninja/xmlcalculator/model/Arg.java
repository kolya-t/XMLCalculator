package ru.eninja.xmlcalculator.model;

public class Arg {

    private int value;

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Arg{" +
                "value=" + value +
                '}';
    }
}
