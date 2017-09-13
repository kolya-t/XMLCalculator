package ru.eninja.xmlcalculator.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "simpleCalculator")
public class SimpleCalculator {

    @JacksonXmlElementWrapper(localName = "expressions")
    @JacksonXmlProperty(localName = "expression")
    private Expression[] expressions;

    @JacksonXmlElementWrapper(localName = "expressionResults")
    @JacksonXmlProperty(localName = "expressionResult")
    private ExpressionResult[] results;

    public void calculate() {
        if (expressions != null) {
            results = new ExpressionResult[expressions.length];
            for (int i = 0; i < expressions.length; i++) {
                results[i] = expressions[i].calculateResult();
            }
        }
    }
}
