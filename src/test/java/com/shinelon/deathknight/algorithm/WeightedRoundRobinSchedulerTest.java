package com.shinelon.deathknight.algorithm;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @ClassName WeightedRoundRobinSchedulerTest
 * @Author Shinelon
 * @Date 10:15 2023/7/6
 * @Version 1.0
 **/
public class WeightedRoundRobinSchedulerTest {

    @Test
    public void weightTest() {
        Server server1 = new Server();
        server1.setName("server1");
        server1.setWeight(3);
        Server server2 = new Server();
        server2.setName("server2");
        server2.setWeight(1);
        Server server3 = new Server();
        server3.setName("server3");
        server3.setWeight(2);

        List<Server> serverList = new ArrayList<>(10);
        serverList.add(server1);
        serverList.add(server2);
        serverList.add(server3);
        WeightedRoundRobinScheduler scheduler = new WeightedRoundRobinScheduler(serverList);
        for (int i = 0; i < 5; i++) {
            Server server = scheduler.getServer();
            System.out.println(server);
        }

        System.out.println("=========");
        int currentCount = 5;
        CyclicBarrier cb = new CyclicBarrier(currentCount);
        for (int i = 0; i < currentCount; i++) {
            Thread t = new Thread(() -> {
                try {
                    cb.await();
                    Server server = scheduler.getServer();
                    System.out.println(Thread.currentThread().getName() + ":" + server.toString());
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            });
            t.start();
        }

    }
}
