import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Example {
    private static Transaction generateRandomTransaction() throws NoSuchAlgorithmException {
        // Generate random names and amount for transaction
        String[] names = {"Manufacturer", "Raw Materials", "Distributor", "Stores", "Online Stores"};
        Random random = new Random();
        Wallet sender = new Wallet(names[random.nextInt(names.length)]);
        Wallet receiver = new Wallet(names[random.nextInt(names.length)]);
        long amount = random.nextInt(1000) + 1; // Random amount between 1 and 1000
        return new Transaction(sender, receiver, amount, new Date().getTime());
    }
    public static void main(String[] args) throws NoSuchAlgorithmException {
        Wallet w1 = new Wallet("Genesis Sender");
        Wallet w2 = new Wallet("Genesis Receiver");
        // Create a new blockchain with a prefix of 4 for proof of work
        List<Transaction> genesisTransaction = Arrays.asList(new Transaction(w1, w2, 1L, new Date().getTime()));
        BlockChain blockchain = new BlockChain(new ArrayList<>(Arrays.asList(new Block(
            "Genesis Block", 
            "0", 
            new Date().getTime(), 
            genesisTransaction
        ))), 3, 4);
        Random random = new Random();

        while (true) {
            blockchain.receiveTransaction(generateRandomTransaction());
            try {
                Thread.sleep(random.nextInt(3000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}