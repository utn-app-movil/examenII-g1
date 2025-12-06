package model

import com.google.gson.annotations.SerializedName

data class dyl_DTOtech(@SerializedName("id")val ID: String,
                        @SerializedName("name")val Name:String,
                        @SerializedName("lastName")val LastName: String)
