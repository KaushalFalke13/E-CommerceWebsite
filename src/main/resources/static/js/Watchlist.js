function removeWatchlistItem(button){
  const productId = button.getAttribute("value");  
  fetch("/removeWatchlistItem", {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded"
    },
    body: "productId=" + encodeURIComponent(productId)
  })
  .then(response => response.json())
  .then(data => {
    console.log(data.length); 
    console.log(data.productsList);
    console.log(data.removedProduct);

    displayWatchlistCnt(data.length);
    displayWatchlist(data.productsList); 
    displayWatchListcntItem(data.length);
    removeItemMessage(data.removedProduct);
  })
}

function addToWatchlist(event, button) {
  event.preventDefault(); 

  var productId = button.getAttribute("value");  
  console.log(productId);
  fetch("/addtoWatchlist", {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded"
    },
    body: "productId=" + encodeURIComponent(productId)
  })
  .then(response => response.json())
  .then(data => {
    displayWatchlistCnt(data.length);
  })
  .catch(error => {
    console.error("Error adding product to watchlist:", error);
  });
}

function displayWatchListcntItem(length){
const watchlistCnt = document.querySelector(".watchlistCntinheading");
watchlistCnt.innerHTML = `<span>${length}</span> items`;
}

function displayWatchlist(ProductList){

  const displayProduct =document.querySelector(".WatchListmainContent");
  displayProduct.innerHTML = ``;
  if (ProductList.length > 0) {
    ProductList.forEach((Product) => {
      displayProduct.innerHTML += `<div class="WatchListContentItem">
                      <img src="${Product.imagepath}" alt="">
                      <button class="close" value="${Product.id}" onclick="removeWatchlistItem(this)">x</button>
                      <div class="Details">
                          <div class="title">${Product.title}</div>
                          <div class="prices">
                              <div class="current-price">Rs.${Product.price}</div>
                              <div class="orignal-price">Rs.${Product.MRP}</div>
                              <div class="discount-Percentage">(${Product.discount}%)</div>
                          </div>
                          <div class="AddToBag"><button>MOVE TO BAG</button></div>
                      </div>
                  </div> `;
    });
  } else {
  displayProduct.innerHTML=`<div> 
                <div class="noIteminWatchlist">
                  <h2>YOUR WISHLIST IS EMPTY</h2>
                  <p>Add items that you like to your wishlist. Review them anytime and easily move them to the bag.</p>
                  <img src="/images/WatchList.webp" alt="">
                  <a href="/showProducts"><button>CONTINUE SHOPPING</button></a>
              </div>
            </div>`;
  }
}

function removeItemMessage(Product){
  let removeditems = document.querySelector(".removeditems");
  removeditems.classList.add('showRemoveitem');

  removeditems.innerHTML = `<div class="Removed">
  <img class="removeditemsImg" src="${Product}" alt="">
  <h4>Item Removed From WatchList</h4>
  </div>`;
 setTimeout(()=>{
  removeditems.classList.remove('showRemoveitem');
 },1500);
}

function addToBag(button){
  const productId = button.getAttribute("value");  
  fetch("/addToBag", {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded"
    },
    body: "productId=" + encodeURIComponent(productId)
  })
  .then(response => response.json())
  .then(data => {
    displayBaglistCnt(data.length);
  })
}