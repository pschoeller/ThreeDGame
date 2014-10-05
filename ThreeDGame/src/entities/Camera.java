package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private Vector3f position = new Vector3f(0,0,0);
	private float pitch, yaw, roll, deltaRate=0.5f;
	
	
	public Camera(){
		
	}
	
	
	public void move(){
<<<<<<< HEAD
		float deltaFactor = 0.04f;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			position.z -= deltaFactor;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			position.z += deltaFactor;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			position.x -= deltaFactor;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			position.x += deltaFactor;
=======
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			position.z -= deltaRate;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			position.z += deltaRate;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			position.x -= deltaRate;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			position.x += deltaRate;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_NEXT)){
			position.y -= deltaRate;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_PRIOR)){
			position.y += deltaRate;
>>>>>>> 89c80314f0df00a2f5df6dd6489b6025cad135fb
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
			position.y += deltaFactor;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
			position.y -= deltaFactor;
		}
	}


	public Vector3f getPosition() {
		return position;
	}


	public void setPosition(Vector3f position) {
		this.position = position;
	}


	public float getPitch() {
		return pitch;
	}


	public void setPitch(float pitch) {
		this.pitch = pitch;
	}


	public float getYaw() {
		return yaw;
	}


	public void setYaw(float yaw) {
		this.yaw = yaw;
	}


	public float getRoll() {
		return roll;
	}


	public void setRoll(float roll) {
		this.roll = roll;
	}
}
