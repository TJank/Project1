

function getEmployeeInfo(){
	let url = "/TJJProject1/main/employeeInfo";
	let xhr = new XMLHttpRequest();
	
	let navEmpName = document.getElementById("employeeName");
	let pendingRequestsLink = document.getElementById("pendingRequests");
		
	xhr.onreadystatechange = function(){
		if(xhr.status === 200 && xhr.readyState === 4){
			let employee = JSON.parse(xhr.responseText);
			let fullname = employee["firstName"] + " " + employee["lastName"];
			navEmpName.innerHTML = fullname;

			let title = employee["title"];
			if(title !== "Employee") {
				pendingRequestsLink.classList.remove("hidden");
			}
		}
	}
	
	xhr.open("GET", url);
	xhr.send();
	
}



function getRequestInfo() {
	let url = "/TJJProject1/main/requestToEdit";
	let xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = function(){
		if(xhr.status === 200 && xhr.readyState === 4){
			let request = JSON.parse(xhr.responseText);
            requestId = request["id"];
            console.log(request);
			let eventType = document.getElementById("eventType");
			let gradeType = document.getElementById("gradeType");
			let rate = document.getElementById("rate");
			let eventLocation = document.getElementById("eventLocation");
			let startDate = document.getElementById("startDate");
			let startTime = document.getElementById("startTime");
			let workMissed = document.getElementById("workMissed");
			let description = document.getElementById("description");
			let eventCost = document.getElementById("cost");
			let projectedAmount = document.getElementById("projectedAmount");
			let justification = document.getElementById("justification");
			let moreInfo = document.getElementById("moreInfo");

			eventType.value = request["eventType"];
			gradeType.value = request["gradeFormat"];
			rate.value = request["eventRate"];
			eventLocation.value = request["eventLocation"];
			startDate.value = request["eventStartDate"];
			startTime.value = request["eventStartTime"];
			workMissed.value = request["workMissed"];
			description.value = request["description"];
			eventCost.value = request["eventCost"];
			projectedAmount.value = request["projectedAmount"];
			justification.value = request["justification"];
			moreInfo.value = request["furtherInfo"];

		}
	}
	xhr.open("GET", url);
	xhr.send();
}


let saveBtn = document.getElementById("saveBtn");
saveBtn.addEventListener("click", function(){
	if(requestId > 0) {
		document.cookie = "saveId=" + requestId + "; path=/TJJProject1/views/main/EditRequest";
	}
});

let cancelBtn = document.getElementById("cancelBtn");
cancelBtn.addEventListener("click", function() {
	if(requestId > 0) {
		document.cookie = "cancelRequestId=" + requestId + "; path=/TJJProject1/views/main/EditRequest";
	}
});


window.onload = function() {
	getEmployeeInfo();
	getRequestInfo();
}