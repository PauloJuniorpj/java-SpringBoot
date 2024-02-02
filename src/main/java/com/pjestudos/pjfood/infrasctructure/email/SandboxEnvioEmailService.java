package com.pjestudos.pjfood.infrasctructure.email;

import com.pjestudos.pjfood.config.core.email.EmailProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class SandboxEnvioEmailService extends SmtpEnvioEmailService {

    @Autowired
    private EmailProperties emailProperties;


    @Override
   protected MimeMessage criarMimeMessage(Mensagem mensagem) throws MessagingException {
       MimeMessage mimeMessage = super.criarMimeMessage(mensagem);

       MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
       helper.setTo(emailProperties.getSandbox().getDestinatario());

       return mimeMessage;
   }
}
