
import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class GridBagLayoutDemo extends Canvas {

    public static void addComponentsToPane(Container pane) {

        JButton jbnButton;
        pane.setLayout(new GridBagLayout());
        GridBagConstraints gBC = new GridBagConstraints();
        gBC.fill = GridBagConstraints.HORIZONTAL;


    

                //Panel for game's map -  FlowLayout
            final JPanel upper_panel = new JPanel();
             upper_panel.setLayout(new FlowLayout());
            upper_panel.setPreferredSize(new Dimension (1030, 700));
           
            
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
            
            upper_panel.addMouseListener(new CustomMouseListener());
            
            //Main game map - GridLayout
            JPanel map = new JPanel();
            map.setLayout(new GridLayout(20,30));
         //  map.setPreferredSize(new Dimension(1000,700));
            
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
            upper_panel.add(map);
            //======================================================== End of CENTER-PANEL

        gBC.gridx = 1;
        gBC.gridy = 0;
        gBC.insets = new Insets(0,0,0,0);  //Padding
        pane.add(upper_panel, gBC);


/*

		JPanel lower_panel =  new JPanel();
		lower_panel.setLayout(new GridBagLayout());
        //jbnButton = new JButton("Button 4");
       

           

            
            //Panel for player's stats
            JPanel stats_panel = new JPanel();
            stats_panel.setLayout(new BorderLayout());
            
            
            JPanel stats_grid = new JPanel();
        //  stats_grid.setPreferredSize(new Dimension (90, 90));
            stats_grid.setLayout(new GridLayout(1,3));

            
            //Add image buttons to grid 
            for(int i =0; i<1; i++){
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
                    
                    if(i==0){
                        if(j==0){
                            ImageIcon icon = new ImageIcon("barbarian_mug.png");
                            Image img = icon.getImage() ;  
                            Image newimg = img.getScaledInstance( 40, 40,  java.awt.Image.SCALE_SMOOTH );  
                            icon = new ImageIcon(newimg);
                            label.setIcon(icon);
                            label.setText("BARBARIAN");
                            label.setText("BARBARIAN");
                          //label.setText("BARBARIAN\n Health Status \n Damage\n Remaining Troops");
                       /*   JPanel archer_panel = new JPanel();
                          archer_panel.setLayout(new FlowLayout());
                            JLabel label_array [][] = new JLabel [4][1];
                            for(int n=0; n<4;n++){
                                for (int m=0;m<1 ;m++ ) {
                                    
                                    if(n==0){
                                            label_array[n][0].setText("BARBARIAN");
                                            label_array[n][1].setText("STATUS");
                                            label_array[n][2].setText("DAMAGE");
                                            label_array[n][3].setText("TROOPS");
                                    }
                                }

                            }
                            archer_panel.add(label_array);  */
    /*                    }else if(j==1){
                            ImageIcon icon = new ImageIcon("archer_mug.png");
                            Image img = icon.getImage() ;  
                            Image newimg = img.getScaledInstance( 40, 40,  java.awt.Image.SCALE_SMOOTH );  
                            icon = new ImageIcon(newimg);
                            label.setIcon(icon);
                            label.setText("ARCHER\n Health Status \n Damage\n Remaining Troops");
                        }else if(j==2){
                            ImageIcon icon = new ImageIcon("horseman_mug.png");
                            Image img = icon.getImage() ;  
                            Image newimg = img.getScaledInstance( 40, 40,  java.awt.Image.SCALE_SMOOTH );  
                            icon = new ImageIcon(newimg);
                            label.setIcon(icon);
                            label.setText("HORSEMAN\n Health Status \n Damage\n Remaining Troops");
                        }

                        stats_grid.add(label);
                    }
                }
            }//end of for-loop
            
            //Health Status of base
    //      JLabel health_status = new JLabel("\n\nHEALTH STATUS =================");
        //  stats_panel.add(health_status, BorderLayout.SOUTH);
            
        //  JLabel stats_header = new JLabel("\nSELECT TROOP TO DEPLOY");
            //stats_panel.add(stats_header, BorderLayout.NORTH);
            
            stats_panel.add(stats_grid,BorderLayout.CENTER);
                    
            //Panel for chat
            JPanel chat_panel = new JPanel();
            chat_panel.setLayout(new BorderLayout());
        /*  
            //Chat Header
            JLabel chat_header = new JLabel("\n\nCHAT HEADER");
            chat_panel.add(chat_header, BorderLayout.NORTH);
            
            //Chatbox
            JLabel chatbox_later = new JLabel("\n\nCHAT BOX");
            chat_panel.add(chatbox_later, BorderLayout.CENTER);
            
            //Typebox
            JLabel chatbox_type = new JLabel("\n\nCHAT TYPE");
            chat_panel.add(chatbox_type, BorderLayout.SOUTH);
            
            lower_panel.add(stats_panel);
            lower_panel.add(chat_panel); */
            //======================================================== End of LEFT-PANEL
      /*      lower_panel.add(stats_panel);
             gBC.ipady = 10;     //This component has more breadth compared to other buttons
        gBC.weightx = 0.0;
        gBC.gridwidth = 3;
        gBC.gridx = 0;
        gBC.gridy = 1;
        pane.add(lower_panel, gBC);
        gBC.insets = new Insets(0,0,0,0);  //Padding 


       
        //CHAT
        JComboBox jcmbSample = new JComboBox(new String[]{"CHAT", "hi", "hello"});

        gBC.ipady = 0;      
        gBC.weighty = 1.0;   
        gBC.anchor = GridBagConstraints.PAGE_END; 
        gBC.insets = new Insets(0,0,0,0);  //Padding
        gBC.gridx = 1;       
        gBC.gridwidth = 2;  
        gBC.gridy = 2;     
        pane.add(jcmbSample, gBC);   */
    }

    private static void createAndShowGUI() {

        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("GridBagLayout Source Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
