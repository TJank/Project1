function eventType(id, type, gradeFormat, rate) {
    this.id = id;
    this.type = type;
    this.gradeFormat = gradeFormat,
    this.rate = rate;
}

let eventTypesArr = [];

let eventTypeSelect = document.getElementById("eventTypeSelect");
let eventGradeType = document.getElementById("eventGradeType");
let eventRate = document.getElementById("eventRate");
let eventCostInput = document.getElementById("eventCost");
let eventProjectedAmount = document.getElementById("projectedAmount");

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


function getEventTypes(){
	let url = "/TJJProject1/main/eventTypes";
	let xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = function(){
		if(xhr.status === 200 && xhr.readyState === 4){
            let jsonEventTypes = JSON.parse(xhr.responseText);
            
            eventTypeSelect.removeChild(eventTypeSelect.options[0]);
            
            jsonEventTypes.forEach(event => {
                let id = event['id'];
                let type = event['eventType'];
                let gradeFormat = event['gradeFormat'];
                let rate = event['rate'];

                let option = document.createElement('option');
                option.appendChild(document.createTextNode(event['eventType']));
                option.value = event['id'];
                eventTypeSelect.appendChild(option);

                eventTypesArr.push(new eventType(id, type, gradeFormat, rate))
                
            });
		}
	}
	
	xhr.open("GET", url);
	xhr.send();
	
}

eventTypeSelect.addEventListener("click", function() {
    if(eventTypeSelect.value == 1) {
        eventGradeType.value = eventTypesArr[0].gradeFormat;
        eventRate.value = eventTypesArr[0].rate;
    }
    if(eventTypeSelect.value == 2) {
        eventGradeType.value = eventTypesArr[1].gradeFormat;
        eventRate.value = eventTypesArr[1].rate;
    }
    if(eventTypeSelect.value == 3) {
        eventGradeType.value = eventTypesArr[2].gradeFormat;
        eventRate.value = eventTypesArr[2].rate;
    }
    if(eventTypeSelect.value == 4) {
        eventGradeType.value = eventTypesArr[3].gradeFormat;
        eventRate.value = eventTypesArr[3].rate;
    }
    if(eventTypeSelect.value == 5) {
        eventGradeType.value = eventTypesArr[4].gradeFormat;
        eventRate.value = eventTypesArr[4].rate;
    }
    if(eventTypeSelect.value == 6) {
        eventGradeType.value = eventTypesArr[5].gradeFormat;
        eventRate.value = eventTypesArr[5].rate;
    }
});

eventTypeSelect.addEventListener("change", function() {
	if(eventTypeSelect.value == 1) {
        eventGradeType.value = eventTypesArr[0].gradeFormat;
        eventRate.value = eventTypesArr[0].rate;
    }
    if(eventTypeSelect.value == 2) {
        eventGradeType.value = eventTypesArr[1].gradeFormat;
        eventRate.value = eventTypesArr[1].rate;
    }
    if(eventTypeSelect.value == 3) {
        eventGradeType.value = eventTypesArr[2].gradeFormat;
        eventRate.value = eventTypesArr[2].rate;
    }
    if(eventTypeSelect.value == 4) {
        eventGradeType.value = eventTypesArr[3].gradeFormat;
        eventRate.value = eventTypesArr[3].rate;
    }
    if(eventTypeSelect.value == 5) {
        eventGradeType.value = eventTypesArr[4].gradeFormat;
        eventRate.value = eventTypesArr[4].rate;
    }
    if(eventTypeSelect.value == 6) {
        eventGradeType.value = eventTypesArr[5].gradeFormat;
        eventRate.value = eventTypesArr[5].rate;
    }
});

eventCostInput.addEventListener("input", function() {
    if(!isNaN(eventCost.value) && eventRate.value != "") {
    	let projectedAmt = eventCost.value * eventRate.value
    	eventProjectedAmount.value = projectedAmt;
    }
});

window.onload = function() {
    getEmployeeInfo();
    getEventTypes();
}
