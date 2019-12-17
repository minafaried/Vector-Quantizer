import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Main {
	public static image_data readimage(String filepath) {
		File file = new File(filepath);
		BufferedImage image;
		int width, height;
		try {
			image = ImageIO.read(file);
			width = image.getWidth();
			height = image.getHeight();
			int[][] pixels = new int[width][height];
			int rgp;
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					rgp = image.getRGB(i, j);
					pixels[i][j] = rgp & 0xffffff;
					//System.out.print(pixels[i][j] + " ");
				}
				//System.out.println();
			}
			image_data imagedata = new image_data(width, height, pixels);

			return imagedata;
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}

	}

	public static int[][] incrementvalue(int w, int h, int[][] v) {
		int[][] res = new int[w][h];
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				res[i][j] = v[i][j] + 1;
			}
		}
		return res;
	}

	public static int[][] decrementvalue(int w, int h, int[][] v) {
		int[][] res = new int[w][h];
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				res[i][j] = v[i][j] - 1;
			}
		}
		return res;
	}

	public static boolean notempty(int[][]arr,int w,int h) {
		
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				if(arr[i][j]!=0) {
					return true;
				}
			}
		}
		return false;
	}
	public static ArrayList<vector> code(image_data imagedata, int width, int height, int codebook) {

		// Create Array Of Vectors
		int[][][] vectors = new int[imagedata.width / width * imagedata.height / height][width][height];
		int remwidth=imagedata.width%width;
		int remheigth=imagedata.height%height;
		// Divide into Vectors and fill The Array Of Vectors
		int count = 0;
		for (int i = 0; i < imagedata.width-remwidth; i += width) {
			for (int j = 0; j < imagedata.height-remheigth; j += height) {
				for (int a = 0; a < width; a++) {
					for (int b = 0; b < height; b++) {
						// System.out.println(i+" "+j);
						vectors[count][a][b] = imagedata.pixels[i + a][j + b];
					}
				}
				count++;
			}
		}

		System.out.println("\n\n\n" + count);
		int[][] tempvector = new int[width][height];
		for (int c = 0; c < count; c++) {
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					tempvector[i][j] += vectors[c][i][j];
					// System.out.print(vectors[c][i][j] + " ");
				}
			}
			// System.out.println("\n");
		}

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				tempvector[i][j] /= count;
				 //System.out.print(tempvector[i][j] + " ");
			}
			 //System.out.println("\n");
		}

		// System.out.println();
		ArrayList<vector> Splitvectors = new ArrayList<vector>();
		Splitvectors.add(new vector(width, height, tempvector));
		int splitecount = 1;
		int countaffterfinsh = 0;
		while (true) {

			if (countaffterfinsh == 0) {
				for (int i = 0; i < splitecount; i++) {
					int[][] v = incrementvalue(width, height, Splitvectors.get(i).v);
					Splitvectors.add(new vector(width, height, v));
					v = decrementvalue(width, height, Splitvectors.get(i).v);
					Splitvectors.add(new vector(width, height, v));
				}

				// System.out.println(Splitvectors.size());
				int sp = Splitvectors.size();
				for (int i = 0; i < sp / 3; i++) {
					Splitvectors.remove(0);
				}
			}
//			for (int i = 0; i < Splitvectors.size(); i++) {
//				System.out.println(Splitvectors.get(i).v[0][0]);
//			}
//			System.out.println();
			int[][][][] avragevector = new int[Splitvectors.size()][1000][width][height];
			ArrayList<vector> avrageres = new ArrayList<vector>();
			//System.out.println("Splitvectors.size()"+Splitvectors.size());
			for (int c = 0; c < count; c++) {
				double [] value = new double[Splitvectors.size()];
				int minindex = 0;
				double max = 100000;
				for (int i = 0; i < width; i++) {
					for (int j = 0; j < height; j++) {
						for (int j2 = 0; j2 < Splitvectors.size(); j2++) {
							value[j2] += Math.pow(vectors[c][i][j] - Splitvectors.get(j2).v[i][j], 2);
						}
					}
				}
				// System.out.println();
				for (int i = 0; i < Splitvectors.size(); i++) {
					value[i] =  Math.sqrt(value[i]);
					// System.out.print("value: "+value[i] + " ");
				}
				
				// System.out.println("\n");
				for (int i = 0; i < Splitvectors.size(); i++) {
     					if (value[i] <= max) {
						minindex = i;
						max = value[i];
					}
					// System.out.print("value: "+value[i] + " ");
				}
				//System.out.println("minindex"+minindex);
				for (int t = 0; t < 1000; t++) {
					if (!notempty(avragevector[minindex][t], width, height)) {
						// System.out.println(t);
						
						for (int i = 0; i < width; i++) {
							for (int j = 0; j < height; j++) {
								
								avragevector[minindex][t][i][j] = vectors[c][i][j];
							}
						}
						break;
					}
				}
				// System.out.println("Splitvectors.size()" + Splitvectors.size() +
				// "avragevector" + avragevector.length);

			}
//			for (int i = 0; i < Splitvectors.size(); i++) {
//				for (int j = 0; j < 1000; j++) {
//					if (avragevector[i][j][0][0] != 0) {
//						// System.out.println(t);
//						for (int i1 = 0; i1 < width; i1++) {
//							for (int j1 = 0; j1 < height; j1++) {
//							System.out.print(avragevector[i][j][i1][j1]+" ");
//							}
//						}
//						System.out.println();
//					}
//					//else break;
//				}
//				System.out.println("\n");
//			}
			int[][][] res = new int[Splitvectors.size()][width][height];

			for (int i = 0; i < Splitvectors.size(); i++) {
				//System.out.println(res[i][j2][k]);
				int counter = 0;
				for (int j = 0; j < 1000; j++) {

					if (notempty(avragevector[i][j], width, height)) {
						for (int j2 = 0; j2 < width; j2++) {
							for (int k = 0; k < height; k++) {
								res[i][j2][k] += avragevector[i][j][j2][k];
								
							}
						}
						counter++;

					} 
				}
				if (counter != 0) {

					for (int j2 = 0; j2 < width; j2++) {
						for (int k = 0; k < height; k++) {
							res[i][j2][k] /= counter;
						}
					}


				}
				// System.out.println();

			}
			for (int i1 = 0; i1 < Splitvectors.size(); i1++) {
				avrageres.add(new vector(width, height, res[i1]));
				//System.out.println(res[i1][0][0]);
			}

//			for (int e = 0; e < avrageres.size(); e++) {
//			avrageres.get(e).dispaly();
//		}
			// System.out.println("Splitvectors" + Splitvectors.size() + "avrageres.size()"
			// + avrageres.size());
			Splitvectors.clear();
			for (int i = 0; i < avrageres.size(); i++) {
				Splitvectors.add(new vector(width, height, avrageres.get(i).v));
			}
//			for (int i = 0; i < Splitvectors.size(); i++) {
//				System.out.println(Splitvectors.get(i).v[0][0]);
//			}
//			System.out.println();
			//System.out.println("countaffterfinsh :"+countaffterfinsh);
//			for (int i = 0; i < splitecount ; i++) {
//				Splitvectors.get(i).dispaly();
//			}
			//System.out.println("\n\n");
			avrageres.clear();
			if (Splitvectors.size() >= codebook) {
				if (countaffterfinsh == 2) {
					break;
				} else {
					countaffterfinsh++;
					continue;
				}
			}
			splitecount *= 2;

		}
		return Splitvectors;
	}

//	27 28 
//	35 36 
//	28 29 
//	36 37 
//	26 27 
//	34 35 
//	28 29 
//	36 37 
//	26 27 
//	34 35 
//	29 30 
//	37 38 
//	27 28 
//	35 36 
	public static void main(String[] args) {
		 image_data imagedata = readimage("1.jpg");
		int[][] w = new int[8][8];
		int c = 1;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				w[i][j] = c;
				System.out.print(w[i][j] + " ");
				c++;
			}
			System.out.println();

		}

		image_data d = new image_data(8, 8, w);
		System.out.println("\n");
		ArrayList<vector> finalvectors = code(d,2, 2, 4);
		for (int e = 0; e < finalvectors.size(); e++) {
			finalvectors.get(e).dispaly();
		}
	}
}
