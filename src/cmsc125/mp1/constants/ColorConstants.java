package cmsc125.mp1.constants;

import java.awt.Color;

public class ColorConstants {

	private static Color[] colors = {
			new Color(255, 228, 196),
			new Color(100, 149, 237),
			new Color(64, 224, 208),
			new Color(0, 255, 127),
			new Color(225, 215, 0),
			new Color(205, 92, 92),
			new Color(210, 180, 140),
			new Color(255, 165, 0),
			new Color(255, 255, 0),
			new Color(240, 128, 128),
			new Color(255, 105, 180),
			new Color(255, 228, 196),
			new Color(238, 130, 238),
			new Color(147, 112, 219),
			new Color(216, 199, 216),
			new Color(233, 150, 122),
			new Color(238, 172, 170),
			new Color(153, 205, 50),
			new Color(176, 196, 222)
	};
	
	public static Color getColor(int index) {
		return colors[index];
	}
}
