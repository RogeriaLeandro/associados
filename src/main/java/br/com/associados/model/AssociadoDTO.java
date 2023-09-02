package br.com.associados.model;

 import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.beans.BeanUtils;

import br.com.associados.utils.RegexUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AssociadoDTO {

    @Schema(description = "Identificador único do associado", example = "afffaf58-62a9-4808-98c7-e5d1a7d4b1c2")
    @NotBlank
    private String id;

    @Schema(description = "Documento do associado. CPF ou CNPJ", example = "123.456.789-43")
    @Pattern(regexp = RegexUtil.REGEX_DOCUMENTO)
    @NotBlank
    private String documento;

    @Schema(description = "Tipo pessoa do associado.", example = "PF")
    private TipoPessoa tipoPessoa;

    @Schema(description = "Nome do associado.", example = "Adalberto Rodrigues")
    @NotBlank
    private String nome;

}
