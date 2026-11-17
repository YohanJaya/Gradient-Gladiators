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

        //floor box

        float floorWidth = 10f;
        float floorHeight = 1f;    
        float floorDepth = 10f;

        Box floor = new Box(floorWidth, floorHeight, floorDepth);

        Geometry floorGeometry = new Geometry("Floor", floor);
        Material floorMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        

        floorMaterial.setColor("Color", ColorRGBA.Gray);
        floorGeometry.setMaterial(floorMaterial);

        rootNode.attachChild(floorGeometry);

        //knight box

        float knightWidth = 1f;
        float knightHeight = 2f;
        float knightDepth = 1f;

        Box knight = new  Box(knightWidth, knightHeight, knightDepth);

        Geometry knightGeo = new Geometry("Knight", knight);
        Material knightMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");

        knightMat.setColor("Color", ColorRGBA.Blue);
        knightGeo.setMaterial(knightMat);
        knightGeo.setLocalTranslation(0, floorHeight + knightHeight, 0);

        rootNode.attachChild(knightGeo);



        


        
    }
}
