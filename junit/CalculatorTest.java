package examples;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    //Define object for calculator
     static Calculator calc;

    @BeforeAll
    public static void setUp(){

        //Initialize calc
        calc =new Calculator();
    }

    @Test
    public void addMethodTest(){
    //call the add method
        int result =calc.add(10,5);
        System.out.println(result);

        //Assertion: Use Assertion.assertEqual when you want to use the all types of assertion
        // otherwise you can directly use the assertion
        assertEquals(15, result);

    }
}
