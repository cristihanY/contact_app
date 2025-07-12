package com.example.dbapp.ui.cart

import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import com.example.dbapp.core.init.MyApp
import com.example.dbapp.model.dto.CartItemDto
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.dbapp.model.enums.MessageType
import com.example.dbapp.viewmodel.MessageServiceViewModel


class CartItemViewModel(
    private val messageService: MessageServiceViewModel,
) : ViewModel() {
    private val cartItemService = MyApp.getInstance().cartItemService;

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _cartItem = MutableStateFlow<CartItemDto>(CartItemDto())
    val cartItem: StateFlow<CartItemDto> = _cartItem

    fun loadCartItem(cartItemId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            val cartFetched = withContext(Dispatchers.IO) {
                cartItemService.getItem(cartItemId)
            }
            _cartItem.value = cartFetched
            _isLoading.value = false
        }
    }

    fun updateItemCart(cartItemId: Long, quantity: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                cartItemService.updateItemQuantity(cartItemId, quantity)
            }
            messageService.showMessage("Delete successfully", MessageType.SUCCESS)
        }
    }

    fun deleteItemCart(cartItemId: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                cartItemService.deleteItemById(cartItemId)
            }
            messageService.showMessage("Delete successfully", MessageType.SUCCESS)
        }
    }

}
