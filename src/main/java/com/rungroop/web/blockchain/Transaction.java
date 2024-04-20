package com.rungroop.web.blockchain;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Transaction {
    
    private Wallet source;
    private Wallet destination;
    private ArrayList<String> data = new ArrayList<>(); // [quantity, productlist( : )]
    private long timeStamp;
    private byte[] signature;

    public Transaction(Wallet source, Wallet destination, ArrayList<String> data, long timeStamp) {
        this.source = source;
        this.destination = destination;
        this.data = data;
        this.timeStamp = timeStamp;
    }

    public boolean isValid() {
        // Check for valid recipient address
        if (destination == null || destination.getAddress().isEmpty()) {
            return false;
        }
        // Check for non-negative amount
        // if (Integer.parseInt(data[0]) <= 0) { DID NOT WORK
        if (Integer.parseInt(data.get(0)) <= 0) {
            return false;
        }
        return true;
    }

    public byte[] getDataToSign() {
        // Concatenate relevant transaction data into a byte array
        String data = source.getAddress() + destination.getAddress() + getQuantity() + timeStamp;
        return data.getBytes(StandardCharsets.UTF_8);
    }

    public void generateSignature() {
        try {
            Signature signAlgorithm = Signature.getInstance("SHA256withRSA");
            signAlgorithm.initSign(source.getPrivateKey());
            signAlgorithm.update(getDataToSign());
            this.signature = signAlgorithm.sign();
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public byte[] getSignature() {
        return signature;
    }

    public PublicKey getSourcePublicKey() {
        return source.getPublicKey();
    }

    public Wallet getSource() {
        return source;
    }

    public Wallet getDestination() {
        return destination;
    }

    public String getQuantity() {
        return data.get(0);
    }

    public String[] getProductList() {
        return data.get(1).split(":");
    }
    
    public String toString() {
        return source + ":" + destination + ":" + data.get(0);
    }

}
