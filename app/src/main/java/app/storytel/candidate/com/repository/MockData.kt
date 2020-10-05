package app.storytel.candidate.com.repository

import app.storytel.candidate.com.model.Comment
import app.storytel.candidate.com.model.Photo
import app.storytel.candidate.com.model.PostAndPhoto

val postsMockData = listOf(
    PostAndPhoto(
        1, "sunt aut facere repellat provident",
        "quia et suscipit nsuscipit recusandae consequuntur",
        ""
    ),
    PostAndPhoto(
        2, "sunt aut facere repellat provident",
        "quia et suscipit nsuscipit recusandae consequuntur", ""
    ),
    PostAndPhoto(
        3, "sunt aut facere repellat provident",
        "quia et suscipit nsuscipit recusandae consequuntur", ""
    ),
    PostAndPhoto(
        4, "sunt aut facere repellat provident",
        "quia et suscipit nsuscipit recusandae consequuntur", ""
    ),
    PostAndPhoto(
        5, "sunt aut facere repellat provident",
        "quia et suscipit nsuscipit recusandae consequuntur", ""
    ),
    PostAndPhoto(
        6, "sunt aut facere repellat provident",
        "quia et suscipit nsuscipit recusandae consequuntur", ""
    )
)

val commentsMockData = listOf(
    Comment(
        1, 1, "id labore ex et quam laborum",
        "Eliseo@gardner.biz", "laudantium enim quasi est quidem magnam"
    ),
    Comment(
        1, 2, "id labore ex et quam laborum",
        "Eliseo@gardner.biz", "laudantium enim quasi est quidem magnam"
    ),
    Comment(
        1, 3, "id labore ex et quam laborum",
        "Eliseo@gardner.biz", "laudantium enim quasi est quidem magnam"
    ),
    Comment(
        1, 4, "id labore ex",
        "Lew@alysha.tv", "laudantium enim quasi est"
    ),
    Comment(
        1, 5, "id labore ex",
        "Hayden@althea.biz", "laudantium enim quasi est"
    )
)

val photosMockData = listOf(
    Photo(
        1, 1, "accusamus beatae",
        "https://via.placeholder.com/600/92c952",
        "https://via.placeholder.com/150/92c952"
    ),
    Photo(
        1, 2, "accusamus beatae",
        "https://via.placeholder.com/600/771796",
        "https://via.placeholder.com/150/771796"
    ),
    Photo(
        1, 3, "accusamus beatae",
        "https://via.placeholder.com/600/24f355",
        "https://via.placeholder.com/150/24f355"
    ),
    Photo(
        1, 4, "accusamus beatae",
        "https://via.placeholder.com/600/d32776",
        "https://via.placeholder.com/150/d32776"
    ),
    Photo(
        1, 5, "accusamus beatae",
        "https://via.placeholder.com/600/f66b97",
        "https://via.placeholder.com/150/f66b97"
    ),
    Photo(
        1, 6, "accusamus beatae",
        "https://via.placeholder.com/600/f66b97",
        "https://via.placeholder.com/150/f66b97"
    ),
    Photo(
        1, 7, "accusamus beatae",
        "https://via.placeholder.com/600/f66b97",
        "https://via.placeholder.com/150/f66b97"
    )
)