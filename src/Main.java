import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		//jframe kütüphanesinden obj nesnesi ve gameplay sýnýfýndan yeni oyun oluþturuluyor
		JFrame obj=new JFrame();
		Gameplay gamePlay = new Gameplay();
		
		//Ekranýn ayarlanmasý
		obj.setBounds(10, 10, 700, 600);
		obj.setTitle("Breakout Ball");		
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gamePlay);
                obj.setVisible(true);
		
	}

}
