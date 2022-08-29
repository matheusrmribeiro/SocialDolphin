package com.example.socialdolphin.core.initialData

import com.example.socialdolphin.features.profile.data.models.UserRealmObject
import com.example.socialdolphin.features.timeline.data.models.CreatorRealmObject
import com.example.socialdolphin.features.timeline.data.models.OriginRealmObject
import com.example.socialdolphin.features.timeline.data.models.PostRealmObject

/**
 * Only for the mock purpose
 */
object RealmInitialData {

    val userData = listOf(
        UserRealmObject(
            id = "0001",
            name = "Matheus Ribeiro Miranda",
            socialName = "@matheusribeiro",
            createdAt = "01/02/2022"
        ),
        UserRealmObject(
            id = "0002",
            name = "Lucas Petrovisk Armadilho",
            socialName = "@lulupetro",
            createdAt = "07/02/2022"
        ),
        UserRealmObject(
            id = "0003",
            name = "Maria Cavines dos Santos",
            socialName = "@ismarix",
            createdAt = "25/06/2022"
        ),
        UserRealmObject(
            id = "0004",
            name = "Joice Xavier Ernez",
            socialName = "@joiceernez",
            createdAt = "01/08/2022"
        ),
    )

    val timelineData = listOf(
            PostRealmObject(
                id = "1660265477",
                creator = CreatorRealmObject(id = "0001", name = "Matheus Ribeiro Miranda"),
                content = "Hey people, let's start the week! GOOOD MORNIG",
                origin = null,
                active = true,
                createdAt = "08/08/2022",
                type = "post"
            ),
            PostRealmObject(
                id = "1660265478",
                creator = CreatorRealmObject(id = "0001", name = "Matheus Ribeiro Miranda"),
                content = "Today I was walking and I realized that I was moving too slow",
                origin = null,
                active = true,
                createdAt = "08/08/2022",
                type = "post"
            ),
            PostRealmObject(
                id = "1660265479",
                creator = CreatorRealmObject(id = "0002", name = "Lucas Petrovisk Armadilho"),
                content = "Take easy dude, it's 05 o'clock yet...",
                origin = OriginRealmObject(
                    id = "1660265477",
                    content = "Hey people, let's start the week! GOOOD MORNIG",
                    creator = CreatorRealmObject(id = "0001", name = "Matheus Ribeiro Miranda")
                ),
                active = true,
                createdAt = "08/08/2022",
                type = "repost"
            ),
            PostRealmObject(
                id = "1660265480",
                creator = CreatorRealmObject(id = "0003", name = "Maria Cavines dos Santos"),
                content = "Wed yet :(",
                origin = OriginRealmObject(
                    id = "1660265477",
                    content = "Hey people, let's start the week! GOOOD MORNIG",
                    creator = CreatorRealmObject(id = "0001", name = "Matheus Ribeiro Miranda")
                ),
                active = true,
                createdAt = "10/08/2022",
                type = "quote"
            ),
            PostRealmObject(
                id = "1660265481",
                creator = CreatorRealmObject(id = "0001", name = "Matheus Ribeiro Miranda"),
                content = "Today it's a good day to take a walk...",
                origin = null,
                active = true,
                createdAt = "10/08/2022",
                type = "post"
            ),
            PostRealmObject(
                id = "1660265482",
                creator = CreatorRealmObject(id = "0002", name = "Lucas Petrovisk Armadilho"),
                content = "Guys, who think it will rain today?",
                origin = null,
                active = true,
                createdAt = "10/08/2022",
                type = "post"
            ),
            PostRealmObject(
                id = "1660265483",
                creator = CreatorRealmObject(id = "0003", name = "Maria Cavines dos Santos"),
                content = "Not sure... But take a umbrella with u",
                origin = OriginRealmObject(
                    id = "1660265482",
                    content = "Guys, who think it will rain today?",
                    creator = CreatorRealmObject(id = "0002", name = "Lucas Petrovisk Armadilho")
                ),
                active = true,
                createdAt = "10/08/2022",
                type = "quote"
            ),
            PostRealmObject(
                id = "1660265484",
                creator = CreatorRealmObject(id = "0004", name = "Joice Xavier Ernez"),
                content = "Today I did a tasty scrambled eggs for breakfast",
                origin = null,
                active = true,
                createdAt = "10/08/2022",
                type = "post"
            ),
        )
}