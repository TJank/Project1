function viewCompletedRow(row)
{
	let tdItems = row.parentNode.parentNode.getElementsByTagName("td");
	let requestId = tdItems[0].innerHTML;
	// now somehow send this data to EDIT / VIEW the requests!!
	document.cookie = "viewRequest=" + requestId + "; path=/TJJProject1/main/requestToView";
	window.location.replace("./viewOnly.html");
}

function editPendingRow(row)
{
	let tdItems = row.parentNode.parentNode.getElementsByTagName("td");
	let requestId = tdItems[0].innerHTML;
	// now somehow send this data to EDIT / VIEW the requests!!
	document.cookie = "editRequest=" + requestId + "; path=/TJJProject1/main/requestToEdit";
	window.location.replace("./editRequest.html");
}

function viewRejectedRow(row)
{
	let tdItems = row.parentNode.parentNode.getElementsByTagName("td");
	let requestId = tdItems[0].innerHTML;
	// now somehow send this data to EDIT / VIEW the requests!!
	document.cookie = "viewRequest=" + requestId + "; path=/TJJProject1/main/requestToView";
	window.location.replace("./viewOnly.html");
}

function getEmployeeInfo(){
	let url = "/TJJProject1/main/employeeInfo";
	let xhr = new XMLHttpRequest();
	
	let navEmpName = document.getElementById("employeeName");
	let welcomeMsg = document.getElementById("userNameSpan");
	let availableAmount = document.getElementById("amount");
	let pendingRequestsLink = document.getElementById("pendingRequests");
		
	xhr.onreadystatechange = function(){
		if(xhr.status === 200 && xhr.readyState === 4){
			let employee = JSON.parse(xhr.responseText);
			let fullname = employee["firstName"] + " " + employee["lastName"];
			navEmpName.innerHTML = fullname;
			
			welcomeMsg.innerHTML = fullname;
			availableAmount.innerHTML = employee["availableAmount"];

			let title = employee["title"];
			if(title !== "Employee") {
				pendingRequestsLink.classList.remove("hidden");
			}
			
		}
	}
	
	xhr.open("GET", url);
	xhr.send();
	
}


function getAllEmployeeRequests(){
	let url = "/TJJProject1/main/getRequests/Completed/EmployeeId";
	let xhr = new XMLHttpRequest();
	
	let completedCount = 0;
	let pendingCount = 0;
	let rejectedCount = 0;
	
	let completedBody = document.getElementById("completedRequestsBody");
	let pendingBody = document.getElementById("pendingRequestsBody");
	let rejectedBody = document.getElementById("rejectedRequestsBody");
	let availableAmountSpan = document.getElementById("amount");
	let currentAmount = document.getElementById("amount").innerHTML;
	
	xhr.onreadystatechange = function(){
		if(xhr.status === 200 && xhr.readyState === 4){
			let requests = JSON.parse(xhr.responseText);
			requests.forEach(function(aRequest) {
				if(aRequest['approvalStatus'].includes('Completed')) {					
					completedCount++;
					let row = document.createElement("tr");

					let rId = document.createElement("td");
					rId.innerHTML = aRequest["id"];
					
					let rLocation = document.createElement("td");
					rLocation.innerHTML = aRequest["eventLocation"];
					
					let rEventType = document.createElement("td");
					rEventType.innerHTML = aRequest["eventType"];
					
					let rGrade = document.createElement("td");
					rGrade.innerHTML = aRequest["finalGrade"];
					
					let rCost = document.createElement("td");
					rCost.innerHTML = aRequest["eventCost"];
					
					let rAwarded = document.createElement("td");
					rAwarded.innerHTML = aRequest["awardedAmount"];
					
					let viewBtn = document.createElement("td");
					viewBtn.innerHTML = '<input type="button" class="btn btn-sm defaultBtn" value="View" onclick="editPendingRow(this)">'
					
					row.appendChild(rId);
					row.appendChild(rLocation);
					row.appendChild(rEventType);
					row.appendChild(rGrade);
					row.appendChild(rCost);
					row.appendChild(rAwarded);
					row.appendChild(viewBtn);
					completedBody.appendChild(row);
					
				} 
				
				else if (aRequest['approvalStatus'].includes('Pending')){
					pendingCount++;
					let row = document.createElement("tr");

					let rId = document.createElement("td");
					rId.innerHTML = aRequest["id"];
					
					let rLocation = document.createElement("td");
					rLocation.innerHTML = aRequest["eventLocation"];
					
					let rEventType = document.createElement("td");
					rEventType.innerHTML = aRequest["eventType"];
					
					let rStartDate = document.createElement("td");
					rStartDate.innerHTML = aRequest["eventStartDate"];
					
					let rCost = document.createElement("td");
					rCost.innerHTML = aRequest["eventCost"];
					
					let rProjected = document.createElement("td");
					rProjected.innerHTML = aRequest["projectedAmount"];
					
					let rStatus = document.createElement("td");
					if(aRequest["waitingStatus"].includes("Info")) {
						rStatus.innerHTML = aRequest["waitingStatus"];
						row.style.background = "orange";
					} else {
						rStatus.innerHTML = aRequest["approvalStatus"];
					}
					
					let editBtn = document.createElement("td");
					editBtn.innerHTML = '<input type="button" class="btn btn-sm defaultBtn" value="Edit" onclick="editPendingRow(this)">'
					
					row.appendChild(rId);
					row.appendChild(rLocation);
					row.appendChild(rEventType);
					row.appendChild(rStartDate);
					row.appendChild(rCost);
					row.appendChild(rProjected);
					row.appendChild(rStatus);
					row.appendChild(editBtn);
					pendingBody.appendChild(row);
					
				} 
				
				else if (aRequest['approvalStatus'].includes('Canceled') || aRequest['approvalStatus'].includes('Rejected')) {
					rejectedCount++;
					let row = document.createElement("tr");

					let rId = document.createElement("td");
					rId.innerHTML = aRequest["id"];
					
					let rLocation = document.createElement("td");
					rLocation.innerHTML = aRequest["eventLocation"];
					
					let rEventType = document.createElement("td");
					rEventType.innerHTML = aRequest["eventType"];
					
					let rStartDate = document.createElement("td");
					rStartDate.innerHTML = aRequest["eventStartDate"];
					
					let rCost = document.createElement("td");
					rCost.innerHTML = aRequest["eventCost"];
					
					let rProjected = document.createElement("td");
					rProjected.innerHTML = aRequest["projectedAmount"];
					
					let rStatus = document.createElement("td");
					rStatus.innerHTML = aRequest["approvalStatus"];

					let viewBtn = document.createElement("td");
					viewBtn.innerHTML = '<input type="button" class="btn btn-sm defaultBtn" value="View" onclick="viewRejectedRow(this)">'
					
					row.appendChild(rId);
					row.appendChild(rLocation);
					row.appendChild(rEventType);
					row.appendChild(rStartDate);
					row.appendChild(rCost);
					row.appendChild(rProjected);
					row.appendChild(rStatus);
					row.appendChild(viewBtn);
					rejectedBody.appendChild(row);
				}
				
			});
			
			if(completedCount > 0) {
				document.getElementById("completedRequestsBody").deleteRow(0);
			}
			if(pendingCount > 0) {
				document.getElementById("pendingRequestsBody").deleteRow(0);
			}
			if(rejectedCount > 0) {
				document.getElementById("rejectedRequestsBody").deleteRow(0);
			}
			
		}
	}
	
	xhr.open("GET", url);
	xhr.send();
	
}





window.onload = function() {
	getEmployeeInfo();
	getAllEmployeeRequests();
}

