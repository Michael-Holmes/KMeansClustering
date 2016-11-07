import java.util.*;

public class Entity {

    double entityCluster;
    double entityDistance;
    int counter = 0;
    String name;
    Map<String, Double> attributes = new LinkedHashMap<String, Double>();

    /**
     * Constructor for the Entity class
     *
     * @param attributes    the map of attributes(columns) of an Entity.
     */
    public Entity(Map<String, Double> attributes){ this.attributes = attributes; }

    /**
     * Used to find the Euclidean Distance between two Entities (a centroid is also an entity).
     *
     * @param centroidAttributes    the map of attributes of an Entity.
     * @return                      the distance between two maps of attributes.
     */
    public Double distance(Map<String, Double> centroidAttributes){

        double rawDistance = 0.0;
        for(String key : attributes.keySet()){

            // Euclidean distance
            rawDistance += Math.pow(attributes.get(key) - centroidAttributes.get(key), 2);
        }

        return Math.sqrt(rawDistance);
    }

    public void setCluster(double cluster) {this.entityCluster = cluster;}
    public double getCluster() {return entityCluster;}

    public void setDistance(double distance){this.entityDistance = distance;}
    public double getDistance(){return entityDistance;}

    public void setAttributeMap(Map<String, Double> attrMap){ this.attributes = attrMap; }
    public Map<String, Double> getAttributeMap(){return attributes;}

    public void setCounter(int counter){ this.counter = counter; }
    public int getCounter(){ return counter; }

    public void setName(String entName){ this.name = entName; }
    public String getName(){ return name; }

}