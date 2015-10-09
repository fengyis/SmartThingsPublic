/**
 *  Call Parse API
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
 
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.GET
import static groovyx.net.http.ContentType.TEXT

definition(
    name: "Call Parse API",
    namespace: "",
    author: "IoT",
    description: "integrate parse API",
    category: "",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    oauth: true)


preferences {
	section("Title") {
		// TODO: put inputs here
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
      //Setup a connection to pull data in with REST
      
Put()




}


// TODO: implement event handlers
def Put(){

def params = [
    uri: "https://tDqgJgXy6F4s7imyPGuPEFyLFkeEkm3B1kEUMg8D:javascript-key=L39wgnjcojxnBS6JG18fEvnIAngwxsNi6cMqWACM@api.parse.com/1/classes/TestObject/R9rJyA1Jx0",
    body: [
        "Situation":"slsb"
    ]
]

try {
    httpPutJson(params) { resp ->
        resp.headers.each {
            log.debug "${it.name} : ${it.value}"
        }
        log.debug "response contentType: ${resp.    contentType}"
    }
} catch (e) {
    log.debug "something went wrong: $e"
}

}
def GetURL(){

    try {
    	
        httpGet("https://tDqgJgXy6F4s7imyPGuPEFyLFkeEkm3B1kEUMg8D:javascript-key=L39wgnjcojxnBS6JG18fEvnIAngwxsNi6cMqWACM@api.parse.com/1/classes/TestObject/R9rJyA1Jx0") 
        { 
        resp ->   
            log.debug "response data: ${resp.data}"
           // def slurper = new JsonSlurper()
           // def result = slurper.parseText(resp.data)
           // log.debug "result is ${result}"
        }
    } catch (e) {
        log.error "something went wrong: $e"
    }
}

def GetURL2(){

def http = new HTTPBuilder( 'http://api.parse.com' )

http.request(GET,JSON) { req ->
  uri.path = '/1/classes/TestObject/R9rJyA1Jx0' // overrides any path in the default URL
  
  headers.'User-Agent' = 'Mozilla/5.0'
  headers.'X-Parse-Application-Id'= 'tDqgJgXy6F4s7imyPGuPEFyLFkeEkm3B1kEUMg8D'
  headers.'X-Parse-REST-API-Key'= 'oB1DseqDiajKRJsOsQ56cDCBYuMgYkFi5hPm9VEd'
  
            
  response.success = { resp, reader ->
    assert resp.status == 200
    println "My response handler got response: ${resp.statusLine}"
    println "Response length: ${resp.headers.'Content-Length'}"
    System.out << reader // print response reader
  }

  // called only for a 404 (not found) status code:
  response.'404' = { resp ->
    println 'Not found'
  }
}


}