package br.com.fiap.ms_pagamento.repository;

import br.com.fiap.ms_pagamento.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
