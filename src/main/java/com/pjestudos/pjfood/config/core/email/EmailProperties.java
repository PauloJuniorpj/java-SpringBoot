package com.pjestudos.pjfood.config.core.email;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;


@Getter
@Setter
@Component
@ConfigurationProperties("pjfood.email")
public class EmailProperties {

    private Implementacao impl = Implementacao.FAKE;
    private Sandbox sandbox = new Sandbox();
    @NonNull
    private String remetente;

    public enum Implementacao {
        SMTP, FAKE, SANDBOX
    }

    @Getter
    @Setter
    public class Sandbox {

        private String destinatario;

    }
}
