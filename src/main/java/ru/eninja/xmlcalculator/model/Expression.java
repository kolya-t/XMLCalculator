package ru.eninja.xmlcalculator.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

class Expression {

    @JacksonXmlProperty(localName = "operation")
    private Operation operation;

    ExpressionResult calculateResult() {
        return new ExpressionResult(operation.calculateResult());
    }
}
