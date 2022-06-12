package reversemrhuffman;

public class Node implements Comparable<Node> {

    public Node(byte byteCode) {
        this.iAmDummy = false;
        this.byteCode = byteCode;
    }

    public Node() {
        this.iAmDummy = true;
        this.byteCode = 0;
    }

    private byte byteCode;
    private Node parent;
    private Node rLink;
    private Node lLink;
    private int frequency;
    private String huffmanCode;
    private final boolean iAmDummy;
    private int myFirstAppearance;
    public static Node root;
    
    public static void setRoot(Node root){
        Node.root = root;
    }

    public Node getrLink() {
        return rLink;
    }

    public void setrLink(Node rLink) {
        this.rLink = rLink;
    }

    public Node getlLink() {
        return lLink;
    }

    public void setlLink(Node lLink) {
        this.lLink = lLink;
    }

    public int getFrequency() {
        return frequency;
    }

    public void incFrequency() {
        this.frequency++;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public byte getByteCode() {
        return byteCode;
    }

    public void setByteCode(byte byteCode) {
        this.byteCode = byteCode;
    }

    @Override
    public int compareTo(Node o) {
        if (this.frequency < o.getFrequency()) {
            return -1;
        } else if (this.frequency > o.getFrequency()) {
            return 1;
        } else {
            return 0;
        }
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public String getHuffmanCode() {
        return huffmanCode;
    }

    public void formHuffmanCode(String plusHuffmanCode) {
        if (this.huffmanCode == null) {
            this.huffmanCode = plusHuffmanCode;
        } else {
            this.huffmanCode = plusHuffmanCode + this.huffmanCode;
        }
        if (this.getlLink() != null) {
            this.getlLink().formHuffmanCode(plusHuffmanCode);
        }
        if (this.getrLink() != null) {
            this.getrLink().formHuffmanCode(plusHuffmanCode);
        }
    }

    public int getMyFirstAppearance() {
        return myFirstAppearance;
    }

    public void setMyFirstAppearance(int myFirstAppearance) {
        this.myFirstAppearance = myFirstAppearance;
    }

}
