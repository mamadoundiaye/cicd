package sn.ept.git.seminaire.cicd.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class B_TripleAPrincipal {

    private static ICalculator calculator;
    double a,b;

    @BeforeAll
    static void beforeAll(){
        calculator = new Calculator();
    }


    @BeforeEach
     void beforeEach(){
         a=100;
         b=200;
         log.info(" one");
    }

    @BeforeEach
    void beforeEachTwo(){
        log.info(" two");
    }

    @Test
    void shouldRespectTripleAPrinciple() {
        //arrange

        double expected=a+b;

        //act
        double result = calculator.add(a,b);

        //assert
        assertThat(result).isEqualTo(expected);
    }

}