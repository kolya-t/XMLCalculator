package ru.eninja.xmlcalculator.model;

public class Expression {

    private Operation operation;

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "Expression{" +
                "operation=" + operation +
                '}';
    }
}
