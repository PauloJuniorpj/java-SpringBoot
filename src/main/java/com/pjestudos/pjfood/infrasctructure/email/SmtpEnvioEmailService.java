package com.pjestudos.pjfood.infrasctructure.email;

import ch.qos.logback.classic.Logger;
import com.pjestudos.pjfood.api.domain.service.EnvioEmailService;
import com.pjestudos.pjfood.config.core.email.EmailProperties;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;


public class SmtpEnvioEmailService implements EnvioEmailService {

    @Autowired(required = true)
    private Configuration freemakerConfig;

    @Autowired
    private JavaMailSender javaMailSender;

    //@Value("{$pjfood.email.remetente}")
    //private String remetente;

    @Autowired
    private EmailProperties remetente;

    @Override
    public void enviar(Mensagem mensagem) {

        try {
            MimeMessage mimeMessage = criarMimeMessage(mensagem);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException("Não foi possivel enviar o email", e);
        }
    }

    protected MimeMessage criarMimeMessage(Mensagem mensagem) throws MessagingException {
        var corpo = processarTemplate(mensagem);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();


        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setFrom(remetente.getRemetente());
        // Convertendo a lista de destinatários para um array de strings
        String[] destinatariosArray = mensagem.getDestinatarios().toArray(new String[0]);

        // Configurando os destinatários
        helper.setTo(destinatariosArray);
        helper.setSubject(mensagem.getAssunto());
        helper.setText(corpo, true);
        return mimeMessage;
    }
    protected String processarTemplate(Mensagem mensagem){
        try {
            Template template = freemakerConfig.getTemplate(mensagem.getCorpo());

            return FreeMarkerTemplateUtils.processTemplateIntoString(template,mensagem.getVariaveis());
        } catch (Exception e) {
            throw new EmailException("Não foi possivel montar o template do email", e);
        }
    }
}
