import java.util.ArrayList;

public class Blockchain {
    private ArrayList<Block> chain;
    private static final int DIFFICULTY = 4; // Difficulty for Proof of Work

    public Blockchain() {
        chain = new ArrayList<>();
        chain.add(createGenesisBlock());
    }

    private Block createGenesisBlock() {
        return new Block(0, "0", "Genesis Block");
    }

    public Block getLatestBlock() {
        return chain.get(chain.size() - 1);
    }

    public void addBlock(Block newBlock) {
        if (isValidNewBlock(newBlock)) {
            chain.add(newBlock);
        }
    }

    private boolean isValidNewBlock(Block newBlock) {
        Block previousBlock = getLatestBlock();

        // Check if index is correct
        if (newBlock.getIndex() != previousBlock.getIndex() + 1) {
            return false;
        }

        // Check if previous hash matches
        if (!newBlock.getPreviousHash().equals(previousBlock.getHash())) {
            return false;
        }

        // Check Proof of Work
        if (!newBlock.getHash().substring(0, DIFFICULTY).equals("0".repeat(DIFFICULTY))) {
            return false;
        }

        return true;
    }

    public Block mineBlock(String[] transactions) {
        Block previousBlock = getLatestBlock();
        int newIndex = previousBlock.getIndex() + 1;
        String previousHash = previousBlock.getHash();
        Block newBlock = new Block(newIndex, previousHash, transactions);

        // Proof of Work
        while (!newBlock.getHash().substring(0, DIFFICULTY).equals("0".repeat(DIFFICULTY))) {
            newBlock.incrementNonce();
        }

        chain.add(newBlock);
        return newBlock;
    }

    public boolean isValidChain() {
        for (int i = 1; i < chain.size(); i++) {
            Block currentBlock = chain.get(i);
            Block previousBlock = chain.get(i - 1);

            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }

            if (!currentBlock.getHash().substring(0, DIFFICULTY).equals("0".repeat(DIFFICULTY))) {
                return false;
            }
        }
        return true;
    }
}
