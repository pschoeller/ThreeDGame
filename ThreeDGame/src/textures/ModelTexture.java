package textures;

public class ModelTexture {
	
	private int textureID;
	private int normalMap;
	private int specularMap;
	
	private float shineDamper = 1;
	private float reflectivity = 0;
	
	private boolean hasTransparency = false;
	private boolean useFakeLighting = false;
	private boolean hasSpecularMap = false;
	
	private int numberOfRows = 1;
	
	
	public int getNumberOfRows() {
		return numberOfRows;
	}


	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

	
	public int getNormalMap(){
		return normalMap;
	}


	public void setNormalMap(int normalMap){
		this.normalMap = normalMap;
	}


	public boolean isUseFakeLighting() {
		return useFakeLighting;
	}


	public void setUseFakeLighting(boolean useFakeLighting) {
		this.useFakeLighting = useFakeLighting;
	}


	public float getShineDamper() {
		return shineDamper;
	}
	
	
	public boolean isHasTransparency() {
		return hasTransparency;
	}


	public void setHasTransparency(boolean hasTransparency) {
		this.hasTransparency = hasTransparency;
	}


	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}


	public float getReflectivity() {
		return reflectivity;
	}


	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}
	
	
	public void setSpecularMap(int specMap){
		this.specularMap = specMap;
		this.hasSpecularMap = true;
	}
	
	
	public boolean hasSpecularMap(){
		return hasSpecularMap;	
	}
	
	
	public int getSpecularMap(){
		return specularMap;
	}


	public ModelTexture(int id){
		this.setTextureID(id);
	}


	public int getTextureID() {
		return textureID;
	}


	public void setTextureID(int textureID) {
		this.textureID = textureID;
	}
}
