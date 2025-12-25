package com.example.localapi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.localapi.modeldata.DetailSiswa
import com.example.localapi.modeldata.UIStateSiswa
import com.example.localapi.modeldata.toDataSiswa
import com.example.localapi.modeldata.toUiStateSiswa
import com.example.localapi.repositori.RepositoriDataSiswa
import com.example.localapi.uicontroller.route.DestinasiDetail
import kotlinx.coroutines.launch
import retrofit2.Response

class EditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryDataSiswa: RepositoriDataSiswa
) : ViewModel() {

    private val idSiswa: Int =
        checkNotNull(savedStateHandle[DestinasiDetail.itemIdArg])

    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    init {
        viewModelScope.launch {
            uiStateSiswa = repositoryDataSiswa
                .getSatuSiswa(idSiswa)
                .toUiStateSiswa(true)
        }
    }

    fun updateUiState(detailSiswa: DetailSiswa) {
        uiStateSiswa = UIStateSiswa(
            detailSiswa = detailSiswa,
            isEntryValid = validasiInput(detailSiswa)
        )
    }

    private fun validasiInput(uiState: DetailSiswa = uiStateSiswa.detailSiswa): Boolean {
        return with(uiState) {
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
        }
    }

    suspend fun editSatuSiswa() {
        if (validasiInput(uiStateSiswa.detailSiswa)) {
            val call: Response<Void> =
                repositoryDataSiswa.editSatuSiswa(idSiswa, uiStateSiswa.detailSiswa.toDataSiswa())

            if (call.isSuccessful) {
                println("Update Sukses : ${call.message()}")
            } else {
                println("Update Error : ${call.errorBody()}")
            }
        }
    }
}