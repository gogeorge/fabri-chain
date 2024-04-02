import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BlockChain {
    private List<Block> chain = new ArrayList<>();
    private int transactionLimit;
    private int prefix;

    public BlockChain(List<Block> chain, int transactionLimit, int prefix) {
        this.chain = chain;
        this.transactionLimit = transactionLimit;
        this.prefix = prefix;
    }

    public void receiveTransaction(Transaction transaction) {
        // Verify the signature using the sender's public key
        if (verifySignature(transaction)) {
            addTransaction(transaction);
        } else {
            System.out.println("Invalid transaction signature. Transaction rejected.");
        }
    }

    public boolean verifySignature(Transaction transaction) {
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(transaction.getSourcePublicKey());
            signature.update(transaction.getDataToSign());
            transaction.generateSignature();
            return signature.verify(transaction.getSignature());
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    public void addTransaction(Transaction transaction) {
        if (transaction.isValid()) {
            Block currentBlock = chain.get(chain.size() - 1);
            System.out.println(currentBlock.getTimeStamp());
            System.out.println("valid transaction " + currentBlock.getTransactions().size() + ", " + chain.size());
            currentBlock.addTransaction(transaction);
            transaction.getSource().setBalance(transaction.getQuantity());
            System.out.println("Balance (Block_" + chain.size() + "): " + transaction.getSource().getBalance() + " " + transaction.getQuantity() + " " + transaction.getSource().getName());
            if (currentBlock.getTransactions().size() >= transactionLimit) {
                System.out.println("limit reached: " + currentBlock.getTransactions());
                addBlock(currentBlock.getHash());
            } 
        } 
    }

    public boolean verifyBlock(Block block) {
        boolean flag = true;
        for (int i = 0; i < chain.size(); i++) {
            String previousHash = i==0 ? "0" : chain.get(i - 1).getHash();
            flag = chain.get(i).getPreviousHash().equals(previousHash);
            if (!flag) break;
        }
        System.out.println("verified: " + flag);    
        return true;
    }

    public void addBlock(String prevHash) {
        Block block = new Block("Block_" + chain.size(), prevHash, new Date().getTime(), null);
        System.out.println("block mining begins");
        block.mineBlock(prefix);
        if (verifyBlock(block)) {
            System.out.println("block verified");
            System.out.println("Block: " + chain.size());
            System.out.println("Previous Hash: " + block.getPreviousHash());
            System.out.println("Hash: " + block.getHash());
            System.out.println("Timestamp: " + block.getTimeStamp());
            System.out.println();
            chain.add(block);
        }
    }

    public int size() {
        return chain.size();
    }

    public List<Block> chain() {
        return chain;
    }
}
