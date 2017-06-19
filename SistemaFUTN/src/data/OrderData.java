package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

import entidades.Order;
import entidades.OrderDetail;
import entidades.Subject;
import entidades.TeachingMaterial;
import entidades.User;
import negocio.CtrlTeachingMaterial;
import utils.ApplicationException;

public class OrderData {
	

	public void add(Order o){
		ResultSet rs=null;
		PreparedStatement stmt=null;
		
		
		try {
			stmt=FactoryConexion.getInstancia().getConn().prepareStatement(
					"insert into orders (totalAmount,orderState,orderDate,estimatedDeliveryDate,studentLegajo)"+
					" values(?,?,CURRENT_DATE,DATE_ADD(CURRENT_DATE, INTERVAL ? DAY),?)",Statement.RETURN_GENERATED_KEYS);
									
			stmt.setDouble(1, o.getTotalAmount());
			stmt.setBoolean(2, o.isOrderState());
			stmt.setInt(3, 3); //  fijo o calculable?
			stmt.setString(4, o.getStudentOrder().getLegajo());

			stmt.execute();
			
			//donde ubicarlo? considerar excepcion
			ResultSet gk=stmt.getGeneratedKeys();
			
			if(gk.next()){
			o.setOrderNumber(gk.getInt(1));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ApplicationException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(stmt!=null)stmt.close();
				FactoryConexion.getInstancia().releaseConn();
				addOrderDetails(o); //esta bien ubicado?  hacer loop aca?
			} catch (ApplicationException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}
	}

	private void addOrderDetails(Order o) {
		ResultSet rs=null;
		PreparedStatement stmt=null;
		ArrayList<OrderDetail> od=o.getDetails();
		
		try {

			for(int i=0;i<o.getDetails().size();i++){
				stmt=FactoryConexion.getInstancia().getConn().prepareStatement(
						"insert into orderDetails (orderNumber,orderDetailNumber,teachingMaterialCode,numberOfCopies,state,parcialAmount,duplex)"+
						" values(?,?,?,?,?,?,?)");
							
				stmt.setInt(1,o.getOrderNumber());
				stmt.setInt(2, od.get(i).getOrderDetailNumber());
				stmt.setInt(3, od.get(i).getItem().getCode());
				stmt.setInt(4, od.get(i).getNumberOfCopies());
				stmt.setBoolean(5, od.get(i).isState());
				stmt.setDouble(6,od.get(i).getParcialAmount());
				stmt.setBoolean(7, od.get(i).isDuplex());
				stmt.execute();	
			};
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ApplicationException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(stmt!=null)stmt.close();
				FactoryConexion.getInstancia().releaseConn();
			} catch (ApplicationException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}
		
	}

	public ArrayList<Order> getOrders(User user) {

		ArrayList<Order> orders = new ArrayList<Order>();
		
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			stmt = FactoryConexion.getInstancia().getConn().prepareStatement(
					"select o.orderNumber,o.orderDate, o.deliveryDate,o.finishDate,"+
			        "o.estimatedDeliveryDate,o.totalAmount,o.studentLegajo,o.orderState"+
			        " from orders o"+
			        " where o.studentLegajo=?");
			stmt.setString(1,user.getLegajo());
			rs= stmt.executeQuery();
			
			
			while(rs!=null && rs.next()){
				
				Order o=new Order();
				
				o.setOrderNumber(rs.getInt("orderNumber"));
				o.setOrderDate(rs.getDate("orderDate"));
				o.setDeliveryDate(rs.getDate("deliveryDate"));
				o.setFinishDate(rs.getDate("finishDate"));
				o.setEstimatedDeliveryDate(rs.getDate("estimatedDeliveryDate"));
				o.setTotalAmount(rs.getDouble("totalAmount"));
				o.setOrderState(rs.getBoolean("orderState"));
								
				orders.add(o);
			}
			 
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(rs!=null)rs.close();
				if(stmt!=null)stmt.close();
				FactoryConexion.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
		}
		
		//prueba
		for (int i = 0; i < orders.size(); i++) {
			orders.get(i).setDetails(getDetails(orders.get(i)));
		}
		
		return orders;
	}

	private ArrayList<OrderDetail> getDetails(Order o) {
		
		TeachingMaterialData tmData=new TeachingMaterialData(); 
		
		ArrayList<OrderDetail> ordersDetails = new ArrayList<OrderDetail>();
		
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			stmt = FactoryConexion.getInstancia().getConn().prepareStatement(
					"select od.orderDetailNumber,od.teachingMaterialCode,od.numberOfCopies,od.state,od.parcialAmount,od.duplex"+			
                    " from orderDetails od"+
					" where od.orderNumber= ?");
			stmt.setInt(1,o.getOrderNumber());
			rs= stmt.executeQuery();
			
			while(rs!=null && rs.next()){
				
				OrderDetail od=new OrderDetail();
				TeachingMaterial tmSearch=new TeachingMaterial();
				
				tmSearch.setCode(rs.getInt("teachingMaterialCode"));
				
				od.setItem(tmSearch);
				
				od.setOrderDetailNumber(rs.getInt("orderDetailNumber"));
				od.setParcialAmount(rs.getDouble("parcialAmount"));
				od.setState(rs.getBoolean("state"));
				od.setDuplex(rs.getBoolean("duplex"));
				od.setNumberOfCopies(rs.getInt("numberOfCopies"));
				
				ordersDetails.add(od);
			}
			 
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(rs!=null)rs.close();
				if(stmt!=null)stmt.close();
				FactoryConexion.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
		}
		
		for (int i = 0; i < ordersDetails.size(); i++) {
			
			TeachingMaterial tm = new TeachingMaterial();
			
			tm=ordersDetails.get(i).getItem();
			
			ordersDetails.get(i).setItem(tmData.getTeachingMaterial(tm));
			
		}
		
		return ordersDetails;
	}

}
