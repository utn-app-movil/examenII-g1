package model

class users_reych_ {
    private var id: String = ""
    private var name: String = ""
    private var active: Boolean = false
    private var lastname: String = ""
    private var password: String = ""
    private var email: String = ""


    constructor()


    constructor(id: String, name: String, active: Boolean, password: String, email: String)
    {
        this.id = id
        this.name = name
        this.lastname = lastname
        this.active = active
        this.password = password
        this.email = email
    }


    var ID: String
        get() = this.id
        set(value) {
            this.id = value
        }

    var Name: String
        get() = this.name
        set(value) {
            this.name = value
        }

    var LastName: String
        get() = this.lastname
        set(value) {
            this.lastname = value
        }

    var Active: Boolean
        get() = this.active
        set(value) {
            this.active = value
        }

    var Password: String
        get() = this.password
        set(value) {
            this.password = value
        }

    var Email: String
        get() = this.email
        set(value) {
            this.email = value
        }

}
