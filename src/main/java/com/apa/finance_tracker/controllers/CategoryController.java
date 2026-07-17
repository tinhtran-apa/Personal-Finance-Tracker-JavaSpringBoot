package com.apa.finance_tracker.controllers;

import com.apa.finance_tracker.constants.SuccessMessage;
import com.apa.finance_tracker.dtos.requests.CategoryCreateRequest;
import com.apa.finance_tracker.dtos.requests.CategoryUpdateRequest;
import com.apa.finance_tracker.dtos.responses.ApiResponse;
import com.apa.finance_tracker.dtos.responses.CategoryResponse;
import com.apa.finance_tracker.entitys.Category;
import com.apa.finance_tracker.mappers.category.CategoryMapperCreate;
import com.apa.finance_tracker.mappers.category.CategoryMapperResponse;
import com.apa.finance_tracker.mappers.category.CategoryMapperUpdate;
import com.apa.finance_tracker.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@Valid @RequestBody CategoryCreateRequest request) {
        Category category = new CategoryMapperCreate().toEntityCreate(request);
        Category savedCategory = categoryService.createCategory(category);
        CategoryResponse response = new CategoryMapperResponse().toResponse(savedCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(SuccessMessage.CATEGORY_CREATED, response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategory() {
        List<Category> category = categoryService.getAllCategory();
        List<CategoryResponse> response = new CategoryMapperResponse().toResponseList(category);
        return ResponseEntity.ok(ApiResponse.success(SuccessMessage.CATEGORY_LIST_RETRIEVED, response));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(@PathVariable Long categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        CategoryResponse response = new CategoryMapperResponse().toResponse(category);
        return ResponseEntity.ok(ApiResponse.success(SuccessMessage.CATEGORY_RETRIEVED, response));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(@Valid @PathVariable Long categoryId, @RequestBody CategoryUpdateRequest request) {
        Category category = new CategoryMapperUpdate().toEntityUpdate(request);
        Category savedCategory = categoryService.updateCategory(categoryId, category);
        CategoryResponse response = new CategoryMapperResponse().toResponse(savedCategory);
        return ResponseEntity.ok(ApiResponse.success(SuccessMessage.CATEGORY_UPDATED, response));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(ApiResponse.success(SuccessMessage.CATEGORY_DELETED));
    }

}
