import org.junit.jupiter.api.Test;

public class Tests {

    @Test
    public void runServiceTest() {
        ServiceTest serviceTest = new ServiceTest();
        serviceTest.testServiceUtilizator();
    }

    @Test
    public void runPrietenieTest() {
        PrietenieTest prietenieTest = new PrietenieTest();
        prietenieTest.testServicePrietenie();
    }

    @Test
    public void runComunitatiTest() {
        ComunitatiTest comunitatiTest = new ComunitatiTest();
        comunitatiTest.testServiceComunitati();
    }
}
