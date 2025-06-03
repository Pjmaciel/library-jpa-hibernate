package br.com.fuctura.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    private String name;
    private String description;

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public boolean isValid() {
        return name != null && !name.trim().isEmpty()
                && description != null && !description.trim().isEmpty();
    }

    // Sobrescrever toString do Lombok.
    @Override
    public String toString() {
        return String.format("Category{id=%d, name='%s', description='%s'}",
                id,
                name != null ? name : "null",
                description != null ? (description.length() > 50 ? description.substring(0, 47) + "..." : description) : "null"
        );
    }
}