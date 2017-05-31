package MODELO.DAO;
import MODELO.DAO.ConexionBD;
import MODELO.DTO.Cita;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;

public class CitaDAO {

	private static PreparedStatement sentenciaPreparada;
	private static Statement sentencia;
	private static ResultSet tuplas;
	private static Connection conexion;
        private static ArrayList<Cita> arregloCitas = new ArrayList<>();
        
	public static boolean insertar(Cita cita){
		int resultado;	
		boolean ok = true;
		try {
                    
			conexion = ConexionBD.getConexion();
			sentenciaPreparada = conexion.prepareStatement(	
			//codigoSQL:
                        "INSERT INTO citas (fecha,hora,idEstilista,idCliente,horaRegistro,idServicio) VALUES (?,?,?,?,?,?)");
			//define valores de values
			sentenciaPreparada.setString(1,cita.getFecha());
			sentenciaPreparada.setInt(2,cita.getHora());
                        sentenciaPreparada.setInt(3,cita.getIdEstilista());
                        sentenciaPreparada.setInt(4,cita.getIdCliente());
                        sentenciaPreparada.setString(5,cita.getHoraDeRegistro());
                        sentenciaPreparada.setInt(6,cita.getIdServicio());
                        resultado = sentenciaPreparada.executeUpdate();
			if (resultado == 0) {
				conexion.rollback();
				ok = false;
                                //System.out.println("Se hizo rollback");
			}else{
                                conexion.commit();
				sentenciaPreparada.close();
				ConexionBD.cerrarConexion();
			}
		} catch (SQLException e){
			ok = false;
                        //System.out.println(e);
		} finally {
			ConexionBD.cerrarConexion();
		}
	
		return ok;		
	}
	
	public static boolean modificar(Cita cita){
		
		int resultado;	
		boolean ok = true;
	
		try {

			conexion = ConexionBD.getConexion();
			sentenciaPreparada = conexion.prepareStatement(	
			//codigoSQL:
				"UPDATE Alumno " +
				"SET nombreAlumno = ? " +
				"WHERE idAlumno = ?");

			//define valores de values				
//			sentenciaPreparada.setString(1, cita.getHora());
//			sentenciaPreparada.setInt(2, cita.getId());

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
		
	public static boolean borrar(Cita cita) {
			
		int resultado;	
		boolean ok = true;
                System.out.println(cita.getIdEstilista());
                System.out.println(cita.getFecha());
                System.out.println(cita.getHora());

		try {

			conexion = ConexionBD.getConexion();
			sentenciaPreparada = conexion.prepareStatement(	
			//codigoSQL:
				"DELETE FROM citas WHERE idEstilista = " +cita.getIdEstilista() +" and"+
                                        " fecha = \""+cita.getFecha()+"\" and hora ="+cita.getHora()+";");

			//define valores de values
                        System.out.println(sentenciaPreparada);
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
                        JOptionPane.showMessageDialog(null,"Error al borrar cita " + e.getMessage(), 
						"Error", JOptionPane.ERROR_MESSAGE);
			ok = false;
		} finally {
			ConexionBD.cerrarConexion();
		}

		return ok;		
	}
        
        public static ArrayList<Cita> getArrayCitas(){
            return arregloCitas;
        }
		
	public static Object[][] getCitas(String fecha){

		Object citas[][] = null;
                Object copiaCitas[][] = null;
		int totalTuplas = 0;
		String query;
				
		try {
		        	
			conexion = ConexionBD.getConexion();

	        sentencia = conexion.createStatement();

	        query = "SELECT Count(*) FROM citas;";
	        tuplas = sentencia.executeQuery(query);
	        tuplas.next();
                
                //citas = new Object[2][25];
                copiaCitas = new Object[2][25];
                Integer [][] horas = new Integer [2][25];
                Integer [][] duracion = new Integer [2][25];
                int persona;
                
	        query = " select citas.id,hora,idEstilista,duracion from citas inner join servicios on citas.idServicio=servicios.id where fecha= \"" + fecha +  "\" order by hora;";
		    tuplas = sentencia.executeQuery(query);
		    int pos = 0;
		    while (tuplas.next()){
                        persona = tuplas.getInt("idEstilista") - 1;
                        //citas[persona][pos] = tuplas.getInt("hora");
                        horas[persona][pos] = tuplas.getInt("hora");
                        copiaCitas[persona][pos] = tuplas.getInt("hora");
                        duracion[persona][horas[persona][pos]]=tuplas.getInt("duracion");
                        /*System.out.println(copiaCitas[persona][pos]);
                        System.out.println(horas[persona][pos]);
                        System.out.println(duracion[persona][pos]);
                        System.out.println(persona);
                        System.out.println(pos);*/
                        pos++;
                    }
                    //SOLO SE RELLENA DATOS
                    for(int i = pos; i < 25; i++){
                        copiaCitas[0][i] = "Disponible";
                        copiaCitas[1][i] = "Disponible";
                   }
                    
                    //ACOMODAR EN ORDEN LAS CITAS POR HORA
                    for(int i = 0; i < 25; i++){
                        
                            if(horas[0][i] != null){
                                Integer temp = horas[0][i];
                                //System.out.println(horas[0][i]);
                                //System.out.println(i);
                                copiaCitas[0][temp] = temp;  
                                temp =  (Integer) copiaCitas[0][i];
                                if(temp != i){
                                    copiaCitas[0][i] = "Disponible";
                                }
                            }
                            if(horas[1][i] != null){
                                Integer temp2 = horas[1][i];
                                copiaCitas[1][temp2] = temp2;  
                                temp2 =  (Integer) copiaCitas[1][i];
                                if(temp2 != i){
                                    copiaCitas[1][i] = "Disponible";
                                }
                                    
                            }
                            
                    }
                        String duration;
                        Integer [] numeros;
                        for(int posicion = 0; posicion < 25; posicion ++){
                            
                            if(copiaCitas[0][posicion] == null){
                                copiaCitas[0][posicion] = "Disponible";
                            }else if(copiaCitas[0][posicion] != "Disponible" && copiaCitas[0][posicion] != null && copiaCitas[0][posicion]!="Ocupado" ){
                            ////// Duración //////
                                duration = duracion[0][posicion].toString();
                                numeros=separarNumeros(duration.length(),duration);
                                
                                for(int p=0; p<duration.length();p++){
                                    copiaCitas[0][posicion+numeros[p]-1] = "Ocupado";
                                    System.out.println(posicion+numeros[p]-1);
                                }
                            //////////////////////
                            }
                              if(copiaCitas[1][posicion] == null){
                                copiaCitas[1][posicion] = "Disponible";
                            }else if(copiaCitas[1][posicion] != "Disponible" && copiaCitas[1][posicion] != null && copiaCitas[1][posicion]!="Ocupado" ){
                                copiaCitas[1][posicion] = "Ocupado";
                            }
                        }        
                    
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al leer citas" + e.getMessage(), 
						"Error", JOptionPane.ERROR_MESSAGE);
		} finally {
				
			ConexionBD.cerrarConexion();
		}
		return copiaCitas;		
	}
        
        public static String[] getClienteCita(int hora, int idEstilista, String fecha){

		String estilistas [] = null;
		String query;
				//mysql> select cliente.nombre from citas INNER JOIN cliente on 
                                  //      citas.idCliente=cliente.id where hora=0 and fecha="15-07-2015";
		try {
		        	
			conexion = ConexionBD.getConexion();

	        sentencia = conexion.createStatement();

	        estilistas = new String[2];		        			

	        query = "select cliente.nombre, apellidoP from citas INNER JOIN cliente on citas.idCliente=cliente.id where hora=\"" + hora + "\"" + 
                        " and fecha=\"" + fecha + "\""  + 
                        " and idEstilista=\"" + (idEstilista+1) + "\"";
		    tuplas = sentencia.executeQuery(query);
		            
		    int pos = 0;
                    tuplas.next();
		    estilistas[pos]= tuplas.getString("nombre");
                    pos+=1;
                    estilistas[pos]=tuplas.getString("apellidoP");
                    
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al leer estilistas " + e.getMessage(), 
						"Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			ConexionBD.cerrarConexion();
		}
		return estilistas;		
	
        }
        
        public static String[] getServicioCita(int hora, int idEstilista, String fecha){

		String estilistas [] = new String [2];
		String query;
				//mysql> select cliente.nombre from citas INNER JOIN cliente on 
                                  //      citas.idCliente=cliente.id where hora=0 and fecha="15-07-2015";
		try {
		        	
			conexion = ConexionBD.getConexion();

	        sentencia = conexion.createStatement();

			        			
                Integer dur;
	        query = "select servicios.nombre, duracion from citas INNER JOIN servicios on citas.idServicio=servicios.id where hora=" + hora +  
                        " and fecha=\"" + fecha + "\""  + 
                        " and idEstilista=" + (idEstilista+1) + ";";
		    tuplas = sentencia.executeQuery(query);
		    if(tuplas.next()){
                        int pos = 0;
                        estilistas[0]= tuplas.getString("nombre");
                        dur = tuplas.getInt("duracion");
                        estilistas[1]= dur.toString();
                    }       
		    //System.out.println("Resulset getServicioCita"+estilistas[1]);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error servicio por cita " + e.getMessage(), 
						"Error", JOptionPane.ERROR_MESSAGE);
		} finally {
				
			ConexionBD.cerrarConexion();
		}
		return estilistas;		
	
        }
        
        public static Integer [] separarNumeros(int n, String num){
            Integer [] numeros = new Integer [n];
            char c;
            for(int i=0; i<n; i++){
                c=num.charAt(i);
                numeros[i]=Integer.parseInt("" + c); 
            }
            return numeros;
        }
	
	
}

