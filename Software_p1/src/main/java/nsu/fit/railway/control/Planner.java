package nsu.fit.railway.control;

import nsu.fit.railway.entities.event.*;
import nsu.fit.railway.entities.timetable.Timetable;
import nsu.fit.railway.entities.timetable.TimetableEntry;
import nsu.fit.railway.entities.timetable.Train;
import nsu.fit.railway.entities.timetable.Type;
import nsu.fit.railway.entities.topology.*;
import org.projog.api.Projog;
import org.projog.api.QueryResult;
import org.projog.api.QueryStatement;
import org.projog.core.term.Term;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.AbstractMap.SimpleEntry;
import java.util.*;

public class Planner {

    private final Projog projog;

    public Planner() {
        this.projog = new Projog();
        projog.consultFile(new File("src/main/resources/nsu/fit/railway/prolog/planner.pl"));
    }

    public static void main(String[] args) {
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

        TopologyNode node1 = new TopologyNode(
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

        TopologyNode node7 = new TopologyNode(
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

        EventQueue eventQueue = new Planner().createSchedule(topology, timetable);
        for (SimpleEntry<SimpleEntry<LocalDateTime, LocalDateTime>, Event> event : eventQueue) {
            System.out.println(event.getKey().getKey());
            System.out.println(event.getKey().getValue());
            System.out.println(event.getValue());
        }
    }

    public EventQueue createSchedule(Topology topology, Timetable timetable){
        uploadTopology(topology);

        return termToEventQueue(timetable, topology, plan(timetable));
    }

    private EventQueue termToEventQueue(Timetable timetable, Topology topology, Term eventsTerm) {
        EventQueue eventQueue = new EventQueue();

        Term curTerm = eventsTerm;
        System.out.println(curTerm);
        while (curTerm.getNumberOfArguments() > 1) {
            System.out.println(curTerm.getArgs()[0].toString().replaceAll("[^a-zA-Z0-9 ]", ""));
            String[] stringEvent = curTerm.getArgs()[0].toString().replaceAll("[^a-zA-Z0-9 ]", "").split(" ");

            eventQueue.add(stringToEvent(timetable, topology, stringEvent));

            curTerm = curTerm.getArgs()[1];
        }

        return eventQueue;
    }

    private SimpleEntry<SimpleEntry<LocalDateTime, LocalDateTime>, Event> stringToEvent(
            Timetable timetable, Topology topology, String[] stringEvent
    ) {
        int entryId = Integer.parseInt(stringEvent[1]);
        TimetableEntry timetableEntry = timetable.getEntries()
                .stream()
                .filter(entry -> entry.getTrain().getId() == entryId)
                .findFirst()
                .orElseThrow(() -> {
                    throw new IllegalStateException("Couldn't find timetable entry from event in timetable");
                });

        int timeStart, timeEnd;
        Event event;
        switch (stringEvent[0]) {
            case "trainMoveEvent" -> {
                int fromTrackId = Integer.parseInt(stringEvent[2]);
                int toTrackId = Integer.parseInt(stringEvent[3]);
                timeStart = Integer.parseInt(stringEvent[4]);
                timeEnd = Integer.parseInt(stringEvent[5]);

                Train train = timetableEntry.getTrain();

                Track fromTrack = null;
                if (fromTrackId != 0)
                    fromTrack = topology.getTracks()
                            .stream()
                            .filter(track -> track.getTrackInfo().getId() == fromTrackId)
                            .findFirst()
                            .orElseThrow(() -> {
                                throw new IllegalStateException("Couldn't find track from event in topology");
                            });

                Track toTrack = topology.getTracks()
                        .stream()
                        .filter(track -> track.getTrackInfo().getId() == toTrackId)
                        .findFirst()
                        .orElseThrow(() -> {
                            throw new IllegalStateException("Couldn't find track from event in topology");
                        });

                event = new MoveTrainEvent(train, fromTrack, toTrack);
            }
            case "signalEvent" -> {
                int signalId = Integer.parseInt(stringEvent[2]);
                String signalValue = stringEvent[3];
                timeStart = Integer.parseInt(stringEvent[4]);
                timeEnd = Integer.parseInt(stringEvent[5]);

                Signal signal = (Signal) topology.getNodes()
                        .stream()
                        .map(TopologyNode::getAssociated)
                        .flatMap(Collection::stream)
                        .filter(controlElement -> controlElement instanceof Signal)
                        .filter(signalElement -> ((Signal) signalElement).getSignalInfo().getId() == signalId)
                        .findFirst()
                        .orElseThrow(() -> {
                            throw new IllegalStateException("Couldn't find signal from event in topology.");
                        });

                SignalState signalState = switch (signalValue) {
                    case "go" -> SignalState.GO;
                    case "stop" -> SignalState.STOP;
                    default -> throw new IllegalStateException("Invalid signal state");
                };

                event = new SignalEvent(signal, signalState);
            }
            case "switchEvent" -> {
                int switchId = Integer.parseInt(stringEvent[2]);
                int toTrackId = Integer.parseInt(stringEvent[3]);
                timeStart = Integer.parseInt(stringEvent[4]);
                timeEnd = Integer.parseInt(stringEvent[5]);

                Switch aSwitch = (Switch) topology.getNodes()
                        .stream()
                        .map(TopologyNode::getAssociated)
                        .flatMap(Collection::stream)
                        .filter(controlElement -> controlElement instanceof Switch)
                        .filter(switchElement -> ((Switch) switchElement).getSwitchInfo().getId() == switchId)
                        .findFirst()
                        .orElseThrow(() -> {
                            throw new IllegalStateException("Couldn't find switch from event in topology.");
                        });

                Track toTrack = topology.getTracks()
                        .stream()
                        .filter(track -> track.getTrackInfo().getId() == toTrackId)
                        .findFirst()
                        .orElseThrow(() -> {
                            throw new IllegalStateException("Couldn't find track from event in topology.");
                        });

                event = new SwitchEvent(aSwitch, toTrack);
            }
            default -> throw new IllegalStateException("Unknown event");
        }

        int eventTimeIntervalStartDiff = timetableEntry.getArrivalIntervalStart().getHour() * 60
                + timetableEntry.getArrivalIntervalStart().getMinute() - timeStart;

        int eventTimeIntervalEndDiff = timetableEntry.getArrivalIntervalFinish().getHour() * 60
                + timetableEntry.getArrivalIntervalFinish().getMinute() - timeEnd;

        LocalDateTime eventTimeIntervalStart =
                timetableEntry.getArrivalIntervalStart().minusMinutes(eventTimeIntervalStartDiff);

        LocalDateTime eventTimeIntervalEnd =
                timetableEntry.getArrivalIntervalFinish().minusMinutes(eventTimeIntervalEndDiff);

        return new SimpleEntry<>(new SimpleEntry<>(eventTimeIntervalStart, eventTimeIntervalEnd), event);
    }

    private Term plan(Timetable timetable) {
        List<Long> longEntries = new ArrayList<>();
        List<String> typeEntries = new ArrayList<>();
        for (TimetableEntry entry : timetable.getEntries()) {
            longEntries.add((long) entry.getTrain().getId());
            longEntries.add(Long.valueOf(entry.getInputNode().getId()));
            longEntries.add(Long.valueOf(entry.getOutputNode().getId()));
            longEntries.add(
                    (long) (entry.getArrivalIntervalStart().getHour() * 60 + entry.getArrivalIntervalStart().getMinute())
            );
            longEntries.add(
                    (long) (entry.getArrivalIntervalFinish().getHour() * 60 + entry.getArrivalIntervalFinish().getMinute())
            );
            longEntries.add(entry.getStationingTime().toMinutes());
            longEntries.add((long) entry.getTrain().getLength());

            typeEntries.add(switch (entry.getTrain().getType()) {
                case PASSENGER -> "passenger";
                case PASS -> "pass";
                case MAINTENANCE -> "maintenance";
                case CARGO -> "cargo";
            });
        }

        QueryStatement planStatement = projog.createStatement(
                "planAdapter(LongEntries, TypeEntries, Events)."
        );
        planStatement.setListOfLongs("LongEntries", longEntries);
        planStatement.setListOfAtomNames("TypeEntries", typeEntries);

        System.out.println("planAdapter(LongEntries, TypeEntries, Events)."
                + " " + longEntries
                + " " + typeEntries);

        QueryResult result = planStatement.executeQuery();
        if (result.next()) {
            return result.getTerm("Events");
        }

        return null;
    }

    private void uploadTopology(Topology topology) {
        uploadNodes(topology.getNodes());
        uploadTracks(topology.getTracks());
    }

    private void uploadNodes(Set<TopologyNode> nodes) {
        for (TopologyNode node : nodes) {
            for (ControlElement controlElement : node.getAssociated()) {
                if (controlElement instanceof Switch) {
                    uploadSwitch(node.getId(), (Switch) controlElement);
                } else if (controlElement instanceof Signal) {
                    uploadSignal(node.getId(), (Signal) controlElement);
                }
            }

            List<Long> tracksIdIn = new ArrayList<>();
            for (Track trackIn : node.getInTracks()) {
                tracksIdIn.add(Long.valueOf(trackIn.getTrackInfo().getId()));
            }

            List<Long> tracksIdOut = new ArrayList<>();
            for (Track trackOut : node.getOutTracks()) {
                tracksIdOut.add(Long.valueOf(trackOut.getTrackInfo().getId()));
            }

            QueryStatement addNodeStatement = projog.createStatement("addNode(NodeId, TracksIdIn, TracksIdOut).");
            addNodeStatement.setLong("NodeId", Long.valueOf(node.getId()));
            addNodeStatement.setListOfLongs("TracksIdIn", tracksIdIn);
            addNodeStatement.setListOfLongs("TracksIdOut", tracksIdOut);

            System.out.println("addNode(NodeId, TracksIdIn, TracksIdOut)."
                    + " " + Long.valueOf(node.getId())
                    + " " + tracksIdIn
                    + " " + tracksIdOut);

            addNodeStatement.executeOnce();
        }
    }

    private void uploadTracks(Set<Track> tracks) {
        for (Track track : tracks) {
            if (!track.isActive())
                continue;

            List<String> trackTypeAtoms = new ArrayList<>();
            for (Type type : track.getCanServe()) {
                trackTypeAtoms.add(switch (type) {
                    case PASS -> "pass";
                    case CARGO -> "cargo";
                    case PASSENGER -> "passenger";
                    case MAINTENANCE -> "maintenance";
                });
            }

            List<Long> tracksIdOut = new ArrayList<>();
            for (Track trackOut: track.getFinishNode().getOutTracks()) {
                tracksIdOut.add(Long.valueOf(trackOut.getTrackInfo().getId()));
            }

            QueryStatement addTrackStatement = projog.createStatement(
                    "addTrack(TrackId, TrackLength, Type, TracksIdOut, NodeId)."
            );
            addTrackStatement.setLong("TrackId", Long.valueOf(track.getTrackInfo().getId()));
            addTrackStatement.setLong("TrackLength", Long.valueOf(track.getTrackInfo().getLength()));
            addTrackStatement.setListOfAtomNames("Type", trackTypeAtoms);
            addTrackStatement.setListOfLongs("TracksIdOut", tracksIdOut);
            addTrackStatement.setLong("NodeId", Long.valueOf(track.getFinishNode().getId()));

            System.out.println("addTrack(TrackId, TrackLength, Type, TracksIdOut, NodeId)."
                    + " " + Long.valueOf(track.getTrackInfo().getId())
                    + " " + Long.valueOf(track.getTrackInfo().getLength())
                    + " " + trackTypeAtoms
                    + " " + tracksIdOut
                    + " " + Long.valueOf(track.getFinishNode().getId()));

            addTrackStatement.executeOnce();
        }
    }

    private void uploadSwitch(Integer nodeId, Switch switchElement) {
        List<Long> toTracksId = new ArrayList<>();
        for (Track toTrack : switchElement.getSwitchInfo().getTracksTo()) {
            toTracksId.add(Long.valueOf(toTrack.getTrackInfo().getId()));
        }

        QueryStatement addSwitchStatement = projog.createStatement(
                "addSwitch(SwitchId, NodeId, FromTrackId, ToTracksId)."
        );
        addSwitchStatement.setLong("SwitchId", Long.valueOf(switchElement.getSwitchInfo().getId()));
        addSwitchStatement.setLong("NodeId", Long.valueOf(nodeId));
        addSwitchStatement.setLong("FromTrackId",
                Long.valueOf(switchElement.getSwitchInfo().getTrackFrom().getTrackInfo().getId())
        );
        addSwitchStatement.setListOfLongs("ToTracksId", toTracksId);

        System.out.println("addSwitch(SwitchId, NodeId, FromTrackId, ToTracksId)."
                + " " + Long.valueOf(switchElement.getSwitchInfo().getId())
                + " " + Long.valueOf(nodeId)
                + " " + Long.valueOf(switchElement.getSwitchInfo().getTrackFrom().getTrackInfo().getId())
                + " " + toTracksId);

        addSwitchStatement.executeOnce();
    }

    private void uploadSignal(Integer nodeId, Signal signal) {
        QueryStatement addSignalStatement = projog.createStatement(
                "addSignal(SignalId, NodeId)."
        );
        addSignalStatement.setLong("SignalId", Long.valueOf(signal.getSignalInfo().getId()));
        addSignalStatement.setLong("NodeId", Long.valueOf(nodeId));

        System.out.println("addSignal(SignalId, NodeId)."
                + " " + Long.valueOf(signal.getSignalInfo().getId())
                + " " + Long.valueOf(nodeId));

        addSignalStatement.executeOnce();
    }

}
