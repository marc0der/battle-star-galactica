package com.mycodesnippets

class GalaxyController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [galaxyInstanceList: Galaxy.list(params), galaxyInstanceTotal: Galaxy.count()]
    }

    def create = {
        def galaxyInstance = new Galaxy()
        galaxyInstance.properties = params
        return [galaxyInstance: galaxyInstance]
    }

    def save = {
        def galaxyInstance = new Galaxy(params)
        if (galaxyInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'galaxy.label', default: 'Galaxy'), galaxyInstance.id])}"
            redirect(action: "show", id: galaxyInstance.id)
        }
        else {
            render(view: "create", model: [galaxyInstance: galaxyInstance])
        }
    }

    def show = {
        def galaxyInstance = Galaxy.get(params.id)
        if (!galaxyInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'galaxy.label', default: 'Galaxy'), params.id])}"
            redirect(action: "list")
        }
        else {
            [galaxyInstance: galaxyInstance]
        }
    }

    def edit = {
        def galaxyInstance = Galaxy.get(params.id)
        if (!galaxyInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'galaxy.label', default: 'Galaxy'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [galaxyInstance: galaxyInstance]
        }
    }

    def update = {
        def galaxyInstance = Galaxy.get(params.id)
        if (galaxyInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (galaxyInstance.version > version) {
                    
                    galaxyInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'galaxy.label', default: 'Galaxy')] as Object[], "Another user has updated this Galaxy while you were editing")
                    render(view: "edit", model: [galaxyInstance: galaxyInstance])
                    return
                }
            }
            galaxyInstance.properties = params
            if (!galaxyInstance.hasErrors() && galaxyInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'galaxy.label', default: 'Galaxy'), galaxyInstance.id])}"
                redirect(action: "show", id: galaxyInstance.id)
            }
            else {
                render(view: "edit", model: [galaxyInstance: galaxyInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'galaxy.label', default: 'Galaxy'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def galaxyInstance = Galaxy.get(params.id)
        if (galaxyInstance) {
            try {
                galaxyInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'galaxy.label', default: 'Galaxy'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'galaxy.label', default: 'Galaxy'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'galaxy.label', default: 'Galaxy'), params.id])}"
            redirect(action: "list")
        }
    }
}
