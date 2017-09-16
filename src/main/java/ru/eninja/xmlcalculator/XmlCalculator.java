package ru.eninja.xmlcalculator;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import org.xml.sax.SAXException;
import ru.eninja.xmlcalculator.model.SimpleCalculator;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class XmlCalculator {

    public static final String XSD_FILENAME = "Calculator.xsd";
    final Validator validator;
    final XmlMapper mapper;

    public XmlCalculator() throws SAXException {
        validator = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                .newSchema(new StreamSource(getClass().getClassLoader().getResourceAsStream(XSD_FILENAME)))
                .newValidator();
        mapper = new XmlMapper();
        mapper.enable(ToXmlGenerator.Feature.WRITE_XML_DECLARATION);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public void calculate(File srcXml, File destXml) throws IOException, SAXException {
        if (!srcXml.exists() || !srcXml.isFile()) {
            throw new FileNotFoundException(String.format("File \'%s\' not found", srcXml));
        }

        validator.validate(new StreamSource(srcXml));

        SimpleCalculator calculator = mapper.readValue(srcXml, SimpleCalculator.class);
        calculator.calculate();
        mapper.writeValue(destXml, calculator);
    }
}
