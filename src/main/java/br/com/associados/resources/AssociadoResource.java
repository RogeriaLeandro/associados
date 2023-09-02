package br.com.associados.resources;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import br.com.associados.repositories.AssociadoRepository;
import br.com.associados.services.AssociadoService;
import br.com.associados.utils.AssociadoUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import io.swagger.v3.oas.annotations.Operation;
@Slf4j
@Api(tags = "associado-resource")
@SwaggerDefinition(tags = {
        			@Tag(name = "associado-resource", 
		     		description = "API de Associados")
					})
@Validated
@RestController
@RequestMapping(value = "/associado")
public class AssociadoResource {
   
	@Autowired
    private AssociadoService associadoService;

	@Autowired
    private AssociadoRepository associadoRepository;
    
	@Autowired
    private AssociadoUtil associadoUtil;

    private static Logger logger = LoggerFactory.getLogger(AssociadoResource.class);

	@Operation(summary = "Consulta Associados")
	@ApiResponses(value = {  @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 500, message = "Internal Server Error")})
    @GetMapping
	public Page<AssociadoDTO> listaAssociados(@PageableDefault(size = 10, page = 1) Pageable pageable) {
		return null;
		//TODO Paginar essa chamada
		// List<Associado> list = associadoService.findAll();
		// return ResponseEntity.ok().body(list);
	}
	

	@Operation(summary = "Consulta Associado por UUID")
	@ApiResponses(value = {  @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 500, message = "Internal Server Error")})
	@GetMapping(value = "/{uuid}")
	// public ResponseEntity<Associado> associadoporId(@PathVariable UUID uuid, @Pattern(regexp = regex) final UUID uuid) {
	public ResponseEntity<Associado> associadoporId(@PathVariable UUID uuid) {
		return null;
		// return associadoService.findById(uuid)
		// 	.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent.build());
	}

	@Operation(summary = "Consulta Associado por Número de Documento")
	@ApiResponses(value = {  @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 500, message = "Internal Server Error")})
    @GetMapping(value = "/{documento}")
	public ResponseEntity<Associado> associadoporDocumento(@PathVariable String documento) {
		return null;
		// return associadoService.findByDocumentoByDocumento(documento)
		// 	.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent.build());
	}

	@Operation(summary = "Cadastra um Novo Associado")
	@ApiResponses(value = {  @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 500, message = "Internal Server Error")})
    @PostMapping()
	public ResponseEntity<String> cadastrarAssociado(@RequestBody @NonNull @Validated Associado associado) {
	
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

	@Operation(summary = "Altera Informações de um Associado")
	@ApiResponses(value = {  @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 500, message = "Internal Server Error")})
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
	
	@Operation(summary = "Apaga um Associado da base de Associados")
	@ApiResponses(value = {  @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 500, message = "Internal Server Error")})
	@CrossOrigin("*")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteUsuario(@PathVariable UUID id) {
        //TODO Verificar se Associado possui boletos que ainda não foram pagos (criar Boletos)
        
        associadoService.deleteAssociado(id);
		return ResponseEntity.noContent().build();
	}

}
