package ru.eninja.xmlcalculator.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

class ExpressionResult {

    @JacksonXmlProperty(localName = "result")
    private double result;

    ExpressionResult(double result) {
        this.result = result;
    }
}
