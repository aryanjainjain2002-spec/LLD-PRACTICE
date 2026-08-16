//DIPENDENCY INVERSION PRINCIPAL

//higher level module should not depends on lower level module both should depends on abstraction(interfaces).


//EXAMPLE:
interface EmailClient {
    void sendEmail(String to,String subject,String body);
}

class GmailClientImpl implements EmailClient {

    @Override
    public void sendEmail(String to,String subject,String body){
        System.out.println("Connecting to Gmail SMTP server...");
        System.out.println("Sending email via Gmail to: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
        // ... actual Gmail API interaction logic ...
        System.out.println("Gmail email sent successfully!");
    }
}

class OutlookClientImpl implements EmailClient {
    @Override
    public void sendEmail(String to, String subject , String body){
        System.out.println("Connecting to Outlook Exchange server...");
        System.out.println("Sending email via Outlook to: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
        // ... actual Outlook API interaction logic ...
        System.out.println("Outlook email sent successfully!");
    }
}


class EmailService {
    private EmailClient emailClient ;//Depends on Interface not actual implementation

    public EmailService(EmailClient emailClient){
        this.emailClient = emailClient;
    }

    public void sendWelcomeEmail(String userEmail, String userName) {
        String subject = "Welcome, " + userName + "!";
        String body = "Thanks for signing up to our awesome platform. We're glad to have you!";
        this.emailClient.sendEmail(userEmail, subject, body); // Calls the interface method
    }

    public void sendPasswordResetEmail(String userEmail) {
        String subject = "Your Password Reset Request";
        String body = "Please click the link below to reset your password...";
        this.emailClient.sendEmail(userEmail, subject, body);
    }

}
public class DIP {
    public static void main(String[] args) {
        System.out.println("--- Using Gmail ---");
        EmailService gmailService = new EmailService(new GmailClientImpl());
        gmailService.sendWelcomeEmail("test@example.com", "Alice");

        System.out.println("\n--- Using Outlook ---");
        EmailService outlookService = new EmailService(new OutlookClientImpl());
        outlookService.sendWelcomeEmail("test@example.com", "Alice");
    }
}


