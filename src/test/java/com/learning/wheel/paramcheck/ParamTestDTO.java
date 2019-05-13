package com.learning.wheel.paramcheck;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author mazhenjie
 * @since 2019/5/7
 */
public class ParamTestDTO implements Serializable {


    private static final long serialVersionUID = 7123882542534668217L;

    @AssertTrue(message = "Error True")
    private Boolean testTrue;

    @AssertFalse(message = "Error False")
    private Boolean testFalse;

    @DecimalMax(value = "10", message = "Error StrMax")
    private String testStrMax;

    @DecimalMin(value = "1", message = "Error StrMin")
    private String testStrMin;

    @Max(value = 10, message = "Error Max")
    private Integer testMax;

    @Min(value = 1, message = "Error Min")
    private Double testMin;

    @Digits(integer = 2, fraction = 3, message = "Error Dig")
    private BigDecimal testDig;

    @Past(message = "Error Past")
    private Date testPast;

    @Future(message = "Error Future")
    private Date testFuture;

    @Null(message = "Error Null")
    private String testNull;

    @NotNull(message = "Error NonNull")
    private String testNonNull;

    @Pattern(regexp = "^[0-9]?[0-9]$", message = "Error Pattern")
    private String testPattern;

    @Size(min = 1, max = 10, message = "Error Size")
    private List<String> testSize;

    @Length(min = 1, max = 10, message = "Error Length")
    private String testLength;

    @NotBlank(message = "Error Blank")
    private String testBlank;

    @NotEmpty(message = "Error NotEmpty")
    private String testEmpty;

    @Range(min = 1, max = 10, message = "Error Range")
    private String testRange;

    @NotEmpty(message = "Error Empty")
    @Length(min = 1, max = 5, message = "Error Length")
    private String testAnnotations;

    /**
     * 构建正常参数
     */
    public void buildSuccess() {
        this.testTrue = true;
        this.testFalse = false;
        this.testStrMax = "10";
        this.testStrMin = "1";
        this.testMax = 10;
        this.testMin = 1.0;
        this.testDig = BigDecimal.valueOf(10.111);
        this.testPast = new Date(System.currentTimeMillis() - 100000);
        this.testFuture = new Date(System.currentTimeMillis() + 100000);
        this.testNull = null;
        this.testNonNull = "null";
        this.testPattern = "11";
        this.testSize = new ArrayList<String>() {{
            add("1");
        }};
        this.testLength = "111";
        this.testBlank = "11";
        this.testEmpty = "11";
        this.testRange = "2";
        this.testAnnotations = "22";
    }

    public Boolean getTestTrue() {
        return testTrue;
    }

    public void setTestTrue(Boolean testTrue) {
        this.testTrue = testTrue;
    }

    public Boolean getTestFalse() {
        return testFalse;
    }

    public void setTestFalse(Boolean testFalse) {
        this.testFalse = testFalse;
    }

    public String getTestStrMax() {
        return testStrMax;
    }

    public void setTestStrMax(String testStrMax) {
        this.testStrMax = testStrMax;
    }

    public String getTestStrMin() {
        return testStrMin;
    }

    public void setTestStrMin(String testStrMin) {
        this.testStrMin = testStrMin;
    }

    public Integer getTestMax() {
        return testMax;
    }

    public void setTestMax(Integer testMax) {
        this.testMax = testMax;
    }

    public Double getTestMin() {
        return testMin;
    }

    public void setTestMin(Double testMin) {
        this.testMin = testMin;
    }

    public BigDecimal getTestDig() {
        return testDig;
    }

    public void setTestDig(BigDecimal testDig) {
        this.testDig = testDig;
    }

    public Date getTestPast() {
        return testPast;
    }

    public void setTestPast(Date testPast) {
        this.testPast = testPast;
    }

    public Date getTestFuture() {
        return testFuture;
    }

    public void setTestFuture(Date testFuture) {
        this.testFuture = testFuture;
    }

    public String getTestNull() {
        return testNull;
    }

    public void setTestNull(String testNull) {
        this.testNull = testNull;
    }

    public String getTestNonNull() {
        return testNonNull;
    }

    public void setTestNonNull(String testNonNull) {
        this.testNonNull = testNonNull;
    }

    public String getTestPattern() {
        return testPattern;
    }

    public void setTestPattern(String testPattern) {
        this.testPattern = testPattern;
    }

    public List<String> getTestSize() {
        return testSize;
    }

    public void setTestSize(List<String> testSize) {
        this.testSize = testSize;
    }

    public String getTestLength() {
        return testLength;
    }

    public void setTestLength(String testLength) {
        this.testLength = testLength;
    }

    public String getTestBlank() {
        return testBlank;
    }

    public void setTestBlank(String testBlank) {
        this.testBlank = testBlank;
    }

    public String getTestEmpty() {
        return testEmpty;
    }

    public void setTestEmpty(String testEmpty) {
        this.testEmpty = testEmpty;
    }

    public String getTestRange() {
        return testRange;
    }

    public void setTestRange(String testRange) {
        this.testRange = testRange;
    }

    public String getTestAnnotations() {
        return testAnnotations;
    }

    public void setTestAnnotations(String testAnnotations) {
        this.testAnnotations = testAnnotations;
    }
}
