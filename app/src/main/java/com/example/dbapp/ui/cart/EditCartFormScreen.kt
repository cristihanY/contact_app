package com.example.dbapp.ui.cart

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.compose.material3.Button
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ButtonDefaults
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.navigation.compose.rememberNavController
import com.example.dbapp.viewmodel.MessageServiceViewModel
import com.example.dbapp.ui.uiutil.DeleteConfirmationDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.dbapp.R
import com.example.dbapp.ui.client.component.TopBarClientComponent
@Composable
fun EditCartFormScreen(
    navController: NavController,
    innerPadding: PaddingValues,
    cartItemId: Long,
    viewModel: CartItemViewModel
) {
    LaunchedEffect(cartItemId) {
        viewModel.loadCartItem(cartItemId)
    }

    val cartItem by viewModel.cartItem.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (isLoading) {
        CircularProgressIndicator(Modifier.fillMaxSize())
    } else {
        var quantityText by remember { mutableStateOf(cartItem.quantity?.toString() ?: "") }


        if (cartItem.id != 0L) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                TopBarClientComponent(
                    navController = navController,
                    onSave = {
                        val finalQ = quantityText.toIntOrNull() ?: 1
                        viewModel.updateItemCart(cartItemId, finalQ)
                        navController.navigateUp()
                    },
                    textOnClick = "Guardar"
                    //modifier = Modifier.weight(0.1f)
                )

                Column(
                    modifier = Modifier.weight(0.9f)
                        .fillMaxSize()
                        .padding(horizontal = 18.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .background(Color.LightGray)
                                    .clip(CircleShape)
                                    .align(Alignment.CenterVertically)
                            ) {
                                Text("Img", Modifier.align(Alignment.Center))
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            cartItem.product?.let { product ->
                                Column {
                                    Text(text = product.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                    Text(text = "PEN ${product.price}", fontSize = 14.sp)
                                    Text(text = "Quantity: ${product.quantity}", fontSize = 14.sp)
                                }
                            } ?: run {
                                Text(
                                    text = "Producto no disponible",
                                    fontSize = 14.sp,
                                    fontStyle = FontStyle.Italic
                                )
                            }
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .clip(RoundedCornerShape(28.dp))
                                .background(Color(0xFFF0F0F0))
                                .padding(horizontal = 0.dp, vertical = 0.dp)
                        ) {
                            IconButton(onClick = {
                                val newQ = quantityText.toIntOrNull()?.minus(1) ?: 1
                                if (newQ > 0) {
                                    quantityText = newQ.toString()
                                } }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_minus),
                                    contentDescription = "Minus",
                                    modifier = Modifier.size(24.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(1.dp))

                            BasicTextField(
                                value = quantityText,
                                onValueChange = {
                                    if (it.isEmpty() || it.toIntOrNull() != null) {
                                        quantityText = it
                                    }
                                },
                                singleLine = true,
                                textStyle = TextStyle.Default.copy(
                                    textAlign = TextAlign.Center,
                                    color = Color.Black
                                ),
                                modifier = Modifier
                                    .width(40.dp)
                                    .padding(horizontal = 4.dp),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Done
                                ),
                                decorationBox = { innerTextField ->
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        innerTextField()
                                    }
                                }
                            )

                            Spacer(modifier = Modifier.width(1.dp))

                            IconButton(
                                onClick = {
                                    val newQ = quantityText.toIntOrNull()?.plus(1) ?: 1
                                    quantityText = newQ.toString()
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add",
                                    modifier = Modifier
                                        .size(24.dp)
                                )
                            }

                        }
                    }
                }


                Button(
                    onClick = { showDeleteDialog = true },
                    colors = ButtonDefaults.buttonColors(Color.Red),
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                ) {
                    Text("Eliminar del carrito", color = Color.White)
                }

                if (showDeleteDialog) {
                    DeleteConfirmationDialog(
                        onConfirm = {
                            viewModel.deleteItemCart(cartItemId)
                            showDeleteDialog = false
                            navController.navigateUp()
                        },
                        onDismiss = { showDeleteDialog = false }
                    )
                }
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun EditContactFormScreenE() {
    val navController = rememberNavController()
    val innerPadding = PaddingValues(16.dp)
    val messageViewModel = MessageServiceViewModel()
    val viewModel = CartItemViewModel(messageViewModel)

    EditCartFormScreen(
        navController = navController,
        innerPadding = innerPadding,
        cartItemId = 1,
        viewModel = viewModel)
}

