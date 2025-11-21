function addRecord(record){
	let tbody = document.getElementById("record");
	let newReord = document.createElement("tr");
	newReord.innerHTML = `<tr><td>${record.id}</td><td>${record.clientName}</td><td>${record.date}</td></tr>`;
	tbody.appendChild(newReord);
}

function create() {
    var clientName = document.getElementById("name").value;
    var token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IkpCWTM5Ynk4WVduVmJFZGk2R1VSayJ9.eyJpc3MiOiJodHRwczovL2Rldi1zZWN1cml0eS1ob21ld29yay51cy5hdXRoMC5jb20vIiwic3ViIjoia3NNVTVISGVvOEJITFdETzdxSXprVmMzQmUxTVZydVFAY2xpZW50cyIsImF1ZCI6Imh0dHBzOi8vZGV2LXNlY3VyaXR5LWhvbWV3b3JrLnVzLmF1dGgwLmNvbS9hcGkvdjIvIiwiaWF0IjoxNzYzNzMwNzI5LCJleHAiOjE3NjM4MTcxMjksImd0eSI6ImNsaWVudC1jcmVkZW50aWFscyIsImF6cCI6ImtzTVU1SEhlbzhCSExXRE83cUl6a1ZjM0JlMU1WcnVRIn0.DNVK2HK6Pp2ei3-ioVN6OetjwsCIqQh25A6t1kxi7glaxMRYL45H8zgZK_dGyZWRYNQdP0PkBQM6PBhKIHTrknz2n0fcdGRdYO-q4he7T9VlzsRH2kreWxMrYODXmYrAWtohMsdNUvBhV-qzW843QUnhFqkLzEJE8usik8V-JDYhD7iOjagsLW0hwVvqAAySYX7bGdheNgQxZqYFwoL3Y6ZWUn8RIY50GqYJO8FrArzshzZTgJaxBTs3m0U6298Wp4yYsP3aiEk2V6PmTc22niiAFevjcLqmWQ4tuHepg4XOfIyMG1qQcgcafcMU2o0pHiYSRnLIvF-6iKeCktMPCg"; 
    $.ajax({
        type: "POST",
        url: "http://ec2-54-226-118-156.compute-1.amazonaws.com:8080/client",
        contentType: "application/json",
        headers: {
            "Authorization": "Bearer " + token
        },
        data: JSON.stringify({
            name: clientName
        }),
        success: function(result) {
            addRecord(result);
        },
        error: function(err) {
            console.error("ERROR:", err);
        }
    });
}



function getClients() {
	$.ajax({
		url: "http://ec2-54-226-118-156.compute-1.amazonaws.com:8080/clients",
		success: function(result) {
			result.forEach(record => {
				addRecord(record)
			})
		}
	});
}


getClients();