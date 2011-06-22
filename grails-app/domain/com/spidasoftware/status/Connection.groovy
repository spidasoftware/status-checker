package com.spidasoftware.status

class Connection {

    String name, url, description

    static transients = ['up']
    static constraints = {
    }

    String toString() { name }

    boolean isUp() {
        try {
            url.toURL().getText()
            return true
        }
        catch(Exception e) {
            return false
        }
    }
}
