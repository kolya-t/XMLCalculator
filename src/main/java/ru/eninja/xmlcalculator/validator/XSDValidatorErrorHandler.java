package ru.eninja.xmlcalculator.validator;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.util.logging.Logger;

public class XSDValidatorErrorHandler implements ErrorHandler {

    Logger log = Logger.getLogger(getClass().getName());

    @Override
    public void error(SAXParseException exception) throws SAXException {
        log.severe(exception.toString());
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        log.severe(exception.toString());
    }

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        log.warning(exception.toString());
    }
}