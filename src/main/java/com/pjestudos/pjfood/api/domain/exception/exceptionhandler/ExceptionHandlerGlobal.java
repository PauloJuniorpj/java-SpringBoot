package com.pjestudos.pjfood.api.domain.exception.exceptionhandler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.pjestudos.pjfood.api.domain.exception.EntidadeEmUsoException;
import com.pjestudos.pjfood.api.domain.exception.EntidadeNaoEncontradaException;
import com.pjestudos.pjfood.api.domain.exception.NegocioException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlerGlobal extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        ProblemaTipo problemType = ProblemaTipo.DADOS_INVALIDOS;
        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
        BindingResult bindingResult = ex.getBindingResult();
        List<Problema.ErrosTratar> tratarList = bindingResult.getFieldErrors().stream()
                .map(fieldError -> Problema.ErrosTratar.builder()
                        .name(fieldError.getField())
                        .userMessage(fieldError.getDefaultMessage())
                        .build()).collect(Collectors.toList());
        Problema problem = createProblemaBuilder(status, problemType, detail)
                .userMessage(detail)
                .errosTratars(tratarList)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers, HttpStatus status, WebRequest request) {

        ProblemaTipo problemaTipo = ProblemaTipo.RECURSO_NAO_ENCONTRADO;
        String detail = String.format("O recurso %s, que você tentou acessar, é inexistente.",
                ex.getRequestURL());

        Problema problem = createProblemaBuilder(status, problemaTipo, detail).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        Throwable causaRaiz = ExceptionUtils.getRootCause(ex);
        if(causaRaiz instanceof InvalidFormatException){
            return handleInvalidFormatException((InvalidFormatException) causaRaiz, headers, status, request);
        }
        else if (causaRaiz instanceof PropertyBindingException) {
            return handlePropertyBindingException((PropertyBindingException) causaRaiz, headers, status, request);
        }
        ProblemaTipo problemaTipo = ProblemaTipo.MENSAGEM_INCOMPRENSIVEL;
        String detail = "O corpo da requisição está invalido. Verefique erro de sintaxe";

        Problema problema = createProblemaBuilder(status, problemaTipo, detail).build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemaTipo problemType = ProblemaTipo.ERRO_DE_SISTEMA;
        String detail = "Ocorreu um erro interno inesperado no sistema. "
                + "Tente novamente e se o problema persistir, entre em contato "
                + "com o administrador do sistema.";

        ex.printStackTrace();

        Problema problem = createProblemaBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleTratarEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request){
        //Customizando O erro do corpo
        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemaTipo problemaTipo = ProblemaTipo.RECURSO_NAO_ENCONTRADO;
        String detail = ex.getMessage();

        Problema problema = createProblemaBuilder(status, problemaTipo, detail).build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?>handleTratarNegocioException(NegocioException ex, WebRequest request){
        //Customizando O erro do corpo
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemaTipo problemaTipo = ProblemaTipo.ERRO_NEGOCIO;
        String detail = ex.getMessage();

        Problema problema = createProblemaBuilder(status, problemaTipo, detail).build();
        return handleExceptionInternal(ex, problema, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?>handleTratarEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request){
        //Customizando O erro do corpo
        HttpStatus status = HttpStatus.CONFLICT;
        ProblemaTipo problemaTipo = ProblemaTipo.ENTIDADE_EM_USO;
        String detail = ex.getMessage();

        Problema problema = createProblemaBuilder(status, problemaTipo, detail).build();
        return handleExceptionInternal(ex, problema, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders httpHeaders,
                                                            HttpStatus httpStatus, WebRequest request){
        if(body == null){
            body = Problema.builder()
                    .status(httpStatus.value())
                    .timesTamp(LocalDateTime.parse(LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))))
                    .title(httpStatus.getReasonPhrase()).build();
        } else if(body instanceof String){
            body = Problema.builder()
                    .timesTamp(LocalDateTime.parse(LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))))
                    .status(httpStatus.value())
                    .title((String) body).build();

        }
        return super.handleExceptionInternal(ex, body, httpHeaders, httpStatus, request);
    };

    private Problema.ProblemaBuilder createProblemaBuilder(HttpStatus httpStatus,
                                                           ProblemaTipo problemaTipo, String detail){
        return Problema.builder()
                .status(httpStatus.value())
                .type(problemaTipo.getPath())
                .timesTamp(LocalDateTime.parse(LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))))
                .detail(detail);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
                                                                HttpStatus status, WebRequest request) {
        String path = joinPath(ex.getPath());

        ProblemaTipo problemaTipo = ProblemaTipo.MENSAGEM_INCOMPRENSIVEL;
        String detail = String.format("A prorpiedade '%s' recebeu o valor '%s' ," +
                "que é de um tipo inválido. Corriga e informe um valor compatível com o tipo '%s'," +
                path, ex.getValue(), ex.getTargetType().getSimpleName());

        Problema problema = createProblemaBuilder(status, problemaTipo, detail).build();
        return handleExceptionInternal(ex, problema, headers, status, request );
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {

        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch(
                    (MethodArgumentTypeMismatchException) ex, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        ProblemaTipo problemaTipo = ProblemaTipo.PARAMETRO_INVALIDO;

        String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
                        + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        Problema problema = createProblemaBuilder(status, problemaTipo, detail).build();

        return handleExceptionInternal(ex, problema, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        // Criei o método joinPath para reaproveitar em todos os métodos que precisam
        // concatenar os nomes das propriedades (separando por ".")
        String path = joinPath(ex.getPath());

        ProblemaTipo problemType = ProblemaTipo.MENSAGEM_INCOMPRENSIVEL;
        String detail = String.format("A propriedade '%s' não existe. "
                + "Corrija ou remova essa propriedade e tente novamente.", path);

        Problema problem = createProblemaBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));
    }
}
