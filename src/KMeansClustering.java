import java.util.*;

public class KMeansClustering {

    int entityCounter = 0;
    private List<Entity> entities;

    /**
     * This method contains the K-Means Clustering algorithm and the math associated with it.
     *
     * @param numberOfClusters  the number of centroids to cluster around
     * @return                  a map in the format (Entity,Double) where Double is the cluster it is associated with.
     */
    public Map<Entity, Double> cluster(int numberOfClusters){

        Map<Entity, Double> clusteredEntityMap = new LinkedHashMap<Entity, Double>();
        Map<Double, Entity> centroidMap = new LinkedHashMap<Double, Entity>();

        // Create numberOfClusters starting clusters and populate centroid map
        for(int i = 0; i < numberOfClusters; i++){
            Random random = new Random();
            int randomNum = random.nextInt(entities.size());
            Entity tempEnt = new Entity(entities.get(randomNum).getAttributeMap());
            tempEnt.setCluster(i*1.0);
            centroidMap.put(i * 1.0, tempEnt);
        }

        int numColumns = centroidMap.get(0.0).getAttributeMap().size();
        String[] headers = new String[numColumns];
        int newCounter = 0;

        // Retrieve headers
        for (String key : centroidMap.get(0.0).getAttributeMap().keySet()) {
            headers[newCounter] = key;
            newCounter++;
        }

        entityCounter = entities.size();
        // Run through 25 iterations of calculating centroids (or change to desired number of iterations)
        for(double i = 0.0; i < 25; i++) {
            for (double key : centroidMap.keySet()) {
                centroidMap.get(key).setCounter(0);
                for (Entity ent : entities) {
                    if (ent.distance(centroidMap.get(key).getAttributeMap()) <= ent.getDistance()) {
                        ent.setCluster(key);
                        ent.setDistance(centroidMap.get(key).distance(ent.getAttributeMap()));
                    }
                }
            }

            for(double keys : centroidMap.keySet()){
                int centroidCounter = 0;
                for(Entity ents : entities){
                    if(ents.getCluster() == keys){
                        centroidCounter++;
                        centroidMap.get(keys).setCounter(centroidCounter);
                    }
                }
            }

            // Sum centroid values and take average to be new centroid values
            for (double key : centroidMap.keySet()) {
                Map<String, Double> tempCentroidAttrMap = new LinkedHashMap<String, Double>();
                tempCentroidAttrMap.put(headers[0], key);
                for(int k = 0; k < numColumns-1; k++){
                    double columnSum = 0;
                    double newColumnValue = 0;
                    for(Entity ent : entities){
                        if(ent.getCluster() == key){
                            columnSum += ent.getAttributeMap().get(headers[k+1]);
                        }
                    }
                    if(columnSum != 0){
                        newColumnValue = (columnSum / centroidMap.get(key).getCounter());
                    }
                    tempCentroidAttrMap.put(headers[k+1],newColumnValue);
                }
                centroidMap.get(key).setAttributeMap(tempCentroidAttrMap);
            }
        }

        for(Entity ent : entities) {
            clusteredEntityMap.put(ent, ent.getCluster());
        }

        return clusteredEntityMap;
    }

    /**
     * This is the constructor for the KMeansClustering Class. The first two columns of the file being read should be
     * Name and ID. Although the specific name of the columns make no difference, the first two must be in that order.
     *
     * @param entities a list of Entity objects
     */
    public KMeansClustering(List<Entity> entities) {
        this.entities = entities;
    }
}
