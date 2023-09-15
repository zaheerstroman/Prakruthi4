package com.prakruthi.ui.Utility;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.net.ssl.HttpsURLConnection;

//import com.sun.net.ssl.HttpsURLConnection;


public class Tester {

    private static String wsURL="https://180.179.175.17/apis/servlet/DoWebTrans";
    //Production API URL:-https://login.ccavenue.com/apis/servlet/DoWebTrans
    //Staging API URL:-https://180.179.175.17/apis/servlet/DoWebTrans



    public static void main(String[] args) {
        String pXmlData="<?xml version='1.0' encoding='UTF-8' standalone='yes'?><Order_Status_Query order_no='' reference_no='205000184724'/>";
        String pAccessCode="TQGFW6GWE9EJLV1B";
        String aesKey="B9C9FB9A596424141A40A029B646BA7F";
        String pCommand="orderStatusTracker";
        String pRequestType="xml";
        String pResponseType="xml";
        String pVersion="1.1";

        new Tester().callCCavenueApi(pXmlData, pAccessCode, pCommand, aesKey, pRequestType, pResponseType, pVersion);


    }

    /**
     * Method to call ccavenue api. Output is printed on console within this method.
     * */
    public void callCCavenueApi(String pXmlData,String pAccessCode,String pCommand,String aesKey,String pRequestType,String pResponseType, String pVersion){
        String vResponse="",encXMLData="",encResXML="",decXML="";
        StringBuffer wsDataBuff=new StringBuffer();
        try {
            if(aesKey!=null && !aesKey.equals("") && pXmlData!=null && !pXmlData.equals("")){
                AesUtil aesUtil=new AesUtil(aesKey);
                encXMLData  = aesUtil.encrypt(pXmlData);
            }
            wsDataBuff.append("enc_request="+encXMLData+"&access_code="+pAccessCode+"&command="+pCommand+"&response_type="+pResponseType+"&request_type="+pRequestType+"&version="+pVersion);
            vResponse = processUrlConnectionReq(wsDataBuff.toString(), wsURL);
            if(vResponse!=null && !vResponse.equals("")){
                Map hm=tokenizeToHashMap(vResponse, "&", "=");
                encResXML = hm.containsKey("enc_response")?hm.get("enc_response").toString():"";
                String vStatus = hm.containsKey("status")?hm.get("status").toString():"";
                String vError_code = hm.containsKey("enc_error_code")?hm.get("enc_error_code").toString():"";
                if(vStatus.equals("1")){//If Api call failed
                    System.out.println("enc_response : "+ encResXML);
                    System.out.println("error_code : "+ vError_code);
                    return;
                }
                if(!encResXML.equals("")) {
                    AesUtil aesUtil=new AesUtil(aesKey);
                    decXML = aesUtil.decrypt(encResXML);
                    System.out.println("enc_response : "+decXML);
                    return;
                }
            }
        }
        catch (Exception e) {
            System.out.println("enc_response : "+ e.getMessage());
        }
    }

    public static HashMap tokenizeToHashMap(String msg, String delimPairValue, String delimKeyPair){
        HashMap keyPair = new HashMap();
        ArrayList respList = new ArrayList();
        String part = "";
        StringTokenizer strTkn = new StringTokenizer(msg, delimPairValue,true);
        while (strTkn.hasMoreTokens())
        {
            part = (String)strTkn.nextElement();
            if(part.equals(delimPairValue)) {
                part=null;
            }
            else {
                String str[]=part.split(delimKeyPair, 2);
                keyPair.put(str[0], str.length>1?(str[1].equals("")?null:str[1]):null);
            }
            if(part == null) continue;
            if(strTkn.hasMoreTokens()) strTkn.nextElement();
        }
        return keyPair.size() > 0 ? keyPair : null;
    }

    public static String processUrlConnectionReq(String pBankData,String pBankUrl) throws Exception{
        URL vUrl = null;
        URLConnection vHttpUrlConnection = null;
        DataOutputStream vPrintout = null;
        DataInputStream vInput = null;
        StringBuffer vStringBuffer=null;
        vUrl = new URL(pBankUrl);

//        URL url = new URL("https://redmine.xxx.cz/time_entries.xml");
//        URL url = new URL(null, "https://redmine.xxx.cz/time_entries.xml", new sun.net.www.protocol.https.Handler());


        if(vUrl.openConnection() instanceof HttpsURLConnection)
        {
            vHttpUrlConnection = (HttpsURLConnection)vUrl.openConnection();
        }

//        else if(vUrl.openConnection() instanceof com.sun.net.ssl.HttpsURLConnection)
//        {
//            vHttpUrlConnection = (com.sun.net.ssl.HttpsURLConnection)vUrl.openConnection();
//        }

        else
        {
            vHttpUrlConnection = (URLConnection)vUrl.openConnection();
        }
        vHttpUrlConnection.setDoInput(true);
        vHttpUrlConnection.setDoOutput(true);
        vHttpUrlConnection.setUseCaches(false);
        vHttpUrlConnection.connect();
        vPrintout = new DataOutputStream (vHttpUrlConnection.getOutputStream());
        vPrintout.writeBytes(pBankData);
        vPrintout.flush();
        vPrintout.close();
        try {
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(vHttpUrlConnection.getInputStream()));
            vStringBuffer = new StringBuffer();
            String vRespData;
            while((vRespData = bufferedreader.readLine()) != null)
                if(vRespData.length() != 0)
                    vStringBuffer.append(vRespData.trim());
            bufferedreader.close();
            bufferedreader = null;
        }finally {
            if (vInput != null)
                vInput.close();
            if (vHttpUrlConnection != null)
                vHttpUrlConnection = null;
        }
        return vStringBuffer.toString();
    }



}
