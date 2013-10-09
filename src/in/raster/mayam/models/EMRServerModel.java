package in.raster.mayam.models;

/**
 *
 * @author mariano
 */
public class EMRServerModel {
    
    private int pk;
    private String description = "";
    private String subprotocol = "";
    private String hostName = "";
    private int port;

    public EMRServerModel(Integer pk, String description,String hostname, Integer port, String subprotocol) {
        this.pk = pk;
        this.hostName = hostname;
        this.description = description;
        this.port = port;
        this.subprotocol = subprotocol;
    } 
    
    public EMRServerModel(String description, String hostname, Integer port, String subprotocol) {
        this.hostName = hostname;
        this.description = description;
        this.port = port;
        this.subprotocol = subprotocol;
    } 
    
    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubprotocol() {
        return subprotocol;
    }

    public void setSubprotocol(String subprotocol) {
        this.subprotocol = subprotocol;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
    
}
