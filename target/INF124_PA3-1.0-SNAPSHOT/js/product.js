
function getParameterByName(target) {
    // Get request URL
    let url = window.location.href;
    // Encode target parameter name to url encoding
    target = target.replace(/[\[\]]/g, "\\$&");

    // Ues regular expression to find matched parameter value
    let regex = new RegExp("[?&]" + target + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';

    // Return the decoded parameter value
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}

function  initItems(){
    
    let product = getParameterByName("product");
    console.log(typeof product);
    var xhr = new XMLHttpRequest();
    xhr.open("GET","api/TrackingServlet?product=" + product,true);
    xhr.send();
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200){
//            console.log(xhr.responseText);
            let products = JSON.parse(xhr.responseText);
            let lastProduct = null;
            let lastImghref = null;
            let lastDescription = null;
            let lastPrice = null;
            
            let viewedlist = document.getElementById("viewedItem");
            
            for(let i = 0; i < products.length; i++){
                lastProduct = products[i]["name"];                
                lastImghref = products[i]["imgHref"];
                lastDescription = products[i]["description"];              
                lastPrice = products[i]["price"];
                
                var iDiv = document.createElement('div');
                iDiv.className = 'col-2';
                viewedlist.appendChild(iDiv);
                
                var linkview = document.createElement('a');   
                
                linkview.href = 'product.html?product=' + products[i]["name"];
                iDiv.appendChild(linkview);
                
                var imageViewed = document.createElement('img');
                imageViewed.src = "picture/" + products[i]["imgHref"];
                imageViewed.style = "width:200px;height:250px;";
                linkview.appendChild(imageViewed);
                
                var title = document.createElement('h3');
                iDiv.appendChild(title);

                var link2 = document.createElement('a');
                link2.href = 'product.html?product=' + products[i]["name"];
                link2.appendChild(document.createTextNode(products[i]["name"]));
                title.appendChild(link2);     
            }


            var left = document.getElementsByClassName('left'); 

            var image = document.createElement('img');
            //image.className = "thumbnail";
            image.src = "./picture/" + lastImghref;
            image.style = "width:300px;height:350px;";
            left[0].appendChild(image)

            //Import product image
            var right = document.getElementsByClassName('right');

            var productName = document.createElement('h2');
            productName.textContent = lastProduct;
            right[0].appendChild(productName);

            var productDescription = document.createElement('h4');
            productDescription.textContent = lastDescription;
            right[0].appendChild(productDescription)

            var price = document.createElement('h5');
            price.textContent = "Price: $" + lastPrice;
            right[0].appendChild(price); 

            var inputDiv = document.createElement('div');
            inputDiv.className ="inputDiv";
            right[0].appendChild(inputDiv);

            var inputDiv1 = document.getElementsByClassName("inputDiv");
            var quantityField = document.createElement('h5');
            quantityField.textContent = "Quantity: ";
            inputDiv1[0].appendChild(quantityField);

            var quantityInput = document.createElement('input');
            quantityInput.type = "number";
            quantityInput.id = "number";
            quantityInput.defaultValue = "1";
            inputDiv1[0].appendChild(quantityInput);

            var newLine = document.createElement("br");
            right[0].appendChild(newLine);  

            //Create button
            var button = document.createElement('button');
            button.type = "button";
            button.className = "button";
            button.id = "addCart";
            button.value = lastProduct;
            right[0].appendChild(button);

            var anchor = document.getElementsByClassName('button');
            var link = document.createElement('a'); 
            //link.href = "form.html";
            link.appendChild(document.createTextNode("Add to cart"));
            anchor[0].appendChild(link);

            var newLine = document.createElement("br");
            right[0].appendChild(newLine);  
           
            //Add more
            var link_more = document.createElement('a');
            link_more.className = "add-more";
            link_more.href = "home.html";
            link_more.appendChild(document.createTextNode("More products"));
            right[0].appendChild(link_more);

            let totalProducts = 0;
            for(let i = 0; i < localStorage.length; i++){
                console.log(localStorage.key(i));
                totalProducts += parseInt(localStorage.getItem(localStorage.key(i)));
            }
            document.querySelector('.cart-update span').textContent = totalProducts;
            
            link.addEventListener('click', function() { 
                var xhr2 = new XMLHttpRequest();
                xhr2.open("GET","api/AddCartServlet?product=" + lastProduct,true);
                xhr2.send();
                
                let addingNum = document.getElementById('number').value;
                addingNum = parseInt(addingNum);
                let productNum = localStorage.getItem(lastProduct);
                productNum = parseInt(productNum);
                if(productNum){
                    localStorage.setItem(lastProduct, productNum+addingNum);
                }
                else{
                    localStorage.setItem(lastProduct, addingNum);
                }
                let totalProducts = 0;
                for(let i = 0; i < localStorage.length; i++){
                    console.log(localStorage.key(i));
                    totalProducts += parseInt(localStorage.getItem(localStorage.key(i)));
                }
                document.querySelector('.cart-update span').textContent = totalProducts;
            }, false);

       }
    };
}
  document.onreadystatechange = () => {
    if (document.readyState === 'complete') {        
        initItems();    
        
    }
  };
