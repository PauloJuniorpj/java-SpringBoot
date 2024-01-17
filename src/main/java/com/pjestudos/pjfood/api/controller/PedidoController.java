package com.pjestudos.pjfood.api.controller;

import com.pjestudos.pjfood.api.domain.dto.Pedido.PedidoDto;
import com.pjestudos.pjfood.api.domain.dto.Pedido.PedidoInputDto;
import com.pjestudos.pjfood.api.domain.dto.Pedido.PedidoResmumoDto;
import com.pjestudos.pjfood.api.domain.dto.Restaurante.RestauranteDto;
import com.pjestudos.pjfood.api.domain.exception.EntidadeNaoEncontradaException;
import com.pjestudos.pjfood.api.domain.exception.NegocioException;
import com.pjestudos.pjfood.api.domain.model.Pedido;
import com.pjestudos.pjfood.api.domain.model.Restaurante;
import com.pjestudos.pjfood.api.domain.model.Usuario;
import com.pjestudos.pjfood.api.domain.repository.PedidoRepository;
import com.pjestudos.pjfood.api.domain.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Listar", description = "Listar pedidos")
    @GetMapping
    public List<PedidoResmumoDto> listar() {
        var todosPedidos = pedidoRepository.findAll();
        return toCollectionDtoPedidoResumo(todosPedidos);
    }

    @Operation(summary = "Buscar", description = "buscar um pedido")
    @GetMapping("/{pedidoId}")
    public PedidoDto buscar(@PathVariable Long pedidoId) {
        var pedido = pedidoService.buscarOuFalhar(pedidoId);

        return toDto(pedido);
    }
    @Operation(summary = "Adcionar", description = "Emissao de um novo pedido")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDto adicionar(@Valid @RequestBody PedidoInputDto pedidoInput) {
        try {
            Pedido novoPedido = toDomainObject(pedidoInput);
            // TODO pegar usuário autenticado
            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);
            novoPedido = pedidoService.emitir(novoPedido);
            return toDto(novoPedido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    //Visualização
    public PedidoDto toDto(Pedido pedido){
        return modelMapper.map(pedido, PedidoDto.class);
    }
    public PedidoResmumoDto toDtoResumo(Pedido pedido){
        return modelMapper.map(pedido, PedidoResmumoDto.class);
    }
    //Visualização
    public List<PedidoDto> toCollectionDto(List<Pedido> pedidos){
        return pedidos.stream().map(this::toDto).collect(Collectors.toList());
    }
    public List<PedidoResmumoDto> toCollectionDtoPedidoResumo(List<Pedido> pedidos){
        return pedidos.stream().map(this::toDtoResumo).collect(Collectors.toList());
    }
    public Pedido toDomainObject(PedidoInputDto pedidoInput) {
        return modelMapper.map(pedidoInput, Pedido.class);
    }
    public void copyToDomainObject(PedidoInputDto pedidoInput, Pedido pedido) {
        modelMapper.map(pedidoInput, pedido);
    }
}
