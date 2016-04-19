package engineTester;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.RawModel;
import models.TexturedModel;
import normalMappingObjConverter.NormalMappedObjLoader;
import objConverter.ModelData;
import objConverter.OBJFileLoader;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import particles.ParticleMaster;
import particles.ParticleSystem;
import particles.ParticleTexture;
import postProcessing.Fbo;
import postProcessing.PostProcessing;
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
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import guis.GuiRenderer;
import guis.GuiTexture;

public class MainGameLoop {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		TextMaster.init(loader);
		RawModel playerModel = OBJLoader.loadObjModel("person", loader);
		TexturedModel textdPlayer = new TexturedModel(playerModel, new ModelTexture(loader.loadTexture("playerTexture", -0.4f)));
		Player player = new Player(textdPlayer, new Vector3f(75, 0, 10), 0, 0, 0, 1);
		Camera camera = new Camera(player);
		MasterRenderer renderer = new MasterRenderer(loader, camera);
		ParticleMaster.init(loader, renderer.getProjectionMatrix());

		FontType font = new FontType(loader.loadTexture("NeverWinter_DF", 0), "NeverWinter_DF");
		GUIText text = new GUIText("This is sample text.", 4, font, new Vector2f(0.5f, 0.5f), 0.5f, true);
				
		//***********  Create Terrains  *************//
		
		List<Terrain> terrains = new ArrayList<Terrain>();
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy", -0.4f));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt", -0.4f));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers", -0.4f));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path", -0.4f));
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap", -0.4f));
		Terrain terrain = new Terrain(0, 0, loader, texturePack, blendMap, "heightmap-01");
		terrains.add(terrain);
		//*******************************************//
		
		
		//***************** Create Entities ***************//
		
		ModelData data = OBJFileLoader.loadOBJ("tree");
		RawModel treeModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
		TexturedModel tree = new TexturedModel(treeModel, new ModelTexture(loader.loadTexture("tree", -0.4f)));
		
		data = OBJFileLoader.loadOBJ("lamp");
		RawModel lampModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
		TexturedModel lamp1 = new TexturedModel(lampModel, new ModelTexture(loader.loadTexture("lamp", -0.4f)));
		
		data = OBJFileLoader.loadOBJ("lowPolyTree");
		RawModel lowPolyTreeModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
		TexturedModel lowPolyTree = new TexturedModel(lowPolyTreeModel, new ModelTexture(loader.loadTexture("tree", -0.4f)));
		
		data = OBJFileLoader.loadOBJ("grassModel");
		RawModel grassModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
		TexturedModel grass = new TexturedModel(grassModel, new ModelTexture(loader.loadTexture("grassTexture", -0.4f)));
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLighting(true);
		
		data = OBJFileLoader.loadOBJ("fern");
		ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fern", -0.4f));
		fernTextureAtlas.setNumberOfRows(2);
		RawModel fernModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
		TexturedModel fern = new TexturedModel(fernModel, fernTextureAtlas);
		fern.getTexture().setHasTransparency(true);
		fern.getTexture().setUseFakeLighting(true);
		
		data = OBJFileLoader.loadOBJ("grassModel");
		RawModel flowerModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
		TexturedModel flower = new TexturedModel(flowerModel, new ModelTexture(loader.loadTexture("flower", -0.4f)));
		flower.getTexture().setHasTransparency(true);
		flower.getTexture().setUseFakeLighting(true);
		
		List<Entity> entities = new ArrayList<Entity>();
		List<Entity> normalMapEntities = new ArrayList<Entity>();
		
		TexturedModel barrelModel = new TexturedModel(NormalMappedObjLoader.loadOBJ("barrel", loader), new ModelTexture(loader.loadTexture("barrel", -0.4f)));
		barrelModel.getTexture().setNormalMap(loader.loadTexture("barrelNormal", -0.4f));
		barrelModel.getTexture().setShineDamper(10);
		barrelModel.getTexture().setReflectivity(0.5f);
		
		entities.add(new Entity(barrelModel, new Vector3f(75, 10, 5), 0, 0, 0, 1f));
		
		Random random = new Random();
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
			
		}
		
		//***************************************************//
		
		
		//***************** Create Lights *******************//
		Light sun = new Light(new Vector3f(10000, 15000, -10000), new Vector3f(1.3f, 1.3f, 1.3f));
		
		List<Light> lights = new ArrayList<Light>();
		lights.add(sun);
		/*lights.add(new Light(new Vector3f(100, terrain.getHeightOfTerrain(100, 300)+10, 300), new Vector3f(2, 0, 0), new Vector3f(1, 0.01f, 0.002f)));
		lights.add(new Light(new Vector3f(200, terrain.getHeightOfTerrain(200, 300)+10, 300), new Vector3f(0, 2, 2), new Vector3f(1, 0.01f, 0.002f)));
		lights.add(new Light(new Vector3f(300, terrain.getHeightOfTerrain(300, 300)+10, 300), new Vector3f(2, 2, 0), new Vector3f(1, 0.01f, 0.002f)));

		entities.add(new Entity(lamp1, new Vector3f(100, terrain.getHeightOfTerrain(100, 300), 300), 0, 0, 0, 1));
		entities.add(new Entity(lamp1, new Vector3f(200, terrain.getHeightOfTerrain(200, 300), 300), 0, 0, 0, 1));
		entities.add(new Entity(lamp1, new Vector3f(300, terrain.getHeightOfTerrain(300, 300), 300), 0, 0, 0, 1));
		*/
		//****************************************************//
		
		entities.add(player);
		
		WaterFrameBuffers buffers = new WaterFrameBuffers();
		WaterShader waterShader = new WaterShader();
		WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), buffers);
		List<WaterTile> waters = new ArrayList<WaterTile>();
		WaterTile water = new WaterTile(75, 75, 0);
		waters.add(water);
		
		List<GuiTexture> guiTextures = new ArrayList<GuiTexture>();
		GuiTexture shadowMap = new GuiTexture(renderer.getShadowMapTexture(), new Vector2f(0.5f, 0.5f), new Vector2f(0.5f, 0.5f));
		guiTextures.add(shadowMap);
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		
		MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix(), terrain);
		
		ParticleTexture particleTexture  = new ParticleTexture(loader.loadTexture("particleAtlas", 0), 4);
		
		ParticleSystem system = new ParticleSystem(particleTexture, 50, 10, 1.0f, 4, 5);
		system.randomizeRotation();
		system.setDirection(new Vector3f(0, 1, 0), 0.3f);
		system.setLifeError(0.1f);
		system.setSpeedError(0.4f);
		system.setScaleError(0.8f);
		
		Fbo fbo = new Fbo(Display.getWidth(), Display.getHeight(), Fbo.DEPTH_RENDER_BUFFER);
		PostProcessing.init(loader);
		
		while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			player.move(terrain);
			camera.move();			
			picker.update();	
			system.generateParticles(new Vector3f(55, 25, 15));
			ParticleMaster.update(camera);
			
			renderer.renderShadowMap(entities, sun);
			GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
			
			
			// render reflection texture
			buffers.bindReflectionFrameBuffer();
			float distance = 2 * (camera.getPosition().y - water.getHeight());
			camera.getPosition().y -= distance;
			camera.invertPitch();
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, 1, 0, -water.getHeight() + 1.0f));
			camera.getPosition().y += distance;
			camera.invertPitch();
			
			// render refraction texture
			buffers.bindRefractionFrameBuffer();
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, -1, 0, water.getHeight()));
			
			// render to screen
			GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
			buffers.unbindCurrentFrameBuffer();
			fbo.bindFrameBuffer();
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, -1, 0, 15));
			waterRenderer.render(waters, camera, sun);
			ParticleMaster.renderParticles(camera);
			fbo.unbindFrameBuffer();
			PostProcessing.doPostProcessing(fbo.getColourTexture());
			
			//guiRenderer.render(guiTextures);
			//TextMaster.render();
			
			DisplayManager.updateDisplay();
		}
		
		PostProcessing.cleanUp();
		fbo.cleanUp();
		ParticleMaster.cleanUp();
		TextMaster.cleanUp();
		buffers.cleanUp();
		waterShader.cleanUp();
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}
