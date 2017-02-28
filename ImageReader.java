//package imagereader;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.*;


public class ImageReader {

	JFrame frame;
	JLabel lbIm1;
	JLabel lbIm2;
	BufferedImage img;
       
	public void showIms(String[] args) throws InterruptedException{
		int width = Integer.parseInt(args[1]);
		int height = Integer.parseInt(args[2]);
                int rate = Integer.parseInt(args[3]);
                double p = 1000/rate;

		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		try { 
        
		       File file = new File(args[0]);
		       InputStream is = new FileInputStream(file);
                       
		        long len = file.length();
                      //  System.out.println("Length of file "+len);
			byte[] bytes = new byte[(int)len];
                        int offset = 0;
			int numRead = 0;
			while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
				offset += numRead;
                                System.out.println("offset= "+offset);
			} 
                        

                frame = new JFrame();
		GridBagLayout gLayout = new GridBagLayout();
		frame.getContentPane().setLayout(gLayout);
		String result = String.format("Video height: %d, width: %d", height, width);
		JLabel lbText1 = new JLabel(result);
		lbText1.setHorizontalAlignment(SwingConstants.CENTER);
                int ind = 0;
                int z =0;
                int q = 0;
                
                while(true)
                {
		for(z = 0; z < len; z = z+height*width*3){
                   ind = z;
                    long startTime = System.currentTimeMillis();
			for(int y = 0; y < height; y++){
				for(int x = 0; x < width; x++){
					byte a = 0;
					byte r = bytes[ind];
					byte g = bytes[ind+height*width];
					byte b = bytes[ind+height*width*2]; 
                                        
					int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
					img.setRGB(x,y,pix);             // color each pixel
					ind++;
                                        
				}
			}
                long endTime = System.currentTimeMillis();
                        
		long diff = endTime - startTime;
		lbIm1 = new JLabel(new ImageIcon(img));
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		frame.getContentPane().add(lbText1, c);		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		frame.getContentPane().add(lbIm1, c);
		frame.pack();
		frame.setVisible(true);
                   
               int ans = (int) (p - diff);
               if(ans < 0){
                   Thread.sleep(0);
               }
               else{
               Thread.sleep(ans);
               }
                             
                }
                ind=0;
                z = 0;
                q++;
                
                }
                        
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
                               
             
		
          
                                 
	public static void main(String[] args) throws InterruptedException {
		ImageReader ren = new ImageReader();
                ren.showIms(args);
         }
                  
	}
