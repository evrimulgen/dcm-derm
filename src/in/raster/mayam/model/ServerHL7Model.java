/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package in.raster.mayam.model;

/**
 * 
 * @author mariano
 */
public class ServerHL7Model {

    private String serverName = null;
    private String serverAddr = null;
    private int port = -1;
    private String subProtocol = null;
    
    public ServerHL7Model() {
    }
    
    public String getServerName() {
        return serverName;
    }

    public String getServerAddr() {
        return serverAddr;
    }

    public int getPort() {
        return port;
    }
    
    public String getSubProtocol() {
        return subProtocol;
    }
    
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
        
    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public void setPort(int port) {
        this.port = port;
    }
    
    public void setSubProtocol(String subProtocol) {
        this.subProtocol = subProtocol;
    }
   
}
