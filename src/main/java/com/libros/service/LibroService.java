package com.libros.service;
import com.libros.model.ApiResponse;
import com.libros.model.Libro;
import com.libros.repository.LibroRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    private final LibroRepository libroRepository;
    private final RestTemplate restTemplate;

    public LibroService(LibroRepository libroRepository, RestTemplate restTemplate) {
        this.libroRepository = libroRepository;
        this.restTemplate = restTemplate;
    }

    public Libro buscarLibroPorTitulo(String titulo) {
        
        Optional<Libro> existente = libroRepository.findByTitulo(titulo);
        if (existente.isPresent()) {
            return existente.get();
        }

        // Consumir Gutendex API
        String url = "https://gutendex.com/books?search=" + titulo;
        var respuesta = restTemplate.getForObject(url, ApiResponse.class);

        if (respuesta != null && respuesta.getResults().size() > 0) {
            var libroApi = respuesta.getResults().get(0);

            Libro libro = new Libro();
            libro.setTitulo(libroApi.getTitle());
            libro.setAutor(libroApi.getAuthors().get(0).getLastName() + ", " + libroApi.getAuthors().get(0).getFirstName());
            libro.setIdioma(libroApi.getLanguage());
            libro.setDescargas(libroApi.getDownloadCount());

            libroRepository.save(libro);
            return libro;
        }

        throw new RuntimeException("Libro no encontrado");
    }

    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }
}
