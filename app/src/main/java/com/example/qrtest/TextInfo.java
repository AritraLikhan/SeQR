package com.example.qrtest;

public class TextInfo {

    public String fileName, fileText, publicKey, privateKey;

    TextInfo(){}
    TextInfo(String fileName, String fileText, String publicKey,String privateKey){
          this.fileName = fileName;
          this.fileText = fileText;
          this.publicKey = publicKey;
          this.privateKey = privateKey;
    }


}
