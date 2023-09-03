package br.com.associados;

import br.com.associados.integracao.boleto.dto.BoletoDTO;
import br.com.associados.model.TipoPessoa;
import br.com.associados.utils.FormatadorUtil;
import br.com.associados.v1.dto.AssociadoDTO;
import br.com.associados.model.Associado;
import br.com.associados.v1.dto.AssociadoRequestDTO;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static br.com.associados.model.TipoPessoa.PF;
import static br.com.associados.model.TipoPessoa.PJ;

public final class AssociadoTestHelper {

    public static final String CPF = "123.123.123-43";
    public static final String CNPJ = "91.957.952/0001-53";
    public static final String CPF_NAO_FORMATADO = "12312312343";
    public static final String CNPJ_NAO_FORMATADO = "91957952000153";
    public static final String ID_ASSOCIADO = UUID.randomUUID().toString();

    public static List<AssociadoDTO> criarAssociadosDTO() {
        return List.of(
                criarAssociadoDTO(PF), criarAssociadoDTO(PJ)
        );
    }

    public static List<Associado> criarAssociados() {
        return List.of(
                criarAssociado(PJ),
                criarAssociado(PF)
        );
    }

    private static Associado criarAssociado(TipoPessoa tipoPessoa) {
        var associado = new Associado();
        associado.setDocumento(tipoPessoa == PF ? CPF_NAO_FORMATADO : CNPJ_NAO_FORMATADO);
        associado.setUuid(UUID.randomUUID());
        associado.setTipoPessoa(tipoPessoa);
        associado.setNome("NOME");
        return associado;
    }

    public static AssociadoDTO criarAssociadoDTO(TipoPessoa tipoPessoa) {
        return AssociadoDTO.builder()
                .id(UUID.randomUUID().toString())
                .nome("João")
                .documento(tipoPessoa == PF ? CPF : CNPJ)
                .tipoPessoa(tipoPessoa)
                .build();
    }

    public static List<AssociadoDTO> criarAssociadosDTO(List<Associado> associados) {
        return associados.stream().map(associado -> criarAssociadoDTO(associado)).collect(Collectors.toList());
    }

    public static AssociadoDTO criarAssociadoDTO(Associado associado) {
        return AssociadoDTO.builder()
                .id(associado.getUuid().toString())
                .nome(associado.getNome())
                .documento(FormatadorUtil.formatarDocumento(associado.getDocumento()))
                .tipoPessoa(associado.getTipoPessoa())
                .build();
    }

    public static AssociadoDTO criarAssociadoDTO(AssociadoRequestDTO associadoRequest) {
        return AssociadoDTO.builder()
                .id(UUID.randomUUID().toString())
                .nome(associadoRequest.getNome())
                .documento(associadoRequest.getDocumento())
                .tipoPessoa(associadoRequest.getTipoPessoa())
                .build();
    }

    public static AssociadoRequestDTO criarAssociadoRequest(TipoPessoa tipoPessoa) {
        return AssociadoRequestDTO.builder()
                .documento(tipoPessoa == PF ? CPF : CNPJ)
                .nome("João")
                .tipoPessoa(tipoPessoa)
                .build();
    }

    public static Associado criarAssociado(AssociadoRequestDTO associadoRequest) {
        var associado = new Associado();
        associado.setUuid(UUID.randomUUID());
        associado.setDocumento(associadoRequest.getTipoPessoa() == PF ? CPF_NAO_FORMATADO : CNPJ_NAO_FORMATADO);
        associado.setTipoPessoa(associadoRequest.getTipoPessoa());
        associado.setNome(associadoRequest.getNome());
        return associado;
    }

    public static List<BoletoDTO> criarBoletosTodosPagos(String idAssociado) {
        return List.of(
                new BoletoDTO(idAssociado, false),
                new BoletoDTO(idAssociado, false),
                new BoletoDTO(idAssociado, false)
        );
    }

    public static List<BoletoDTO> criarBoletosUmAPagar(String idAssociado) {
        return List.of(
                new BoletoDTO(idAssociado, false),
                new BoletoDTO(idAssociado, true),
                new BoletoDTO(idAssociado, false)
        );
    }

}