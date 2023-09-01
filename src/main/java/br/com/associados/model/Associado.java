package br.com.associados.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "associado")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Associado {
    
    @Id
    private UUID uuid = UUID.randomUUID();

    private String documento;
    private String tipo_pessoa;
    private String nome;

    
    @Override
    public String toString() {
        return "{" +
            " uuid='" + getUuid() + "'" +
            ", documento='" + getDocumento() + "'" +
            ", tipo_pessoa='" + getTipo_pessoa() + "'" +
            ", nome='" + getNome() + "'" +
            "}";
    }

}
