package com.learning.wheel.paramcheck;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

/**
 * @author hangwei
 * @date 2019/5/14 下午 2:17
 */
public class RerenceQueueTest {
    public static void main(String[] args) {
        Object counter = new Object();
        ReferenceQueue referenceQueue = new ReferenceQueue();
        PhantomReference<Object> p = new PhantomReference<Object>(counter, referenceQueue);
        counter = null;
        System.gc();
        try {
            Reference<Object> ref = referenceQueue.remove(1000L);
            if (ref != null) {
                System.out.println(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
