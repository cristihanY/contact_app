package com.example.dbapp.service;

import java.util.List;
import com.example.dbapp.model.entity.Order;
import com.example.dbapp.repository.dao.OrderDao;


public class OrderService {

    private final OrderDao orderDao;

    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public List<Order> listAllOrders() {
        return orderDao.getAll();
    }

    public Order getOrderById(long id) {
        return orderDao.getById(id);
    }

    public Order createOrder(Order order) {
        long id = orderDao.insert(order);
        return orderDao.getById(id);
    }

    public void updateOrder(Order order) {
        orderDao.update(order);
    }

    public void deleteOrder(Order order) {
        orderDao.delete(order);
    }

    public void deleteOrderById(long id) {
        orderDao.deleteById(id);
    }

    public List<Order> getOrdersByCustomerId(long customerId) {
        return orderDao.getByCustomerId(customerId);
    }
}
