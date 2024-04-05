package com.rungroop.web.blockchain;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Map;

public class Transaction {
    
    private Wallet source;
    private Wallet destination;
    private long quantity;
    private long timeStamp;
    private byte[] signature;

    public Transaction(Wallet source, Wallet destination, long quantity, long timeStamp) {
        this.source = source;
        this.destination = destination;
        this.quantity = quantity;
        this.timeStamp = timeStamp;
    }

    public boolean isValid() {
        // Check for valid recipient address
        if (destination == null || destination.getAddress().isEmpty()) {
            return false;
        }
        // Check for non-negative amount
        if (quantity <= 0) {
            return false;
        }
        return true;
    }

    public byte[] getDataToSign() {
        // Concatenate relevant transaction data into a byte array
        String data = source.getAddress() + destination.getAddress() + quantity + timeStamp;
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
        return source;
    }

    public long getQuantity() {
        return quantity;
    }
    
    public String toString() {
        return source + ":" + destination + ":" + quantity;
    }

}
