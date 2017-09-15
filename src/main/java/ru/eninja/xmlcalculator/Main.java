package ru.eninja.xmlcalculator;

import org.xml.sax.SAXException;
import ru.eninja.xmlcalculator.model.XmlCalculator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    private static final String manual =
            "Need at least one argument.\n" +
            "First argument is source calculator xml data file.\n" +
            "Second (optional) argument is destination results file.\n" +
            "If second argument is missing, the results will be stored in the target.xml file located in the same " +
            "directory as the source file.\n" +
            "Examples:" +
            "java -jar calculator.jar src/main/resources/sampleTest.xml" +
            "java -jar calculator.jar \"src/main/resources/sample Test.xml\"" +
            "java -jar calculator.jar src/main/resources/sampleTest.xml src/main/resources/target.xml";

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(manual);
            System.exit(0);
        }

        File srcXml = new File(args[0]);
        File destXml = (args.length > 1) ? new File(args[1]) : new File(srcXml.getParentFile(), "target.xml");

        try {
            new XmlCalculator().calculate(srcXml, destXml);
            System.out.println(String.format("Calculations saved to \'%s\'", destXml));
        } catch (SAXException e) {
            System.err.println("XML invalid. Reason: " + e);
        } catch (FileNotFoundException e) {
            System.err.println(e);
        } catch (IOException ignored) {
        }
    }
}
