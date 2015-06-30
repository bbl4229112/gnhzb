package edu.zju.cims201.GOF.util.ontology;

public class Node {  
    
    private int id;  
    private String name;  
    private String nameURI; 
    private int pId;  
      
    public Node(){}  
      
    public Node(int id, String name,String nameURI, int pId){  
        this.id = id;  
        this.name = name;  
        this.nameURI = nameURI; 
        this.pId = pId;  
    }  
      
    public int getId() {  
        return id;  
    }  
    public void setId(int id) {  
        this.id = id;  
    }  
    public String getName() {  
        return name;  
    }  
    public void setNameURI(String nameURI) {  
        this.nameURI = nameURI;  
    }  
    public String getNameURI() {  
        return nameURI;  
    }  
    public void setName(String name) {  
        this.name = name;  
    } 
    public int getPId() {  
        return pId;  
    }  
    public void setPId(int id) {  
        pId = id;  
    }  
}  
