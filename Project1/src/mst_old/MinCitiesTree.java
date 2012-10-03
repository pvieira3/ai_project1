/*
NAME: Juan C. Garcia 
CERTIFICATION: I certify that this work is my own and that
               none of it is the work of any other person.
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class MinCitiesTree 
{
    private List<Edge> edges;
    private LinkedList<String> cities;
    
    /*Main "Tester" method*/    
    public static void main(String [] args)
    {
        MinCitiesTree test = new MinCitiesTree("miles.dat");
        test.printMinTree(test.minSpanTree());
    }
    
    public MinCitiesTree(String fname){
        edges = new ArrayList<Edge>();
        cities = new LinkedList<String>();
        
        cities.addFirst("milk");
        cities.addFirst("bread");
        cities.addFirst("juice");
        
        edges.add(new Edge("bread","milk", 3));
        edges.add(new Edge("juice","milk", 5));
        edges.add(new Edge("milk", "bread", 10));
        edges.add(new Edge("juice", "bread", 5));
        edges.add(new Edge("milk", "juice", 7));
        edges.add(new Edge("bread", "juice", 10));
    }
    
//    public MinCitiesTree(String fName)
//    {
//        edges = new ArrayList<Edge>();
//        cities = new LinkedList<String>();
//        try
//        {
//            File graph = new File(fName);
//            Scanner sc = new Scanner(graph);
//            
//            int counter = 0;
//            String currentCity = null;
//            
//            while(sc.hasNextLine())
//            {
//                String line = sc.nextLine();
//                /*Skips comments*/
//                if(line.startsWith("*"))
//                    continue;
//                /*If line starts with a letter, it is city info.
//                  The proper city info is added first into a list
//                  and the counter is set to 0 (To facilitate search
//                  in reversed ordered numbers)*/    
//                if(Character.isLetter(line.charAt(0)))
//                {
//                    counter = 0;
//                    currentCity = getCleanCityInfo(line);
//                    cities.addFirst(currentCity);
//                }
//                /*Otherwise, the line is mileage info.*/
//                else
//                {
//                    Scanner sc2 = new Scanner(line);
//                    while(sc2.hasNextInt())
//                    {
//                        /*Counter is increased to find corresponding city in list*/
//                        counter++;
//                        edges.add(new Edge(cities.get(counter), currentCity, sc2.nextInt()));
//                    }
//                }
//            }
//        }
//        catch(FileNotFoundException file)
//        {
//            System.out.println("File was not found.");
//        }
//        catch (IOException exception)
//        {
//            exception.printStackTrace();
//        }
//    }
    /*Obtains the Minimum Spanning Tree via Kruskal's algorithm*/
    public List<Edge> minSpanTree()
    {
        int v = cities.size();
        List<Edge> result = new ArrayList<Edge>();
        PriorityQueue<Edge> pq = new PriorityQueue<Edge>(edges);
        DisjointSet ds = new DisjointSet(v);
        
        while(! pq.isEmpty())
        {
            /*The minimum edge is constantly removed*/
            Edge e = pq.remove();
            
            int r1 = ds.find(cities.indexOf(e.from()));
            int r2 = ds.find(cities.indexOf(e.to()));
            /*If the two find calls don't yield same value, then a cycle is 
             * not made and the edge can be added*/
            if(r1 != r2)
            {
                ds.union(r1, r2);
                result.add(e);
                /*Algorithm stops when there are V-1 edges*/
                if(result.size() == (v - 1))
                    break;
            }   
        }
        return result;
    }
    /*Prints the Minimum Spanning Tree with its total cost*/
    public void printMinTree(List<Edge> edges)
    {
        System.out.println("[ From     -     To ] Cost: ");
        int totalCost = 0;
        for(Edge e : edges)
        {
            totalCost += e.cost();
            System.out.println(e);
        }
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println("Total Cost: " + totalCost);
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$");
    }
    /*Gets the city and state name only*/
    private String getCleanCityInfo(String cityInfo)
    {
        int index = 0;
        while(cityInfo.charAt(index) != new Character('['))
            index++;
        
        return cityInfo.substring(0, index);  
    }
}//End of MinCitiesTree
