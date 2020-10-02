package app.storytel.candidate.com.model

data class PostAndPhoto(
    val id: Int,
    val title: String,
    val body: String,
    var thumbnailUrl: String
)