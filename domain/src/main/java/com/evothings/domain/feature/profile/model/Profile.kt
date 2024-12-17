package com.evothings.domain.feature.profile.model

import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val firstName: String = "",
    val lastName: String = "",
    val birthday: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val city: String = "",
    val cashback: Int = 0,
    val referalCode: String = "",
)

//val demoProfile = Profile(
//    firstName = "Иван",
//    lastName = "Иванов",
//    birthday = "21.09.2009",
//    phoneNumber = "+7 (999) 99-99-99",
//    email = "testmail@gmail.com",
//    city = "Москва",
//    cashback = 5,
//    referalBalance = 150,
//    referalLink = "https://google.com/",
//    referalsList = listOf(
//        Referal(
//            name = "Анна Рязанцева",
//            cashback = 2,
//            joinDate = "11.01.2024"
//        ),
//        Referal(
//            name = "Иван Викторов",
//            cashback = 2,
//            joinDate = "11.01.2024"
//        ),
//        Referal(
//            name = "Екатерина Симонохина",
//            cashback = 2,
//            joinDate = "10.01.2024"
//        ),
//        Referal(
//            name = "Павел Гиляберц",
//            cashback = 2,
//            joinDate = "05.01.2024"
//        ),
//        Referal(
//            name = "Егор Семихватов",
//            cashback = 2,
//            joinDate = "01.01.2024"
//        )
//    )
//)