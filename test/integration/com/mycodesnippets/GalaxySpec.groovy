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
					def planetWater = (i == 1 && j == 2 && k == 1) || (i == 1 && j == 2 && k == 2) || (i == 2 && j == 3 && k == 1)
                    def planet = Planet.build(name: "planet $i $j $k", water: planetWater)
                    sun.addToPlanets planet 
                    for(l in 1..3) {
						def moonWater = (i == 1 && j == 2 && k == 1 && (l == 1)) || (i == 1 && j == 2 && k == 1 && (l == 2)) || (i == 3 && j == 2 && k == 2 && l == 3)
                        def moon = Moon.build(name: "moon $i $j $k $l", water: moonWater)
                        planet.addToMoons moon
                    }
                }
            }
        }
        
        assert universe.save()
        
    }
    
    def "find all galaxies that have waterbaring bodies descending"() {
        given:
		def results = Galaxy.findAllByWaterbaringBodiesDescending().list()
		println results
		
		expect:
        results[i][0] == name
        results[i][1] == planets
		results[i][2] == moons
        
        where:
        i    |        name  | planets | moons 
        0    |  'galaxy 1'  |       2 |     6
        1    |  'galaxy 2'  |       1 |     3
        2    |  'galaxy 3'  |       1 |     1
        
    }
    
}
