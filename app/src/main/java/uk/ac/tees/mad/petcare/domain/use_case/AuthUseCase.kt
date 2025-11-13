//package uk.ac.tees.mad.petcare.domain.use_case
//
//
//
//import com.google.firebase.auth.AuthResult
//import uk.ac.tees.mad.petcare.domain.model.User
//import uk.ac.tees.mad.petcare.domain.repository.AuthRepository
//import uk.ac.tees.mad.petcare.domain.repository.UserRepository
//import javax.inject.Inject
//import kotlin.Result
//import kotlin.text.isBlank
//
//class SignUpUseCase @Inject constructor(
//    private val repository: AuthRepository
//) {
//    suspend operator fun invoke(email: String, password: String): Result<User> {
//        if (email.isBlank() || password.isBlank()) {
//            return Result.failure(Exception("Email and password cannot be empty"))
//        }
//
//        return repository.signUp(email, password)
//    }
//}
//
//
//class SignInUseCase @Inject constructor(
//    private val repository: AuthRepository,
//    private val userRepository: UserRepository
//) {
//    suspend operator fun invoke(email: String, password: String): Result<User> {
//        if (email.isBlank() || password.isBlank()) {
//            return Result.failure(Exception("Email and password cannot be empty"))
//        }
//
//        return try {
//            val authResult = repository.signIn(email, password)
//
//            val user = userRepository.getUserById(email)
//            Result.success(user)
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }
//}
