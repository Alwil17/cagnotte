package com.grey.cagnotte.service;

import com.grey.cagnotte.entity.Category;
import com.grey.cagnotte.payload.request.CategoryRequest;
import com.grey.cagnotte.payload.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

     List<Category> getAllCategories() ;

     public long addCategory(CategoryRequest categoryRequest) ;

     public CategoryResponse getCategory(long categorieId) ;

     public long editCategory(CategoryRequest categoryRequest, long categoryId) ;

     public void deleteCategory(long categorieId) ;

}
