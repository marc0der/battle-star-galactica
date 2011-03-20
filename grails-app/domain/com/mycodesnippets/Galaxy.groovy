package com.mycodesnippets

class Galaxy {

    String name

    static hasMany = [suns: Sun]
    
    static belongsTo = Universe

    static constraints = {
    }
    
    static namedQueries = {
        findAllByWaterbaringPlanetNames(){
            createAlias 'suns', 's'
            createAlias 's.planets', 'p'
            
            projections {
                property 'name', 'galaxy'
                property 's.name', 'sun'
                property 'p.name', 'planet'
            }
            
            eq 'p.water', true
            
            order 'galaxy'
            order 'sun'
            order 'planet'
        }

        findAllByWaterbaringMoonCountDescending(){
            createAlias 'suns', 's'
            createAlias 's.planets', 'p'
            createAlias 'p.moons', 'm'
            
			projections {
                groupProperty 'name'
                countDistinct 'm.id', 'moons'
            }
            
            eq 'm.water', true
            order 'moons', 'desc'
        }        
    }
	
	String toString(){
		name
	}
}
