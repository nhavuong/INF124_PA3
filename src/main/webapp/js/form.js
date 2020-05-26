
  (function () {

    let productData;
    var total;
    var totalWithoutShipping;
    var tax = 0;
    var shippingMethod = '';
      
  function  initItems(){        

     this.productData = JSON.parse(localStorage.getItem('productData'));
     console.log(this.productData);
     var productImage = document.getElementById("productImage");
      productImage.src = "./picture/" + this.productData.imgHref;
      productImage.style = "width:200px;height:250px;";
        
      var itemName = document.getElementById("itemName");
      itemName.textContent = this.productData.name;
      
      var itemQuantity = document.getElementById("itemQuantity");
      itemQuantity.textContent = this.productData.quantity;

      var itemPrice = document.getElementById("itemPrice");
      itemPrice.textContent = "$" + this.productData.price;

      var shippingPrice = document.getElementById("option").value;
      var itemShippingPrice = document.getElementById("shippingMethod");
      itemShippingPrice.textContent = "$" + shippingPrice;

      var tax = document.getElementById("tax");
      tax.textContent = "----";

      this.totalWithoutShipping = (this.productData.price*this.productData.quantity);
      document.getElementById("total").textContent = "$"+ this.totalWithoutShipping.toFixed(2);      
     } 

    document.onreadystatechange = () => {
      if (document.readyState === 'complete') {        
        initItems();        
    }
    };  

    })();

    function getShippingPrice(){  
      var shippingPrice = document.getElementById("option").value;
      var itemShippingPrice = document.getElementById("shippingMethod");
      itemShippingPrice.textContent = shippingPrice;
      switch(shippingPrice){
        case "5":
          this.shippingMethod = "6 Days Ground"
          break;
        case "10":
          this.shippingMethod = "2 Days Expedited"
          break;
        case "30":
          this.shippingMethod = "Overnight"
          break;
      }
      this.total = (this.totalWithoutShipping + parseInt(shippingPrice) + tax);
      document.getElementById("total").textContent = "$" + this.total.toFixed(2);      
    }

    function calculateTotalPrice(taxRate){      
      var shippingPrice = document.getElementById("option").value;      
      if(taxRate){
        tax = (this.totalWithoutShipping*parseFloat(taxRate));
        document.getElementById("tax").textContent = "$" + tax.toFixed(2);
      }
      else{
        document.getElementById("tax").textContent = "----";
      }
      
      var totalWithoutTax = this.totalWithoutShipping + parseInt(shippingPrice);
      this.total = totalWithoutTax + tax;      
      document.getElementById("total").textContent = "$" + this.total.toFixed(2);
    }
    
    //Get info based on zipcode
    function getInfoFromZipcode(){
      document.getElementById('zipcode-button').addEventListener('click', function () {
        var currentZipcode = zipcode.value.trim();
        if (window.XMLHttpRequest)
        {  // IE7+, Firefox, Chrome, Opera, Safari
          var xhr = new XMLHttpRequest();
        }
        else
        {  // IE5, IE6
          var xhr = new ActiveXObject ("Microsoft.XMLHTTP");
        }
        
        var newXhr = new XMLHttpRequest();
        newXhr.open ("GET", "TaxData?zipcode=" + currentZipcode);
        newXhr.send ();
        newXhr.onreadystatechange = function ()
        { // 4 means finished, and 200 means okay.
          if (newXhr.readyState == 4 && newXhr.status == 200){
            var tax = JSON.parse(newXhr.responseText);     
            console.log(tax[0].CombinedRate);
            calculateTotalPrice(tax[0].CombinedRate);
          }
        }  
     });      
    }
    
    const form = document.getElementById('form');
    const firstName = document.getElementById('firstName');
    const lastName = document.getElementById('lastName');
    const phone = document.getElementById('phone');
    const email = document.getElementById('email');
    const address = document.getElementById('address');
    const city = document.getElementById('city');
    const state = document.getElementById('state');
    const zipcode = document.getElementById('zipcode');
    const nameOnCard = document.getElementById('nameOnCard');
    const cardNo = document.getElementById('cardNo');
    const exp = document.getElementById('exp');
    const cvv = document.getElementById('cvv');  
    var isFormValid = true  
    var customerID = ''
    var orderID = ''
    var shippingMethod = ''

    var formObject = [      
      {fieldName: firstName, valid: false},
      {fieldName: lastName, valid: false},
      {fieldName: phone, valid: false},
      {fieldName: email, valid: false},
      {fieldName: address, valid: false},
      {fieldName: city, valid: false},
      {fieldName: state, valid: false},
      {fieldName: zipcode, valid: false},
      {fieldName: nameOnCard, valid: false},
      {fieldName: cardNo, valid: false},
      {fieldName: exp, valid: false},
      {fieldName: cvv, valid: false},
    ]
    
    function valiData(){
        isFormValid = true;
        checkInputs();     

        formObject.forEach(item =>{
          if(!item.valid){
            isFormValid = false;
            //return false;
          }
        })

        if(isFormValid){        
          //createNewOrderData();
          return true;
        }     
        else {
            return false;
        }
    }
    
    /*****************************************
    ADD DATA TO DATABASE
    *****************************************/
    function createNewOrderData(){
      //get shipping method
      var shippingPrice = document.getElementById("option").value;
      switch(shippingPrice){
        case "5":
          this.shippingMethod = "6 Days Ground"
          break;
        case "10":
          this.shippingMethod = "2 Days Expedited"
          break;
        case "30":
          this.shippingMethod = "Overnight"
          break;
      }
      let orderInfo = {
        ClothID: this.productData.id,
        firstNameValue : firstName.value.trim(),        
        lastNameValue : lastName.value.trim(),
        phoneValue : phone.value.trim(),
        emailValue : email.value.trim(),
        addressValue : address.value.trim(),
        cityValue : city.value.trim(),
        stateValue : state.value.trim(),
        zipcodeValue : zipcode.value.trim(),
        ShippingMethod: this.shippingMethod,
        Quantity: this.productData.quantity,
        Tax: this.tax.toFixed(2),
        TotalPrice: this.total.toFixed(2),
        CardLast4Digit: cardNo.value.trim().substring(12,17),
        CardExpDate: exp.value.trim()
      }
            
      var xhr = new XMLHttpRequest();
      xhr.onreadystatechange = function (){
        if (this.readyState == 4 && this.status == 200) {
          console.log(xhr.responseText);
              orderID = xhr.responseText;
          console.log(orderID)
          localStorage.setItem('orderID', JSON.stringify({OrderID:orderID }));
          //window.location.href = "confirmation.html";       
        }         
      }
      xhr.open("POST", "form-customerInfo.php",true);
      xhr.setRequestHeader("content-type", "application/json");
      xhr.send(JSON.stringify(orderInfo));
    }


    /************************************
        FORM VALIDATION
    *****************************************/
    function checkInputs(){
      const firstNameValue = firstName.value.trim();
      const lastNameValue = lastName.value.trim();
      const phoneValue = phone.value.trim();
      const emailValue = email.value.trim();
      const addressValue = address.value.trim();
      const cityValue = city.value.trim();
      const stateValue = state.value.trim();
      const zipcodeValue = zipcode.value.trim();
      const nameOnCardValue = nameOnCard.value.trim();
      const cardNoValue = cardNo.value.trim();
      const expValue = exp.value.trim();
      const cvvValue = cvv.value.trim();
      
      //FIRSTNAME
      if(firstNameValue === ''){
        setErrorFor(firstName, 'First Name is required');
      } 
      else if(firstNameValue.length < 3 | firstNameValue.length > 20){
        setErrorFor(firstName, 'At least 3 characters and NOT exceed 20 characters!')
      }
      else{
        setSuccessFor(firstName);
      }

      //LASTNAME
      if(lastNameValue === ''){
        setErrorFor(lastName, 'Last Name is required');
      }
      else if(lastNameValue.length < 3 | lastNameValue.length > 20){
        setErrorFor(lastName, 'At least 3 characters and NOT exceed 20 characters!')
      }
      else{
        setSuccessFor(lastName);
      }

      //PHONE
      if(phoneValue === ''){
        setErrorFor(phone, 'Phone Number is required');
      } 
      else if(phoneValue.length != 10){
        setErrorFor(phone, 'Must be 10 numbers')
      }
      
      else{
        setSuccessFor(phone);
      }
      
      //EMAIL
      var emailRegEx = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
      if(emailValue === ''){
        setErrorFor(email, 'Email is required');
      } 
      else if(!emailRegEx.test(String(emailValue).toLowerCase())){        
        setErrorFor(email, 'Email is invalid.')
      }      
      else{
        setSuccessFor(email);
      }

      //ADDRESS
      if(addressValue === ''){
        setErrorFor(address, 'Shipping Address is required');
      } 
      
      else{
        setSuccessFor(address);
      }

      //CITY
      if(cityValue === ''){
        setErrorFor(city, 'City is required');
      } 
      
      else{
        setSuccessFor(city);
      }

      //STATE
      if(stateValue === ''){
        setErrorFor(state, 'State is required');
      } 
      
      else{
        setSuccessFor(state);
      }

      //ZIPCODE
      if(zipcodeValue === ''){
        setErrorFor(zipcode, 'Zip Code is required');
      } 
      else if(zipcodeValue.length < 4){
        setErrorFor(zipcode, 'At least 4 digit!')
      }
      else{
        setSuccessFor(zipcode);
      }

      //NAME ON CARD
      if(nameOnCardValue === ''){
        setErrorFor(nameOnCard, 'Name On Card is required');
      } 
      else if(nameOnCardValue.length < 3 | nameOnCardValue.length > 20){
        setErrorFor(nameOnCard, 'At least 3 characters and NOT exceed 20 characters!')
      }
      else{
        setSuccessFor(nameOnCard);
      }

      //CARD NUMBER
      if(cardNoValue === ''){
        setErrorFor(cardNo, 'Card Number is required');
      } 
      else if(cardNoValue.length != 16){
        setErrorFor(cardNo, 'Card number must be 16 digits')
      }      
      else{
        setSuccessFor(cardNo);
      }

      //EXP DATE
      if(expValue === ''){
        setErrorFor(exp, 'Expiration Date is required');
      } 
      else if(expValue.length > 6){
        setErrorFor(exp, 'Must be mm/yy')
      }
      
      else{
        setSuccessFor(exp);
      }

      //CVV
      if(cvvValue === ''){
        setErrorFor(cvv, 'CVV Number is required');
      } 
      else if(cvvValue.length > 4){
        setErrorFor(cvv, 'Must be 3 digits')
      }
      
      else{
        setSuccessFor(cvv);
      }
    }

    function setErrorFor(input, message){
      const formControl = input.parentElement;
      const small = formControl.querySelector('small');

      //add error message inside small
      small.innerText = message;

      //add error class
      formControl.className = 'form-control error';
      formObject.forEach(item =>{
        if(item.fieldName == input){
          item.valid = false;
        }
      })

    }

    function setSuccessFor(input) {
      const formControl = input.parentElement;
      formControl.className = 'form-control success';
      formObject.forEach(item =>{
        if(item.fieldName == input){
          item.valid = true;
        }
      })
    }

    
