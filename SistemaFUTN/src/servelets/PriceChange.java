package servelets;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import futn.CopyPrice;
import negocio.CtrlFutn;
import utils.ApplicationException;

/**
 * Servlet implementation class PriceChange
 */
@WebServlet("/PriceChange")
public class PriceChange extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PriceChange() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CtrlFutn ctrl=new CtrlFutn();
	    CopyPrice newCopyPrice=new CopyPrice();

	    //setea precios ingresados
	    newCopyPrice.setDuplexPrice(Double.parseDouble(request.getParameter("duplex")));
	    newCopyPrice.setSimplePrice(Double.parseDouble(request.getParameter("simple")));
	    
	    //registra nuevo precio copias
	    try {
			ctrl.changeCopyPrice(newCopyPrice);
			request.getSession().setAttribute("message","Price change successfully");
			request.getSession().setAttribute("currentCopyPrice",newCopyPrice);


		} catch (ApplicationException e) {
			request.getSession().setAttribute("exceptionMessage",e.getMessage());	
		}finally {
		    request.getRequestDispatcher("Home.jsp").forward(request, response);
		}    
	}
}
