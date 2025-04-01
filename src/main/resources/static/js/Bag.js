var TotalMRP;
var TotalPRICE;
var PRODUCTS;


function getUserAddress(){
document.querySelector('.address-form').addEventListener('submit', async function(e) {
    e.preventDefault(); // Prevent default form submission
    
    const formData = {
        street: document.getElementById('street').value,
        city: document.getElementById('city').value,
        state: document.getElementById('state').value,
        pinCode: document.getElementById('zip').value
    };

    try {
        const response = await fetch('/getUserAddress', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        });

        if (response.ok) {
          const addressForm = await response.json();
           const addressDetails = document.querySelector('.address-details');
          addressDetails.innerHTML=`
            <div>
            <p>${addressForm.street}</p>
              <p>${addressForm.city}<p${addressForm.state}</p></p>
              <p>${addressForm.pinCode}</p>
              <p>Mobile: +1 123 456 7890</p>
            </div>`;
        }
    } catch (error) {
        console.error('Error:', error);
    }
});
}

function placeOrder(){
  if (Object.keys(PRODUCTS).length == 0) {
    console.log(PRODUCTS);
  } else {
    var orderDetail = {};
const checkboxes = document.querySelectorAll('.itemCheckbox');
var product = [];
var qty =[];

    checkboxes.forEach(checkbox => {
      if (checkbox.checked) {
        const selectedQuantity = checkbox.closest('.bagitem').querySelector('.mySelect').value;
        const productId = checkbox.getAttribute("value");
        product.push(productId);
        qty.push(selectedQuantity);
     
      }
    });
    orderDetail.TotalMRP = TotalMRP;
    orderDetail.TotalPRICE = TotalPRICE;
    orderDetail.product = product;
    orderDetail.qty = qty;
let orderDetailJson = JSON.stringify(orderDetail);
    
    fetch("/getOrderdata", {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      body: "orderDetail=" + encodeURIComponent(orderDetailJson)
    })
      .then(response => {
        console.log(response)
        if (response.redirected) {
    window.location.href = response.url;
  }   });
}
}

async function confirmOrder(){
  const response = await fetch("/confirmOrder", {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded"
    }  
  })
  if (response.ok) {
    Swal.fire({
      title: 'Order Confirmed! 🎉', // Updated title
      html: `
        <div class="text-left">
          <p><strong>Name:</strong> Kaushal</p>
          <p>Order Place Successfully</p>
        </div>
      `,
      icon: 'success',
      confirmButtonText: 'Continue Shopping',
      confirmButtonColor: '#3085d6'
    });
}else{
  Swal.fire({
    title: 'Error!',
    text: 'Failed to confirm order. Please try again.',
    icon: 'error',
    confirmButtonText: 'OK'
  });
}
}

function StartPayment(){
  fetch("/startPayment", {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded"
    },
  })
  .then(response => response.json())
  .then(data => {
      if (data.status=="created") {
        let options={
          key:'rzp_test_gSGPi3xiRwiLZa',
          amount:data.amount,
          currency:'INR',
          name:"Kaushal",
          description:"Order Payment",
          order_id:data.id,
          handler:function(paymentResponse){
            console.log(paymentResponse.razorpay_payment_id);
            console.log(paymentResponse.razorpay_order_id);
            console.log(paymentResponse.razorpay_signature);
            console.log("Payment Sucessfull !!");
          }, 
          prefill: {
            name:"",
            email:"",
            contact:"",
          },
          notes:{
            address:"payment",
          },
          theme:{
            color:"#3399cc"
          }
        };
  
        let rzp = new Razorpay(options);
  
        rzp.open();
  
        rzp.on("payment.failed",function(response){
          console.log(response.error.code);
          console.log(response.error.description);
        });
  
      }
  
    });
}
  
function ShowOffers() {
  const offerlist = document.querySelector(".offersclass");
  const ShowMore = document.querySelector(".ShowMore");
  let show = ShowMore.innerText;
  console.log(show);
  if (offerlist.classList.contains("offerlist")) {
    offerlist.classList.remove("offerlist");
    offerlist.classList.add("fullOffers");
    ShowMore.innerText = 'Show less';
  } else {
    offerlist.classList.remove("fullOffers");
    offerlist.classList.add("offerlist");
    ShowMore.innerText = 'Show more';
  }
}

function removeitemFromBag(button) {
  const productId = button.getAttribute("value");
  fetch("/removeBaglistItem", {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded"
    },
    body: "productId=" + encodeURIComponent(productId)
  })
    .then(response => response.json())
    .then(data => {
      displayBaglist(data.productsList);
      displayBaglistCnt(data.length);
    })
}

function displayBaglist(ProductList) {
  const ProductLength = document.querySelector(".BagProductLenght");
  ProductLength.innerText=ProductList.length;

  const displayProduct = document.querySelector(".bagitemsContainer");
  displayProduct.innerHTML = ``;
  ProductList.forEach((Product) => {
    displayProduct.innerHTML += ` 
    <div class="bagitem">
      <div class="selectBagItem">
      <input type="checkbox"></div>
    <div class="removeFromBag">
    <button value="${Product.id}" onclick="removeitemFromBag(this)">X</button></div>
   <img src="${Product.imagepath}" class="bagImage" alt="Product Image">
    <div class="bagProductsDetails">
        <div class="bagBrand">
            <span>${Product.brand}</span>
        </div>    
        <div class="bagTitle">
            <span">${Product.title}</span>
        </div>
        <div class="soldBy">
            <span>Sold By: <span>DPG MARKETING PVT LTD</span> </span>
        </div>
        <div class="buttons">
            <button class="sizeButton">Size: Onesize <i class="fa-solid fa-angle-down"></i></button>
            <button class="QtyButton">Qty: 1 <i class="fa-solid fa-angle-down"></i></button>
        </div>
        <div class="BagPriceDetails">
            <div class="price">Rs.<span>${Product.price}</span></div>
            <div class="MRP">Rs.<span >${Product.MRP}</span></div>
            <div class="discount"> ${Product.discount} % OFF</div>
        </div>
        <div class="return">
      <span>${Product.returnPeriod}<span>return available
        </div>
    </div>
  </div> `;});
} 

function changeColor(button) {
  const currentColor = window.getComputedStyle(button).color;
  const donationCheckbox = document.querySelector(".donationCheckbox");
  if (currentColor === 'rgb(0, 0, 0)') {  
    button.style.color = 'red';  
    button.style.border = '0.5px solid rgba(240, 11, 11, 0.89)';  
    donationCheckbox.checked = true;  
  } else {
    button.style.color = 'black';  
    button.style.border = '0.5px solid rgba(139, 138, 138, 0.205)';  
    donationCheckbox.checked = false;  
  }

  const buttons = document.querySelectorAll('.donetebuttons button'); 
  buttons.forEach(btn => {
    if (btn === button) {
    }else{
    btn.style.color = 'black';  
    btn.style.border = '0.5px solid rgba(139, 138, 138, 0.205)';
  } 
  });
}

function checkBoxClicked(){
const checkboxes = document.querySelectorAll('.itemCheckbox');
let cnt =[];
    checkboxes.forEach(checkbox => {
      if (checkbox.checked) {
        const productId = checkbox.getAttribute("value");
        cnt.push(productId);
      }
    }
  ); 
  getProductPrice(cnt);
  displayCnt(cnt);  
}

function getProductPrice(cnt){
  fetch("/getProductByIds", {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded"
    },
    body: "productId=" + encodeURIComponent(cnt)
  })
    .then(response => response.json())
    .then(data => { 
  displayProductPrice(data.productsForms);
})
}


function displayProductPrice(ProductList){
var TotalMrp =0;
var TotalPrice =0;
PRODUCTS = ProductList;
ProductList.forEach((Product)=>{
  TotalMrp +=Product.mrp;
  TotalPrice += Product.price;
});
const discountOnMRP=TotalMrp-TotalPrice;
const DisplayTotalMRP = document.querySelector(".DisplayTotalMRP");
const DisplayTotalDiscount = document.querySelector(".DisplayTotalDiscount");
const DisplayTotalAmount = document.querySelector(".DisplayTotalAmount");

DisplayTotalMRP.innerText = TotalMrp;
TotalMRP = TotalMrp;
DisplayTotalDiscount.innerText = discountOnMRP;
DisplayTotalAmount.innerText = TotalPrice+79;
TotalPRICE = TotalPrice+79;
}

function displayCnt(cnt){
  const displaycnt = document.querySelector('.displaycnt');
  const displayitemcnt = document.querySelector('.displayitemcnt');
  displaycnt.innerText = cnt.length;
  displayitemcnt.innerText = cnt.length;

  const mainCheckbox = document.querySelector(".mainCheckbox");
  if(cnt.length > 0){
      mainCheckbox.checked =true;
  }else{
    mainCheckbox.checked =false;
  }
}

