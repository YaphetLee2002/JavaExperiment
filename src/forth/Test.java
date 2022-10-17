package forth;

import forth.KnnNumber;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        String trainingDataPath;
        String testDataPath;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your training data's full path: ");
        trainingDataPath = scanner.nextLine();
        System.out.println("Enter your test data's full path: ");
        testDataPath = scanner.nextLine();

        KnnNumber knn = new KnnNumber();
        knn.createTrainingDataSet(trainingDataPath);
        knn.tagIdentify(testDataPath);

    }
}