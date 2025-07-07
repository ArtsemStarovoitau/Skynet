package com.app;

import com.app.service.Faction;
import com.app.service.Factory;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final int SIMULATION_DAYS = 100;

    public static void main(String[] args) {
        System.out.println("Starting Skynet Simulation...");

        Factory factory = new Factory();
        Faction world = new Faction("World");
        Faction wednesday = new Faction("Wednesday");
        List<Faction> factions = List.of(world, wednesday);

        for (int i = 0; i < SIMULATION_DAYS; i++) {
            System.out.println("Day " + (i + 1));

            factory.runDayProd();

            List<Thread> factionThreads = new ArrayList<>();

            for (Faction faction : factions) {
                Thread factionThread = Thread.ofVirtual().start(() -> {
                    faction.runNightProd(factory.getWarehouse());
                });
                factionThreads.add(factionThread);
            }

            // Обрабатываем возможное прерывание для thread.join()
            for (Thread thread : factionThreads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    System.err.println("Основной поток был прерван во время ожидания дочернего потока.");
                    // Восстанавливаем статус прерывания, это хорошая практика
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }

            // Обрабатываем возможное прерывание для Thread.sleep()
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                System.err.println("Сон основного потока был прерван.");
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }

        System.out.println("Simulation completed.");
        System.out.println("World faction robots: " + world.getRobotCount());
        System.out.println("Wednesday faction robots: " + wednesday.getRobotCount());

        if (world.getRobotCount() > wednesday.getRobotCount()) {
            System.out.println("World faction wins!");
        } else if (wednesday.getRobotCount() > world.getRobotCount()) {
            System.out.println("Wednesday faction wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }
}