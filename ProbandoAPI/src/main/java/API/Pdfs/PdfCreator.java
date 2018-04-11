package API.Pdfs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import API.Acta.Acta;
import API.Acta.ActaRepository;
import API.Equipo.Equipo;
import API.Equipo.EquipoRepository;
import API.Incidencia.Incidencia;
import API.Jugador.Jugador;
import API.Jugador.JugadorRepository;
import API.Partido.Partido;
import API.Partido.PartidoRepository;

@Service
public class PdfCreator {
	@Autowired
	private PartidoRepository partidoRepository;
	@Autowired
	private JugadorRepository jugadorRepository;
	@Autowired
	private EquipoRepository equipoRepository;

	public String crearPdf(Acta acta) throws DocumentException, MalformedURLException, IOException {
		Partido partidoDelActa = partidoRepository.findById(acta.getIdPartido());
		if (partidoDelActa != null) {
			Document document = new Document();
			PdfPTable tablaDatosIniciales = new PdfPTable(2);
			PdfPTable tablaEquipos = new PdfPTable(4);
			PdfPTable tablaCabeceraConvocados = new PdfPTable(2);
			PdfPTable tablaContenidoConvocados = new PdfPTable(4);
			PdfPTable tablaCabeceraIncidencias = new PdfPTable(1);
			PdfPTable tablaContenidoIncidencias = new PdfPTable(5);
			PdfPTable tablaObservaciones = new PdfPTable(1);
			PdfPCell celdaEquipo = new PdfPCell();
			PdfPCell celdaEquipo1 = new PdfPCell();
			//Paragraph nombreLocal = new Paragraph(acta.getEquipoLocal().getNombre());
			//Paragraph nombreVisitante = new Paragraph(acta.getEquipoVisitante().getNombre());
			Paragraph golesLocal = new Paragraph(Integer.toString(acta.getGolesLocal()), FontFactory.getFont("arial", // fuente
					50));
			Paragraph golesVisitante = new Paragraph(Integer.toString(acta.getGolesVisitante()),
					FontFactory.getFont("arial", // fuente
							50));
			PdfPCell celdaGolesLocal = new PdfPCell();
			PdfPCell celdaGolesVisitante = new PdfPCell();
			PdfPCell txtEquipoLocal = new PdfPCell(new Phrase("Equipo Local"));
			PdfPCell txtEquipoVisitante = new PdfPCell(new Phrase("Equipo Visitante"));
			PdfPCell txtGoles = new PdfPCell(new Phrase("Goles"));
			PdfPCell txtConvocadosEquipoLocal = new PdfPCell(new Phrase("Convocados Equipo Local"));
			PdfPCell txtConvocadosEquipoVisitante = new PdfPCell(new Phrase("Convocados Equipo Visitante"));
			PdfPCell txtDorsal = new PdfPCell(new Phrase("Dorsal"));
			PdfPCell txtNombreApellidos = new PdfPCell(new Phrase("Nombre"));
			PdfPCell txtIncidencias = new PdfPCell(new Phrase("Incidencias del partido"));
			PdfPCell txtTipo = new PdfPCell(new Phrase("Tipo"));
			PdfPCell txtEquipo = new PdfPCell(new Phrase("Equipo"));
			PdfPCell txtMinuto = new PdfPCell(new Phrase("Minuto"));
			PdfPCell txtObservaciones = new PdfPCell(new Phrase("Observaciones del partido"));
			// El nombre del archivo constará del equipo local, el equipo visitante y la
			// fecha.
			//String nombreArchivo = acta.getEquipoLocal().getNombre() + acta.getEquipoVisitante().getNombre()
					//+ acta.getFecha();
			File folder = new File ("src/main/resources/static/actasGeneradas/" + partidoDelActa.getLiga());
			folder.mkdir();
			//FileOutputStream ficheroPDF = new FileOutputStream(
			//		"src/main/resources/static/actasGeneradas/" + partidoDelActa.getLiga() + "/" + nombreArchivo);
			//PdfWriter.getInstance(document, ficheroPDF).setInitialLeading(20);
			document.open();
			// Se añade la imagen corporativa de la URJC.
			Image imagen = Image.getInstance("src/main/resources/static/images/urjc.png");
			imagen.scaleToFit(100, 200);
			imagen.setAlignment(Chunk.ALIGN_LEFT);
			document.add(imagen);
			// Se añade el título de la liga del partido.
			Paragraph tituloLigaParagraph = new Paragraph("Liga " + partidoDelActa.getLiga(),
					FontFactory.getFont("arial", // fuente
							22));
			tituloLigaParagraph.setAlignment(Chunk.ALIGN_CENTER);
			document.add(tituloLigaParagraph);
			tablaDatosIniciales.setSpacingBefore(20);
			tablaDatosIniciales.addCell("Lugar");
			tablaDatosIniciales.addCell(partidoDelActa.getEstadio().getPoblacion() + " ("
					+ partidoDelActa.getEstadio().getProvincia() + ")");
			tablaDatosIniciales.addCell("Estadio");
			tablaDatosIniciales.addCell(partidoDelActa.getEstadio().getNombre());
			tablaDatosIniciales.addCell("Fecha");
			tablaDatosIniciales.addCell(acta.getFecha());
			tablaDatosIniciales.addCell("Hora");
			tablaDatosIniciales.addCell(acta.getHora());
			tablaDatosIniciales.addCell("Jornada");
			tablaDatosIniciales.addCell(Integer.toString(partidoDelActa.getJornada()));
			tablaDatosIniciales.addCell("Árbitro");
			//tablaDatosIniciales.addCell(acta.getArbitro().getNombre());
			tablaDatosIniciales.setTotalWidth(100);
			//celdaEquipo.addElement(
					//Image.getInstance("src/main/resources/static/images/" + acta.getEquipoLocal().getImagenEquipo()));
			//nombreLocal.setAlignment(Chunk.ALIGN_CENTER);
			//celdaEquipo.addElement(nombreLocal);
			//celdaEquipo1.addElement(Image
					//.getInstance("src/main/resources/static/images/" + acta.getEquipoVisitante().getImagenEquipo()));
			//nombreVisitante.setAlignment(Chunk.ALIGN_CENTER);
			//celdaEquipo1.addElement(nombreVisitante);
			txtEquipoLocal.setBackgroundColor(BaseColor.GRAY);
			txtEquipoVisitante.setBackgroundColor(BaseColor.GRAY);
			txtGoles.setBackgroundColor(BaseColor.GRAY);
			tablaEquipos.addCell(txtEquipoLocal);
			tablaEquipos.addCell(txtGoles);
			tablaEquipos.addCell(txtEquipoVisitante);
			tablaEquipos.addCell(txtGoles);
			tablaEquipos.addCell(celdaEquipo);
			golesLocal.setAlignment(Chunk.ALIGN_CENTER);
			celdaGolesLocal.addElement(golesLocal);
			celdaGolesLocal.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablaEquipos.addCell(celdaGolesLocal);
			tablaEquipos.addCell(celdaEquipo1);
			golesVisitante.setAlignment(Chunk.ALIGN_CENTER);
			celdaGolesVisitante.addElement(golesVisitante);
			// celdaGol.setVerticalAlignment(Element.ALIGN_MIDDLE);
			celdaGolesVisitante.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablaEquipos.addCell(celdaGolesVisitante);
			document.add(tablaDatosIniciales);
			tablaEquipos.setSpacingBefore(10);
			tablaCabeceraConvocados.setSpacingBefore(10);
			txtConvocadosEquipoLocal.setBackgroundColor(BaseColor.GRAY);
			txtConvocadosEquipoVisitante.setBackgroundColor(BaseColor.GRAY);
			tablaCabeceraConvocados.addCell(txtConvocadosEquipoLocal);
			tablaCabeceraConvocados.addCell(txtConvocadosEquipoVisitante);
			txtDorsal.setBackgroundColor(BaseColor.WHITE.darker());
			txtNombreApellidos.setBackgroundColor(BaseColor.WHITE.darker());
			tablaContenidoConvocados.addCell(txtDorsal);
			tablaContenidoConvocados.addCell(txtNombreApellidos);
			tablaContenidoConvocados.addCell(txtDorsal);
			tablaContenidoConvocados.addCell(txtNombreApellidos);
			for (int i = 0; i < Math.max(acta.getConvocadosLocal().size(), acta.getConvocadosVisitante().size()); i++) {
				if (acta.getConvocadosLocal().size() > i) {
					if (acta.getConvocadosLocal().get(i) == null) {
						tablaContenidoConvocados.addCell("");
						tablaContenidoConvocados.addCell("");
					} else {
						tablaContenidoConvocados
								.addCell(Integer.toString((acta.getConvocadosLocal().get(i).getDorsal())));
						tablaContenidoConvocados.addCell(acta.getConvocadosLocal().get(i).getNombre() + " "
								+ acta.getConvocadosLocal().get(i).getApellidos());
					}
				} else {
					tablaContenidoConvocados.addCell("");
					tablaContenidoConvocados.addCell("");
				}
				if (acta.getConvocadosVisitante().size() > i) {
					if (acta.getConvocadosVisitante().get(i) == null) {
						tablaContenidoConvocados.addCell("");
						tablaContenidoConvocados.addCell("");
					} else {
						tablaContenidoConvocados
								.addCell(Integer.toString((acta.getConvocadosVisitante().get(i).getDorsal())));
						tablaContenidoConvocados.addCell(acta.getConvocadosVisitante().get(i).getNombre() + " "
								+ acta.getConvocadosVisitante().get(i).getApellidos());
					}
				} else {
					tablaContenidoConvocados.addCell("");
					tablaContenidoConvocados.addCell("");
				}
			}
			txtIncidencias.setBackgroundColor(BaseColor.GRAY);
			tablaCabeceraIncidencias.addCell(txtIncidencias);
			txtTipo.setBackgroundColor(BaseColor.GRAY.brighter());
			txtEquipo.setBackgroundColor(BaseColor.GRAY.brighter());
			txtMinuto.setBackgroundColor(BaseColor.GRAY.brighter());
			txtIncidencias.setBackgroundColor(BaseColor.GRAY.brighter());
			txtObservaciones.setBackgroundColor(BaseColor.GRAY);
			tablaCabeceraIncidencias.setSpacingBefore(10);
			tablaContenidoIncidencias.addCell(txtTipo);
			tablaContenidoIncidencias.addCell(txtEquipo);
			tablaContenidoIncidencias.addCell(txtDorsal);
			tablaContenidoIncidencias.addCell(txtNombreApellidos);
			tablaContenidoIncidencias.addCell(txtMinuto);
			for (Incidencia incidencia : acta.getIncidencias()) {
				Jugador jugadorIncidencia = jugadorRepository.findById(incidencia.getIdJugador());
				if (jugadorIncidencia == null) {
					throw (new RemoteException());
				} else {
					Equipo equipoIncidencia = equipoRepository.findById(jugadorIncidencia.getEquipo());
					if (equipoIncidencia == null) {
						throw (new RemoteException());
					} else {
						tablaContenidoIncidencias.addCell(incidencia.getTipo());
						tablaContenidoIncidencias.addCell(equipoIncidencia.getNombre());
						tablaContenidoIncidencias.addCell(Integer.toString(jugadorIncidencia.getDorsal()));
						tablaContenidoIncidencias
								.addCell(jugadorIncidencia.getNombre() + " " + jugadorIncidencia.getApellidos());
						tablaContenidoIncidencias.addCell(incidencia.getMinuto());
					}
				}
			}
			document.add(tablaEquipos);
			document.add(tablaCabeceraConvocados);
			document.add(tablaContenidoConvocados);
			document.add(tablaCabeceraIncidencias);
			document.add(tablaContenidoIncidencias);
			if (acta.getObservaciones() != null || !acta.getObservaciones().isEmpty()) {
				tablaObservaciones.setSpacingBefore(10);
				tablaObservaciones.addCell(txtObservaciones);
				tablaObservaciones.addCell(acta.getObservaciones());
				document.add(tablaObservaciones);
			}
			document.close();
			return null;
			//return nombreArchivo;
		} else {
			return null;
		}
	}
}
