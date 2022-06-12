package mrhuffman;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.BitSet;
import java.util.Collections;
import java.util.LinkedList;

public final class Compresser {

    public Compresser(DataInputStream inputFile, File file) throws IOException {
        int counter = 0;
        boolean sameFound = false;
        numberOfBytes = (int) file.length();
        Words.createArrays(numberOfBytes);
        Words.allBytes = Files.readAllBytes(file.toPath());
        this.createFirstAppearances(numberOfBytes);
        nodes = new LinkedList();
        while (counter < numberOfBytes) {
            for (int j = 0; j < nodes.size(); j++) {
                sameFound = false;
                if (nodes.get(j).getByteCode() == Words.allBytes[counter]) {
                    nodes.get(j).incFrequency();
                    sameFound = true;
                    Compresser.firstAppearances[counter]
                            = nodes.get(j).getMyFirstAppearance();
                    Words.allNodes[counter] = nodes.get(j);
                    counter++;
                    break;
                }
            }
            if (!sameFound) {
                nodes.add(new Node(Words.allBytes[counter]));
                nodes.getLast().incFrequency();
                Compresser.firstAppearances[counter] = -1;
                nodes.getLast().setMyFirstAppearance(counter);
                Words.allNodes[counter] = nodes.getLast();
                counter++;
            }
        }
    }

    private int numberOfBytes;
    private LinkedList<Byte> inputBytes;
    private LinkedList<Node> nodes;   //This LinkedList will become our Huffman tree
    private byte[] bytesArray;
    public static Node huffmanTree;
    static int[] firstAppearances;
    static Node[] nodesArray;
    private String huffmanTreeMaker;

    public void createFirstAppearances(int numberOfBytes) {
        Compresser.firstAppearances = new int[numberOfBytes];
    }

    public byte[] compress() {
        String compressionString = "";
        int j = 0;
        nodesArray = new Node[nodes.size()];
        for (int y = 0; y < nodes.size(); y++) {
            Compresser.nodesArray[j] = nodes.get(y);
            j++;
        }
        for (int u = 0; u < nodesArray.length; u++) {
            if (huffmanTreeMaker == null) {
                this.huffmanTreeMaker = nodesArray[u].getFrequency() + ",";
            } else {
                this.huffmanTreeMaker += nodesArray[u].getFrequency() + ",";
            }
        }
        for (int i = 0; i < nodesArray.length; i++) {
            if (i == nodesArray.length - 1) {
                this.huffmanTreeMaker += nodesArray[i].getByteCode() + "";
            } else {
                this.huffmanTreeMaker += nodesArray[i].getByteCode() + ",";
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
        Compresser.huffmanTree = this.nodes.getFirst();
        Node.setRoot(huffmanTree);
        for (int i = 0; i < numberOfBytes; i++) {
            compressionString += Words.allNodes[i].getHuffmanCode();
        }
        BitSet bitSet = new BitSet(compressionString.length()+1);
        for (int i = 0; i < compressionString.length(); i++) {
            if (compressionString.charAt(i) == '1') {
                bitSet.set(i, true);
            } else {
                bitSet.set(i, false);
            }
        }
        bitSet.set(compressionString.length(), true);
        byte[] outputBytes = bitSet.toByteArray();
        return outputBytes;
    }

    public String getHuffmanTreeMaker() {
        return huffmanTreeMaker;
    }
}
