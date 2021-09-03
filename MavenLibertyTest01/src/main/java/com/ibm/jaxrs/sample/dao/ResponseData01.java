package com.ibm.jaxrs.sample.dao;

public class ResponseData01 {
  private String strResult = "";
  private String strData = "";

  public ResponseData01(String strResult, String strData) {
    this.strResult = strResult;
    this.strData = strData;
  }

  public String getStrResult() {
    return strResult;
  }

  public void setStrResult(String strResult) {
    this.strResult = strResult;
  }

  public String getStrData() {
    return strData;
  }

  public void setStrData(String strData) {
    this.strData = strData;
  }

}