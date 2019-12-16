package com.yilmazakkan;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


 class FindMaxSum {
    public static int[][] numberArray = new int[15][15];

    public static int findMaxSum(String fileName, ArrayList<Location> path) {

        readNumberFromFile(fileName, numberArray);
        if (prime(numberArray[0][0])) {
            System.out.println("First number is Prime !!!");
            return 0;
        } else {
            return findMaxSumOprt(0, 0, 15, path);
        }

    }

     //asallık durumu
     private static boolean prime(int number) {

         for (int i = 2; i < number; ++i) {
             if ((number % i) == 0) {
                 return false;
             }
         }
         return true;
     }

     //arraylerin liste lokasyonları için
     private static class Location {
         private int x;
         private int y;
         private int number;

         public Location(int x, int y, int number) {
             this.x = x;
             this.y = y;
             this.number = number;

         }

         public int getX() {
             return x;
         }

         public int getY() {
             return y;
         }

         public int getNumber() {
             return number;
         }
     }

    //Sayıların asallıklarımın kontolü ve path'de ilerleme
    public static int findMaxSumOprt(int x, int y, int size, ArrayList<Location> path) {

        int totalLeft = 0, totalRight = 0;

        ArrayList<Location> tempListLeft = new ArrayList<>();
        ArrayList<Location> tempListRight = new ArrayList<>();

        if (x >= size) {
            return 0;
        } else if (x == (size - 1)) {
            path.add(new Location(x, y, numberArray[x][y]));
            return numberArray[x][y];
        }

        if (!prime(numberArray[x + 1][y])) {
            totalRight = findMaxSumOprt(x + 1, y, size, tempListRight);
        }

        if (!prime(numberArray[x + 1][y + 1])) {
            totalLeft = findMaxSumOprt(x + 1, y + 1, size, tempListLeft);
        }

        // asal olma durumu
        if (prime(numberArray[x + 1][y + 1]) && prime(numberArray[x + 1][y]))
            return Integer.MIN_VALUE;

        if (totalLeft > totalRight) {
            path.addAll(tempListLeft);
            path.add(new Location(x, y, numberArray[x][y]));
            return totalLeft + numberArray[x][y];
        } else {
            path.addAll(tempListRight);
            path.add(new Location(x, y, numberArray[x][y]));
            return totalRight + numberArray[x][y];
        }
    }



//    piramitdeki sayıları text dosyasından okutma.
    public static void readNumberFromFile(String fileName, int[][] numberArray) {

        try {

            InputStream inputStream = new FileInputStream(fileName);

            Scanner scanner = new Scanner(inputStream);
            int counter = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                //reges \\D rakam dışındaki bir şeyle eşleşirse
                String[] numbers = line.split("\\D+");
                for (int i= 0; i < numbers.length; ++i) {
                    numberArray[counter][i] = Integer.parseInt(numbers[i]);

                }
                ++counter;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            java.lang.System.exit(1);
        }
    }
    public static void main(String[] args) {


        ArrayList<Location> path = new ArrayList<>();

       findMaxSum("pyramid.txt", path);

        System.out.println("\nMax Sum Path ");
        for (int i = path.size() - 1; i >= 0; --i) {
            System.out.print(path.get(i).number + " > ");
        }

        System.out.printf(">>>> Max Sum = %d \n", findMaxSum("pyramid.txt", path));

    }
}



