/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author Xuyen
 */
public class MyFile {
    private String namefile;
    private byte[] file;

    public byte[] getFile() {
        return file;
    }

    public String getNamefile() {
        return namefile;
    }

    public MyFile(String namefile, byte[] file) {
        this.namefile = namefile;
        int b=file.length;
        this.file=new byte[b];
        int j=0;
        for (byte i:file){
            this.file[j]=i;
            j++;
        }
        
    }
    
}
