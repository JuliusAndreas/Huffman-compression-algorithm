package reversemrhuffman;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.BitSet;

/**
 *
 * @author Julius Andreas
 */
public class Decompresser {

    public Decompresser(DataInputStream inputFile, File file) throws IOException {
        BitSet bitSet = BitSet.valueOf(Files.readAllBytes(file.toPath()));
        bitSet.clear(bitSet.length() - 1);
        huffmanBitset = new BitSet(bitSet.length());
        huffmanBitset = bitSet;
    }

//    private String huffmanCodes;
    private BitSet huffmanBitset;

    public BitSet getHuffmanCodes() {
        return huffmanBitset;
    }
}
