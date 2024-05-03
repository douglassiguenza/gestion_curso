package com.des.gestioncursos.gestion_cursos.reports;

import com.des.gestioncursos.gestion_cursos.entity.Curso;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class CursoExporterPDF {

    // 1: Crear lista de cursos
    private List<Curso> listaCursos;

    // Crear el constructor
    public CursoExporterPDF(List<Curso> listaCursos) {
        this.listaCursos = listaCursos;
    }

    // Método para crear la cabecera de la tabla
    private void writeTableHeader(PdfPTable table){

        // 1:  Crear una celda para la cabecera de la tabla
        PdfPCell cell = new PdfPCell();

        // 2: Establecer el color de fondo de la celda
        cell.setBackgroundColor(Color.BLUE);

        // 3: Establecer el padding de la celda
        cell.setPadding(5);

        // 4: Crear una fuente para el texto de la cabecera
        Font font = FontFactory.getFont(FontFactory.HELVETICA);

        // 5: Establecer el color del texto como blanco
        font.setColor(Color.WHITE);

        // 6: Establecer el texto y la fuente para cada celda de la cabecera
        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Titulo", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Descripcion", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Nivel", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Publicado", font));
        table.addCell(cell);
    }

    // Método para poder escribir datos
    private void writeTableData(PdfPTable table){
        // 1: Iterar sobre la lista de cursos y añadir los datos a la tabla
        for(Curso curso : listaCursos){
            table.addCell(String.valueOf(curso.getId())); // Añade ID del curso
            table.addCell(curso.getTitulo()); // Añade título del curso
            table.addCell(curso.getDescripcion()); // Añade descripción del curso
            table.addCell(String.valueOf(curso.getNivel())); // Añade nivel del curso
            table.addCell(String.valueOf(curso.isPublicado())); // Añade estado de publicación del curso
        }
    }

    // Método para exportar el documento PDF
    public void export(HttpServletResponse response) throws IOException {
        // 1: Crear un nuevo documento PDF con tamaño A4
        Document document = new Document(PageSize.A4);
        // 2: Obtener la instancia de PdfWriter para escribir en el OutputStream de la respuesta
        PdfWriter.getInstance(document, response.getOutputStream());

        // 3: Abrir el documento
        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA); // Crear una fuente
        font.setSize(18); // Establecer el tamaño de la fuente
        font.setColor(Color.BLUE); // Establecer el color de la fuente

        // 4: crear un parrafo para agregar el nombre del autor en el pdf
        Paragraph author = new Paragraph("Des", font);

        // 5: Crear un párrafo para el título de la lista de cursos
        Paragraph p = new Paragraph("Lista de cursos", font);

        p.setAlignment(Paragraph.ALIGN_CENTER); // alinea al centro

        document.add(author); // Agregar el párrafo del autor al documento
        document.add(p); // Agregar el párrafo del título al documento

        // Crear una tabla con 5 columnas
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f); // Establecer el ancho de la tabla al 100%
        table.setWidths(new float[]{1.3f, 3.5f, 3.5f, 2.0f, 1.5f}); // Establecer los anchos relativos de las columnas
        table.setSpacingBefore(10); // Establecer espacio antes de la tabla

        writeTableHeader(table); // Escribir la cabecera de la tabla
        writeTableData(table); // Escribir los datos de la tabla

        document.add(table); // Agregar la tabla al documento
        document.close(); // Cerrar el documento
    }

}
