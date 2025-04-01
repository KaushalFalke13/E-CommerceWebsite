function sort(div) {
  const choose = div.innerText;
  if (choose === "Recommended") {
   sortProducts("Recommended");
     return;
   } else if(choose === "What's New"){
     sortProducts("/WhatsNew");
     return;
   } else if(choose === "Popularity"){
     sortProducts("/Popularity");
     return;
   } else if(choose === "Better Discount"){
     sortProducts("/sortbyDiscount");
     return;
   } else if(choose === "Price: High to Low"){
     sortProducts("/sortDesc");
     return;
   }else{
     sortProducts("/sortAsc");
     return;
   }
}

function changeSortOption() {
  const sortOptionSelected= document.querySelector(".sortOptionSelected");
  const sortOptions= document.querySelectorAll(".sortOptions");
  let currentSortValue=sortOptionSelected;
  sortOptions.forEach(options=>{
    options.addEventListener("click",()=>{
      let NewValue=options.innerText;
      currentSortValue.innerText=NewValue;
        });
  });
}

function sortProducts(link) {
   fetch(link)
       .then(response => response.json())
       .then(data => {
           const displayProducts = document.querySelector('.displayProducts');
           displayProducts.innerHTML = ''; 
           data.productsForms.forEach(product => {
             displayProducts.innerHTML += createProductHTML(product);
           });
         })
         .catch(error => console.error('Error fetching updated product list:', error));
}

function sortbyBrands(){

  let selectedBrands = [];
  let checkboxes = document.querySelectorAll('.BrandCheckBox input[type="checkbox"]');
  checkboxes.forEach(function (checkbox) {
    if (checkbox.checked) {
        selectedBrands.push(checkbox.value);
    }
});
if (selectedBrands.length >0) {
  fetch("/getProductsByBrand", {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded"
    },
    body: "BrandName=" + encodeURIComponent(selectedBrands)
  })
  .then(response => response.json())
  .then(data => {
           const displayProducts = document.querySelector('.displayProducts');
           displayProducts.innerHTML = ''; 
           data.productsForms.forEach(product => {
             displayProducts.innerHTML += createProductHTML(product);
           });
  })
  .catch(error => {
    console.error("Error adding product to watchlist:", error);
  });
} else {
  sortProducts("Recommended");
}
}

function sortByDiscount(checkbox){
  const DiscountPercent = checkbox.getAttribute('value');
  if (checkbox.checked) {
    fetch("/getProductsByDiscount", {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      body: "DiscountPercent=" + encodeURIComponent(DiscountPercent)
    })
    .then(response => response.json())
    .then(data => {
      const displayProducts = document.querySelector('.displayProducts');
           displayProducts.innerHTML = ''; 
           data.productsForms.forEach(product => {
             displayProducts.innerHTML += createProductHTML(product);
           });
         })
    .catch(error => {
      console.error("Error adding product to watchlist:", error);
    });
  } else {
    sortProducts("Recommended");
  }
}

function displayProductsByPriceBetween(min,max){
  if (isNaN(min) || isNaN(max)) {
    return;
  }
  if (max == 10000) {
    max=500000;
  }

  fetch("/getProductBetweenPrice", {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded"
    },
    body: "min=" + encodeURIComponent(min) +
          "&max=" + encodeURIComponent(max)
  })
  .then(response => response.json())
  .then(data => {
    const displayProducts = document.querySelector('.displayProducts');
    displayProducts.innerHTML = ''; 
    data.productsForms.forEach(product => {
      displayProducts.innerHTML += createProductHTML(product);
    });
  })
  .catch(error => {
    console.error("Error adding product to watchlist:", error);
  });
}