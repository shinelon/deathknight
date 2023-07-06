package com.shinelon.deathknight.algorithm;

import java.util.List;

/**
 * @ClassName WeightedRoundRobinScheduler
 * @Author Shinelon
 * @Date 10:08 2023/7/6
 * @Version 1.0
 **/
public class WeightedRoundRobinScheduler {
    private List<Server> serverList;
    private int currentIndex = -1;
    private int currentWeight = 0;
    private int maxWeight;
    private int gcdWeight;

    public WeightedRoundRobinScheduler(List<Server> serverList) {
        this.serverList = serverList;
        maxWeight = getMaxWeight(serverList);
        gcdWeight = getGcdWeight(serverList);
    }

    public Server getServer() {
        while (true) {
            currentIndex = (currentIndex + 1) % serverList.size();
            if (currentIndex == 0) {
                currentWeight = currentWeight - gcdWeight;
                if (currentWeight <= 0) {
                    currentWeight = maxWeight;
                    if (currentWeight == 0) {
                        return null;
                    }
                }
            }
            if (serverList.get(currentIndex).getWeight() >= currentWeight) {
                return serverList.get(currentIndex);
            }
        }
    }

    private int getMaxWeight(List<Server> serverList) {
        int maxWeight = 0;
        for (Server server : serverList) {
            int weight = server.getWeight();
            if (weight > maxWeight) {
                maxWeight = weight;
            }
        }
        return maxWeight;
    }

    private int getGcdWeight(List<Server> serverList) {
        int gcd = serverList.get(0).getWeight();
        for (int i = 1; i < serverList.size(); i++) {
            gcd = gcd(gcd, serverList.get(i).getWeight());
        }
        return gcd;
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}

