package com.yohan.gladiators;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;

public class HealthBar {
    private final Geometry background;
    private final Geometry fill;

    public HealthBar(AssetManager assetManager, Node guiNode, float x, float y, float width, float height) {

        background = new Geometry("HealthBarBackground", new Quad(width, height));
        Material backgroundMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        backgroundMaterial.setColor("Color", new ColorRGBA(0.2f, 0.2f, 0.2f, 1f)); 
        background.setMaterial(backgroundMaterial);
        background.setLocalTranslation(x, y, 0);
        guiNode.attachChild(background);

        fill = new Geometry("HealthBarFill", new Quad(width, height));
        Material fillMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        fillMaterial.setColor("Color",new ColorRGBA(1f, 0f, 0f, 1f)); 
        fill.setMaterial(fillMaterial);
        fill.setLocalTranslation(x, y, 0.01f);
        guiNode.attachChild(fill);
    }

    public void setFraction(float fraction) {
        
        float clamped = FastMath.clamp(fraction, 0f, 1f);
        fill.setLocalScale(clamped, 1, 1);
    }
}