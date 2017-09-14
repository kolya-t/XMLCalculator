package ru.eninja.xmlcalculator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import org.xml.sax.SAXException;
import ru.eninja.xmlcalculator.model.SimpleCalculator;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class XmlCalculator {

    private static final String XSD_FILENAME = "Calculator.xsd";
    private final SchemaFactory schemaFactory;
    private final XmlMapper mapper;

    public XmlCalculator() {
        schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        mapper = new XmlMapper();
        mapper.enable(ToXmlGenerator.Feature.WRITE_XML_DECLARATION);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public void calculate(File srcXml, File destXml) throws IOException {
        if (!srcXml.exists() || !srcXml.isFile()) {
            throw new FileNotFoundException(String.format("File \'%s\' not found", srcXml));
        }

        try {
            schemaFactory
                    .newSchema(new StreamSource(new File(srcXml.getParentFile(), XSD_FILENAME)))
                    .newValidator()
                    .validate(new StreamSource(srcXml));
        } catch (SAXException e) {
            System.err.println("XML invalid. Reason: " + e);
        }

        SimpleCalculator calculator = mapper.readValue(srcXml, SimpleCalculator.class);
        calculator.calculate();
        mapper.writeValue(destXml, calculator);
    }
}
