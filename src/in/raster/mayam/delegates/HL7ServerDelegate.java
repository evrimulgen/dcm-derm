/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package in.raster.mayam.delegates;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.app.Application;
import ca.uhn.hl7v2.app.ApplicationException;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.PipeParser;
//import in.raster.mayam.model.ServerHL7Model;
//import in.raster.mayam.model.table.StudyListModel;
import java.util.Vector;
import ca.uhn.hl7v2.model.v22.message.ADR_A19;

/**
 *
 * @author mariano
 */
public class HL7ServerDelegate implements Application {
 
//    private String name;
//    private  ServerHL7Model emr;
//    private StudyListModel studyListModel; //TODO cambiar por el list model adecuado
    private Vector queryResults = null;
    
    public HL7ServerDelegate() {
    }
    
    public Vector getResults() {
        return queryResults;
    }
    
    /*public HL7ServerDelegate(String name) {
        this.name = name;
    }

    public ServerEMRModel getEMRModel() {
        return emr;
    }

    public void setAe(ServerEMRModel emr) {
        this.emr = emr;
    }

    public StudyListModel getStudyListModel() {
        return studyListModel;
    }

    public void setStudyListModel(StudyListModel studyListModel) {
        this.studyListModel = studyListModel;
    }

    public String getName() {
        return name;
    }*/

    @Override
    public Message processMessage(Message in) throws ApplicationException, HL7Exception {
        PipeParser parser = new PipeParser();
        String encodedMessage = parser.encode(in);
        System.out.println("** Received message: ***" + encodedMessage);
 
        ///Armar vector con cada registro necesario para el resultado
        ADR_A19 response = (ADR_A19) in;
        
        //DUMMY;
        queryResults = new Vector();
        
        queryResults.add(new String[]{
            response.getQUERY_RESPONSE().getPID().getPid5_PatientName().getPn2_GivenName().getValue(),
            response.getQUERY_RESPONSE().getPID().getPid5_PatientName().getPn1_FamilyName().getValue(),
            response.getQUERY_RESPONSE().getPID().getPid7_DateOfBirth().getTimeOfAnEvent().getValue(),
            response.getQUERY_RESPONSE().getPID().getPid2_PatientIDExternalID().getIDNumber().getValue(),
            response.getQUERY_RESPONSE().getPID().getPid8_Sex().getValue()
        });
        // Now we need to generate a message to return. This will generally be an ACK message.
//        MSH msh = (MSH)in.get("MSH");
//        ACK retVal;
//        try {
//            // This method takes in the MSH segment of an incoming message, and generates an
//            // appropriate ACK
//            retVal = (ACK)DefaultApplication.makeACK(msh);
//         } catch (IOException e) {
//            throw new HL7Exception(e);
//         }
//         System.out.println("AAAAAAAA "+retVal.toString());
         return null;
    }

    @Override
    public boolean canProcess(Message in) {
        return true;
    }

}
