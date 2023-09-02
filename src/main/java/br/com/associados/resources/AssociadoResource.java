package br.com.associados.resources;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.associados.model.Associado;
import br.com.associados.model.AssociadoDTO;
import br.com.associados.services.AssociadoService;
import br.com.associados.utils.AssociadoUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.Tag;
@Slf4j
// @Tag(name = "associado-resource", description = "API de Associados")
@Validated
@RestController
@RequestMapping(value = "/associado")
public class AssociadoResource {
   
	@Autowired
    private AssociadoService associadoService;
    
	@Autowired
    private AssociadoUtil associadoUtil;

    private static Logger logger = LoggerFactory.getLogger(AssociadoResource.class);

	// @Operation(summary = "Consulta Associados")
	// @ApiResponse(responseCode = "200", description = "Retorno de Lista de Associados")
    @GetMapping
	public ResponseEntity<Page<AssociadoDTO>> listaAssociados(@PageableDefault(size = 10, page = 1) Pageable pageable) {
		//TODO Paginar essa chamada
		// List<Associado> list = associadoService.findAll();
		// return ResponseEntity.ok().body(list);
	}
	
	// @GetMapping(value = "/{uuid}")
	// public ResponseEntity<Associado> associadoporId(@PathVariable UUID uuid, @Pattern(regexp = regex) final UUID uuid) {
	// 	return associadoService.findById(uuid)
	// 		.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent.build());
	// }

    // @GetMapping(value = "/{documento}")
	// public ResponseEntity<Associado> associadoporDocumento(@PathVariable String documento) {
	// 	return associadoService.findByDocumentondById(uuid)
	// 		.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent.build());
	// }

    @PostMapping()
	public ResponseEntity<String> cadastrarAssociado(@RequestBody @NonNull @Valid Associado associado) {
	
        boolean documentoValido = associadoService.cadastrarAssociado(associado);

		if (documentoValido) {
				logger.info("Associado Cadastrado");
				return ResponseEntity.status(HttpStatus.CREATED).body("Associado cadastrado");
		} else {
			if(associado.getTipoPessoa().equals("PF")){
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("CPF do Associado Inválido.");
     	} else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("CNPJ do Associado Inválido.");
            } 
		}
	}

	@PutMapping(value = "/{uuid}")
	public ResponseEntity<String> alteraAssociado(@PathVariable UUID uuid, @RequestBody Associado associado) {
		
		Associado associadoAtual = associadoService.findById(uuid);
		
		if(associadoAtual != null) {
			BeanUtils.copyProperties(associado, associadoAtual, "uuid");
			associadoService.cadastrarAssociado(associadoAtual);
			return ResponseEntity.status(HttpStatus.OK).body("Associado Alterado");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Associado Não Encontrado");
		}
	}
	
	@CrossOrigin("*")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteUsuario(@PathVariable UUID id) {
        //TODO Verificar se Associado possui boletos que ainda não foram pagos (criar Boletos)
        
        associadoService.deleteAssociado(id);
		return ResponseEntity.noContent().build();
	}

}
