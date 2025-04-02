package br.com.postechfiap.fiap_produto_service.entities;

import br.com.postechfiap.fiap_produto_service.interfaces.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
@ToString(onlyExplicitlyIncluded = true)
public abstract class BaseEntity<ID> implements Entity<ID> {

    @JsonIgnore
    @CreatedDate
    protected LocalDateTime createdAt;
    @JsonIgnore
    @LastModifiedDate
    protected LocalDateTime updatedAt;
    @JsonIgnore
    protected LocalDateTime deletedTmsp;

    public void delete() {
        setDeletedTmsp(LocalDateTime.now());
    }

}
