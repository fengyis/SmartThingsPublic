/**
 *  SmartAC
 *
 *  Copyright 2015 IoT11
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
    name: "SmartAC",
    namespace: "",
    author: "IoT11",
    description: "Hub to Smart Vent",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
  /*  page(name:"page name", title:"page title",nextPage: "pageTwo") {
        section("section title") {
            paragraph "some text"
            input "motionSensors", "capability.motionSensor",
                title: "Motions sensors?", multiple: true
        }
    section("When activity on any of these sensors") {

        input "contactSensors", "capability.contactSensor",
            title: "Open/close sensors", multiple: true

        input "motionSensors", "capability.motionSensor",
            title: "Motion sensors?", multiple: true
    }
    section("Turn on these lights") {
        input "switches", "capability.switch", multiple: true
    }
    
    
    }*/
    

    page(name: "pageTwo", title: "SecondPage") {
      section("paragraph") {
      	//input "temperature1", "number", title: "Temperature"
        input "tempSensor", "capability.temperatureMeasurement"
        input "myLock", "capability.lock"
        input(name: "color", type: "enum", title: "Color", options: ["Red","Green","Blue","Yellow"])
        paragraph "This us how you can make a paragraph element"
        paragraph image: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
                  title: "paragraph title",
                  required: false,
                  "This is a long description that rambles on and on and on..."
        mode(name: "modeMultiple",
                 title: "pick some modes",
                 required: false)
        mode(name: "modeWithImage",
                 title: "This element has an image and a long title.",
                 required: false,
                 multiple: false,
                 image: "https://s3.amazonaws.com/smartapp-icons/Developers/ObyThingSTLogo@2x.png")
    }
    
    section("labels") {
        label(name: "label",
              title: "required:false",
             
              required: false,
              multiple: false)
        label(name: "labelRequired",
              title: "required:true",
              required: true,
              multiple: false)
        label(name: "labelWithImage",
              title: "This element has an image and a title.",
              description: "image and a title",
              required: false,
              image: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png")
    }
    
 
}


}


def installed() {
	log.debug "Installed with settings: ${settings}"
      // simple number to keep track of executions
  	state.count = 0

  // we can store maps in state
 	 state.myMap = [foo: "bar", baz: "fee"]

  // booleans are ok of course
 	 state.myBoolean = true

  // we can use array index notation if we want
 	 state['key'] = 'value'

  // we can store lists and maps, so we can make some interesting structures
  	state.myListOfMaps = [[key1: "val1", bool1: true],
                        [otherKey: ["string 1", "string 2"]]]

	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"

	unsubscribe()
	initialize()
}

def initialize() {
	// TODO: subscribe to attributes, devices, locations, etc.
    someEventHandler()
    //tempEventHandler()
	lockEventHandler()
}

// TODO: implement event handlers

def tempEventHandler(evt) {

    def currentState = tempSensor.currentState("temperature")
    log.debug "temperature value as a string: ${currentState.value}"
    log.debug "time this temperature record was created: ${currentState.date}"

    // shortcut notation - temperature measurement capability supports
    // a "temperature" attribute. We then append "State" to it.
    def anotherCurrentState = tempSensor.temperatureState
    log.debug "temperature value as an integer: ${anotherCurrentState.integerValue}"
}

def lockEventHandler(evt) {
    def currentValue = myLock.currentValue("lock")
    log.debug "the current value of myLock is $currentValue"

    // Lock capability has "lock" attribute.
    // <deviceName>.current<uppercase attribute name>:
    def anotherCurrentValue = myLock.currentLock
    log.debug "the current value of myLock using shortcut is: $anotherCurrentValue"
}




def someEventHandler(evt) {
 
  state.count = state.count + 1

  log.debug "this event handler has been called ${state.count} times since installed"

  log.debug "state.myMap.foo: ${state.myMap.foo}" // => prints "bar"

  // we can access state value using array notation if we wish
  log.debug "state['myBoolean']: ${state['myBoolean']}"

  // we can navigate our list of maps
  state.myListOfMaps.each { map ->
    log.debug "entry: $map"
    map.each {
      log.debug "key: ${it.key}, value: ${it.value}"
    }
}

}

