package com.yohan.gladiators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.jme3.math.Vector3f;

public class WaveManager {

    // Same height the Brute had in Main (floorHeight + knightHeight)
    
    private final EnemyFactory enemyFactory;
    private final Player knight;
    private final GameConfig config;
    private final List<Brute> aliveBrutes = new ArrayList<>();
    private final Random random = new Random();

    private int currentWave;

    public WaveManager(EnemyFactory enemyFactory, Player knight, GameConfig config) {
        this.enemyFactory = enemyFactory;
        this.knight = knight;
        this.config = config;
        this.currentWave = 0;
    }

    public void startWave(int waveNumber) {
        currentWave = waveNumber;
        int count = config.getInt("bruteWave" + waveNumber + ".count");

        float sepMinDist = config.getFloat("brute.separationMinDistance");
        float sepStrength = config.getFloat("brute.separationStrength");

        for (int i = 0; i < count; i++) {
            // Random spawn position between -5 and 5 on x and z
            float x = random.nextFloat() * 10 - 5;
            float z = random.nextFloat() * 10 - 5;
            Vector3f spawnPosition = new Vector3f(x, config.getFloat("arena.floorHeight") + config.getFloat("brute.height"), z);

            Brute brute = enemyFactory.createBrute(spawnPosition, knight);

            // Both sides can hit each other
            brute.addTarget(knight);
            knight.addTarget(brute);

            aliveBrutes.add(brute);
        }

        // Give every brain a shared view of all brutes for separation steering
        for (Brute brute : aliveBrutes) {
            Brain brain = brute.getBrain();
            if (brain instanceof BruteInputBrain b) {
                b.setAllBrutes(aliveBrutes, sepMinDist, sepStrength);
            }
        }
    }

    public void update(float tpf) {
        for (Brute brute : aliveBrutes) {
            brute.update(tpf);
        }
    }

    public int getCurrentWave() {
        return currentWave;
    }
}