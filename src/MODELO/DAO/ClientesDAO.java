package MODELO.DAO;
import MODELO.DAO.ConexionBD;
import MODELO.DTO.Cita;
import MODELO.DTO.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;

public class ClientesDAO {

	private static PreparedStatement sentenciaPreparada;
	private static Statement sentencia;
	private static ResultSet tuplas;
	private static Connection conexion;
		
	public static String [][] getClientes(){

		String clientes [][] = null;
		int totalTuplas = 0;
		String query;
				
		try {
		        	
			conexion = ConexionBD.getConexion();

	        sentencia = conexion.createStatement();

	        query = "SELECT Count(*) FROM cliente ;";	
	        tuplas = sentencia.executeQuery(query);
		        
	        tuplas.next();
	        totalTuplas = tuplas.getInt(1);

	        clientes = new String[totalTuplas][5];		        			

	        query = "SELECT id,nombre,apellidoP,apellidoM,celular from cliente" + " ORDER BY id";
		    tuplas = sentencia.executeQuery(query);
		            
		    int pos = 0;
		    while (tuplas.next()) {
		       	clientes[pos][0]= tuplas.getString("id");		       
		       	clientes[pos][1]= tuplas.getString("nombre");
                        clientes[pos][2]= tuplas.getString("apellidoP");		       
		       	clientes[pos][3]= tuplas.getString("apellidoM");
		       	clientes[pos][4]= tuplas.getString("celular");
		        pos++;
		    }
								
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al leer estilistas " + e.getMessage(), 
						"Error", JOptionPane.ERROR_MESSAGE);
		} finally {
				
			ConexionBD.cerrarConexion();
		}
		return clientes;		
	}
	
        
        public static boolean insertar(Cliente cliente){
		
		int resultado;	
		boolean ok = true;
		try {
			conexion = ConexionBD.getConexion();
			sentenciaPreparada = conexion.prepareStatement(	
			//codigoSQL:
                                
				"INSERT INTO cliente (nombre,apellidoP,apellidoM,celular) " +
				"VALUES (?, ?, ?, ?)");
                        
			//define valores de values
			sentenciaPreparada.setString(1, cliente.getNombre());
			sentenciaPreparada.setString(2, cliente.getApellidoP());
                        sentenciaPreparada.setString(3, cliente.getApellidoM());
                        sentenciaPreparada.setString(4, cliente.getCelular());
			resultado = sentenciaPreparada.executeUpdate();
			
			if (resultado == 0) {
				conexion.rollback();
				ok = false;
			} else {

				conexion.commit();
				sentenciaPreparada.close();
				ConexionBD.cerrarConexion();
			}
		} catch (SQLException e){
			ok = false;
		} finally {
			ConexionBD.cerrarConexion();
		}
	
		return ok;		
	}
        
        public static int getId(Cliente cliente){

		int id = 0;
		String query;
				
		try {
                conexion = ConexionBD.getConexion();
	        sentencia = conexion.createStatement();
                query = "select id from cliente where nombre=\"" + cliente.getNombre() + "\" and apellidoP=\"" + cliente.getApellidoP() + "\" and apellidoM=\"" + cliente.getApellidoM() + "\";";
	        tuplas = sentencia.executeQuery(query); 
	        tuplas.next();
	        id = tuplas.getInt(1);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al obtener id " + e.getMessage(), 
						"Error", JOptionPane.ERROR_MESSAGE);
		} finally {
				
			ConexionBD.cerrarConexion();
		}
                
		return id;		
	}
	
       
}

