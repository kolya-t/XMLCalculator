package ru.eninja.xmlcalculator.model;

import java.util.LinkedList;
import java.util.List;

public class Operation {

    private OperationType type;
    private List<Operation> operations = new LinkedList<>();
    private List<Arg> args = new LinkedList<>();

    public Operation(OperationType type) {
        this.type = type;
    }

    public void addOperation(Operation operation) {
        this.operations.add(operation);
    }

    public void addArg(Arg arg) {
        this.args.add(arg);
    }

    @Override
    public String toString() {
        return "Operation{" +
                "type=" + type +
                ", operations=" + operations +
                ", args=" + args +
                '}';
    }
}
