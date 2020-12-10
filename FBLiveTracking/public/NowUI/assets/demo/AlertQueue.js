var alertQueue5 = [];
var alertQueue25 = [];


alert ={
    AlertQueue:function(){
    
        const db = firebase.database();
        const Alerts = db.ref().child('Alerts');
    
        Alerts.on('value',snap =>
        {
          datasnap = snap.val();
          console.log(datasnap);

          if(alertQueue5.length==5){
              alertQueue5.shift();
          }
          alertQueue5.push(datasnap);

          if(alertQueue25.length==25){
              alertQueue25.shift();
          }
          alertQueue25.push(datasnap);
    
          var element = document.getElementById("AlertQueue5");
          var str ="<table class='table'><thead class=' text-primary'><th> Alert Type</th><th>Alert message</th></thead>  <tbody >";
          console.log(alertQueue5);
          for(var i in alertQueue5){
            console.log(alertQueue5[i]);
            for( j in alertQueue5[i])
            {
                console.log(j);
                console.log(alertQueue5[i][j]);
                str+="<tr><td>"+j+"</td><td>"+alertQueue5[i][j]+"</td></tr>";
            }
            
          }
          str+="</tbody></table>"
            console.log(str);
          element.innerHTML =str;
    
          //var element = document.getElementById("AlertQueue25");
          //element.innerHTML ="";
        });
      }
      ,
};
