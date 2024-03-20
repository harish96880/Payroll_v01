import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * This class is responsible for sending emails using SMTP protocol.
 */
public class EmailSender {
    private final String username;
    private final String password;
    private final String smtpHost;
    private final int port;

    /**
     * Constructs an EmailSender object with specified parameters.
     * username The username for authentication.
     * password The password for authentication.
     * smtpHost The SMTP host address.
     * port The port number for SMTP server.
     */
    public EmailSender(String username, String password, String smtpHost, int port) {
        this.username = username;
        this.password = password;
        this.smtpHost = smtpHost;
        this.port = port;
    }

    /**
     * Retrieves a mail session object with the specified properties.
     * @return A mail session object.
     */
    public Session getSession() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", port);
        return Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    /**
     * Sends an email message.
     * toEmail The recipient email address.
     * subject The subject of the email.
     * message The MimeMessage object containing the email content.
     * MessagingException If an error occurs during email sending.
     */
    public void sendEmail(String toEmail, String subject, MimeMessage message) throws MessagingException {
        Transport transport = null;
        try {
            Session session = getSession();
            transport = session.getTransport("smtp");
            transport.connect(smtpHost, port, username, password);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(subject);
            // Send the message
            transport.sendMessage(message, message.getAllRecipients());
        } finally {
            if (transport != null) {
                transport.close();
            }
        }
    }
}
