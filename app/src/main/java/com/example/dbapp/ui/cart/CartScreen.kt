package com.example.dbapp.ui.cart

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dbapp.model.entity.Order
import com.example.dbapp.ui.client.component.TopBarCartComponent
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberDismissState
import kotlinx.coroutines.delay
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.ui.platform.LocalContext
import com.example.dbapp.model.dto.CartItemDto
import com.example.dbapp.model.dto.CartSummary

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> SwipeToDeleted(
    item: T,
    onDelete: (T) -> Unit,
    animationDuration: Int = 500,
    content: @Composable (T) -> Unit
) {
    var isRemoved by remember { mutableStateOf(false) }

    val state = rememberDismissState(
        confirmStateChange = { value ->
            if (value == DismissValue.DismissedToStart) {
                isRemoved = true
                true
            } else {
                false
            }
        }
    )

    LaunchedEffect(key1 = isRemoved) {
        if (isRemoved) {
            delay(animationDuration.toLong())
            onDelete(item)
        }
    }

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismiss(
            state = state,
            background = {
                Deleting(swipeDismissState = state)
            },
            dismissContent = {
                content(item)
            },
            directions = setOf(DismissDirection.EndToStart)
        )
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Deleting(
    modifier: Modifier = Modifier,
    swipeDismissState: DismissState
) {
    val color = if(swipeDismissState.dismissDirection == DismissDirection.EndToStart) {
        Color.Red
    } else Color.Transparent

    Box (modifier = Modifier
        .fillMaxSize()
        .background(color)
        .padding(16.dp),
        contentAlignment = Alignment.CenterEnd) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = Color.White)
    }
    
}

@Composable
fun CartScreen(
    navController: NavController,
    innerPadding: PaddingValues,
    viewModel: CartViewModel,
    onSave: (Order) -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.initCart(context)
    }
    val isLoading by viewModel.isLoading.collectAsState()
    val cart by viewModel.cart.collectAsState()
    val countCartItem by viewModel.countCartItems.collectAsState()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        // TopBar
        TopBarCartComponent(
            navController = navController
        )
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp) // Tamaño pequeño
                )
            }
        } else {
            if (cart.id != 0L) {
                val items = remember(cart) {
                    mutableStateListOf<CartItemDto>().apply {
                        clear()
                        addAll(cart.item)
                    }
                }
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Column (
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        SecondaryTopBar(onDeleteCart = { viewModel.deleteCart(context) })
                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            item {
                                Row (
                                    modifier = Modifier
                                        .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp))
                                        .padding(12.dp)
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(vertical = 12.dp),
                                        text = "Agregar cliente")
                                }

                            }

                            item{
                                Spacer(modifier = Modifier.padding(vertical = 6.dp))
                            }

                            item {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(
                                            1.dp,
                                            color = Color.Gray,
                                            shape = RoundedCornerShape(8.dp)
                                        ) // Borde del grupo
                                        .padding(12.dp)
                                ) {
                                    items.forEach { detail ->
                                        key(detail) { // Usamos `key` para cada producto
                                            SwipeToDeleted(
                                                item = detail,
                                                onDelete = {
                                                    viewModel.deleteItemCart(detail.id)
                                                    items -= detail
                                                }
                                            ) {
                                                ProductItem(cartItem = detail) {
                                                    navController.navigate("cartItem/${detail.id}")
                                                }
                                            }
                                        }
                                    }
                                    ResumeCart(summary = cart.summary)
                                }
                            }
                        }
                    }

                    ButtonBarCart(
                        modifier = Modifier.weight(0.15f),
                        summary = cart.summary,
                        cartItemCount = countCartItem,
                        onNavigate = ({ navController.navigate("pay") }))
                }
            }
        }
    }
}

@Composable
fun ProductItem(cartItem: CartItemDto, onClick: () -> Unit ) {

    Column  {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .background(color = MaterialTheme.colorScheme.background)
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            ProductImageBuilder(cartItem = cartItem)

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = cartItem.product.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "PEN ${cartItem.product.price}",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        HorizontalDivider()
    }

}

@Composable
fun ProductImageBuilder(modifier: Modifier = Modifier, cartItem: CartItemDto) {
    Box(
        modifier = Modifier.size(64.dp)
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                .clip(CircleShape)
        ) {
            Text(
                text = "Img",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        if (cartItem.quantity!! > 0) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)  // Mueve el contador a la esquina superior derecha
                    .offset(
                        x = 8.dp,
                        y = (-8).dp
                    )  // Desplaza un poco para sobresalir fuera de la caja
                    .size(28.dp)  // Tamaño del círculo grande
            ) {
                // Círculo blanco como borde
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White, shape = CircleShape)  // Fondo blanco
                )

                // Círculo rojo para el contador
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(2.dp)  // Espaciado para que no se solapen los bordes
                        .background(Color.Red, shape = CircleShape)   // Borde blanco para el círculo rojo
                ) {
                    Text(
                        text = cartItem.quantity.toString(),
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold  // Aquí se aplica el estilo en negrita
                        ),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }

    }
}

@Composable
fun SecondaryTopBar(modifier: Modifier = Modifier,
                    onDeleteCart: () -> Unit,) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Carrito",
            style = MaterialTheme.typography.labelLarge.copy(fontSize = 22.sp),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )
        IconButton(onClick = { onDeleteCart() }) {
            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
        }
    }
}

@Composable
fun ResumeCart(modifier: Modifier = Modifier, summary: CartSummary) {
    Column(
        modifier = modifier
            .fillMaxWidth(),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Subtotal",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = "PEN ${summary.subTotal}",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.End
            )
        }

        HorizontalDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Impuestos",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = "PEN ${summary.taxAmount}",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.End
            )
        }

    }
}

@Composable
fun ButtonBarCart(modifier: Modifier = Modifier, summary: CartSummary, cartItemCount: Int,   onNavigate: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        HorizontalDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.End)
                .padding(vertical = 8.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold  // Aquí se aplica el estilo en negrita
                    )
                )
                Text(
                    text = if (cartItemCount == 1) "$cartItemCount artículo" else "$cartItemCount artículos",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.SemiBold  // Aquí se aplica el estilo en negrita
                    ),
                    color = Color.Gray
                )
            }
            Text(
                text = "PEN ${summary.total}",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold  // Aquí se aplica el estilo en negrita
                ),
                textAlign = TextAlign.End
            )
        }

        Button(
            onClick = { onNavigate()  },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "Pantalla de pago")
        }
    }
}
