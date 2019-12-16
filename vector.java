
public class vector {
	int w, h;
	int[][] v;

	public vector(int w, int h, int[][] p) {
		this.w = w;
		this.h = h;
		v=new int[w][h];
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				this.v[i][j] = p[i][j];
			}
		}
	}


	public void dispaly() {
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				System.out.print(this.v[i][j]+" ");
			}
			System.out.println();
		}
	}
	
}