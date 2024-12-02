package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class contains methods which, when used together, perform the
 * entire Huffman Coding encoding and decoding process
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class HuffmanCoding {
    private String fileName;
    private ArrayList<CharFreq> sortedCharFreqList;
    private TreeNode huffmanRoot;
    private String[] encodings;

    /**
     * Constructor used by the driver, sets filename
     * DO NOT EDIT
     * @param f The file we want to encode
     */
    public HuffmanCoding(String f) { 
        fileName = f; 
    }

    /**
     * Reads from filename character by character, and sets sortedCharFreqList
     * to a new ArrayList of CharFreq objects with frequency > 0, sorted by frequency
     */
    public void makeSortedList() {

        StdIn.setFile(fileName);

        sortedCharFreqList = new ArrayList<>();

        int[] numberOfOccurrences = new int[128];

        int sizeOfInput = 0;

        // fill numberOfOccurrences and keep count of sizeOfInput
        while (StdIn.hasNextChar()) {
            char currentChar = StdIn.readChar();
            numberOfOccurrences[currentChar]++;
            sizeOfInput++;
        }

        // adds to sortedCharFreqList ONLY if numberOfOccurrences > 0
        for (int i = 0; i < numberOfOccurrences.length; i++) {
            if (numberOfOccurrences[i] > 0) {
                sortedCharFreqList.add(new CharFreq((char)i, (double)numberOfOccurrences[i]/sizeOfInput));
            }
        }

        // if sortedCharFreqList is size 1, must add a character that is 1 ASCII value over the only character in the list
        // if only character in list is the 127th ASCII value, must wrap around (use modulo, like in Josephus Problem)
        if (sortedCharFreqList.size() == 1) {
            CharFreq edgeChar = new CharFreq((char)((sortedCharFreqList.get(0).getCharacter() + 1) % 128), 0.0);
            sortedCharFreqList.add(edgeChar);
        }
        
        // ArrayList is now properly filled out; use Collections.sort()
        Collections.sort(sortedCharFreqList);

    }

    /**
     * Uses sortedCharFreqList to build a huffman coding tree, and stores its root
     * in huffmanRoot
     */
    public void makeTree() {
        
        // initialize source queue and target queue
        Queue<TreeNode> source = new Queue<>();
        Queue<TreeNode> target = new Queue<>();

 
        // iterate through sortedCharFreqList, enqueuing each iteration of sortedCharFreqList into source queue
        // it's already sorted, so no need to do anything other than enqueue
        for (CharFreq ele : sortedCharFreqList) {
            TreeNode newTreeNode = new TreeNode(ele, null, null);
            source.enqueue(newTreeNode);
        }

        // build the Huffman Tree using the following conditions:
        // while (!source.isEmpty() && target.size() != 1)
        //      1. dequeue from the front node of either source or target the TreeNode with lowest probOcc
        //          if they are both the same probOcc, dequeue from Source first
        //          then, enqueue the TreeNode with second lowest probOcc
        //      2. create an insertDequeuedNodes whose character is null and whos probOcc is the sum of both dequeued TreeNodes 
        //          to the left of insertDequeuedNodes, insert the TreeNode with lowest probOcc
        //          to the right of insertDequeudNodes, insert the TreeNode with second lowest probOcc
        //      3. enqueue this TreeNode into the target queue
        while (!source.isEmpty() || target.size() != 1) {

            // initialize the queue to put in
            TreeNode insertDequeuedNodes = new TreeNode(null, null, null);

            // these two will be the TreeNodes that are inserted to the left or right of insertDequeuedNodes
            TreeNode insertLeft;
            TreeNode insertRight;

            // find if insertLeft will come from source or target
            // if source or target is empty, set comparison value to 1 to ensure that we insertLeft the nonempty option
            double leftProbSource;
            double leftProbTarget;

            if (!source.isEmpty()) {
                leftProbSource = source.peek().getData().getProbOcc();
            } else leftProbSource = 1;       

            if (!target.isEmpty()) {
                leftProbTarget = target.peek().getData().getProbOcc();
            } else leftProbTarget = 1;

            // compare the two
            if (leftProbSource > leftProbTarget) {
                insertLeft = target.dequeue();
            } else insertLeft = source.dequeue();

            // find if insertRight will come from source or target
            // if source or target is empty, use Double.MAX_VALUE to ensure that we insertRight the nonempty option
            double rightProbSource;
            double rightProbTarget;

            if (!source.isEmpty()) {
                rightProbSource = source.peek().getData().getProbOcc();
            } else rightProbSource = 1;

            if (!target.isEmpty()) {
                rightProbTarget = target.peek().getData().getProbOcc();
            } else rightProbTarget = 1;

            // compare the two
            if (rightProbSource > rightProbTarget) {
                insertRight = target.dequeue();
            } else insertRight = source.dequeue();

            insertDequeuedNodes.setData(new CharFreq(null, insertLeft.getData().getProbOcc() + insertRight.getData().getProbOcc()));
            insertDequeuedNodes.setLeft(insertLeft);
            insertDequeuedNodes.setRight(insertRight);

            // enqueue into target insertDequeuedNodes
            target.enqueue(insertDequeuedNodes);

        }

        // store the root of our Huffman Tree into huffmanRoot
        huffmanRoot = target.dequeue();    
    
    }

    /**
     * Uses huffmanRoot to create a string array of size 128, where each
     * index in the array contains that ASCII character's bitstring encoding. Characters not
     * present in the huffman coding tree should have their spots in the array left null.
     * Set encodings to this array.
     */
    public void makeEncodings() {

        encodings = new String[128];

        leafEncodings(huffmanRoot, "");

    }

    /**
     * Use inorder traversal on the Huffman Tree to calculate encodings of leaf nodes
     * Note that only LEAF nodes will have an encoding
     * @param root is the root of the Huffman Tree
     * @param currentEncoding is the String that represents the current encoding. currentEncoding is 
     *                        concatenated with a "0" on left traversal, and "1" on right traversal
     */ 
    private void leafEncodings(TreeNode root, String currentEncoding) {

        if (root.getLeft() == null && root.getRight() == null) {
            encodings[root.getData().getCharacter()] = currentEncoding;
            return;
        }

        leafEncodings(root.getLeft(), currentEncoding + "0");
        leafEncodings(root.getRight(), currentEncoding + "1");
        
    }

    /**
     * Using encodings and filename, this method makes use of the writeBitString method
     * to write the final encoding of 1's and 0's to the encoded file.
     * 
     * @param encodedFile The file name into which the text file is to be encoded
     */
    public void encode(String encodedFile) {

        StdIn.setFile(fileName);

        String toBeEncoded = new String();
        
        while (StdIn.hasNextChar()) {
            toBeEncoded += encodings[StdIn.readChar()];
        }

        writeBitString(encodedFile, toBeEncoded);

    }
    
    /**
     * Writes a given string of 1's and 0's to the given file byte by byte
     * and NOT as characters of 1 and 0 which take up 8 bits each
     * DO NOT EDIT
     * 
     * @param filename The file to write to (doesn't need to exist yet)
     * @param bitString The string of 1's and 0's to write to the file in bits
     */
    public static void writeBitString(String filename, String bitString) {
        byte[] bytes = new byte[bitString.length() / 8 + 1];
        int bytesIndex = 0, byteIndex = 0, currentByte = 0;

        // Pad the string with initial zeroes and then a one in order to bring
        // its length to a multiple of 8. When reading, the 1 signifies the
        // end of padding.
        int padding = 8 - (bitString.length() % 8);
        String pad = "";
        for (int i = 0; i < padding-1; i++) pad = pad + "0";
        pad = pad + "1";
        bitString = pad + bitString;

        // For every bit, add it to the right spot in the corresponding byte,
        // and store bytes in the array when finished
        for (char c : bitString.toCharArray()) {
            if (c != '1' && c != '0') {
                System.out.println("Invalid characters in bitstring");
                return;
            }

            if (c == '1') currentByte += 1 << (7-byteIndex);
            byteIndex++;
            
            if (byteIndex == 8) {
                bytes[bytesIndex] = (byte) currentByte;
                bytesIndex++;
                currentByte = 0;
                byteIndex = 0;
            }
        }
        
        // Write the array of bytes to the provided file
        try {
            FileOutputStream out = new FileOutputStream(filename);
            out.write(bytes);
            out.close();
        }
        catch(Exception e) {
            System.err.println("Error when writing to file!");
        }
    }

    /**
     * Using a given encoded file name, this method makes use of the readBitString method 
     * to convert the file into a bit string, then decodes the bit string using the 
     * tree, and writes it to a decoded file. 
     * 
     * @param encodedFile The file which has already been encoded by encode()
     * @param decodedFile The name of the new file we want to decode into
     */
    public void decode(String encodedFile, String decodedFile) {

        // ALGORITHM:
        // Traverse through each character in encodedFile (a bit string of 0s and 1s)
        // If 0, then traverse left on the Huffman Tree
        // If 1, then traverse right on the Huffman Tree
        //      If leaf node, concatenate the leaf node's character to decodedBitString
        //      Then, reset root to the top of the tree, as you have to decode from the beginning again

        StdOut.setFile(decodedFile);

        char[] encodedBitString = readBitString(encodedFile).toCharArray();
        String decodedBitString = "";
        TreeNode root = huffmanRoot;
    
        for (int i = 0; i < encodedBitString.length; i++) {
            char current = encodedBitString[i];
            if (current == '0') root = root.getLeft();
            if (current == '1') root = root.getRight();

            if (root.getLeft() == null && root.getRight() == null) {
                decodedBitString += root.getData().getCharacter();
                root = huffmanRoot;
            }
        }

        StdOut.print(decodedBitString);

    }

    /**
     * Reads a given file byte by byte, and returns a string of 1's and 0's
     * representing the bits in the file
     * DO NOT EDIT
     * 
     * @param filename The encoded file to read from
     * @return String of 1's and 0's representing the bits in the file
     */
    public static String readBitString(String filename) {
        String bitString = "";
        
        try {
            FileInputStream in = new FileInputStream(filename);
            File file = new File(filename);

            byte bytes[] = new byte[(int) file.length()];
            in.read(bytes);
            in.close();
            
            // For each byte read, convert it to a binary string of length 8 and add it
            // to the bit string
            for (byte b : bytes) {
                bitString = bitString + 
                String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            }

            // Detect the first 1 signifying the end of padding, then remove the first few
            // characters, including the 1
            for (int i = 0; i < 8; i++) {
                if (bitString.charAt(i) == '1') return bitString.substring(i+1);
            }
            
            return bitString.substring(8);
        }
        catch(Exception e) {
            System.out.println("Error while reading file!");
            return "";
        }
    }

    /*
     * Getters used by the driver. 
     * DO NOT EDIT or REMOVE
     */

    public String getFileName() { 
        return fileName; 
    }

    public ArrayList<CharFreq> getSortedCharFreqList() { 
        return sortedCharFreqList; 
    }

    public TreeNode getHuffmanRoot() { 
        return huffmanRoot; 
    }

    public String[] getEncodings() { 
        return encodings; 
    }
}
