package com.mycodesnippets

import spock.lang.*
import grails.plugin.spock.*

class UniverseSpec extends IntegrationSpec {

    def "in the beginning, there was light"() {
        given:
        def moon = Moon.build()
        def planet = Planet.build()
        def sun = Sun.build()
        def galaxy = Galaxy.build()
        def universe = Universe.build()
        assert universe.save()
        
        and:
        planet.addToMoons moon
        sun.addToPlanets planet
        galaxy.addToSuns sun
        universe.addToGalaxys galaxy
        
        when:
        def storedUniverse = Universe.get(universe.id)
        def storedGalaxy = Galaxy.get(galaxy.id)
        def storedSun = Sun.get(sun.id)
        def storedPlanet = Planet.get(planet.id)
        def storedMoon = Moon.get(moon.id)
        
        then:
        storedUniverse.galaxys.contains galaxy
        storedGalaxy.suns.contains sun
        storedSun.planets.contains planet
        storedPlanet.moons.contains moon
    }
}
