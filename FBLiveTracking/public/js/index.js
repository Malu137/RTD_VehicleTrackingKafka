let map;
let markers = new Map();

function dump(obj) {
  var out = '';
  for (var i in obj) {
  out += i + ": " + obj[i] + "\n";
  }
  return out;
 }

function generateMarker({ position, icon, title }) {
  return new google.maps.Marker({
    position, icon, title, map,
    animation: google.maps.Animation.DROP,
  });
}

function addActiveBus(datasnaps)
{
  console.log(datasnaps);
  key = datasnaps.vehicleID;
  latitude = parseFloat(datasnaps.latitude);
  longitude = parseFloat(datasnaps.longitude);
  console.log(key);
  console.log(latitude);
  console.log(longitude);
  var temp;
  if (!markers.has(key)){
    temp = generateMarker(
      { position: { lat: latitude, lng: longitude },
        icon: '/images/dashboard/placemarker_green.png', 
        title: key,
      }
    );
  markers.set(key,{temp , latitude, longitude});
  console.log(temp);
  }
  
}

function animateMarker(key ,latitude,longitude)
{
  //console.log(markers);
  console.log("Inside animator "+ key);
  current = markers.get(key);
  console.log(current);
  
  var numDeltas = 100;
  var delay = 0; //milliseconds
  var i = 0;
  var deltaLat;
  var deltaLng;
  function transition(){
      i = 0;
      deltaLat = (latitude - current.latitude)/numDeltas;
      deltaLng = (longitude - current.longitude)/numDeltas;
      console.log("Difference = "+(deltaLat*100)+" "+(deltaLng*100));
      current.temp.setIcon('/images/dashboard/placemarker_red.png');
      moveMarker();
      current.temp.setIcon('/images/dashboard/placemarker_green.png');

  }
  
  function moveMarker(){
      current.latitude += deltaLat;
      current.longitude += deltaLng;
      var latlng = new google.maps.LatLng(current.latitude, current.longitude);
      current.temp.setPosition(latlng);
      //markers.set(key,{current.temp,latitude,longitude})
      if(i!=numDeltas){
          i++;
          setTimeout(moveMarker, delay);
      }
  }

  transition();

}

  function deleteMarker(key){
    console.log("Deleting marker for "+key);
    Del = markers.get(key)
    Del.temp.setMap(null);
    markers.delete(key);


  }



//initialise Map
function initMap(){
    map = new google.maps.Map(document.getElementById("map"), {
        center: { lat: 39.6351, lng: -104.89602 },
        zoom: 12,
      }
    );

    //Place Marker at center
    /*
    destinationMarker = generateMarker(
      { position: { lat: 39.6351, lng: -104.89602 },
        icon: '/images/dashboard/placemarker_red.png', 
        title: 'Destination',
      });

    */

    // Initialize Firebase Database
    const db = firebase.database();
    const Locations = db.ref().child('ActiveBuses');

    //when bus is added
    Locations.on('child_added',(snap) =>
    {
      datasnaps = snap.val()
      console.log(datasnaps);
      addActiveBus(datasnaps);     
      
    });

    Locations.on('child_changed', snap=>
    {
      datasnap = snap.val();
      //Update Marker
      console.log("Datasnap is "+datasnap.vehicleID);

      //parseFloat(datasnaps.latitude)  longitude = parseFloat(datasnaps.longitude)

      if(datasnap.stagCounter == 6)
        {
          deleteMarker(datasnap.vehicleID);
        }
      else{
        animateMarker(datasnap.vehicleID,parseFloat(datasnap.latitude),parseFloat(datasnap.longitude));
      //Busmarker.setPositio(latlng);
      }

      

    });
    Locations.on('child_removed',snap=>
    {
      datasnap =  snap.val()
      //Delete marker
      deleteMarker(datasnap.vehicleID);
    }
     
    )

    
  }
  
  