package code;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingWorker;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import net.miginfocom.swing.MigLayout;

public class MainWindow {

	private JFrame frame;
	private listOfTracks tracks = new listOfTracks();
	private boolean stop;
	private SwingWorker<Void, Void> sw;
	private Thread t;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(184, 134, 11));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[grow,center]", "[100px:n:100px,grow][grow]"));
		
		JPanel johnPanel = new JPanel();

		johnPanel.setBackground(new Color(153, 180, 209));
		frame.getContentPane().add(johnPanel, "cell 0 0,growx,aligny center");
		
		JLabel lblMpPlayer = new JLabel("MP3 PLAYER");
		johnPanel.add(lblMpPlayer);
		
		JButton btnRwd = new JButton("<<");
		johnPanel.add(btnRwd);
		
		JButton btnPlay = new JButton("PLAY");
		btnPlay.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//play song in the background thread
				if(tracks.getCurrentSelectedSong()!=null){
					RunSong rs = new RunSong(tracks.getCurrentSelectedSong());
					t = new Thread(rs);
					t.start();
				}
			

			}
		});
		johnPanel.add(btnPlay);
		JButton btnFwd = new JButton(">>");
		johnPanel.add(btnFwd);
		
		JButton btnStop = new JButton("STOP");
		btnStop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//stop song
				System.out.println("Stopping song");
				t.stop();
				
						
			}
		});	
		
		johnPanel.add(btnStop);

		johnPanel.setBackground(SystemColor.activeCaption);
		frame.getContentPane().add(johnPanel, "cell 0 0,grow");

		JPanel splitPanel = new JPanel();
		frame.getContentPane().add(splitPanel, "cell 0 1,grow");
		splitPanel.setLayout(new MigLayout("", "[grow,left][grow,right]", "[grow,center]"));

		JTabbedPane seanTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		songsSetup(tracks);
		seanTabbedPane.add("Artists", tracks.getArtistPanel());
		seanTabbedPane.add("Songs", tracks.getSongPanel());
		splitPanel.add(seanTabbedPane, "cell 0 0,grow");
		
		JPanel tommiePanel = new JPanel();
		
		
		tommiePanel.add(tracks.getDisplayPanel());
		
		splitPanel.add(tommiePanel, "cell 1 0,grow");
	}
	
	private void songsSetup(listOfTracks tracks){

		tracks.setArtistPanel();
		tracks.setSongPanel();
	}
	public class RunSong implements Runnable{
		
		private String song;
		
		public RunSong(String song) {
			
			this.song = song;
		}
		@Override
		public void run() {
			

			try {
				FileInputStream fis =  new FileInputStream("res\\music\\" 
						+ song + ".mp3");
				AdvancedPlayer player = new AdvancedPlayer(fis);
				player.play();
			} catch (FileNotFoundException | JavaLayerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}
		
	}

}
