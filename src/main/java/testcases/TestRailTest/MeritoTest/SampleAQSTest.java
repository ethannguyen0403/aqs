package testcases.TestRailTest.MeritoTest;

import org.testng.annotations.Test;
import testcases.BaseCaseMeritoTestRails;
import utils.testraildemo.TestRails;

public class SampleAQSTest extends BaseCaseMeritoTestRails {

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


    @TestRails(id = "3")
    @Test(groups = {"smoke"})
    public void BB001_GetToken_003() {
        System.out.println("I am in test1 test method and it will pass.");
    }

    @TestRails (id = "5")
    @Test(groups = {"smoke2"})//(expectedExceptions=RuntimeException.class)
    public void BB001_GetToken_004() {
        System.out.println("I am in test2 test method and it will fail.");
    }

    @TestRails(id = "6")
    @Test(groups = {"smoke2"})
    public void BB001_GetToken_005() {
        System.out.println("I am in test1 test method and it will pass.");
    }

}
