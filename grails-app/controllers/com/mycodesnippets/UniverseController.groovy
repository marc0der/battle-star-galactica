package com.mycodesnippets

class UniverseController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [universeInstanceList: Universe.list(params), universeInstanceTotal: Universe.count()]
    }

    def create = {
        def universeInstance = new Universe()
        universeInstance.properties = params
        return [universeInstance: universeInstance]
    }

    def save = {
        def universeInstance = new Universe(params)
        if (universeInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'universe.label', default: 'Universe'), universeInstance.id])}"
            redirect(action: "show", id: universeInstance.id)
        }
        else {
            render(view: "create", model: [universeInstance: universeInstance])
        }
    }

    def show = {
        def universeInstance = Universe.get(params.id)
        if (!universeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'universe.label', default: 'Universe'), params.id])}"
            redirect(action: "list")
        }
        else {
            [universeInstance: universeInstance]
        }
    }

    def edit = {
        def universeInstance = Universe.get(params.id)
        if (!universeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'universe.label', default: 'Universe'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [universeInstance: universeInstance]
        }
    }

    def update = {
        def universeInstance = Universe.get(params.id)
        if (universeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (universeInstance.version > version) {
                    
                    universeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'universe.label', default: 'Universe')] as Object[], "Another user has updated this Universe while you were editing")
                    render(view: "edit", model: [universeInstance: universeInstance])
                    return
                }
            }
            universeInstance.properties = params
            if (!universeInstance.hasErrors() && universeInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'universe.label', default: 'Universe'), universeInstance.id])}"
                redirect(action: "show", id: universeInstance.id)
            }
            else {
                render(view: "edit", model: [universeInstance: universeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'universe.label', default: 'Universe'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def universeInstance = Universe.get(params.id)
        if (universeInstance) {
            try {
                universeInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'universe.label', default: 'Universe'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'universe.label', default: 'Universe'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'universe.label', default: 'Universe'), params.id])}"
            redirect(action: "list")
        }
    }
}
