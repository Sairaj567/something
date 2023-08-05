import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Block {
    private int index;
    private long timestamp;
    private int previousHash;
    private String[] transactions;
    private String blockHash;

    public Block(int previousHash, String[] transactions) {
        this.index = 0;  // Default index, can be calculated based on blockchain position
        this.timestamp = System.currentTimeMillis();
        this.previousHash = previousHash;
        this.transactions = transactions;
        calculateBlockHash();
    }

    private void calculateBlockHash() {
        String data = Arrays.hashCode(transactions) + previousHash + timestamp;
        this.blockHash = applySHA512(data);
    }

    private String applySHA512(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
