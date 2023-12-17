package com.today.demo.controller.map.Category;

import com.today.demo.dto.CategoryDTO;
import com.today.demo.dto.Request.CategoryRequestDTO;
import com.today.demo.entity.Category;
import com.today.demo.repository.CategoryRepository;
import com.today.demo.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.internal.BinderHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RestCategoryController {

    private final CategoryService categoryService;

    @PostMapping("/admin/db/categoryAdd")
    public ResponseEntity<String> addCategory(@Valid @RequestBody CategoryRequestDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 실패 시 처리
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        categoryService.addCategory(dto);
        return new ResponseEntity<>("Category added successfully", HttpStatus.OK);
    }

    @DeleteMapping("/admin/category/db/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable int id) {
        categoryService.detectCategory(id);
        return ResponseEntity.ok("Category DB with ID " + id + " has been deleted.");
    }

    @PutMapping("/admin/category/db/{id}")
    public ResponseEntity<String> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 실패 시 처리
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        categoryService.updateCategory(categoryDTO);
        return ResponseEntity.ok("Category DB with ID " + categoryDTO.getId() + " has been updated.");
    }
}
