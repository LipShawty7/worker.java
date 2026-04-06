import com.fasterxml.jackson.databind.JsonNode;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService {

    private static final String EMAIL_USER = System.getenv("EMAIL_USER");
    private static final String EMAIL_PASS = System.getenv("EMAIL_PASS");

    public static void sendEmail(Task task) throws Exception {
        JsonNode payload = task.getPayload();

        if (payload == null) {
            throw new Exception("Payload da tarefa está vazio.");
        }

        String to      = payload.has("to")      ? payload.get("to").asText()      : null;
        String subject = payload.has("subject")  ? payload.get("subject").asText() : "(sem assunto)";
        String body    = payload.has("body")     ? payload.get("body").asText()    : "";

        if (to == null || to.isEmpty()) {
            throw new Exception("Campo 'to' ausente no payload.");
        }

        if (EMAIL_USER == null || EMAIL_PASS == null) {
            throw new Exception("EMAIL_USER ou EMAIL_PASS não configurados.");
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_USER, EMAIL_PASS);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(EMAIL_USER));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
        System.out.println("Email enviado para: " + to);
    }
}
