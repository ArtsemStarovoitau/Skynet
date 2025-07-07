package com.app.service;

import com.app.model.Part;
import com.app.model.PartType;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

public class Faction {

    private final String name;
    private final Map<PartType, Integer> storedParts = new ConcurrentHashMap<>();
    private int robotCount = 0;

    public Faction(String name) {
        this.name = name;
    }

    public void collectFromFactory(Queue<Part> warehouse) {
        System.out.println("Collecting parts from factory for faction: " + name);
        for (int i = 0; i < 5; i++) {
            Part part = warehouse.poll();
            if (part == null) {
                System.out.println("waraehouse is empty, cannot collect parts." + name);
                break;
            }
            storedParts.merge(part.type(), 1, Integer::sum);
            System.out.println("Collected part: " + part.type() + " for faction: " + name);
        }

    }

    private void makeRobots() {
        while (true) {
            int heads = storedParts.getOrDefault(PartType.HEAD, 0);
            int torsos = storedParts.getOrDefault(PartType.TORSO, 0);
            int hands = storedParts.getOrDefault(PartType.HAND, 0);
            int feet = storedParts.getOrDefault(PartType.FEET, 0);

            if (heads >= 1 && torsos >= 1 && hands >= 2 && feet >= 2) {
                storedParts.put(PartType.HEAD, heads - 1);
                storedParts.put(PartType.TORSO, torsos - 1);
                storedParts.put(PartType.HAND, hands - 2);
                storedParts.put(PartType.FEET, feet - 2);

                robotCount++;
                System.out.printf("Faction %s has built a robot! Total robots: %d%n", name, robotCount);
            }
        }
    }

    public void runNightProd(Queue<Part> warehouse) {
        collectFromFactory(warehouse);
        makeRobots();
    }

    public String getName() {
        return name;
    }

    public int getRobotCount() {
        return robotCount;
    }
}
