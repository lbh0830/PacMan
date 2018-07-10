import javax.swing.JFrame;
import java.awt.*;

public class RunPacman{
	public static void main(String[] args){
		DrawPanel panel = new DrawPanel();
		JFrame frame = new JFrame();
		
		panel.setFocusable(true);
		frame.add(panel);
		frame.setLocation(500,150);
		frame.setResizable(false);
		frame.setTitle("Pacman");
		frame.setSize(377,423);
		frame.setBackground(Color.BLACK);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	} 
}