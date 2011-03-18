package com.mycodesnippets

class Sun {

    String name

    static hasMany = [planets: Planet]
    
    static belongsTo = Galaxy

    static constraints = {
    }
	
	String toString(){
		name
	}

}
