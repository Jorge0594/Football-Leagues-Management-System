package API.Historico;

import org.springframework.data.mongodb.core.mapping.Document;

import API.Jugador.Jugador;

@Document(collection = "HistoricoJugador")
public class HistoricoJugador extends Historico {
	
	private Jugador jugador;
	
	public HistoricoJugador(){}
	
	public HistoricoJugador( Jugador jugador) {
		super();
		this.jugador = jugador;
	}

	public HistoricoJugador(String id, String temporada, Jugador jugador){
		super(id, temporada);
		this.jugador = jugador;
	}

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	@Override
	public String toString() {
		return "HistoricoJugador" + super.toString() +  ", jugador=" + jugador + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((jugador == null) ? 0 : jugador.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		HistoricoJugador other = (HistoricoJugador) obj;
		if (jugador == null) {
			if (other.jugador != null)
				return false;
		} else if (!jugador.equals(other.jugador))
			return false;
		return true;
	}

	
	

}
