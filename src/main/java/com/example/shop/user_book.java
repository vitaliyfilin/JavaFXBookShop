package com.example.shop;

import java.math.BigDecimal;

public class user_book {
    private Integer userbookid;
    private String userbookname;
    private String userbookauthor;
    private BigDecimal userbookprice;
    private Integer userbooknum;
    private Integer userbookpurchaseid;

    public user_book(Integer userbookid, String userbookname, String userbookauthor, BigDecimal userbookprice, Integer userbooknum, Integer userbookpurchaseid) {
        this.userbookid = userbookid;
        this.userbookname = userbookname;
        this.userbookauthor = userbookauthor;
        this.userbookprice = userbookprice;
        this.userbooknum = userbooknum;
        this.userbookpurchaseid = userbookpurchaseid;
    }

    public Integer getUserbookid() {
        return userbookid;
    }

    public void setUserbookid(Integer userbookid) {
        this.userbookid = userbookid;
    }

    public String getUserbookname() {
        return userbookname;
    }

    public void setUserbookname(String userbookname) {
        this.userbookname = userbookname;
    }

    public String getUserbookauthor() {
        return userbookauthor;
    }

    public void setUserbookauthor(String userbookauthor) {
        this.userbookauthor = userbookauthor;
    }

    public BigDecimal getUserbookprice() {
        return userbookprice;
    }

    public void setUserbookprice(BigDecimal userbookprice) {
        this.userbookprice = userbookprice;
    }

    public Integer getUserbooknum() {
        return userbooknum;
    }

    public void setUserbooknum(Integer userbooknum) {
        this.userbooknum = userbooknum;
    }

    public Integer getUserbookpurchaseid() {
        return userbookpurchaseid;
    }

    public void setUserbookpurchaseid(Integer userbookpurchaseid) {
        this.userbookpurchaseid = userbookpurchaseid;
    }
}
