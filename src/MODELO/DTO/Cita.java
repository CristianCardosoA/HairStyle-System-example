package MODELO.DTO;

/**
 *
 * @author Cristianloki
 */
public class Cita {
    
    int id;
    String fecha;
    int hora;
    int idEstilista;
    int idCliente;
    int idServicio;
    String horaDeRegistro;

    public Cita(String fecha, int hora, int idEstilista, int idServicio ,int idCliente, String horaDeRegistro) {
        this.fecha = fecha;
        this.hora = hora;
        this.idEstilista = idEstilista;
        this.idCliente = idCliente;
        this.horaDeRegistro = horaDeRegistro;
        this.idServicio = idServicio;
    }

    public Cita(String fecha, int hora, int idEstilista){
        this.fecha = fecha;
        this.hora = hora;
        this.idEstilista = idEstilista;
        this.idCliente = -1;
        this.horaDeRegistro = null;
        this.idServicio = -1;
    }
    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getIdEstilista() {
        return idEstilista;
    }

    public void setIdEstilista(int idEstilista) {
        this.idEstilista = idEstilista;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getHoraDeRegistro() {
        return horaDeRegistro;
    }

    public void setHoraDeRegistro(String horaDeRegistro) {
        this.horaDeRegistro = horaDeRegistro;
    }
    
    
}
