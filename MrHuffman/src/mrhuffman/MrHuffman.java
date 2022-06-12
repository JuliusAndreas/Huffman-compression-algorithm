package mrhuffman;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.StringWriter;

/**
 *
 * @author Julius Andreas
 */
public class MrHuffman {

    public static void main(String[] args) throws IOException {
        BufferedOutputStream output = null;
        DataInputStream input = null;
        Compresser compresser = null;
        try {
            File file = new File("../image/3.bmp");
            if (file.canRead()) {
                input = new DataInputStream(new BufferedInputStream(
                        new FileInputStream(file)));
                compresser = new Compresser(input,file);
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
        File outputFile = new File("../compression/compressedFile");
        outputFile.createNewFile();
        try {
            output = new BufferedOutputStream(new FileOutputStream(outputFile));
            output.write(compresser.compress());
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }finally{
            if(output != null){
                output.close();
            }
        }
        File treeFile = new File("../HuffmanTree/tree.txt");
        treeFile.createNewFile();
        FileWriter writer = new FileWriter(treeFile);
        String result = compresser.getHuffmanTreeMaker();
        result += "\n";
        writer.write(result);
        writer.close();
    
    }

}
