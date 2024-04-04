package com.rungroop.web.blockchain;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Dictionary;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;

public class InjectedExample {
    public static void main(String[] args) throws NoSuchAlgorithmException, InterruptedException {
        Wallet w1 = new Wallet("Genesis Sender");
        Wallet w2 = new Wallet("Genesis Receiver");   
        
        // quantity (1L) can be interpreted as waste
        List<Transaction> genesisTransaction = Arrays.asList(new Transaction(w1, w2, 1L, new Date().getTime()));
        BlockChain blockchain = new BlockChain(new ArrayList<>(Arrays.asList(new Block(
            "Genesis Block", 
            "0",
            new Date().getTime(),
            genesisTransaction
        ))), 3, 4);
        
        Wallet rmWallet = new Wallet("Brazil Cotton Inc. (Raw Materials)");
        Wallet fpWallet = new Wallet("Mexico Fabrics ltd (Fabric Production)");
        Wallet sfWallet = new Wallet("SewingLabs co (Sewing Fabrics)");
        Wallet dWallet = new Wallet("Zara (Dsitributor)");

        blockchain.receiveTransaction(new Transaction(rmWallet, fpWallet, 23L, new Date().getTime()));
        Thread.sleep(200);
        blockchain.receiveTransaction(new Transaction(rmWallet, fpWallet, 212L, new Date().getTime()));
        Thread.sleep(200);
        blockchain.receiveTransaction(new Transaction(rmWallet, fpWallet, 40L, new Date().getTime()));
        Thread.sleep(200);
        blockchain.receiveTransaction(new Transaction(rmWallet, fpWallet, 33L, new Date().getTime()));
        Thread.sleep(1000);

        blockchain.receiveTransaction(new Transaction(fpWallet, sfWallet, 23L, new Date().getTime()));
        Thread.sleep(200);
        blockchain.receiveTransaction(new Transaction(fpWallet, sfWallet, 25L, new Date().getTime()));
        Thread.sleep(1000);

        blockchain.receiveTransaction(new Transaction(sfWallet, dWallet, 4L, new Date().getTime()));

        System.out.println(blockchain);

        // blockchain to json

        // String jsonData = "{\"transactions\": [{ \"source\":" + rmWallet.getName() + ", \"dest\":" + fpWallet.getName() + ", \"quantity\":" + 23L + "}]}";
        String jsonData = "{\"transactions\": [{ \"source\": \"Brazil Cotton Inc\", \"dest\": \"Mexico Fabrics ltd\", \"quantity\":" + 23L + "}]}";
        
        System.out.println(jsonData);

        try {
            // Create ObjectMapper object
            ObjectMapper objectMapper = new ObjectMapper();

            // Write JSON data to file
            objectMapper.writeValue(new File("src/main/java/com/rungroop/web/blockchain/data.json"), objectMapper.readTree(jsonData));

            System.out.println("JSON data has been written to data.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
