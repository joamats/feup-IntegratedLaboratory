import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

public class SendEmail{
	
	public static void send(String reciever, String messageBody) throws Exception{

        final String emailId = "ocularcontrol@gmail.com"; //sender's email address 
        final String password = "lieb2020";      //provide your password here
        
        Properties pr = new Properties();
         String host = "smtp.gmail.com";
         pr.put("mail.smtp.auth","true");    //for username and password authentication
         pr.put("mail.smtp.starttls.enable","true");
         pr.put("mail.smtp.host",host);  //here host is gmail.com 
         pr.put("mail.smtp.port","587");             //port no.
        
        Session gs = Session.getInstance(pr, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(emailId,password);  //pass your email id and password here
         
            }
        });
        
        Message ms = messageContent(gs,emailId,reciever, messageBody);   
    }
    
       private static Message messageContent(Session gs, String emailId, String reciever, String messageBody) throws Exception {
           try{
           
           Message msg = new MimeMessage(gs);
           msg.setFrom(new InternetAddress(emailId));
           msg.setRecipient(Message.RecipientType.TO,new InternetAddress(reciever));
           msg.setSubject("Ocular Control"); //email's subject 
           msg.setText("Message of email");
         BodyPart messageBodyPart = new MimeBodyPart();
         messageBodyPart.setText(messageBody);
         Multipart multipart = new MimeMultipart();
         multipart.addBodyPart(messageBodyPart);;
         
         // Send the complete message 
         msg.setContent(multipart);
           Transport.send(msg);
           return msg;
           
           }catch(MessagingException e)
           {
               System.out.println(e);
           }
           
           return null;
            
       }
}