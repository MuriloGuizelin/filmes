package com.example.springboot.controllers;

import com.example.springboot.dtos.FilmeDTO;
import com.example.springboot.models.FilmeEntity;
import com.example.springboot.repositories.FilmeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class FilmeControlle {
	
	@Autowired
	FilmeRepository filmes;
	
	@GetMapping("/filmes")
	public ResponseEntity<List<FilmeEntity>> getAllFilmes(){
		List<FilmeEntity> Filmes = filmes.findAll();
		if(!Filmes.isEmpty()) {
			for(FilmeEntity filme : Filmes) {
				UUID id = filme.getId();
				filme.add(linkTo(methodOn(FilmeControlle.class).getOneFilme(id)).withSelfRel());
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(Filmes);
	}

	@GetMapping("/filmes/{id}")
	public ResponseEntity<Object> getOneFilme(@PathVariable(value="id") UUID id){
		Optional<FilmeEntity> Filme = filmes.findById(id);
		if(Filme.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Filme não encontrado.");
		}
		Filme.get().add(linkTo(methodOn(FilmeControlle.class).getAllFilmes()).withRel("Lista dos filmes"));
		return ResponseEntity.status(HttpStatus.OK).body(Filme.get());
	}
	
	@PostMapping("/filmes")
	public ResponseEntity<FilmeEntity> saveFime(@RequestBody @Valid FilmeDTO FilmeRecordDto) {
		var Entidade = new FilmeEntity();
		BeanUtils.copyProperties(FilmeRecordDto, Entidade);
		return ResponseEntity.status(HttpStatus.CREATED).body(filmes.save(Entidade));
	}
	
	@DeleteMapping("/filmes/{id}")
	public ResponseEntity<Object> deleteFilme(@PathVariable(value="id") UUID id) {
		Optional<FilmeEntity> filme = filmes.findById(id);
		if(filme.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Filme não encontrado.");
		}
		filmes.delete(filme.get());
		return ResponseEntity.status(HttpStatus.OK).body("Filme deletado com sucesso.");
	}
	
	@PutMapping("/filmes/{id}")
	public ResponseEntity<Object> updateProduct(@PathVariable(value="id") UUID id, @RequestBody @Valid FilmeDTO FilmeRecordDto) {
		Optional<FilmeEntity> filme = filmes.findById(id);
		if(filme.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Filme não encontrado.");
		}
		var Entidade = filme.get();
		BeanUtils.copyProperties(FilmeRecordDto, Entidade);
		return ResponseEntity.status(HttpStatus.OK).body(filmes.save(Entidade));
	}

}
