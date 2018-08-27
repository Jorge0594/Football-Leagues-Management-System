 package API.Grupo;

import API.Jugador.*;
import API.Mails.MailService;
import API.MongoBulk.MongoBulk;
import API.Partido.Partido;
import API.Partido.PartidoRepository;
import API.Usuario.Usuario;
import API.Usuario.UsuarioRepository;
import API.Utilidades.UsuarioUtils;
import API.Vistas.*;
import API.Acta.Acta;
import API.Acta.ActaRepository;
import API.Equipo.*;
import API.Grupo.Grupo.GrupoAtt;
import API.Temporada.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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


	public interface ClasificacionView extends Equipo.RankAtt, Equipo.PerfilAtt, VistaGrupo.VistaGrupoAtt, VistaTemporada.VistaTemporadaAtt {
	}

	public interface InfoGrupoView extends GrupoAtt, Jugador.EquipoAtt, Jugador.PerfilAtt, Equipo.RankAtt, Partido.InfoAtt, VistaGrupo.VistaGrupoAtt, VistaTemporada.VistaTemporadaAtt{
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
	private TemporadaRepository temporadaRepository;
	@Autowired
	private ActaRepository actaRepository;
	@Autowired
	private PartidoRepository partidoRepository;

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

		return new ResponseEntity<Grupo>(grupo, HttpStatus.CREATED);
	}
	
	@JsonView(InfoGrupoView.class)
	@RequestMapping(value = "/{idTemporada}", method = RequestMethod.POST)
	public ResponseEntity<Grupo> crearGrupoConVista(@RequestBody Grupo grupo, @PathVariable String idTemporada) {	
		grupo.setId(null);
		grupo.setNombre(grupo.getNombre().toUpperCase());		
		grupoRepository.save(grupo);
		
		Temporada temporadaActual = temporadaRepository.findById(idTemporada);
		
		if(temporadaActual != null) {
			grupo.setTemporada(new VistaTemporada(temporadaActual.getId(), temporadaActual.getNombre(), temporadaActual.getLiga()));
			grupoRepository.save(grupo);
			
			VistaGrupo vistaGrupo = new VistaGrupo(grupo.getId(),grupo.getNombre());
			if(!temporadaActual.addVistaGrupo(vistaGrupo)){
				grupoRepository.delete(grupo);
				return new ResponseEntity<Grupo>(HttpStatus.NOT_FOUND);
			}
			
			temporadaRepository.save(temporadaActual);	
			return new ResponseEntity<Grupo>(grupo, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Grupo>(HttpStatus.NOT_ACCEPTABLE);
		}
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
	@RequestMapping(value = "nombre/{nombre}", method = RequestMethod.GET)
	public ResponseEntity<Grupo> verGrupoNombre(@PathVariable String nombre) {
		return new ResponseEntity<Grupo>(grupoRepository.findByNombreIgnoreCase(nombre), HttpStatus.OK);
	}
	
	@JsonView(InfoGrupoView.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Grupo> verGrupoId(@PathVariable String id) {
		return new ResponseEntity<Grupo>(grupoRepository.findById(id), HttpStatus.OK);
	}

	@JsonView(InfoGrupoView.class)
	@RequestMapping(value = "{idGrupo}/clasificacion" , method = RequestMethod.GET)
	public ResponseEntity<List<Equipo>> verClasificacion(@PathVariable String idGrupo) {
		Sort sort = new Sort(Sort.Direction.DESC, "puntos", "goles", "golesEncajados");
		List<Equipo> equipos = equipoRepository.findCustomClasificacion(idGrupo, sort);
		if (equipos == null) {
			return new ResponseEntity<List<Equipo>>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Equipo>>(equipos, HttpStatus.OK);
	}
	
	@JsonView(InfoGrupoView.class)
	@RequestMapping(value = "/goleadores/{idGrupo}", method = RequestMethod.GET)
	public ResponseEntity<List<Jugador>> obtenerGoleadores(@PathVariable String idGrupo){
		PageRequest page = new PageRequest(0, 5, new Sort(Sort.Direction.DESC, "goles"));
		
		return new ResponseEntity<List<Jugador>>(jugadorRepository.getRankings(idGrupo, page), HttpStatus.OK);
	}
	
	@JsonView(InfoGrupoView.class)
	@RequestMapping(value = "/amarillas/{idGrupo}", method = RequestMethod.GET)
	public ResponseEntity<List<Jugador>> obtenerRankAmarillas(@PathVariable String idGrupo){
		PageRequest page = new PageRequest(0, 5, new Sort(Sort.Direction.DESC, "tarjetasAmarillas"));
		
		return new ResponseEntity<List<Jugador>>(jugadorRepository.getRankings(idGrupo, page), HttpStatus.OK);
	}
	
	@JsonView(InfoGrupoView.class)
	@RequestMapping(value = "/rojas/{idGrupo}", method = RequestMethod.GET)
	public ResponseEntity<List<Jugador>> obtenerRankRojas(@PathVariable String idGrupo){
		PageRequest page = new PageRequest(0, 5, new Sort(Sort.Direction.DESC, "tarjetasRojas"));
		
		return new ResponseEntity<List<Jugador>>(jugadorRepository.getRankings(idGrupo, page), HttpStatus.OK);
	}
	
	@JsonView(InfoGrupoView.class)
	@RequestMapping(value = "/porteros/{idGrupo}", method = RequestMethod.GET)
	public ResponseEntity<List<Jugador>> obtenerRankPorteros(@PathVariable String idGrupo){
		List<Jugador> porteros = jugadorRepository.getPorteros(idGrupo);
		
		if(porteros == null || porteros.isEmpty()){
			return new ResponseEntity<List<Jugador>>(HttpStatus.NOT_ACCEPTABLE);
		}
		
		Long partidos = actaRepository.getNumeroPartidosPortero("5abf51f35118d22d0022b528");
		System.out.println("PARTIDOS: " + partidos );
		Map<Jugador, Integer> mapaPorteroGoles = porteros.stream()
				.filter(p -> p.getEquipo() != null && actaRepository.getNumeroPartidosPortero(p.getId()) > 0 && actaRepository.getNumeroPartidosPortero(p.getId()) >= (this.partidosJugadosEquipo(p.getEquipo()) * 0.8))
				.collect(Collectors.toMap(Function.identity(), this::calcularGolesEncajadosPortero));
		
		mapaPorteroGoles = mapaPorteroGoles.entrySet().stream()
				.sorted(Map.Entry.<Jugador, Integer>comparingByValue().reversed())
				.limit(5)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		
		porteros = new ArrayList<>();
		porteros.addAll(mapaPorteroGoles.keySet());
		
		return new ResponseEntity<List<Jugador>>(porteros, HttpStatus.OK);
	}
	
	
	@JsonView(InfoGrupoView.class)
	@RequestMapping(value = "/modificar/{idGrupo}", method = RequestMethod.PUT)
	public ResponseEntity<List<Jugador>> modificarJugadores(@PathVariable String idGrupo){
		Grupo grupo = grupoRepository.findById(idGrupo);
		
		if(grupo == null){
			 return new ResponseEntity<List<Jugador>>(HttpStatus.NOT_ACCEPTABLE);
		}
		
		VistaGrupo vistaGrupo = new VistaGrupo(grupo.getId(), grupo.getNombre());
		
		Update update = new Update();
		update.set("grupo", vistaGrupo);
		
		Query query = new Query();
		
		mongoBulk.modificarBloque(query, update, "Jugador");
		
		return new ResponseEntity<List<Jugador>>(HttpStatus.OK);
	}
	
	
	
	@JsonView(InfoGrupoView.class)
	@RequestMapping(value = "/{idGrupo}/generarCalendario/{fechaInicio}/{duracionJornada}", method = RequestMethod.GET)
	public ResponseEntity<List<Partido>> generarCalendario(@PathVariable(value = "idGrupo") String idGrupo, @PathVariable(value = "fechaInicio") String fechaInicio, @PathVariable(value = "duracionJornada") String duracionJornada) {
			
			Grupo grupo = grupoRepository.findCustomTemporada(idGrupo);
			if(grupo == null){
				return new ResponseEntity<List<Partido>>(HttpStatus.NOT_ACCEPTABLE);
			}
			
			List<Equipo> equipos = equipoRepository.findCustomEquiposGrupo(idGrupo, true);
			Map<String, Equipo> mapaEquipos = equipos.stream()
					.collect(Collectors.toMap(Equipo::getId, Function.identity()));

			List<Partido> partidos = generarCalendarioGrupo(mapaEquipos, fechaInicio, grupo, Integer.parseInt(duracionJornada));

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
				Grupo aux = grupoRepository.findById(equipo.getGrupo().getIdGrupo());
				if (aux != null) {
					aux.getClasificacion().remove(equipo);
					grupoRepository.save(aux);
				}
			}
			equipo.setGrupo(new VistaGrupo(grupo.getId(), grupo.getNombre()));
			equipo.setAceptado(true);
			for (Jugador j : equipo.getPlantillaEquipo()) {
				String clave = utils.generarClave();
				
				j.setAceptado(true);
				j.setGrupo(new VistaGrupo(grupo.getId(), grupo.getNombre()));
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
		equipo.setGrupo(new VistaGrupo());

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
			e.setGrupo(new VistaGrupo());
			equipoRepository.save(e);
		}
		grupoRepository.delete(grupo);
		return new ResponseEntity<Grupo>(grupo, HttpStatus.OK);
	}

	private List<Partido> generarCalendarioGrupo(Map<String, Equipo> mapaEquipos, String fechaInicio, Grupo grupo, int duracionJornada) {
		
		VistaGrupo vistaGrupo = new VistaGrupo(grupo.getId(), grupo.getNombre());
		VistaTemporada vistaTemporada = grupo.getTemporada();
		
		List<String> idEquipos  = new ArrayList<>();
		idEquipos.addAll(mapaEquipos.keySet());

		TournamentCalendar generadorCalendario = new TournamentCalendar(idEquipos, fechaInicio, duracionJornada);
		List<Round> calendario = generadorCalendario.getSchedule();
		
		return calendario.stream()
				.map(round -> {
					return new Partido(vistaTemporada.getLiga(), vistaGrupo, vistaTemporada, round.getLocalId(), mapaEquipos.get(round.getLocalId()).getNombre(), round.getVisitorId(),
							mapaEquipos.get(round.getVisitorId()).getNombre(), mapaEquipos.get(round.getLocalId()).getImagenEquipo(),
								mapaEquipos.get(round.getVisitorId()).getImagenEquipo(), round.getDate(), round.getRoundNum());
		}).collect(Collectors.toList());

	}
	
	private int calcularGolesEncajadosPortero(Jugador portero){
		
		int totalLocal = actaRepository.findCustomEncajadosPorterolLocal(portero.getId()).stream()
				.map(Acta::getGolesVisitante)
				.reduce(0, (p1, p2) -> p1 + p2);
		
		int totalVisitante = actaRepository.findCustomEncajadosPorterolVisitante(portero.getId()).stream()
				.map(Acta::getGolesLocal)
				.reduce(0, (p1, p2) -> p1 + p2);
		
		return (totalLocal + totalVisitante);
	}
	
	private int partidosJugadosEquipo(String id){
		return equipoRepository.getPartidosJugadosEquipo(id).getPartidosJugados(); 
	}
	
}
