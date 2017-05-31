package MODELO.DAO;
import MODELO.DAO.ConexionBD;
import MODELO.DTO.Cita;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.JOptionPane;

public class HorasDAO {

	private static PreparedStatement sentenciaPreparada;
	private static Statement sentencia;
	private static ResultSet tuplas;
	private static Connection conexion;
		
	public static String [] getHorasDisponibles(String fecha, int idEstilista){

		String horasDisponibles [] = {"8:00 a.m.","8:30 a.m.","9:00 a.m.","9:30 a.m.","10:00 a.m.",
                    "10:30 a.m.","11:00 a.m.", "11:30 a.m.","12:00 p.m.","12:30 p.m.","1:00 p.m."
                ,"1:30 p.m.","2:00 p.m.","2:30 p.m.","3:00 p.m.","3:30 p.m.","4:00 p.m.","4:30 p.m."
                ,"5:00 p.m.","5:30 p.m.","6:00 p.m.","6:30 p.m.","7:00 p.m.","7:30 p.m.","8:00 p.m."
                };
                
		String query;
				
		try {        	
                conexion = ConexionBD.getConexion();
	        sentencia = conexion.createStatement();             
	        query = "SELECT hora from citas where fecha=\"" + fecha +"\" and idEstilista=\"" + idEstilista + "\";";
		tuplas = sentencia.executeQuery(query);         

                while (tuplas.next()) {
		       	horasDisponibles[Integer.parseInt(tuplas.getString("hora"))] = "No disponible";	       
                    }
                 					
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al leer las horas disponibles " + e.getMessage(), 
						"Error", JOptionPane.ERROR_MESSAGE);
		} finally {
				
			ConexionBD.cerrarConexion();
		}
		return horasDisponibles;		
	}
	
    /**
     *
     * @param fecha
     * @param idEstilista
     * @param idServicio
     * @return
     */
    public static String [] getHorasDisponibles(String fecha, int idEstilista, int idServicio){

		String horasDisponibles [] = {"8:00 a.m.","8:30 a.m.","9:00 a.m.","9:30 a.m.","10:00 a.m.",
                    "10:30 a.m.","11:00 a.m.", "11:30 a.m.","12:00 p.m.","12:30 p.m.","1:00 p.m."
                ,"1:30 p.m.","2:00 p.m.","2:30 p.m.","3:00 p.m.","3:30 p.m.","4:00 p.m.","4:30 p.m."
                ,"5:00 p.m.","5:30 p.m.","6:00 p.m.","6:30 p.m.","7:00 p.m.","7:30 p.m.","8:00 p.m."
                };
                
		String query;
		Integer duracionServicio=1;		
		try {        	
                conexion = ConexionBD.getConexion();
	        sentencia = conexion.createStatement(); 
                
                query = "select duracion from servicios where id="+idServicio+";";
                tuplas = sentencia.executeQuery(query);
                tuplas.next();
                duracionServicio=tuplas.getInt("duracion");
                Integer[] duracionServicioActual=CitaDAO.separarNumeros(duracionServicio.toString().length(), duracionServicio.toString());
                //System.out.println(duracionServicioActual[0]);                
	        query = "SELECT hora from citas where fecha=\"" + fecha +"\" and idEstilista=\"" + idEstilista + "\";";
		tuplas = sentencia.executeQuery(query);         

                while (tuplas.next()) {
		       	horasDisponibles[Integer.parseInt(tuplas.getString("hora"))] = "No disponible";	       
                    }
               ///////// Agrega los no disponnibles por el tiempo de cada servicio agendado

                String [] servicios;
                Integer [] duracion;
                for(int i =0; i <25; i++){
                    if(horasDisponibles[i].equals("No disponible")){//Aplic apara los No disponible obtenidos de la base
                        servicios=CitaDAO.getServicioCita(i, idEstilista-1, fecha);
                        //System.out.println(Arrays.toString(servicios));
                        //System.out.println(i);
                        if(!Arrays.toString(servicios).equals("[null, null]")){//Si encuentra un No disponible agregado sin cita lo ignora
                            //System.out.println(Arrays.toString(servicios));
                            duracion =  CitaDAO.separarNumeros(servicios [1].length(), servicios[1]);
                            for(int j=0; j<servicios[1].length();j++){ //Agrega no disponibles según la duración dle servicio
                                //System.out.println(duracion[j]);
                                horasDisponibles[i+duracion[j]-1]="No disponible"; //Separa la clave de duración y aplica los no disponible
                                //System.out.println(i+duracion[j]-1);
                            }
                        }
                    }
                }
                //Inhabilita las horas que no se acomoden al servicio que se va a agregar
                for(int i=0; i<25; i++){
                    if(!horasDisponibles[i].equals("No disponible")){
                        for(int j=0; j<duracionServicio.toString().length();j++){
                            //System.out.println(i+duracionServicioActual[j]-1);
                            //System.out.println(horasDisponibles[i+duracionServicioActual[j]-1]);
                            //Recorre las horas buscado No disponibles en las horas del servicio actual
                            if(horasDisponibles[i+duracionServicioActual[j]-1].equals("No disponible")){
                                horasDisponibles[i]="No disponible";
                                //System.out.println(i+duracionServicioActual[j]-1);
                                
                            }
                            //Evita que la busqueda desborde el array
                            if(j+1<duracionServicio.toString().length() && i+duracionServicioActual[j+1]-1>=25){ 
                                horasDisponibles[i]="No disponible";
                                break;
                            }
                        }
                    }    
                }     
                    //System.out.println(horasDisponibles[i]);
                    
                
                
		} catch (NumberFormatException | SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al leer las horas disponibles " + e.getMessage(), 
						"Error", JOptionPane.ERROR_MESSAGE);
		} finally {
				
			ConexionBD.cerrarConexion();
		}
		return horasDisponibles;		
	}
	
}

