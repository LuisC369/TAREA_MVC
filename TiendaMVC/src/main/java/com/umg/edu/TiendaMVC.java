package com.umg.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal. Levanta el servidor Tomcat que trae Spring Boot.
 * Una vez corriendo, se abre en el navegador: http://localhost:8080
 *
 * Esta clase esta en com.umg.edu para que Spring encuentre los
 * controladores que estan en com.umg.edu.controlador.
 *
 * @author Luis
 */
@SpringBootApplication
public class TiendaMVC {

    public static void main(String[] args) {
        SpringApplication.run(TiendaMVC.class, args);
    }
}
