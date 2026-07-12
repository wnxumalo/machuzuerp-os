/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jsf;

import com.machuzuerp.jdbc.FileProcessing;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.model.StreamedContent;

@SessionScoped
@Named
public class FileComments implements Serializable {

    Connection connection;
    Statement statement;
    ResultSet results;

    private StreamedContent filePath;

    private String recNum;
    private String teamID;
    private String teamName;
    private String fileID;
    private String date;
    private String time;
    private String comment;
    private String type;       

    private Date dateFrom;
    private Date dateTo;      
    
    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;
    
    FileProcessing fp = new FileProcessing();   
    
    String query = "";
    
    private List<FileComments> fileComments = new ArrayList<FileComments>();
    
    Login login = new Login();
    
    public FileComments() {
        
    }
    
    public FileComments(String comment) {
    
        this.comment = comment;        
    
    }

    public String getRecnum() {
        return recNum;
    }

    public void setRecNum(String recNum) {
        this.recNum = recNum;
    }

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }
    
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    
    public String getFileID() {
        return fileID;
    }

    public void setFileID(String fileID) {
        this.fileID = fileID;
    }
   /* 
    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }*/
    
    public String getComments() {
        return comment;
    }

    public void setComments(String comment) {
        this.comment = comment;
    }
    /*
    public String getNewComment() {
        return newComment;
    }

    public void setNewComment(String newComment) {
        this.newComment = newComment;
    }    
       
    public Date getDateFrom() {
        return dateFrom;
    }
    
    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }
    
    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }
    
    public List<SelectItem> getTeams() throws SQLException {  
        return fp.getTeams(this.uk);
    }        
    
  /*  public List<FileData> getFiles() {
        getParams();
        filterFiles();
        //customers.add(new CustomerList("0000","Wandile","Nxum","dwed","sdfvsdf","sdfsd","Sdfsdf","ergergt","bergfergf","wfwrewer","2432edwqed","sdfwefrw","wsfwerw"));

        return files;
    }

    public FileData getSelectedFile() {
        return selectedFile;
    }

    public void setSelectedFile(FileData selectedFile) {
        this.selectedFile = selectedFile;
    }
    */
    public List<FileComments> getFileComments() {
        return fileComments;
    }

    public void setFileComments(List<FileComments> fileComments) {
        this.fileComments = fileComments;
    }
/*
    public void onRowSelect(SelectEvent event) throws IOException {

        getParams();
        getLoginParams();

        this.recNum = ((FileData) event.getObject()).recNum;
        this.fileName = ((FileData) event.getObject()).fileName;
        this.teamName = ((FileData) event.getObject()).teamName;
        this.uploadDate = ((FileData) event.getObject()).uploadDate;        

        try {
            this.name = URLEncoder.encode(this.name, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FileData.class.getName()).log(Level.SEVERE, null, ex);
        }

        String param = "name=" + name + "&uk=" + uk + "&recnum=" + this.recNum + "&fileName=" + fileName + "&teamName=" + teamName + "&uploadDate=" + uploadDate;

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        url = request.getRequestURL().toString();
        uri = request.getRequestURI();

        endIndex = uri.lastIndexOf("/");

        if (endIndex != -1) {
            newstr = uri.substring(0, endIndex);
            FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/discuss-file.xhtml?" + param);
        }                

    }
    
    public void saveComment() {
 System.out.println("Pressed enter!");
}*/

    //public List<String> getFileComments(String fileID) {
        //return fp.getFileComments(fileID);
    //}

}