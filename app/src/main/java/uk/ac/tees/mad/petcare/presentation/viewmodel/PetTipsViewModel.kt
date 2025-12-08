package uk.ac.tees.mad.petcare.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.petcare.data.datasource.remote.RetrofitInstance
import uk.ac.tees.mad.petcare.data.model.DogFact
import javax.inject.Inject

enum class PetType { DOG, CAT }

@HiltViewModel
open class PetTipsViewModel @Inject constructor(): ViewModel() {

    protected val _facts = MutableStateFlow<List<DogFact>>(emptyList())
    val facts: StateFlow<List<DogFact>> = _facts

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow(false)
    val error: StateFlow<Boolean> = _error

    private val _selectedType = MutableStateFlow(PetType.DOG)
    val selectedType: StateFlow<PetType> = _selectedType

//    private val apiKey = "your-api-key"
    private val apiKey = " live_n6S927N7uFWt2PhiQx3HGQVersr7vLAn9obr1YCDNyeDC2rXqBIpegcKINNS1Z57 "

    fun changeType(type: PetType) {
        _selectedType.value = type
        fetchTips()
    }

    fun fetchTips() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = false
            try {
                _facts.value = when (_selectedType.value) {
                    PetType.DOG -> listOf( RetrofitInstance.api.getDogFacts())
                    PetType.CAT -> listOf( RetrofitInstance.catApi.getCatFacts())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = true
            } finally {
                _loading.value = false
            }
        }
    }
}
