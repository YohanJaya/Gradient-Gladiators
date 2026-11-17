package com.yohan.gladiators;

import com.jme3.app.SimpleApplication;
import com.jme3.scene.shape.Box;
import com.jme3.scene.Geometry;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

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

        float knightWidth = 0.5f;
        float knightHeight = 1f;
        float knightDepth = 0.5f;

        Box knight = new  Box(knightWidth, knightHeight, knightDepth);

        Geometry knightGeo = new Geometry("Knight", knight);
        Material knightMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");

        knightMat.setColor("Color", ColorRGBA.Blue);
        knightGeo.setMaterial(knightMat);
        knightGeo.setLocalTranslation(0, floorHeight + knightHeight, 0);

        rootNode.attachChild(knightGeo);

        
        // Third-person camera
        flyCam.setEnabled(false);                                  // stop WASD/mouse from moving the camera

        Vector3f knightPos = knightGeo.getLocalTranslation();      // where the Knight is
        Vector3f offset = new Vector3f(0, 4, 8);                   // 4 up, 8 back
        Vector3f camPos = knightPos.add(offset);                   // camera = Knight + offset

        cam.setLocation(camPos);                                   // put the camera there
        cam.lookAt(knightPos, Vector3f.UNIT_Y);                    // look AT the Knight, keep Y as "up"

                


        
    }
}
