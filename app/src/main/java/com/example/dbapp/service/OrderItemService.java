package com.example.dbapp.service;

import java.util.List;
import com.example.dbapp.model.entity.OrderItem;
import com.example.dbapp.repository.dao.OrderItemDao;

public class OrderItemService {

    private final OrderItemDao orderItemDao;

    public OrderItemService(OrderItemDao orderItemDao) {
        this.orderItemDao = orderItemDao;
    }

    public List<OrderItem> listAllItems() {
        return orderItemDao.getAll();
    }

    public OrderItem getItemById(long id) {
        return orderItemDao.getById(id);
    }

    public List<OrderItem> getItemsByOrderId(long orderId) {
        return orderItemDao.getByOrderId(orderId);
    }

    public OrderItem createItem(OrderItem item) {
        long id = orderItemDao.insert(item);
        return orderItemDao.getById(id);
    }

    public List<Long> createItems(List<OrderItem> items) {
        return orderItemDao.insertAll(items);
    }

    public void updateItem(OrderItem item) {
        orderItemDao.update(item);
    }

    public void deleteItem(OrderItem item) {
        orderItemDao.delete(item);
    }

    public void deleteItemById(long id) {
        orderItemDao.deleteById(id);
    }

    public void deleteItemsByOrderId(long orderId) {
        orderItemDao.deleteByOrderId(orderId);
    }
}
