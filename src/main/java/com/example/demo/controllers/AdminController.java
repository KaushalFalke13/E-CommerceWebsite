package com.example.demo.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Repositories.OrdersProductsRepo;
import com.example.demo.Repositories.searchKeywordRepository;
import com.example.demo.Services.AdminServiceImpl;
import com.example.demo.Services.ProductsServicesImpl;
import com.example.demo.entities.Admin;
import com.example.demo.entities.Brands;
import com.example.demo.entities.Categories;
import com.example.demo.entities.Images;
import com.example.demo.entities.OrdersProducts;
import com.example.demo.entities.Products;
import com.example.demo.entities.rating;
import com.example.demo.entities.searchKeywords;
import com.example.demo.enums.CategoryTypes;
import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.SubCategory;
import com.example.demo.forms.ChangeOrderToOrderForms;
import com.example.demo.forms.OrderForm;
import com.example.demo.forms.ProductsForms;
import com.example.demo.forms.SlugGenerator;

@Controller
@RequestMapping("/admin")
public class AdminController {

  @Autowired
  private searchKeywordRepository searchKeywordRepository;

  @Autowired
  private OrdersProductsRepo OrdersProductsRepo;

  @Autowired
  private ProductsServicesImpl ProductsServices;

  @Autowired
  private AdminServiceImpl adminservice;

  private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

  // Save New Product
@RequestMapping(value = "/processData", method = RequestMethod.POST)
  public String getProductData(@ModelAttribute ProductsForms productsForm, @RequestParam("images") MultipartFile[] images,Principal principal)
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

    String adminName = principal.getName();
    Admin admin = adminservice.findAdminByEmail(adminName);

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
        .admin(admin)
        .searchKeyword(keywords)
        .rating(rating.builder()
            .stars(0)
            .count(0)
            .build())
        .build();
    ProductsServices.SaveProducts(product);
    return "redirect:/admin/Dashboard";
  }



  @RequestMapping("/addProducts")  
  public String AddProducts(Model model) {
    ProductsForms ProductsForm = new ProductsForms();
    model.addAttribute("ProductsForm", ProductsForm);
    return "addProducts";   
  }

  @RequestMapping("/Dashboard")
  public String Dashboard(Model model,Principal principal){
    String adminName = principal.getName();
    Admin admin = adminservice.findAdminByEmail(adminName);
    List<OrdersProducts> ordersProducts =  admin.getOrdersProducts();
    List<OrderForm> orderFormsList = ChangeOrderToOrderForms.changeToOrderForm(ordersProducts);
    int OrdersCount = ordersProducts.size();
    int ProductsCount = admin.getProduct().size();
    model.addAttribute("orderFormsList", orderFormsList);
    model.addAttribute("AdminName", admin.getName());
    model.addAttribute("ProductsCount", ProductsCount);
    model.addAttribute("OrdersCount", OrdersCount);
    return "Dashboard";
  }

  @RequestMapping("/Products")
  public String Products(Model model,Principal principal){
    String adminName = principal.getName();
    Admin admin = adminservice.findAdminByEmail(adminName);
    List<Products> adminProductList =  admin.getProduct();
    model.addAttribute("adminProductList",adminProductList);
    return "Products";
  }

  @RequestMapping("/orders")
  public String orders(Model model,Principal principal){
    String adminName = principal.getName();
    Admin admin = adminservice.findAdminByEmail(adminName);
    List<OrdersProducts> ordersProducts =  admin.getOrdersProducts();
    List<OrderForm> orderFormsList = ChangeOrderToOrderForms.changeToOrderForm(ordersProducts);
    List<OrdersProducts>  pendingOrders = OrdersProductsRepo.findOrderByAdminAndStatus( admin,OrderStatus.PENDING);
    List<OrdersProducts>  ProcessingOrders = OrdersProductsRepo.findOrderByAdminAndStatus( admin,OrderStatus.PROCESSING);
    List<OrdersProducts>  DeliveredOrders = OrdersProductsRepo.findOrderByAdminAndStatus( admin,OrderStatus.DELIVERED);

    model.addAttribute("ProcessingOrders", ProcessingOrders);
    model.addAttribute("DeliveredOrders", DeliveredOrders);
    model.addAttribute("pendingOrders", pendingOrders);
    model.addAttribute("orderFormsList", orderFormsList);
    model.addAttribute("AdminName", admin.getName());

    return "AdminOrders";
  }

  @RequestMapping(value = "/getOrderDetails",method = RequestMethod.POST)
  @ResponseBody
  public List<OrdersProducts> OrderDetails(@RequestParam("OrderName") String orderName,Principal principal){

    String adminName = principal.getName();
    Admin admin = adminservice.findAdminByEmail(adminName);

    if(orderName.equals("pendingOrders")){
      List<OrdersProducts>  pendingOrders = OrdersProductsRepo.findOrderByAdminAndStatus( admin,OrderStatus.PENDING);
      return pendingOrders;
      
    }else if(orderName.equals("ProcessingOrders")){
      List<OrdersProducts>  ProcessingOrders = OrdersProductsRepo.findOrderByAdminAndStatus( admin,OrderStatus.PENDING);
      return ProcessingOrders;

    }else if(orderName.equals("ShippedOrders")){
      List<OrdersProducts>  ShippedOrders = OrdersProductsRepo.findOrderByAdminAndStatus( admin,OrderStatus.PENDING);
      return ShippedOrders;

    }else if(orderName.equals("DeliveredOrders")){
      List<OrdersProducts>  DeliveredOrders = OrdersProductsRepo.findOrderByAdminAndStatus( admin,OrderStatus.PENDING);
      return DeliveredOrders;
    }
    return null;
  }

  @RequestMapping("/reports")
  public String reports(){
    
    return "reports";
  }
 
}
