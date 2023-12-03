package com.today.demo.service;

import com.today.demo.dto.CategoryDTO;
import com.today.demo.dto.Request.CategoryRequestDTO;
import com.today.demo.entity.Category;
import com.today.demo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void addCategory(CategoryRequestDTO categoryDTO){
        Category category = Category.builder()
                .name(categoryDTO.getName())
                .build();
        categoryRepository.save(category);
    }

    public List<Category> getCategory(){
        return categoryRepository.findAll();
    }

    public void detectCategory(int id){
        categoryRepository.deleteById(id);
    }


    public void updateCategory(CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(categoryDTO.getId());
        category.updateCategory(categoryDTO);
        categoryRepository.save(category);
    }

    public Category getCategory(int category){
        return categoryRepository.findById(category);
    }
}
