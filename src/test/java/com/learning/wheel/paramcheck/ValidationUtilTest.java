package com.learning.wheel.paramcheck;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author mazhenjie
 * @since 2019/5/8
 */
public class ValidationUtilTest {

    ParamTestDTO paramTestDTO = new ParamTestDTO();
    CheckService checkService = new CheckService();
    ValidResult validResult;

    @Before
    public void setUp() {
        paramTestDTO.buildSuccess();
    }

    @Test
    public void testSuccess() {
        validResult = checkService.checkParam(paramTestDTO);
        Assert.assertTrue(validResult.isSuccess());
    }

    @Test
    public void testTrue() {
        paramTestDTO.setTestTrue(false);
        validResult = checkService.checkParam(paramTestDTO);
        Assert.assertFalse(validResult.isSuccess());
        Assert.assertEquals("Error True", validResult.getParamError());
    }

    @Test
    public void testFalse() {
        paramTestDTO.setTestFalse(true);
        validResult = checkService.checkParam(paramTestDTO);
        Assert.assertFalse(validResult.isSuccess());
        Assert.assertEquals("Error False", validResult.getParamError());
    }

    @Test
    public void testStrMax() {
        paramTestDTO.setTestStrMax("11");
        validResult = checkService.checkParam(paramTestDTO);
        Assert.assertFalse(validResult.isSuccess());
        Assert.assertEquals("Error StrMax", validResult.getParamError());
    }

    @Test
    public void testStrMin() {
        paramTestDTO.setTestStrMin("0");
        validResult = checkService.checkParam(paramTestDTO);
        Assert.assertFalse(validResult.isSuccess());
        Assert.assertEquals("Error StrMin", validResult.getParamError());
    }

    @Test
    public void testMax() {
        paramTestDTO.setTestMax(11);
        validResult = checkService.checkParam(paramTestDTO);
        Assert.assertFalse(validResult.isSuccess());
        Assert.assertEquals("Error Max", validResult.getParamError());
    }

    @Test
    public void testMin() {
        paramTestDTO.setTestMin(0.8);
        validResult = checkService.checkParam(paramTestDTO);
        Assert.assertFalse(validResult.isSuccess());
        Assert.assertEquals("Error Min", validResult.getParamError());
    }

    @Test
    public void testDig() {
        paramTestDTO.setTestDig(BigDecimal.valueOf(10.1111));
        validResult = checkService.checkParam(paramTestDTO);
        Assert.assertFalse(validResult.isSuccess());
        Assert.assertEquals("Error Dig", validResult.getParamError());
    }

    @Test
    public void testPast() {
        paramTestDTO.setTestPast(new Date(System.currentTimeMillis() + 100));
        validResult = checkService.checkParam(paramTestDTO);
        Assert.assertFalse(validResult.isSuccess());
        Assert.assertEquals("Error Past", validResult.getParamError());
    }

    @Test
    public void testFuture() {
        paramTestDTO.setTestFuture(new Date(System.currentTimeMillis() - 100));
        validResult = checkService.checkParam(paramTestDTO);
        Assert.assertFalse(validResult.isSuccess());
        Assert.assertEquals("Error Future", validResult.getParamError());
    }

    @Test
    public void testNull() {
        paramTestDTO.setTestNull("");
        validResult = checkService.checkParam(paramTestDTO);
        Assert.assertFalse(validResult.isSuccess());
        Assert.assertEquals("Error Null", validResult.getParamError());
    }

    @Test
    public void testNotNull() {
        paramTestDTO.setTestNonNull(null);
        validResult = checkService.checkParam(paramTestDTO);
        Assert.assertFalse(validResult.isSuccess());
        Assert.assertEquals("Error NonNull", validResult.getParamError());
    }

    @Test
    public void testPattern() {
        paramTestDTO.setTestPattern("111");
        validResult = checkService.checkParam(paramTestDTO);
        Assert.assertFalse(validResult.isSuccess());
        Assert.assertEquals("Error Pattern", validResult.getParamError());
    }

    @Test
    public void testSize() {
        paramTestDTO.setTestSize(new ArrayList<String>());
        validResult = checkService.checkParam(paramTestDTO);
        Assert.assertFalse(validResult.isSuccess());
        Assert.assertEquals("Error Size", validResult.getParamError());
    }

    @Test
    public void testLength() {
        paramTestDTO.setTestLength("12345678901");
        validResult = checkService.checkParam(paramTestDTO);
        Assert.assertFalse(validResult.isSuccess());
        Assert.assertEquals("Error Length", validResult.getParamError());
    }

    @Test
    public void testBlank() {
        paramTestDTO.setTestBlank("");
        validResult = checkService.checkParam(paramTestDTO);
        Assert.assertFalse(validResult.isSuccess());
        Assert.assertEquals("Error Blank", validResult.getParamError());
    }

    @Test
    public void testEmpty() {
        paramTestDTO.setTestEmpty("");
        validResult = checkService.checkParam(paramTestDTO);
        Assert.assertFalse(validResult.isSuccess());
        Assert.assertEquals("Error NotEmpty", validResult.getParamError());
    }

    @Test
    public void testRange() {
        paramTestDTO.setTestRange("11");
        validResult = checkService.checkParam(paramTestDTO);
        Assert.assertFalse(validResult.isSuccess());
        Assert.assertEquals("Error Range", validResult.getParamError());
    }
}
