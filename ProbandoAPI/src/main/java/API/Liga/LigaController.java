package API.Liga;

import API.Jugador.*;
import API.Partido.PartidoRepository;
import API.Arbitro.Arbitro;
import API.Arbitro.ArbitroRepository;
import API.Equipo.*;


import java.util.Collections;
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
@RequestMapping (value="/ligas")
public class LigaController {
	
	public interface GoleadoresView extends Jugador.EquipoAtt {}
	public interface ClasificacionView extends Equipo.RankAtt,Equipo.PerfilAtt{}
	public interface InfoLigaView extends Liga.LigaAtt, Jugador.EquipoAtt, Equipo.RankAtt{}
	
	@Autowired
	private EquipoRepository equipoRepository;
	@Autowired
	private LigaRepository ligaRepository;
	@Autowired
	private ArbitroRepository arbitroRepository;
	@Autowired
	private PartidoRepository partidoRepository;
	
	@JsonView(InfoLigaView.class)
	@RequestMapping (method = RequestMethod.POST)
	public ResponseEntity<Liga>crearLiga (@RequestBody Liga liga){
		if(ligaRepository.findByNombreIgnoreCase(liga.getNombre())!= null){
			return new ResponseEntity<Liga>(HttpStatus.NOT_ACCEPTABLE);
		}
		ligaRepository.save(liga);
		return new ResponseEntity<Liga>(liga,HttpStatus.CREATED);
	}
	
	@JsonView(InfoLigaView.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Liga>>verLigas(){
		return new ResponseEntity<List<Liga>>(ligaRepository.findAll(),HttpStatus.OK);
	}
	
	@JsonView(InfoLigaView.class)
	@RequestMapping(value ="/{nombre}",method = RequestMethod.GET)
	public ResponseEntity<Liga> verLigaNombre(@PathVariable String nombre){
		return new ResponseEntity<Liga>(ligaRepository.findByNombreIgnoreCase(nombre),HttpStatus.OK);
	}
	@JsonView(ClasificacionView.class)
	@RequestMapping(value = "/{nombre}/clasificacion", method = RequestMethod.GET)
	public ResponseEntity<List<Equipo>> verClasificacion (@PathVariable String nombre){
		Liga liga = ligaRepository.findByNombreIgnoreCase(nombre);
		if(liga == null){
			return new ResponseEntity<List<Equipo>>(HttpStatus.NO_CONTENT);
		}
		Collections.sort(liga.getClasificacion());
		
		return new ResponseEntity<List<Equipo>>(liga.getClasificacion(),HttpStatus.OK);
	}
	
	@JsonView(GoleadoresView.class)
	@RequestMapping(value = "/{nombre}/goleadores", method = RequestMethod.GET)
	public ResponseEntity<List<Jugador>> verGoleadores (@PathVariable String nombre){
		Liga liga = ligaRepository.findByNombreIgnoreCase(nombre);
		if(liga == null){
			return new ResponseEntity<List<Jugador>>(HttpStatus.NO_CONTENT);
		}
		Collections.sort(liga.getGoleadores());
		
		return new ResponseEntity<List<Jugador>>(liga.getGoleadores(),HttpStatus.OK);
	}
	@JsonView(InfoLigaView.class)
	@RequestMapping(value="/{nombre}/equipo/{nombreEquipo}",method = RequestMethod.PUT)
	public ResponseEntity<Liga> añadirEquipo (@PathVariable (value = "nombre")String nombre, @PathVariable(value = "nombreEquipo") String nombreEquipo){
		Equipo equipo = equipoRepository.findByNombreIgnoreCase(nombreEquipo);
		Liga liga = ligaRepository.findByNombreIgnoreCase(nombre);
		if(liga == null || equipo == null){
			return new ResponseEntity<Liga>(HttpStatus.NO_CONTENT);
		}
		
		if(!liga.getClasificacion().contains(equipo)){
			if(!equipo.getLiga().equals("")){
				Liga aux = ligaRepository.findByNombreIgnoreCase(equipo.getLiga());
				if(aux != null){
					aux.getClasificacion().remove(equipo);
					aux.getGoleadores().removeAll(equipo.getPlantillaEquipo());
					ligaRepository.save(aux);
				}
			}
			equipo.setLiga(liga.getNombre());
			
			equipoRepository.save(equipo);
			
			liga.getClasificacion().add(equipo);
			liga.getGoleadores().addAll(equipo.getPlantillaEquipo());
			
			ligaRepository.save(liga);
			return new ResponseEntity<Liga>(liga, HttpStatus.OK);
		}else{
			return new ResponseEntity<Liga>(HttpStatus.NOT_ACCEPTABLE);
		}
	}
	@JsonView(InfoLigaView.class)
	@RequestMapping(value="/{nombre}/arbitro/{idArbitro}",method = RequestMethod.PUT)
	public ResponseEntity<Liga>añadirArbitro(@PathVariable (value = "nombre")String nombre, @PathVariable (value = "idArbitro")String idArbitro){
		Liga liga = ligaRepository.findByNombreIgnoreCase(nombre);
		Arbitro arbitro = arbitroRepository.findById(idArbitro);
		
		if(liga == null || arbitro == null || liga.getArbitros().contains(arbitro)){
			return new ResponseEntity<Liga>(HttpStatus.NOT_ACCEPTABLE);
		}
		liga.getArbitros().add(arbitro);
		ligaRepository.save(liga);
		
		return new ResponseEntity<Liga>(liga,HttpStatus.OK);
	}
	@JsonView(InfoLigaView.class)
	@RequestMapping(value="/{nombre}/arbitro/{idArbitro}",method = RequestMethod.DELETE)
	public ResponseEntity<Liga>eliminarArbitroLiga(@PathVariable (value = "nombre")String nombre, @PathVariable (value = "idArbitro")String idArbitro){
		Liga liga = ligaRepository.findByNombreIgnoreCase(nombre);
		Arbitro arbitro = arbitroRepository.findById(idArbitro);
		
		if(liga == null || arbitro == null || !liga.getArbitros().contains(arbitro)){
			return new ResponseEntity<Liga>(HttpStatus.NO_CONTENT);
		}
		liga.getArbitros().remove(arbitro);
		ligaRepository.save(liga);
		
		return new ResponseEntity<Liga>(liga,HttpStatus.OK);
	}
	@JsonView(InfoLigaView.class)
	@RequestMapping(value="/{nombre}/equipo/{idEquipo}",method = RequestMethod.DELETE)
	public ResponseEntity<Liga>eliminarEquipoLiga(@PathVariable (value = "nombre")String nombre, @PathVariable (value = "idEquipo")String idEquipo){
		Liga liga = ligaRepository.findByNombreIgnoreCase(nombre);
		Equipo equipo = equipoRepository.findById(idEquipo);
		if(liga == null || equipo == null || !liga.getClasificacion().contains(equipo)){
			return new ResponseEntity<Liga>(HttpStatus.NO_CONTENT);
		}
		liga.getClasificacion().remove(equipo);
		liga.getGoleadores().removeAll(equipo.getPlantillaEquipo());
		equipo.setLiga("");
		
		equipoRepository.save(equipo);
		ligaRepository.save(liga);
		
		
		return new ResponseEntity<Liga>(liga, HttpStatus.OK);
	}
	
	@JsonView(InfoLigaView.class)
	@RequestMapping(value="/{id}",method = RequestMethod.DELETE)
	public ResponseEntity<Liga>eliminarLiga(@PathVariable String id){
		Liga liga = ligaRepository.findById(id);
		if(liga == null){
			return new ResponseEntity<Liga>(HttpStatus.NO_CONTENT);
		}
		for(Equipo e:liga.getClasificacion()){
			e.setLiga("");
			equipoRepository.save(e);
		}
		ligaRepository.delete(liga);
		return new ResponseEntity<Liga>(liga, HttpStatus.OK);
	}
	/*//Siempre que se apruebe un acta se deberá llamar a este método.
	@JsonView(ClasificacionView.class)
	@RequestMapping(value = "/{idLiga}/actualizarClasificacion", method = RequestMethod.PUT)
	public ResponseEntity<List<Equipo>> actualizarClasificacion (@PathVariable String idLiga){
		Liga liga = ligaRepository.findById(idLiga);
		if(liga == null){
			return new ResponseEntity<List<Equipo>>(HttpStatus.NO_CONTENT);
		}
		Collections.sort(liga.getClasificacion());
		
		return new ResponseEntity<List<Equipo>>(liga.getClasificacion(),HttpStatus.OK);
	}*/
}
