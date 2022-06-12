package mrhuffman;

/**
 *
 * @author Julius Andreas
 */
public class Words {

    static byte[] allBytes;
    static Node[] allNodes;
    
    public static void createArrays(int numberOfBytes){
        allBytes = new byte[numberOfBytes];
        allNodes = new Node[numberOfBytes];
    }
}
