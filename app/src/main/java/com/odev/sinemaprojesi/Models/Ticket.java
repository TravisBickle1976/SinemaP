package com.odev.sinemaprojesi.Models;

public class Ticket {
    String film;
    String date;
    int session;
    int saloon;
    int row;
    int column;
    int price;
    int campaign_used; //Yoksa -1

    public Ticket(String date, String film, int session, int saloon, int row, int column, int price, int campaign_used) {
        this.film = film;
        this.date = date;
        this.session = session;
        this.saloon = saloon;
        this.row = row;
        this.column = column;
        this.price = price;
        this.campaign_used = campaign_used;
    }

    public Ticket(){

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFilm() {
        return film;
    }

    public void setFilm(String film) {
        this.film = film;
    }

    public int getSession() {
        return session;
    }

    public void setSession(int session) {
        this.session = session;
    }

    public int getSaloon() {
        return saloon;
    }

    public void setSaloon(int saloon) {
        this.saloon = saloon;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCampaign_used() {
        return campaign_used;
    }

    public void setCampaign_used(int campaign_used) {
        this.campaign_used = campaign_used;
    }
}
