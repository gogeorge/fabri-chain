package com.rungroop.web.blockchain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.nio.charset.StandardCharsets;


public class Block {
    private String hash;
    private String previousHash;
    private String data;
    private long timeStamp;
    private int nonce;
    private List<Transaction> transactions;

    // should block extend blockchain?
    public Block(String data, String previousHash, long timeStamp, List<Transaction> transactions) {
        // what data hmmm?
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = timeStamp;
        this.transactions = new ArrayList<>();
        this.hash = calculateBlockHash();
    }

    public String calculateBlockHash() {
        // System.out.println("transaction: " + transactions.toString() + " " + nonce + " " + hash);
        String dataToHash = previousHash
          + Long.toString(timeStamp)
          + Integer.toString(nonce)
          + transactions
          + data;
        MessageDigest digest = null;
        byte[] bytes = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            bytes = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex.getMessage());
        }
        StringBuffer buffer = new StringBuffer();
        for (byte b : bytes) {
            buffer.append(String.format("%02x", b));
        }
        return buffer.toString();
    }

    public String mineBlock(int prefix) {
        String prefixString = new String(new char[prefix]).replace('\0', '0');
        while (!hash.substring(0, prefix).equals(prefixString)) {
            nonce++;
            hash = calculateBlockHash();
        }
        System.out.println("transaction: " + transactions.toString().split(", ").length + " " + nonce + " " + hash);
        return hash;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getHash() {
        return hash;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}