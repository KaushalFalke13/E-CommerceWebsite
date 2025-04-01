package com.example.demo.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.Repositories.searchKeywordRepository;
import com.example.demo.Services.ProductsServicesImpl;
import com.example.demo.entities.*;
import com.example.demo.enums.CategoryTypes;
import com.example.demo.enums.SubCategory;
import com.example.demo.forms.ChangeProductToProductForm;
import com.example.demo.forms.ProductsForms;
import com.example.demo.forms.SlugGenerator;

@Controller
public class ProductRequestController {

  @Autowired
  private ProductsServicesImpl ProductsServices;

  @Autowired
  private searchKeywordRepository searchKeywordRepository;

  private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

  // Save New Product
@RequestMapping(value = "/processData", method = RequestMethod.POST)
  public String getProductData(@ModelAttribute ProductsForms productsForm, @RequestParam("images") MultipartFile[] images)
      throws IOException {
        for (int i = 0; i < images.length; i++) {
          MultipartFile image = images[i];
          if (image != null && !image.isEmpty()) {
              String fileName = image.getOriginalFilename();
              if (fileName != null && !fileName.isEmpty()) {
                  // Create directory if it doesn't exist
                  File directory = new File(UPLOAD_DIR);
                  if (!directory.exists()) {
                      directory.mkdirs();
                  }
                  Path path = Paths.get(UPLOAD_DIR + fileName);
                  image.transferTo(path);

                  switch (i) {
                      case 0:
                          productsForm.setImagePath1("uploads/" + fileName);
                          break;
                      case 1:
                          productsForm.setImagePath2("uploads/" + fileName);
                          break;
                      case 2:
                          productsForm.setImagePath3("uploads/" + fileName);
                          break;
                      case 3:
                          productsForm.setImagePath4("uploads/" + fileName);
                          break;
                      case 4:
                          productsForm.setImagePath5("uploads/" + fileName);
                          break;
                      default:
                          break;
                  }
              }
          }
      }
      
    String slug = SlugGenerator.generateSlug(productsForm.getTitle());
    String productId = UUID.randomUUID().toString();

    Set<searchKeywords> keywords = new HashSet<>();
    List<String> keywordFields = Arrays.asList(
        productsForm.getKeyword1(),
        productsForm.getKeyword2(),
        productsForm.getKeyword3(),
        productsForm.getKeyword4(),
        productsForm.getKeyword5()
    );

    for (String kw : keywordFields) {
        if (kw != null && !kw.trim().isEmpty()) {
            String normalizedKeyword = kw.trim().toLowerCase();
            if (normalizedKeyword.isEmpty()) continue;

            searchKeywords keyword = searchKeywordRepository.findByKeyword(normalizedKeyword)
            .orElseGet(() -> {
              searchKeywords newKw = new searchKeywords(normalizedKeyword);
                return searchKeywordRepository.save(newKw);
            });
            keywords.add(keyword);
        }
    }

    Products product = Products.builder()
        .productId(productId)
        .title(productsForm.getTitle())
        .brand(Brands.builder()
            .brandName(productsForm.getBrand())
            .build())
        .MRP(productsForm.getMRP())
        .images(Images.builder()
            .image1(productsForm.getImagePath1())
            .image2(productsForm.getImagePath2())
            .image3(productsForm.getImagePath3())
            .image4(productsForm.getImagePath4())
            .image5(productsForm.getImagePath5())
            .build())
        .price(productsForm.getPrice()) 
        .discount(productsForm.getDiscount())
        .slug(slug)
        .Discription(productsForm.getDiscription())
        .stock(productsForm.getStock())
        .category(Categories.builder()
            .catagory_type(CategoryTypes.valueOf(productsForm.getCategory()))
            .sub_catagory(SubCategory.valueOf(productsForm.getSubCategory()))
            .build())
        .searchKeyword(keywords)
        .rating(rating.builder()
            .stars(0)
            .count(0)
            .build())
        .build();
    ProductsServices.SaveProducts(product);
    return "redirect:/showProducts";
  }




//   Display Particular Product
  // @RequestMapping(value = "/{slug}", method = RequestMethod.GET)
  // private String viewProducts(@PathVariable String slug, Model model) {
  //   Products product = ProductsServices.SearchBySlug(slug);
    
  //   // ProductType productType = product.getCategory().getProductType();
  //   // System.out.println(product.getCategory());
  //   // List<Products> ListOfSimilarProduct = ProductsServices.SearchByProductType(productType);
  //   // model.addAttribute("ListOfSimilarProduct", ListOfSimilarProduct);

  //   model.addAttribute("Product", product);
  //   return "ViewProduct";
  // }



  // Get Product by ProductID
  @RequestMapping(value = "/getProductByIds", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> getProductByIds(@RequestParam("productId") String[] productId) {

    List<Products> ProductList = new ArrayList<>();
    for (String element : productId) {
      ProductList.add(ProductsServices.SearchProductById(element));
    }
    List<ProductsForms> productsForms = ChangeProductToProductForm.changeToProductForm(ProductList);
    Map<String, Object> response = new HashMap<>();
    response.put("productsForms", productsForms);
    return response;
  }

  @RequestMapping(value = "/getProductById", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> getProductById(@RequestParam("productId") String productId) {

    Products Product = ProductsServices.SearchProductById(productId);
    ProductsForms productform = ChangeProductToProductForm.changeToProductForm(Product);
    Map<String, Object> response = new HashMap<>();
    response.put("productform", productform);
    return response;
  } 
}

