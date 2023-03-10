package br.com.sw2you.realmeet.service;

import static br.com.sw2you.realmeet.report.enumeration.ReportHandlerType.ALLOCATION;
import static br.com.sw2you.realmeet.util.Constants.REPORT;

import br.com.sw2you.realmeet.report.enumeration.ReportFormat;
import br.com.sw2you.realmeet.report.enumeration.ReportHandlerType;
import br.com.sw2you.realmeet.report.model.AllocationReportData;
import br.com.sw2you.realmeet.report.model.GeneratedReport;
import br.com.sw2you.realmeet.report.resolver.ReportHandlerResolver;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportCreationService {
    private final ReportHandlerResolver reportHandlerResolver;
    private final ReportDispatcherService reportDispatcherService;

    public ReportCreationService(
        ReportHandlerResolver reportHandlerResolver,
        ReportDispatcherService reportDispatcherService
    ) {
        this.reportHandlerResolver = reportHandlerResolver;
        this.reportDispatcherService = reportDispatcherService;
    }

    @Transactional(readOnly = true)
    public void createAllocationReport(LocalDate dateFrom, LocalDate dateTo, String email, String reportFormatStr) {
        var reportData = AllocationReportData.newBuilder().dateFrom(dateFrom).dateTo(dateTo).email(email).build();

        createReport(ALLOCATION, email, reportFormatStr, reportData);
    }

    private void createReport(
        ReportHandlerType reportHandlerType,
        String email,
        String reportFormatStr,
        AllocationReportData reportData
    ) {
        var reportFormat = ReportFormat.fromString(reportFormatStr);
        var reportHandler = reportHandlerResolver.resolveReportHandler(reportHandlerType);
        reportHandler.getReportValidator().validate(reportData);
        var bytes = reportHandler.createReportBytes(reportData, reportFormat);

        reportDispatcherService.dispatch(
            GeneratedReport
                .newBuilder()
                .bytes(bytes)
                .fileName(buildFileName(reportHandlerType, reportFormat))
                .reportFormat(reportFormat)
                .email(email)
                .templateType(reportHandler.getTemplateType())
                .build()
        );
    }

    private String buildFileName(ReportHandlerType reportHandlerType, ReportFormat reportFormat) {
        return REPORT + reportHandlerType.name().toLowerCase() + reportFormat.getExtension();
    }
}
