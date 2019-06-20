package com.learning.wheel.io.nio;

import java.util.Scanner;

/**
 * @program wheel
 * @author: hangwei
 * @create: 2019/06/20 14:11
 */
public class Test {
    //测试主方法
    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception{
        //运行服务器
        Server.start();
        //避免客户端先于服务器启动前执行代码
        Thread.sleep(100);
        //运行客户端
        Client.start();
        while(Client.sendMsg(new Scanner(System.in).nextLine()));
    }
}

