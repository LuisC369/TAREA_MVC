package com.umg.edu.controlador;

import com.umg.edu.dao.MarcaDAO;
import com.umg.edu.modelo.Marca;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador del modulo Marcas. Mismo patron que ClienteControlador.
 *
 * @author Luis
 */
@Controller
@RequestMapping("/marcas")
public class MarcaControlador {

    private final MarcaDAO dao = new MarcaDAO();

    // GET /marcas -> lista de marcas
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("marcas", dao.listarTodos());
        return "marcas/lista";
    }

    // GET /marcas/nuevo -> formulario vacio
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("marca", new Marca());
        return "marcas/formulario";
    }

    // GET /marcas/editar/5 -> formulario con los datos de la marca 5
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model, RedirectAttributes flash) {
        Marca marca = dao.buscarPorId(id);
        if (marca == null) {
            flash.addFlashAttribute("error", "No se encontro la marca");
            return "redirect:/marcas";
        }
        model.addAttribute("marca", marca);
        return "marcas/formulario";
    }

    // POST /marcas/guardar
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Marca marca, Model model, RedirectAttributes flash) {
        if (marca.getNombre().trim().isEmpty()) {
            model.addAttribute("error", "El nombre de la marca es obligatorio");
            return "marcas/formulario";
        }

        // Si el id es 0 es nueva, si no, se actualiza
        boolean ok;
        if (marca.getIdMarca() == 0) {
            ok = dao.insertar(marca);
        } else {
            ok = dao.actualizar(marca);
        }

        if (ok) {
            flash.addFlashAttribute("mensaje", "Marca guardada correctamente");
            return "redirect:/marcas";
        }
        model.addAttribute("error", "No se pudo guardar la marca");
        return "marcas/formulario";
    }

    // GET /marcas/eliminar/5
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id, RedirectAttributes flash) {
        Marca marca = dao.buscarPorId(id);
        if (marca != null && dao.eliminar(marca)) {
            flash.addFlashAttribute("mensaje", "Marca eliminada correctamente");
        } else {
            flash.addFlashAttribute("error", "No se pudo eliminar la marca (tiene productos asignados)");
        }
        return "redirect:/marcas";
    }
}
