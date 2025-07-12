package com.example.dbapp.ui.orders

import android.util.Log
import android.content.Context
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import com.example.dbapp.core.init.MyApp
import com.example.dbapp.model.entity.Cart
import com.example.dbapp.model.entity.CartItem
import com.example.dbapp.model.entity.Product
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.dbapp.model.enums.MessageType
import com.example.dbapp.viewmodel.MessageServiceViewModel
import com.example.dbapp.core.preference.saveCartIdToPreferences
import com.example.dbapp.core.preference.getCartIdFromPreferences


class OrderViewModel(
    private val messageService: MessageServiceViewModel,
) : ViewModel() {

    private val productService = MyApp.getInstance().productService
    private val cartService = MyApp.getInstance().cartService;
    private val cartItemService = MyApp.getInstance().cartItemService;

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _countCartItems = MutableStateFlow(0)
    val countCartItems: StateFlow<Int> = _countCartItems

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            val productsFetched = withContext(Dispatchers.IO) {
                productService.all
            }
            _products.value = productsFetched
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
    }

    fun addCartItem(context: Context, productId: Long, quantity: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                var cartId = getCartIdFromPreferences(context)

                if (cartId == -1L) {
                    val newCart = Cart().apply {}
                    val createdCart = cartService.createCart(newCart)
                    cartId = createdCart.id
                    saveCartIdToPreferences(context, cartId)
                }

                val cartItem = CartItem(
                    cartId,
                    productId,
                    null,
                    quantity,
                     null
                )

                cartItemService.createItem(cartItem)
                loadCartItems(context)
            }
        }
    }

    fun loadCartItems(context: Context,) {
        viewModelScope.launch {
            val cartId = getCartIdFromPreferences(context)
            val cartItemFetched = withContext(Dispatchers.IO) {
                cartItemService.getTotalQuantityByCartId(cartId)
            }
            Log.d("CartViewModel", "Cart items fetched: $cartItemFetched")
            _countCartItems.value = cartItemFetched
        }
    }

}
