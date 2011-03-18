package com.mycodesnippets

class Planet {
    String name
    boolean water

    static hasMany = [moons: Moon]
    
    static belongsTo = Sun

    static constraints = {
    }
	
	String toString(){
		"$name with water:$water" 
	}

}
