package com.example.dbapp.ui.cart

import android.util.Log
import android.content.Context
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import com.example.dbapp.core.init.MyApp
import com.example.dbapp.model.dto.CartDto
import com.example.dbapp.model.entity.Cart
import com.example.dbapp.model.entity.Product
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.dbapp.model.enums.MessageType
import com.example.dbapp.viewmodel.MessageServiceViewModel
import com.example.dbapp.core.preference.saveCartIdToPreferences
import com.example.dbapp.core.preference.getCartIdFromPreferences
import com.example.dbapp.core.preference.clearCartIdFromPreferences


class CartViewModel(
    private val messageService: MessageServiceViewModel,
) : ViewModel() {

    private val productService = MyApp.getInstance().productService
    private val cartService = MyApp.getInstance().cartService;
    private val cartItemService = MyApp.getInstance().cartItemService;

    private val _cart = MutableStateFlow<CartDto>(CartDto())
    val cart: StateFlow<CartDto> = _cart

    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _countCartItems = MutableStateFlow(0)
    val countCartItems: StateFlow<Int> = _countCartItems

    private val _cartId = MutableStateFlow(0L)
    val currentCartId: StateFlow<Long> = _cartId

    init {
        println("hello")
    }

    fun initCart(context: Context) {
        viewModelScope.launch {
            _isLoading.value = true
            val cartId = getCartIdFromPreferences(context)

            if (cartId == -1L) {
                val (createdCart, cartFetched) = withContext(Dispatchers.IO) {
                    val newCart = Cart()
                    val created = cartService.createCart(newCart)
                    val fetched = cartService.getCart(created.id)
                    created to fetched
                }

                saveCartIdToPreferences(context, createdCart.id) // Esto sí puede estar fuera
                _cart.value = cartFetched
                _cartId.value = createdCart.id

            } else {
                _cartId.value = cartId
                loadCart(_cartId.value)
            }

            _isLoading.value = false
        }
    }


    private fun loadCart(cartId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            val cartFetched = withContext(Dispatchers.IO) {
                cartService.getCart(cartId)
            }
            _cart.value = cartFetched
            countItems(cartId)
            _isLoading.value = false
        }
    }

    fun getProductById(productId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            val fetchedProduct = withContext(Dispatchers.IO) {
                productService.getById(productId)
            }
            _product.value = fetchedProduct
            _isLoading.value = false
        }
    }

    /*
    fun addProduct(product: Product) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                productService.insert(product)
            }
            loadProducts()
            messageService.showMessage("El producto ha sido creado correctamente", MessageType.SUCCESS)
        }
    }

    fun updateProduct(productId: Long, product: Product) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                productService.updateProduct(productId, product)
            }
            loadProducts()
            messageService.showMessage("El producto ha sido actualizado correctamente", MessageType.SUCCESS)
        }
    }

    fun deleteProduct(productId: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                productService.removeProduct(productId)
            }
            loadProducts()
            messageService.showMessage("El producto ha sido eliminado correctamente", MessageType.SUCCESS)
        }
    }

    fun addCart(context: Context) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val cart = Cart().apply {}
                val createdCart = cartService.createCart(cart)
                saveCartIdToPreferences(context, createdCart.id)
            }
        }
    }*/

    fun updateItemCart(cartItemId: Long, quantity: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                cartItemService.updateItemQuantity(cartItemId, quantity)
            }
            messageService.showMessage("Delete successfully", MessageType.SUCCESS)
            loadCart(_cartId.value)
        }
    }

    fun deleteItemCart(cartItemId: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                cartItemService.deleteItemById(cartItemId)
            }
            messageService.showMessage("Delete successfully", MessageType.SUCCESS)
            loadCart(_cartId.value)
        }
    }

    fun deleteCart(context: Context) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                cartService.deleteCartById(_cartId.value)
            }
            clearCartIdFromPreferences(context)
            messageService.showMessage("Delete successfully", MessageType.SUCCESS)
            initCart(context)
        }
    }

    private fun countItems(cartId: Long) {
        viewModelScope.launch {
            val cartItemFetched = withContext(Dispatchers.IO) {
                cartItemService.getTotalQuantityByCartId(cartId)
            }
            Log.d("CartViewModel", "Cart items fetched: $cartItemFetched")
            _countCartItems.value = cartItemFetched
        }
    }

}
