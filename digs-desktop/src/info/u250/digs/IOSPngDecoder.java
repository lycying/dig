package info.u250.digs;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.dd.plist.NSDictionary;
import com.dd.plist.NSNumber;
import com.dd.plist.NSString;
import com.dd.plist.PropertyListParser;

public class IOSPngDecoder {

	public static void main(String[] args) {
		IOSPngDecoder test = new IOSPngDecoder();
		final String fishPrefix = "fish-fury/";
		test.test("birds-hd.plist","birds-hd.png",fishPrefix);
		test.test("zombiebirds-hd.plist","zombiebirds-hd.png",fishPrefix);
		test.test("fish-hd.plist","fish-hd.png",fishPrefix);
		test.test("effects-hd.plist","effects-hd.png",fishPrefix);
		test.test("credits-hd.plist","credits-hd.png",fishPrefix);
		test.test("furymenu-hd.plist","furymenu-hd.png",fishPrefix);
		test.test("hud-hd.plist","hud-hd.png",fishPrefix);
		test.test("level.hills-hd.plist","level.hills-hd.png",fishPrefix);
		test.test("level.ice-hd.plist","level.ice-hd.png",fishPrefix);
		test.test("level.zombie-hd.plist","level.zombie-hd.png",fishPrefix);
		test.test("level.toxic-hd.plist","level.toxic-hd.png",fishPrefix);
		test.test("levelmenu-hd.plist","levelmenu-hd.png",fishPrefix);
		test.test("mainmenu-hd.plist","mainmenu-hd.png",fishPrefix);
		test.test("optionsmenu-hd.plist","optionsmenu-hd.png",fishPrefix);
		test.test("shop-hd.plist","shop-hd.png",fishPrefix);
	}
	public void test(String plist,String png,String prefix) {
		try {
			BufferedImage image;
			try {
				image = ImageIO.read(new File(prefix+"ios/"+png));
			} catch (IOException ex) {
				throw new RuntimeException("Error reading image: ", ex);
			}
			if (image == null)
				throw new RuntimeException("Unable to read image: ");

			File file = new File(prefix+"ios/"+plist);
			NSDictionary rootDict = (NSDictionary) PropertyListParser.parse(file);
			NSDictionary parameters = ((NSDictionary) rootDict.objectForKey("frames"));

			for (String key : parameters.allKeys()) {
				NSDictionary item = (NSDictionary) parameters.objectForKey(key);
				processItem(image, key, item,prefix);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public void processItem(BufferedImage image, String key, NSDictionary item,String prefix)
			throws Exception {
		String frame = ((NSString) item.objectForKey("frame")).toString().replace("{", "").replace("}", "");
		String offset = ((NSString) item.objectForKey("offset")).toString().replace("{", "").replace("}", "");
		boolean rotated = ((NSNumber) item.objectForKey("rotated")).boolValue();

		String[] frames = frame.split(",");


		final int x = Integer.parseInt(frames[0]);
		final int y = Integer.parseInt(frames[1]);
		final int w = Integer.parseInt(frames[2]);
		final int h = Integer.parseInt(frames[3]);


		BufferedImage tmp;
		System.out.println(x + "," + y + "," + (x + w) + "," + (y + h) + "");
		System.out.println(frame);
		System.out.println(offset);
		System.out.println(rotated);
		if (rotated) {
			tmp = getImage_rote270(image.getSubimage(x, y, h, w));
		} else {
			tmp = image.getSubimage(x, y, w, h);
		}
		File file = new File(prefix+"d/"+key);
		if (!file.exists()) {
			file.mkdirs();
		}
		ImageIO.write(tmp, "png", file);
	}
	
	public BufferedImage getImage_rote270(BufferedImage img) {
		int width = img.getWidth();
		int height = img.getHeight();
		int type = img.getColorModel().getTransparency();
		BufferedImage newImg = new BufferedImage(height, width, type);
		Graphics g = newImg.getGraphics();
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.rotate(Math.toRadians(-90), 0, 0);
		g2d.drawImage(img, -width, 0, width, height, null);
		g2d.dispose();
		return newImg;
	}
}
