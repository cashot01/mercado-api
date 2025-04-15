package br.com.fiap.mercado_api.controller;

import br.com.fiap.mercado_api.dto.CompraDTO;
import br.com.fiap.mercado_api.dto.TrocaDTO;
import br.com.fiap.mercado_api.dto.VendaDTO;
import br.com.fiap.mercado_api.model.Item;
import br.com.fiap.mercado_api.service.MercadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mercado")
public class MercadoController {

    @Autowired
    private MercadoService mercadoService;

    @PostMapping("/comprar")
    public ResponseEntity<Item> comprarItem(@RequestBody CompraDTO compraDTO) {
        return ResponseEntity.ok(mercadoService.comprarItem(compraDTO));
    }

    @PostMapping("/vender")
    public ResponseEntity<Item> venderItem(@RequestBody VendaDTO vendaDTO) {
        return ResponseEntity.ok(mercadoService.venderItem(vendaDTO));
    }

    @PostMapping("/trocar")
    public ResponseEntity<String> trocarItens(@RequestBody TrocaDTO trocaDTO) {
        return ResponseEntity.ok(mercadoService.trocarItens(trocaDTO));
    }
}
