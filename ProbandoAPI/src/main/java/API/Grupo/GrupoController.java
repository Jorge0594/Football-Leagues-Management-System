 package API.Grupo;

import API.Jugador.*;
import API.Mails.MailService;
import API.MongoBulk.MongoBulk;
import API.Partido.Partido;
import API.Partido.PartidoRepository;
import API.Usuario.Usuario;
import API.Usuario.UsuarioRepository;
import API.Utilidades.UsuarioUtils;
import API.Equipo.*;
import API.Grupo.*;
import API.Grupo.Grupo.GrupoAtt;
import API.VistaGrupo.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.tournament.generator.Round;
import com.tournament.generator.TournamentCalendar;

@RestController
@CrossOrigin
@RequestMapping(value = "/grupos")
public class GrupoController {


	public interface ClasificacionView extends Equipo.RankAtt, Equipo.PerfilAtt {
	}

	public interface InfoGrupoView extends GrupoAtt, Jugador.EquipoAtt, Equipo.RankAtt, Partido.InfoAtt {
	}

	@Autowired
	private EquipoRepository equipoRepository;
	@Autowired
	private GrupoRepository grupoRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private JugadorRepository jugadorRepository;
	@Autowired
	private VistaGrupoRepository vistaGrupoRepository;

	@Autowired
	private MailService mailService;
	@Autowired
	private UsuarioUtils utils;
	@Autowired
	private MongoBulk mongoBulk;

	@JsonView(InfoGrupoView.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Grupo> crearGrupo(@RequestBody Grupo grupo) {	
		grupo.setId(null);
		grupo.setNombre(grupo.getNombre().toUpperCase());
		grupoRepository.save(grupo);
		
		VistaGrupo vistaGrupo = new VistaGrupo(grupo.getId(),grupo.getNombre());
		vistaGrupoRepository.save(vistaGrupo);
		
		return new ResponseEntity<Grupo>(grupo, HttpStatus.CREATED);
	}

	@JsonView(InfoGrupoView.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Grupo>> verGrupos() {
		return new ResponseEntity<List<Grupo>>(grupoRepository.findAll(), HttpStatus.OK);
	}

	@RequestMapping(value = "/nombres", method = RequestMethod.GET)
	public ResponseEntity<List<Grupo>> verNombresGrupos() {
		return new ResponseEntity<List<Grupo>>(grupoRepository.findCustomNombresGrupo(), HttpStatus.OK);
	}

	@JsonView(InfoGrupoView.class)
	@RequestMapping(value = "/{nombre}", method = RequestMethod.GET)
	public ResponseEntity<Grupo> verGrupoNombre(@PathVariable String nombre) {
		return new ResponseEntity<Grupo>(grupoRepository.findByNombreIgnoreCase(nombre), HttpStatus.OK);
	}

	@JsonView(InfoGrupoView.class)
	@RequestMapping(value = "{nombre}/clasificacion" , method = RequestMethod.GET)
	public ResponseEntity<List<Equipo>> verClasificacion(@PathVariable String nombre) {
		Sort sort = new Sort(Sort.Direction.DESC, "puntos");
		List<Equipo> equipos = equipoRepository.findCustomClasificacion(nombre.toUpperCase(), sort);
		if (equipos == null) {
			return new ResponseEntity<List<Equipo>>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Equipo>>(equipos, HttpStatus.OK);
	}
	
	@JsonView(InfoGrupoView.class)
	@RequestMapping(value = "/{nombreGrupo}/generarCalendario/{fechaInicio}/{duracionJornada}", method = RequestMethod.GET)
	public ResponseEntity<List<Partido>> generarCalendario(@PathVariable(value = "nombreGrupo") String nombreGrupo, @PathVariable(value = "fechaInicio") String fechaInicio, @PathVariable(value = "duracionJornada") String duracionJornada) {
	
			List<Equipo> equipos = equipoRepository.findCustomEquiposGrupo(nombreGrupo.toUpperCase(), true);
			Map<String, Equipo> mapaEquipos = equipos.stream()
					.collect(Collectors.toMap(Equipo::getId, Function.identity()));

			List<Partido> partidos = generarCalendarioGrupo(mapaEquipos, fechaInicio, nombreGrupo, Integer.parseInt(duracionJornada));

			try {
				mongoBulk.insertarBloque(partidos, "Partido");
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<List<Partido>>(HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<List<Partido>>(partidos, HttpStatus.OK);

		
	}

	@JsonView(InfoGrupoView.class)
	@RequestMapping(value = "/{nombre}/equipo/{idEquipo}", method = RequestMethod.PUT)
	public ResponseEntity<Grupo> aceptarEquipo(@PathVariable(value = "nombre") String nombre, @PathVariable(value = "idEquipo") String idEquipo) {
		Equipo equipo = equipoRepository.findById(idEquipo);
		Grupo grupo = grupoRepository.findByNombreIgnoreCase(nombre);
		if (grupo == null || equipo == null) {
			return new ResponseEntity<Grupo>(HttpStatus.NO_CONTENT);
		}

		if (!grupo.getClasificacion().contains(equipo)) {
			if (!equipo.getGrupo().equals("")) {
				Grupo aux = grupoRepository.findByNombreIgnoreCase(equipo.getGrupo());
				if (aux != null) {
					aux.getClasificacion().remove(equipo);
					grupoRepository.save(aux);
				}
			}
			equipo.setGrupo(grupo.getNombre());
			equipo.setAceptado(true);
			for (Jugador j : equipo.getPlantillaEquipo()) {
				String clave = utils.generarClave();

				j.setAceptado(true);
				j.setGrupo(grupo.getNombre());
				j.setNombreUsuario(utils.generarNombreUsuario(j.getNombre(), j.getApellidos()));
				j.setClaveEncriptada(clave);

				String texto = j.getNombre() + ";" + j.getNombreUsuario() + ";" + clave;
				// mailService.getMail().mandarEmail(j.getEmail(), "Nombre de
				// usuario y contraseña ", texto, "jugador");

				Usuario usuario = new Usuario(j.getNombreUsuario(), j.getClave(), "ROLE_JUGADOR");
				usuarioRepository.save(usuario);

				jugadorRepository.save(j);

			}
			equipoRepository.save(equipo);

			grupo.getClasificacion().add(equipo);

			grupoRepository.save(grupo);
			return new ResponseEntity<Grupo>(grupo, HttpStatus.OK);
		} else {
			return new ResponseEntity<Grupo>(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@JsonView(InfoGrupoView.class)
	@RequestMapping(value = "/{nombre}/equipo/{idEquipo}", method = RequestMethod.DELETE)
	public ResponseEntity<Grupo> eliminarEquipoGrupo(@PathVariable(value = "nombre") String nombre, @PathVariable(value = "idEquipo") String idEquipo) {
		Grupo grupo = grupoRepository.findByNombreIgnoreCase(nombre);
		Equipo equipo = equipoRepository.findById(idEquipo);
		if (grupo == null || equipo == null || !grupo.getClasificacion().contains(equipo)) {
			return new ResponseEntity<Grupo>(HttpStatus.NO_CONTENT);
		}
		grupo.getClasificacion().remove(equipo);
		equipo.setGrupo("");

		equipoRepository.save(equipo);
		grupoRepository.save(grupo);

		return new ResponseEntity<Grupo>(grupo, HttpStatus.OK);
	}

	@JsonView(InfoGrupoView.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Grupo> eliminarGrupo(@PathVariable String id) {
		Grupo grupo = grupoRepository.findById(id);
		if (grupo == null) {
			return new ResponseEntity<Grupo>(HttpStatus.NO_CONTENT);
		}
		for (Equipo e : grupo.getClasificacion()) {
			e.setGrupo("");
			equipoRepository.save(e);
		}
		grupoRepository.delete(grupo);
		return new ResponseEntity<Grupo>(grupo, HttpStatus.OK);
	}

	private List<Partido> generarCalendarioGrupo(Map<String, Equipo> mapaEquipos, String fechaInicio, String nombreGrupo, int duracionJornada) {
		
		List<String> idEquipos  = new ArrayList<>();
		idEquipos.addAll(mapaEquipos.keySet());

		TournamentCalendar generadorCalendario = new TournamentCalendar(idEquipos, fechaInicio, duracionJornada);
		List<Round> calendario = generadorCalendario.getSchedule();
		
		return calendario.stream()
				.map(round -> {
					return new Partido(nombreGrupo, round.getLocalId(), mapaEquipos.get(round.getLocalId()).getNombre(), round.getVisitorId(),
							mapaEquipos.get(round.getVisitorId()).getNombre(), mapaEquipos.get(round.getLocalId()).getImagenEquipo(),
								mapaEquipos.get(round.getVisitorId()).getImagenEquipo(), round.getDate(), round.getRoundNum());
		}).collect(Collectors.toList());

	}
	
}
