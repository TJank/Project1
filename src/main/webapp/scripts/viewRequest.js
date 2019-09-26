
let requestId = -1;
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
	let url = "/TJJProject1/main/requestToApprove";
	let xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = function(){
		if(xhr.status === 200 && xhr.readyState === 4){
			let request = JSON.parse(xhr.responseText);
			requestId = request["id"];


			let nameSpan = document.getElementById("employeeNameSpan");
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

			nameSpan.value = request["employeeFirstName"] + " " + request["employeeLastName"];
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


let approveBtn = document.getElementById("approveBtn");
approveBtn.addEventListener("click", function(){
	if(requestId > 0) {
		document.cookie = "approveId=" + requestId + "; path=/TJJProject1/views/main/ApproveOrReject";
	}
});

let requestInfoBtn = document.getElementById("requestInfoBtn");
requestInfoBtn.addEventListener("click", function() {
	if(requestId > 0) {
		document.cookie = "requestInfoId=" + requestId + "; path=/TJJProject1/views/main/ApproveOrReject";
	}
});

let rejectBtn = document.getElementById("rejectBtn");
rejectBtn.addEventListener("click", function() {
	if(requestId > 0) {
		document.cookie = "rejectId=" + requestId + "; path=/TJJProject1/views/main/ApproveOrReject";
	}
});


window.onload = function() {
	getEmployeeInfo();
	getRequestInfo();
}

