/**
 *  SmartSwitch
 *
 *  Copyright 2015 Fengyi
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
 
 
 
definition(
    name: "SmartSwitch",
    namespace: "fengyis",
    author: "Fengyi",
    description: "Aeon Lab Smart Energy Switch",
    category: "",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
	section("Title") {
		// TODO: put inputs here
        input "outlet", "capability.switch", title: "outlet", required: true, multiple: false
     
	}
}

def installed() {
	log.debug "Installed with settings: ${settings}"
  
   // subscribe(outlet, "energy", energyHandler)
    state.energy=0
	state.diff=0
    state.power=0
	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"
	
	unsubscribe()
	initialize()
}

def initialize() {
	// TODO: subscribe to attributes, devices, locations, etc.
  subscribe(outlet, "switch", switchHandler)
  subscribe(outlet, "energy", energyHandler)
  subscribe(outlet, "power", powerHandler)
}

// TODO: implement event handlers
def switchHandler(evt) {
	log.debug "in myHandler: ${evt.name} "
    log.debug "Data: ${evt.data} "
    
    
    //def latestState = outlet.currentState("energy")
    //log.debug "${lastestState.value}"
  	//log.debug "${outlet.energy}"
    
}
def energyHandler(evt) {
	log.debug "in energyHandler: ${evt.name} "
    log.debug "Energy Value: ${evt.numericValue} kwh"
    state.diff=evt.numericValue-state.energy
    state.energy=evt.numericValue
    
    SendEventData(evt)
    //def latestState = outlet.currentState("energy")
    //log.debug "${lastestState.value}"
  	//log.debug "${outlet.energy}"
    
}
def powerHandler(evt) {
	log.debug "in powerHandler: ${evt.name} "
    log.debug "Power Value: ${evt.numericValue} w"
    state.power=evt.numericValue
    //SendEventData(evt,evt.numericValue)
    //def latestState = outlet.currentState("energy")
    //log.debug "${lastestState.value}"
  	//log.debug "${outlet.energy}"
    
}

def SendEventData(evt){
try {
    def params = [
    uri: "http://104.209.176.250:8000/api/smartthings/SaveEnergy",
    body: [
    	"name":"switch",
        "room":"nwc1008",
        "power":state.power,
        "energy":state.diff
    ]
]
    httpPostJson(params) { resp ->
        resp.headers.each {
            log.debug "sucess"
        }
        //log.debug "response contentType: ${resp.contentType}"
    }
} catch (e) {
    log.debug "something went wrong: $e"
}


}
