package com.yohan.gladiators;

import com.jme3.app.SimpleApplication;
import com.jme3.scene.shape.Box;
import com.jme3.scene.Geometry;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;

public class Main extends SimpleApplication {

    public static void main(String[] args) {
        new Main().start();
    }

    @Override
    public void simpleInitApp() {

        float floorWidth = 10f;
        float floorHeight = 1f;    
        float floorDepth = 10f;

        Box floor = new Box(floorWidth, floorHeight, floorDepth);

        Geometry floorGeometry = new Geometry("Floor", floor);
        Material floorMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        

        floorMaterial.setColor("Color", ColorRGBA.Gray);
        floorGeometry.setMaterial(floorMaterial);

        rootNode.attachChild(floorGeometry);


        


        
    }
}
