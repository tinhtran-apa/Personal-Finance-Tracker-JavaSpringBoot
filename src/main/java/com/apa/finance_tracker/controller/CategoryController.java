package com.apa.finance_tracker.controller;

import com.apa.finance_tracker.constant.SuccessMessage;
import com.apa.finance_tracker.dto.request.CategoryCreateRequest;
import com.apa.finance_tracker.dto.request.CategoryUpdateRequest;
import com.apa.finance_tracker.dto.response.ApiResponse;
import com.apa.finance_tracker.dto.response.CategoryResponse;
import com.apa.finance_tracker.entity.Category;
import com.apa.finance_tracker.mapper.CategoryMapper;
import com.apa.finance_tracker.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;
    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@Valid @RequestBody CategoryCreateRequest request) {
        Category category = categoryMapper.toEntityCreate(request);
        Category savedCategory = categoryService.createCategory(category);
        CategoryResponse response = categoryMapper.toResponse(savedCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(SuccessMessage.CATEGORY_CREATED, response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategory() {
        List<Category> category = categoryService.getAllCategory();
        List<CategoryResponse> response = categoryMapper.toResponseList(category);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(ApiResponse.success(SuccessMessage.CATEGORY_LIST_RETRIEVED, response));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(@PathVariable Long categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        CategoryResponse response = categoryMapper.toResponse(category);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(ApiResponse.success(SuccessMessage.CATEGORY_RETRIEVED, response));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(@Valid @PathVariable Long categoryId, @RequestBody CategoryUpdateRequest request) {
        Category category = categoryMapper.toEntityUpdate(request);
        Category savedCategory = categoryService.updateCategory(categoryId, category);
        CategoryResponse response = categoryMapper.toResponse(savedCategory);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(ApiResponse.success(SuccessMessage.CATEGORY_UPDATED, response));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(ApiResponse.success(SuccessMessage.CATEGORY_DELETED));
    }

}
