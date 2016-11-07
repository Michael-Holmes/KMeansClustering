import org.junit.Test;
import java.io.*;
import java.net.URL;
import java.util.*;

public class KMeansClusteringTest {

    /**
     * Tests to make sure there is a file in the directory that matches what is given.
     *
     * @throws Exception
     */
    @Test
    public void TestClassify() throws Exception{
        URL url = this.getClass().getResource("/ml_project_data.csv");
        String dataFile = url.toString();
        List<Entity> entities = readEntitiesFromFile(dataFile);

        KMeansClustering kMeansClustering = new KMeansClustering(entities);

        Map<Entity, Double> clusteredEntityMap = kMeansClustering.cluster(3);

        //Prints out person and what cluster they are in
        for(Map.Entry<Entity, Double> entry : clusteredEntityMap.entrySet()){

            System.out.println(entry.getKey().getName() + " is in cluster " + (entry.getValue().intValue()+1));
        }
    }

    /**
     * Reads in the file and creates the maps and lists for the Entity data.
     *
     * @param fileName      the name of the file to be clustered.
     * @return              a list of type Entity.
     * @throws Exception    does this when file cannot be found.
     */
    private List<Entity> readEntitiesFromFile (String fileName) throws Exception{
        URL url = this.getClass().getResource("/ml_project_data.csv");
        System.out.println(url);
        File testCSV = new File(url.getFile());
        Scanner fileScan = new Scanner(new FileReader(testCSV));
        String[] headers = fileScan.nextLine().split("[,|]+");      //Separates lines by "," or "|"
        Map<String, Map<String, Double>> bigMap = new LinkedHashMap<String, Map<String, Double>>();
        List<String> idList = new ArrayList<String>();
        List<String> nameList = new ArrayList<String>();
        int numColumns = headers.length - 2;
        while(fileScan.hasNextLine()){
            String[] entityInfo = fileScan.nextLine().split("[,|]+");
            String entityID = entityInfo[1];
            Map<String, Double> smallMap = new LinkedHashMap<String, Double>();
            nameList.add(entityInfo[0]);
            idList.add(entityInfo[1]);
            for(int i = 1; i < headers.length; i++){
                smallMap.put(headers[i],Double.parseDouble(entityInfo[i]));
            }
            bigMap.put(entityID,smallMap);
        }
        fileScan.close();

        int newCounter = 0;
        //  Initialize entity list and populate
        List<Entity> entities = new ArrayList<Entity>();
        for(String key : bigMap.keySet()){
            Entity tempEnt = new Entity(bigMap.get(key));
            tempEnt.setAttributeMap(bigMap.get(key));
            tempEnt.setDistance(Double.MAX_VALUE);
            tempEnt.setName(nameList.get(newCounter));
            entities.add(tempEnt);
            newCounter++;
        }

        return entities;
    }
}