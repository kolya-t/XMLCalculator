package ru.eninja.xmlcalculator;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        String srcFilename = "src/main/resources/sampleTest.xml";
        String targetFilename = "src/main/resources/target.xml";

        try {
            new XMLCalculator(true).calculate(srcFilename, targetFilename);
        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }
}
