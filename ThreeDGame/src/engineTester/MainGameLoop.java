package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.RawModel;
import models.TexturedModel;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import textures.ModelTexture;
import entities.Camera;
import entities.Entity;
import entities.Light;

public class MainGameLoop {

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		RawModel model = OBJLoader.loadObjModel("dragon", loader);
		TexturedModel dragonModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("white1024")));
		//ModelTexture texture = staticModel.getTexture();
		//texture.setShineDamper(10);
		//texture.setReflectivity(1);
		//Entity entity = new Entity(staticModel, new Vector3f(0,0,-25), 0, 0, 0, 1);
		Camera camera = new Camera();
		Light light = new Light(new Vector3f(3000, 2000, 3000), new Vector3f(1, 1, 1));
		
		List<Entity> allDragons = new ArrayList<Entity>();
		Random random = new Random();
		
		for(int i=0; i<100; i++){
			float x = random.nextFloat() * 100 - 50;
			float y = random.nextFloat() * 100 - 50;
			float z = random.nextFloat() * -300;
			allDragons.add(new Entity(dragonModel, new Vector3f(x, y, z), random.nextFloat() * 180f, random.nextFloat() * 180f, 0f, 1f));
		}
		
		
		MasterRenderer renderer = new MasterRenderer();
		
		while(!Display.isCloseRequested()){
			camera.move();
			
			for(Entity dragon : allDragons){
				renderer.processEntity(dragon);
			}
			
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}
		
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}
