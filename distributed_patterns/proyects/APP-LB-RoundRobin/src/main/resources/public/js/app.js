var app = (function () {

   var saveData = function () {
    cadena = $("#cadena").val();
    apiclient.saveString(cadena)
    /*.then(function(datos){
    	//getData()
    });*/
   };

   var getData = function (){
       apiclient.getStrings(updateTable)
   }

   var updateTable = function(data){
       //Vaciar tabla del HTML.
       var tabla = $("table");
       var body = $("tbody");
       if (body != null) {
           body.remove();
       }
       tabla.append("<tbody>");

       //Llenar tabla del HTLM con la información.
       var cols = ""
       for (const val in data){
           cols = cols + '<tr> <td>' + data[val].string + '</td>'
           cols = cols + '<td>' + data[val].date + '</td> </tr>'
       }
       $("tbody").append(cols)
       tabla.append("</tbody>");
   }

   return {
       saveData: saveData,
       getData: getData,
       updateTable: updateTable,
   }
})();