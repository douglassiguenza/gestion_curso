package com.des.gestioncursos.gestion_cursos.reports;

import com.des.gestioncursos.gestion_cursos.entity.Curso;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

public class CursoExporterExcel2 {
    // Crear un objeto XSSFWorkbook para el libro de Excel
    private XSSFWorkbook workbook;

    // Crear un objeto XSSFSheet para la hoja de Excel
    private XSSFSheet sheet;

    // Crear una lista de cursos
    private List<Curso> cursos;

    public CursoExporterExcel2(List<Curso> cursos) {
        // Inicializar la lista de cursos
        this.cursos = cursos;

        // Inicializar el libro de Excel
        workbook = new XSSFWorkbook();
    }

    // Método para crear la cabecera
    private void writeHeaderLine(){
        // 1: Crear una nueva hoja de Excel llamada "Cursos"
        sheet = workbook.createSheet("Cursos");

        // 2: Crear una fila en la hoja
        Row row = sheet.createRow(0);

        // 3: Crear un estilo de celda
        CellStyle style = workbook.createCellStyle();

        // 4: Crear una fuente
        XSSFFont font = workbook.createFont();

        // 5: Establecer negrita para la fuente
        font.setBold(true);

        // 6: Establecer tamaño de fuente
        font.setFontHeight(16);

        // 7: Establecer la fuente en el estilo
        style.setFont(font);

        // 8: Crear celdas para la cabecera de la tabla con el estilo especificado
        createCell(row, 0, "Id", style);
        createCell(row, 1, "Título", style);
        createCell(row, 2, "Descripción", style);
        createCell(row, 3, "Nivel", style);
        createCell(row, 4, "Estado de publicación", style);
    }

    //Método para crear una nueva celda en una fila dada
    private void createCell(Row row, int columnCount, Object value, CellStyle style){
        // 1: Autoajustar el ancho de la columna
        sheet.autoSizeColumn(columnCount);

        // 2: Crear una celda en la fila y columna especificadas
        Cell cell = row.createCell(columnCount);

        // 3: Según el tipo de valor, establecer el contenido de la celda
        if(value instanceof Integer){
            cell.setCellValue((Integer)value);
        } else if(value instanceof Boolean){
            cell.setCellValue((Boolean)value);
        } else{
            cell.setCellValue((String)value);
        }

        // 4: Aplicar el estilo de celda
        cell.setCellStyle(style);
    }

    // Método para escribir los datos de cada curso en filas
    private void writeDataLinea(){
        // 1: Variable que contará las filas
        int rowCount = 1;

        // 2: Crear un estilo de celda
        CellStyle style = workbook.createCellStyle();

        // 3: Crear una fuente
        XSSFFont font = workbook.createFont();

        // 4: Establecer tamaño de fuente
        font.setFontHeight(14);

        // 5: Establecer la fuente en el estilo
        style.setFont(font);

        // Iterar sobre la lista de cursos y escribir los datos en las celdas
        for (Curso curso : cursos){
            // 6: Crear una nueva fila
            Row row = sheet.createRow(rowCount++);

            // 7: Inicializar el contador de columnas
            int columnCount = 0;

            // 8: Escribir datos de cada curso en las celdas de la fila con el estilo especificado
            createCell(row, columnCount++, curso.getId(), style);
            createCell(row, columnCount++, curso.getTitulo(), style);
            createCell(row, columnCount++, curso.getDescripcion(), style);
            createCell(row, columnCount++, curso.getNivel(), style);
            createCell(row, columnCount++, curso.isPublicado(), style);
        }
    }

    //Método que sirve para exportar
    public void export(HttpServletResponse response) throws IOException {
        // 1: Escribir la cabecera de la tabla
        writeHeaderLine();

        // 2: Escribir los datos de la tabla
        writeDataLinea();

        // 3: Obtener el OutputStream de la respuesta
        ServletOutputStream outputStream = response.getOutputStream();

        // 4: Escribir el libro de Excel en el OutputStream
        workbook.write(outputStream);

        // 5: Cerrar el libro de Excel
        workbook.close();

        // 6: Cerrar el OutputStream
        outputStream.close();
    }

}
