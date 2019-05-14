package com.learning.wheel.paramcheck;

import org.junit.Before;
import org.junit.Test;

/**
 * @author mazhenjie
 * @since 2019/5/13
 */
public class PerformanceTest {
    ParamTestDTO paramTestDTO = new ParamTestDTO();
    CheckService checkService = new CheckService();

    @Before
    public void setUp() {
        paramTestDTO.buildSuccess();
        paramTestDTO.setTestEmpty("");
        paramTestDTO.setTestTrue(false);
    }

    @Test
    public void testNormal() {
        for (int i = 0; i < 100000; i++) {
            checkService.checkNormal(paramTestDTO);
        }
    }

    @Test
    public void testAllCheck() {
        for (int i = 0; i < 100000; i++) {
            checkService.checkAllParam(paramTestDTO);
        }
    }

    @Test
    public void testFailFast() {
        for (int i = 0; i < 100000; i++) {
            checkService.checkParam(paramTestDTO);
        }
    }
}
