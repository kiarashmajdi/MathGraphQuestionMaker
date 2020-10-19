import GUIStuff.Welcome;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;


import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.ArrayList;
import java.util.Random;

public class MainClass{
	public static String latexFileText = "";
	
	public static JProgressBar pb; 
	
	public static JLabel label1 = new JLabel(" ");
	
	public static String pathOut = "C:/Users/35012/OneDrive/Desktop/Test10"; //Folder path
	public static String pathIn = "C:/USers/35012/OneDrive/DEsktop/test.csv"; //CSV path
	
	public static String[] ins = new String[] {"Sine", "Cosine", "Polynomial", "Exponential", "Square Root", "Rational"}; //All categories
	public static String[] eqs; //Generrated equations
	public static int[] values; //Generated coefficients
	public static XYSeriesCollection col; //Graphing stuff
	
	public static boolean[] cats; //checkbox booleans
	
	public static void main(String[] args) throws IOException{
		
		Welcome.main(args); //Runs the gui
		 
		cats = Welcome.reportCats(); //get the boolean list
		
		pathIn = Welcome.getInPath(); //get the CSV
		pathOut = Welcome.getOutPath();//get the Folder


		File pdfFolder = new File(pathOut + "/" + "PDFs");//make a new folder called pdfs
		pdfFolder.mkdir();
		pdfFolder.setWritable(true, false);
		
		String[][] students = csvReadStudentList(pathIn);//read the students csv
		
		// create a frame 
        JFrame f = new JFrame("ProgressBar demo"); 
  
        // create a panel 
        JPanel p = new JPanel();
		
		pb = new JProgressBar(SwingConstants.HORIZONTAL, 0, students.length);
		
		pb.setForeground(new Color(0,176,80));
		
		pb.setStringPainted(true);
		
		
		label1.setBounds(200,40,300,60);
		
		pb.add(label1, BorderLayout.CENTER);
		
		pb.setPreferredSize(new Dimension(300, 50));
		
		
		p.add(pb);
		
		f.add(p);
		//f.add(label1);
		
		f.setSize(500 , 120); 
        f.setVisible(true);
		
		makeGraphsForStudents(students);
		

	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////                                         I/O Stuff                                            /////
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static String[][] csvReadStudentList(String filePath) throws IOException {
		
		/*
		 * params: path tp csv file 
		 * outputs: 2d array of the csv
		 * 
		 */

        File csvFile = new File(filePath);
        Scanner csvScanner = new Scanner(csvFile);
        int counter = 0;
        String veryTemp;
        csvScanner.nextLine();
        while (csvScanner.hasNextLine()) {
        	csvScanner.nextLine();
            counter++;
        }
        csvScanner = new Scanner(csvFile);
        String[][] theProcessedArray = new String[counter][3];
        String readString;
        csvScanner.nextLine();
        while (csvScanner.hasNextLine()) {
            readString = csvScanner.nextLine();
            String[] reads = readString.split("," , 4);
            theProcessedArray[theProcessedArray.length - counter] = new String[] {reads[1], reads[2], reads[0]} ;
            counter--;
        }

        return theProcessedArray;

    }
	public static void makeGraphsForStudents(String[][] studentArray) throws IOException {
		
		/*
		 * Purpose: integrate the methods, graphing and IO part:
		 * make the random values, equations, graaphs
		 * chooose 2 graphs by random
		 * put the graphs and stuff in a tex file
		 * make pdfs and copy them in the PDFs folder for all students
		 */
		
		int counter = 0;
		
		for (String[] h: studentArray) {
			pb.setString("Progress: " + h[0] + " " + h[1] + " (" + h[2] + ".pdf) ...");
			pb.setStringPainted(true);
			Random rand = new Random();
			int[] randomThings = new int[] {rand.nextInt(6), rand.nextInt(6)};
			while (cats[randomThings[0]] == false) {
				randomThings[0] = rand.nextInt(6);
			}
			while (cats[randomThings[1]] == false || randomThings[1] == randomThings[0] || (randomThings[0] == 0 && randomThings[1] == 1) || (randomThings[0] == 1 && randomThings[1] == 0)) {
				randomThings[1] = rand.nextInt(6);
			}
			eqs = new String[6];
			values = valueRandomizer();
			eqs[0] = values[0] + " sin(" + values[1] + "(x - " + values[2] + ")) + " + values[3];
			graphSine(h[2]+"1.png", values);
			values = valueRandomizer();
			eqs[1] = values[0] + " cos(" + values[1] + "(x - " + values[2] + ")) + " + values[3];
			graphCose(h[2] + "2.png", values);
			values = valueRandomizer();
			values[1] /= 2;
			values[1] += 1;
			eqs[2] = values[0] + "(x - " + (values[1]) + ") ^ 2 + " + (values[2] - values[0] * ((double)values[1] * (double)values[1]));
			graphPoly(h[2]+"3.png", values);
			values = valueRandomizer();
			int l = 0;
			for (int i = 0; i < 4; i++) {
				if (values[i] > values[l]) {
					l = i;
				}
			}
			eqs[3] = "(" + (l+2) + "^x" + ") + " + values[1];
			graphExp(h[2]+"4.png", values);
			values = valueRandomizer();
			eqs[4] = values[0] + " \\sqrt{" + values[1] + " (x - " + values[2] + ")} + " + values[3];
			graphSqrt(h[2]+"5.png", values);
			
			values = valueRandomizer();
			eqs[5] = "\\frac{" + values[0] + "}{" + values[1] + "(x - " + values[2] + "))} + " + values[3];
			graphRat(h[2]+"6.png", values);
			for (int i = 0; i < eqs.length; i++) {
				eqs[i] = removeNegatives(eqs[i]);
			}
			
			
			latexFileText = "";
			makeLatexFile(h, new String[] {h[2]+(randomThings[0] + 1), h[2]+(randomThings[1] + 1)}, eqs, randomThings);
			File texFile = new File(pathOut + "/"+ h[2] + ".tex");
			texFile.createNewFile();
			PrintWriter pwriter = new PrintWriter(pathOut + "/"+ h[2] + ".tex");
			pwriter.print(latexFileText); 
			pwriter.close();

			Process proc = Runtime.getRuntime().exec(new String[] {"cmd.exe", "/c", "cd " + pathOut + " && pdflatex " + h[2] + ".tex"});

			BufferedReader stdInput = new BufferedReader(new 
			     InputStreamReader(proc.getInputStream()));

			BufferedReader stdError = new BufferedReader(new 
			     InputStreamReader(proc.getErrorStream()));

			// Read the output from the command
			System.out.println("Here is the standard output of the command:\n");
			String s = null;
			while ((s = stdInput.readLine()) != null) {
			    System.out.println(s);
			}

			// Read any errors from the attempted command
			System.out.println("Here is the standard error of the command (if any):\n");
			while ((s = stdError.readLine()) != null) {
			    System.out.println(s);
			}
			System.out.println("*****************************************************************************");
			counter += 1;
			pb.setValue(counter);
		}
		char[] a = pathOut.toCharArray();
		for (int h = 0; h < a.length; h++) {
			if (a[h] == '/') {
				a[h] = '\\';
			}
		}
		pathOut = new String(a);
		for (String[] h: studentArray) {
			Process proc = Runtime.getRuntime().exec(new String[] {"cmd.exe", "/c", "cd " + pathOut + " && copy " + h[2] + ".pdf" + " " + pathOut + "\\PDFs"});
			System.out.println("cd " + pathOut + " && copy " + h[2] + ".pdf" + " " + pathOut + "\\PDFs");
			BufferedReader stdInput = new BufferedReader(new 
			     InputStreamReader(proc.getInputStream()));

			BufferedReader stdError = new BufferedReader(new 
			     InputStreamReader(proc.getErrorStream()));

			// Read the output from the command
			System.out.println("Here is the standard output of the command:\n");
			String s = null;
			while ((s = stdInput.readLine()) != null) {
			    System.out.println(s);
				continue;
			}

			// Read any errors from the attempted command
			System.out.println("Here is the standard error of the command (if any):\n");
			while ((s = stdError.readLine()) != null) {
			    System.out.println(s);
				continue;
			}
			System.out.println("*****************************************************************************");
			
		}
		System.exit(0);
	}
	///////////////////////////////////////////////////////////////////////////
	////                        Randomizing part                           ////
	///////////////////////////////////////////////////////////////////////////
	public static int[] valueRandomizer() {
		
		/*
		 * Make 4 random values
		 * return int array
		 * 
		 * 
		 */
		
		int[] result = new int[4];
		Random rand = new Random();
		int randomSign = 0;


		for (int h = 0; h < 4; h++) {
			
			result[h] = rand.nextInt(6) + 1;
			randomSign = rand.nextInt(2);
			
			if (randomSign == 1) {
				result[h] = -result[h];
			}
			
		}


		return result;
	}
	public static int sign(int num) {
		
		/*
		 * get a number
		 * give -1 if negative, 1 if positive
		 */
		return Math.abs(num) / num;
	}
	//////////////////////////////////////////////////////////////////////////////////
	////                               Graph Drawing                              ////
	//////////////////////////////////////////////////////////////////////////////////
	public static void graphSqrt(String filename, int[] values) throws IOException{
		/*
		 * 
		 * Graph the squareroot function and save it in filename.png
		 * Param: String filename: whatever the png is supposed to be names, int[4] values: randomized transformers
		 * 
		 */
		XYSeries series1 = new XYSeries("First");
		for(double i = values[2] - 10; i <= values[2] + 10 ; i += 0.002){
	        double sinx = values[0] * Math.pow(values[1] * (i - values[2]), 0.5) + values[3];
	        series1.add(i, sinx);
	    }
		col = new XYSeriesCollection();
	    col.addSeries(series1);
	    
	    JFreeChart chart = ChartFactory.createXYLineChart("","", "", col);
	    ChartUtils.saveChartAsPNG(new File(pathOut +"/" + filename), chart, 400, 400);
	}
	public static void graphCose(String filename, int[] values) throws IOException{
		/*
		 * 
		 * Graph the cosine function and save it in filename.png
		 * Param: String filename: whatever the png is supposed to be names, int[4] values: randomized transformers
		 * 
		 */
		XYSeries series1 = new XYSeries("First");
		for(double i = 0; i < Math.abs(2.0 / values[1])*7; i += 0.002){
	        double sinx = values[0] * Math.cos(values[1] * (i - values[2])) + values[3];
	        series1.add(i, sinx);
	    }
		col = new XYSeriesCollection();
	    col.addSeries(series1);
	    
	    JFreeChart chart = ChartFactory.createXYLineChart("","", "", col);
	    ChartUtils.saveChartAsPNG(new File(pathOut +"/" + filename), chart, 400, 400);
	}
	
	public static void graphExp(String filename, int[] values) throws IOException{
		/*
		 * 
		 * Graph the exponential function and save it in filename.png
		 * Param: String filename: whatever the png is supposed to be names, int[4] values: randomized transformers
		 * 
		 */
		XYSeries series1 = new XYSeries("First");
		double maxIndex = 0;
		for (int h = 0; h < 4; h++) {
			if (values[(int)maxIndex] < values[h]){
				maxIndex = h * 1.0;
			}
		}
		for(double i = -2; i < 3.1; i += 0.002){
	        double sinx = Math.pow(maxIndex + 2, i) + values[1];
	        series1.add(i, sinx);
	    }
		col = new XYSeriesCollection();
	    col.addSeries(series1);
	    
	    JFreeChart chart = ChartFactory.createXYLineChart("","", "", col);
	    ChartUtils.saveChartAsPNG(new File(pathOut +"/" + filename), chart, 400, 400);
	}
	public static void graphSine(String filename, int[] values) throws IOException{
		/*
		 * 
		 * Graph the sinus function and save it in filename.png
		 * Param: String filename: whatever the png is supposed to be names, int[4] values: randomized transformers
		 * 
		 */
		XYSeries series1 = new XYSeries("First");
		for(double i = 0; i < Math.abs(2.0 / values[1])*7; i += 0.002){
	        double sinx = values[0] * Math.sin(values[1] * (i - values[2])) + values[3];
	        series1.add(i, sinx);
	    }
		col = new XYSeriesCollection();
	    col.addSeries(series1);
	    
	    JFreeChart chart = ChartFactory.createXYLineChart("","", "", col);
	    ChartUtils.saveChartAsPNG(new File(pathOut +"/" + filename), chart, 400, 400);
	}
	
	public static void graphPoly(String filename, int[] values) throws IOException{
		/*
		 * 
		 * Graph the polynomial function and save it in filename.png
		 * Param: String filename: whatever the png is supposed to be names, int[4] values: randomized transformers
		 * 
		 */
		XYSeries series1 = new XYSeries("First");
		for(double i = values[1] - 4; i < values[1] + 4; i += 0.002){
	        double sinx = values[0] * (i - values[1])*(i - values[1]) + values[2] - values[0] * ((double)values[1] * (double)values[1]);
	        series1.add(i, sinx);
	    }
		col = new XYSeriesCollection();
	    col.addSeries(series1);
	    
	    JFreeChart chart = ChartFactory.createXYLineChart("","", "", col);
	    ChartUtils.saveChartAsPNG(new File(pathOut +"/" + filename), chart, 400, 400);
	}
	public static void graphRat(String filename, int[] values) throws IOException{
		/*
		 * 
		 * Graph the rational function and save it in filename.png
		 * Param: String filename: whatever the png is supposed to be names, int[4] values: randomized transformers
		 * 
		 */
		XYSeries series1 = new XYSeries("First");
		XYSeries series2 = new XYSeries("SECONd");
		for(double i = values[2] - 20; i < values[2]; i += 0.1
				){
			if (i >= values[2] + 0.5 || i <= values[2] - 0.5){
				double sinx = (double)values[0] / (values[1] * (i - values[2])) + values[3];
		        series1.add(i, sinx);
			}
	        
	    }
		for(double i = values[2]; i < values[2] + 20; i += 0.1
				){
			if (i >= values[2] + 0.5 || i <= values[2] -0.5  ){
				double sinx = (double)values[0] / (values[1] * (i - values[2])) + values[3];
		        series2.add(i, sinx);
			}
	        
	    }
		
		col = new XYSeriesCollection();
	    col.addSeries(series1);
	    col.addSeries(series2);
		JFreeChart chart = ChartFactory.createXYLineChart("","", "", col);
	    ChartUtils.saveChartAsPNG(new File(pathOut +"/" + filename), chart, 400, 400);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	//////                           LaTeX Making and Text Analysis                               //////
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public static void makeLatexFile(String[] StudentArray, String[] questions, String[] answers, int[] randomNums) {
		/*
		 * make the context of the tex file, knowing the questions, answers, student's name, lastname, id, and the graphs name
		 * param: string array[3] : name lastname id, String array[] : names of png files, String[] answers: equation strings, int[] randomNums: categories randomized to be in the test
		 * 
		 */
		latexFileText += "\\documentclass[11pt]{article}\n\\usepackage{graphicx}\n\\begin{document}\nFull Name: " + StudentArray[0] + " " + StudentArray[1] + "\n\nID: " + StudentArray[2] + "\n\n";
		int counter = 1;
		for (String h: questions) {
			if (counter != 1) {
				latexFileText += "\\newpage " + counter + ")Find the Equation for the "  + ins[randomNums[1]] + " Function below. " + "\n\n";
			}
			else{
				latexFileText += counter + ")Find the Equation for the "  + ins[randomNums[0]] + " Function below. " + "\n\n";
			}
			latexFileText += "\\includegraphics{{";
			latexFileText += h;
			latexFileText += "}}\n\n\n\n";
			counter ++;
		}
		latexFileText += "\\newpage 1) " + ins[randomNums[0]] + " Function: " + "$f(x) = " + answers[randomNums[0]] + "$\n\n2)"  + ins[randomNums[1]] + " Function: $f(x) = " + answers[randomNums[1]] + "$";
		latexFileText += "\\end{document}";
	}
	public static String removeNegatives(String eq) {
		/*
		 * param: equation string
		 * in case of ++, +-, -+, -- in the equation change it to +, -, -, +		 * 
		 * ret: new equation string
		 */
		String newEq = "";
		char[] eqChar = eq.toCharArray();
		ArrayList<Character> newEqChar = new ArrayList<Character>();
		for (int h = 0; h < eqChar.length; h++) {
			if (eqChar[h] == '-' && eqChar[h+1] == '-') {
				newEqChar.add('+');
				h++;
			}
			else if (eqChar[h] == '-' && eqChar[h+2] == '-') {
				newEqChar.add('+');
				h++;
				h++;
			}
			else if (eqChar[h] == '-' && eqChar[h+1] == '+') {
				newEqChar.add('-');
				h++;
			}
			else if (eqChar[h] == '-' && eqChar[h+2] == '+') {
				newEqChar.add('-');
				h++;
				h++;
			}
			else if (eqChar[h] == '+' && eqChar[h+1] == '-') {
				newEqChar.add('-');
				h++;
			}
			else if (eqChar[h] == '+' && eqChar[h+2] == '-') {
				newEqChar.add('-');
				h++;
				h++;
			}
			else if (eqChar[h] == '+' && eqChar[h+1] == '+') {
				newEqChar.add('+');
				h++;
			}
			else if (eqChar[h] == '+' && eqChar[h+2] == '+') {
				newEqChar.add('+');
				h++;
				h++;
			}
			else {
				newEqChar.add(eqChar[h]);
			}
			
		}
		char[] a = new char[newEqChar.size()];
		for (int h = 0; h < newEqChar.size(); h++) {
			a[h] = newEqChar.get(h);
		}
		newEq = new String(a);
		return newEq;
	}
	
}