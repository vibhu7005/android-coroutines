package com.jordiee.coroutines.exercises.exercise9


import com.jordiee.coroutines.exercises.exercise8.GetUserEndpoint
import com.jordiee.coroutines.exercises.exercise8.User
import com.jordiee.coroutines.exercises.exercise8.UsersDao
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class FetchAndCacheUsersUseCaseExercise9(
    private val getUserEndpoint: GetUserEndpoint,
    private val usersDao: UsersDao
) {

    suspend fun fetchAndCacheUsers(userIds: List<String>) : List<User> = withContext(Dispatchers.Default) {
        val listOfUsers = mutableListOf<User>()
        val defferedListOfUsers = mutableListOf<Deferred<User>>()
        for (userId in userIds) {
            defferedListOfUsers.add(async {
                val user = getUserEndpoint.getUser(userId)
                usersDao.upsertUserInfo(user)
                user
            })
        }

        for (user in defferedListOfUsers) {
            listOfUsers.add(user.await())
        }
        listOfUsers
    }

}