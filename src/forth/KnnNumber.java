package forth;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * This programme show the use of K-nearest neighbor algorithm
 * @version 1.0 2022-4-8
 * @author 1120202469 李亚赟
 */

public class KnnNumber {

    private static final int TOTAL_LENGTH = 40;

    private int trainNum; // The amount of training data
    private int testNum; // The amount of test data

    private int[][] trainData; // Store all the training data, trainData[i][j] represents the number i file and the number j training data code
    private int[] trainLabel; // Store all the training label for checking the forecast label


    /**
     * Turn the training data or test data to one dimension vector
     * @param filename the source data file
     * @return the one dimension vector after transferring
     */
    public int[] initData(String filename)
    {
        int[] ints = new int[1024];
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            char[] s = new char[34];
            for (int i = 0; i < 32; i++) {
                if(bufferedReader.read(s, 0, 34) != -1) {
                    for (int j = 0; j < 32; j++) {
                        ints[i * 32 + j] = s[j] - '0';
                    }
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return ints;
    }

    /**
     * Get the data's right label
     * @param filename the source data
     * @return the right label
     */
    public int getRightLabel(String filename) {
        String[] strings = filename.split("_");
        return Integer.parseInt(strings[0]);
    }

    public void createTrainingDataSet(String filename) {
        File file = new File(filename);
        String[] trainingFileList = file.list();

        assert trainingFileList != null;
        int l = trainingFileList.length;

        trainData = new int[l][1024];
        trainLabel = new int[l];
        trainNum = l;

        for (int i = 0; i < l; i++) {
            trainLabel[i] = getRightLabel(trainingFileList[i]);
            trainData[i] = initData(filename + "\\" + trainingFileList[i]);
        }
    }


    /**
     * The main part of K-nearest neighbor algorithm
     * @param testData The data waiting for test
     * @param k Top k most similar data in K-nearest neighbor algorithm
     * @return
     */
    public int knn(int[] testData, int k) {
        int[][] expandTestData = new int[trainNum][1024];

        // Collect all the test data, resize the array
        for (int i = 0; i < trainNum; i++)
            System.arraycopy(testData, 0, expandTestData[i], 0, 1024);

        // Test matrix minus training matrix, then square
        for (int i = 0; i < trainNum; i++) {
            for (int j = 0; j < 1024; j++)
                expandTestData[i][j] = Math.abs(expandTestData[i][j] - trainData[i][j]);
        }

        // distance[i][0] represents the diversity between test data and training data, distance[i][1] is the number of test number
        int[][] distance = new int[trainNum][2];
        for (int i = 0; i < trainNum; i++) {
            int temp = 0;
            for (int j = 0; j < 1024; j++)
                temp += expandTestData[i][j];
            distance[i][0] = temp;
            distance[i][1] = i;
        }

        // Rank the list by the rise order of diversity, which has been stored at distance[i][0]
        Arrays.sort(distance, Comparator.comparingDouble(a -> a[0]));

        int[] Race = new int[10];
        Arrays.fill(Race, 0);

        // Get the k most similar tags
        for (int i = 0; i < k; i++) {
            int label = trainLabel[distance[i][1]];
            Race[label]++;
        }
        int max = 0, maxi = 0;
        for (int i = 0; i < 10; i++)
            if (Race[i] > max) {
                max = Race[i];
                maxi = i;
            }
        return maxi;
    }

    /**
     * Check the forecast label and calculate the accuracy
     * @param filename The source training data
     */
    public void tagIdentify(String filename) {

        File file = new File(filename);
        String[] testFileList = file.list();

        assert testFileList != null;
        testNum = testFileList.length;

        HashMap<Integer, Double> hashMap = new HashMap<>();

        for (int k = 3; k <= 9; k++) {
            int errorNum = 0;
            System.out.println("Running when k is " + k + "...");
            for (int i = 0; i < testNum; i++) {
                int rightLabel = getRightLabel(testFileList[i]);
                int[] testData = initData(filename + "\\" + testFileList[i]);

                int forecastLabel = knn(testData, k);

                if (forecastLabel != rightLabel)
                    errorNum++;
                printSchedule(i + 1, testNum);
                // This line exists for print all the check situation
//                System.out.println("While k is " + k + ", the " + (i + 1) + "th data:  " + "forecast label is " + forecastLabel + ", and right label is " + rightLabel + ".");
            }

            System.out.println("\nTotal test data amount is " + testNum);
            System.out.println("Wrong answer amount is " + errorNum);

            double accuracy = (1 - (double) errorNum / testNum) * 100;
            System.out.println("accuracy: " + accuracy + "%\n");
            hashMap.put(k, accuracy);
        }
        List<HashMap.Entry<Integer, Double>> list = new ArrayList<>(hashMap.entrySet());

        list.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        System.out.println("The k num rank as this list: ");
        for (HashMap.Entry<Integer, Double> entry : list) {
            System.out.println("k：" + entry.getKey() + ", " + "accuracy: " + entry.getValue());
        }

        System.out.println();
    }

    /**
     * Create the schedule
     * @param process the number of data has been dealt
     * @param total the total number of data
     */
    public static void printSchedule(int process, int total){
        int percent = (process * 100) / total;
        for (int i = 0; i < TOTAL_LENGTH + 10; i++) {
            System.out.print("\b");
        }
        System.out.print("|");
        int now = TOTAL_LENGTH * percent / 100;
        for (int i = 0; i < now; i++) {
            System.out.print(">");
        }
        for (int i = 0; i < TOTAL_LENGTH - now; i++) {
            System.out.print(" ");
        }
        System.out.print("|  " + percent + "%");
    }
}
