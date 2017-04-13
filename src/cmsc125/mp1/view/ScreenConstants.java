package cmsc125.mp1.view;

import java.awt.Toolkit;

public interface ScreenConstants {

    public static final int WIDTH = (int) 
    		Toolkit.getDefaultToolkit().
    		getScreenSize().getWidth();
    public static final int HEIGHT = (int) 
    		Toolkit.getDefaultToolkit().
    		getScreenSize().getHeight();
}
