package com.green.webstoreadmin.orders;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.green.webstoremodels.entities.Category;
import com.green.webstoremodels.entities.Order;
import com.green.webstoremodels.entities.OrderDetail;
import com.green.webstoremodels.entities.Product;
import com.green.webstoremodels.formdata.ProductData;

@Controller
public class OrderDetailController {
	@Autowired
	private OrderDetailService orderDetailService;
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/orders-list")
	public String showOrders(Model model) {
	
		List<Order> listOrders = orderService.getAllOrders();
		
		model.addAttribute("listOrders", listOrders);
		
		return "order_list";
	}
	
	@RequestMapping("/detail-order/id={id}")
	public String detailOrder(@PathVariable(name = "id") int id, Model model) {
		Order order = orderService.getOrderById(id);
		List<OrderDetail> listOrderDetails = new ArrayList<OrderDetail>();
		
		listOrderDetails = orderDetailService.getOrderDetailByOrderId(order);

		model.addAttribute("listOrderDetails", listOrderDetails);
		
		return "detail_order";
		
	}

}
