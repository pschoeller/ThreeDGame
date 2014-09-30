package engineTester;

import models.RawModel;
import models.TexturedModel;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;
import entities.Camera;
import entities.Entity;
import entities.Light;

public class MainGameLoop {

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);
		
		
		
		RawModel model = OBJLoader.loadObjModel("dragon", loader);
<<<<<<< HEAD
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("texture2048")));
		Entity entity = new Entity(staticModel, new Vector3f(0,0,-25), 0, 0, 0, 1);
		Camera camera = new Camera();
		Light light = new Light(new Vector3f(0, 1, -20), new Vector3f(1, 1, 1));
=======
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("white1024")));
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(1);
		Entity entity = new Entity(staticModel, new Vector3f(0,0,-25), 0, 0, 0, 1);
		Camera camera = new Camera();
		Light light = new Light(new Vector3f(0, 0, -20), new Vector3f(1, 1, 1));
>>>>>>> status
		
		while(!Display.isCloseRequested()){
			entity.increaseRotation(0, 0.5f, 0);
			camera.move();
			renderer.prepare();
			shader.start();
<<<<<<< HEAD
			shader.loadLight(light);
=======
			shader.loadLightPosition(light);
>>>>>>> status
			shader.loadViewMatrix(camera);
			renderer.render(entity, shader);
			shader.stop();
			DisplayManager.updateDisplay();
		}
		
		shader.cleanUP();
		loader.cleanUP();
		DisplayManager.closeDisplay();
	}

}
