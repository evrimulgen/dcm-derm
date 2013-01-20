/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package in.raster.mayam.delegate;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.app.Connection;
import ca.uhn.hl7v2.app.ConnectionHub;
import ca.uhn.hl7v2.app.Initiator;
import ca.uhn.hl7v2.app.SimpleServer;
import ca.uhn.hl7v2.llp.LLPException;
import ca.uhn.hl7v2.llp.LowerLayerProtocol;
import ca.uhn.hl7v2.llp.MinLowerLayerProtocol;
import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v22.message.QRY_A19;
import ca.uhn.hl7v2.model.v22.segment.MSH;
import ca.uhn.hl7v2.model.v22.segment.QRD;
import ca.uhn.hl7v2.parser.GenericParser;
import ca.uhn.hl7v2.parser.Parser;
import ca.uhn.hl7v2.parser.PipeParser;
import in.raster.mayam.param.QueryParam;
import java.io.IOException;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.log4j.BasicConfigurator;

/**
 *
 * @author mariano
 */
public class HL7QueryService {

    private String serverAddr;
    private int port;
    private String serverName;
    private String subprotocol;
    private Vector dataset;
    
    public HL7QueryService(){
    }
    
    public void find(final QueryParam queryParam) throws DataTypeException,
            IOException, LLPException, HL7Exception {

        //Server
        int sport = 6660; // The port to listen on
        PipeParser sparser = new PipeParser(); // The message parser
        SimpleServer server = new SimpleServer(sport, LowerLayerProtocol.makeLLP(), sparser);
        HL7ServerDelegate delegate = new HL7ServerDelegate();
        server.registerApplication("ADR", "A19", delegate);
        server.start();
        //Client
        ConnectionHub connectionHub = ConnectionHub.getInstance();
        Connection connection = connectionHub
            .attach(serverAddr, port, new PipeParser(), MinLowerLayerProtocol.class);
        Initiator initiator = connection.getInitiator();
        initiator.setTimeoutMillis(2500);
        try {
            initiator.sendAndReceive(createMessage(queryParam));
        } catch (HL7Exception ex) {
            Logger.getLogger(HL7QueryService.class.getName()).log(Level.SEVERE, ex.getMessage());
        }
        connectionHub.discard(connection);
        ConnectionHub.shutdown();
        server.stopAndWait();
        dataset = delegate.getResults();
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
    
    public void setSubprotocol(String subprotocol) {
        this.subprotocol = subprotocol;
    }
    
    private Message createMessage(QueryParam queryParam) throws DataTypeException, HL7Exception, IOException {
        QRY_A19 qry = new QRY_A19();
       
        // Populate the MSH Segment
        MSH mshSegment = qry.getMSH();
        mshSegment.getFieldSeparator().setValue("|");
        mshSegment.getEncodingCharacters().setValue("^~\\&");
        mshSegment.getMsh10_MessageControlID().setValue("1234"/*MessageIDGenerator.getInstance().getNewID()*/);
        mshSegment.getDateTimeOfMessage().getTimeOfAnEvent().setValue(new Date());
        mshSegment.getSendingApplication().setValue("Mayam Derm CAD");
        mshSegment.getReceivingApplication().setValue(serverName);
        //mshSegment.getSequenceNumber().setValue("123");
        mshSegment.getMessageType().getMessageType().setValue("QRY");
        mshSegment.getMessageType().getTriggerEvent().setValue("A19");
        mshSegment.getProcessingID().setValue("P");
        mshSegment.getReceivingApplication().setValue(serverName);
        mshSegment.getVersionID().setValue("2.2");
        
        QRD qrdSegment = qry.getQRD();
        qrdSegment.getQueryDateTime().getTimeOfAnEvent().setValue(new Date());
        qrdSegment.getQrd2_QueryFormatCode().setValue("R"); //Structured Data
        qrdSegment.getQrd3_QueryPriority().setValue("I"); // The query has to be dealt with immediately (I) by the receiving application. 
        qrdSegment.getQrd4_QueryID().setValue("Q1001");
        qrdSegment.getQrd7_QuantityLimitedRequest().getQuantity().setValue("50");
        qrdSegment.insertQrd8_WhoSubjectFilter(0).setValue(queryParam.getPatientId());
        qrdSegment.insertQrd8_WhoSubjectFilter(1).setValue(queryParam.getPatientName());
        qrdSegment.insertQrd9_WhatSubjectFilter(0).setValue("DEM");

        // Now, let's encode the message and look at the output
        Parser parser = new PipeParser();
        String encodedMessage = parser.encode(qry);
        System.out.println("Printing ER7 Encoded Message:");
        System.out.println(encodedMessage);        
        
        return qry;
    }
    
    public Vector getDatasetVector() {
        return dataset;
    }

}
