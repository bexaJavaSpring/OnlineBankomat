package uz.pdp.apponlineatm.model;


import uz.pdp.apponlineatm.model.Enum.CardStatus;

import static uz.pdp.apponlineatm.model.Enum.CardStatus.UNBLOCK;

public class BankCard  {
    private Integer id;
    private Long cardNumber;
    private int pinCode;
    private CardStatus status = UNBLOCK;
    private User user;
    private double balance;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public BankCard() {
    }

    public BankCard(Integer id, Long cardNumber, int pinCode, CardStatus status, User user) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.pinCode = pinCode;
        this.status = status;
        this.user = user;
    }

    public BankCard(Integer id, Long cardNumber, int pinCode, CardStatus status) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.pinCode = pinCode;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getPinCode() {
        return pinCode;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }

    public CardStatus getStatus() {
        return status;
    }

    public void setStatus(CardStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "BankCard{" +
                "id=" + id +
                ", cardNumber=" + cardNumber +
                ", pinCode=" + pinCode +
                ", status=" + status +
                ", user=" + user +
                '}';
    }
}
