package audio;

import java.io.IOException;

public class Test{

	public static void main(String[] args) throws IOException{
		
		AudioMaster.init();
		AudioMaster.setListenerData();
		
		int buffer = AudioMaster.loadSound("audio/cartoon-spring01.wav");
		Source source = new Source();
		
		char c = ' ';
		while(c != 'q'){
			c = (char)System.in.read();
			
			if(c == 'p'){
				source.play(buffer);
			}
		}
		
		source.delete();
		AudioMaster.cleanUp();
	}
}
