package test;

import main.java.FormulaParser;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static junit.framework.TestCase.assertEquals;


/**
 * Created by gres on 26.06.2016.
 */
public class ParserTest {
    FormulaParser fp;

    @Test
    public void parserTest1() {
        Double dd = 96.0;
        String inputString = "(2 + 5 * 2 * (((3 + 8) * 4) - 25))/2";
        fp = new FormulaParser(inputString);
        assertEquals(fp.calculate(), dd);
    }

    @Test
    public void parserTest2() {
        Double dd = 746.4;
        String inputString = "(2 + 5 + 17 * 23 / (5+5) * 2 * (((3 + 8) * 4) - 25)) / 2";
        fp = new FormulaParser(inputString);
        assertEquals(dd, fp.calculate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void parserException() {
        String inputString = "(2 + 5 + 17 * 23 / (5+5) * 2 * (((3 + 8) * 4) - 25)) / 2))"; // лишние скобки
        fp = new FormulaParser(inputString);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parserExceptionDivisionByZero() {
        String inputString = "(2 + 5 + 17 * 23 / (5+5) * 2 * (((3 + 8) * 4) - 25)) / 0"; // Деление на 0
        fp = new FormulaParser(inputString);
    }
}
