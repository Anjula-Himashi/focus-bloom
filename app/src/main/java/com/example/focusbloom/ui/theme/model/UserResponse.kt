data class UserResponse(
    val success: Boolean,
    val user: UserData?
)

data class UserData(
    val id: Int?,
    val google_id: String?,
    val email: String?,
    val name: String?,
    val picture: String?
)