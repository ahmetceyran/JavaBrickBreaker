import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		//jframe k�t�phanesinden obj nesnesi ve gameplay s�n�f�ndan yeni oyun olu�turuluyor
		JFrame obj=new JFrame();
		Gameplay gamePlay = new Gameplay();
		
		//Ekran�n ayarlanmas�
		obj.setBounds(10, 10, 700, 600);
		obj.setTitle("Breakout Ball");		
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gamePlay);
                obj.setVisible(true);
		
	}

}
