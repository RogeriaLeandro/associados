package br.com.associados.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;

import lombok.Data;
@Entity
@Table(name = "associado")
@Data
public class Associado {
    
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
	@Column(name = "uuid", updatable = false, unique = true, nullable = false, columnDefinition = "varchar(36)")
	private UUID uuid;

    @Column(name = "documento",  length = 14)
    private String documento;

    @Column(name = "tipoPessoa",  length = 2)
    private String tipoPessoa;

    @Column(name = "nome", nullable = false, length = 50)    
    private String nome;

    
    @Override
    public String toString() {
        return "{" +
            " uuid='" + getUuid() + "'" +
            ", documento='" + getDocumento() + "'" +
            ", tipo_pessoa='" + getTipoPessoa() + "'" +
            ", nome='" + getNome() + "'" +
            "}";
    }

}
