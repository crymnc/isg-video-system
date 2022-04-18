function getCurrentDate() {
   var currentDate = new Date();
   var dd =  ('0' + currentDate.getDate()).slice(-2); //yields day
   var MM = ('0' + (currentDate.getMonth()+1)).slice(-2); //yields month
   var yyyy = currentDate.getFullYear(); //yields year
   var hh = currentDate.getHours();
   var mm = currentDate.getMinutes();
   //yyyy-MM-dd'T'HH:mm
   var currentDate= yyyy+'-'+MM+'-'+dd+'T'+hh+':'+mm;

   return currentDate;
}