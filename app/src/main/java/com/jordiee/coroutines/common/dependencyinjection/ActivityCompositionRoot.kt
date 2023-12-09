package com.jordiee.coroutines.common.dependencyinjection

import androidx.fragment.app.FragmentActivity
import com.jordiee.coroutines.R
import com.jordiee.coroutines.demonstrations.coroutinescancellationcooperative.CancellableBenchmarkUseCase
import com.jordiee.coroutines.demonstrations.coroutinescancellationcooperative2.BlockingBenchmarkUseCase
import com.jordiee.coroutines.demonstrations.design.BenchmarkUseCase
import com.jordiee.coroutines.demonstrations.noncancellable.CustomersDao
import com.jordiee.coroutines.demonstrations.noncancellable.MakeCustomerPremiumUseCase
import com.jordiee.coroutines.demonstrations.noncancellable.PremiumCustomersEndpoint
import com.jordiee.coroutines.demonstrations.uncaughtexception.LoginEndpointUncaughtException
import com.jordiee.coroutines.demonstrations.uncaughtexception.LoginUseCaseUncaughtException
import com.jordiee.coroutines.demonstrations.uncaughtexception.UserStateManager
import com.jordiee.coroutines.exercises.exercise1.GetReputationEndpoint
import com.jordiee.coroutines.exercises.exercise4.FactorialUseCase
import com.jordiee.coroutines.exercises.exercise6.Exercise6BenchmarkUseCase
import com.jordiee.coroutines.exercises.exercise6.PostBenchmarkResultsEndpoint
import com.jordiee.coroutines.exercises.exercise8.FetchAndCacheUsersUseCase
import com.jordiee.coroutines.exercises.exercise8.GetUserEndpoint
import com.jordiee.coroutines.exercises.exercise8.UsersDao
import com.jordiee.coroutines.exercises.exercise9.FetchAndCacheUsersUseCaseExercise9
import com.jordiee.coroutines.solutions.exercise5.GetReputationUseCase
import com.jordiee.coroutines.solutions.exercise6.Exercise6SolutionBenchmarkUseCase
import com.jordiee.coroutines.solutions.exercise8.Exercise8SolutionFetchAndCacheUsersUseCase
import com.jordiee.coroutines.solutions.exercise9.FetchAndCacheUsersUseCaseSolutionExercise9
import com.ncapdevi.fragnav.FragNavController

class ActivityCompositionRoot(
        private val activity: FragmentActivity,
        private val appCompositionRoot: com.jordiee.coroutines.common.dependencyinjection.ApplicationCompositionRoot
) {

    val toolbarManipulator get() = activity as com.jordiee.coroutines.common.ToolbarDelegate

    val screensNavigator: com.jordiee.coroutines.common.ScreensNavigator by lazy {
        com.jordiee.coroutines.common.ScreensNavigator(fragNavController)
    }

    private val fragNavController get() = FragNavController(fragmentManager, R.id.frame_content)

    private val fragmentManager get() = activity.supportFragmentManager

    private val postBenchmarkResultsEndpoint get() = PostBenchmarkResultsEndpoint()

    private val premiumCustomersEndpoint get() = PremiumCustomersEndpoint()

    private val customersDao get() = CustomersDao()

    private val getUserEndpoint get() = GetUserEndpoint()

    private val usersDao get() = UsersDao()

    private val loginEndpointUncaughtException get() = LoginEndpointUncaughtException()

    private val userStateManager get() = UserStateManager()

    val getReputationEndpoint get() = GetReputationEndpoint()

    val factorialUseCase get() = FactorialUseCase()

    val benchmarkUseCase get() = BenchmarkUseCase()

    val cancellableBenchmarkUseCase get() = CancellableBenchmarkUseCase()

    val blockingBenchmarkUseCase get() = BlockingBenchmarkUseCase()

    val exercise6BenchmarkUseCase get() = Exercise6BenchmarkUseCase(postBenchmarkResultsEndpoint)

    val exercise6SolutionBenchmarkUseCase get() = Exercise6SolutionBenchmarkUseCase(postBenchmarkResultsEndpoint)

    val getReputationUseCase get() = GetReputationUseCase(getReputationEndpoint)

    val makeCustomerPremiumUseCase get() = MakeCustomerPremiumUseCase(premiumCustomersEndpoint, customersDao)

    val fetchAndCacheUserUseCase get() = FetchAndCacheUsersUseCase(getUserEndpoint, usersDao)

    val exercise8SolutionFetchAndCacheUserUseCase get() = Exercise8SolutionFetchAndCacheUsersUseCase(getUserEndpoint, usersDao)

    val fetchAndCacheUserUseCaseExercise9 get() = FetchAndCacheUsersUseCaseExercise9(getUserEndpoint, usersDao)

    val fetchAndCacheUserUseCaseSolutionExercise9 get() = FetchAndCacheUsersUseCaseSolutionExercise9(getUserEndpoint, usersDao)

    val loginUseCaseUncaughtException get() = LoginUseCaseUncaughtException(loginEndpointUncaughtException, userStateManager)

}