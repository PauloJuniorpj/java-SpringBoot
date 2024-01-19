package com.pjestudos.pjfood.api.domain.dto.VendaDiaria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendaDiaria {

    private Date data;
    private Long totalVendas;
    private BigDecimal totalFaturado;
}
