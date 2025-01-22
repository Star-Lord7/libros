package com.libros.controller;
import com.libros.model.Libro;
import com.libros.service.LibroService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsolaController implements CommandLineRunner {

    private final LibroService libroService;

    public ConsolaController(LibroService libroService) {
        this.libroService = libroService;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Buscar libro por título");
            System.out.println("2. Listar libros");
            System.out.println("3. Salir");

            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    System.out.println("Ingrese el título del libro:");
                    String titulo = scanner.nextLine();
                    try {
                        Libro libro = libroService.buscarLibroPorTitulo(titulo);
                        System.out.println("Libro encontrado: " + libro.getTitulo() + ", Autor: " + libro.getAutor());
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 2:
                    libroService.listarLibros().forEach(libro -> System.out.println(libro.getTitulo()));
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }
}
