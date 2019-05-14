package com.learning.wheel.beans;

/**
 * @author hangwei
 * @date 2019/5/14 下午 3:07
 */
public class BenchmarkTest {
    private int count;

    public BenchmarkTest(int count) {
        this.count = count;
        System.out.println("性能测试" + this.count + "==================");
    }

    public void benchmark(IMethodCallBack m, FromBean frombean) {
        try {
            long begin = new java.util.Date().getTime();
            ToBean tobean = null;
            System.out.println(m.getMethodName() + "开始进行测试");
            for (int i = 0; i < count; i++) {

                tobean = m.callMethod(frombean);

            }
            long end = new java.util.Date().getTime();
            System.out.println(m.getMethodName() + "耗时" + (end - begin));
            System.out.println(tobean.getAddress());
            System.out.println(tobean.getAge());
            System.out.println(tobean.getIdno());
            System.out.println(tobean.getMoney());
            System.out.println(tobean.getName());
            System.out.println("                                      ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
