//var busID ='5178';
var busIDsearch = document.getElementById("BUSID");
var busID = busIDsearch.value;
var busListener = busIDsearch.addEventListener('input',
() =>
{
  busID = busIDsearch.value;
  demo.initGoogleMaps();
}
);



demo = {
  initPickColor: function() {
    $('.pick-class-label').click(function() {
      var new_class = $(this).attr('new-class');
      var old_class = $('#display-buttons').attr('data-class');
      var display_div = $('#display-buttons');
      if (display_div.length) {
        var display_buttons = display_div.find('.btn');
        display_buttons.removeClass(old_class);
        display_buttons.addClass(new_class);
        display_div.attr('data-class', new_class);
      }
    });
  },

  initDocChart: function() {
    chartColor = "#FFFFFF";

    // General configuration for the charts with Line gradientStroke
    gradientChartOptionsConfiguration = {
      maintainAspectRatio: false,
      legend: {
        display: false
      },
      tooltips: {
        bodySpacing: 4,
        mode: "nearest",
        intersect: 0,
        position: "nearest",
        xPadding: 10,
        yPadding: 10,
        caretPadding: 10
      },
      responsive: true,
      scales: {
        yAxes: [{
          display: 0,
          gridLines: 0,
          ticks: {
            display: false
          },
          gridLines: {
            zeroLineColor: "transparent",
            drawTicks: false,
            display: false,
            drawBorder: false
          }
        }],
        xAxes: [{
          display: 0,
          gridLines: 0,
          ticks: {
            display: false
          },
          gridLines: {
            zeroLineColor: "transparent",
            drawTicks: false,
            display: false,
            drawBorder: false
          }
        }]
      },
      layout: {
        padding: {
          left: 0,
          right: 0,
          top: 15,
          bottom: 15
        }
      }
    };

    ctx = document.getElementById('lineChartExample').getContext("2d");

    gradientStroke = ctx.createLinearGradient(500, 0, 100, 0);
    gradientStroke.addColorStop(0, '#80b6f4');
    gradientStroke.addColorStop(1, chartColor);

    gradientFill = ctx.createLinearGradient(0, 170, 0, 50);
    gradientFill.addColorStop(0, "rgba(128, 182, 244, 0)");
    gradientFill.addColorStop(1, "rgba(249, 99, 59, 0.40)");

    myChart = new Chart(ctx, {
      type: 'line',
      responsive: true,
      data: {
        labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
        datasets: [{
          label: "Active Users",
          borderColor: "#f96332",
          pointBorderColor: "#FFF",
          pointBackgroundColor: "#f96332",
          pointBorderWidth: 2,
          pointHoverRadius: 4,
          pointHoverBorderWidth: 1,
          pointRadius: 4,
          fill: true,
          backgroundColor: gradientFill,
          borderWidth: 2,
          data: [542, 480, 430, 550, 530, 453, 380, 434, 568, 610, 700, 630]
        }]
      },
      options: gradientChartOptionsConfiguration
    });
  },

  initDashboardPageCharts: function() {

    chartColor = "#FFFFFF";

    // General configuration for the charts with Line gradientStroke
    gradientChartOptionsConfiguration = {
      maintainAspectRatio: false,
      legend: {
        display: false
      },
      tooltips: {
        bodySpacing: 4,
        mode: "nearest",
        intersect: 0,
        position: "nearest",
        xPadding: 10,
        yPadding: 10,
        caretPadding: 10
      },
      responsive: 1,
      scales: {
        yAxes: [{
          display: 0,
          gridLines: 0,
          ticks: {
            display: false
          },
          gridLines: {
            zeroLineColor: "transparent",
            drawTicks: false,
            display: false,
            drawBorder: false
          }
        }],
        xAxes: [{
          display: 0,
          gridLines: 0,
          ticks: {
            display: false
          },
          gridLines: {
            zeroLineColor: "transparent",
            drawTicks: false,
            display: false,
            drawBorder: false
          }
        }]
      },
      layout: {
        padding: {
          left: 0,
          right: 0,
          top: 15,
          bottom: 15
        }
      }
    };

    gradientChartOptionsConfigurationWithNumbersAndGrid = {
      maintainAspectRatio: false,
      legend: {
        display: false
      },
      tooltips: {
        bodySpacing: 4,
        mode: "nearest",
        intersect: 0,
        position: "nearest",
        xPadding: 10,
        yPadding: 10,
        caretPadding: 10
      },
      responsive: true,
      scales: {
        yAxes: [{
          gridLines: 0,
          gridLines: {
            zeroLineColor: "transparent",
            drawBorder: false
          }
        }],
        xAxes: [{
          display: 0,
          gridLines: 0,
          ticks: {
            display: false
          },
          gridLines: {
            zeroLineColor: "transparent",
            drawTicks: false,
            display: false,
            drawBorder: false
          }
        }]
      },
      layout: {
        padding: {
          left: 0,
          right: 0,
          top: 15,
          bottom: 15
        }
      }
    };

    var ctx = document.getElementById('bigDashboardChart').getContext("2d");

    var gradientStroke = ctx.createLinearGradient(500, 0, 100, 0);
    gradientStroke.addColorStop(0, '#80b6f4');
    gradientStroke.addColorStop(1, chartColor);

    var gradientFill = ctx.createLinearGradient(0, 200, 0, 50);
    gradientFill.addColorStop(0, "rgba(128, 182, 244, 0)");
    gradientFill.addColorStop(1, "rgba(255, 255, 255, 0.24)");

    var myChart = new Chart(ctx, {
      type: 'line',
      data: {
        labels: ["JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"],
        datasets: [{
          label: "Data",
          borderColor: chartColor,
          pointBorderColor: chartColor,
          pointBackgroundColor: "#1e3d60",
          pointHoverBackgroundColor: "#1e3d60",
          pointHoverBorderColor: chartColor,
          pointBorderWidth: 1,
          pointHoverRadius: 7,
          pointHoverBorderWidth: 2,
          pointRadius: 5,
          fill: true,
          backgroundColor: gradientFill,
          borderWidth: 2,
          data: [50, 150, 100, 190, 130, 90, 150, 160, 120, 140, 190, 95]
        }]
      },
      options: {
        layout: {
          padding: {
            left: 20,
            right: 20,
            top: 0,
            bottom: 0
          }
        },
        maintainAspectRatio: false,
        tooltips: {
          backgroundColor: '#fff',
          titleFontColor: '#333',
          bodyFontColor: '#666',
          bodySpacing: 4,
          xPadding: 12,
          mode: "nearest",
          intersect: 0,
          position: "nearest"
        },
        legend: {
          position: "bottom",
          fillStyle: "#FFF",
          display: false
        },
        scales: {
          yAxes: [{
            ticks: {
              fontColor: "rgba(255,255,255,0.4)",
              fontStyle: "bold",
              beginAtZero: true,
              maxTicksLimit: 5,
              padding: 10
            },
            gridLines: {
              drawTicks: true,
              drawBorder: false,
              display: true,
              color: "rgba(255,255,255,0.1)",
              zeroLineColor: "transparent"
            }

          }],
          xAxes: [{
            gridLines: {
              zeroLineColor: "transparent",
              display: false,

            },
            ticks: {
              padding: 10,
              fontColor: "rgba(255,255,255,0.4)",
              fontStyle: "bold"
            }
          }]
        }
      }
    });

  
  },

  initGoogleMaps: function() {

    const db = firebase.database();
    var buslng =-104.89602;
    var buslat=39.6351;

        function animateMarker(latitude,longitude)
      {
        //console.log(markers);
        //console.log("Inside animator "+ key);
        
        //console.log(current);
        
        var numDeltas = 100;
        var delay = 10; //milliseconds
        var i = 0;
        var deltaLat;
        var deltaLng;
        function transition(){
            i = 0;
            deltaLat = (latitude - buslat)/numDeltas;
            deltaLng = (longitude - buslng)/numDeltas;
            //console.log("Difference = "+(deltaLat*100)+" "+(deltaLng*100));
            //current.temp.setIcon('/images/dashboard/placemarker_red.png');
            moveMarker();
            //current.temp.setIcon('/images/dashboard/placemarker_green.png');

        }
        
        function moveMarker(){
            buslat += deltaLat;
            buslng += deltaLng;
            var latlng = new google.maps.LatLng(buslat, buslng);
            Busmarker.setPosition(latlng);
            //markers.set(key,{current.temp,latitude,longitude})
            if(i!=numDeltas){
                i++;
                setTimeout(moveMarker, delay);
            }
        }

        transition();

      }

    map = new google.maps.Map(document.getElementById("map"), {
      center: { lat: 39.6351, lng: -104.89602 },
      zoom: 10,
    }
    );
    var Busmarker = new google.maps.Marker(
      { position: { lat: 39.6351, lng: -104.89602 },
        //icon: '../images/dashboard/placemarker_red.png', 
        title: 'BusPosition',
      });

      Busmarker.setMap(map);

    
    const Locations = db.ref().child('ActiveBuses').child(busID);
    
    
    Locations.on( 'value',
      snap =>
      { datasnap = snap.val();
        if(datasnap==null)
          {
            console.log("Bus is inactive");
            var element = document.getElementById("BusId");
            element.innerHTML = busID;

            element = document.getElementById("BusStatus");
            element.innerHTML = "Inactive";
          }
          else{
            
            var element = document.getElementById("BusId");
            element.innerHTML = busID;
            
            element = document.getElementById("BusStatus");
            element.innerHTML = "Active";

            element = document.getElementById("BusTripDetails");
            element.innerHTML ="<h4 class='card-title'>Bus Location Detail</h4>Longitude :"+datasnap.longitude+" <br/>Latitude  : "+datasnap.latitude+"<br/><h4 class='card-title'>Bus Trip Details</h4>RouteID   : "+datasnap.routeId+"<br/>TripId    : "+datasnap.tripId+"<br/><br/>";

            
            var element = document.getElementById("InstScore");
            element.innerHTML = datasnap.instantScore;
            
            var element = document.getElementById("CumScore");
            element.innerHTML = datasnap.cumScore;

            animateMarker(parseFloat(datasnap.latitude), parseFloat(datasnap.longitude));
            //Busmarker.setPositio(latlng);
          }
      }
    );
    



    }
};


function FixedQueue( size, initialValues ){

  // If there are no initial arguments, default it to
  // an empty value so we can call the constructor in
  // a uniform way.
  initialValues = (initialValues || []);

  // Create the fixed queue array value.
  var queue = Array.apply( null, initialValues );

  // Store the fixed size in the queue.
  queue.fixedSize = size;

  // Add the class methods to the queue. Some of these have
  // to override the native Array methods in order to make
  // sure the queue lenght is maintained.
  queue.push = FixedQueue.push;
  queue.splice = FixedQueue.splice;
  queue.unshift = FixedQueue.unshift;

  // Trim any initial excess from the queue.
  FixedQueue.trimTail.call( queue );

  // Return the new queue.
  return( queue );

}


// I trim the queue down to the appropriate size, removing
// items from the beginning of the internal array.
FixedQueue.trimHead = function(){

  // Check to see if any trimming needs to be performed.
  if (this.length <= this.fixedSize){

    // No trimming, return out.
    return;

  }

  // Trim whatever is beyond the fixed size.
  Array.prototype.splice.call(
    this,
    0,
    (this.length - this.fixedSize)
  );

};


// I trim the queue down to the appropriate size, removing
// items from the end of the internal array.
FixedQueue.trimTail = function(){

  // Check to see if any trimming needs to be performed.
  if (this.length <= this.fixedSize){

    // No trimming, return out.
    return;

  }

  // Trim whatever is beyond the fixed size.
  Array.prototype.splice.call(
    this,
    this.fixedSize,
    (this.length - this.fixedSize)
  );

};


// I synthesize wrapper methods that call the native Array
// methods followed by a trimming method.
FixedQueue.wrapMethod = function( methodName, trimMethod ){

  // Create a wrapper that calls the given method.
  var wrapper = function(){

    // Get the native Array method.
    var method = Array.prototype[ methodName ];

    // Call the native method first.
    var result = method.apply( this, arguments );

    // Trim the queue now that it's been augmented.
    trimMethod.call( this );

    // Return the original value.
    return( result );

  };

  // Return the wrapper method.
  return( wrapper );

};


// Wrap the native methods.
FixedQueue.push = FixedQueue.wrapMethod(
  "push",
  FixedQueue.trimHead
);

FixedQueue.splice = FixedQueue.wrapMethod(
  "splice",
  FixedQueue.trimTail
);

FixedQueue.unshift = FixedQueue.wrapMethod(
  "unshift",
  FixedQueue.trimTail
);
