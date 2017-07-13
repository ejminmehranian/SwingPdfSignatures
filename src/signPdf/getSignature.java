package signPdf;
 
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.awt.RenderingHints;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseMotionAdapter;
//
//import javax.imageio.ImageIO;
//import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
 

public class getSignature extends JComponent {
   

private static final long serialVersionUID = 1L;

//image which will hold the signature value
  private BufferedImage image;
  
  //used to draw on 
  private Graphics2D graphics;
  
  //mouse cordination to track
  private int currentX, currentY, oldX, oldY;
  
  
  //get the mouse location and saves the values
  public getSignature() {
	  
    setDoubleBuffered(false);
    
    addMouseListener(new MouseAdapter() {
      
    	
      public void mousePressed(MouseEvent e) {
        
        oldX = e.getX();
        oldY = e.getY();
      }
    });
    
    //get the motion of the mouse
    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {

        currentX = e.getX();
        currentY = e.getY();
 
        if (graphics != null) {
          
        	
          graphics.drawLine(oldX, oldY, currentX, currentY);
          
          repaint();
           
          oldX = currentX;
          oldY = currentY;
        }
      }
    });
  }
  
  
  
  protected void paintComponent(Graphics g) {
			
    if (image == null) {
      
    	//how big the signature is
      image = new BufferedImage(120,120,BufferedImage.TYPE_INT_RGB);
      
      //get the design on the field
      graphics = (Graphics2D) image.getGraphics();

      graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

      eraseField();
    }
 
    g.drawImage(image, 0, 0, null);
  }
  
  //clean the signature field
  public void eraseField() {
	  
	    graphics.setPaint(Color.white);
	    
	    graphics.fillRect(0, 0, getSize().width, getSize().height);
	    graphics.setPaint(Color.black);
	    repaint();
	  }
  
  public BufferedImage getImage(){
	
	graphics.drawImage(image, null, 0, 0);
	
	try {
		
		  ImageIO.write(image, "png", new File("./Pictures/signature.png"));
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  return image;
  }
  
  
  public void erase() {
	  
    graphics.setPaint(Color.white);
    
    graphics.fillRect(0, 0, getSize().width, getSize().height);
    graphics.setPaint(Color.black);
    repaint();
  }

  public void black() {
    graphics.setPaint(Color.black);
  }

 
}