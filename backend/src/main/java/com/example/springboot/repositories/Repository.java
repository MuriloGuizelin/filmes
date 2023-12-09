package com.example.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springboot.models.Entities;

import java.util.UUID;

public interface Repository extends JpaRepository<Entities, UUID>{

}
