package vue;



import client.Client;
import client.MainClient;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ViewCommand {
	private JFrame jFrame;
	private JLabel affichageTurn;
	private JButton[] initChoice;
    private static ViewCommand instance = null;
    private  Client client;

    public static ViewCommand getInstance() {
        if (instance == null)
            instance = new ViewCommand();
        return  instance;
    }
	public ViewCommand() {
		// TODO Auto-generated constructor stub

		jFrame = new JFrame();
		jFrame.setTitle("Game");
		jFrame.setSize(new Dimension(700, 400));
		Dimension windowSize = jFrame.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int dx = centerPoint.x - windowSize.width / 2 ;
		int dy = centerPoint.y - windowSize.height / 2 - 350;
		jFrame.setLocation(dx, dy);
		createView();
		jFrame.setVisible(true);
	}
	 public void init(Client c) {
			client = c;
	 }

		//this.affichageTurn.setText("Turn: "+this.bombermanGame.getTurn());

	
	public void createView() {
		initChoice = new JButton[4];
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(2,1));
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(1,4));
		Icon icon_restart = new ImageIcon("icones/icon_restart.png");
		initChoice[0] = new JButton(icon_restart);
		initChoice[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				//controleurGame.start();
				client.setRequete("init");
				client.envoyerRequete();
				initChoice[0].setEnabled(false);
				initChoice[1].setEnabled(true);
				initChoice[2].setEnabled(true);
			}
		});
		Icon icon_run = new ImageIcon("icones/icon_run.png");
		initChoice[1] = new JButton(icon_run);
		initChoice[1].setEnabled(false);
		initChoice[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				client.setRequete("run");
				client.envoyerRequete();
				initChoice[1].setEnabled(false);
				initChoice[3].setEnabled(true);
			}
		});
		Icon icon_step = new ImageIcon("icones/icon_step.png");
		initChoice[2] = new JButton(icon_step);
		initChoice[2].setEnabled(false);
		initChoice[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				client.setRequete("step");
				client.envoyerRequete();
			}
		});
		
		Icon icon_pause = new ImageIcon("icones/icon_pause.png");
		initChoice[3] = new JButton(icon_pause);
		initChoice[3].setEnabled(false);
		initChoice[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				client.setRequete("pause");
				client.envoyerRequete();
				initChoice[1].setEnabled(true);
				initChoice[3].setEnabled(false);
			}
		});
		panel1.add(panel2);
		for(int i=0;i<4;i++)
			panel2.add(initChoice[i]);
		panel1.add(panel2);
		JPanel panel3 = new JPanel();
		panel3.setLayout(new GridLayout(1,2));
		final JSlider slider = new JSlider(JSlider.HORIZONTAL, 1, 10, 2);
        slider.setMinorTickSpacing(1);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				double value = (double)slider.getValue();
	             //controleurGame.setTime(value*1000);
			}
          });
        panel3.add(slider);
		affichageTurn = new JLabel("Turn: 0");
		panel3.add(affichageTurn);
		affichageTurn.setHorizontalAlignment(JLabel.CENTER);
		affichageTurn.setVerticalAlignment(JLabel.CENTER);
		panel1.add(panel3);	
		jFrame.setContentPane(panel1);
		}

}
