package br.com.sw2you.realmeet.service;

import static br.com.sw2you.realmeet.email.model.Attachment.newBuilder;

import br.com.sw2you.realmeet.email.EmailInfoBuilder;
import br.com.sw2you.realmeet.email.EmailSender;
import br.com.sw2you.realmeet.email.model.Attachment;
import br.com.sw2you.realmeet.report.model.GeneratedReport;
import java.io.ByteArrayInputStream;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReportDispatcherService {
    private final EmailSender emailSender;
    private final EmailInfoBuilder emailInfoBuilder;

    public ReportDispatcherService(EmailSender emailSender, EmailInfoBuilder emailInfoBuilder) {
        this.emailSender = emailSender;
        this.emailInfoBuilder = emailInfoBuilder;
    }

    public void dispatch(GeneratedReport generatedReport) {
        emailSender.send(
            emailInfoBuilder.createEmailInfo(
                generatedReport.getEmail(),
                generatedReport.getTemplateType(),
                null,
                List.of(
                    newBuilder()
                        .fileName(generatedReport.getFileName())
                        .contentType(generatedReport.getReportFormat().getContentType())
                        .inputStream(new ByteArrayInputStream(generatedReport.getBytes()))
                        .build()
                )
            )
        );
    }
}
