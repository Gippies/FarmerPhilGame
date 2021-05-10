package patterson;

import java.awt.Image;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Crop extends JFrame {

    Image image;

    public Crop(String file, int x, int y, int w, int h) {
        super();
        ImageIcon icon = new ImageIcon(file);
        image = icon.getImage();
        image = createImage(new FilteredImageSource(image.getSource(),
                new CropImageFilter(x, y, w, h)));
    }

    public Image getImage() {
        return image;
    }

}