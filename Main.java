import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Main {
	public static image_data readimage(String filepath){
		File file =new File(filepath);
		BufferedImage image;
		int width,height;
		try {
			image =ImageIO.read(file);
			width=image.getWidth();
			height=image.getHeight();
			int [][] pixels=new int [width][height];
			int rgp;
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					rgp=image.getRGB(i, j);
					pixels[i][j]=rgp&0xffffff;
					System.out.print(pixels[i][j]+" ");
				}
				System.out.println();
			}
			image_data imagedata=new image_data(width,height,pixels);
			
			return imagedata;
		}
		catch (Exception e) {
			System.err.println(e);
			return null;
		}
		
	}
	public static void code (image_data imagedata,int width ,int height) {
		
		for (int i = 0; i <imagedata.width; i+=imagedata.width/width) {
			for (int j = 0; j <imagedata.height/height; j+=imagedata.height/height) {
				// here
			}
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		image_data imagedata=readimage("1.jpg");
		code(imagedata,4 ,4);
	}
}
