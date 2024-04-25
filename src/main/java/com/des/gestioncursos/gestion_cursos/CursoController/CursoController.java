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
    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public String home(){
        return "redirect:/cursos";
    }

    //Model -> agregar prductos para enviar a la vista
    //Principal
    @GetMapping("/cursos")
    public String listarCursos(Model model){
        List<Curso> cursos = cursoRepository.findAll(); //Listar los cursos
        cursos = cursoRepository.findAll();

        model.addAttribute("cursos", cursos);

        //Como es primera llamada simplemente cursos
        return "cursos";
    }

    //Método que muestra el formulario y los datos que les guarde
    @GetMapping("/cursos/nuevo")
    public String agregarCurso(Model model){
        Curso curso = new Curso();
        curso.setPublicado(true); //Por defecto

        model.addAttribute("curso", curso); //se enviará al formulario y los datos se asignan al objeto
        model.addAttribute("pageTitle", "Nuevo curso");

        return "curso_form";

    }


    //Redirect attribute -> para agregar atributos
    //Cuando le de en guardar formulario se ejecuta este método
    @PostMapping("/cursos/save")
    public String guardarCurso(Curso curso, RedirectAttributes redirectAttributes){
        try{
            //Para gaurdar un curso
            cursoRepository.save(curso);
            redirectAttributes.addFlashAttribute("message", "El curso ha sido guardado con éxito");
        }catch(Exception e){
            //Por si no se ha logrado guardar el curso
            redirectAttributes.addFlashAttribute("message", e.getMessage());

        }

        //Como es tercera llamada hacemos uso de redirect
        return "redirect:/cursos";

    }

    //Porque rediciona a un archivo
    @GetMapping("/cursos/{id}")
    public String editarCurso(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes){
        try{
            //Para gaurdar un curso
            Curso curso = cursoRepository.findById(id).get();

            model.addAttribute("pageTitle", "Editar curso : " + curso.getTitulo());
            model.addAttribute("curso", curso);
            //redirectAttributes.addFlashAttribute("message", "El curso ha sido actualizado con éxito");
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
