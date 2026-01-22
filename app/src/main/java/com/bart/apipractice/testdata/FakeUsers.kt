package com.bart.apipractice.testdata

import com.bart.apipractice.model.Address
import com.bart.apipractice.model.Company
import com.bart.apipractice.model.Geo
import com.bart.apipractice.model.User

val FAKE_USERS = listOf(
    User(
        id = 1,
        name = "John Doe",
        userName = "johnd",
        email = "john@example.com",
        address = Address(
            street = "123 Main St",
            suite = "Apt 1",
            city = "Metropolis",
            zipcode = "12345",
            geo = Geo(lat = "12.3456", lng = "65.4321")
        ),
        phone = "123-456-7890",
        website = "johndoe.com",
        company = Company(
            name = "Doe Industries",
            catchPhrase = "Innovate Everything",
            bs = "business solutions"
        )
    ),
    User(
        id = 2,
        name = "Jane Smith",
        userName = "janes",
        email = "jane@example.com",
        address = Address(
            street = "456 Side St",
            suite = "Suite 200",
            city = "Gotham",
            zipcode = "67890",
            geo = Geo(lat = "98.7654", lng = "43.2109")
        ),
        phone = "987-654-3210",
        website = "janesmith.io",
        company = Company(
            name = "Smith Co",
            catchPhrase = "Think Different",
            bs = "tech solutions"
        )
    )
)

fun fakeUser(id: Int = 1) = FAKE_USERS.first { it.id == id }
