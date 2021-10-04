 package introducao_springboot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import introducao_springboot.model.Usuario;
import introducao_springboot.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {

	@Autowired /* CDI - Injeção de Dependência */
	private UsuarioRepository usuarioRepository;
	
	/**
     *
     * @param name the name to greet
     * @return greeting text
     */
    @RequestMapping(value = "/mostarnome/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Olá, " + name + "!";
    }
    
    @RequestMapping(value = "/olamundo/{nome}")
    @ResponseStatus(HttpStatus.OK)
    public String retornarOlaMundo(@PathVariable String nome) {
    	
    	Usuario usuario = new Usuario();
    	usuario.setNome(nome);
    	
    	usuarioRepository.save(usuario);
    	
    	return "Olá, Mundo: " + nome;
    }
    
    @GetMapping(value = "listartodos")
    @ResponseBody /* Retornar os dados para o corpo da resposta */
    public ResponseEntity<List<Usuario>> listarUsuario(){
    	
    	List<Usuario> usuarios = usuarioRepository.findAll();
    	
    	return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK); /* Retorna a lista em Json */
    }

    @PostMapping(value = "salvar") /* Mapeia a URL */
    @ResponseBody
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario){ /* Recebe os dados para salvar */
    	
    	Usuario user = usuarioRepository.save(usuario);
    
    	return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
    }
    
    @PutMapping(value = "atualizar") /* Mapeia a URL */
    @ResponseBody
    public ResponseEntity<?> atualizar(@RequestBody Usuario usuario){ /* Recebe os dados para atualizar */
    	
    	if(usuario.getId() == null) {
    		return new ResponseEntity<String>("Informe o Id para a atualização", HttpStatus.OK);
    	}
    	
    	Usuario user = usuarioRepository.saveAndFlush(usuario);
    
    	return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    }



    @DeleteMapping(value = "deletar") /* Mapeia a URL */
    @ResponseBody
    public ResponseEntity<String> deletar(@RequestParam Long idUser){ /* Recebe os dados para salvar */
    	
    	usuarioRepository.deleteById(idUser);
    
    	return new ResponseEntity<String>("Usuario Excluído", HttpStatus.OK);
    }
    
    @GetMapping(value = "buscaruserid") /* Mapeia a URL */
    @ResponseBody
    public ResponseEntity<Usuario> buscaruserid(@RequestParam(name = "idUser") Long idUser){ /* Recebe os dados para deletar */
    	
    	Usuario usuario = usuarioRepository.findById(idUser).get();
    
    	return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }
    
    @GetMapping(value = "buscarPorNome") /* Mapeia a URL */
    @ResponseBody
    public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "name") String name){ /* Recebe o nome para buscar */
    	
    	List<Usuario> usuarios = usuarioRepository.buscarPorNome(name.trim());
    
    	return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
    }
    


}






