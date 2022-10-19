package testcases.TestRailTest;

import org.testng.annotations.Test;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

public class SampleTest extends BaseCaseAQS {

    @TestRails(id = "1")
    @Test(groups = {"smoke"})
    public void BB001_GetToken_001() {
        System.out.println("I am in test1 test method and it will pass.");
    }

    @TestRails (id = "2")
    @Test(groups = {"smoke"})//(expectedExceptions=RuntimeException.class)
    public void BB001_GetToken_002() {
        System.out.println("I am in test2 test method and it will fail.");
    }
/*
    @TestRails (id = "3")
    @Test
    public void BB001_GetToken_003() {
        throw new SkipException("Skipping the test3 test method!");
    }
    private int i=0;
    @TestRails (id = "4")
    @Test(successPercentage=60, invocationCount=5)
    public void BB001_GetToken_004() {
        i++;
        System.out.println("test4 test method, invocation count: " + i);
        if (i == 1 || i == 2) {
            System.out.println("test4 failed!");
            Assert.assertEquals(i, 8);
        }
    }*/

}
