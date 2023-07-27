package com.shinelon.deathknight.demo;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName LineDemo
 * @Author Shinelon
 * @Date 13:32 2023/6/30
 * @Version 1.0
 **/
public class LineDemo {
    private static char[] token = "my-super-secret-auth-token".toCharArray();
    private static String org = "my-org";
    private static String bucket = "bucket";

    @Test
    public void lineNo() {
        InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://127.0.0.1:8086/", token, org, bucket);
        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
        writeApi.writePoints(getList());
        influxDBClient.close();
        System.out.println("success");
    }

    @Test
    public void lineTest2() {
        Point point = Point.measurement("measurement")
                .addTag("gateway_name", "gateway")
                .addTag("service_id", "api")
                .addTag("gateway_host", "127.0.0.1")
                .addTag("gateway_port", "18085")
                .addField("service_host", "127.0.0.1")
                .addField("service_port", 8080)
                .time(Instant.now().toEpochMilli(), WritePrecision.MS);
        System.out.println(point.toLineProtocol());
    }

    public List<Point> getList() {
        List<Point> points = new ArrayList<>(50);
        long milli = Instant.now().toEpochMilli();
        int nextInt = RandomUtils.nextInt(0, 30);
        for (int i = 0; i < nextInt; i++) {
            milli = milli + 1000L;
            points.add(getPoint(milli));
        }
        return points;
    }

    public Point getPoint(long time) {
        Point point = Point.measurement("monitor")
                .addTag("gateway_name", "gateway")
                .addTag("service_id", "rest-api")
                .addTag("gateway_host", "127.0.0.1")
                .addTag("gateway_port", "18085")
                .addField("service_host", "127.0.0.1")
                .addField("service_port", 8080)
                .addField("countValue", 1)
                .time(time, WritePrecision.MS);
        System.out.println(point.toLineProtocol());
        return point;
    }

}
