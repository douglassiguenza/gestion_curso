package com.des.gestioncursos.gestion_cursos.CursoController;

import com.des.gestioncursos.gestion_cursos.entity.Curso;
import com.des.gestioncursos.gestion_cursos.reports.CursoExporterExcel2;
import com.des.gestioncursos.gestion_cursos.reports.CursoExporterPDF;
import com.des.gestioncursos.gestion_cursos.repository.CursoRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
public class CursoController {

    //Inyectamos
    @Autowired
    private CursoRepository cursoRepository;

    //Principal

    @GetMapping
    public String home(){
        return "redirect:/cursos";
    }

    //Model -> permite relacionar objetos entre la vista y el controlador
    //Método para listar cursos
    @GetMapping("/cursos")
    public String listarCursos(Model model){

        //1: Obtener  los cursos y se almacenan en la lista "cursos"
        List<Curso> cursos = cursoRepository.findAll();

        //2: Se agregan los cursos al modelo para que estén disponibles en la vista
        model.addAttribute("cursos", cursos);

        //3: Dirige a cursos.html, como es primera llamada simplemente colocamos cursos
        return "cursos";
    }

    //Método que muestra el formulario y prepara los datos
    @GetMapping("/cursos/nuevo")
    public String agregarCurso(Model model){

        //1: Crea un nuevo curso
        Curso curso = new Curso();

        //2: Por defecto establacemos que sea true
        curso.setPublicado(true);

        //3: se enviará al formulario y los datos se asignan al objeto
        model.addAttribute("curso", curso);

        //4: Titulo de la página curso_form
        model.addAttribute("pageTitle", "Nuevo curso");

        //5: Dirige a la página curso_form
        return "curso_form";

    }


    //Método que sirve para guardar un curso
    @PostMapping("/cursos/save")
    public String guardarCurso(Curso curso, RedirectAttributes redirectAttributes){
        try{
            //1: Guardar un curso
            cursoRepository.save(curso);

            //Redirect attribute -> para agregar atributos
            redirectAttributes.addFlashAttribute("message", "El curso ha sido guardado con éxito");
        }catch(Exception e){
            //Por si no se ha logrado guardar el curso
            redirectAttributes.addFlashAttribute("message", e.getMessage());

        }

        //Como es tercera llamada hacemos uso de redirect
        //Porque rediciona a un archivo
        return "redirect:/cursos";

    }

    @GetMapping("/cursos/{id}")
    public String editarCurso(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes){
        try{
            //Para guardar un curso
            Curso curso = cursoRepository.findById(id).get();

            model.addAttribute("pageTitle", "Editar curso : " + curso.getTitulo());
            model.addAttribute("curso", curso);
            return "curso_form";
        }catch(Exception e){
            //Por si no se logra guardar el curso
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        //Como es tercera llamada hacemos uso de redirect
        return "redirect:/cursos";

    }

    @GetMapping("/cursos/delete/{id}")
    public String eliminarCurso(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes){
        try {
            cursoRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Curso eliminado con éxito");
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("message", e.getMessage());

        }
        return "redirect:/cursos";

    }

    //Método para generar y descargar un informe en formato PDF que contiene la lista de cursos
    @GetMapping("/export/pdf")
    public void generarReportePDF(HttpServletResponse response) throws IOException {

        // 1: Establecer el tipo de contenido de la respuesta como PDF
        response.setContentType("application/pdf");

        // 2: Obtener la fecha y hora actual para el nombre del archivo
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());

        // Configurar el encabezado para indicar que el archivo se descargará como un archivo adjunto con el nombre "cursos + fecha y hora actual + .pdf"
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=cursos" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        // Obtener la lista de cursos desde el repositorio
        List<Curso> cursos = cursoRepository.findAll();

        // Crear un objeto CursoExporterPDF con la lista de cursos obtenida
        CursoExporterPDF exporterPDF = new CursoExporterPDF(cursos);

        // Exportar la lista de cursos como un documento PDF usando el método export
        exporterPDF.export(response);
    }

    //sirve para generar y descargar un informe en formato xlsx que contiene la lista de cursos
    @GetMapping("/export/excel")
    public void generarReporteExcel(HttpServletResponse response) throws IOException {
        // Establecer el tipo de contenido de la respuesta como una secuencia de octetos (archivo)
        response.setContentType("application/octet-stream");

        // Obtener la fecha y hora actual para el nombre del archivo
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());

        // Configurar el encabezado para indicar que el archivo se descargará como un archivo adjunto con el nombre "cursos + fecha y hora actual + .xlsx"
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=cursos" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        // Obtener la lista de cursos desde el repositorio
        List<Curso> cursos = cursoRepository.findAll();

        // Crear un objeto CursoExporterExcel2 con la lista de cursos obtenida
        CursoExporterExcel2 exporterExcel2 = new CursoExporterExcel2(cursos);

        // Exportar la lista de cursos como un archivo Excel usando el método export
        exporterExcel2.export(response);
    }


}
