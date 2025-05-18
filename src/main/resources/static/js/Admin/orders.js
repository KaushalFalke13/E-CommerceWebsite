function DisplayOrders(Orders){
  const div = document.querySelector(".OrderData");
  div.innerHTML = "";
  div.innerHTML = `<tr th:each="order : ${Orders}">
                    <td th:text="${order.OrderId}">#12345</td>
                    <td th:text="${order.UserName}">John Doe</td>
                    <td th:text="${order.status}">Shipped</td>
                    <td th:text="${order.TotalAmount}">$120.00</td>
                </tr>`
                }

function getdata(){
  fetch("/admin/getOrderDetails",{
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded"
    },
    body: "OrderName=" + encodeURIComponent()  
  })
  .then(response => response.json())
  .then(data => {
    DisplayOrders(data);
  })
  .catch(error => {
    console.error("Error fetching cartlist:", error);
  });
}