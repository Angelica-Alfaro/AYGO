var apiclient = (function () {

    var getStrings = function (callback) {
        $.getJSON("/string", function (data) {
            callback(data);
        });}

    var saveString = function (myString) {
        return $.ajax({
            url: "http://localhost:8087/string",
            type: "POST",
            data: myString,
            contentType: "application/x-www-form-urlencoded",
        });
        };

    return {
        //getStrings: getStrings,
        saveString : saveString, 
    };
})();