package com.github.Atu141.ms_pedido.controller;

import com.github.Atu141.ms_pedido.dto.PedidoDTO;
import com.github.Atu141.ms_pedido.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService service;

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> findAllPedidos(){
        List<PedidoDTO> list = service.getAllPedidos();
        return ResponseEntity.ok(list);
    }
}
