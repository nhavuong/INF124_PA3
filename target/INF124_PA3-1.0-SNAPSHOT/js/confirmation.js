(function () {
    function initItems() {
        var orderID= JSON.parse(localStorage.getItem('orderID'));
        var itemList = [];
        var xhr = new XMLHttpRequest();
        xhr.open("GET","confirmation-data.php?orderID="+orderID.OrderID,true);
        xhr.send();
        xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200){
          itemList = JSON.parse(xhr.responseText);
          document.getElementById("customerName").innerHTML = itemList[0].LastName + ", " + itemList[0].FirstName;
          document.getElementById("phoneNumber").innerHTML = itemList[0].PhoneNumber;
          document.getElementById("address").innerHTML = itemList[0].ShippingAddress + ", " + itemList[0].City + ", " + itemList[0].State + " " + itemList[0].Zipcode;
          document.getElementById("method").innerHTML = itemList[0].ShippingMethod;
          document.getElementById("item").innerHTML = itemList[0].name;
          document.getElementById("price").innerHTML = itemList[0].price;
          document.getElementById("quantity").innerHTML = itemList[0].Quantity;
          document.getElementById("subtotal").innerHTML = parseFloat(itemList[0].price)*parseInt(itemList[0].Quantity);
          if (itemList[0].ShippingMethod == '6 Days Ground'){
            document.getElementById("shipping").innerHTML = '5';
          }
          else if (itemList[0].ShippingMethod == '2 Days Expedited'){
            document.getElementById("shipping").innerHTML = '10';
          }
          else {
            document.getElementById("shipping").innerHTML = '30';
          }
          
          document.getElementById("tax").innerHTML = itemList[0].Tax;
          document.getElementById("total").innerHTML = itemList[0].TotalPrice;
          document.getElementById("cardNumber").innerHTML = "xxxx-xxxx-xxxx-" + itemList[0].CardLast4Digit;
          document.getElementById("expireDate").innerHTML = itemList[0].CardExpDate;
          document.getElementById("billingAddress").innerHTML = itemList[0].ShippingAddress + ", " + itemList[0].City + ", " + itemList[0].State + " " + itemList[0].Zipcode;
        }
      };
    }
    
    document.onreadystatechange = () => {
      if (document.readyState === 'complete') {
        initItems();
      }
    };
  
  })();