    function changeColor() {
     var link1 = document.getElementById('active-link');
     var link2 = document.getElementById('nav-slot');
      link1.classList.add('active');
      link2.classList.add('active');
 }
 window.onload = changeColor;

 $(document).ready(function() {

         $("#searchForm").submit(function(event) {
             event.preventDefault();

             var venue = $("input[name='venue']:checked").val();
             var capacity = $("input[name='capacity']:checked").val();

             // AJAX POST request
             $.ajax({
                 url: "/search",
                 type: "POST",
                 data: { venue: venue, capacity: capacity },
                 success: function(data) {
                     console.log("Error:", data);
                 $("#result").html(data);
                 },
                 error: function(xhr, status, error) {
                     // Handle error here
                     console.log("Error:", error);
                 }
             });
         });
     });