package br.com.associados.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "associado")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Data
public class Associado {

    @Id
    @Column(name = "id", updatable = false, unique = true, nullable = false, columnDefinition = "varchar(36)")
    private String id;

    @Column(name = "documento",  length = 14, nullable = false, unique = true)
    @Size(min = 11, max = 14)
    private String documento;

    @Column(name = "tipoPessoa", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoPessoa tipoPessoa;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

}
