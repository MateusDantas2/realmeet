package br.com.sw2you.realmeet.integration;

import static br.com.sw2you.realmeet.utils.TestUtils.sleep;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.sw2you.realmeet.core.BaseIntegrationTest;
import br.com.sw2you.realmeet.email.EmailSender;
import br.com.sw2you.realmeet.email.model.EmailInfo;
import java.util.List;
import java.util.Map;
import javax.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;

public class SendEmailIntegrationTest extends BaseIntegrationTest {
    private static final String EMAIL_ADDRESS = "mateusdantas.dev@gmail.com";
    //    private static final String EMAIL_TO = "mateusdantas.dev@gmail.com";
    private static final String SUBJECT = "Subject";
    private static final String EMAIL_TEMPLATE = "template-test.html";

    @Autowired
    private EmailSender victim;

    @MockBean
    private JavaMailSender javaMailSender;

    @Mock
    private MimeMessage mimeMessage;

    @Test
    void testSendEmail() {
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        var emailInfo = EmailInfo
            .newBuilder()
            .from(EMAIL_ADDRESS)
            .to(List.of(EMAIL_ADDRESS))
            .subject(SUBJECT)
            .template(EMAIL_TEMPLATE)
            .templateData(Map.of("param", "E-mail enviado com sucesso!"))
            .build();
        victim.send(emailInfo);
        sleep(2000);
        verify(javaMailSender).send(eq(mimeMessage));
    }
}
