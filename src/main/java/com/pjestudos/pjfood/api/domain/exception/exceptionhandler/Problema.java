package com.pjestudos.pjfood.api.domain.exception.exceptionhandler;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class Problema {

    private Integer status;
    private String type;
    private String title;
    private String detail;

    private String userMessage;
    private LocalDateTime timesTamp;
    private List<ErrosTratar> errosTratars;

    @Getter
    @Builder
    public static class ErrosTratar{
        private String name;
        private String userMessage;
    }


}
