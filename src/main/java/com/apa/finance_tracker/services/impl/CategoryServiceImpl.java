package com.apa.finance_tracker.services.impl;

import com.apa.finance_tracker.constants.ErrorMessage;
import com.apa.finance_tracker.entitys.Category;
import com.apa.finance_tracker.entitys.User;
import com.apa.finance_tracker.exceptions.resource.BusinessException;
import com.apa.finance_tracker.exceptions.resource.DuplicateResourceException;
import com.apa.finance_tracker.exceptions.resource.ResourceNotFoundException;
import com.apa.finance_tracker.helpers.SecurityHelper;
import com.apa.finance_tracker.mappers.category.CategoryMapperUpdate;
import com.apa.finance_tracker.repositories.CategoryRepository;
import com.apa.finance_tracker.repositories.TransactionRepository;
import com.apa.finance_tracker.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;
    private final SecurityHelper securityHelper;

    @Override
    public Category createCategory(Category category) {
        User currentUser = securityHelper.getCurrentUser();
        category.setUser(currentUser);
        if(categoryRepository.existsByNameAndUserId(category.getName(),currentUser.getId() )){
            throw new DuplicateResourceException(ErrorMessage.CATEGORY_ALREADY_EXISTS);
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return getCategory(categoryId, securityHelper.getCurrentUser().getId());
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAllByUserId(securityHelper.getCurrentUser().getId());
    }

    @Override
    public Category updateCategory(Long categoryId, Category category) {
        User currentUser = securityHelper.getCurrentUser();
        Category existCategory = getCategory(categoryId, currentUser.getId());
        if (existCategory.getType() != category.getType()
                && transactionRepository.existsByCategoryId(categoryId)) {

            throw new BusinessException(
                    ErrorMessage.CATEGORY_HAS_TRANSACTIONS
            );
        }
        if (categoryRepository.existsByNameAndUserIdAndIdNot(category.getName(),currentUser.getId() ,categoryId)) {
            throw new DuplicateResourceException(ErrorMessage.CATEGORY_ALREADY_EXISTS);
        }
        new CategoryMapperUpdate().updateEntity(existCategory, category);
        return categoryRepository.save(existCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = getCategory(categoryId, securityHelper.getCurrentUser().getId());
        if(transactionRepository.existsByCategoryId(categoryId)) {
            throw new BusinessException(ErrorMessage.CATEGORY_HAS_TRANSACTIONS);
        }
        categoryRepository.delete(category);
    }

    private Category getCategory(Long categoryId, Long userId) {
        return categoryRepository.findByIdAndUserId(categoryId, userId).orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.CATEGORY_NOT_FOUND));
    }
}
