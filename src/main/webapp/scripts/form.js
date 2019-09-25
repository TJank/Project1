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
                console.log(option.value);
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

function Request(eventTypeId, eventLocation, eventStartDate, eventStartTime, workMissed,
                 description, eventCost, projectedAmount, justification){
    this.eventTypeId = eventTypeId;
    this.eventLocation = eventLocation;
    this.eventStartDate = eventStartDate;
    this.eventStartTime = eventStartTime;
    this.workMissed = workMissed;
    this.description = description;
    this.eventCost = eventCost;
    this.projectedAmount = projectedAmount;
    this.justification = justification; 
}


//let submitBtn = document.getElementById("submitBtn");
//submitBtn.addEventListener("click", ()=> {
//    console.log("we hit the submit AJAX request!")
//    let url = "/TJJProject1/main/submitRequest";
//	let xhr = new XMLHttpRequest();
//	
//    let rEventTypeId = document.getElementById("eventTypeSelect").value;
//	let rEventLocation = document.getElementById("eventLocation").value;
//	let rEventStartDate = document.getElementById("eventStartDate").value;
//    let rEventStartTime = document.getElementById("eventStartTime").value;
//    let rWorkedMissed = document.getElementById("eventWorkMissed").value;
//	let rDescription = document.getElementById("eventDescription").value;
//	let rEventCost = document.getElementById("eventStartDate").value;
//    let rate = document.getElementById("eventRate").value;
//    let rJustification = document.getElementById("eventJustification").value;
//    
//    let rProjectedAmount = rEventCost * rate;
//
//    let request = new Request(rEventTypeId, rEventLocation, 
//                                rEventStartDate, rEventStartTime,
//                                rWorkedMissed, rDescription,
//                                rEventCost, rProjectedAmount, 
//                                rJustification);
//    
//    console.log(request);
//
//	
//	xhr.onreadystatechange = function(){
//		if(xhr.status === 200 && xhr.readyState === 4){
//			console.log("Object Sent!")
//		}
//	}
//	
//	xhr.open("POST", url);
//	xhr.send(JSON.stringify(request));
//})



window.onload = function() {
    getEventTypes();
}
