



const rangeInput = document.querySelectorAll(".rangeInput input"),
progress = document.querySelector(".Slider .progress"),
minPrice = document.querySelector(".inputMin"),
maxPrice = document.querySelector(".inputMax");
let priceGap = 400;
let debounceTimeout;
rangeInput.forEach(input =>{
  input.addEventListener("input", e =>{
    let minval = parseInt(rangeInput[0].value),
        maxval = parseInt(rangeInput[1].value),min,max;

        if(maxval - minval < priceGap){
          if (e.target.className === "rangeMin"){
            rangeInput[0].value= maxval - priceGap;
          }else{
            rangeInput[1].value= minval + priceGap;
          }
        }else{
        progress.style.left = (minval/rangeInput[0].max)*100 +"%";
        progress.style.right = 100-(maxval/rangeInput[1].max)*100 +"%";
          min=Math.round((minval/rangeInput[0].max)*10000);
          max=Math.round(((maxval/rangeInput[1].max)*10000)); 
        minPrice.innerText = "₹"+min; 
        maxPrice.innerText = "₹"+max; 
        }
        clearTimeout(debounceTimeout);
        debounceTimeout = setTimeout(() => {
          displayProductsByPriceBetween(min,max);
        }, 500);
  });
});


window.onload = function() {
  getCartlist();
  getBaglist();
};

function showimages(div) {
  let productid = div.getAttribute("id"); 
  let image = div.closest('.containerProduct').querySelector('.img'); 
  let dots = div.closest('.containerProduct').querySelectorAll('.dot'); 
  let firstImage = image.getAttribute('value');

  if (div.dataset.animationStarted === "true") {
      clearInterval(interval);
    dots.forEach(dot => {
      dot.style.backgroundColor = "#ccc";
    });
    image.src = firstImage; 
    timeouts = [];  
    div.dataset.animationStarted = "false";  
    return; 
  }
  div.dataset.animationStarted = "true";
  dots[0].style.backgroundColor = "red";

  fetch("/getProductById", {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded"
    },
    body: "productId=" + encodeURIComponent(productid)  
  })
    .then(response => response.json())
    .then(data => {
      let imagePath1 = data.productform.imagePath1;  
      let imagePath2 = data.productform.imagePath2;  
      let imagePath3 = data.productform.imagePath3;  
      let imagePath4 = data.productform.imagePath4;  
      let imagePath5 = data.productform.imagePath5;  
      let imagePaths = [imagePath1, imagePath2, imagePath3, imagePath4, imagePath5];

      if (imagePaths.every(path => path)) {
        let currentIndex = 1;
          interval = setInterval(function() {
            dots.forEach(dot => {
              dot.style.backgroundColor = "#ccc";
            });

            dots[currentIndex].style.backgroundColor = "red";

            image.src = imagePaths[currentIndex];
            currentIndex = (currentIndex + 1) % imagePaths.length;
          }, 1500); 

      } else {
      console.log("One or more image paths are missing!"); }
    })
    .catch(error => {
      console.error('Error fetching product data:', error); 
  });
}

function getCartlist() {
  fetch("/getCartlist")
    .then(response => response.json())
    .then(data => {
      displayWatchlistCnt(data.length);
    })
    .catch(error => {
      console.error("Error fetching cartlist:", error);
    });
}

function displayWatchlistCnt(length) {
  var watchlistCnt=document.querySelector(".watchlistCnt");
  if (length && length > 0) {
    watchlistCnt.style.visibility = "visible";
    watchlistCnt.innerText = length;
  } else {
    watchlistCnt.style.visibility = "hidden";
  }
}

function getBaglist() {
  fetch("/getBaglist")
    .then(response => response.json())
    .then(data => {
      displayBaglistCnt(data.length);
    })
    .catch(error => {
      console.error("Error fetching baglist:", error);
    });
}

function displayBaglistCnt(length) {
  var baglistCnt=document.querySelector(".bag-count");
  if (length && length > 0) {
    baglistCnt.style.visibility = "visible";
    baglistCnt.innerText = length;
  } else {
    baglistCnt.style.visibility = "hidden";
  }
}

function search(){
let SearchResult= document.querySelector(".SearchResult");
let searchString = document.getElementById("SearchBox").value;
fetch(`/searchProducts?query=${encodeURIComponent(searchString)}`)
                .then(response => response.json())
                .then(data => {
                  displaySearchResults(data);
                })
                .catch(error => console.log('Error:', error));
  if (searchString == "") {
    SearchResult.style.visibility ="hidden";
  } else {
    SearchResult.style.visibility ="visible";
  }
}

function displaySearchResults(results) {
  const resultsDiv = document.getElementById("SearchResult");
  resultsDiv.innerHTML = "";
  if (results.length === 0) {
      resultsDiv.innerHTML = "<p >No products found</p>";
      return;
  }
  results.forEach(result => {
      const productDiv = document.createElement("div");
      productDiv.innerHTML = `<p class="searchRes" onclick="getSearchedProducts(this)">${result}</p>`;
      resultsDiv.appendChild(productDiv);
  });
}

function getSearchedProducts(searchKeywordElement){
  let SearchResult= document.querySelector(".SearchResult");
  SearchResult.style.visibility ="hidden";
  searchKeyword  = searchKeywordElement.textContent;
  fetch(`/getSearchedProducts?query=${encodeURIComponent(searchKeyword)}`)
          .then(response => response.json())
          .then(data => {
            const displayProducts = document.querySelector('.displayProducts');
            displayProducts.innerHTML = ''; 
            data.forEach(product => {
            displayProducts.innerHTML += createProductHTML(product);
          });
        })
          .catch(error => console.log('Error:', error));
}

function createProductHTML(product) {
  return `
    <div class="containerProduct">
      <div class="image"><img src="${product.imagePath1}" alt=""></div>
      <div class="Ratings">
        <div class="stars">${product.rating.stars}</div><span>&#10030</span> 
        <div class="StarCount">${product.rating.count}</div>
      </div>                
      <div class="BrandName">${product.brand}</div>
      <div class="title">${product.title}</div>
      <div class="WatchList">
        <div>
          <button value ="${product.productId}" onclick="addToWatchlist(this)"><i class="fa-regular fa-heart"></i><span>WATCHLIST</span></button>
        </div>
        <div class="size">Size: S</div>
      </div>
      <div class="priceDetails">
        <div class="sellingPrice">Rs.${product.price}</div>
        <div class="MRP">Rs.${product.mrp}</div>
        <div class="discountOnMRP">(${product.discount}% OFF)</div>
      </div>
      <div class="fewLeftMes">Only Few Left!</div>
    </div>
  `;
}

