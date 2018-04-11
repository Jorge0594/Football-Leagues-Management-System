package API.Liga;

import API.Jugador.*;
import API.Partido.Partido;
import API.Partido.PartidoRepository;
import API.Arbitro.Arbitro;
import API.Arbitro.ArbitroRepository;
import API.Equipo.*;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

@RestController
@CrossOrigin
@RequestMapping(value = "/ligas")
public class LigaController {

	public interface GoleadoresView extends Jugador.EquipoAtt {
	}

	public interface ClasificacionView extends Equipo.RankAtt, Equipo.PerfilAtt {
	}

	public interface InfoLigaView extends Liga.LigaAtt, Jugador.EquipoAtt, Equipo.RankAtt, Partido.InfoAtt{
	}

	@Autowired
	private EquipoRepository equipoRepository;
	@Autowired
	private LigaRepository ligaRepository;
	@Autowired
	private ArbitroRepository arbitroRepository;
	@Autowired
	private PartidoRepository partidoRepository;
	@Autowired
	private JugadorRepository jugadorRepository;

	@JsonView(InfoLigaView.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Liga> crearLiga(@RequestBody Liga liga) {
		if (ligaRepository.findByNombreIgnoreCase(liga.getNombre()) != null) {
			return new ResponseEntity<Liga>(HttpStatus.NOT_ACCEPTABLE);
		}
		liga.setId(null);
		ligaRepository.save(liga);
		return new ResponseEntity<Liga>(liga, HttpStatus.CREATED);
	}
	@JsonView(InfoLigaView.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Liga>> verLigas() {
		return new ResponseEntity<List<Liga>>(ligaRepository.findAll(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value= "nombres", method = RequestMethod.GET)
	public ResponseEntity<List<Liga>> verNombresLigas() {
		return new ResponseEntity<List<Liga>>(ligaRepository.findCustomNombresliga(), HttpStatus.OK);
	}

	@JsonView(InfoLigaView.class)
	@RequestMapping(value = "/{nombre}", method = RequestMethod.GET)
	public ResponseEntity<Liga> verLigaNombre(@PathVariable String nombre) {
		return new ResponseEntity<Liga>(ligaRepository.findByNombreIgnoreCase(nombre), HttpStatus.OK);
	}

	@JsonView(ClasificacionView.class)
	@RequestMapping(value = "/{nombre}/clasificacion", method = RequestMethod.GET)
	public ResponseEntity<List<Equipo>> verClasificacion(@PathVariable String nombre) {
		Liga liga = ligaRepository.findByNombreIgnoreCase(nombre);
		if (liga == null) {
			return new ResponseEntity<List<Equipo>>(HttpStatus.NO_CONTENT);
		}
		//Collections.sort(liga.getClasificacion());

		return new ResponseEntity<List<Equipo>>(liga.getClasificacion(), HttpStatus.OK);
	}

	@JsonView(GoleadoresView.class)
	@RequestMapping(value = "/{nombre}/goleadores", method = RequestMethod.GET)
	public ResponseEntity<List<Jugador>> verGoleadores(@PathVariable String nombre) {
		Liga liga = ligaRepository.findByNombreIgnoreCase(nombre);
		if (liga == null) {
			return new ResponseEntity<List<Jugador>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Jugador>>(liga.getGoleadores(), HttpStatus.OK);
	}

	@JsonView(InfoLigaView.class)
	@RequestMapping(value = "/{nombre}/equipo/{idEquipo}", method = RequestMethod.PUT)
	public ResponseEntity<Liga> añadirEquipo(@PathVariable(value = "nombre") String nombre, @PathVariable(value = "idEquipo") String idEquipo) {
		Equipo equipo = equipoRepository.findById(idEquipo);
		Liga liga = ligaRepository.findByNombreIgnoreCase(nombre);
		if (liga == null || equipo == null) {
			return new ResponseEntity<Liga>(HttpStatus.NO_CONTENT);
		}

		if (!liga.getClasificacion().contains(equipo)) {
			if (!equipo.getLiga().equals("")) {
				Liga aux = ligaRepository.findByNombreIgnoreCase(equipo.getLiga());
				if (aux != null) {
					aux.getClasificacion().remove(equipo);
					aux.getGoleadores().removeAll(equipo.getPlantillaEquipo());
					ligaRepository.save(aux);
				}
			}
			equipo.setLiga(liga.getNombre());
			equipo.setAceptado(true);
			for(Jugador j : equipo.getPlantillaEquipo()){
				j.setAceptado(true);
				j.setLiga(liga.getNombre());
				jugadorRepository.save(j);
			}
			equipoRepository.save(equipo);

			liga.getClasificacion().add(equipo);
			//liga.getGoleadores().addAll(equipo.getPlantillaEquipo());//No queremos tener a todos los jugadorss en la clasificacion

			ligaRepository.save(liga);
			return new ResponseEntity<Liga>(liga, HttpStatus.OK);
		} else {
			return new ResponseEntity<Liga>(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@JsonView(InfoLigaView.class)
	@RequestMapping(value = "/{nombre}/arbitro/{idArbitro}", method = RequestMethod.PUT)
	public ResponseEntity<Liga> añadirArbitro(@PathVariable(value = "nombre") String nombre,
			@PathVariable(value = "idArbitro") String idArbitro) {
		Liga liga = ligaRepository.findByNombreIgnoreCase(nombre);
		Arbitro arbitro = arbitroRepository.findById(idArbitro);

		if (liga == null || arbitro == null || liga.getArbitros().contains(arbitro)) {
			return new ResponseEntity<Liga>(HttpStatus.NOT_ACCEPTABLE);
		}
		liga.getArbitros().add(arbitro);
		ligaRepository.save(liga);

		return new ResponseEntity<Liga>(liga, HttpStatus.OK);
	}

	/*@JsonView(InfoLigaView.class)
	@RequestMapping(value = "/{nombre}/partido/{idPartido}", method = RequestMethod.PUT)
	public ResponseEntity<Liga> añadirPartido(@PathVariable(value = "nombre") String nombre,
			@PathVariable(value = "idPartido") String idPartido) {
		Liga liga = ligaRepository.findByNombreIgnoreCase(nombre);
		Partido partido = partidoRepository.findById(idPartido);
		if (liga == null || partido == null || liga.getPartidos().contains(partido)) {
			return new ResponseEntity<Liga>(HttpStatus.NOT_ACCEPTABLE);
		}
		partido.setLiga(liga.getNombre());
		liga.getPartidos().add(partido);
		Collections.sort(liga.getPartidos());
		
		partidoRepository.save(partido);
		ligaRepository.save(liga);

		return new ResponseEntity<Liga>(liga, HttpStatus.OK);
	}*/

	@JsonView(InfoLigaView.class)
	@RequestMapping(value = "/{nombre}/arbitro/{idArbitro}", method = RequestMethod.DELETE)
	public ResponseEntity<Liga> eliminarArbitroLiga(@PathVariable(value = "nombre") String nombre,
			@PathVariable(value = "idArbitro") String idArbitro) {
		Liga liga = ligaRepository.findByNombreIgnoreCase(nombre);
		Arbitro arbitro = arbitroRepository.findById(idArbitro);

		if (liga == null || arbitro == null || !liga.getArbitros().contains(arbitro)) {
			return new ResponseEntity<Liga>(HttpStatus.NO_CONTENT);
		}
		liga.getArbitros().remove(arbitro);
		ligaRepository.save(liga);

		return new ResponseEntity<Liga>(liga, HttpStatus.OK);
	}

	@JsonView(InfoLigaView.class)
	@RequestMapping(value = "/{nombre}/equipo/{idEquipo}", method = RequestMethod.DELETE)
	public ResponseEntity<Liga> eliminarEquipoLiga(@PathVariable(value = "nombre") String nombre,
			@PathVariable(value = "idEquipo") String idEquipo) {
		Liga liga = ligaRepository.findByNombreIgnoreCase(nombre);
		Equipo equipo = equipoRepository.findById(idEquipo);
		if (liga == null || equipo == null || !liga.getClasificacion().contains(equipo)) {
			return new ResponseEntity<Liga>(HttpStatus.NO_CONTENT);
		}
		liga.getClasificacion().remove(equipo);
		liga.getGoleadores().removeAll(equipo.getPlantillaEquipo());
		equipo.setLiga("");

		equipoRepository.save(equipo);
		ligaRepository.save(liga);

		return new ResponseEntity<Liga>(liga, HttpStatus.OK);
	}

	/*@JsonView(InfoLigaView.class)
	@RequestMapping(value = "/{nombre}/partido/{idPartido}", method = RequestMethod.DELETE)
	public ResponseEntity<Liga> eliminarPartido(@PathVariable(value = "nombre") String nombre,
			@PathVariable(value = "idPartido") String idPartido) {
		Liga liga = ligaRepository.findByNombreIgnoreCase(nombre);
		Partido partido = partidoRepository.findById(idPartido);
		if (liga == null || partido == null || !liga.getPartidos().contains(partido)) {
			return new ResponseEntity<Liga>(HttpStatus.NOT_ACCEPTABLE);
		}
		partido.setLiga("");
		liga.getPartidos().remove(partido);

		partidoRepository.save(partido);
		ligaRepository.save(liga);

		return new ResponseEntity<Liga>(liga, HttpStatus.OK);
	}*/

	@JsonView(InfoLigaView.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Liga> eliminarLiga(@PathVariable String id) {
		Liga liga = ligaRepository.findById(id);
		if (liga == null) {
			return new ResponseEntity<Liga>(HttpStatus.NO_CONTENT);
		}
		for (Equipo e : liga.getClasificacion()) {
			e.setLiga("");
			equipoRepository.save(e);
		}
		ligaRepository.delete(liga);
		return new ResponseEntity<Liga>(liga, HttpStatus.OK);
	}
	 

	// GENERAR PARTIDOS DE LIGA --> EN PROCESO (Ignorar de aquí hasta el final de la clase - EN PROGRESO) 
	/*@RequestMapping(value = "generarPartidos/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Liga> generarPartidosLiga(@PathVariable String ligaId) {
		Liga liga = ligaRepository.findById(ligaId);
		if (liga == null) {
			new ResponseEntity<Liga>(HttpStatus.NO_CONTENT);
		}
		List<Equipo> equipos = liga.getEquipos();
		List<Partido> partidos = new ArrayList<Partido>();
		List<Arbitro> arbitros = liga.getArbitros();
		int numJornadas = 0;
		int l = 0, v = 1, c = 0;
		if (equipos.size() % 2 == 0) {
			numJornadas = (equipos.size() - 1) * 2;
			for (int j = 1; j <= numJornadas; j++) {// Número de Jornada
				for (int p = 0; p <= equipos.size() / 2; p++) { // Cada Jornada tiene numeroEquipos/2 partidos para que jueguen todos
					for (Equipo eqLocal : equipos) {
						if (!juegaEnJornada(eqLocal, j, liga)) {
							for (Equipo eqVisi : equipos) {
								if (!eqLocal.equals(eqVisi) || !juegaEnJornada(eqVisi, j, liga) || !existePartido(eqLocal, eqVisi, partidos)) {
									if (!juegaEnJornada(eqVisi, j, liga)) {
										if (j < numJornadas / 2) {
											Partido partido = crearPartido(eqLocal, eqVisi, j, ligaId);
											partidos.add(partido);
											partidoRepository.save(partido);
										} else {
											Partido partido = crearPartido(eqVisi, eqLocal, j, ligaId);
											partidos.add(partido);
											partidoRepository.save(partido);
										}
									}
								}
							}

						}
					}

				}
			}
			liga.setPartidos(partidos);
			ligaRepository.save(liga);
		}

		else { //si no hay un numero par de equipos

		}

		return new ResponseEntity<Liga>(liga, HttpStatus.OK);
	}

	public Partido crearPartido(Equipo local, Equipo visitante, int jornada, String ligaId) {
		Partido partido = new Partido();
		partido.setJornada(jornada);
		partido.setLiga(ligaId);
		partido.setEstado("Pendiente");
		partido.setEquipoLocal(local);
		partido.setEquipoVisitante(visitante);
		return partido;
	}

	public boolean juegaEnJornada(Equipo equipo, int jornada, Liga liga) {
		boolean juega=false;
		for (Partido p : liga.getPartidos()) {
			if((p.getEquipoLocal().getId() == equipo.getId() || (p.getEquipoVisitante().getId() == equipo.getId())) && p.getJornada() == jornada) {
				juega= true;
			}
		}
		return juega;
	}

	public boolean existePartido(Equipo local, Equipo visitante, List<Partido> partidos) {
		boolean existe=false;
		for(Partido p : partidos) {
			if((p.getEquipoLocal().getId()==local.getId()) && (p.getEquipoVisitante().getId()==visitante.getId())) {
				existe=true;
			}
		}
		return existe;
	}
	/*
	 * //Siempre que se apruebe un acta se deberá llamar a este método. Está sin
	 * probar hasta que se genere una liga
	 * 
	 * @JsonView(ClasificacionView.class)
	 * 
	 * @RequestMapping(value = "/{idLiga}/actualizarClasificacion", method =
	 * RequestMethod.PUT) public ResponseEntity<List<Equipo>>
	 * actualizarClasificacion (@PathVariable String idLiga){ Liga liga =
	 * ligaRepository.findById(idLiga); if(liga == null){ return new
	 * ResponseEntity<List<Equipo>>(HttpStatus.NO_CONTENT); }
	 * Collections.sort(liga.getClasificacion());
	 * 
	 * return new
	 * ResponseEntity<List<Equipo>>(liga.getClasificacion(),HttpStatus.OK); }
	 */
}
