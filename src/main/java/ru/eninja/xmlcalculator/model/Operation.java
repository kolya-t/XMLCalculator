package ru.eninja.xmlcalculator.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

class Operation {

    @JacksonXmlProperty(localName = "OperationType", isAttribute = true)
    private OperationType type;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "operation")
    private Operation[] operations;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "arg")
    private int[] args;

    double calculateResult() {
        double result;
        double arg1;
        double arg2;

        if (operations != null) {
            arg1 = operations[0].calculateResult();
            arg2 = operations[1].calculateResult();
        } else {
            arg1 = args[0];
            arg2 = args[1];
        }

        switch (type) {
            case SUB:
                result = arg1 - arg2;
                break;
            case SUM:
                result = arg1 + arg2;
                break;
            case DIV:
                result = arg1 / arg2;
                break;
            case MUL:
                result = arg1 * arg2;
                break;
            default:
                throw new IllegalStateException("Unknown type");
        }

        return result;
    }
}
