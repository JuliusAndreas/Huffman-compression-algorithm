package reversemrhuffman;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.BitSet;
import java.util.Collections;
import java.util.LinkedList;


/**
 *
 * @author Julius Andreas
 */
public class ReverseMrHuffman {

    public static void main(String[] args) throws IOException {
        LinkedList outputBytes = new LinkedList<>();
        LinkedList<Node> nodes = new LinkedList();
        byte[] arrayOfOutputBytes;
        BitSet huffmanCode;
        DataOutputStream output;
        File outputFile = new File("../decompression/decompressedFile");
        int numberOfNodes;
        Node formedHuffmanTree;
        Decompresser decompresser = null;
        DataInputStream input = null;
        String huffmanTreeMaker;
        BufferedReader reader;
        byte[] byteCodes = null;
        int[] frequencies = null;
        try {
            File treeFile = new File("../HuffmanTree/tree.txt");
            File file = new File("../compression/compressedFile");
            if (file.canRead()) {
                input = new DataInputStream(new BufferedInputStream(
                        new FileInputStream(file)));
                decompresser = new Decompresser(input, file);
            }
            if (treeFile.canRead()) {
                reader = new BufferedReader(new FileReader(treeFile));
                huffmanTreeMaker = reader.readLine();
                String[] temp = huffmanTreeMaker.split(",");
                frequencies = new int[temp.length / 2];
                byteCodes = new byte[temp.length / 2];
                for (int i = 0; i < temp.length / 2; i++) {
                    frequencies[i] = Integer.parseInt(temp[i]);
                }
                for (int i = 0; i < temp.length / 2; i++) {
                    byteCodes[i] = Byte.parseByte(temp[temp.length / 2 + i]);
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (byteCodes != null && frequencies != null) {
            numberOfNodes = byteCodes.length;
            for (int i = 0; i < numberOfNodes; i++) {
                Node tempNode = new Node(byteCodes[i]);
                tempNode.setFrequency(frequencies[i]);
                nodes.add(tempNode);
            }
        }
        while (nodes.size() > 1) {
            Collections.sort(nodes);
            Node dummyParent = new Node();
            dummyParent.setFrequency(
                    nodes.get(0).getFrequency() + nodes.get(1).getFrequency());
            nodes.get(0).setParent(dummyParent);
            nodes.get(1).setParent(dummyParent);
            if (nodes.get(0).getFrequency() <= nodes.get(1).getFrequency()) {
                dummyParent.setlLink(nodes.get(0));
                nodes.get(0).formHuffmanCode("0");
                dummyParent.setrLink(nodes.get(1));
                nodes.get(1).formHuffmanCode("1");
            } else {
                dummyParent.setlLink(nodes.get(1));
                nodes.get(1).formHuffmanCode("0");
                dummyParent.setrLink(nodes.get(0));
                nodes.get(0).formHuffmanCode("1");
            }
            nodes.remove(0);
            nodes.remove(0);
            nodes.addFirst(dummyParent);
        }
        formedHuffmanTree = nodes.getFirst();
        huffmanCode = decompresser.getHuffmanCodes();
        outputFile.createNewFile();
        output = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(outputFile)));
        Node currentNode = formedHuffmanTree;
        for (int i = 0; i < huffmanCode.length(); i++) {
            if (currentNode.getlLink() == null && currentNode.getrLink() == null) {
                outputBytes.add(currentNode.getByteCode());
                currentNode = formedHuffmanTree;
            }
            if (huffmanCode.get(i) == false) {
                if (currentNode.getlLink() != null) {
                    currentNode = currentNode.getlLink();
                }
            }
            if (huffmanCode.get(i) == true) {
                if (currentNode.getlLink() != null) {
                    currentNode = currentNode.getrLink();
                }
            }
        }
        arrayOfOutputBytes = new byte[outputBytes.size()];
        for (int i = 0; i < outputBytes.size(); i++) {
            arrayOfOutputBytes[i] = (byte) outputBytes.get(i);
        }
        output.write(arrayOfOutputBytes, 0, arrayOfOutputBytes.length);
        output.close();

    }
}
