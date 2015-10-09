/**
 *  Test
 *
 *  Copyright 2015 IoT
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
    name: "Test",
    namespace: "",
    author: "IoT",
    description: "IoT for fun",
    category: "",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
	section ("Select Devices") {
    	input "Switch0", "capability.Switch"
        input "contact1", "capability.contactSensor",
              title: "Where?"
    }
    // What light should this app be configured for?
    section ("Turn on/off a light...") {
    	input "SmartVent", "capability.switch"
        input "switch1", "capability.switch"
    }
}

def installed() {
	log.debug "Installed with settings: ${settings}"

	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"

	unsubscribe()
	initialize()
}

def initialize() {
	// TODO: subscribe to attributes, devices, locations, etc.
    subscribe(switch0,"Switch",switchHandler)
    subscribe(contact1, "contact", contactHandler)
}

// TODO: implement event handlers

def switchHandler(evt){
	log.debug "$evt.value"
}

def contactHandler(evt) {
    log.debug "$evt.value"

    // The contactSensor capability can be either "open" or "closed"
    // If it's "open", turn on the light!
    // If it's "closed" turn the light off.
    if (evt.value == "open") {
        switch1.on();
    } else if (evt.value == "closed") {
        switch1.off();
    }
}