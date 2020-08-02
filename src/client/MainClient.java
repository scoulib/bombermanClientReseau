package client;


public class MainClient  {


    public static void main(String[] args) {
    	if(args.length == 2) {
			int port = Integer.parseInt(args[1]);
			String serveur = args[0] ;
	

        Client.Login fenetre = new Client.Login();
        fenetre.setVisible(true);
        new Client(serveur,port).communication();
    	}else {
			System.out.println("arguments : serveur port \n");
		}


    }



}