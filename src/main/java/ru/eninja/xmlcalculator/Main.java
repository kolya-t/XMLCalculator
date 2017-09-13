package ru.eninja.xmlcalculator;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import ru.eninja.xmlcalculator.model.SimpleCalculator;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new XmlMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        SimpleCalculator calculator = mapper.readValue(
                Main.class.getClassLoader().getResourceAsStream("sampleTest.xml"),
                SimpleCalculator.class);

        calculator.calculate();

        mapper.writeValue(new File("src/main/resources/target.xml"), calculator);
    }
}
