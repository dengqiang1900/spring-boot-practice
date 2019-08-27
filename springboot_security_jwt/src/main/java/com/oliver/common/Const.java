package com.oliver.common;

public class Const {
  public static final long EXPIRATION_TIME = 432000000;      //5天（ms）
  public static final String SECRET = "OliverQueenSecret";   //JWT密码
  public static final String TOKEN_PREFIX = "Bearer";        //Token前缀
  public static final String HEADER_STRING = "Authorization";//存放Token的Head Key
}
