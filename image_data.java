
public class image_data {
	public int width,height;
	public int [][] pixels;
	public image_data(int w,int h,int [][] p) {
		this.width=w;
		this.height=h;
		pixels=new int [width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				this.pixels[i][j]=p[i][j];
			}
		}
	}
	
}
