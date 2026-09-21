package com.umg.edu.controlador;

import com.umg.edu.dao.ClienteDAO;
import com.umg.edu.modelo.Cliente;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador del modulo Clientes. Recibe las peticiones del navegador,
 * le pide los datos al DAO (Modelo) y decide que vista mostrar.
 * La vista nunca habla con el DAO directamente.
 *
 * @author Luis
 */
@Controller
@RequestMapping("/clientes")
public class ClienteControlador {

    private final ClienteDAO dao = new ClienteDAO();

    // GET /clientes -> lista de clientes
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", dao.listarTodos());
        return "clientes/lista";
    }

    // GET /clientes/nuevo -> formulario vacio
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/formulario";
    }

    // GET /clientes/editar/5 -> formulario con los datos del cliente 5
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model, RedirectAttributes flash) {
        Cliente cliente = dao.buscarPorId(id);
        if (cliente == null) {
            flash.addFlashAttribute("error", "No se encontro el cliente");
            return "redirect:/clientes";
        }
        model.addAttribute("cliente", cliente);
        return "clientes/formulario";
    }

    // POST /clientes/guardar -> Spring arma el Cliente con los campos del formulario
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Cliente cliente, Model model, RedirectAttributes flash) {
        // Validar antes de tocar la base de datos
        if (cliente.getNit().trim().isEmpty() || cliente.getNombre().trim().isEmpty()
                || cliente.getApellidos().trim().isEmpty()) {
            model.addAttribute("error", "El NIT, el nombre y los apellidos son obligatorios");
            return "clientes/formulario";
        }

        // Si el id es 0 es nuevo (AUTO_INCREMENT), si no, se actualiza
        boolean ok;
        if (cliente.getId() == 0) {
            ok = dao.insertar(cliente);
        } else {
            ok = dao.actualizar(cliente);
        }

        if (ok) {
            flash.addFlashAttribute("mensaje", "Cliente guardado correctamente");
            return "redirect:/clientes";
        }
        model.addAttribute("error", "No se pudo guardar el cliente (revise que el NIT no este repetido)");
        return "clientes/formulario";
    }

    // GET /clientes/eliminar/5
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id, RedirectAttributes flash) {
        Cliente cliente = dao.buscarPorId(id);
        if (cliente != null && dao.eliminar(cliente)) {
            flash.addFlashAttribute("mensaje", "Cliente eliminado correctamente");
        } else {
            flash.addFlashAttribute("error", "No se pudo eliminar el cliente (puede tener facturas)");
        }
        return "redirect:/clientes";
    }
}
