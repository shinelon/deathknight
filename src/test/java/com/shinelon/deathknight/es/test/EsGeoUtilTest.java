package com.shinelon.deathknight.es.test;

import java.util.Collection;

import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.geometry.utils.Geohash;
import org.junit.jupiter.api.Test;

public class EsGeoUtilTest {
	
  @Test
  public void distanceTest() {
    double calculate =
        GeoDistance.PLANE.calculate(40.722D, -73.989D, 40.715D, -74.011D, DistanceUnit.METERS);
    System.out.println("GeoDistance.PLANE:" + calculate + "m");
    double calculate2 =
        GeoDistance.ARC.calculate(40.722D, -73.989D, 40.715D, -74.011D, DistanceUnit.METERS);
    System.out.println("GeoDistance.ARC:" + calculate2 + "m");
  }

  @Test
  public void geoHashTest() {
    String stringEncode = Geohash.stringEncode(-73.989D, 40.722D, 9);
    System.out.println("Geohash:" + stringEncode);
    Collection<? extends CharSequence> neighbors = Geohash.getNeighbors("dr5rsm4yg");
    System.out.println(neighbors);
  }
}
