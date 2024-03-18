package application.services;

import io.github.cdimascio.dotenv.Dotenv;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class EnviarEmailService {

    public static void enviarEmail(String emailDestinatario,String caminhoDoPdf,String assunto,String corpoDoTexto,String nomeDoArquivo){

        // Dados da Conta que vai ENVIAR o Email
        Dotenv dotenv = Dotenv.load();
        String remetente = dotenv.get("emailRemetente");
        String senha = dotenv.get("senha");



        // Configurações do servidor SMTP do Gmail
        String host = "smtp.gmail.com";

        int porta = Integer.parseInt(dotenv.get("porta"));

        // Propriedades do sistema
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", porta);


        // Sessão de email
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remetente, senha);
            }
        });

        try {
            // Criando o Corpo da mensagem
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remetente));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailDestinatario));
            message.setSubject(assunto);

            // Criando o corpo do email
            MimeBodyPart corpoEmail = new MimeBodyPart();
            corpoEmail.setText(corpoDoTexto);

            // Criando o anexo do Email
            MimeBodyPart anexo = new MimeBodyPart();
            String arquivoAnexo = caminhoDoPdf;

            DataSource source = new FileDataSource(arquivoAnexo);

            anexo.setDataHandler(new DataHandler(source));
            anexo.setFileName(nomeDoArquivo);

            // Juntando corpo do email e os anexo
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(corpoEmail);
            multipart.addBodyPart(anexo);

            // Definindo o conteúdo do email
            message.setContent(multipart);

            // Enviando o email
            Transport.send(message);

            System.out.println("Email enviado com sucesso!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

