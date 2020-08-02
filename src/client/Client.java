package client;

import affichageAPI.PanelBomberman;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import vue.ViewBombermanGame;
import vue.ViewCommand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.*;
import java.net.Socket;
import java.util.Observable;

public class Client extends Observable {
    private static Socket socketClient;
    private static int port = 2345;
    private static OutputStreamWriter sortie;
    private static InputStream inputStream;
    BufferedReader br;
    private static String serveur = "127.0.0.1";
    private static ViewBombermanGame viewBombermanGame;
    private static ViewCommand viewCommand;
    private static PrintWriter so;
    private static PanelBomberman pane;
    private  static boolean first = true;
    private static String requete;


    public Client(String serveur, int port) {
        viewBombermanGame = new ViewBombermanGame(this);
        this.serveur = serveur ;
        this.port = port ;
    }


    public  void envoyerRequete() {
        so.println(requete);
    }

    public  void setRequete(String req) {
        requete = req;
    }

    public  PanelBomberman getPane() {
        return pane;
    }

    public void communication() {
        try {

            socketClient = new Socket(serveur,port);
            inputStream = socketClient.getInputStream();
            InputStreamReader isr = new InputStreamReader(inputStream);
            br = new BufferedReader(isr);
            so = new PrintWriter(socketClient.getOutputStream(),true);

            while (true) {
                String reponseServeur;
                while((reponseServeur = br.readLine()) != null) {
                    System.out.println( "le serveur a envoyé "+reponseServeur);
                    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                    PanelBomberman panel = gson.fromJson(reponseServeur,PanelBomberman.class);
                    pane =  new PanelBomberman(panel.getMap(),this);
                    pane.setInfoGame(panel.getBreakable_walls(),panel.getListInfoAgents(),panel.getListInfoItems(),panel.getListInfoBombs());
                    System.out.println("panel recu "+pane.getListInfoAgents().size());
                    viewCommand = ViewCommand.getInstance();
                    viewCommand.init(this);

                    if(first) {
                        viewBombermanGame.createView();
                        first = false;
                    }else {
                        this.setChanged();
                        this.notifyObservers();
                    }

                }
            }
        } catch (IOException  e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{

            try {
                br.close();so.close();socketClient.close();
                System.out.println("Connection Closed");
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    static class Login  extends JFrame implements ActionListener {
        private JLabel loginLabel, mdpLabel;
        private JTextField loginField;
        private JPasswordField mdpField;
        private JButton validerButton;
        private String login, motDePasse;

        public Login(){
            super();
            this.setTitle("Connexion Bomberman");
            this.setSize(new Dimension(500,200));
            this.setLocationRelativeTo(null);
            this.setResizable(false);


            loginLabel = new JLabel("Login");
            loginField = new JTextField("souley");

            mdpLabel = new JLabel("Mot de Passe");
            mdpField = new JPasswordField("passer");

            validerButton = new JButton("connecter");


            Container contenu = this.getContentPane();
            contenu.setLayout(null);

            contenu.add(loginLabel);
            loginLabel.setBounds(20, 20, 100, 20);

            contenu.add(loginField);
            loginField.setBounds(150, 20, 150, 20);

            contenu.add(mdpLabel);
            mdpLabel.setBounds(22, 55, 100, 20);

            contenu.add(mdpField);
            mdpField.setBounds(150, 55, 150, 20);

            contenu.add(validerButton);
            validerButton.setBounds(175,100 ,125 ,20 );

            validerButton.addActionListener(this);

        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(checkCredentials()) {
                JSONObject json = new JSONObject();
                try {
                    json.put("login", login);
                    json.put("mdp", motDePasse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    PrintWriter so = new PrintWriter(socketClient.getOutputStream(), true);
                    so.println(json.toString());
                    setVisible(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                dispose();
            }
        }

        public boolean checkCredentials() {
            login = loginField.getText();
            motDePasse = String.valueOf(mdpField.getPassword());

            if(login.equals("") || login.equals(null) || login.equals("Entrez votre login svp")){
                this.loginField.setText("Entrez votre login svp");

                this.loginField.addFocusListener(new FocusListener() {

                    @Override
                    public void focusLost(FocusEvent e) {
                    }

                    @Override
                    public void focusGained(FocusEvent e) {
                        loginField.setText("");
                    }
                });

                return false;
            } else 	if(motDePasse.equals("") || motDePasse.equals(null)) {
                JOptionPane.showMessageDialog(null, "Veuillez renseigner votre mdp");

                this.mdpField.addFocusListener(new FocusListener() {

                    @Override
                    public void focusLost(FocusEvent e) {
                    }

                    @Override
                    public void focusGained(FocusEvent e) {
                        mdpField.setText("");
                    }
                });

                return false;
            } else {
                System.out.println(" login:"+login+"           mdp:"+motDePasse);
                return true;
            }
        }
    }
}
