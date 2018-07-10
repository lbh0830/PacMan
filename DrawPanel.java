import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.*;

public class DrawPanel extends JPanel implements KeyListener{
	Map map = new Map();
	Blue blue = new Blue(map);
	Red red = new Red(map);
	Orange orange = new Orange(map);
	Pacman pacman = new Pacman(map,blue,red,orange);
	int index;
	private Timer time;
	private Monster monster[];
	private int sleepTime, monsterdelaytime;	
	
	public DrawPanel(){
		setSize(360,360);
		setBackground(Color.BLACK);
		sleepTime = 10;
		start();
		index = 3;
		monster = new Monster[3];
		monster[0] = blue;
		monster[1] = red;
		monster[2] = orange;
	}
	
	public void start()
	{
		time = new Timer();
		time.schedule( new TimerTask() {
			public void run()
			{
				pacman.setMove(index);
				System.out.printf("");
				pacman.setPosition();
				pacman.setpacmanimage();
				if(map.isfinished()){
					try{
						Thread.sleep(1200);
						pacman.newLevel();
					}catch(InterruptedException e){}
				}
				if(pacman.runcase!=0){
					try{
						Thread.sleep(1200);
						pacman.runcase++;
					}catch(InterruptedException e){}
				}
				if(pacman.runcase==3)
					pacman.runcase = 0;
				if(pacman.endgame())
				{
					time.cancel();
				}
				for(int i=0;i<3;i++){
					if(monster[i].iseatable(pacman.geteatingtime()))
					{
						monsterdelaytime++;
						if(monsterdelaytime%2==0)
							monster[i].setPosition(pacman.getPoint().x, pacman.getPoint().y, pacman.geteatingtime());
					}
					else
						monster[i].setPosition(pacman.getPoint().x, pacman.getPoint().y, pacman.geteatingtime());
					monster[i].setmonsterimage(pacman.geteatingtime());
				}
				repaint();
			}
		},0,sleepTime);
		addKeyListener(this);
	}
	@Override 
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		switch(key){
			case KeyEvent.VK_RIGHT:
                index = 1;
                break;
 
            case KeyEvent.VK_DOWN:
                index = 2;
                break;
             
            case KeyEvent.VK_UP:
                index = 4;
                break;
             
            case KeyEvent.VK_LEFT:
                index = 3;
                break;
				
			default:
				break;
		}
	}
	@Override
	public void keyReleased(KeyEvent e){
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		map.drawmap(g);
		pacman.drawpacman(g);
		for(int i=0;i<3;i++)
			monster[i].drawmonster(g);
	}
}