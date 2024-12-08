package com.example.dbapp.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.dbapp.model.enums.MessageType

class MessageServiceViewModel : ViewModel() {
    private val _messageQueue = MutableStateFlow<List<Pair<String, MessageType>>>(emptyList())
    val messageQueue: StateFlow<List<Pair<String, MessageType>>> = _messageQueue

    fun showMessage(message: String, type: MessageType = MessageType.SUCCESS) {
        _messageQueue.update { it + Pair(message, type) }
    }

    fun dismissMessage() {
        _messageQueue.update { it.drop(1) }
    }
}
