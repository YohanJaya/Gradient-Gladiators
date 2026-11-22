package com.yohan.gladiators;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
// import com.jme3.material.TechniqueDef.ShadowMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.light.DirectionalLight;
import com.jme3.light.AmbientLight;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
public class Main extends SimpleApplication {

    private static final String UNSHADED = "Common/MatDefs/Misc/Unshaded.j3md";

    public static void main(String[] args) {
        new Main().start();
    }

    private static final String LIGHTING = "Common/MatDefs/Light/Lighting.j3md";
    private Player knight;
    

    /**
     * Creates a coloured box, attaches it to the scene, and returns it.
     * @param name   The name of the geometry.
     * @param width  Half-width of the box.
     * @param height Half-height of the box.
     * @param depth  Half-depth of the box.
     * @param mat    Path to the material definition (.j3md).
     * @param color  The colour of the box.
     * @return The created Geometry, so the caller can move it.
     */

    // Creates a coloured box, attaches it to the scene, and returns it (Helper Method)
    private Geometry createBox(String name, float width, float height, float depth, String mat, ColorRGBA color) {

        Box box = new Box(width, height, depth);
        Geometry geometry = new Geometry(name, box);
        Material material = new Material(assetManager, mat);

        material.setColor("Diffuse", color);
        material.setColor("Ambient", color);
        material.setBoolean("UseMaterialColors", true);
        geometry.setMaterial(material);

        rootNode.attachChild(geometry);
        return geometry;
    }

    // Called once at startup: builds the scene, spawns the knight, sets up lights and camera
    @Override
    public void simpleInitApp() {

        // Floor
        float floorHeight = 1f;
        Geometry floorGeo = createBox("Floor", 10f, floorHeight, 10f, LIGHTING, ColorRGBA.Gray);
        floorGeo.setShadowMode(ShadowMode.Receive);

        // Knight body and facing indicator (red nose)
        float knightHeight = 1f;
        Geometry knightGeo = createBox("Knight", 0.5f, knightHeight, 0.5f, LIGHTING, ColorRGBA.Blue);
        knightGeo.setShadowMode(ShadowMode.Cast);

        Health health = new Health(100f);

        knight = new Player("Knight", 5f, health);
        knight.registerInput(inputManager);

        knight.getNode().attachChild(knightGeo);
        knight.getNode().setLocalTranslation(0, floorHeight + knightHeight, 0);

        Geometry nose = createBox("Nose", 0.15f, 0.15f, 0.15f, LIGHTING, ColorRGBA.Red);
        nose.setLocalTranslation(0, 0.5f, -0.6f);
        knight.getNode().attachChild(nose);

        rootNode.attachChild(knight.getNode());

        inputManager.setCursorVisible(false);

        // Directional sun light with shadows
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-1, -2, -3).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);

        // Low-level ambient fill so shadowed areas aren't pitch black
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White.mult(0.3f));
        rootNode.addLight(ambient);

        DirectionalLightShadowRenderer shadowRenderer = new DirectionalLightShadowRenderer(assetManager, 1024, 3);
        shadowRenderer.setLight(sun);
        viewPort.addProcessor(shadowRenderer);


        flyCam.setEnabled(false);

        

    }

    // Called every frame: delegates to the knight's update logic
    @Override
    public void simpleUpdate(float tpf) {
        knight.update(tpf);

        Vector3f knightPos = knight.getNode().getLocalTranslation();
        Vector3f offset = new Vector3f(0, 4, 8);

        offset = knight.getNode().getLocalRotation().mult(offset);


        cam.setLocation(knightPos.add(offset));
        cam.lookAt(knightPos.add(0, 1, 0), Vector3f.UNIT_Y);

    }
}
