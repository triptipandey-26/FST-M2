package examples;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParametersTest {

    Calculator calc=new Calculator();

    @ParameterizedTest
    @CsvSource({
            "1, 2, 3",
            "5, 5, 10",
            "2, 2, 4"
    })
    public void addMethodTest(int num1, int num2, int answer){
        int result=calc.add(num1, num2);
        System.out.println(result);

        //Assertion
        assertEquals(answer, result);

    }

//    @ParameterizedTest
//    @ValueSource(ints ={1,2,3,4,5})
//
//    public void squareMethodTest(int num){
//
//        int result=calc.square(num);
//        assertEquals(25, result);
//    }

}
