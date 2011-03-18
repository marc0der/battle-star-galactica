package com.mycodesnippets

class Sun {

    static hasMany = [planets: Planet]
    
    static belongsTo = Galaxy

    static constraints = {
    }
}
