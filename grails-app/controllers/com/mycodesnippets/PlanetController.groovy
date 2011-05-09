package com.mycodesnippets

class PlanetController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [planetInstanceList: Planet.list(params), planetInstanceTotal: Planet.count()]
    }

    def create = {
        def planetInstance = new Planet()
        planetInstance.properties = params
        return [planetInstance: planetInstance]
    }

    def save = {
        def planetInstance = new Planet(params)
        if (planetInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'planet.label', default: 'Planet'), planetInstance.id])}"
            redirect(action: "show", id: planetInstance.id)
        }
        else {
            render(view: "create", model: [planetInstance: planetInstance])
        }
    }

    def show = {
        def planetInstance = Planet.get(params.id)
        if (!planetInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'planet.label', default: 'Planet'), params.id])}"
            redirect(action: "list")
        }
        else {
            [planetInstance: planetInstance]
        }
    }

    def edit = {
        def planetInstance = Planet.get(params.id)
        if (!planetInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'planet.label', default: 'Planet'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [planetInstance: planetInstance]
        }
    }

    def update = {
        def planetInstance = Planet.get(params.id)
        if (planetInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (planetInstance.version > version) {
                    
                    planetInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'planet.label', default: 'Planet')] as Object[], "Another user has updated this Planet while you were editing")
                    render(view: "edit", model: [planetInstance: planetInstance])
                    return
                }
            }
            planetInstance.properties = params
            if (!planetInstance.hasErrors() && planetInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'planet.label', default: 'Planet'), planetInstance.id])}"
                redirect(action: "show", id: planetInstance.id)
            }
            else {
                render(view: "edit", model: [planetInstance: planetInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'planet.label', default: 'Planet'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def planetInstance = Planet.get(params.id)
        if (planetInstance) {
            try {
                planetInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'planet.label', default: 'Planet'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'planet.label', default: 'Planet'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'planet.label', default: 'Planet'), params.id])}"
            redirect(action: "list")
        }
    }
}
