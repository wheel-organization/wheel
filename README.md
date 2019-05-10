想一起的留下github账号，把你们拉进组织里一起

## dubbo泛化调用
---

### 此util存在的意义和局限性
意义：
期望是和http调用的方式互补的去使用

优点：
1. 支持group、version、无token情况。联调时更方便配合其他系统。
2. dubbo协议调用，可在代码里泛化调用
3. 泛型支持

局限性：
1. 传入的参数列表顺序必须和方法上的参数顺序相同（问题不大）
2. 拿不到具体类型的泛型反序列化还是会失败（这种情况很少）
3. 需要自己多写一个helper（问题不大）
4. 必须项目引入了对应的api（问题不大）
5. dubbo源码有大量变动或结构性改变时，此util也要维护（Apache的dubbo可以兼容alibaba的dubbo，所以我用了alibaba的dubbo）

### 如何使用
注意：

引入的时候一定要去掉api里的dubbo

![](http://ww1.sinaimg.cn/large/ee36fa1bly1g2kigmk3v9j209e04pdg4.jpg)

url是dubbo-admin里的url
![](http://ww1.sinaimg.cn/large/ee36fa1bly1g2khc9tt2sj21g504n75e.jpg)

1. 配合swagger
![](http://ww1.sinaimg.cn/large/ee36fa1bly1g2kf99yvwsj20u704iq3s.jpg)
![](http://ww1.sinaimg.cn/large/ee36fa1bly1g2kfhjn68hj21mm0ycgpn.jpg)
2. 代码单测调用
![](http://ww1.sinaimg.cn/large/ee36fa1bly1g2kfcy6l6mj20nd047whd.jpg)

### 大致说明
大体思路就是反射+泛化调用。有点造轮子了，具体实现代码里注释写的还算详细，有问题和建议欢迎提到Issues上。

## hibernate-validator 更简洁的参数校验及一个util

### 简介

hibernate-validator是Hibernate项目中的一个数据校验框架，是Bean Validation 的参考实现，hibernate-validator除了提供了JSR 303规范中所有内置constraint 的实现，还有一些附加的constraint。<br>
使用hibernate-validator能够将数据校验从业务代码中脱离出来，增加代码可读性，同时也让数据校验变得更加方便、简单。

官网地址：http://hibernate.org/validator/

### 如何使用
> damascus中已经引入了需要的api，无需重复引入

``` 
        <dependency>
            <groupId>javax.validation</groupId>
            <artifactId>validation-api</artifactId>
            <version>2.0.1.Final</version>
        </dependency>
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-validator</artifactId>
            <version>6.0.16.Final</version>
        </dependency>
        <dependency>
            <groupId>org.glassfish</groupId>
            <artifactId>javax.el</artifactId>
            <version>3.0.1-b09</version>
            <scope>provided</scope>
        </dependency>
```
在要校验的POJO上加上以下注解即可（注解支持类型在注解源码的注释上有）


注解 | 用途
--- |---
Valid | 递归的对关联的对象进行校验
AssertFalse | 用于boolean字段，该字段的值只能为false
AssertTrue | 用于boolean字段，该字段只能为true 
DecimalMax(value) | 被注释的元素必须是一个数字，只能大于或等于该值
DecimalMin(value) | 被注释的元素必须是一个数字，只能小于或等于该值
Digits(integer,fraction) | 检查是否是一种数字的(整数最大位,小数最大位)位数
Future | 检查该字段的日期是否是属于将来的日期
Past | 检查该字段的日期是在过去
Max(value) | 该字段的值只能小于或等于该值
Min(value) | 该字段的值只能大于或等于该值
NotNull | 不能为null
Null | 必须为 null
Pattern(value) | 被注释的元素必须符合指定的正则表达式
Size(max, min) | 检查该字段的size是否在min和max之间，可以是字符串、数组、集合、Map等
CreditCardNumber | 被注释的字符串必须通过Luhn校验算法，银行卡，信用卡等号码一般都用Luhn计算合法性
Email | 被注释的元素必须是电子邮箱地址
Length(min=, max=) | 被注释的字符串的大小必须在指定的范围内
NotBlank | 只能用于字符串不为null，并且字符串trim()以后length要大于0
NotEmpty | 集合对象的元素不为0，即集合不为空，也可以用于字符串不为null
Range(min=, max=) | 被注释的元素必须在合适的范围内
SafeHtml | classpath中要有jsoup包
ScriptAssert | 要有Java Scripting API 即JSR 223("Scripting for the JavaTMPlatform")的实现
URL(protocol=,host=,port=,regexp=,flags=) | 被注释的字符串必须是一个有效的url
<br>

更多功能，如：自定义校验规则、分组校验、关联参数联合校验请查看官网或百度

### Dubbo中使用Hibernate Validator校验入参
无需util，Dubbo接口配置上的validation为true即可

在客户端验证参数
```
<dubbo:reference id="xxxService" interface="xxx.ValidationService" validation="true" />
```

在服务器端验证参数
```
<dubbo:service interface="xxx.ValidationService" ref="xxxService" validation="true" />
```

### 在代码里校验入参
```
//obj为包含Hibernate Validator注解的POJO
ValidResult validResult = ValidationUtil.validate(obj);
```
ValidResult大致结构：
```java
    /**
     * 是否通过校验
     */
    private boolean success = false;

    /**
     * 未通过的参数名
     */
    private String paramName = "";

    /**
     * 未通过参数的报错信息(注解上的message)
     */
    private String paramError = "";

    /**
     * 校验是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        return this.success;
    }
    
    /**
     * 获取未通过的参数名
     *
     * @return
     */
    public String getParamName() {
        return paramName;
    }
    
    /**
     * 未通过参数的报错信息
     *
     * @return
     */
    public String getParamError() {
        return paramError;
    }

    /**
     * 获取完整报错信息
     *
     * @return
     */
    public String getMsg() {
        return String.format("%s:%s", this.paramName, this.paramError);
    }
```

### 样例

```java

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
}
```
**单测：ValidationUtilTest**
