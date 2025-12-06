package Controller

import model.users_reych_
import Service.ContaAPIService_reych_

class ControllerUser_reych_ {

    suspend fun getUser(): List<users_reych_> {
        var User = mutableListOf<users_reych_>()

        try {
            val response = ContaAPIService_reych_.apiConta.getAllUsers()
            if (response.responseCode != 200)
                throw Exception("Error en la respuesta del servidor"response.message)

                response.data.forEach { item ->
                    user.add(users_reych_(C
                }

{



    }
}