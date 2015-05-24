package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.RawModel;
import models.TexturedModel;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import entities.Camera;
import entities.Entity;
import entities.Light;

public class MainGameLoop {

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		Loader loader = new Loader();
//		//RawModel model = OBJLoader.loadObjModel("dragon", loader);
//		//TexturedModel dragonModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("white1024")));
		Camera camera = new Camera(new Vector3f(400, 5, 500));
		Light light = new Light(new Vector3f(3000, 5000, 3000), new Vector3f(1, 1, 1));
//		
		Terrain terrain = new Terrain(0, 0, loader, new ModelTexture(loader.loadTexture("grass")));
//		Terrain terrain2 = new Terrain(0, 0, loader, new ModelTexture(loader.loadTexture("09")));
//		
		
		TexturedModel tree = new TexturedModel(OBJLoader.loadObjModel("tree", loader), new ModelTexture(loader.loadTexture("tree")));
		//TexturedModel tree1 = new TexturedModel(OBJLoader.loadObjModel("Tree1", loader), new ModelTexture(loader.loadTexture("tree")));
		
		TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader), new ModelTexture(loader.loadTexture("grassTexture")));
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLighting(true);
		
		TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader), new ModelTexture(loader.loadTexture("fern")));
		fern.getTexture().setHasTransparency(true);
		fern.getTexture().setUseFakeLighting(true);
		
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		
		for(int i=0; i<500; i++){
			entities.add(new Entity(tree, new Vector3f(random.nextFloat()*800, 0, random.nextFloat()*799), 0, 0, 0, 1));
			//entities.add(new Entity(tree1, new Vector3f(random.nextFloat()*800, 0, random.nextFloat()*799), 0, 0, 0, 1));
			entities.add(new Entity(grass, new Vector3f(random.nextFloat()*800, 0, random.nextFloat()*799), 0, 0, 0, 1));
			entities.add(new Entity(fern, new Vector3f(random.nextFloat()*800, 0, random.nextFloat()*799), 0, 0, 0, 0.6f));
		}
		
		MasterRenderer renderer = new MasterRenderer();
		
		
		while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			camera.move();
			
			renderer.processTerrain(terrain);
			//renderer.processTerrain(terrain2);
			for(Entity entity:entities){
				renderer.processEntity(entity);
			}
			
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}
		
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}
