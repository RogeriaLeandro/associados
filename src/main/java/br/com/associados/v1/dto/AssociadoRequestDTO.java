package br.com.associados.v1.dto;

import br.com.associados.model.TipoPessoa;
import br.com.associados.utils.RegexUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Builder
// @Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AssociadoRequestDTO {

    @Schema(description = "Documento do associado. CPF ou CNPJ", example = "123.456.789-43")
    @Pattern(regexp = RegexUtil.REGEX_DOCUMENTO, message = "Documento informado inválido")
    @NotBlank(message = "O Documento deve ser informado")
    private String documento;

    @Schema(description = "Tipo pessoa do associado.", example = "PF")
    private TipoPessoa tipoPessoa;

    @Schema(description = "Nome do associado.", example = "Adalberto Rodrigues")
    @NotBlank
    private String nome;
}
