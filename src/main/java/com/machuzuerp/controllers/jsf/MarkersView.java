package com.machuzuerp.controllers.jsf;

import com.machuzuerp.jdbc.GISProcessing;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.shaded.json.JSONArray;
import org.primefaces.shaded.json.JSONException;
import org.primefaces.shaded.json.JSONObject;

@Named
@RequestScoped
public class MarkersView implements Serializable {

    ResultSet results;
    GISProcessing gp = new GISProcessing();

    private MapModel model;// = new DefaultMapModel();
    private EntityManager em;

    private String title;
    private double lat;
    private double lng;

    @PostConstruct
    public void init() {
        model = new DefaultMapModel();

        List<LatLng> coords = new ArrayList<LatLng>();

        try {

            boolean next = false;
            String inline = "";

            //  results = gp.getGISClient();
            // call api
            try {
                //URL url=new URL("https://www.machuzu.com/rest/ecobuzz/getaccounts.php?all=true");    
                URL url = new URL("http://www.machuzu.com/rest/ecobuzz/getaccounts.php?all=true");
                //HttpsURLConnection huc=(HttpsURLConnection)url.openConnection();  
                HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                huc.setRequestMethod("GET");                
                Scanner sc = new Scanner(url.openStream());
                while (sc.hasNext()) {
                    inline += sc.nextLine();
                }

                sc.close();

                huc.disconnect();
            } catch (Exception e) {
                System.out.println("PHP SERVICE ERR: " + e);
            }

            //end

            try {
                // create the json array from String rules
                JSONArray jsonRules = new JSONArray(inline.toString());
                // iterate over the rules 
                for (int i=0; i<jsonRules.length();i++){
                    JSONObject obj = jsonRules.getJSONObject(i);

                    System.out.println("===id is===: "+obj.getString("organisation"));
                    
                    Marker marker = null;
                    coords.clear();
                   // while (results.next()) {

                   if (!obj.getString("coordinates").equals("undefined,undefined")) { 
                        String[] coord = obj.getString("coordinates").split(",");

                        marker = new Marker(new LatLng(Double.parseDouble(coord[1]), Double.parseDouble(coord[0])), obj.getString("organisation"));
                        model.addOverlay(marker);
                   }
                }
            } catch (NumberFormatException | JSONException e){
            }

            //System.out.println(inline); = Info:   [{"uuid":"ddc99133-eaec-45f4-b8e9-44ada11379a6","organisation":"Charlton Investments ","telephone":"76055641","cellphone":"76381758","emailaddress":"charltoninvestments@yahoo.com","physicaladdress":"Manzini Post Office building office "},{"uuid":"5fc42db9-781f-4563-a6ae-1ea05a094c2d","organisation":"Quick cash loans","telephone":"79027688","cellphone":"78659000","emailaddress":"centralquickloans@gmail.com","physicaladdress":"Croydon,Ethayini"},{"uuid":"c97e828f-0e7b-4a30-9c5a-60228b0aeea0","organisation":"Flexi Financial Solutions ","telephone":"76810906 ","cellphone":"76135552","emailaddress":"khumalo.nomvuyo@gmail.com","physicaladdress":"216c Dlanubeka Building Mbabane "},{"uuid":"6d8abe00-0b8d-49f7-b20e-b5725ecad44e","organisation":"Cooper","telephone":"","cellphone":"76242698","emailaddress":"wandile@machuzu.com","physicaladdress":"31.1393596,-26.3282492"}]

            /*   Marker marker = null;
            coords.clear();
            while (results.next()) {

                String[] coord = results.getString(7).split(",");

                marker = new Marker(new LatLng(Double.parseDouble(coord[1]), Double.parseDouble(coord[0])));
                model.addOverlay(marker);
            }*/
        } catch (Exception ex) {
            Logger.getLogger(MarkersView.class.getName()).log(Level.SEVERE, null, ex);
        }

        //em.find(entityClass, this)
    }

    public MapModel getSimpleModel() {
        return model;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

}
