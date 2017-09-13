package ru.eninja.xmlcalculator.validator;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XSDValidator {

    private Validator validator;

    public XSDValidator(Source xsd) throws SAXException {
        validator = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                .newSchema(xsd)
                .newValidator();
        validator.setErrorHandler(new XSDValidatorErrorHandler());
    }

    public void validate(File xml) throws IOException, SAXException {
        validator.validate(new StreamSource(xml));
    }
}
