package com.mycodesnippets

class MoonController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [moonInstanceList: Moon.list(params), moonInstanceTotal: Moon.count()]
    }

    def create = {
        def moonInstance = new Moon()
        moonInstance.properties = params
        return [moonInstance: moonInstance]
    }

    def save = {
        def moonInstance = new Moon(params)
        if (moonInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'moon.label', default: 'Moon'), moonInstance.id])}"
            redirect(action: "show", id: moonInstance.id)
        }
        else {
            render(view: "create", model: [moonInstance: moonInstance])
        }
    }

    def show = {
        def moonInstance = Moon.get(params.id)
        if (!moonInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'moon.label', default: 'Moon'), params.id])}"
            redirect(action: "list")
        }
        else {
            [moonInstance: moonInstance]
        }
    }

    def edit = {
        def moonInstance = Moon.get(params.id)
        if (!moonInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'moon.label', default: 'Moon'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [moonInstance: moonInstance]
        }
    }

    def update = {
        def moonInstance = Moon.get(params.id)
        if (moonInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (moonInstance.version > version) {
                    
                    moonInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'moon.label', default: 'Moon')] as Object[], "Another user has updated this Moon while you were editing")
                    render(view: "edit", model: [moonInstance: moonInstance])
                    return
                }
            }
            moonInstance.properties = params
            if (!moonInstance.hasErrors() && moonInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'moon.label', default: 'Moon'), moonInstance.id])}"
                redirect(action: "show", id: moonInstance.id)
            }
            else {
                render(view: "edit", model: [moonInstance: moonInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'moon.label', default: 'Moon'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def moonInstance = Moon.get(params.id)
        if (moonInstance) {
            try {
                moonInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'moon.label', default: 'Moon'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'moon.label', default: 'Moon'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'moon.label', default: 'Moon'), params.id])}"
            redirect(action: "list")
        }
    }
}
