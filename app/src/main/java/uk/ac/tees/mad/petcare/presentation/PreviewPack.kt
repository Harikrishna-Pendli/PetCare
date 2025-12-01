package uk.ac.tees.mad.petcare.presentation

import android.net.Uri
import android.os.Parcel
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FirebaseUserMetadata
import com.google.firebase.auth.MultiFactorInfo
import com.google.firebase.auth.UserInfo
import com.google.firebase.auth.zzan
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import uk.ac.tees.mad.petcare.domain.model.Pet
import uk.ac.tees.mad.petcare.domain.model.User
import uk.ac.tees.mad.petcare.domain.repository.AuthRepository
import uk.ac.tees.mad.petcare.domain.repository.PetRepository
import uk.ac.tees.mad.petcare.domain.repository.UserRepository
import uk.ac.tees.mad.petcare.presentation.viewmodel.*
import uk.ac.tees.mad.petcare.util.UiState

// FAKE AUTH VIEWMODEL
class FakeAuthViewModel : AuthViewModel(
    authRepository = FakeAuthRepository()
) {
    val fakeSignIn = MutableStateFlow<UiState<User>>(UiState.Idle)
    val fakeSignUp = MutableStateFlow<UiState<Unit>>(UiState.Idle)

    override val signInState get() = fakeSignIn
    override val signUpState get() = fakeSignUp
}

class FakeAuthRepository : AuthRepository {
    override suspend fun login(email: String, password: String) =
        Result.success(User(email))

    override suspend fun signUp(email: String, password: String) =
        Result.success(User(email))

    override suspend fun logout() {}
    override suspend fun currentUser(): User? = User("test@test.com")
    override fun checkIfUserLoggedIn() = true
}

// FAKE PET VIEWMODEL
class FakePetViewModel : PetViewModel(
    petRepository = FakePetRepo()
) {
    init {
        _pets.value = listOf(
            Pet(localId = 1, name = "Bruno", age = "2", type = "Dog"),
            Pet(localId = 2, name = "Kitty", age = "1", type = "Cat")
        )
    }
}

class FakePetRepo : PetRepository {
    private val fakeList = MutableStateFlow<List<Pet>>(emptyList())

    override fun getAllPets(): Flow<List<Pet>> = fakeList
    override suspend fun insertPet(pet: Pet) {}
    override suspend fun updatePet(localId: Int?, pet: Pet) {}
    override suspend fun deletePet(localId: Int?) {}
}

// FAKE USER PROFILE VIEWMODEL
class FakeUserProfileVM : UserProfileViewModel(
    userRepository = FakeUserRepo(),
    auth = FakeFirebaseAuth()
) {
    init {
        _user.value = User("Siya", "siya@gmail.com", notifications = true)
    }
}

class FakeUserRepo : UserRepository {
    override suspend fun createUser(user: User) {}
    override suspend fun getUserById(uid: String) =
        User("Siya", "siya@gmail.com", notifications = true)

    override suspend fun updateNotifications(uid: String, enabled: Boolean) {}
}

// FAKE FIREBASE AUTH + USER (ALL ABSTRACT METHODS IMPLEMENTED)
class FakeFirebaseAuth : FirebaseAuth() {
    override fun getCurrentUser(): FirebaseUser = FakeFirebaseUser()
}

class FakeFirebaseUser : FirebaseUser() {

    override fun getUid(): String = "preview-uid"
    override fun zzf(): List<zzan?> {
        TODO("Not yet implemented")
    }

    override fun getEmail(): String = "siya@gmail.com"

    override fun isEmailVerified() = true
    override fun getProviderId() = "firebase"
    override fun zze(): String {
        TODO("Not yet implemented")
    }

    override fun getDisplayName() = "Siya Preview"
    override fun getPhotoUrl(): Uri? = null
    override fun getPhoneNumber(): String? = null
    override fun getTenantId(): String? = null
    override fun getMultiFactor() = null
    override fun zzc(): com.google.android.gms.internal.`firebase-auth-api`.zzahv {
        TODO("Not yet implemented")
    }

    override fun zzd(): String {
        TODO("Not yet implemented")
    }

    override fun getProviderData(): MutableList<FirebaseUser> = mutableListOf()
    override fun zzg(): List<String?>? {
        TODO("Not yet implemented")
    }

    override fun zza(p0: com.google.android.gms.internal.`firebase-auth-api`.zzahv) {
        TODO("Not yet implemented")
    }

    override fun zzb(p0: List<zzan?>?) {
        TODO("Not yet implemented")
    }

    override fun zzc(p0: List<MultiFactorInfo?>) {
        TODO("Not yet implemented")
    }

    override fun isAnonymous(): Boolean {
        TODO("Not yet implemented")
    }

    override fun reload() {}
    override fun delete() {}
    override fun sendEmailVerification() {}
    override fun zza(): FirebaseApp {
        TODO("Not yet implemented")
    }

    override fun zzb(): FirebaseUser {
        TODO("Not yet implemented")
    }

    override fun zza(p0: List<UserInfo?>): FirebaseUser {
        TODO("Not yet implemented")
    }

    override fun getMetadata(): FirebaseUserMetadata? {
        TODO("Not yet implemented")
    }

    override fun updateEmail(p0: String?) {}
    override fun updatePassword(p0: String?) {}
    override fun updatePhoneN
    override fun writeToParcel(p0: Parcel, p1: Int) {
        TODO("Not yet implemented")
    }
}

// FAKE TIPS VIEWMODEL FOR PREVIEW
class FakeTipsViewModel : PetTipsViewModel() {
    init {
        _facts.value = listOf(
            uk.ac.tees.mad.petcare.data.model.DogFact("Dogs need fresh water daily"),
            uk.ac.tees.mad.petcare.data.model.DogFact("Cats sleep 12-16 hours a day")
        )
        loading.value = false
        error.value = false
    }
}