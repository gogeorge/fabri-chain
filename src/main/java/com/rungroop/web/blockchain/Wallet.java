package com.rungroop.web.blockchain;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Wallet {
    private String name;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private String address;
    private List<Transaction> walletTransactions;
    private long balance;

    public Wallet(String name) throws NoSuchAlgorithmException {
        KeyPair keyPair = WalletUtil.generateKeyPair();
        this.publicKey = keyPair.getPublic();
        this.privateKey = keyPair.getPrivate();
        this.address = WalletUtil.generateHashAddress(publicKey);
        this.name = name;
    }

    public boolean validateWallet(PublicKey publicKey, PrivateKey privateKey) {
        return true;
    }

    public Transaction createTransaction(Wallet source, Wallet destination, long quantity) {
        if (validateWallet(publicKey, privateKey)) {
            Transaction newTransaction = new Transaction(source, destination, new ArrayList<>(), new Date().getTime());
            try {
                byte[] signature = WalletUtil.sign(newTransaction.getDataToSign(), privateKey);
                newTransaction.setSignature(signature);
                walletTransactions.add(newTransaction);
                return newTransaction;
            } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException  e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance += balance;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

}
