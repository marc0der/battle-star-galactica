package com.mycodesnippets

class SunController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [sunInstanceList: Sun.list(params), sunInstanceTotal: Sun.count()]
    }

    def create = {
        def sunInstance = new Sun()
        sunInstance.properties = params
        return [sunInstance: sunInstance]
    }

    def save = {
        def sunInstance = new Sun(params)
        if (sunInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'sun.label', default: 'Sun'), sunInstance.id])}"
            redirect(action: "show", id: sunInstance.id)
        }
        else {
            render(view: "create", model: [sunInstance: sunInstance])
        }
    }

    def show = {
        def sunInstance = Sun.get(params.id)
        if (!sunInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'sun.label', default: 'Sun'), params.id])}"
            redirect(action: "list")
        }
        else {
            [sunInstance: sunInstance]
        }
    }

    def edit = {
        def sunInstance = Sun.get(params.id)
        if (!sunInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'sun.label', default: 'Sun'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [sunInstance: sunInstance]
        }
    }

    def update = {
        def sunInstance = Sun.get(params.id)
        if (sunInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (sunInstance.version > version) {
                    
                    sunInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'sun.label', default: 'Sun')] as Object[], "Another user has updated this Sun while you were editing")
                    render(view: "edit", model: [sunInstance: sunInstance])
                    return
                }
            }
            sunInstance.properties = params
            if (!sunInstance.hasErrors() && sunInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'sun.label', default: 'Sun'), sunInstance.id])}"
                redirect(action: "show", id: sunInstance.id)
            }
            else {
                render(view: "edit", model: [sunInstance: sunInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'sun.label', default: 'Sun'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def sunInstance = Sun.get(params.id)
        if (sunInstance) {
            try {
                sunInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'sun.label', default: 'Sun'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'sun.label', default: 'Sun'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'sun.label', default: 'Sun'), params.id])}"
            redirect(action: "list")
        }
    }
}
