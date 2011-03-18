package com.mycodesnippets

class Galaxy {

    String name

    static hasMany = [suns: Sun]
    
    static belongsTo = Universe

    static constraints = {
    }
    
    static namedQueries = {
        findAllByWaterbaringBodiesDescending(){
            createAlias 'suns', 's'
            createAlias 's.planets', 'p'
            createAlias 'p.moons', 'm'
            
			projections {
                groupProperty 'name'
                countDistinct('p.id', 'planets')
                countDistinct('m.id', 'moons')
            }
            
			or {
	            eq 'p.water', true
	            eq 'm.water', true
			}
			
            order 'planets', 'desc'
            order 'moons', 'desc'
        }
    }
	
	String toString(){
		name
	}
}
