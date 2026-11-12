package com.academico.sistema.controller;

import com.academico.sistema.model.Estudiante;
import com.academico.sistema.service.EstudianteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping({"/", "/estudiantes"})
public class EstudianteController {

    private final EstudianteService service;

    public EstudianteController(EstudianteService service) {
        this.service = service;
    }

    @GetMapping
    public String vistaPrincipal(@RequestParam(value = "filtro", required = false) String filtro, Model model) {
        if (!model.containsAttribute("estudiante")) {
            model.addAttribute("estudiante", new Estudiante());
        }
        model.addAttribute("estudiantes", service.listarTodos(filtro));
        model.addAttribute("filtro", filtro);
        return "estudiantes";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("estudiante") Estudiante estudiante,
                          BindingResult result,
                          Model model,
                          RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("estudiantes", service.listarTodos(null));
            model.addAttribute("errorValidacion", "Por favor corrige los errores señalados en el formulario.");
            return "estudiantes";
        }

        try {
            boolean esEdicion = (estudiante.getId() != null);
            service.guardar(estudiante);
            flash.addFlashAttribute("exito", esEdicion
                    ? "Estudiante actualizado correctamente."
                    : "Estudiante registrado exitosamente.");
        } catch (IllegalArgumentException e) {
            model.addAttribute("estudiantes", service.listarTodos(null));
            model.addAttribute("errorNegocio", e.getMessage());
            return "estudiantes";
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Ocurrió un error inesperado al guardar el registro.");
        }

        return "redirect:/estudiantes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, RedirectAttributes flash) {
        return service.buscarPorId(id)
                .map(est -> {
                    flash.addFlashAttribute("estudiante", est);
                    return "redirect:/estudiantes";
                })
                .orElseGet(() -> {
                    flash.addFlashAttribute("error", "No se encontró el estudiante solicitado.");
                    return "redirect:/estudiantes";
                });
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes flash) {
        try {
            service.eliminar(id);
            flash.addFlashAttribute("exito", "Estudiante eliminado correctamente.");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al eliminar: " + e.getMessage());
        }
        return "redirect:/estudiantes";
    }
}