package com.app.service;

import com.app.model.Part;
import com.app.model.PartType;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Factory {

    private final Queue<Part> warehouse = new ConcurrentLinkedQueue<>();
    private final Random random = new Random();
    private final PartType[] partTypes = PartType.values();

    public void runDayProd(){
        int partsToProduce = random.nextInt(10) + 1;
        System.out.println("Producing " + partsToProduce + " parts today.");
        for (int i = 0; i < partsToProduce; i++) {
            PartType partType = partTypes[random.nextInt(partTypes.length)];
            System.out.println("Producing part: " + partType);
            warehouse.add(new Part(partType));
        }
    }

    public Queue<Part> getWarehouse() {
        return warehouse;
    }

}
