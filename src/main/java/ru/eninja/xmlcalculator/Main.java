package ru.eninja.xmlcalculator;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("Need at least one argument");
            System.exit(1);
        }

        File srcXml = new File(args[0]);
        File destXml = (args.length > 1) ? new File(args[1]) : new File(srcXml.getParentFile(), "target.xml");

        new XmlCalculator().calculate(srcXml, destXml);
    }
}
