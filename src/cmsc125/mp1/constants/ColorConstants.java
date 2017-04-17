package cmsc125.mp1.constants;

import java.awt.Color;

public class ColorConstants {

	private Color[] colors;
	private static final int MAX_SIZE = 20;

	public ColorConstants() {
		defineColors();
	}

	public void defineColors() {
		setColors(new Color[MAX_SIZE]);

		getColors()[0] = new Color(132, 112, 255);
		getColors()[1] = new Color(255, 228, 196);
		getColors()[2] = new Color(100, 149, 237);
		getColors()[3] = new Color(64, 224, 208);
		getColors()[4] = new Color(0, 255, 127);
		getColors()[5] = new Color(225, 215, 0);
		getColors()[6] = new Color(205, 92, 92);
		getColors()[7] = new Color(210, 180, 140);
		getColors()[8] = new Color(255, 165, 0);
		getColors()[9] = new Color(255, 255, 0);
		getColors()[10] = new Color(240, 128, 128);
		getColors()[11] = new Color(255, 105, 180);
		getColors()[12] = new Color(255, 228, 196);
		getColors()[13] = new Color(238, 130, 238);
		getColors()[14] = new Color(147, 112, 219);
		getColors()[15] = new Color(216, 199, 216);
		getColors()[16] = new Color(233, 150, 122);
		getColors()[17] = new Color(238, 172, 170);
		getColors()[18] = new Color(153, 205, 50);
		getColors()[19] = new Color(176, 196, 222);
	}

	public Color[] getColors() {
		return colors;
	}

	public void setColors(Color[] colors) {
		this.colors = colors;
	}
}
