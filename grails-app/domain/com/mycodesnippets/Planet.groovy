package com.mycodesnippets

class Planet {

    boolean hasWater

    static hasMany = [moons: Moon]
    
    static belongsTo = Sun

    static constraints = {
    }
}
