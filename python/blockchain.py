'''
@author: Krisna Pranav
@brief: blockchain implementation
@copyright Copyright (c) 2025 Krisna Pranav
@date: 1-04-2025
'''

import hashlib
import time
import json

class Block:
    def __init__(self, index, previous_hash, transactions, timestamp, nonce=0):
        self.index = index
        self.previous_hash = previous_hash
        self.transactions = transactions
        self.timestamp = timestamp
        self.nonce = nonce
        self.hash = self.calculate_hash()
    
    def calculate_hash(self):
        block_string = json.dumps(self.__dict__, sort_keys=True)
        return hashlib.sha256(block_string.encode()).hexdigest()
    
    def mine_block(self, difficulty):
        while self.hash[:difficulty] != "0" * difficulty:
            self.nonce += 1
            self.hash = self.calculate_hash()
            
class Blockchain:
    def __init__(self, difficulty=2):
        self.chain = [self.create_genesis_block()]
        self.difficulty = difficulty

    def create_genesis_block(self):
        return Block(0, "0", [], time.time())

    def add_block(self, transactions):
        new_block = Block(len(self.chain), self.chain[-1].hash, transactions, time.time())
        new_block.mine_block(self.difficulty)
        self.chain.append(new_block)
        

def main():
    blockchain = Blockchain()
    blockchain.add_block(["John pays Bob 10 BTC"])
    blockchain.add_block(["Bob pays Carlos 5 BTC"])

    for block in blockchain.chain:
        print(vars(block))
        
if __name__ == "__main__":
    print("Blockchain activating...")
    main()