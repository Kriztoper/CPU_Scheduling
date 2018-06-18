package cmsc125.mp1.constants;

import java.awt.Color;

public class ColorConstants {

	private static Color[] colors = {
			new Color(231, 76, 60),		// P0
			new Color(155, 89, 182),	// P1
			new Color(127, 140, 141),	// P2
			new Color(165, 198, 59),	// P3
			new Color(230, 126, 34),	// P4
			new Color(58, 111, 129),	// P5
			new Color(52, 95, 65),		// P6
			new Color(244, 124, 195),	// P7
			new Color(255, 205, 2),		// P8
			new Color(52, 152, 219),	// P9
			new Color(116, 94, 197),	// P10
			new Color(121, 48, 42),		// P11
			new Color(213, 194, 149),	// P12
			new Color(46, 204, 113),	// P13
			new Color(94, 52, 94),		// P14
			new Color(22, 160, 133),	// P15
			new Color(153, 171, 213),	// P16
			new Color(217, 84, 89),		// P17
			new Color(52, 73, 94),		// P18
			new Color(57, 76, 129)		// P19
	};

	public static java.awt.Color getColor(int index) {
		return colors[index];
	}

	public static javafx.scene.paint.Color getColorFX(int index) {
		return new javafx.scene.paint.Color(colors[index].getRed()/255.0, colors[index].getGreen()/255.0, colors[index].getBlue()/255.0, 1.0);
	}
}
