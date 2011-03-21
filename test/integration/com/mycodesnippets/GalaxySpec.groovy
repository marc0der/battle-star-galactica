package com.mycodesnippets

import spock.lang.*
import grails.plugin.spock.*

class GalaxySpec extends IntegrationSpec {
    
    def universe

    def setup(){
        universe = Universe.build()
        for(i in 1..3) {
            def galaxy = Galaxy.build(name: "galaxy $i")
            universe.addToGalaxys galaxy
            for(j in 1..3) {
                def sun = Sun.build(name: "sun $i $j")
                galaxy.addToSuns sun
                for(k in 1..3) {
                    //two planets with water in galaxy 1, on planet with water in galaxy 2
					def planetWater = (i == 1 && j == 2 && k == 1) || (i == 1 && j == 2 && k == 2) || (i == 2 && j == 3 && k == 1)
                    def planet = Planet.build(name: "planet $i $j $k", water: planetWater)
                    sun.addToPlanets planet 
                    for(l in 1..3) {
                        //two moons with water in galaxy 1 on planet 1 2 1, one moon with water in galaxy 3 on planet 3 2 2.
						def moonWater = (i == 1 && j == 2 && k == 1 && (l == 1)) || (i == 1 && j == 2 && k == 1 && (l == 2)) || (i == 3 && j == 2 && k == 2 && l == 3)
                        def moon = Moon.build(name: "moon $i $j $k $l", water: moonWater)
                        planet.addToMoons moon
                    }
                }
            }
        }
        
        assert universe.save()
        
    }
    
    def "find all galaxies that have waterbaring planets descending"() {
        given:
        def results = Galaxy.findAllByWaterbaringPlanetNames().list()
        
		expect:
        results[i][0] == name
        results[i][1] == sun
        results[i][2] == planet
		
        where:
        i    |        name  |      sun  |        planet 
        0    |  'galaxy 1'  | 'sun 1 2' | 'planet 1 2 1'
        1    |  'galaxy 1'  | 'sun 1 2' | 'planet 1 2 2'
        2    |  'galaxy 2'  | 'sun 2 3' | 'planet 2 3 1'
		
    }

    def "find all galaxies that have waterbaring moons descending"() {
        given:
		def results = Galaxy.findAllByWaterbaringMoonCountDescending().list()
		println results
		
		expect:
        results[i][0] == name
		results[i][1] == waterMoons
        
        where:
        i    |        name  | waterMoons
        0    |  'galaxy 1'  |          2
        1    |  'galaxy 3'  |          1
        
    }
        
}
