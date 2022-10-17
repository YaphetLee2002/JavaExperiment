package fifth;

import java.io.*;
import java.util.*;

public class Kmeans
{
    private ArrayList<ArrayList<Double>> data =new ArrayList<ArrayList<Double>>();
    private HashMap<String,Double> mp= new HashMap<String, Double>();
    int k;

    public void creatDataSet(String filename) throws IOException
    {
        mp.put("Iris-setosa", 1.0);
        mp.put("Iris-versicolor", 2.0);
        mp.put("Iris-virginica", 3.0);
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String string;
        while((string=br.readLine())!=null)
        {
            String[] a=string.split(",");
            data.add(new ArrayList<Double>());
            int l=data.size();
            for(int i=0;i<4;i++)
            {
                data.get(l-1).add(Double.parseDouble(a[i]));
            }
            data.get(l-1).add(mp.get(a[4]));
        }
        br.close();
    }

    public double getDistance(ArrayList<Double> x,ArrayList<Double> y)
    {
        double distance=0;
        for(int i=0;i<4;i++) distance+=Math.pow(x.get(i)-y.get(i), 2);
        return Math.sqrt(distance);
    }

    public int findCluster(ArrayList<Double>[] means,ArrayList<Double> y) {
        double mindistance=getDistance(y,means[0]);
        double tempdistance;
        int label=0;
        for(int i=1;i<k;i++)
        {
            tempdistance=getDistance(y,means[i]);
            if(tempdistance<mindistance)
            {
                mindistance=tempdistance;
                label=i;
            }
        }
        return label;
    }

    public double getSSE(ArrayList<ArrayList<Double>>[] cluster,ArrayList<Double>[] means)
    {
        double sse=0;
        for(int i=0;i<k;i++)
        {
            for(int j=0;j<cluster[i].size();j++)
            {
                sse+=getDistance(cluster[i].get(j), means[i]);
            }
        }
        return sse;
    }

    public ArrayList<Double> getMeans(ArrayList<ArrayList<Double>> cluster)
    {
        int l=cluster.size();
        ArrayList<Double> mean=new ArrayList<Double>(4);
        for(int i=0;i<4;i++)mean.add(0.0);
        for(int i=0;i<l;i++)
        {
            for(int j=0;j<4;j++) mean.set(j, mean.get(j)+cluster.get(i).get(j));
        }
        for(int i=0;i<4;i++)
        {
            mean.set(i, mean.get(i)/l);
        }
        return mean;
    }

    public void kMeans()
    {
        ArrayList<ArrayList<Double>>[] cluster = new ArrayList[k];
        ArrayList<Double>[] means = new ArrayList[k];
        Random random = new Random();
        for(int i=0;i<k;i++)
        {
            means[i]=new ArrayList<Double>();
            cluster[i]=new ArrayList<ArrayList<Double>>();
            int randnum=random.nextInt(data.size()-1);
            for(int j=0;j<4;j++) means[i].add(data.get(randnum).get(j));
        }
        for(int i=0;i<data.size();i++)
        {
            int label=findCluster(means, data.get(i));
            cluster[label].add(data.get(i));
        }
        double sse=getSSE(cluster, means),pre_sse=0;
        System.out.println("初始整体误差平方和："+sse);
        int iteration=0;
        do {
            pre_sse=sse;
            for(int i=0;i<k;i++)
            {
                means[i]=getMeans(cluster[i]);
            }
            for(int i=0;i<k;i++)
            {
                cluster[i].clear();
            }
            for(int i=0;i<data.size();i++)
            {
                int label=findCluster(means, data.get(i));
                cluster[label].add(data.get(i));
            }
            sse=getSSE(cluster, means);
            System.out.println("第"+(++iteration)+"次迭代后整体误差平方和为："+sse);
        } while (Math.abs(sse-pre_sse)>=0.2);
        printCluster("cluster.txt",cluster);
        calculateAccuracy(cluster);
    }

    public void calculateAccuracy(ArrayList<ArrayList<Double>>[] cluster)
    {
        int m=0;
        for(int i=0;i<k;i++)
        {
            int a=0,b=0,c=0;
            for(int j=0;j<cluster[i].size();j++)
            {
                switch (cluster[i].get(j).get(4).intValue()){
                    case 1: a++; break;
                    case 2: b++; break;
                    case 3: c++; break;
                    default:;
                }
            }
            m+=Math.max(c, Math.max(a, b));
        }
        System.out.println("分类结果准确率："+(double)m/150*100+"%");
    }

    public void printCluster(String filename,ArrayList<ArrayList<Double>>[] cluster)
    {
        try {
            File file = new File(filename);
            file.createNewFile();
            DataOutputStream ds=new DataOutputStream(new FileOutputStream(file));
            for(int i=0;i<k;i++)
            {
                for(int j=0;j<cluster[i].size();j++)
                {
                    for(int u=0;u<4;u++)
                    {
                        ds.writeBytes(""+cluster[i].get(j).get(u));
                        ds.writeBytes(",");
                    }
                    ds.writeBytes(i+1+"\r\n");
                }
            }
            ds.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}

class Text {

    public static void main(String[] args) {
        try {
            Kmeans kmeans=new Kmeans();
            kmeans.creatDataSet("C:\\Users\\19057\\Documents\\Codefield\\file_java\\JavaExperiment\\src\\fifth\\iris.data");
            kmeans.k=3;
            kmeans.kMeans();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

}

