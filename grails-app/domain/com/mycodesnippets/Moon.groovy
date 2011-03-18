package com.mycodesnippets

class Moon {
    String name
    boolean water
    
    static belongsTo = Planet
    
    static constraints = {
    }
	
	String toString(){
		"$name with water:$water" 
	}
		
}
