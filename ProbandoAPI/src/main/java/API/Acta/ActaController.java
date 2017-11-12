package API.Acta;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import API.Arbitro.Arbitro;
import API.Arbitro.ArbitroRepository;
import API.Partido.Partido;
import API.Partido.PartidoRepository;
import API.Usuario.UsuarioComponent;

@RestController
@CrossOrigin
@RequestMapping("/actas")
public class ActaController {

	@Autowired
	ActaRepository actaRepository;
	@Autowired
	PartidoRepository partidoRepository;
	@Autowired
	ArbitroRepository arbitroRepository;
	@Autowired
	UsuarioComponent usuarioComponent;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Acta>> verActas() {
		return new ResponseEntity<List<Acta>>(actaRepository.findAll(), HttpStatus.OK);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Acta> verActaId(@PathVariable String id) {
		Acta entrada = actaRepository.findById(id);
		if (entrada == null) {
			return new ResponseEntity<Acta>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Acta>(entrada, HttpStatus.OK);
	}

	@RequestMapping(value = "/partido/{idPartido}")
	public ResponseEntity<Acta> verActaIdPartido(@PathVariable String idPartido) {
		Acta entrada = actaRepository.findByIdPartidoIgnoreCase(idPartido);
		if (entrada == null) {
			return new ResponseEntity<Acta>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Acta>(entrada, HttpStatus.OK);
	}

	@RequestMapping(value = "/arbitro/{arbitro}", method = RequestMethod.GET)
	public ResponseEntity<List<Acta>> verActaArbitro(@PathVariable String arbitro) {
		List<Acta> entrada = actaRepository.findByArbitroId(new ObjectId(arbitro));
		if (entrada.isEmpty()) {
			return new ResponseEntity<List<Acta>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Acta>>(entrada, HttpStatus.OK);
	}

	@RequestMapping(value = "/fecha/{fecha}", method = RequestMethod.GET)
	public ResponseEntity<List<Acta>> verActaFecha(@PathVariable String fecha) {
		List<Acta> entrada = actaRepository.findByFecha(fecha);
		if (entrada.isEmpty()) {
			return new ResponseEntity<List<Acta>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Acta>>(entrada, HttpStatus.OK);
	}

	@RequestMapping(value = "/equipoLocal/{equipoLocal}", method = RequestMethod.GET)
	public ResponseEntity<List<Acta>> verActaEquipoLocal(@PathVariable String equipoLocal) {
		List<Acta> entrada = actaRepository.findByEquipoLocalId(new ObjectId(equipoLocal));
		if (entrada.isEmpty()) {
			return new ResponseEntity<List<Acta>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Acta>>(entrada, HttpStatus.OK);
	}

	@RequestMapping(value = "/equipoVisitante/{equipoVisitante}", method = RequestMethod.GET)
	public ResponseEntity<List<Acta>> verActaEquipoVisitante(@PathVariable String equipoVisitante) {
		List<Acta> entrada = actaRepository.findByEquipoVisitanteId(new ObjectId(equipoVisitante));
		if (entrada.isEmpty()) {
			return new ResponseEntity<List<Acta>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Acta>>(entrada, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Acta> crearActa(@RequestBody Acta entrada) {
		Partido partidoDelActa = partidoRepository.findById(entrada.getIdPartido());
		// Si el partido cuyo acta estamos creando no existe.
		if (partidoDelActa == null) {
			return new ResponseEntity<Acta>(HttpStatus.NOT_ACCEPTABLE);
		} else {
			// Si el usuario conectado es un árbitro.
			if (usuarioComponent.getLoggedUser().getRol().equals("ROLE_ARBITRO")) {
				Arbitro arbitroConectado = arbitroRepository
						.findByNombreUsuario(usuarioComponent.getLoggedUser().getNombreUsuario());
				// Si un árbitro intenta crear un acta que no sea de un partido que ha
				// arbitrado.
				if (!arbitroConectado.getPartidosArbitrados().contains(partidoDelActa)) {
					return new ResponseEntity<Acta>(HttpStatus.NOT_ACCEPTABLE);
				} else {
					entrada.setId(null);
					actaRepository.save(entrada);
					Acta actaConId = actaRepository.findById(entrada.getId());
					partidoDelActa.setActa(actaConId);
					partidoRepository.save(partidoDelActa);
					return new ResponseEntity<Acta>(entrada, HttpStatus.CREATED);
				}
			}
			// Si el usuario conectado es un miembro del comité o un administrador.
			else {

				entrada.setId(null);
				actaRepository.save(entrada);
				Acta actaConId = actaRepository.findById(entrada.getId());
				partidoDelActa.setActa(actaConId);
				partidoRepository.save(partidoDelActa);
				return new ResponseEntity<Acta>(entrada, HttpStatus.CREATED);
			}
		}
	}
}
