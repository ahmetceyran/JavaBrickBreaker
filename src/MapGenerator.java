import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator 
{
	public int map[][];
	public int brickWidth;
	public int brickHeight;
	
	//Mapin olu�turulmas�
	public MapGenerator (int row, int col)
	{
		map = new int[row][col];		
		for(int i = 0; i<map.length; i++)
		{
			for(int j =0; j<map[0].length; j++)
			{
				map[i][j] = 1;
			}			
		}
		//pixel ayarlanmas�
		brickWidth = 540/col;
		brickHeight = 150/row;
	}	
	
	//mapin �iziminin yap�lmas�
	public void draw(Graphics2D g)
	{
		for(int i = 0; i<map.length; i++)
		{
			for(int j =0; j<map[0].length; j++)
			{
				if(map[i][j] > 0)
				{
					//grafik �zelliklerinin ayarlanmas�
					g.setColor(Color.white);
					g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
					
					//bu sadece ayr� bir tu�la g�stermek i�indir, oyun hala onsuz �al��abilir
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.black);
					g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);				
				}
			}
		}
	}
	//Topun haritadaki konumunu bulma
	public void setBrickValue(int value, int row, int col)
	{
		map[row][col] = value;
	}
}
