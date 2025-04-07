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

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll() ;
    }

    @Override
    public long addCategory(CategoryRequest categoryRequest) {
        Category category;
        if(categoryRepository.existsByLibelleEquals(categoryRequest.getLabel())){
            category = categoryRepository.findByLibelle(categoryRequest.getLabel()).orElseThrow();
            editCategory(categoryRequest, category.getId());
        }
        else {
            category = Category.builder()
                    .label(categoryRequest.getLabel())
                    .slug(Str.slug(categoryRequest.getLabel()))
                    .icone(categoryRequest.getIcone())
                    .allowConcern(categoryRequest.isAllowConcern())
                    .allowMessage(categoryRequest.isAllowMessage())
                    .allowMedia(categoryRequest.isAllowMedia())
                    .allowLocation(categoryRequest.isAllowLocation())
                    .allowUrl(categoryRequest.isAllowUrl())
                    .build();
            category =  categoryRepository.save(category);
        }


        return category.getId();
    }

    @Override
    public CategoryResponse getCategory(long categorieId) {

        Category category;

        category = categoryRepository.findById(categorieId).orElseThrow(
                () -> new CagnotteCustomException("Category not found","404")
        );

        CategoryResponse categoryResponse = new CategoryResponse();

        copyProperties(category, categoryResponse);
        return categoryResponse;
    }

    @Override
    public long editCategory(CategoryRequest categoryRequest, long categorieId) {

        Category category = categoryRepository.findById(categorieId).orElseThrow(
                ()-> new CagnotteCustomException("Category with given Id not found","404")
        );
        category.setLabel(categoryRequest.getLabel());
        category.setSlug(Str.slug(categoryRequest.getLabel()));
        category.setIcone(categoryRequest.getIcone());
        category.setAllowConcern(categoryRequest.isAllowConcern());
        category.setAllowMessage(categoryRequest.isAllowMessage());
        category.setAllowMedia(categoryRequest.isAllowMedia());
        category.setAllowLocation(categoryRequest.isAllowLocation());
        category.setAllowUrl(categoryRequest.isAllowUrl());

        category = categoryRepository.save(category);
        return category.getId();
    }

    @Override
    public void deleteCategory(long categorieId) {

       if(!categoryRepository.existsById(categorieId)){
           throw new CagnotteCustomException("Category with given ID Not Found","404");
       }
       categoryRepository.deleteById(categorieId);
    }
}
