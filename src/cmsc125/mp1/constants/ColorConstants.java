package cmsc125.mp1.constants;

import java.awt.Color;

public class ColorConstants {

	private static Color[] colors = {
			new Color(230, 25, 75),
			new Color(60, 180, 75),
			new Color(255, 225, 25),
			new Color(0, 130, 200),
			new Color(245, 130, 48),
			new Color(145, 30, 180),
			new Color(70, 240, 240),
			new Color(240, 50, 230),
			new Color(210, 245, 60),
			new Color(250, 190, 190),
			new Color(0, 128, 128),
			new Color(0, 0, 0),
			new Color(230, 190, 255),
			new Color(170, 110, 40),
			new Color(255, 250, 200),
			new Color(128, 0, 0),
			new Color(170, 255, 195),
			new Color(128, 128, 0),
			new Color(255, 215, 180),
			new Color(0, 0, 128),
	};
	
	public static java.awt.Color getColor(int index) {
		return colors[index];
	}
	
	public static javafx.scene.paint.Color getColorFX(int index) {
		return new javafx.scene.paint.Color(colors[index].getRed()/255.0, colors[index].getGreen()/255.0, colors[index].getBlue()/255.0, 1.0);
	}
}
