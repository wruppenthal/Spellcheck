/*
 * Created by WilliamRuppenthal on 12/4/15.
 */
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;


public class Main {

    static String[] words;
    static ArrayList<String> wrongs=new ArrayList<String>();


    public static void main(String[] args) {
        //Read words into dictionary array
        String strLine="error";
        words = new String[109582];
        try {
            FileInputStream in = new FileInputStream("wordsEn.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            while ((strLine = br.readLine()) != null) {
                words[0]=strLine;
                for (int c=1;c<words.length;c++){
                    words[c]=br.readLine();
                }
            }
            strLine="error";
            in=new FileInputStream("DankText.txt");
            br=new BufferedReader((new InputStreamReader(in)));
            while ((strLine = br.readLine()) != null) {
                wrongs.add(strLine);
            }

            in.close();
        }catch(Exception e){
            System.out.println("Failed to read file");
            return;
        }



        //Start actual checking
        final long startTime = System.currentTimeMillis();
        Scanner scan=new Scanner(System.in);

        boolean go=true;
        for(int i=0;i<wrongs.size();i++) {
            System.out.println("Word to correct: "+wrongs.get(i));

            double dif=100;
            String poss="";

            for(int c=0;c<words.length;c++){
                double nd=101;
                if(wrongs.get(i)!=null&&words[c]!=null)
                    nd=dijkstra(wrongs.get(i),words[c]);

                if(nd<dif) {
                    poss=words[c];
                    dif = nd;
                }
                if(dif==-5)
                    break;
            }

            System.out.println("Suggestion:");
            System.out.println(poss);

        }
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Time elapsed in millis: "+totalTime);
    }

    //Get the dijkstra distance between two strings
    private static double dijkstra(String og,String test){
        //Set up variables
        if(og.equals(test))
            return-5;
        else if(Math.abs(og.length()-test.length())>2)
            return 15;


        double[][] graph=new double[og.length()+1][test.length()+1];
        graph[0][0]=0;

        for(int row=0;row<graph.length;row++)
            for (int col=0;col<graph[0].length;col++)
                graph[row][col]=getVal(row,col,graph,og,test);

//        String newstr="";
//        for(int c=0;c<og.length()-1;c++){
//            for(int i=0;i<c;i++)
//                newstr+=og.charAt(i);
//            newstr+=og.charAt(c+1);
//            newstr+=og.charAt(c);
//            for(int i=c+2;i<og.length();i++)
//                newstr+=og.charAt(i);
//            if(newstr.equals(test))
//                return 0.5;
//            else
//                newstr="";
//        }


        return graph[og.length()][test.length()];
    }

    private static double getVal(int row,int col,double[][]graph,String og,String test){
        if(row==0&&col==0)
            return 0;
        else if(row==0)
            return graph[0][col-1]+1;
        else if(col==0)
            return graph[row-1][0]+1;
        else{
            double val;
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