package ru.eninja.xmlcalculator.io;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ru.eninja.xmlcalculator.model.Arg;
import ru.eninja.xmlcalculator.model.Expression;
import ru.eninja.xmlcalculator.model.Operation;
import ru.eninja.xmlcalculator.model.OperationType;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class ExpressionsParser extends DefaultHandler {

    private static final String OPERATION_TYPE = "OperationType";
    private static final String EXPRESSION = "expression";
    private static final String OPERATION = "operation";
    private static final String ARG = "arg";

    private boolean inArg;

    private Expression expression;
    private Stack<Operation> operationStack = new Stack<>();
    private Arg arg;

    private List<Expression> expressions = new LinkedList<>();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
            case EXPRESSION:
                expression = new Expression();
                break;
            case OPERATION:
                OperationType opType = OperationType.valueOf(attributes.getValue(OPERATION_TYPE));
                operationStack.push(new Operation(opType));
                break;
            case ARG:
                inArg = true;
                arg = new Arg();
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case EXPRESSION:
                expressions.add(expression);
                break;
            case OPERATION:
                Operation op = operationStack.pop();
                if (!operationStack.empty()) {
                    operationStack.peek().addOperation(op);
                } else {
                    expression.setOperation(op);
                }

                break;
            case ARG:
                operationStack.peek().addArg(arg);
                inArg = false;
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (inArg) {
            arg.setValue(Integer.valueOf(String.copyValueOf(ch, start, length).trim()));
        }
    }

    public List<Expression> getExpressions() {
        return expressions;
    }
}
