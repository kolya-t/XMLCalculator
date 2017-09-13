package ru.eninja.xmlcalculator;

import org.xml.sax.SAXException;
import ru.eninja.xmlcalculator.io.ExpressionsParser;
import ru.eninja.xmlcalculator.model.Expression;
import ru.eninja.xmlcalculator.validator.XSDValidator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;

public class XMLCalculator {

    private static final String XSD_FILENAME = "Calculator.xsd";
    private final SAXParserFactory factory;
    private final XSDValidator xsdValidator;
    private boolean isValidating;

    public XMLCalculator() throws SAXException {
        this(false);
    }

    public XMLCalculator(boolean validating) throws SAXException {
        this.isValidating = validating;
        factory = SAXParserFactory.newInstance();

        InputStream xsdInputStream = getClass().getClassLoader().getResourceAsStream(XSD_FILENAME);
        xsdValidator = new XSDValidator(new StreamSource(xsdInputStream));
    }

    public void calculate(String sourceXmlPath) {
        // todo: брать targetPath из sourcePath
    }

    public void calculate(String sourceXmlPath, String targetXmlPath)
            throws SAXException, ParserConfigurationException, IOException {
        File source = new File(sourceXmlPath);
        File target = new File(targetXmlPath);

        assertNotExists(source);
        assertExists(target);

        if (isValidating) {
            xsdValidator.validate(source);
        }

        // todo: спрятать парсинг
        SAXParser saxParser = factory.newSAXParser();
        ExpressionsParser argHandler = new ExpressionsParser();
        saxParser.parse(source, argHandler);

        List<Expression> expressions = argHandler.getExpressions();
        // todo: посчитать результаты expressions и вывести в target
    }

    private void assertNotExists(File file) throws FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException(String.format("File \'%s\' not found", file));
        }
    }

    private void assertExists(File file) throws FileAlreadyExistsException {
        if (file.exists()) {
            throw new FileAlreadyExistsException(String.format("File \'%s\' already exists", file));
        }
    }
}
