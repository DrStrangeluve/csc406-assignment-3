import java.io.*;
import java.math.BigDecimal;
import java.util.Scanner;

public class OBSTKnappSection1 {
    private Writer writer;
    private int num_keys;
    private String[] keys;
    private BigDecimal[] probabilities;
    private BigDecimal[][] cost_root_matrix;

    OBSTKnappSection1(File dataFile){
        computeCostRootMatrix(dataFile);
    }

    private void createOutputFile(String fileName) {
        try {
            writer = new PrintWriter(fileName);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void write(String s) {
        try {
            writer.write(s);
            writer.flush();

        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeArrays(File dataFile){
        try {
            Scanner in = new Scanner(dataFile);
            while (in.findInLine("c ") != null) {
                in.nextLine();
            }
            num_keys = in.nextInt();
            keys = new String[num_keys+1];
            for (int i = 1; i <= num_keys; i++){
                keys[i] = in.next();
            }
            probabilities = new BigDecimal[num_keys+1];
            for (int i = 1; i <= num_keys; i++){
                probabilities[i] = parseBigDecial(in.next());
            }
            cost_root_matrix = new BigDecimal[num_keys+2][num_keys+1];
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    private BigDecimal parseBigDecial(String next) {
        if (next.contains("/")){
            String[] fraction = next.split("/");
            return new BigDecimal(Double.parseDouble(fraction[0]) / Double.parseDouble(fraction[1]));
        }
        else {
            return new BigDecimal(next);
        }
    }

    private void computeCostRootMatrix(File dataFile){
        createOutputFile("obst_output.txt");
        initializeArrays(dataFile);
        for (int i = 1; i <= num_keys; i++){
            cost_root_matrix[i][i] = probabilities[i];
            cost_root_matrix[i+1][i-1] = new BigDecimal(i).setScale(0, BigDecimal.ROUND_HALF_EVEN);
            cost_root_matrix[i][i-1] = new BigDecimal(0);
        }
        cost_root_matrix[num_keys+1][num_keys] =new BigDecimal(0);
        for (int d = 1; d < num_keys; d++){
            for (int i = 1; i <= (num_keys - d); i++){
                int j = i + d;
                int minK = 0;
                BigDecimal sumOfProb = new BigDecimal(0);
                BigDecimal tempMin = new BigDecimal(Double.MAX_VALUE);
                for (int k = i; k <= j; k++){
                    sumOfProb = sumOfProb.add(probabilities[k]);
                    BigDecimal c = new BigDecimal(cost_root_matrix[i][k-1].doubleValue() + cost_root_matrix[k+1][j].doubleValue());
                    if (tempMin.compareTo(c) > 0){
                        tempMin = c;
                        minK = k;
                    }
                }
                cost_root_matrix[i][j] = new BigDecimal(tempMin.doubleValue() + sumOfProb.doubleValue());
                cost_root_matrix[j+1][i-1] = new BigDecimal(minK).setScale(0, BigDecimal.ROUND_HALF_EVEN);
            }
        }
        matrixToString(cost_root_matrix);
        WeightedQuickUnionPathCompression uf = new WeightedQuickUnionPathCompression(num_keys);
        matrixToParenthesizedExp(num_keys+1, 0, uf); // start at the bottom left corner of matrix, which is the root
    }

    private void matrixToParenthesizedExp(int i, int j, WeightedQuickUnion uf){
        if (num_keys > 0) {
            int start_i = i;
            int start_j = j;
            write(cost_root_matrix[i][j].toString());
            num_keys--;
            if (num_keys > 0) {
                while (cost_root_matrix[i][j].intValue() == cost_root_matrix[start_i][start_j].intValue()) {
                    j++;
                }
                findSubtree(i, j, uf, start_i, start_j);
                i = start_i;
                j = start_j;
                while (cost_root_matrix[i][j].intValue() == cost_root_matrix[start_i][start_j].intValue()) {
                    i--;
                }
                findSubtree(i, j, uf, start_i, start_j);
            }
        }
    }

    private void findSubtree(int i, int j, WeightedQuickUnion uf, int start_i, int start_j) {
        if (cost_root_matrix[i][j].compareTo(new BigDecimal(0)) > 0) {
            if (uf.union(cost_root_matrix[start_i][start_j].intValue(), cost_root_matrix[i][j].intValue())) {
                write("(");
                matrixToParenthesizedExp(i, j, uf);
                write(")");
            }
        }
    }

    private void matrixToString(BigDecimal[][] matrix){
        int counter = 0;
        for(BigDecimal[] row : matrix) {
            if (counter != 0){
                printRow(row);
            }
            counter++;
        }
    }

    private void printRow(BigDecimal[] row) {
        for (BigDecimal i : row) {
            if (i.scale() <= 0){
                write(i + "\t");
            }
            else {
                write(i.setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());
            }
            write("\t");
        }
        write("\n");
    }

    public static void main(String[] args){
        if (args.length != 1) {
            System.out.println("Usage: <fullFilePath>");
            System.exit(1);
        }
        else {
            new OBSTKnappSection1(new File(args[0]));
        }
    }
}