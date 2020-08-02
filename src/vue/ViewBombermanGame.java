package vue;

import affichageAPI.PanelBomberman;
import client.Client;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class ViewBombermanGame implements Observer{
	private JFrame jFrame;
	PanelBomberman affichage;
	private Client client;
	private static ViewBombermanGame instance = null;
	private JPanel conteneur;

	
	public ViewBombermanGame(Client c)

	{
		// TODO Auto-generated constructor stub
		client = c;
		c.addObserver(this);
		jFrame = new JFrame();
		jFrame.setTitle("Game");
		jFrame.setSize(new Dimension(700, 400));
		Dimension windowSize = jFrame.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int dx = centerPoint.x - windowSize.width / 2 ;
		int dy = centerPoint.y - windowSize.height / 2+100;
		jFrame.setLocation(dx, dy);


	}



	public void createView() {
		conteneur = new JPanel();
		conteneur.setLayout(new GridLayout(1,1));
		affichage = client.getPane();
		conteneur.add(affichage);
		jFrame.setContentPane(conteneur);
		jFrame.setFocusable(true);
		jFrame.requestFocusInWindow();
		jFrame.addKeyListener(affichage);
		jFrame.setVisible(true);
		
	}


	@Override
	public void update(Observable observable, Object o) {
				System.out.println("mise à jour du panel");
				PanelBomberman nouv = ((Client) observable).getPane();
				affichage.setInfoGame(nouv.getBreakable_walls(),nouv.getListInfoAgents(),nouv.getListInfoItems(),nouv.getListInfoBombs());
				affichage.repaint();

	}
}
