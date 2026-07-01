package com.siddhant.grocilist.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.siddhant.grocilist.data.repository.ProductRepository

import com.siddhant.grocilist.domain.ProductState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository

): ViewModel(){
    private val _productsState= MutableStateFlow<ProductState>(ProductState.Loading)
    val productsState : StateFlow<ProductState> =_productsState
    init {
        viewModelScope.launch {
            try{
                productRepository.getProducts()
                    .collect { products ->
                        _productsState.value = ProductState.Success(products)
                    }

            }catch (e: Exception){
                _productsState.value= ProductState.Error(e.message?: "Something went wrong")
            }

        }
    }


}