/**
 *  Keen Data Collection
 *
 *  Copyright 2014 Gregg Altschul
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
    name: "Keen Data Collection",
    namespace: "Keen Home",
    author: "Gregg Altschul",
    description: "Keen Data Collection App",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
    section("Allow Endpoint to Control These Things...") {
        input "vents", "capability.temperatureMeasurement", title: "Select all of your vents", multiple: true
        href "http://keenhome.io"
    }
    section("Stat Collection...") {
        label name: "statsURI", title: "Stats URI", required: true
        // input name: "statUri", title: "Enter URI", multiple: false, required: true
    }
}

def installed() {
    log.debug "Installed with settings: ${settings}"

    /*
    httpPostJson(uri: statsUri, 
            path: "/locations",  
            body: [id: location.id, 
                    zipCode: location.zipCode, 
                    lat: location.latitude, 
                    lon: location.longitude,
                    name: location.name])
        {
            log.debug "location successfully posted"
        }
    */
    initialize(true)
}

def updated() {
    log.debug "Updated with settings: ${settings}"

    unsubscribe()
    initialize(true)
}

def initialize(updated) {
    // TODO: subscribe to attributes, devices, locations, etc.
    log.trace "initialize()"

    subscribe(vents, "temperature", handleTemperature)
    subscribe(vents, "level", handleLevel)
    subscribe(vents, "pressure", handlePressure)
    subscribe(vents, "battery", handleBatteries)
	subscribe(vents, "humidity", handleHumidity)

    initializeVents(updated)
}

def initializeVents(updated) {
    vents.each() { vent ->
        log.debug "vent ${vent.displayName} id is " + vent.currentValue("zigbeeId")
    }
}

def handleTemperature(evt) {
    log.trace "handleTemperature()"
    sendEventData(evt, "temperature", evt.numericValue)
}

def handleHumidity(evt) {
    log.trace "handleHuymidity()"
    log.debug "humidity json: ${evt.longValue }"
    sendEventData(evt, "humidity", evt.longValue)
}

def handleLevel(evt) {
    log.trace "handleLevel()"
    sendEventData(evt, "level", evt.numericValue)
}

def handlePressure(evt) {
    log.trace "handlePressure()"
    sendEventData(evt, "pressure", evt.numericValue)
}

def handleBatteries(evt) {
    log.trace "handleBatteries()"
    sendEventData(evt, "battery", evt.numericValue)
}

def sendEventData(evt, tag, value) {
    log.trace "sendEventData()"
    
    def vent = vents.find { it.id == evt.deviceId }
    def zigbeeId = vent.currentValue("zigbeeId")
    log.debug "\tdeviceId: ${evt.deviceId}"
    log.debug "\tzigbeeId: ${zigbeeId}"
    log.debug "\tdevice name:  ${evt.displayName }"
//    log.devug "\tvalue: ${value}"
    
    httpPostJson(uri: statsURI, 
        path: "/devices/" + zigbeeId + '/' + tag,
        body: [
            date: evt.isoDate,
            value: value,
            name: evt.displayName 
            ])
    {
        log.debug "tag '${tag}' successfully posted"
    }
}

def packageData(data) {

}