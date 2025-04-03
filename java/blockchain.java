import java.security.MessageDigest;
import java.sql.Date;
import java.util.ArrayList;

/**
 * @file blockchain.java
 * @author Krisna Pranav
 * @version 1.0
 * @date 2025-04-01
 * 
 * @copyright Copyright (c) 2025 Krisna Pranav
 * 
 */

class Block {
    public int index;
    public String previousHash;
    public String transactions;
    public long timestamp;
    public int nonce;
    public String hash;

    public Block(int index, String previousHash, String transactions) {
        this.index = index;
        this.previousHash = previousHash;
        this.transactions = transactions;
        this.timestamp = new Date().getTime();
        this.nonce = 0;
        this.hash = calculateHash();
    }

    public String calculateHash() {
        try {
            String input = index + previousHash + transactions + timestamp;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void mineBlock(int difficulty) {

    }
}

class Blockchain {
    private ArrayList<Block> chain;
    private int difficulty;

    public Blockchain(int difficulty) {
        this.chain = new ArrayList<>();
        this.difficulty = difficulty;
        chain.add(new Block(0, "0", "Genesis Block"));
    }

    public void addBlock(String transactions) {
        Block newBlock = new Block(chain.size(), chain.get());
    }

    public void printChain() {

    }
}