package com.grey.cagnotte.service.impl;

import com.grey.cagnotte.entity.Category;
import com.grey.cagnotte.exception.CagnotteCustomException;
import com.grey.cagnotte.payload.request.CategoryRequest;
import com.grey.cagnotte.payload.response.CategoryResponse;
import com.grey.cagnotte.repository.CategoryRepository;
import com.grey.cagnotte.service.CategoryService;
import com.grey.cagnotte.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@Log4j2
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final String NOT_FOUND = "CATEGORY_NOT_FOUND";

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll() ;
    }

    @Override
    public long addCategory(CategoryRequest categoryRequest) {
        Category category;
        if(categoryRepository.existsByLabelEquals(categoryRequest.getLabel())){
            category = categoryRepository.findByLabel(categoryRequest.getLabel()).orElseThrow();
            editCategory(categoryRequest, category.getId());
        }
        else {
            category = Category.builder()
                    .label(categoryRequest.getLabel())
                    .slug(Str.slug(categoryRequest.getLabel()))
                    .icon(categoryRequest.getIcon())
                    .description(categoryRequest.getDescription())
                    .build();
            category =  categoryRepository.save(category);
        }


        return category.getId();
    }

    @Override
    public CategoryResponse getCategory(long categorieId) {

        Category category;

        category = categoryRepository.findById(categorieId).orElseThrow(
                () -> new CagnotteCustomException("Category not found", NOT_FOUND)
        );

        CategoryResponse categoryResponse = new CategoryResponse();

        copyProperties(category, categoryResponse);
        return categoryResponse;
    }

    @Override
    public long editCategory(CategoryRequest categoryRequest, long categorieId) {

        Category category = categoryRepository.findById(categorieId).orElseThrow(
                ()-> new CagnotteCustomException("Category with given Id not found", NOT_FOUND)
        );
        category.setLabel(categoryRequest.getLabel());
        category.setSlug(Str.slug(categoryRequest.getLabel()));
        category.setIcon(categoryRequest.getIcon());
        category.setDescription(categoryRequest.getDescription());

        category = categoryRepository.save(category);
        return category.getId();
    }

    @Override
    public void deleteCategory(long categorieId) {

       if(!categoryRepository.existsById(categorieId)){
           throw new CagnotteCustomException("Category with given ID Not Found", NOT_FOUND);
       }
       categoryRepository.deleteById(categorieId);
    }
}
