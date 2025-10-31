var apiclient = (function () {

    var getStrings = function (callback) {
        $.getJSON("/string", function (data) {
            callback(data);
        });}

    var saveString = function (myString) {
        return $.ajax({
            url: "http://ec2-3-82-36-94.compute-1.amazonaws.com:8087/string",
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