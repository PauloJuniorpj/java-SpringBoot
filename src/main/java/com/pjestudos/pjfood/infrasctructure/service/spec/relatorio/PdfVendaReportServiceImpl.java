package com.pjestudos.pjfood.infrasctructure.service.spec.relatorio;

import com.pjestudos.pjfood.api.domain.dto.VendaDiaria.VendaDiariaFilter;
import com.pjestudos.pjfood.api.domain.service.VendaQueryService;
import com.pjestudos.pjfood.api.domain.service.VendaReportService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;

@Service
public class PdfVendaReportServiceImpl implements VendaReportService {

    @Autowired
    private VendaQueryService vendaQueryService;

    @Override
    public byte[] emitirVendasDiariasRelatorio(VendaDiariaFilter filter, String timeOffset) {
        try {
            var inputStream = this.getClass().getResourceAsStream("/relatorios/vendas-diarias.jasper");

            var parameters = new HashMap<String, Object>();
            parameters.put("REPORT_LOCALE", new Locale("pt", "BR"));
            var dataSource = new JRBeanCollectionDataSource(vendaQueryService.consultarVendasDiarias(filter, timeOffset));

            var jasperPrint = JasperFillManager.fillReport(inputStream, parameters, dataSource);
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new ReportExeption("Não foi possível emitir relatório de vendas diárias", e);
        }
    }
}
