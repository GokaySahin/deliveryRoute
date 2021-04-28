/**
 * 041901032 Gökay_Şahin
 */

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;
import java.util.function.DoubleBinaryOperator;

public class Gökay_Şahin {
    public static void main(String args[]) throws FileNotFoundException {
        String dataFileName = "data3.txt";
        File dataBase = new File(dataFileName);
        ArrayList<Place> readedPlaces = fileReader(dataBase);
        ArrayList<Place> route = routeFinder(readedPlaces);
        routePrinter(route);
    }

    public static ArrayList<Place> fileReader(File dataBase) throws FileNotFoundException {
        ArrayList<Place> input_readPlaces = new ArrayList<>();
        Place temp = new Place(1, 0, 0, 0);
        input_readPlaces.add(temp);
        Scanner scanner = new Scanner(dataBase);
        int lineNumber = 1;
        while (scanner.hasNextLine()) {
            String wholeData = scanner.nextLine();
            String[] wholeDataArray = wholeData.split(",");
            double input_x = Double.parseDouble(wholeDataArray[0]);
            double input_y = Double.parseDouble(wholeDataArray[1]);
            int input_type = 0;
            if (wholeData.contains("Migros")) input_type = 1;
            Place createdPlace = new Place(input_type, input_x, input_y, lineNumber);
            lineNumber++;
            if (input_type == 1) {
                input_readPlaces.set(0, createdPlace);
            } else {
                input_readPlaces.add(createdPlace);
            }
        }
        scanner.close();
        return input_readPlaces;
    }

    public static double distanceCalculator(Place input_PlaceOne, Place input_PlaceTwo) {
        double firstX = input_PlaceOne.x;
        double firstY = input_PlaceOne.y;
        double secondX = input_PlaceOne.x;
        double secondY = input_PlaceTwo.y;
        double distance = Math.sqrt((Math.pow((input_PlaceTwo.x - input_PlaceOne.x), 2)) + Math.pow((input_PlaceTwo.y - input_PlaceOne.y), 2));
        return distance;

    }

    public static ArrayList<Place> routeFinder(ArrayList<Place> input_readPlaces) {
        ArrayList<Place> migrosList = new ArrayList<>();
        ArrayList<Place> correctList = new ArrayList<>();
        for (int i = 0; i < input_readPlaces.size()+1; i++) {
            Place tempPlace = new Place();
            correctList.add(tempPlace);
        }
        migrosList.add(input_readPlaces.get(0));
        input_readPlaces.remove(0);
        Collections.shuffle(input_readPlaces);
        input_readPlaces.add(0, migrosList.get(0));
        input_readPlaces.add(migrosList.get(0));
        double minDistance = Double.MAX_VALUE;
        int n = 0;
        while (n < 100000000) {
            double totalDist = 0;
            for (int i = 0; i < input_readPlaces.size() - 1; i++) {
                totalDist += distanceCalculator(input_readPlaces.get(i), input_readPlaces.get(i + 1));
            }
            if (Double.compare(totalDist, minDistance) <= 0) {
                minDistance = totalDist;
                for (int i = 0; i <input_readPlaces.size() ; i++) {
                    correctList.set(i,input_readPlaces.get(i));
                }
                for (int i = input_readPlaces.size()-1; i > -1 ; i--) {
                    if (input_readPlaces.get(i).placeType == 1) {
                        input_readPlaces.remove(i);
                    }
                }
                Collections.shuffle(input_readPlaces);
                n++;
                input_readPlaces.add(0, migrosList.get(0));
                input_readPlaces.add(migrosList.get(0));
            }
            else {
                 n++;
                for (int i = input_readPlaces.size()-1; i > -1 ; i--) {
                    if (input_readPlaces.get(i).placeType == 1) {
                        input_readPlaces.remove(i);
                    }
                }
                Collections.shuffle(input_readPlaces);
                n++;
                input_readPlaces.add(0, migrosList.get(0));
                input_readPlaces.add(migrosList.get(0));
            }
        }
        return correctList;
    }

    public static void routePrinter(ArrayList<Place> inputSortedHouses) {
        double totalDistance = 0;
        StdDraw.setCanvasSize(1024, 768);
        StdDraw.setXscale(-1.4, 1.4);
        StdDraw.setYscale(-1.4, 1.4);
        for (int i = 0; i < inputSortedHouses.size(); i++) {
            if (inputSortedHouses.get(i).placeType == 1) {
                StdDraw.setPenColor(Color.BLUE);
                StdDraw.filledCircle(inputSortedHouses.get(i).x, inputSortedHouses.get(i).y, 0.04);
                StdDraw.setPenColor(Color.YELLOW);
                StdDraw.text(inputSortedHouses.get(i).x, inputSortedHouses.get(i).y, Integer.toString(inputSortedHouses.get(i).lineNumber));
            } else {
                StdDraw.setPenColor(Color.BLACK);
                StdDraw.filledCircle(inputSortedHouses.get(i).x, inputSortedHouses.get(i).y, 0.04);
                StdDraw.setPenColor(Color.YELLOW);
                StdDraw.text(inputSortedHouses.get(i).x, inputSortedHouses.get(i).y, Integer.toString(inputSortedHouses.get(i).lineNumber));
            }
        }
        System.out.print("Best Path : [");
        for (int i = 0; i < inputSortedHouses.size() - 1; i++) {
            try {
                StdDraw.setPenColor(Color.MAGENTA);
                StdDraw.line(inputSortedHouses.get(i).x, inputSortedHouses.get(i).y, inputSortedHouses.get(i + 1).x, inputSortedHouses.get(i + 1).y);

            } catch (IndexOutOfBoundsException e) {
            }
            totalDistance += distanceCalculator(inputSortedHouses.get(i), inputSortedHouses.get(i + 1));
        }
        for (int i = 0; i < inputSortedHouses.size(); i++) {
            System.out.print(inputSortedHouses.get(i).lineNumber + ", ");
        }
        System.out.print("]");
        System.out.println("");
        System.out.println("Distance : " + totalDistance);

        StdDraw.show();
    }
}

