import javax.swing.ImageIcon;
import java.awt.*;
import java.io.*;
import java.util.*;

public class Pacman{
	private int x,y,dx,dy,index,reqdx,reqdy,imageindex,temp,flag_beeaten[];
	private String path;
	private Image pacmanup[], pacmandown[], pacmanleft[], pacmanright[], image;
	private Map map = new Map();
	private int pacmaninblock, blockinfo;
	private int score,life,highscore;
	private int eatingtime;
	private Blue blue = new Blue(map);
	private Red red = new Red(map);
	private Orange orange = new Orange(map);
	private Monster monster[];
	private Formatter output;
	int runcase;
	
	public Pacman(Map map, Blue blue, Red red, Orange orange)
	{
		x = 7*map.blocksize+1; y = 10*map.blocksize+1;
		try{
			Scanner file = new Scanner(new File("highscore.txt"));
			while(file.hasNext())
				highscore = file.nextInt();
		}catch(FileNotFoundException e){
			System.out.println("File not Found!");
		};
		imageindex = 0;
		eatingtime = 501;
		runcase = 0;
		temp = 0;
		life = 2;
		flag_beeaten = new int[3];
		monster = new Monster[3];
		monster[0] = blue;
		monster[1] = red;
		monster[2] = orange;
		pacmanup = new Image[3];
		pacmandown = new Image[3];
		pacmanleft = new Image[3];
		pacmanright = new Image[3];
		pacmanup[0] = new ImageIcon("image\\PacMan2up.gif").getImage();
		pacmanup[1] = new ImageIcon("image\\PacMan3up.gif").getImage();
		pacmanup[2] = new ImageIcon("image\\PacMan4up.gif").getImage();
		pacmandown[0] = new ImageIcon("image\\PacMan2down.gif").getImage();
		pacmandown[1] = new ImageIcon("image\\PacMan3down.gif").getImage();
		pacmandown[2] = new ImageIcon("image\\PacMan4down.gif").getImage();
		pacmanleft[0] = new ImageIcon("image\\PacMan2left.gif").getImage();
		pacmanleft[1] = new ImageIcon("image\\PacMan3left.gif").getImage();
		pacmanleft[2] = new ImageIcon("image\\PacMan4left.gif").getImage();
		pacmanright[0] = new ImageIcon("image\\PacMan2right.gif").getImage();
		pacmanright[1] = new ImageIcon("image\\PacMan3right.gif").getImage();
		pacmanright[2] = new ImageIcon("image\\PacMan4right.gif").getImage();
		this.map = map;
		this.blue = blue;
		this.red = red;
		this.orange = orange;
		
	}
	public void setpacmanimage(){
		if(dx!=0 || dy!=0)
		{
			temp++;
			if(temp%3==0)
				imageindex++;
		}
		if(dx==-1)
			image = pacmanleft[imageindex%3];
		else if(dx==1)
			image = pacmanright[imageindex%3];
		else if(dy==-1)
			image = pacmanup[imageindex%3];
		else if(dy==1)
			image = pacmandown[imageindex%3];
	}
	public void setMove(int index){
		this.index = index;
		switch(index){
			case 4:
				reqdy = -1;
				reqdx = 0;
				break;
 
			case 1:
				reqdx = 1;
				reqdy = 0;
				break;
			 
			case 2:
				reqdy = 1;
				reqdx = 0;
				break;
			 
			case 3:
				reqdx = -1;
				reqdy = 0;
				break;
				
			default:
				dx = -1;
				break;
		}
	}
 	public int geteatingtime(){
		return eatingtime;
	}
	public void setPosition(){
		eatingtime++;
		if((x%map.blocksize==1) && (y%map.blocksize==1) || dx==-reqdx || dy==-reqdy)
			if(nextmovable())
			{
				dx = reqdx;
				dy = reqdy;
			}
		if(!movable() && dx==reqdx && dy==reqdy)
		{
			dx = 0;
			dy = 0;	
		}
		else if(!movable())
		{
			if(nextmovable())
			{
				dx = reqdx;
				dy = reqdy;
			}
			else
			{
				dx = 0;
				dy = 0;
			}
		}  
		x = x+dx;
	    y = y+dy;
		if(x==360 && y==145)
			x = -21;
		if(x==-22 && y==145)
			x = 359;
		eat();
		if(becatch()){
			dx = 0;
			dy = 0;
			x = 7*map.blocksize+1;
			y = 10*map.blocksize+1;
			imageindex = 0;
			eatingtime = 501;
			temp = 0;
			life--;
		}
	}
	
	public void eat(){
		if((blockinfo&16)!=0)
		{
			map.screendata[pacmaninblock]-=16;
			score++;
		}
		if((blockinfo&32)!=0)
		{
			map.screendata[pacmaninblock]-=32;
			eatingtime = 0;
			score+=5;
		}
		for(int i=0;i<3;i++){
			if(monster[i].beeaten() && flag_beeaten[i]==0){
				flag_beeaten[i] = 1;
				score+=20;
			}
			if(!monster[i].iseatable(eatingtime)){
				flag_beeaten[i] = 0;
			}
		}
		setHighScore();
	}
	public boolean becatch(){
		for(int i=0;i<3;i++){
			if(!monster[i].iseatable(eatingtime) && Math.abs(monster[i].getPoint().x-x)<3 && Math.abs(monster[i].getPoint().y-y)<3){
				x = 7*map.blocksize+1; 
				y = 10*map.blocksize+1;
				blue.resetMonsterPosi();
				red.resetMonsterPosi();
				orange.resetMonsterPosi();
				runcase = 1;
				return true;
			}
		}
		return false;
	}
	public boolean endgame(){
		if(life==-1){
			if(highscore==score){
				try{
					output = new Formatter("highscore.txt");
				}catch(FileNotFoundException e){};
				try{
					output.format("%d",score);
				}catch(NoSuchElementException e){};
				if(output != null)
					output.close();
			}
			return true;
		}
		else
			return false;
	}
	
	public boolean movable(){
		setBlocknum();
		blockinfo = map.screendata[pacmaninblock]; 
		if((dx==-1 && (blockinfo&1)!=0) || 
		    (dx==1 && (blockinfo&4)!=0) || 
		   (dy==-1 && (blockinfo&2)!=0) ||
 		    (dy==1 && (blockinfo&8)!=0)	)
			return false;
		else return true;
	}
	
	public boolean nextmovable(){
		setBlocknum();
		blockinfo = map.screendata[pacmaninblock]; 
		if((reqdx==-1 && (blockinfo&1)!=0) || 
		    (reqdx==1 && (blockinfo&4)!=0) || 
		   (reqdy==-1 && (blockinfo&2)!=0) ||
 		    (reqdy==1 && (blockinfo&8)!=0)	)
			return false;
		else return true;
	}
	public void setHighScore(){
		if(score>=highscore){
			highscore = score;
		}
	}
	public void setBlocknum(){
		if((x%map.blocksize==1) && (y%map.blocksize==1))
		{
			pacmaninblock = x/map.blocksize+map.numofblocks*(int)(y/map.blocksize);
		}
	}
	public void drawpacman(Graphics g){
		String s = "Score: "+score;
		g.setColor(Color.YELLOW);
		g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
		g.drawImage(image,x,y,null);
		g.drawString(s,60,378);
		for(int i=0;i<life;i++){
			g.drawImage(pacmanright[1],2+i*22,map.scrsize+2,null);
		}
		if(endgame()){
			g.drawString("Game Over!",145,233);
		}
		if(runcase==2&&!endgame()){
			g.drawString("Ready!",158,233);
		}
		g.drawString("Highscore: "+highscore, 200,378);
	}
	public void newLevel(){
		if(map.isfinished())
		{
			for(int i=0;i<map.numofblocks*map.numofblocks;i++){
				map.screendata[i] = map.mapdata[i];
			}
			x = 7*map.blocksize+1; y = 10*map.blocksize+1;
			eatingtime = 501;
			for(int i=0;i<3;i++)
				monster[i].newLevel();
		}
	}
	public Point getPoint(){
		Point p = new Point(x,y);
		return p;
	}
	/*public String debug(){
		return String.format("x:%d, y:%d, dx:%d, dy:%d, index:%d, info1 :%d, info2 :%d", x, y, dx, dy, index, pacmaninblock, blockinfo);
	}*/
}