
/** * @file blockchain.java
 * @author Krisna Pranav
 * @version 1.0
 * @date 2025-04-01
 * 
 * @copyright Copyright (c) 2025 Krisna Pranav
 * 
 */

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;

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
            String input = index + previousHash + transactions + timestamp + nonce;
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
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
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
        Block newBlock = new Block(chain.size(), chain.get(chain.size() - 1).hash, transactions);
        newBlock.mineBlock(difficulty);
        chain.add(newBlock);
    }

    public void printChain() {
        for (Block block : chain) {
            System.out.println("Index: " + block.index);
            System.out.println("Hash: " + block.hash);
            System.out.println("Previous Hash: " + block.previousHash);
            System.out.println();
        }
    }
}

public class main {
    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain(2);
        blockchain.addBlock("Alice pays Bob 10 BTC");
        blockchain.addBlock("Bob pays Charlie 5 BTC");
        blockchain.printChain();
    }
}
