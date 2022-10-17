package third;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class testString {
    public static void main(String[] args) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\Users\\19057\\Documents\\Codefield\\file_java\\JavaHomework\\src\\experiment\\Lunch.java"));
            String line = null;
            ArrayList<String> arrayList = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                arrayList.add(line);
            }
            bufferedReader.close();
            arrayList.sort(new compareString());
            List<String> list = new ArrayList<>(arrayList);
            File file = new File("inverse.txt");
            file.createNewFile();
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file));

            for (int i = list.size() - 1; i >= 0; i--) {
                dataOutputStream.writeBytes(list.get(i) + "\n");
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
