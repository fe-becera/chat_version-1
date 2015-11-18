import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Image;

public class Game extends Canvas{
	
		public static final int width = 1024;
		public static final int height = 768;
		public static final String NAME = "GAME";
		
		private JFrame main_frame;
		
		private void prepareGUI(){
		
			setMinimumSize(new Dimension (width, height));
			setMaximumSize(new Dimension (width, height));
			setPreferredSize(new Dimension (width, height));

			//main_frame - GridLayout
			main_frame = new JFrame(NAME);

			main_frame.setDefaultCloseOperation(main_frame.EXIT_ON_CLOSE);
			main_frame.setLayout(new FlowLayout());
			
			//========================================================LEFT-PANEL
			//Panel for chat and player's stats -  GridLayout
			JPanel left_panel = new JPanel();
			left_panel.setPreferredSize(new Dimension (220, height));
			left_panel.setLayout(new GridLayout(2,1));

			
			//Panel for player's stats
			JPanel stats_panel = new JPanel();
			stats_panel.setLayout(new BorderLayout());
			
			
			JPanel stats_grid = new JPanel();
			stats_grid.setPreferredSize(new Dimension (90, 90));
			stats_grid.setLayout(new GridLayout(3,3));

			
			//Add image buttons to grid 
			for(int i =0; i<3; i++){
				for(int j=0; j<3; j++){
				
					final JLabel label = new JLabel();
					
					//Deploys chosen character
					label.addMouseListener(new MouseAdapter(){  
						public void mouseClicked(MouseEvent e)  
						{ 
							if(label.getText()== "BARBARIAN"){
								System.out.println("Barbarian");
							}else if(label.getText()== "ARCHER"){
								System.out.println("Archer");
							}else if(label.getText()== "HORSEMAN"){
								System.out.println("Horseman");
							}
						} 
					});
					
					if(j==0){
						if(i==0){
							ImageIcon icon = new ImageIcon("barbarian_mug.png");
							Image img = icon.getImage() ;  
				    		Image newimg = img.getScaledInstance( 40, 40,  java.awt.Image.SCALE_SMOOTH );  
				    		icon = new ImageIcon(newimg);
							label.setIcon(icon);
							label.setText("BARBARIAN");
						}else if(i==1){
							ImageIcon icon = new ImageIcon("archer_mug.png");
							Image img = icon.getImage() ;  
				    		Image newimg = img.getScaledInstance( 40, 40,  java.awt.Image.SCALE_SMOOTH );  
				    		icon = new ImageIcon(newimg);
							label.setIcon(icon);
							label.setText("ARCHER");
						}else if(i==2){
							ImageIcon icon = new ImageIcon("horseman_mug.png");
							Image img = icon.getImage() ;  
				    		Image newimg = img.getScaledInstance( 40, 40,  java.awt.Image.SCALE_SMOOTH );  
				    		icon = new ImageIcon(newimg);
							label.setIcon(icon);
							label.setText("HORSEMAN");
						}

						stats_grid.add(label);
					}
				}
			}//end of for-loop
			
			//Health Status of base
			JLabel health_status = new JLabel("\n\nHEALTH STATUS =================");
			stats_panel.add(health_status, BorderLayout.SOUTH);
			
			JLabel stats_header = new JLabel("\nSELECT TROOP TO DEPLOY");
			stats_panel.add(stats_header, BorderLayout.NORTH);
			
			stats_panel.add(stats_grid,BorderLayout.CENTER);
					
			//Panel for chat
			JPanel chat_panel = new JPanel();
			chat_panel.setLayout(new BorderLayout());
			
			//Chat Header
			JLabel chat_header = new JLabel("\n\nCHAT HEADER");
			chat_panel.add(chat_header, BorderLayout.NORTH);
			
			//Chatbox
			JLabel chatbox_later = new JLabel("\n\nCHAT BOX");
			chat_panel.add(chatbox_later, BorderLayout.CENTER);
			
			//Typebox
			JLabel chatbox_type = new JLabel("\n\nCHAT TYPE");
			chat_panel.add(chatbox_type, BorderLayout.SOUTH);
			
			left_panel.add(stats_panel);
			left_panel.add(chat_panel);
			//======================================================== End of LEFT-PANEL
			
			//Panel for game's map -  FlowLayout
			final JPanel center_panel = new JPanel();
			center_panel.setPreferredSize(new Dimension (870, height));
			center_panel.setLayout(new FlowLayout());
			
			//MouseListener for map coordinates
			class CustomMouseListener implements MouseListener{
			
			  public void mouseClicked(MouseEvent e) {
				System.out.println("Mouse Clicked: ("+e.getX()+", "+e.getY() +")");
			  }

			  public void mousePressed(MouseEvent e) {
			  }

			  public void mouseReleased(MouseEvent e) {
			  }

			  public void mouseEntered(MouseEvent e) {
			  }

			  public void mouseExited(MouseEvent e) {
			  }
			}
			
			center_panel.addMouseListener(new CustomMouseListener());
			
			//Main game map - GridLayout
			JPanel map = new JPanel();
			map.setLayout(new GridLayout(20,30));
			
			//Filling the whole map with images
			for(int i =0; i<20; i++){
				for(int j=0; j<30; j++){
				
					JLabel map_label = new JLabel();
					
					if((i>=0 && i<=10) && (j>=0 && j<=10)){
						ImageIcon map_icon = new ImageIcon("green.png");
						Image map_img = map_icon.getImage() ;  
            			Image map_newimg = map_img.getScaledInstance( 30, 30,  java.awt.Image.SCALE_SMOOTH ) ;  
            			map_icon = new ImageIcon( map_newimg );
						map_label.setIcon(map_icon);
						
					}
					else if((i>=11 && i<=20) && (j>=11 && j<=20)){
						ImageIcon map_icon = new ImageIcon("green.png");
						Image map_img = map_icon.getImage() ;  
            			Image map_newimg = map_img.getScaledInstance( 30, 30,  java.awt.Image.SCALE_SMOOTH ) ;  
            			map_icon = new ImageIcon( map_newimg );
						map_label.setIcon(map_icon);
						
					}
					else if((i>=0 && i<=10) && (j>=21 && j<=30)){
						ImageIcon map_icon = new ImageIcon("green.png");
						Image map_img = map_icon.getImage() ;  
            			Image map_newimg = map_img.getScaledInstance( 30, 30,  java.awt.Image.SCALE_SMOOTH ) ;  
            			map_icon = new ImageIcon( map_newimg );
						map_label.setIcon(map_icon);
						
					}
					else{
						ImageIcon map_icon = new ImageIcon("brown.png");
						Image map_img = map_icon.getImage() ;  
            			Image map_newimg = map_img.getScaledInstance( 30, 30,  java.awt.Image.SCALE_SMOOTH ) ;  
            			map_icon = new ImageIcon( map_newimg );
						map_label.setIcon(map_icon);
					}
					map.add(map_label);
				}
			}
			center_panel.add(map);
			//======================================================== End of CENTER-PANEL
			main_frame.add(left_panel);
			main_frame.add(center_panel);
			//main_frame.add(grid, BorderLayout.CENTER);
			main_frame.pack();

			main_frame.setResizable(false);
			main_frame.setLocationRelativeTo(null);
			main_frame.setVisible(true);


		}//end of prepareGUI method
		
		public Game(){
			prepareGUI();
		}
		public static void main(String[] args){
			Game game = new Game();
		}
}


