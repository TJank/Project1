function approvePendingRow(row)
{
	let tdItems = row.parentNode.parentNode.getElementsByTagName("td");
	let requestId = tdItems[0].innerHTML;
	// now somehow send this data to EDIT / VIEW the requests!!
	document.cookie = "approveRequest=" + requestId + "; path=/TJJProject1/main/requestToApprove";
	window.location.replace("./viewRequest.html");
}



function getEmployeeInfo(){
	let url = "/TJJProject1/main/employeeInfo";
	let xhr = new XMLHttpRequest();
	
	let navEmpName = document.getElementById("employeeName");
	let pendingRequestsLink = document.getElementById("pendingRequests");
	let waitingFinalGradeBody = document.getElementById("finalGradeBody");
		
	xhr.onreadystatechange = function(){
		if(xhr.status === 200 && xhr.readyState === 4){
			let employee = JSON.parse(xhr.responseText);
			let fullname = employee["firstName"] + " " + employee["lastName"];
			navEmpName.innerHTML = fullname;

			let title = employee["title"];
			if(title !== "Employee") {
				pendingRequestsLink.classList.remove("hidden");
				if(title === "BenCo.") {
					waitingFinalGradeBody.className.remove("hidden");
					getAllWaitingFinalGradeRequests();
				}
			}
			
		}
	}
	
	xhr.open("GET", url);
	xhr.send();
	
}


function getAllPendingRequests(){
	let url = "/TJJProject1/main/getRequests/Pending/PositionLevel";
	let xhr = new XMLHttpRequest();
	
	let pendingCount = 0;
	let pendingBody = document.getElementById("pendingRequestsBody");
	
	xhr.onreadystatechange = function(){
		if(xhr.status === 200 && xhr.readyState === 4){
			let pendingRequests = JSON.parse(xhr.responseText);
			// loop through all requests and enter the URGENT ones first
			pendingRequests.forEach(function(aRequest) {
				console.log(aRequest["urgent"]);
				if(aRequest["urgent"] == true) {
					pendingCount++;
					let row = document.createElement("tr");
					row.style.background = "red";

					let rId = document.createElement("td");
					rId.innerHTML = aRequest["id"];

					let fullname = aRequest["employeeFirstName"] + " " + aRequest["employeeLastName"];
					let rName = document.createElement("td");
					rName.innerHTML = fullname;

					let rLocation = document.createElement("td");
					rLocation.innerHTML = aRequest["eventLocation"];

					let rEventType = document.createElement("td");
					rEventType.innerHTML = aRequest["eventType"];

					let rCost = document.createElement("td");
					rCost.innerHTML = aRequest["eventCost"];

					let rStatus = document.createElement("td");
					if(aRequest["waitingStatus"].includes("Info")) {
						rStatus.innerHTML = aRequest["waitingStatus"];						
					} else {
						rStatus.innerHTML = aRequest["approvalStatus"];
					}

					let viewBtn = document.createElement("td");
					viewBtn.innerHTML = '<input type="button" class="btn btn-sm defaultBtn" value="View" onclick="approvePendingRow(this)">'

					row.appendChild(rId);
					row.appendChild(rName);
					row.appendChild(rLocation);
					row.appendChild(rEventType);
					row.appendChild(rCost);
					row.appendChild(rStatus);
					row.appendChild(viewBtn);
					pendingBody.appendChild(row);
				}
			});

			pendingRequests.forEach(function(aPendingRequest) {
				if(aPendingRequest["urgent"] != true) {					
					pendingCount++;
					let row = document.createElement("tr");

					let rId = document.createElement("td");
					rId.innerHTML = aPendingRequest["id"];

					let fullname = aPendingRequest["employeeFirstName"] + " " + aPendingRequest["employeeLastName"];
					let rName = document.createElement("td");
					rName.innerHTML = fullname;

					let rLocation = document.createElement("td");
					rLocation.innerHTML = aPendingRequest["eventLocation"];

					let rEventType = document.createElement("td");
					rEventType.innerHTML = aPendingRequest["eventType"];

					let rCost = document.createElement("td");
					rCost.innerHTML = aPendingRequest["eventCost"];

					let rStatus = document.createElement("td");
					if(aPendingRequest["waitingStatus"].includes("Info")) {
						rStatus.innerHTML = aPendingRequest["waitingStatus"];
						row.style.background = "orange";
					} else {
						rStatus.innerHTML = aPendingRequest["approvalStatus"];
					}

					let viewBtn = document.createElement("td");
					viewBtn.innerHTML = '<input type="button" class="btn btn-sm defaultBtn" value="View" onclick="approvePendingRow(this)">'

					row.appendChild(rId);
					row.appendChild(rName);
					row.appendChild(rLocation);
					row.appendChild(rEventType);
					row.appendChild(rCost);
					row.appendChild(rStatus);
					row.appendChild(viewBtn);
					pendingBody.appendChild(row);
					
				} 
			});
			
			if(pendingCount > 0) {
				document.getElementById("pendingRequestsBody").deleteRow(0);
			}
		}
	}
	
	xhr.open("GET", url);
	xhr.send();
	
}

function getAllWaitingFinalGradeRequests() {
	console.log("Getting all waiting final grade requests...")
}













window.onload = function() {
	getEmployeeInfo();
	getAllPendingRequests();
}