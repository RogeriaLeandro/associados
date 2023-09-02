package br.com.associados.resources;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import br.com.associados.services.AssociadoService;
import br.com.associados.utils.AssociadoUtil;

@RestController
@RequestMapping(value = "/associado")
public class AssociadoResource {
    
    @Autowired
    private AssociadoService associadoService;

    @Autowired 
    private AssociadoUtil associadoUtil;

    private static Logger logger = LoggerFactory.getLogger(AssociadoResource.class);


	/*
	 * endpoint listar todos os associados
	 */
    @GetMapping
	public ResponseEntity<List<Associado>> listaAssociados() {
		List<Associado> list = associadoService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{uuid}")
	public ResponseEntity<Associado> associadoporId(@PathVariable UUID id) {
		Associado associado = associadoService.findById(id);
		return ResponseEntity.ok().body(associado);
	}

    @GetMapping(value = "/{documento}")
	public ResponseEntity<Associado> associadoporDocumento(@PathVariable String documento) {
		Associado associado = associadoService.findByDocumento(documento);
		return ResponseEntity.ok().body(associado);
	}

    @PostMapping()
	public ResponseEntity<String> cadastrarAssociado(@RequestBody Associado associado) {
	
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
