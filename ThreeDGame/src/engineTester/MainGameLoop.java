package engineTester;



import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import textures.ModelTexture;
import entities.Camera;
import entities.Light;

public class MainGameLoop {

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		//RawModel model = OBJLoader.loadObjModel("dragon", loader);
		//TexturedModel dragonModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("white1024")));
		Camera camera = new Camera(new Vector3f(100, 50, 350));
		Light light = new Light(new Vector3f(3000, 2000, 3000), new Vector3f(1, 1, 1));
		
		Terrain terrain = new Terrain(0, 0, loader, new ModelTexture(loader.loadTexture("09")));
		Terrain terrain2 = new Terrain(1, 0, loader, new ModelTexture(loader.loadTexture("09")));
		
		MasterRenderer renderer = new MasterRenderer();
		
		while(!Display.isCloseRequested()){
			camera.move();
			
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}
		
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}
