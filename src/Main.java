/*
 * Created by WilliamRuppenthal on 12/4/15.
 */
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;


public class Main {

    static String[] words;
    static String[] wrongs;


    public static void main(String[] args) {
        //Read words into dictionary array
        String strLine;
        words = new String[109582];
        wrongs = new String[69];
        try {
            FileInputStream in = new FileInputStream("wordsEn.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            while ((strLine = br.readLine()) != null) {
                for (int c=0;c<words.length;c++){
                    words[c]=br.readLine();
                }
            }

            in=new FileInputStream("Dank list");
            br=new BufferedReader((new InputStreamReader(in)));
            while ((strLine = br.readLine()) != null) {
                for (int c=0;c<wrongs.length;c++){
                    wrongs[c]=br.readLine();
                }
            }

            in.close();
        }catch(Exception e){
            System.out.println("Failed to read file");
            return;
        }

        Scanner scan=new Scanner(System.in);

        boolean go=true;
        for(int c=0;c<wrongs.length;c++) {
            System.out.println("Word to correct: "+wrongs[c]);

            int dif=100;
            ArrayList<String> poss=new ArrayList<String>(); //List of possibles
            final long startTime = System.currentTimeMillis();
            for(int c=0;c<words.length;c++){
                int nd=101;
                if(wrongs[c]!=null&&words[c]!=null)
                    nd=dijkstra(wrongs[c],words[c]);
                if(nd<dif) {
                    poss.clear();
                    dif = nd;
                    poss.add(words[c]);
                }
                else if(nd==dif)
                    poss.add(words[c]);
            }

            System.out.println("Suggestion(s):");
            long endTime   = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            System.out.println("Time elapsed in millis: "+totalTime);
            for(String s:poss)
                System.out.println(s);

        }
    }

    //Get the dijkstra distance between two strings
    private static int dijkstra(String og,String test){
        //Set up variables

        int[][] graph=new int[og.length()+1][test.length()+1];
        graph[0][0]=0;

        for(int row=0;row<graph.length;row++)
            for (int col=0;col<graph[0].length;col++)
                graph[row][col]=getVal(row,col,graph,og,test);

        return graph[og.length()][test.length()];
    }

    private static int getVal(int row,int col,int[][]graph,String og,String test){
        if(row==0&&col==0)
            return 0;
        else if(row==0)
            return graph[0][col-1]+1;
        else if(col==0)
            return graph[row-1][0]+1;
        else{
            int val;
            if(og.charAt(row-1) == test.charAt(col-1))
                if(row==1&&col==1)
                    val=-1;
                else
                    val=0;
            else
                val=1;

            return Math.min(graph[row-1][col-1],Math.min(graph[row-1][col],graph[row][col-1]))+val;
            }
    }
}