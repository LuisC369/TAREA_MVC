package com.umg.edu.controlador;

import com.umg.edu.dao.ClienteDAO;
import com.umg.edu.dao.MarcaDAO;
import com.umg.edu.dao.PuestoDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador de la pagina de inicio. Muestra cuantos registros hay en
 * cada modulo.
 *
 * @author Luis
 */
@Controller
public class InicioControlador {

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("totalClientes", new ClienteDAO().listarTodos().size());
        model.addAttribute("totalMarcas", new MarcaDAO().listarTodos().size());
        model.addAttribute("totalPuestos", new PuestoDAO().listarTodos().size());
        return "index"; // templates/index.html
    }
}
