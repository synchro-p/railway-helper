package nsu.fit.railway.gui;

import nsu.fit.railway.entities.timetable.Timetable;
import nsu.fit.railway.entities.timetable.TimetableEntry;
import nsu.fit.railway.entities.timetable.Train;
import nsu.fit.railway.entities.timetable.Type;
import nsu.fit.railway.entities.topology.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

public class TimetableTopologyGenerator {
    static TopologyNode node1;
    static TopologyNode node7;
    public static Topology createTopology() {
        Signal signal1 = new Signal(1);
        Signal signal2 = new Signal(2);

        Track track1 = new Track(
                new TrackInfo(1, 10), new ArrayList<>(List.of(Type.PASS)), true
        );

        Track track2 = new Track(
                new TrackInfo(2, 10), new ArrayList<>(List.of(Type.PASS)), true
        );

        Track track3 = new Track(
                new TrackInfo(3, 10), new ArrayList<>(Arrays.asList(Type.PASS, Type.PASSENGER)), true
        );

        Track track4 = new Track(
                new TrackInfo(4, 10), new ArrayList<>(Arrays.asList(Type.PASS, Type.PASSENGER)), true
        );

        Track track5 = new Track(
                new TrackInfo(5, 10), new ArrayList<>(List.of(Type.PASS)), true
        );

        Track track6 = new Track(
                new TrackInfo(6, 10), new ArrayList<>(List.of(Type.PASS)), true
        );

        Track track7 = new Track(
                new TrackInfo(7, 10), new ArrayList<>(List.of(Type.PASS)), true
        );

        Switch switch1 = new Switch(1, track5, new ArrayList<>(List.of(track7)), track7);

        node1 = new TopologyNode(
                1,
                new ArrayList<>(),
                true,
                false
        );

        TopologyNode node2 = new TopologyNode(
                2,
                new ArrayList<>(List.of(signal1)),
                false,
                false
        );

        TopologyNode node3 = new TopologyNode(
                3,
                new ArrayList<>(),
                false,
                false
        );

        TopologyNode node4 = new TopologyNode(
                4,
                new ArrayList<>(List.of(signal2)),
                false,
                false
        );

        TopologyNode node5 = new TopologyNode(
                5,
                new ArrayList<>(),
                false,
                false
        );

        TopologyNode node6 = new TopologyNode(
                6,
                new ArrayList<>(List.of(switch1)),
                false,
                false
        );

        node7 = new TopologyNode(
                7,
                new ArrayList<>(),
                false,
                true
        );

        node1.setInTracks(new HashSet<>());
        node1.setOutTracks(new HashSet<>(Set.of(track1, track2)));

        node2.setInTracks(new HashSet<>(Set.of(track1)));
        node2.setOutTracks(new HashSet<>(Set.of(track3)));

        node3.setInTracks(new HashSet<>(Set.of(track2)));
        node3.setOutTracks(new HashSet<>(Set.of(track4)));

        node4.setInTracks(new HashSet<>(Set.of(track3)));
        node4.setOutTracks(new HashSet<>(Set.of(track5)));

        node5.setInTracks(new HashSet<>(Set.of(track4)));
        node5.setOutTracks(new HashSet<>(Set.of(track6)));

        node6.setInTracks(new HashSet<>(Set.of(track5, track6)));
        node6.setOutTracks(new HashSet<>(Set.of(track7)));

        node7.setInTracks(new HashSet<>(Set.of(track7)));
        node7.setOutTracks(new HashSet<>());

        track1.setStartNode(node1);
        track1.setFinishNode(node2);

        track2.setStartNode(node1);
        track2.setFinishNode(node3);

        track3.setStartNode(node2);
        track3.setFinishNode(node4);

        track4.setStartNode(node3);
        track4.setFinishNode(node5);

        track5.setStartNode(node4);
        track5.setFinishNode(node6);

        track6.setStartNode(node5);
        track6.setFinishNode(node6);

        track7.setStartNode(node6);
        track7.setFinishNode(node7);

        Set<TopologyNode> nodeSet = new HashSet<>(Set.of(node1, node2, node3, node4, node5, node6, node7));
        Set<Track> trackSet = new HashSet<>(Set.of(track1, track2, track3, track4, track5, track6, track7));

        Topology topology = new Topology(nodeSet, trackSet);
        return topology;
    }

    public static Timetable createTimetable() {
        TimetableEntry timetableEntry1 = new TimetableEntry();
        Train train1 = new Train(1, 1, Type.PASSENGER);
        timetableEntry1.setTrain(train1);
        timetableEntry1.setInputNode(node1);
        timetableEntry1.setOutputNode(node7);
        timetableEntry1.setArrivalIntervalStart(LocalDateTime.of(2022, Month.JANUARY, 10, 0, 15));
        timetableEntry1.setArrivalIntervalFinish(LocalDateTime.of(2022, Month.JANUARY, 10, 1, 0));
        timetableEntry1.setStationingTime(Duration.ofMinutes(10));

        TimetableEntry timetableEntry2 = new TimetableEntry();
        Train train2 = new Train(2, 1, Type.PASSENGER);
        timetableEntry2.setTrain(train2);
        timetableEntry2.setInputNode(node1);
        timetableEntry2.setOutputNode(node7);
        timetableEntry2.setArrivalIntervalStart(LocalDateTime.of(2022, Month.JANUARY, 10, 1, 10));
        timetableEntry2.setArrivalIntervalFinish(LocalDateTime.of(2022, Month.JANUARY, 10, 1, 20));
        timetableEntry2.setStationingTime(Duration.ofMinutes(5));

        Timetable timetable = new Timetable(new ArrayList<>(Arrays.asList(timetableEntry1, timetableEntry2)));
        return timetable;
    }
}
