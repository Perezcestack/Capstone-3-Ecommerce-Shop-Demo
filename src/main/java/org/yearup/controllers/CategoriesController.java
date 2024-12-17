package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.data.CategoryDao;
import org.yearup.data.ProductDao;
import org.yearup.models.Category;
import org.yearup.models.Product;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("categories")
// add the annotations to make this a REST controller DONE
// add the annotation to make this controller the endpoint for the following url DONE
    // http://localhost:8080/categories
// add annotation to allow cross site origin requests DONE
public class CategoriesController
{
    private CategoryDao categoryDao;
    private ProductDao productDao;

    @Autowired  // create an Autowired controller to inject the categoryDao and ProductDao DONE
        public CategoriesController(CategoryDao categoryDao, ProductDao productDao) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }

    @GetMapping      // add the appropriate annotation for a get action DONE
        public List<Category> getAll() {
        // find and return all categories DONE
        return categoryDao.getAllCategories();
    }

    @GetMapping("{categoryId}")      // add the appropriate annotation for a get action DONE
        public Category getById(@PathVariable int id)
    {
        // get the category by id DONE
        return categoryDao.getById(id);
    }

    // the url to return all products in category 1 would look like this
    // https://localhost:8080/categories/1/products

    @GetMapping("{categoryId}/products")
    public List<Product> getProductsById(@PathVariable int categoryId)
    {
        // get a list of product by categoryId DONE
        return productDao.listByCategoryId(categoryId);
    }

    // add annotation to call this method for a POST action DONE
    // add annotation to ensure that only an ADMIN can call this function DONE
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Category addCategory(@RequestBody Category category)
    {
        // insert the category DONE
        return categoryDao.create(category);
    }

    // add annotation to call this method for a PUT (update) action - the url path must include the categoryId DONE
    // add annotation to ensure that only an ADMIN can call this function DONE
    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateCategory(@PathVariable int id, @RequestBody Category category)
    {
        // update the category by id DONE
        categoryDao.update(id,category);
    }


    // add annotation to call this method for a DELETE action - the url path must include the categoryId
    // add annotation to ensure that only an ADMIN can call this function
    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteCategory(@PathVariable int id)
    {
        categoryDao.delete(id);
        // delete the category by id DONE
    }
}
