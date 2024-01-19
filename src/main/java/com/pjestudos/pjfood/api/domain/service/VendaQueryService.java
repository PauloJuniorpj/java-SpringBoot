package com.pjestudos.pjfood.api.domain.service;

import com.pjestudos.pjfood.api.domain.dto.VendaDiaria.VendaDiaria;
import com.pjestudos.pjfood.api.domain.dto.VendaDiaria.VendaDiariaFilter;

import java.util.List;

public interface VendaQueryService {
    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter vendaDiariaFilter, String timeOffSet);
}
