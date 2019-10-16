package Input;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * Keyboard class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
class MKeyListener extends KeyAdapter {
	 
	private ChatClient client;
	
	public MKeyListener(ChatClient client) {
		this.client = client;
	}
	
    @Override
    public void keyPressed(KeyEvent event) {
 
    	if (event.getKeyCode() == KeyEvent.VK_UP) {
    		try {
				client.sendToServer(event.getKeyCode());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	if (event.getKeyCode() == KeyEvent.VK_DOWN) {
    		try {
				client.sendToServer(event.getKeyCode());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	if (event.getKeyCode() == KeyEvent.VK_LEFT) {
    		try {
				client.sendToServer(event.getKeyCode());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
    		try {
				client.sendToServer(event.getKeyCode());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
}
