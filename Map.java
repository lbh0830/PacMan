import java.awt.*;

public class Map{
	final int blocksize=24;
	final int numofblocks=15;
	final int scrsize=numofblocks*blocksize;
	int []screendata = new int[numofblocks*numofblocks];
	final int mapdata[]={
		19,26,18,26,26,26,22, 7,19,26,26,26,18,26,22,
		37,15,21,11,10,14,21,13,21,11,10,14,21,15,37,
		17,26,16,26,18,26,24,26,24,26,18,26,16,26,20,
		21,15,21, 7,21,11, 2, 2, 2,14,21, 7,21,15,21,
		25,26,20, 5,25,22, 9, 8,12,19,28, 5,17,26,28,
		11,14,21, 1,14,17,10,10,10,20,11, 4,21,11,14,
		10,10,20, 5,19,28, 3, 2, 6,25,22, 5,17,10,10,
		11,14,21,13,21,11, 8, 8, 8,14,21,13,21,11,14,
		19,26,24,18,24,26,26,26,26,26,24,18,24,26,22,
		21,11, 6,21,11,10,10,10,10,10,14,21, 3,14,21,
		41,22, 5,17,26,18,26,10,26,18,26,20, 5,19,44,
		15,21,13,21, 7,21,11, 2,14,21, 7,21,13,21,15,
		19,24,26,28, 5,25,22, 5,19,28, 5,25,26,24,22,
		21,11,10,10, 8,14,21,13,21,11, 8,10,10,14,21,
		25,26,26,26,26,26,24,26,24,26,26,26,26,26,28
	};
	
	
	public Map(){
		for(int i=0;i<numofblocks*numofblocks;i++){
			screendata[i] = mapdata[i];
		}
	}
	
	
	public void drawmap(Graphics g){
		int i=0;
		for(int y=0; y<scrsize; y+=blocksize)
		{
			for(int x=0; x<scrsize; x+=blocksize)
			{
				g.setColor(Color.BLUE);
				if((screendata[i]&1)!=0)
				{
					g.drawLine(x,y,x,y+blocksize-1);
				}
				if((screendata[i]&2)!=0)
				{	
					g.drawLine(x,y,x+blocksize-1,y);
				}
				if((screendata[i]&4)!=0)
				{
					g.drawLine(x+blocksize-1,y,x+blocksize-1,y+blocksize-1);
				}
				if((screendata[i]&8)!=0)
				{
					g.drawLine(x,y+blocksize-1,x+blocksize-1,y+blocksize-1);
				}
				if((screendata[i]&16)!=0)
				{
					g.setColor(Color.YELLOW);
					g.fillRect(x+11,y+11,2,2);
				}
				if((screendata[i]&32)!=0)
				{
					g.setColor(Color.YELLOW);
					g.fillRect(x+8,y+8,8,8);
				}
				i++;
			}
		}
	} 
	

	
	public boolean isfinished(){
		for(int i=0;i<numofblocks*numofblocks;i++){
			if((screendata[i] & 16)!=0 || (screendata[i] & 32)!=0)
				return false;
		}
		return true;
	}		
}