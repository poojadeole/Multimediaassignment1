//package imagereader3;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.swing.*;


/**
 *
 * @author poojadeole
 */
public class ImageReader3 {
    JFrame frame;
    JLabel lbIm1;
    JLabel lbIm2;
    BufferedImage img;

    /**
     * @param args the command line arguments
     */
            public void showIms1(String[] args) throws InterruptedException{
             int width = 0;
             int height = 0;
          
             String convert = args[2];
             int mode = Integer.parseInt(args[3]);
              

if(convert.equals("HD2SD") && mode == 0){ 
                    width = 960;
                    height = 540;
                    int w2 = 176;
                    int h2 = 144;
                    int[] pixel = new int[width * height];
                     int[][] arr = new int[width][height];
                     int[][] ans = new int[width][height];
                    
	    img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            BufferedImage img2 = new BufferedImage(w2, h2, BufferedImage.TYPE_INT_RGB);

		try { 
        	       File file = new File(args[0]);
		       InputStream is = new FileInputStream(file);
                        long len = file.length();
                        System.out.println("Length of file "+len);
			byte[] bytes = new byte[(int)len];
                      
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
				offset += numRead;
                                System.out.println("offset= "+offset);
			}     
                        
                        int noFrames = bytes.length/(960*540);  //original
                        byte[] finalArr = new byte[176*144*noFrames]; 

                int ind = 0;
                int z =0;
                int k = 0;
                int s = 0;
                int pix=0;
                int indNew =0;
                int zNew = 0;
                
             
		       
                       for(z = 0; z < len; z = z+height*width*3){ 
                           ind = z;
			for(int y = 0; y < height; y++){
				for(int x = 0; x < width; x++){
                                    
					byte a = 0;
					byte r = bytes[ind];
					byte g = bytes[ind+height*width];
					byte b = bytes[ind+height*width*2]; 
					pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
                                        pixel[k] = pix;
					ind++;
                                        k++;
                                        
				}
			}

                        
                        k = 0;
                       Neighbor nei = new Neighbor();
                       finalArr = nei.nearestneighbor(width, height, w2, h2,pixel,indNew,zNew,finalArr);
                       zNew = zNew+h2*w2*3;
            
                       
                       }
                       
                       File fileOutput = new File(args[1]);
			OutputStream outputStream =  new FileOutputStream(fileOutput);
			outputStream.flush();
                       outputStream.write(finalArr);	
                       outputStream.close();
                       
                       ind  = 0;
                       z = 0;

		} 
                 catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
              
     }
     if(convert.equals("HD2SD") && mode == 1){ 
                    width = 960;
                    height = 540;
                    int w2 = 176;
                    int h2 = 144;
                    int[] pixel = new int[width * height];
                    int[] resizepixel = new int[w2*h2];
                     int[][] arr = new int[width][height];
                     int[][] ans = new int[width][height];
                    
	    img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            BufferedImage img2 = new BufferedImage(w2, h2, BufferedImage.TYPE_INT_RGB);

		try { 
       
		       File file = new File(args[0]);
		       InputStream is = new FileInputStream(file);
                      		        long len = file.length();
                       
			byte[] bytes = new byte[(int)len];
                      	int offset = 0;
			int numRead = 0;
			while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
				offset += numRead;
                               
			}                       
                    int noFrames = bytes.length/(960*540);  //original
                        byte[] finalArr = new byte[176*144*noFrames];
//                
                int ind = 0;
                int z =0;
                int k = 0;
                int s = 0;
                int pix=0;
                int zNew =0;
                int indNew = 0;
                
             
		        
                       for(z = 0; z < len; z = z+height*width*3){ 
                           ind = z;
			for(int y = 0; y < height; y++){
				for(int x = 0; x < width; x++){
                                    
					byte a = 0;
					byte r = bytes[ind];
					byte g = bytes[ind+height*width];
					byte b = bytes[ind+height*width*2]; 
					pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
                                        img.setRGB(x, y, pix);
					ind++;
                                        
				}
			}
               for(int g = 0; g < height; g++){
                 for(int h = 0; h < width;h++){
                        
                        arr[h][g] = img.getRGB(h, g);
                          
                    }    
                } 
                        AvgImage im = new AvgImage();
                        ans = im.smoothBinaryImage(arr);
                        
                        for(int f = 0; f < arr[0].length; f++){
                            for(int e = 0; e < arr.length ;e++){
                                img.setRGB(e, f, arr[e][f]);
                                pixel[k] = arr[e][f];
                                k++;
                            }
                         }
                        
                        
                        k = 0;
                        
                       
                        Neighbor nei = new Neighbor();
                       finalArr = nei.nearestneighbor(width, height, w2, h2,pixel,indNew,zNew,finalArr);
                       zNew = zNew+h2*w2*3;

          
                       }
                       ind  = 0;
                       z = 0;
                       File fileOutput = new File(args[1]);
			OutputStream outputStream =  new FileOutputStream(fileOutput);
			outputStream.flush();
                       outputStream.write(finalArr);	
                       outputStream.close();
                       
		} 
                 catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
     }
     
     
     if(convert.equals("SD2HD") && mode == 1){
                    width = 176;
                    height = 144;
                    int w2 = 960;
                    int h2 = 540;
                    int[] pixel = new int[width * height];
                    int[] resizepixel = new int[w2*h2];
                    int[][] arr = new int[w2][h2];
                    int[][] ans = new int[w2][h2];
                    
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            BufferedImage img2 = new BufferedImage(w2, h2, BufferedImage.TYPE_INT_RGB);

		try { 
       
		       File file = new File(args[0]);
		       InputStream is = new FileInputStream(file);
                       long len = file.length();
                        System.out.println("Length of file "+len);
			byte[] bytes = new byte[(int)len];
                       
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
				offset += numRead;
                                System.out.println("offset= "+offset);
			}  
                        
                        int noFrames = bytes.length/(176*144);  //original
                        byte[] finalArr = new byte[960*540*noFrames]; 
                int ind = 0;
                int z =0;
                int k = 0;
                int s = 0;
                int pix=0;
             //   Image tagimage;
             int indNew = 0;
             int zNew = 0;
             
		        
                        
                       for(z = 0; z < len; z = z+height*width*3){ 
                           ind = z;
			for(int y = 0; y < height; y++){
				for(int x = 0; x < width; x++){
                                    
					byte a = 0;
					byte r = bytes[ind];
					byte g = bytes[ind+height*width];
					byte b = bytes[ind+height*width*2]; 
					pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
                                        pixel[k] = pix ;
			                ind++;
                                        k++;
				}
			}
                        k =0;
                        
                        
                        System.out.println("pixel length"+pixel.length);
                        System.out.println("k value" + k);
                       // int[] temp = new int[w2*h2] ;
                        double x_ratio = width/(double)w2 ;
                        double y_ratio = height/(double)h2 ;
                        double px, py; 
                        for (int i=0;i<h2;i++) {
                            for (int j=0;j<w2;j++) {
                                px = Math.floor(j*x_ratio) ;
                                    py = Math.floor(i*y_ratio) ;
                                    resizepixel[(i*w2)+j] = pixel[(int)((py*width)+px)] ;
        }
                               } 

               for(int g = 0; g < h2; g++){
                 for(int h = 0; h < w2;h++){
                        img2.setRGB(h, g, resizepixel[s]);
                        arr[h][g] = img2.getRGB(h, g);
                        s++;  
                    }
                } 
               
                AvgImage im = new AvgImage();
                ans = im.smoothBinaryImage(arr);
                
          
                indNew = zNew;
                for(int f = 0; f < ans[0].length; f++){
                 for(int e = 0; e < ans.length ;e++){
                    
                     Color c1 = new Color(ans[e][f]);
                                    int alpha = c1.getAlpha();
                                    int red = c1.getRed();
                                    int green = c1.getGreen();
                                    int blue = c1.getBlue();
                                    
                                    finalArr[indNew] = (byte)red;
                                    finalArr[indNew+h2*w2] = (byte) green;
                                    finalArr[indNew+h2*w2*2] = (byte) blue;
                                    indNew++;
                 }
                }
                zNew = zNew+h2*w2*3;
                
                s = 0;             
               
        
                       }
                       ind  = 0;
                       z = 0; 
                     
                        File fileOutput = new File(args[1]);
			OutputStream outputStream =  new FileOutputStream(fileOutput);
			outputStream.flush();

                       outputStream.write(finalArr);	
                       outputStream.close();
                       
                 

		} 
                 catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
     }   
          if(convert.equals("SD2HD") && mode == 0){
                    width = 176;
                    height = 144;
                    int w2 = 960;
                    int h2 = 540;
                    int[] pixel = new int[width * height];
                    byte[] pixelByte = new byte[width * height];
                    int[] resizepixel = new int[w2*h2];
                    byte[] resizepixelByte = new byte[w2*h2];
                    int[][] arr = new int[w2][h2];
                    int[][] ans = new int[w2][h2];
                    int indexx =0;
                    
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            BufferedImage img2 = new BufferedImage(w2, h2, BufferedImage.TYPE_INT_RGB);

		try { 
        //            int max = Integer.MAX_VALUE;
		       File file = new File(args[0]);
		       InputStream is = new FileInputStream(file);
                       
                       //for(int i = 0;i < 10; i++){
		        long len = file.length();
			byte[] bytes = new byte[(int)len];
                       // System.out.println(bytes.length);

			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
				offset += numRead;
                              
			}                       
                        int noFrames = bytes.length/(176*144);  //original
                        byte[] finalArr = new byte[960*540*noFrames]; //converted

                int ind = 0;
                int indNew = 0;
                int z =0;
                int zNew=0;
                int k = 0;
                int s = 0;
                int pix=0;
                
                
             
                       for(z = 0; z < len; z = z+height*width*3){ 
                           ind = z;
			for(int y = 0; y < height; y++){
				for(int x = 0; x < width; x++){
                                    
					byte a = 0;
					byte r = bytes[ind];
					byte g = bytes[ind+height*width];
					byte b = bytes[ind+height*width*2]; 
                                        
					pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
                                       
                                        pixel[k] = pix ;
                                        pixelByte[k] = (byte) pix; 
					//img.setRGB(x,y,pix);             // color each pixel
					ind++;
                                        k++;
				}
			}
                        k =0;
                        
                        
                       
                       
                       Neighbor nei = new Neighbor();
                       finalArr = nei.nearestneighbor(width, height, w2, h2,pixel,indNew,zNew,finalArr);
                       zNew = zNew+h2*w2*3;

                       }
                       
                        File fileOutput = new File(args[1]);
			OutputStream outputStream =  new FileOutputStream(fileOutput);
			outputStream.flush();
                        outputStream.write(finalArr);	
                        outputStream.close();
                        System.out.println("Write done");
                       ind  = 0;
                       z = 0; 
                       
               

		} 
                 catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
     }
     
	}
    
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
                ImageReader3 ren = new ImageReader3();
                ren.showIms1(args);
	}
    }
    

class AvgImage {

    public int[][] smoothBinaryImage(int[][] arr) {
        double average;
        int row = arr.length;        //height
        int col = arr[0].length;    //width
        double sum;

        int[][] new_array = new int[row][col];
        for (int i = 1; i < col - 2; i++) {
            for (int j = 1; j < row - 2; j++) {

                Color c1 = new Color(arr[j][i]);
                
                int r1 = c1.getRed();
                int g1 = c1.getGreen();
                int b1 = c1.getBlue();
                
                Color c2 = new Color(arr[j-1][i-1]);
                int r2 = c2.getRed();
                int g2 = c2.getGreen();
                int b2 = c2.getBlue();
                
                Color c3 = new Color(arr[j-1][i]);
                int r3 = c3.getRed();
                int g3 = c3.getGreen();
                int b3 = c3.getBlue();
                
                Color c4 = new Color(arr[j-1][i+1]);
                int r4 = c4.getRed();
                int g4 = c4.getGreen();
                int b4 = c4.getBlue();
                
                Color c5 = new Color(arr[j][i+1]);
                int r5 = c2.getRed();
                int g5 = c2.getGreen();
                int b5 = c2.getBlue();
                
                Color c6 = new Color(arr[j+1][i+1]);
                int r6 = c6.getRed();
                int g6 = c6.getGreen();
                int b6 = c6.getBlue();
                
                Color c7 = new Color(arr[j+1][i]);
                int r7 = c7.getRed();
                int g7 = c7.getGreen();
                int b7 = c7.getBlue();
                
                Color c8 = new Color(arr[j+1][i-1]);
                int r8 = c8.getRed();
                int g8 = c8.getGreen();
                int b8 = c8.getBlue();
                
                Color c9 = new Color(arr[j][i-1]);
                int r9 = c9.getRed();
                int g9 = c9.getGreen();
                int b9 = c9.getBlue();
                int final_r = (r1 + r2 + r3 + r4 + r5 + r6 + r7 + r8 + r9) / 9;
                int final_b = (b1 + b2 + b3 + b4 + b5 + b6 + b7 + b8 + b9) / 9;
                int final_g = (g1 + g2 + g3 + g4 + g5 + g6 + g7 + g8 + g9) / 9;
                Color c = new Color(final_r, final_g, final_b);
                
                final_r =(final_r << 16) & 0x00FF0000;
                final_g =(final_g << 8) & 0x0000FF00;;
                final_b = final_b & 0x000000FF;
                
                int pix = 0xFF000000 | final_r | final_g | final_b;
                new_array[j][i] = pix;
 
            }
        }
        return new_array;
    }

}

class Neighbor{
    public byte[] nearestneighbor(int w1,int h1,int w2,int h2, int[] pixel, int indNew, int zNew,byte[] finalArr){
         double x_ratio = w1/(double)w2 ;
         double y_ratio = h1/(double)h2 ;
         double px, py; 
         indNew = zNew;
         for (int i=0;i<h2;i++) {
                            for (int j=0;j<w2;j++) {
                                px = Math.floor(j*x_ratio) ;
                                    py = Math.floor(i*y_ratio) ;
                                    Color c1 = new Color(pixel[(int)((py*w1)+px)]);
                                    int alpha = c1.getAlpha();
                                    int red = c1.getRed();
                                    int green = c1.getGreen();
                                    int blue = c1.getBlue();
                                    finalArr[indNew] = (byte)red;
                                    finalArr[indNew+h2*w2] = (byte) green;
                                    finalArr[indNew+h2*w2*2] = (byte) blue;
                                    indNew++;                
                            }
                     }
         
         
         return finalArr;
                        
    }
}