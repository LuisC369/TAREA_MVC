package com.umg.edu.controlador;

import com.umg.edu.dao.PuestoDAO;
import com.umg.edu.modelo.Puesto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador del modulo Puestos. Mismo patron que ClienteControlador.
 *
 * @author Luis
 */
@Controller
@RequestMapping("/puestos")
public class PuestoControlador {

    private final PuestoDAO dao = new PuestoDAO();

    // GET /puestos -> lista de puestos
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("puestos", dao.listarTodos());
        return "puestos/lista";
    }

    // GET /puestos/nuevo -> formulario vacio
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("puesto", new Puesto());
        return "puestos/formulario";
    }

    // GET /puestos/editar/5 -> formulario con los datos del puesto 5
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model, RedirectAttributes flash) {
        Puesto puesto = dao.buscarPorId(id);
        if (puesto == null) {
            flash.addFlashAttribute("error", "No se encontro el puesto");
            return "redirect:/puestos";
        }
        model.addAttribute("puesto", puesto);
        return "puestos/formulario";
    }

    // POST /puestos/guardar
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Puesto puesto, Model model, RedirectAttributes flash) {
        if (puesto.getNombre().trim().isEmpty() || puesto.getSalarioBase() <= 0) {
            model.addAttribute("error", "El nombre es obligatorio y el salario debe ser mayor a 0");
            return "puestos/formulario";
        }

        // Si el id es 0 es nuevo, si no, se actualiza
        boolean ok;
        if (puesto.getIdPuesto() == 0) {
            ok = dao.insertar(puesto);
        } else {
            ok = dao.actualizar(puesto);
        }

        if (ok) {
            flash.addFlashAttribute("mensaje", "Puesto guardado correctamente");
            return "redirect:/puestos";
        }
        model.addAttribute("error", "No se pudo guardar el puesto");
        return "puestos/formulario";
    }

    // GET /puestos/eliminar/5
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id, RedirectAttributes flash) {
        Puesto puesto = dao.buscarPorId(id);
        if (puesto != null && dao.eliminar(puesto)) {
            flash.addFlashAttribute("mensaje", "Puesto eliminado correctamente");
        } else {
            flash.addFlashAttribute("error", "No se pudo eliminar el puesto (tiene empleados asignados)");
        }
        return "redirect:/puestos";
    }
}
