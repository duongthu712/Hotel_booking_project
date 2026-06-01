package dal;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailUtil {

    // Email Gmail dùng để gửi reset code
    private static final String FROM_EMAIL = "phuonglinhthcsphuongdien@gmail.com";

    // App Password 16 ký tự của Gmail, không phải mật khẩu Gmail thường
    private static final String APP_PASSWORD = "pnzf biix zhmo zrxt";

    public static void sendResetCode(String toEmail, String code) throws Exception {

        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
            }
        });

        Message message = new MimeMessage(session);

        message.setFrom(new InternetAddress(FROM_EMAIL, "La Mer Hotel"));

        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(toEmail)
        );

        message.setSubject("La Mer Hotel - Password Reset Code");

        String htmlContent =
                "<div style='font-family: Arial, sans-serif; color: #062f35;'>"
                + "<h2>La Mer Hotel Password Reset</h2>"
                + "<p>We received a request to reset your staff account password.</p>"
                + "<p>Your reset code is:</p>"
                + "<h1 style='letter-spacing: 6px; color: #062f35;'>" + code + "</h1>"
                + "<p>This code will expire in <strong>10 minutes</strong>.</p>"
                + "<p>If you did not request this, please ignore this email.</p>"
                + "</div>";

        message.setContent(htmlContent, "text/html; charset=UTF-8");

        Transport.send(message);
    }
}