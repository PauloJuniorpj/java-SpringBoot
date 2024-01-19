package com.pjestudos.pjfood.api.controller;

import com.pjestudos.pjfood.api.domain.dto.VendaDiaria.VendaDiaria;
import com.pjestudos.pjfood.api.domain.dto.VendaDiaria.VendaDiariaFilter;
import com.pjestudos.pjfood.api.domain.service.VendaQueryService;
import com.pjestudos.pjfood.api.domain.service.VendaReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/relatorios")
public class RelatoriosController {

    @Autowired
    private VendaQueryService vendaQueryService;
    @Autowired
    private VendaReportService vendaReportService;

    @GetMapping(path = "/vendas-diarias")
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter vendaDiariaFilter,
                                                    @RequestParam(required = false, defaultValue = "+00:00") String timeOffSet){
        return vendaQueryService.consultarVendasDiarias(vendaDiariaFilter, timeOffSet);
    }

    @GetMapping(path = "/vendas-diarias-pdf")
    public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter vendaDiariaFilter,
                                                            @RequestParam(required = false, defaultValue = "+00:00")
                                                                    String timeOffSet) throws JRException {

        var bytesPdf = vendaReportService.emitirVendasDiariasRelatorio(vendaDiariaFilter, timeOffSet);
        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");


        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).headers(headers).body(bytesPdf);
    }
}
