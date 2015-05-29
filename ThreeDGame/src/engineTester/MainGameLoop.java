package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toolbox.MousePicker;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import guis.GuiRenderer;
import guis.GuiTexture;

public class MainGameLoop {

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		Loader loader = new Loader();

		
		//***********  Create Terrains  *************//
		
		List<Terrain> terrains = new ArrayList<Terrain>();
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		
		Terrain terrain = new Terrain(0, 0, loader, texturePack, blendMap, "heightmap-01");
		
		terrains.add(terrain);
		//*******************************************//
		
		
		//***************** Create Entities ***************//
		
		ModelData data = OBJFileLoader.loadOBJ("tree");
		RawModel treeModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
		TexturedModel tree = new TexturedModel(treeModel, new ModelTexture(loader.loadTexture("tree")));
		
		data = OBJFileLoader.loadOBJ("lamp");
		RawModel lampModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
		TexturedModel lamp1 = new TexturedModel(lampModel, new ModelTexture(loader.loadTexture("lamp")));
		
		data = OBJFileLoader.loadOBJ("lowPolyTree");
		RawModel lowPolyTreeModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
		TexturedModel lowPolyTree = new TexturedModel(lowPolyTreeModel, new ModelTexture(loader.loadTexture("tree")));
		
		data = OBJFileLoader.loadOBJ("grassModel");
		RawModel grassModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
		TexturedModel grass = new TexturedModel(grassModel, new ModelTexture(loader.loadTexture("grassTexture")));
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLighting(true);
		
		data = OBJFileLoader.loadOBJ("fern");
		ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fern"));
		fernTextureAtlas.setNumberOfRows(2);
		RawModel fernModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
		TexturedModel fern = new TexturedModel(fernModel, fernTextureAtlas);
		fern.getTexture().setHasTransparency(true);
		fern.getTexture().setUseFakeLighting(true);
		
		data = OBJFileLoader.loadOBJ("grassModel");
		RawModel flowerModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
		TexturedModel flower = new TexturedModel(flowerModel, new ModelTexture(loader.loadTexture("flower")));
		flower.getTexture().setHasTransparency(true);
		flower.getTexture().setUseFakeLighting(true);
		
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		/*
		for(int i=0; i<400; i++){
			float x=0, y=0, z=0;
			
			x = random.nextFloat()*800;
			z = random.nextFloat()*799;
			y = terrain.getHeightOfTerrain(x, z);
			entities.add(new Entity(tree, new Vector3f(x, y, z), 0, 0, 0, 1));
			
			if(i % 2 == 0){
				x = random.nextFloat()*800;
				z = random.nextFloat()*799;
				y = terrain.getHeightOfTerrain(x, z);
				entities.add(new Entity(lowPolyTree, new Vector3f(x, y, z), 0, 0, 0, 1));
			}
			
			x = random.nextFloat()*800;
			z = random.nextFloat()*799;
			y = terrain.getHeightOfTerrain(x, z);
			entities.add(new Entity(grass, new Vector3f(x, y, z), 0, 0, 0, 1));
			
			x = random.nextFloat()*800;
			z = random.nextFloat()*799;
			y = terrain.getHeightOfTerrain(x, z);
			entities.add(new Entity(flower, new Vector3f(x, y, z), 0, 0, 0, 0.6f));
			
			x = random.nextFloat()*800;
			z = random.nextFloat()*799;
			y = terrain.getHeightOfTerrain(x, z);
			entities.add(new Entity(fern, random.nextInt(4), new Vector3f(x, y, z), 0, 0, 0, 0.9f));
			
		}*/
		
		//***************************************************//
		
		
		//***************** Create Lights *******************//
		
		List<Light> lights = new ArrayList<Light>();/*
		lights.add(new Light(new Vector3f(3000, 5000, 3000), new Vector3f(0.3f, 0.3f, 0.3f)));
		lights.add(new Light(new Vector3f(100, terrain.getHeightOfTerrain(100, 300)+10, 300), new Vector3f(2, 0, 0), new Vector3f(1, 0.01f, 0.002f)));
		lights.add(new Light(new Vector3f(200, terrain.getHeightOfTerrain(200, 300)+10, 300), new Vector3f(0, 2, 2), new Vector3f(1, 0.01f, 0.002f)));
		lights.add(new Light(new Vector3f(300, terrain.getHeightOfTerrain(300, 300)+10, 300), new Vector3f(2, 2, 0), new Vector3f(1, 0.01f, 0.002f)));

		entities.add(new Entity(lamp1, new Vector3f(100, terrain.getHeightOfTerrain(100, 300), 300), 0, 0, 0, 1));
		entities.add(new Entity(lamp1, new Vector3f(200, terrain.getHeightOfTerrain(200, 300), 300), 0, 0, 0, 1));
		entities.add(new Entity(lamp1, new Vector3f(300, terrain.getHeightOfTerrain(300, 300), 300), 0, 0, 0, 1));
		*/
		//****************************************************//
		
		RawModel playerModel = OBJLoader.loadObjModel("person", loader);
		TexturedModel textdPlayer = new TexturedModel(playerModel, new ModelTexture(loader.loadTexture("playerTexture")));
		
		Player player = new Player(textdPlayer, new Vector3f(75, 0, 10), 0, 0, 0, 1);
		Camera camera = new Camera(player);
		
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		MasterRenderer renderer = new MasterRenderer(loader);
		
		
		WaterShader waterShader = new WaterShader();
		WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix());
		List<WaterTile> waters = new ArrayList<WaterTile>();
		waters.add(new WaterTile(75, 75, 2));
		
		WaterFrameBuffers fbos = new WaterFrameBuffers();
		
		List<GuiTexture> guis = new ArrayList<GuiTexture>();
		GuiTexture gui = new GuiTexture(loader.loadTexture("socuwan"), new Vector2f(0.85f, 0.9f), new Vector2f(0.125f, 0.125f));
		GuiTexture gui2 = new GuiTexture(fbos.getReflectionTexture(), new Vector2f(-0.5f, 0.5f), new Vector2f(0.5f, 0.5f));
		guis.add(gui);
		guis.add(gui2);
		
		MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix(), terrain);
		
		while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			player.move(terrain);
			camera.move();			
			picker.update();
			
			fbos.bindReflectionFrameBuffer();
			renderer.renderScene(entities, terrains, lights, camera);
			fbos.unbindCurrentFrameBuffer();
			
			renderer.renderScene(entities, terrains, lights, camera);
			waterRenderer.render(waters, camera);
			renderer.processEntity(player);
			
			guiRenderer.render(guis);
			DisplayManager.updateDisplay();
		}
		
		fbos.cleanUp();
		waterShader.cleanUp();
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}
