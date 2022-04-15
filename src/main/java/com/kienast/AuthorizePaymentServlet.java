package com.kienast;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.paypal.base.rest.PayPalRESTException;

@WebServlet("/authorize_payment")
public class AuthorizePaymentServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    public AuthorizePaymentServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String product = request.getParameter("product");
		System.out.println(product);
		String subtotal = request.getParameter("subtotal");
		System.out.println(subtotal);
		String shipping = request.getParameter("shipping");
		System.out.println(shipping);
		String tax = request.getParameter("tax");
		System.out.println(tax);
		String total = request.getParameter("total");
		System.out.println(total);

		OrderDetail orderDetail = new OrderDetail(product, subtotal, shipping, tax, total);
		System.out.println(orderDetail);

		try {
			PaymentService paymentService = new PaymentService();
			String approvalLink = paymentService.authorizePayment(orderDetail);
			response.sendRedirect(approvalLink);
		} catch(PayPalRESTException ex) {
			ex.printStackTrace();
			request.setAttribute("errorMessage", "Invalid Payment Details");
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	
	}

}
