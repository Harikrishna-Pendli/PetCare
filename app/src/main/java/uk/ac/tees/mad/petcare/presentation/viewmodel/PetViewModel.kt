package uk.ac.tees.mad.petcare.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.petcare.domain.model.Pet
import uk.ac.tees.mad.petcare.domain.repository.PetRepository
import javax.inject.Inject

@HiltViewModel
class PetViewModel @Inject constructor(
    private val petRepository: PetRepository
) : ViewModel() {

    private val _pets = MutableStateFlow<List<Pet>>(emptyList())
    val pets: StateFlow<List<Pet>> get() = _pets


    init {
        loadPets()
    }

    fun loadPets() {
        viewModelScope.launch {
            petRepository.getAllPets().collect {
                _pets.value = it
            }
        }
    }

    fun addPet(pet: Pet) {
        viewModelScope.launch {
            petRepository.insertPet(pet)
        }
    }

    fun updatePet(id: Int?, pet: Pet) {
        viewModelScope.launch {
            petRepository.updatePet(id, pet)
        }
    }

    fun deletePet(id: Int?) {
        viewModelScope.launch {
            petRepository.deletePet(id)
        }
    }
}