package com.example.demo.ServicesTest;

import com.example.demo.Repositories.CatagoriesRepo;
import com.example.demo.Repositories.ProductsRepo;
import com.example.demo.Services.ProductsServicesImpl;
import com.example.demo.entities.Categories;
import com.example.demo.entities.Products;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProductsServicesImplTest {

  @Mock
  ProductsRepo productsRepo;

  @Mock
  CatagoriesRepo catagoriesRepo;

  @InjectMocks
  ProductsServicesImpl productsServicesImpl;

  Products product = new Products();
  Categories category = new Categories();

  @BeforeEach
   void beforeAllTest(){
    product.setProductId("kau");
    product.setSlug("productSlug");
    product.setCategory(category);
  }

// @Test
// void SearchProductsTest(){
//   List<Products> proList = new ArrayList<>();
//   proList.add(product);
//   Mockito.when(productsRepo.findBycategory_productType(Mockito.any())).thenReturn(proList);
////   List<Products> ProductList = ProductsServicesImpl.SearchProducts(Mockito.any());
//
// }

  @Test
  void SearchByProductTypeTest(){
    List<Products> proList = new ArrayList<>();
    proList.add(product);
    Mockito.when(productsRepo.findBycategory_productType(Mockito.any())).thenReturn(proList);
    List<Products> ProductList = productsServicesImpl.SearchByProductType(Mockito.any());
    Assertions.assertEquals(ProductList.size(),1);
    Assertions.assertEquals(ProductList.getFirst().getProductId(),product.getProductId());
  }

  @Test
  void SearchBySlugTest(){
      Mockito.when(productsRepo.findBySlug(product.getSlug())).thenReturn(product);
      Products newProducts = productsServicesImpl.SearchBySlug(product.getSlug());
      Assertions.assertEquals(product.getSlug(), newProducts.getSlug());
  }

  @Test
  void  SaveCatagoryTest(){
    Mockito.when(catagoriesRepo.save(category)).thenReturn(category);
    Categories newcategory = productsServicesImpl.SaveCatagory(product.getCategory());
    Assertions.assertEquals(category.getClass(), newcategory.getClass());
  }

  @Test
  void  SearchProductByIdTest(){
    Products product = new Products();
    product.setProductId("kau");
      Mockito.when(productsRepo.findByproductId(product.getProductId())).thenReturn(product);
    Products newProducts = productsServicesImpl.SearchProductById(product.getProductId());
    Assertions.assertEquals(product.getProductId(), newProducts.getProductId());
  }

  @Test
  void SaveProductsTest() {
    Mockito.when(productsRepo.save(product)).thenReturn(product);
    Products newProducts = productsServicesImpl.SaveProducts(product);
    Assertions.assertEquals(product.getProductId(), newProducts.getProductId());
  }

  @Test
  void getAllProductsTest(){
    List<Products> proList = new ArrayList<>();
    proList.add(product);
    Mockito.when(productsRepo.findAll()).thenReturn(proList);
    List<Products> ProductList = productsServicesImpl.getAllProducts();
    Assertions.assertEquals(ProductList.size(),1);
    Assertions.assertEquals(ProductList.getFirst().getProductId(),product.getProductId());
  }

  @Test
  void SortByPriceATDTest(){
    List<Products> proList = new ArrayList<>();
    proList.add(product);
    Mockito.when(productsRepo.findAllByOrderByPriceAsc()).thenReturn(proList);
    List<Products> ProductList = productsServicesImpl.SortByPriceATD();
    Assertions.assertEquals(ProductList.size(),1);
    Assertions.assertEquals(ProductList.getFirst().getProductId(),product.getProductId());
  }

  @Test
  void SortByPriceDTATest(){
    List<Products> proList = new ArrayList<>();
    proList.add(product);
    Mockito.when(productsRepo.findAllByOrderByPriceDesc()).thenReturn(proList);
    List<Products> ProductList = productsServicesImpl.SortByPriceDTA();
    Assertions.assertEquals(ProductList.size(),1);
    Assertions.assertEquals(ProductList.getFirst().getProductId(),product.getProductId());
  }

  @Test
  void SortbyDiscountTest(){
    List<Products> proList = new ArrayList<>();
    proList.add(product);
    Mockito.when(productsRepo.findAllByOrderByDiscountDesc()).thenReturn(proList);
    List<Products> ProductList = productsServicesImpl.SortbyDiscount();
    Assertions.assertEquals(ProductList.size(),1);
    Assertions.assertEquals(ProductList.getFirst().getProductId(),product.getProductId());
  }

  @Test
  void findByPriceBetweenTest(){
    List<Products> proList = new ArrayList<>();
    proList.add(product);
    Mockito.when(productsRepo.findAllByPriceBetween(3,4)).thenReturn(proList);
    List<Products> ProductList = productsServicesImpl.findByPriceBetween( 3, 4);
    Assertions.assertEquals(ProductList.size(),1);
    Assertions.assertEquals(ProductList.getFirst().getProductId(),product.getProductId());
  }

  @Test
  void findByDiscountTest(){
    List<Products> proList = new ArrayList<>();
    proList.add(product);
    Mockito.when(productsRepo.findAllByDiscountGreaterThanEqual(9)).thenReturn(proList);
    List<Products> ProductList = productsServicesImpl.findByDiscount(9);
    Assertions.assertEquals(ProductList.size(),1);
    Assertions.assertEquals(ProductList.getFirst().getProductId(),product.getProductId());
  }

}
