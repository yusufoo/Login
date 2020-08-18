package com.redGuuner.login.Utils

class UserInformation(
    var FullName: String="",
    var Email: String="",
    var Country: String="",
    var City: String="",
    var AddressOne: String="",
    var AddressTow: String = "",
    var Zip: String=""
) {


    fun updateInformation(
        fullName: String,
        email: String,
        country: String,
        city: String,
        addressOne: String,
        addressTow: String = "",
        zip: String
    ) {
        this.FullName=fullName
        this.Email=email
        this.Country=country
        this.City=city
        this.AddressOne=addressOne
        this.AddressTow=addressTow
        this.Zip=zip

    }

    fun printAllInformation(): String {
        val S="FullName : $FullName ,\n" +
                "Email : $Email ,\n" +
                "Country : $Country ,\n" +
                "City : $City ,\n" +
                "Address 1 : $AddressOne,\n" +
                "Address 2 : $AddressTow,\n" +
                "Zip : $Zip ."
        return S
    }


}