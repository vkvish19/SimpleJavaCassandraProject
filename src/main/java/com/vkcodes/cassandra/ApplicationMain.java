package com.vkcodes.cassandra;

import static com.datastax.oss.driver.internal.core.time.Clock.LOG;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.datastax.oss.driver.api.core.CqlSession;

public class ApplicationMain
{
    public static void main(String[] args) {
        CassandraConnector connector = new CassandraConnector();
        connector.connect("127.0.0.1", 9042, "datacenter1");
        CqlSession session = connector.getSession();
        
        KeyspaceRepository keyspaceRepository = new KeyspaceRepository(session);
        
        keyspaceRepository.createKeyspace("testKeyspace", 1);
        keyspaceRepository.useKeyspace("testKeyspace");
        
        VideoRepository videoRepository = new VideoRepository(session);
        
//        videoRepository.createTable();
        
        videoRepository.insertVideo(new Video("Video Title 1", Instant.now()), null);
        videoRepository.insertVideo(new Video("Video Title 2",
                Instant.now().minus(1, ChronoUnit.DAYS)), null);
        
        List<Video> videos = videoRepository.selectAll(null);
        
        videos.forEach(x -> System.out.println((x.toString())));
        
        connector.close();
    }}
