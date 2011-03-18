package com.mycodesnippets

class Galaxy {

    static hasMany = [suns: Sun]
    
    static belongsTo = Universe

    static constraints = {
    }
}
