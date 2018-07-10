import javax.swing.ImageIcon;
import java.awt.*;
import java.security.SecureRandom;

public class Monster{
	int x,y,dx,dy,index,imageindex,temp,timetobreak,pacx,pacy,eatingtime,flag_beeaten;
	String path;
	Image monsterimage[], scaredmonster[], image;
	Map map = new Map();
	int monsterinblock, blockinfo, forkpara;
	SecureRandom random = new SecureRandom();
	
	/*public Monster(Map map)
	{
		x = 7*map.blocksize+1; y = 5*map.blocksize+1;
		imageindex = 0;
		temp = 0;
		monsterimage = new Image[2];
		scaredmonster = new Image[2];
		monsterimage[0] = new ImageIcon("image\\GhostRed1.png").getImage();
		monsterimage[1] = new ImageIcon("image\\GhostRed2.png").getImage();
		scaredmonster[0] = new ImageIcon("image\\GhostScared1.png").getImage();
		scaredmonster[1] = new ImageIcon("image\\GhostScared2.png").getImage();
		this.map = map;
	}*/
	public void newLevel(){
		x = 7*map.blocksize+1; y = 5*map.blocksize+1;
	}
	public boolean isfork(){
		forkpara = 0;
		setBlocknum();
		blockinfo = map.screendata[monsterinblock]; 
		if((blockinfo&1) == 0)
			forkpara++;
		if((blockinfo&2) == 0)
			forkpara++;
		if((blockinfo&4) == 0)
			forkpara++;
		if((blockinfo&8) == 0)
			forkpara++;
		if(forkpara>2)
			return true;
		else
			return false;
	} 
	public void setBlocknum(){
		if((x%map.blocksize==1) && (y%map.blocksize==1))
		{
			monsterinblock = x/map.blocksize+map.numofblocks*(int)(y/map.blocksize);
		}
	}
	
	public void setmonsterimage(int eatingtime){
		temp++;
		if(temp%20==0)
			imageindex++;
		if(iseatable(eatingtime) && !beeaten())
			image = scaredmonster[imageindex%2];
		else if(beeaten())
			image = new ImageIcon("null").getImage();
		else
			image = monsterimage[imageindex%2];
	}
	
	public boolean beeaten(){
		if(iseatable(eatingtime))
		{
			if(Math.abs(pacx-x)<3 && Math.abs(pacy-y)<3)
				flag_beeaten = 1;
			if(flag_beeaten==1)
				return true;
			else return false;
		}
		else if(flag_beeaten==1){
			x = 7*map.blocksize+1; 
			y = 5*map.blocksize+1;
			flag_beeaten = 0;
			return false;
		}
		else 
			return false;
	}
 	public boolean iseatable(int eatingtime){
		this.eatingtime = eatingtime;
		if(eatingtime>500)
			return false;
		else
			return true;
	} 
	
	public void setPosition(int pacx, int pacy, int eatingtime){
		this.pacx = pacx;
		this.pacy = pacy;
		if(iseatable(eatingtime))
			gorandom();
		else
			gochase(pacx, pacy);
		x = x+dx;
	    y = y+dy;
		if(x==360 && y==145)
			x = -21;
		if(x==-22 && y==145)
			x = 359;
	}
	
	public void gochase(int pacx, int pacy){
		if((x%map.blocksize==1) && (y%map.blocksize==1)){
			timetobreak = 0;
			if(isfork())
			{
				while(true){
					timetobreak++;
					index = 1 + random.nextInt(4);
					if(x > pacx && (blockinfo&1) == 0 && dx!=1 && index==1)
					{
						dx = -1;
						dy = 0;
						break;
					}
					else if(x < pacx && (blockinfo&4) == 0 && dx!=-1 && index==2)
					{
						dx = 1;
						dy = 0;
						break;
					}
					else if(y > pacy && (blockinfo&2) == 0 && dy!=1 && index==3)
					{
						dx = 0;
						dy = -1;
						break;
					}
					else if(y < pacy && (blockinfo&8) == 0 && dy!=-1 && index==4)
					{
						dx = 0;
						dy = 1;
						break;
					}
					else if(timetobreak>10000){
						gorandom();
						break;
					}
				}
			}
			else
			{
				if((blockinfo&1) == 0 && dx!=1)
				{
					dx = -1;
					dy = 0;
				}
				else if((blockinfo&2) == 0 && dy!=1)
				{
					dx = 0;
					dy = -1;
				}
				else if((blockinfo&4) == 0 && dx!=-1)
				{
					dx = 1;
					dy = 0;
				}
				else if((blockinfo&8) == 0 && dy!=-1)
				{
					dx = 0;
					dy = 1;
				}
			}
		}
	}		
	
	public void gorandom(){
		if((x%map.blocksize==1) && (y%map.blocksize==1)){
			if(isfork())
			{
				while(true){
					index = 1 + random.nextInt(4);
					if(index==1 && (blockinfo&1) ==0 && dx!=1)
					{
						dx = -1;
						dy = 0;
						break;
					}
					else if(index==2 && (blockinfo&2) == 0 && dy!=1)
					{
						dx = 0;
						dy = -1;
						break;
					}
					else if(index==3 && (blockinfo&4) == 0 && dx!=-1)
					{
						dx = 1;
						dy = 0;
						break;
					}
					else if(index==4 && (blockinfo&8) == 0 && dy!=-1)
					{
						dx = 0;
						dy = 1;
						break;
					}
					
				}
			}
			else
			{
				if((blockinfo&1) == 0 && dx!=1)
				{
					dx = -1;
					dy = 0;
				}
				else if((blockinfo&2) == 0 && dy!=1)
				{
					dx = 0;
					dy = -1;
				}
				else if((blockinfo&4) == 0 && dx!=-1)
				{
					dx = 1;
					dy = 0;
				}
				else if((blockinfo&8) == 0 && dy!=-1)
				{
					dx = 0;
					dy = 1;
				}
			}
		}
	}
	public void drawmonster(Graphics g){
		g.drawImage(image,x,y,null);
//		g.drawString(debug(),300,400);
	}
	public void resetMonsterPosi(){
		x = 7*map.blocksize+1; 
		y = 5*map.blocksize+1;
	}
	//public String debug(){
	//	return String.format("x:%d, y:%d, dx:%d, dy:%d, index:%d, info1 :%d, info2 :%d", x, y, dx, dy, index, monsterinblock, blockinfo);
	//}
	public Point getPoint(){
		Point p = new Point(x,y);
		return p;
	}
}