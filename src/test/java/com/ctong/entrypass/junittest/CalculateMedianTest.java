package com.ctong.entrypass.junittest;

import org.junit.*;

import static org.junit.Assert.*;

public class CalculateMedianTest {

    @BeforeClass // needs static
    public static void runOnceBeforeFirstTest() {
        System.out.println("------ Start Testing ------");
    }
    @AfterClass // needs static
    public static void runOnceAfterLastTest() {
        System.out.println("===== End Testing =====");
    }
    @Before
    public void runBeforeEachCase() {
        System.out.println("start a case");
    }
    @After
    public void runAfterEachTest() {
        System.out.println("end a case");
    }

    @Test
    public void getMedianTest1() {
        // Assert.fail("Not yet implemented");
    }

    @Test
    public void getMedianTest2() {
        CalculateMedian cm = new CalculateMedian();
        int[] arr = {1, 2, 3};
        int res = cm.getMedian(arr);
        assertEquals(2, res); // (expected, actual)
    }

    @Test
    public void getMedianTest3() {
        CalculateMedian cm = new CalculateMedian();
        int[] arr = {1, 2, 3};
        double res = cm.getMedian(arr);
        assertEquals(2, res, 0.00001); // (expected, actual, tolerance)
    }
}
