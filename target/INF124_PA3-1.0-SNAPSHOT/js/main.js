(function () {
    function navbar() {
      var x = document.getElementById("toggle");
      if (x.style.display === "none") {
        x.style.display = "block";
      } else {
        x.style.display = "none";
      }
    }
    
    window.addEventListener("click", function (e) {
      if (!event.target.closest(".nav-icon")) {
        var x = document.getElementById("toggle");
        if (x.style.display === "block") {
          x.style.display = "none";
        }
        return;
      } else {
        // Clicked toggle-button
        return;
      }
    });
  })();
  