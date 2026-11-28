package com.yohan.gladiators;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

public class EnemyFactory {

    private final GameConfig config;
    private final EventBus eventBus;
    private final AssetManager assetManager;
    private final Node guiNode;
    private final Node rootNode;
    private final int screenHeight;

    public EnemyFactory(GameConfig config, EventBus eventBus, AssetManager assetManager, Node guiNode, Node rootNode, int screenHeight) {

        this.config = config;
        this.eventBus = eventBus;
        this.assetManager = assetManager;
        this.guiNode = guiNode;
        this.rootNode = rootNode;
        this.screenHeight = screenHeight;
    }

    public Brute createBrute(Vector3f position, Character target) {

        Health bruteHealth = new Health(config.getFloat("brute.maxHealth"));
        float speed = config.getFloat("brute.speed");
        Brain bruteBrain = new BruteInputBrain(target);

        Brute brute = new Brute("Brute", speed, bruteHealth);
        brute.setBrain(bruteBrain);
        brute.setEventBus(eventBus);

        HealthBar bruteBar = new HealthBar(assetManager, guiNode, 10, screenHeight - 60, 200, 20, brute);
        eventBus.subscribe(bruteBar);

        Geometry bruteGeo = createBox("Brute", 0.5f, config.getFloat("brute.height"), 0.5f, ColorRGBA.Red);
        bruteGeo.setShadowMode(ShadowMode.Cast);
        brute.getNode().attachChild(bruteGeo);

        brute.getNode().setLocalTranslation(position);
        rootNode.attachChild(brute.getNode());

        return brute;
    }

    private Geometry createBox(String name, float halfX, float halfY, float halfZ, ColorRGBA color) {
        Box box = new Box(halfX, halfY, halfZ);
        Geometry geometry = new Geometry(name, box);
        Material material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Diffuse", color);
        material.setColor("Ambient", color);
        geometry.setMaterial(material);
        return geometry;
    }
}