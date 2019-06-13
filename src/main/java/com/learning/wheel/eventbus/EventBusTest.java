package com.learning.wheel.eventbus;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program wheel
 * @author: hangwei
 * @create: 2019/06/13 17:59
 */
final public class EventBusTest {
    private static class EventA {
        public String toString() {
            return "A 类型事件";
        }
    }
    private static class EventB extends EventA {
        public String toString() {
            return "B 类型事件";
        }
    }
    private static class EventC {
        public String toString() {
            return "C 类型事件";
        }
    }
    private static class EventD {
        public String toString() {
            return "D 类型事件";
        }
    }
    private static class EventX {
        public String toString() {
            return "X 类型事件";
        }
    }
    private static class EventListener {
        @Subscribe
        public void onEvent(EventA e) {
            System.out.println("我订阅的是 A事件,接收到:" + e);
        }
        @Subscribe
        public void onEvent(EventB e) {
            System.out.println("我订阅的是B事件,接收到:" + e);
        }
        @Subscribe
        @AllowConcurrentEvents
        public void onEvent(EventC e) throws InterruptedException {
            String name = Thread.currentThread().getName();
            System.out.format("%s sleep 一会儿%n", name);
            Thread.sleep(1000);
            System.out.println(name + "订阅的是C事件,接收到:" + e);
            System.out.format("%s sleep 完成%n", name);
        }
        @Subscribe
        public void onEvent(EventD e) throws InterruptedException {
            String name = Thread.currentThread().getName();
            System.out.format("%s sleep 一会儿%n", name);
            Thread.sleep(1000);
            System.out.println(name + "订阅的是D事件,接收到:" + e);
            System.out.format("%s sleep 完成%n", name);
        }
        @Subscribe
        public void onEvent(DeadEvent de) {
            System.out.println("发布了错误的事件:" + de.getEvent());
        }
    }

    private static class EventListenerB{
        @Subscribe
        public void onEvent(EventA e){
            System.out.println("B订阅的是A事件，接收到："+e);
        }
        @Subscribe
        public void onEvent(EventC e) {
            System.out.println("B订阅的是C事件,接收到:" + e);
        }
    }

    private static class EventListenerC{
        @Subscribe
        public void onEvent(EventA e){
            System.out.println("C订阅的是A事件，接收到："+e);
        }
        @Subscribe
        public void onEvent(EventC e) {
            System.out.println("C订阅的是C事件,接收到:" + e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        EventBus eb = new EventBus();
        eb.register(new EventListener());
        System.out.println("----------发送事件A---------");
        eb.post(new EventA());
        System.out.println("----------发送事件B---------");
        eb.post(new EventB());
        System.out.println("----------发送事件X---------");
        eb.post(new EventX());
        System.out.println("----------发送事件C---------");
        ExecutorService threadPool = Executors.newCachedThreadPool();
        eb = new AsyncEventBus(threadPool);
        eb.register(new EventListener());
        for (int i = 0; i < 20; i++) {
            eb.post(new EventC());
        }
        System.out.println("----------发送事件D---------");
        for (int i = 0; i < 20; i++) {
            eb.post(new EventD());
        }
        Thread.sleep(2000);
        threadPool.shutdown();
    }
}
