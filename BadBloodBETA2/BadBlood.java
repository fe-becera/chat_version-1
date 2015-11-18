import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Image;
/**
 * The game client itself!
 * @author Joseph Anthony C. Hermocilla
 *
 */

public class BadBlood extends JPanel implements Runnable, Constants{
	/**
	 * Main window
	 */
	JFrame frame= new JFrame();
	
	/**
	 * Player position, speed etc.
	 */
	int x=10,y=10,xspeed=2,yspeed=2,prevX,prevY;
	
	/**
	 * Game timer, handler receives data from server to update game state
	 */
	Thread t=new Thread(this);
	
	/**
	 * Nice name!
	 */
	String name="";
	
	/**
	 * Player name of others
	 */
	String pname;
	
	/**
	 * Server to connect to
	 */
	String server="localhost";

	/**
	 * Flag to indicate whether this player has connected or not
	 */
	boolean connected=false, connecting=false;
	
	/**
	 * Numbe of troops
	 */
	int archerCount = 0, barbarianCount = 0, horsemanCount = 0;
	JLabel archerCountLabel = new JLabel(Integer.toString(archerCount));
	JLabel barbarianCountLabel = new JLabel(Integer.toString(barbarianCount));
	JLabel horsemanCountLabel = new JLabel(Integer.toString(horsemanCount));
	JButton doneButton = new JButton("DONE");
	JTextField chatField = new JTextField(100);
	DefaultTableModel chatTableModel = new DefaultTableModel(new Object[][]{}, new Object[]{"Chat"});
	JTable chatTable = new JTable(chatTableModel);
	JTextArea chat = new JTextArea();
	JTextPane chatText = new JTextPane();
	JScrollPane chatPane = new JScrollPane(chat);
	
	/**
	 * get a datagram socket
	 */
    DatagramSocket socket = new DatagramSocket();

	
    /**
     * Placeholder for data received from server
     */
	String serverData;
	
	/**
	 * Offscreen image for double buffering, for some
	 * real smooth animation :)
	 */
	//BufferedImage offscreen;

	
	/**
	 * Basic constructor
	 * @param server
	 * @param name
	 * @throws Exception
	 */
	public BadBlood() throws Exception{
		this.server=server;
		this.name=name;
		
		frame.setTitle(APP_NAME+":"+name);
		//set some timeout for the socket
		socket.setSoTimeout(100);
		
		//Some gui stuff i hate.
		//frame.getContentPane().add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1024, 768);
		frame.setVisible(true);
		
		//create the buffer
		//offscreen=(BufferedImage)this.createImage(720, 480);
		
		//Some gui stuff again...
		//frame.addKeyListener(new KeyHandler());		
		//frame.addMouseMotionListener(new MouseMotionHandler());

		//tiime to play
		t.start();		
	}
	
	/**
	 * Helper method for sending data to server
	 * @param msg
	 */
	public void send(String msg){
		try{
			byte[] buf = msg.getBytes();
			InetAddress address = InetAddress.getByName(server);
			DatagramPacket packet = new DatagramPacket(buf, buf.length, address, PORT);
			socket.send(packet);
		}catch(Exception e){}
		
	}
	
	public boolean isTroopComplete(){
		if(archerCount+barbarianCount+horsemanCount == 30)
			return true;
		return false;
	}
	
	public void updateCountLabels(){
		archerCountLabel.setText(Integer.toString(archerCount));
		barbarianCountLabel.setText(Integer.toString(barbarianCount));
		horsemanCountLabel.setText(Integer.toString(horsemanCount));
		frame.getContentPane().repaint();
		frame.revalidate();
	}
	
	/**
	 * The juicy part!
	 */
	public void run(){
		while(true){
			try{
				Thread.sleep(1);
			}catch(Exception ioe){}
						
			//Get the data from players
			byte[] buf = new byte[256];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			try{
			socket.receive(packet);
			}catch(Exception ioe){}
			
			serverData=new String(buf);
			serverData=serverData.trim();
			
			//if (!serverData.equals("")){
			//	System.out.println("Server Data:" +serverData);
			//}

			//Study the following kids.
			if (!connected && serverData.startsWith("CONNECTED")){
				connected=true;
				System.out.println("Connected.");
				
			}else if (!connected && !connecting){
				JLabel titleLabel = new JLabel("BAD BLOOD");
				JButton connectButton = new JButton("CONNECT");
				JTextField nameField = new JTextField(10);
				connectButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						((JButton) e.getSource()).setEnabled(false);
						connecting = true;
					}
				});
				
				frame.setLayout(new GridLayout(4,1));
				frame.getContentPane().add(titleLabel);
				frame.getContentPane().add(nameField);
				frame.getContentPane().add(connectButton);
				frame.getContentPane().repaint();
				frame.revalidate();
				//frame.add(titleLabel);
				//frame.add(nameField);
				//frame.add(connectButton);
				//frame.add(new JPanel());
				while(connectButton.isEnabled()){	
					try{
						Thread.sleep(1);
					}catch(Exception ioe){}
				}
				nameField.setEditable(false);
				name = nameField.getText();
			}else if (!connected && connecting){
				System.out.println("Connecting..");
				System.out.println(name);
				send("CONNECT "+ name);
			}
			else if (connected){
				//offscreen.getGraphics().clearRect(0, 0, 640, 480);
				/*if (serverData.startsWith("PLAYER")){
					String[] playersInfo = serverData.split(":");
					for (int i=0;i<playersInfo.length;i++){
						String[] playerInfo = playersInfo[i].split(" ");
						String pname =playerInfo[1];
						int x = Integer.parseInt(playerInfo[2]);
						int y = Integer.parseInt(playerInfo[3]);
						//draw on the offscreen image
						//offscreen.getGraphics().fillOval(x, y, 20, 20);
						//offscreen.getGraphics().drawString(pname,x-10,y+30);					
					}
					//show the changes
					frame.repaint();
				}*/
				if (serverData.startsWith("SELECT")){
					frame.getContentPane().removeAll();
					frame.getContentPane().repaint();
					frame.revalidate();
					
					
					frame.setLayout(new BorderLayout());
					
					doneButton.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e)
						{
							if(isTroopComplete())
								((JButton) e.getSource()).setEnabled(false);
						}
					});
				
					JButton archerButtonAdd = new JButton("+");
					JButton barbarianButtonAdd = new JButton("+");
					JButton horsemanButtonAdd = new JButton("+");
					JButton archerButtonMinus = new JButton("-");
					JButton barbarianButtonMinus = new JButton("-");
					JButton horsemanButtonMinus = new JButton("-");
					
					archerButtonAdd.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							if(!isTroopComplete()){
								archerCount++;
								updateCountLabels();
							}
					}});
					barbarianButtonAdd.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							if(!isTroopComplete()){
								barbarianCount++;
								updateCountLabels();
							}
					}});
					horsemanButtonAdd.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							if(!isTroopComplete()){
								horsemanCount++;
								updateCountLabels();
							}
					}});
					archerButtonMinus.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							if(archerCount > 0){
								archerCount--;
								updateCountLabels();
							}
					}});
					barbarianButtonMinus.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							if(barbarianCount > 0){
								barbarianCount--;
								updateCountLabels();
							}
					}});
					horsemanButtonMinus.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							if(horsemanCount > 0){
								horsemanCount--;
								updateCountLabels();
							}
					}});
					JPanel centerPanel = new JPanel();
					JPanel archerPanel = new JPanel();
					JPanel barbarianPanel = new JPanel();
					JPanel horsemanPanel = new JPanel();
					JPanel archerBottomPanel = new JPanel();
					JPanel barbarianBottomPanel = new JPanel();
					JPanel horsemanBottomPanel = new JPanel();
					
					JLabel archerMug = new JLabel();
					JLabel barbarianMug = new JLabel();
					JLabel horsemanMug = new JLabel();
					
					archerMug.setIcon(new ImageIcon("archer_mug.png"));
					barbarianMug.setIcon(new ImageIcon("barbarian_mug.png"));
					horsemanMug.setIcon(new ImageIcon("horseman_mug.png"));
					
					archerBottomPanel.setLayout(new GridLayout(1,3));
					barbarianBottomPanel.setLayout(new GridLayout(1,3));
					horsemanBottomPanel.setLayout(new GridLayout(1,3));
					
					archerPanel.setLayout(new BorderLayout());
					archerPanel.add(new JLabel("ARCHERS"), BorderLayout.NORTH);
					archerPanel.add(archerMug, BorderLayout.CENTER);
					archerBottomPanel.add(archerButtonAdd);
					archerBottomPanel.add(archerCountLabel);
					archerBottomPanel.add(archerButtonMinus);
					archerPanel.add(archerBottomPanel, BorderLayout.SOUTH);
					
					barbarianPanel.setLayout(new BorderLayout());
					barbarianPanel.add(new JLabel("BARBARIANS"), BorderLayout.NORTH);
					barbarianPanel.add(barbarianMug, BorderLayout.CENTER);
					barbarianBottomPanel.add(barbarianButtonAdd);
					barbarianBottomPanel.add(barbarianCountLabel);
					barbarianBottomPanel.add(barbarianButtonMinus);
					barbarianPanel.add(barbarianBottomPanel, BorderLayout.SOUTH);
					
					horsemanPanel.setLayout(new BorderLayout());
					horsemanPanel.add(new JLabel("HORSEMEN"), BorderLayout.NORTH);
					horsemanPanel.add(horsemanMug, BorderLayout.CENTER);
					horsemanBottomPanel.add(horsemanButtonAdd);
					horsemanBottomPanel.add(horsemanCountLabel);
					horsemanBottomPanel.add(horsemanButtonMinus);
					horsemanPanel.add(horsemanBottomPanel, BorderLayout.SOUTH);
					
					centerPanel.setLayout(new GridLayout(1,3));
					centerPanel.add(archerPanel);
					centerPanel.add(barbarianPanel);
					centerPanel.add(horsemanPanel);
					
					frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
					frame.getContentPane().add(doneButton, BorderLayout.SOUTH);
					frame.getContentPane().repaint();
					frame.revalidate();
					//frame.getContentPane(BorderLayout.CENTER);
					while(doneButton.isEnabled() || !isTroopComplete()){	
						try{
							Thread.sleep(100);
						}catch(Exception ioe){}
					}
					send("TROOPS "+ name);
				}
				if (serverData.startsWith("START")){
					frame.getContentPane().removeAll();
					frame.getContentPane().repaint();
					frame.revalidate();
					
					frame.setLayout(new GridLayout(2,3));
					JPanel chatPanel = new JPanel();
					Action chatEnter = new AbstractAction(){
						@Override
						public void actionPerformed(ActionEvent e){
							send("MESSAGE "+ name + " : " + chatField.getText());
							chatField.setText("");
						}
					};
					chatPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
					chatPanel.setLayout(new BorderLayout());
					chatPanel.add(chatPane, BorderLayout.CENTER);
					chatPanel.add(chatField, BorderLayout.SOUTH);
					chatField.addActionListener(chatEnter);
					chat.setText("______________________CHATBOX______________________\n");
					chatText.setEditable(false);
					chat.setLineWrap(true);
					chat.setEditable(false);
					frame.getContentPane().add(new JPanel());
					frame.getContentPane().add(new JPanel());
					frame.getContentPane().add(new JPanel());
					frame.getContentPane().add(chatPanel);
				
					frame.getContentPane().add(new JPanel());
					frame.getContentPane().add(new JPanel());
					frame.getContentPane().repaint();
					frame.revalidate();
				}
				if (serverData.startsWith("MESSAGE")){
					/*JTextArea chat = new JTextArea();
					chat.setText(serverData.substring(7));
					chat.setEditable(false);
					chat.setLineWrap(true);
					chatPane.getViewport().add(chat);*/
					/*chatTableModel.addRow(new Object[]{
						serverData.substring(7)
					});*/
					//chatTable.setRowHeight(chatTable.getRowCount()-1, ((int)serverData.substring(7).length()/22 + 1)*14);
					chat.append("\n"+serverData.substring(7)+"\n");
					chat.setCaretPosition(chat.getText().length());
				}
			}			
		}
	}
	
	/**
	 * Repainting method
	 */
	/*public void paintComponent(Graphics g){
		g.drawImage(offscreen, 0, 0, null);
	}*/
	
	
	
	
	class MouseMotionHandler extends MouseMotionAdapter{
		public void mouseMoved(MouseEvent me){
			x=me.getX();y=me.getY();
			if (prevX != x || prevY != y){
				send("PLAYER "+name+" "+x+" "+y);
			}				
		}
	}
	
	class KeyHandler extends KeyAdapter{
		public void keyPressed(KeyEvent ke){
			/*prevX=x;prevY=y;
			switch (ke.getKeyCode()){
			case KeyEvent.VK_DOWN:y+=yspeed;break;
			case KeyEvent.VK_UP:y-=yspeed;break;
			case KeyEvent.VK_LEFT:x-=xspeed;break;
			case KeyEvent.VK_RIGHT:x+=xspeed;break;
			}
			if (prevX != x || prevY != y){
				send("PLAYER "+name+" "+x+" "+y);
			}*/
				
		}
	}
	
	
	public static void main(String args[]) throws Exception{
		//if (args.length != 2){
			//System.out.println("Usage: java -jar badblood <server> <player name>");
		//	System.exit(1);
		//}

		new BadBlood();
	}
}
