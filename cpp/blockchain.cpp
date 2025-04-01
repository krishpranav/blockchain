/**
 * @file blockchain.cpp
 * @author Krisna Pranav
 * @version 1.0
 * @date 2025-04-01
 * 
 * @copyright Copyright (c) 2025 Krisna Pranav
 * 
 */

#include <iostream>
#include <vector>
#include <ctime>
#include <sstream>
#include <openssl/sha.h>

using namespace std;

class Block
{
public:
    int index;
    string previousHash;
    string transactions;
    time_t timestamp;
    int nonce;
    string hash;

    /**
     * @brief Construct a new Block object
     * 
     * @param idx 
     * @param prevHash 
     * @param txn 
     * @param time 
     */
    Block(int idx, string prevHash, string txn, time_t time) : index(idx), previousHash(prevHash), transactions(txn), timestamp(time), nonce(0)
    {
        hash = calculateHash();
    }

    string calculateHash()
    {
        stringstream ss;
        ss << index << previousHash << transactions << timestamp << nonce;
        string data = ss.str();

        unsigned char hashBuffer[SHA256_DIGEST_LENGTH];
        SHA256((unsigned char *)&data[0], data.size(), hashBuffer);

        stringstream hexStream;
        for (int i = 0; i < SHA256_DIGEST_LENGTH; ++i)
        {
            hexStream << hex << (int)hashBuffer[i];
        }
        return hexStream.str();
    }

    /**
     * @param difficulty 
     */
    void mineBlock(int difficulty)
    {
        string prefix(difficulty, '0');
        while (hash.substr(0, difficulty) != prefix)
        {
            nonce++;
            hash = calculateHash();
        }
    }
}; // class Block

class Blockchain
{
public:
    vector<Block> chain;
    int difficulty;

    /**
     * @brief Construct a new Blockchain object
     * 
     * @param diff 
     */
    Blockchain(int diff) : difficulty(diff)
    {
        chain.push_back(createGenesisBlock());
    }

    /**
     * @brief Create a Genesis Block object
     * 
     * @return Block 
     */
    Block createGenesisBlock()
    {
        return Block(0, "0", "Genesis Block", time(0));
    }

    /**
     * @param transactions 
     */
    void addBlock(string transactions)
    {
        Block newBlock(chain.size(), chain.back().hash, transactions, time(0));
        newBlock.mineBlock(difficulty);
        chain.push_back(newBlock);
    }
}; // class Blockchain

int main()
{
    Blockchain blockchain(2);
    blockchain.addBlock("John pays Bob 10 BTC");
    blockchain.addBlock("Bob pays Carlos 5 BTC");

    for (const auto &block : blockchain.chain)
    {
        cout << "Index: " << block.index << "\nHash: " << block.hash << "\nPrevious: " << block.previousHash << "\n"
             << endl;
    }
}
