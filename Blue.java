import javax.swing.ImageIcon;
import java.awt.*;
import java.security.SecureRandom;

public class Blue extends Monster{
	public Blue(Map map)
	{
		x = 7*map.blocksize+1; y = 5*map.blocksize+1;
		imageindex = 0;
		temp = 0;
		monsterimage = new Image[2];
		scaredmonster = new Image[2];
		monsterimage[0] = new ImageIcon("image\\GhostBlue1.png").getImage();
		monsterimage[1] = new ImageIcon("image\\GhostBlue2.png").getImage();
		scaredmonster[0] = new ImageIcon("image\\GhostScared1.png").getImage();
		scaredmonster[1] = new ImageIcon("image\\GhostScared2.png").getImage();
		this.map = map;
	}
	@Override
	public void setPosition(int pacx, int pacy, int eatingtime){
		this.pacx = pacx;
		this.pacy = pacy;
		gorandom();
		x = x+dx;
	    y = y+dy;
		if(x==360 && y==145)
			x = -21;
		if(x==-22 && y==145)
			x = 359;
	}
	
}