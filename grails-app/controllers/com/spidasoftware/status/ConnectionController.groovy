package com.spidasoftware.status

class ConnectionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "create", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [connectionInstanceList: Connection.list(params), connectionInstanceTotal: Connection.count()]
    }

    def create = {
        def connectionInstance = new Connection()
        connectionInstance.properties = params
        return [connectionInstance: connectionInstance]
    }

    def save = {
        def connectionInstance = new Connection(params)
        if (connectionInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'connection.label', default: 'Connection'), connectionInstance.id])}"
            redirect(uri:'/')
        }
        else {
            render(view: "create", model: [connectionInstance: connectionInstance])
        }
    }

    def show = {
        def connectionInstance = Connection.get(params.id)
        if (!connectionInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'connection.label', default: 'Connection'), params.id])}"
            redirect(uri:'/')
        }
        else {
            [connectionInstance: connectionInstance]
        }
    }

    def edit = {
        def connectionInstance = Connection.get(params.id)
        if (!connectionInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'connection.label', default: 'Connection'), params.id])}"
            redirect(uri:'/')
        }
        else {
            return [connectionInstance: connectionInstance]
        }
    }

    def update = {
        def connectionInstance = Connection.get(params.id)
        if (connectionInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (connectionInstance.version > version) {
                    connectionInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'connection.label', default: 'Connection')] as Object[], "Another user has updated this Connection while you were editing")
                    render(view: "edit", model: [connectionInstance: connectionInstance])
                    return
                }
            }
            connectionInstance.properties = params
            if (!connectionInstance.hasErrors() && connectionInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'connection.label', default: 'Connection'), connectionInstance.id])}"
                redirect(uri:'/')
            }
            else {
                render(view: "edit", model: [connectionInstance: connectionInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'connection.label', default: 'Connection'), params.id])}"
            redirect(uri:'/')
        }
    }

    def delete = {
        def connectionInstance = Connection.get(params.id)
        if (connectionInstance) {
            try {
                connectionInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'connection.label', default: 'Connection'), params.id])}"
                redirect(uri:'/')
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'connection.label', default: 'Connection'), params.id])}"
                redirect(uri:'/')
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'connection.label', default: 'Connection'), params.id])}"
            redirect(uri:'/')
        }
    }
}
