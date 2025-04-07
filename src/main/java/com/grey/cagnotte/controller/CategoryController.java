package com.grey.cagnotte.controller;

import com.grey.cagnotte.entity.Category;
import com.grey.cagnotte.payload.request.CategoryRequest;
import com.grey.cagnotte.payload.response.CategoryResponse;
import com.grey.cagnotte.service.CategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Log4j2
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories (){
        log.info("CategoryController | getAllCategorys is called");
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Long> addCategory(@RequestBody CategoryRequest categorieRequest){
        log.info("CategoryController | addCategory is called");
        long categorieId = categoryService.addCategory(categorieRequest);
        return new ResponseEntity<>(categorieId,HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public  ResponseEntity<CategoryResponse> getCategoryById (@PathVariable("id") long id){
        log.info("CategoryController | getCategoryById is called");
        CategoryResponse categorieResponse = categoryService.getCategory(id);
        return  new ResponseEntity<>(categorieResponse,HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public  ResponseEntity<Long> editCategory (@RequestBody CategoryRequest categorieRequest, @PathVariable("id") long id){
        log.info("CategoryController | editCategory is called");
        long categorieId = categoryService.editCategory(categorieRequest,id);
        return new ResponseEntity<>(categorieId,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public  void deleteCategoryById(@PathVariable("id") long id ){
        categoryService.deleteCategory(id);
    }
}
