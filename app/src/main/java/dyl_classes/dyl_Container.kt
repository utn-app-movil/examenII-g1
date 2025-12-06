package dyl_classes

class dyl_Container {
    private var id: String
    private var product: String
    private var technician: String
    private var date: String

    // Constructor
    constructor(id: String, product: String, technician: String, date: String) {
        this.id = id
        this.product = product
        this.technician = technician
        this.date = date
    }

    var ID: String
        get() = this.id
        set(value) { this.id = value }

    var Product: String
        get() = this.product
        set(value) { this.product = value }

    var Technician: String
        get() = this.technician
        set(value) { this.technician = value }

    var Date: String
        get() = this.date
        set(value) { this.date = value }
}