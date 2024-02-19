package com.today.demo.entity;

import com.today.demo.dto.CategoryDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cate_id")
    private int id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<Marker> markers = new ArrayList<>();

    @OneToMany(mappedBy = "category")
    private List<Board> boards = new ArrayList<>();


    public void updateCategory(CategoryDTO categoryDTO){
        this.name = categoryDTO.getName();
    }

}