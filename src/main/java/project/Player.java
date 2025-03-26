/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project;

/**
 *
 * @author nickbenoit
 */

import java.util.ArrayList;

public class Player {
    private String name;
    private int balance;
    private int betAmount;
    //Player positioning on table
    private boolean isButtonPos;
    private boolean isLittleBlind;
    private boolean isBigBlind;
    private boolean allIn;

    //
    private ArrayList<Card> cards;

    public Player(String name) {
        this.name = name;
        this.cards = new ArrayList<>();
        this.betAmount = 0;
        this.balance = 1000;
        this.isButtonPos = false;
        this.isBigBlind = false;
        this.isLittleBlind = false;
        this.allIn = false;

    }

    //called on every instance of a new poker game
    public void resetPlayer(){
        cards = new ArrayList<>();
        betAmount = 0;
        allIn = false;
    }

    // Getters
    public String getName() {
        return name;
    }
    public int getBalance() {
        return balance;
    }
    public int getBetAmount() {
        return betAmount;
    }
    public ArrayList<Card> getCards() {
        return cards;
    }
    public boolean isButton(){
        return isButtonPos;
    }
    public boolean isBigBlind(){
        return isBigBlind;
    }
    public boolean isLittleBlind(){
        return isLittleBlind;
    }
    public boolean isAllIn(){
        return allIn;
    }

    //Setters
    public String changeName(String name){
        this.name = name;
        return name;
    }
    public void addToBalance(int amount) {
        if(amount > 0){
            balance += amount;
        }
    }
    public void addToBetAmount(int amount) {
        if(amount > 0){
            betAmount += amount;
        }
    }
    public void addCard(Card card) {
        cards.add(card);
    }
    public void changeButton(boolean b){
        isButtonPos = b;
    }
    public void changeLittleBlind(boolean b){
        isLittleBlind = b;
    }
    public void changeBigBlind(boolean b){
        isBigBlind = b;
    }
    public void changeAllIn(boolean b){
        allIn = b;
    }
}

