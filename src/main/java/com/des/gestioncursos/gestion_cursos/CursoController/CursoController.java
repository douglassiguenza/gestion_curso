package com.des.gestioncursos.gestion_cursos.CursoController;

import com.des.gestioncursos.gestion_cursos.entity.Curso;
import com.des.gestioncursos.gestion_cursos.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        //Obtener  los cursos y se almacenan en la lista "cursos"
        List<Curso> cursos = cursoRepository.findAll();

        // Se agregan los cursos al modelo para que estén disponibles en la vista
        model.addAttribute("cursos", cursos);

        //Dirige a cursos.html, como es primera llamada simplemente colocamos cursos
        return "cursos";
    }

    //Método que muestra el formulario y alista los datos
    @GetMapping("/cursos/nuevo")
    public String agregarCurso(Model model){
        //Crea un nuevo curso
        Curso curso = new Curso();
        //Por defecto lo coloca en true
        curso.setPublicado(true);

        //se enviará al formulario y los datos se asignan al objeto
        model.addAttribute("curso", curso);

        //Titulo de la página curso_form
        model.addAttribute("pageTitle", "Nuevo curso");

        //Dirige a la página curso_form
        return "curso_form";

    }


    //Método que sirve para guardar un curso
    @PostMapping("/cursos/save")
    public String guardarCurso(Curso curso, RedirectAttributes redirectAttributes){
        try{
            //Para guardar un curso
            cursoRepository.save(curso);

            //Redirect attribute -> para agregar atributos
            //
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
            //Para gaurdar un curso
            Curso curso = cursoRepository.findById(id).get();

            model.addAttribute("pageTitle", "Editar curso : " + curso.getTitulo());
            model.addAttribute("curso", curso);
            return "curso_form";
        }catch(Exception e){
            //Por si no se ha logrado guardar el curso
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


}
