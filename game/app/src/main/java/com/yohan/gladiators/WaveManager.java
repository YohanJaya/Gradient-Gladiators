package com.yohan.gladiators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.jme3.math.Vector3f;

public class WaveManager {

    private final EnemyFactory enemyFactory;
    private final Player knight;
    private final GameConfig config;
    private final List<Brute> aliveBrutes = new ArrayList<>();
    private final Random random = new Random();

    private final int totalWaves;
    

    private int currentWave;
    private float waveTime;      // seconds since the current wave started
    private boolean won;
    private boolean lost;

    private final int gameLevel;

    public WaveManager(EnemyFactory enemyFactory, Player knight, GameConfig config, int gameLevel) {
        this.enemyFactory = enemyFactory;
        this.knight = knight;
        this.config = config;
        this.gameLevel = gameLevel;
        this.totalWaves = config.getInt("bruteAllWave.count");
        this.currentWave = 0;
    }

    public void startWave(int waveNumber) {
        currentWave = waveNumber;
        waveTime = 0f;
        int count = config.getInt("bruteWave" + waveNumber + ".count");

        float sepMinDist = config.getFloat("brute.separationMinDistance");
        float sepStrength = config.getFloat("brute.separationStrength");

        for (int i = 0; i < count; i++) {
            // Random spawn position between -5 and 5 on x and z
            float x = random.nextFloat() * 10 - 5;
            float z = random.nextFloat() * 10 - 5;
            Vector3f spawnPosition = new Vector3f(x,
                    config.getFloat("arena.floorHeight") + config.getFloat("brute.height"), z);

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
        if (won || lost) {
            return;
        }

        waveTime += tpf;

        // 1. update the Brutes
        for (Brute brute : aliveBrutes) {
            brute.update(tpf);
        }

        // 2. remove the dead Brutes
        aliveBrutes.removeIf(brute -> {
            if (brute.getHealth().getCurrent() <= 0) {
                brute.getNode().removeFromParent();
                return true;
            }
            return false;
        });

        // 3. lose: the Knight is dead
        if (knight.getHealth().getCurrent() <= 0) {
            lost = true;
            System.out.println("You lose! Reached wave " + currentWave);
            return;
        }

        // 4. next wave, or win
        if (shouldStartNextWave()) {
            if (currentWave < totalWaves) {
                startWave(currentWave + 1);
            } else if (aliveBrutes.isEmpty()) {
                won = true;
                System.out.println("You win!");
            }
        }
    }

    // A wave ends when all Brutes are dead, or (in timed mode) when its time is up.
    private boolean shouldStartNextWave() {
        if (aliveBrutes.isEmpty()) {
            return true;
        }
        if (gameLevel == 2) {                      // Level 2: the time limit also ends the wave
            float limit = config.getFloat("bruteWaveBase.duration")
                        * config.getInt("bruteWave" + currentWave + ".count");
            return waveTime >= limit;
        }
        return false;                              // Level 1: only killing all Brutes ends the wave
    }

    public int getCurrentWave() {
        return currentWave;
    }
}