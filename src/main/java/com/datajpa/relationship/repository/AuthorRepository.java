package com.datajpa.relationship.repository;

import com.datajpa.relationship.model.Author;
import com.datajpa.relationship.model.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

}































/**
package com.fr.ratp.pov.randomPolyGone;

        import com.fr.ratp.pov.service.GeneratorCoordinate;
        import com.google.common.net.HttpHeaders;
        import com.google.common.util.concurrent.RateLimiter;
        import com.vividsolutions.jts.geom.Coordinate;
        import com.vividsolutions.jts.geom.Polygon;

        import java.net.URI;
        import java.net.http.HttpClient;
        import java.net.http.HttpRequest;
        import java.net.http.HttpResponse;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.concurrent.ThreadLocalRandom;

public class InjecteurRandomPolyGone implements Runnable {
    private double MAXLATITUDE = 48.9385528;
    private double MINLATITUDE = 48.800306;
    private double MAXLONGITUDE = 2.360434;
    private double MINLONGITUDE = 2.379404;

    private Integer nombreSommetMin;
    private Integer nombreSommetMax;
    private Integer nbPolygoneParThread;
    private Double debitThread;
    private int srid = 3758;
    List<Coordinate> points = new ArrayList<>();
    final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(InjecteurRandomPolyGone.class);


    public InjecteurRandomPolyGone(Double debitThread, Integer nombreSommetMin, Integer nombreSommetMax, Integer nbPolygoneParThread) {
        this.nombreSommetMin = nombreSommetMin;
        this.nombreSommetMax = nombreSommetMax;
        this.debitThread = debitThread;
        this.nbPolygoneParThread = nbPolygoneParThread;
    }

    public void run() {


        final RateLimiter rateLimiter = RateLimiter.create(debitThread);


        try {

            int randomSommet = ThreadLocalRandom.current().nextInt(nombreSommetMin, nombreSommetMax + 1);
            GeneratorCoordinate generatorCoordinate = new GeneratorCoordinate();
            String url = "http://localhost:8080/bus?nbPoint=" + randomSommet + "&srid=" + srid;

            List<Coordinate> points = new ArrayList<>();

            while (true) {

                for (int i = 0; i < randomSommet; i++) {
                    Coordinate point = generatorCoordinate.randXYPoints(MAXLATITUDE, MINLATITUDE, MAXLONGITUDE, MINLONGITUDE);
                    url += "&pt" + i + "x=" + point.x + "&pt" + i + "y=" + point.y;
                    points.add(point);
                }

                Polygon polygon = generatorCoordinate.createRandomPolygone(points);
                polygon.isValid()
                boolean isPolygoneConvex = generatorCoordinate.checkConvexity(polygon);
                //logger.info("La liste des polygones" + generatorCoordinate.createRandomPolygone(points) + " URL : " + url);
                if(isPolygoneConvex){
                    HttpClient client = HttpClient.newHttpClient();

                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(url))
                            .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                            .GET().build();

                    long startExecution = System.currentTimeMillis();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                    long endExecution = System.currentTimeMillis();
                    long dureeEnmilliSeconde = (endExecution - startExecution);
                    logger.info("durée d'éxécution du thread " + dureeEnmilliSeconde);

                    // logger.info("response" + response);
                    rateLimiter.acquire();
                }

            }
        } catch (Exception e) {
            logger.info("Exception is caught");

        }

    }

}
 **/

