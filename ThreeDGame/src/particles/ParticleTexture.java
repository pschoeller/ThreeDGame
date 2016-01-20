package particles;

public class ParticleTexture{
	
	private int textureID;
	private int numberOfRows;
	private boolean additive;
	
	
	public ParticleTexture(int textureID, int numberOfRows){
	    super();
	    this.textureID = textureID;
	    this.numberOfRows = numberOfRows;
    }


	protected int getTextureID(){ return textureID; }
	protected int getNumberOfRows(){ return numberOfRows; }
	protected boolean usesAdditiveBlending(){ return additive; }
}
