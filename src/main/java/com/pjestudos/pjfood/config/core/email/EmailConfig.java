package com.pjestudos.pjfood.config.core.email;

import com.pjestudos.pjfood.api.domain.service.EnvioEmailService;
import com.pjestudos.pjfood.infrasctructure.email.FakeEnvioEmailService;
import com.pjestudos.pjfood.infrasctructure.email.SandboxEnvioEmailService;
import com.pjestudos.pjfood.infrasctructure.email.SmtpEnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {
    @Autowired
    private EmailProperties emailProperties;

    @Bean
    EnvioEmailService envioEmailService(){
        switch (emailProperties.getImpl()){
            case FAKE:
                return new FakeEnvioEmailService();
            case SMTP:
                return new SmtpEnvioEmailService();
            case SANDBOX:
                return new SandboxEnvioEmailService();
            default:
                return null;
        }
    }
}
