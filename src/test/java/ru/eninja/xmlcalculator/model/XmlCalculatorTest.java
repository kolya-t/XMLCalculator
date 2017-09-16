package ru.eninja.xmlcalculator.model;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import ru.eninja.xmlcalculator.XmlCalculator;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

import static ru.eninja.xmlcalculator.XmlCalculator.XSD_FILENAME;

public class XmlCalculatorTest {

    private static final String SRC_DIR = "src/test/resources";
    private static XmlCalculator xmlCalculator;
    private static Validator validator;
    private static XmlMapper mapper;

    @BeforeClass
    public static void initData() throws SAXException {
        xmlCalculator = new XmlCalculator();
        validator = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                .newSchema(new StreamSource(XmlCalculator.class.getClassLoader().getResourceAsStream(XSD_FILENAME)))
                .newValidator();
        mapper = new XmlMapper();
    }

    @AfterClass
    public static void deleteResultXmls() {
        Pattern resultPattern = Pattern.compile(".+Result\\.xml");
        String[] fileList = new File(SRC_DIR).list();
        if (fileList != null) {
            for (String filename : fileList) {
                if (resultPattern.matcher(filename).matches()) {
                    new File(SRC_DIR, filename).delete();
                }
            }
        }
    }

    @Test
    public void sampleTest() throws IOException, SAXException {
        assertCalculationsCorrect("sampleTest");
    }

    @Test(expected = FileNotFoundException.class)
    public void nonexistentFileTest() throws IOException, SAXException {
        calculate("nonexistent.xml", "irrelevant");
    }

    @Test(expected = FileNotFoundException.class)
    public void directoryTest() throws IOException, SAXException {
        calculate("", "irrelevant");
    }

    @Test(expected = SAXParseException.class)
    public void badStructure1Test() throws IOException, SAXException {
        calculate("badStructureTest1.xml", "irrelevant");
    }

    @Test
    public void oneExpressionTest() throws IOException, SAXException {
        assertCalculationsCorrect("oneExpressionTest");
    }

    @Test
    public void threeExpressionsTest() throws IOException, SAXException {
        assertCalculationsCorrect("threeExpressionsTest");
    }

    @Test
    public void twoXmlsTest() throws IOException, SAXException {
        calculate("twoXmlsTest1.xml", "twoXmlsTest1Result.xml");
        calculate("twoXmlsTest2.xml", "twoXmlsTest2Result.xml");
        validate("twoXmlsTest1Result.xml");
        validate("twoXmlsTest2Result.xml");
    }

    private void calculate(String srcFilename, String destFilename) throws IOException, SAXException {
        xmlCalculator.calculate(new File(SRC_DIR, srcFilename), new File(SRC_DIR, destFilename));
    }

    private void validate(String dstFilename) throws IOException, SAXException {
        validator.validate(new StreamSource(new File(SRC_DIR, dstFilename)));
    }

    private void assertCalculationsCorrect(String testName) throws IOException, SAXException {
        String srcFilename = testName + ".xml";
        String destFilename = testName + "Result.xml";
        String expectedFilename = testName + "Expected.xml";

        calculate(srcFilename, destFilename);
        validate(destFilename);

        SimpleCalculator expected = loadCalculatorFromXml(expectedFilename);
        SimpleCalculator calculated = loadCalculatorFromXml(destFilename);
        Assert.assertArrayEquals(
                Arrays.stream(expected.getResults()).mapToDouble(ExpressionResult::getResult).toArray(),
                Arrays.stream(calculated.getResults()).mapToDouble(ExpressionResult::getResult).toArray(),
                1e-5);
    }

    private SimpleCalculator loadCalculatorFromXml(String filename) throws IOException {
        return mapper.readValue(new File(SRC_DIR, filename), SimpleCalculator.class);
    }
}
