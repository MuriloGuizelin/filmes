package com.example.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springboot.models.FilmeEntity;

import java.util.UUID;

public interface FilmeRepository extends JpaRepository<FilmeEntity, UUID>{

}
