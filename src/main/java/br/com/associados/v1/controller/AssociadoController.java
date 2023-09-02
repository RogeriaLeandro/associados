package br.com.associados.v1.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.websocket.server.PathParam;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.associados.model.Associado;
import br.com.associados.repositories.AssociadoRepository;
import br.com.associados.services.AssociadoService;
import br.com.associados.utils.RegexUtil;
import br.com.associados.v1.dto.AssociadoDTO;
import br.com.associados.v1.dto.AssociadoRequestDTO;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@Slf4j
@Api(tags = "associado-controller")
@SwaggerDefinition(tags = {
        			@Tag(name = "associado-controller", 
		     		description = "API de Operações de Associados")
					})
@Validated
@RestController
@RequestMapping(value = "/v1/associado")
public class AssociadoController {
   
	@Autowired
    private AssociadoService associadoService;

	@Autowired
    private AssociadoRepository associadoRepository;
    
    private static Logger logger = LoggerFactory.getLogger(AssociadoController.class);

	@Operation(summary = "Consulta todos Associados")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lista Associados") })
    @GetMapping
	public ResponseEntity<List<AssociadoDTO>> listaAssociados(
															   @Parameter(description = "Número da Página")
															   @RequestParam(value = "pagina", required = false, defaultValue = "1")
															   @Min(value = 1)
															   final int pagina) {
		return ResponseEntity.ok().body(associadoService.consultaAssociados(pagina));
	}
	


	@Operation(summary = "Consulta um único associado por id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Associado"),
			@ApiResponse(code = 204, message = "Nenhum associado encontrado para o id informado")})
	@GetMapping(value = "/{id}")
	public ResponseEntity<AssociadoDTO> consultarAssociado(@PathVariable @Pattern(regexp = RegexUtil.REGEX_ASSOCIADO_ID, message = "O ID deve ter formato de UUID")
															final String id) {
		return associadoService.consultarAssociado(id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.noContent().build());
	}

	@Operation(summary = "Consulta um único associado por CPF ou CNPJ")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Associado"),
			@ApiResponse(code = 204, message = "Nenhum associado encontrado para o documento informado")})
	@GetMapping(value = "/documento")
	public ResponseEntity<AssociadoDTO> consultarAssociadoPorDocumento(@PathParam(value = "documento") @Pattern(regexp = RegexUtil.REGEX_DOCUMENTO, message = "O documento deve ser um CPF ou CNPJ válido")
																		   final String documento) {
		return associadoService.consultarAssociadoPorDocumento(documento)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.noContent().build());
	}


	@Operation(summary = "Cadastra um associado")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Associado cadastrado", content = @Content(schema = @Schema(implementation = AssociadoRequestDTO.class))),
			@ApiResponse(code = 400, message = "Erro ao cadastrar associado")})
	@PostMapping
	public ResponseEntity<AssociadoDTO> cadastrarAssociado(@RequestBody @Valid AssociadoRequestDTO associadoRequestDTO) {
		return ResponseEntity.status(HttpStatus.CREATED).body(associadoService.cadastrarAssociado(associadoRequestDTO));
	}

	@Operation(summary = "Altera dados de um associado")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Associado alterado", content = @Content(schema = @Schema(implementation = AssociadoRequestDTO.class))),
			@ApiResponse(code = 400, message = "Erro ao cadastrar associado")})
	@PutMapping("/{id}")
	public ResponseEntity<AssociadoDTO> alterarAssociado(@PathVariable @Pattern(regexp = RegexUtil.REGEX_ASSOCIADO_ID, message = "O ID deve ter formato de UUID")
											   final String id, @RequestBody AssociadoRequestDTO associadoRequestDTO) {
		return ResponseEntity.status(HttpStatus.OK).body(associadoService.alterarAssociado(id, associadoRequestDTO));
	}

	
	// @Operation(summary = "Apaga um Associado da base de Associados")
	// @ApiResponses(value = {  @ApiResponse(code = 200, message = "Success"),
    //     @ApiResponse(code = 204, message = "No Content"),
    //     @ApiResponse(code = 400, message = "Bad Request"),
    //     @ApiResponse(code = 401, message = "Unauthorized"),
    //     @ApiResponse(code = 500, message = "Internal Server Error")})
	// @CrossOrigin("*")
	// @DeleteMapping(value = "/{id}")
	// public ResponseEntity<Void> deleteUsuario(@PathVariable UUID id) {
    //     //TODO Verificar se Associado possui boletos que ainda não foram pagos (criar Boletos)
        
    //     associadoService.deleteAssociado(id);
	// 	return ResponseEntity.noContent().build();
	// }

}
