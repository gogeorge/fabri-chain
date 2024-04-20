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
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class InjectedExample {
    public static void main(String[] args) throws NoSuchAlgorithmException, InterruptedException {
        Wallet w1 = new Wallet("Genesis Sender");
        Wallet w2 = new Wallet("Genesis Receiver");   
        ArrayList<String> data = new ArrayList<>();
        Random random = new Random();

        data.add(random.nextInt(1000) + 1, "clo1903123:clo1234567:clo00039287");
        
        // quantity (1L) can be interpreted as waste
        List<Transaction> genesisTransaction = Arrays.asList(new Transaction(w1, w2, data, new Date().getTime()));
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


        data.add(random.nextInt(1000) + 1, "clo1903123:clo1234567:clo00039287");

        blockchain.receiveTransaction(new Transaction(rmWallet, fpWallet, data, new Date().getTime()));
        // Thread.sleep(200);
        blockchain.receiveTransaction(new Transaction(rmWallet, fpWallet, data, new Date().getTime()));
        // Thread.sleep(200);
        blockchain.receiveTransaction(new Transaction(rmWallet, fpWallet, data, new Date().getTime()));
        // Thread.sleep(200);
        blockchain.receiveTransaction(new Transaction(rmWallet, fpWallet, data, new Date().getTime()));
        // Thread.sleep(1000);

        blockchain.receiveTransaction(new Transaction(fpWallet, sfWallet, data, new Date().getTime()));
        // Thread.sleep(200);
        blockchain.receiveTransaction(new Transaction(fpWallet, sfWallet, data, new Date().getTime()));
        // Thread.sleep(1000);

        blockchain.receiveTransaction(new Transaction(sfWallet, dWallet, data, new Date().getTime()));

        System.out.println(blockchain);

        Gson gson = new Gson();

        JsonArray blocksArray = new JsonArray();

        for (int i = 0; i < blockchain.size(); i++) {
            Block currentBlock = blockchain.chain().get(i);

            JsonObject blockObject = new JsonObject();
            blockObject.addProperty("hash", currentBlock.getHash());
            blockObject.addProperty("previousHash", currentBlock.getPreviousHash());
            blockObject.addProperty("timestamp", currentBlock.getTimeStamp());

            JsonArray transactionsArray = new JsonArray();

            List<Transaction> transactions = currentBlock.getTransactions();

            for (Transaction transaction : transactions) {
                JsonObject transactionObject = new JsonObject();
                transactionObject.addProperty("source", transaction.getSource().getName());
                transactionObject.addProperty("destination", transaction.getDestination().getName());
                transactionObject.addProperty("quantity", transaction.getQuantity());
                transactionsArray.add(transactionObject);
            }

            blockObject.add("transactions", transactionsArray);

            blocksArray.add(blockObject);
        }

        String json = gson.toJson(blocksArray);

        System.out.println(json);

        // blockchain to json

        // String jsonData = "{\"transactions\": [{ \"source\":" + rmWallet.getName() + ", \"dest\":" + fpWallet.getName() + ", \"quantity\":" + 23L + "}]}";
        // String jsonData = "{\"transactions\": [{ \"source\": \"Brazil Cotton Inc\", \"dest\": \"Mexico Fabrics ltd\", \"quantity\":" + 23L + "}]}";

        // System.out.println(jsonData);

        try {
            // Create ObjectMapper object
            ObjectMapper objectMapper = new ObjectMapper();

            // Write JSON data to file
            objectMapper.writeValue(new File("src/main/java/com/rungroop/web/blockchain/data.json"), objectMapper.readTree(json));

            System.out.println("JSON data has been written to data.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
