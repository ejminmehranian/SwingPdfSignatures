package signPdf;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PushbuttonField;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * Program is used to get signatures in java and insert the 
 * signature inside a pdf form.
 * Steps:
 * 	1.)insert a button into a pdf file using adobe acrobat
 *  2.)use java to change the icon of the button to the signature
 *    captured with java. 
 * jars needed:
 *    itext5 jars 
 *    
 * author: Ejmin Mehranian
 * 
 */
public class SwingPDFSignature  {

  /*
   * clearBtn: to clear the signature
   * sub : to submit the signature
   */
  JButton eraseButton, submit;
  
  //to start recording signature by the user
  getSignature signature;

  //if clicked on a button
  ActionListener actionListener = new ActionListener() {
	
	//get the button id
    public void actionPerformed(ActionEvent e) {
      
      //if button is clear
      if (e.getSource() == eraseButton) {
        
    	  signature.eraseField();
     
     //submiting the signature
      } else {

        //get the signature and save
        signature.getImage();
        
        //to open the pdf file
        PdfReader reader;
        
        /*
         * open the pdf fies and insert the signature inside the button icon
         * 
         */
        try {
          
          reader = new PdfReader("./PdfFiles/originalPdf.pdf");
          
          PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("./PdfFiles/signaturePdf.pdf"));

          AcroFields form = stamper.getAcroFields();
          
          PushbuttonField ad = form.getNewPushbuttonFromField("buttonName");
          
          ad.setLayout(PushbuttonField.LAYOUT_ICON_ONLY);
          
          ad.setProportionalIcon(true);
          
          ad.setImage(Image.getInstance("Pictures/signature.png"));
          
          form.replacePushbuttonField("buttonName", ad.getField());
          
          stamper.close();
          
          reader.close();

          File f;
          
          //get the file path of the signature
          f = new File("./Pictures/signature.png");
          //delete the signature
          f.delete();


        } catch (IOException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        } catch (BadElementException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        } catch (DocumentException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }


      }
    }
  };

  public static void main(String[] args) {
    new SwingPDFSignature().signatureField();
  }

  public void signatureField() {
    
	//frame of the program
    JFrame frame = new JFrame("SwingPdfSignature");
    
    Container content = frame.getContentPane();
    
    content.setLayout(new BorderLayout());
    signature = new getSignature();
    JPanel controls = new JPanel();

    submit = new JButton("Submit");
    submit.addActionListener(actionListener);
    
    eraseButton = new JButton("Erase");
    eraseButton.addActionListener(actionListener);

    controls.add(eraseButton);
    controls.add(submit);

    content.add(controls, BorderLayout.NORTH);
    content.add(signature, BorderLayout.CENTER);

    frame.setSize(1000,1000);
    
    
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    
    frame.setVisible(true);

  }

}
