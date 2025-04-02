package br.com.postechfiap.fiap_produto_service.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import jakarta.persistence.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder(toBuilder = true)
@Entity
@Table(name = Produto.TABLE_NAME)
public class Produto extends BaseEntity<Long> {

    public static final String TABLE_NAME = "produto";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "produto_id_seq", allocationSize = 1)
    private Long id;

    @Setter
    @NotBlank(message = "O nome do produto é obrigatório.")
    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true, updatable = false)
    private String sku;

    @Setter
    @NotNull(message = "O preço é obrigatório.")
    @Positive(message = "O preço deve ser maior que zero.")
    @Column(nullable = false)
    private Double preco;

    @PrePersist
    @PreUpdate
    private void gerarSku() {
        if (this.sku == null || this.sku.isBlank()) {
            this.sku = "PROD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
    }
}