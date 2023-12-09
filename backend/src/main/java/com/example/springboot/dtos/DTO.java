package com.example.springboot.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DTO(@NotBlank String nome, @NotNull Integer ano) {
}
