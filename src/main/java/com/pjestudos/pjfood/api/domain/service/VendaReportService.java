package com.pjestudos.pjfood.api.domain.service;

import com.pjestudos.pjfood.api.domain.dto.VendaDiaria.VendaDiariaFilter;
import net.sf.jasperreports.engine.JRException;

public interface VendaReportService {
    byte[] emitirVendasDiariasRelatorio(VendaDiariaFilter filter, String timeOffset) throws JRException;
}
