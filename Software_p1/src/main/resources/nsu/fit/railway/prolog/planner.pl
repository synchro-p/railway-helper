%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% static topology predicates %
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% track(trackId, trackLength, trackType).
:- dynamic(track/3).
% trackOut(trackId, trackIdOut, nodeId).
:- dynamic(trackOut/3).
% nodeTrackOut(nodeId, trackIdOut).
:- dynamic(nodeTrackOut/2).
% nodeTrackIn(nodeId, trackIdIn).
:- dynamic(nodeTrackIn/2).
% switch(switchId, nodeId, fromTrackId, toTrackId).
:- dynamic(switch/4).
% signal(signalId, nodeId).
:- dynamic(signal/2).


addTrack(TrackId, TrackLength, Type, TracksIdOut, NodeId) :-
    assert(track(TrackId, TrackLength, Type)),
    addTrackOut(TrackId, TracksIdOut, NodeId).

addTrackOut(TrackId, [TrackIdOut | OtherOutTracks], NodeId) :-
    assert(trackOut(TrackId, TrackIdOut, NodeId)),
    addTrackOut(TrackId, OtherOutTracks, NodeId).

addTrackOut(_, [], _).

addNode(NodeId, TracksIdIn, TracksIdOut) :-
    addNodeTrackIn(NodeId, TracksIdIn),
    addNodeTrackOut(NodeId, TracksIdOut).

addNodeTrackOut(_, []).

addNodeTrackOut(NodeId, [TrackIdOut | Other]) :-
    assert(nodeTrackOut(NodeId, TrackIdOut)),
    addNodeTrackOut(NodeId, Other).

addNodeTrackIn(_, []).

addNodeTrackIn(NodeId, [TrackIdIn | Other]) :-
    assert(nodeTrackIn(NodeId, TrackIdIn)),
    addNodeTrackIn(NodeId, Other).

addSwitch(_, _, _, []).

addSwitch(SwitchId, NodeId, FromTrackId, [ToTrackId | Other]) :-
    assert(switch(SwitchId, NodeId, FromTrackId, ToTrackId)),
    addSwitch(SwitchId, NodeId, FromTrackId, Other).

addSignal(SignalId, NodeId) :-
    assert(signal(SignalId, NodeId)).

% trackTrain(trainId, trackId, timeStart, timeEnd).
:- dynamic(trackTrain/4).


buildSignalEvent(_, OutNodeId, _, _, _, []) :-
    \+ signal(_, OutNodeId).

buildSignalEvent(TrainId, OutNodeId, SignalValue, TimeStart, TimeEnd, [[signalEvent, TrainId, SignalId, SignalValue, TimeStart, TimeEnd]]) :-
    signal(SignalId, OutNodeId).

buildSwitchEvent(_, OutNodeId, FromTrackId, ToTrackId, _, _, []) :-
    \+ switch(_, OutNodeId, FromTrackId, ToTrackId).

buildSwitchEvent(TrainId, OutNodeId, FromTrackId, ToTrackId, TimeStart, TimeEnd, [[switchEvent, TrainId, SwitchId, ToTrackId, TimeStart, TimeEnd]]) :-
    switch(SwitchId, OutNodeId, FromTrackId, ToTrackId).

buildTrainMoveEvent(TrainId, FromTrackId, ToTrackId, TimeStart, TimeEnd, [[trainMoveEvent, TrainId, FromTrackId, ToTrackId, TimeStart, TimeEnd]]).

buildPath(_, _, InNodeId, InNodeId, _, _, EventsAcc, EventsAcc).

buildPath(TrainId, FromTrackId, InNodeId, FinishNodeId, TimeStart, TimeEnd, EventsAcc, Events) :-
    nodeTrackOut(InNodeId, TrackOutId),
    checkTrackAvailability(TrackOutId, TimeStart, TimeEnd, 0),
    nodeTrackIn(NodeOutId, TrackOutId),
    buildSwitchEvent(TrainId, InNodeId, FromTrackId, TrackOutId, TimeStart, TimeEnd, SwitchEvent),
    buildSignalEvent(TrainId, InNodeId, go, TimeStart, TimeEnd, SignalEvent),
    buildTrainMoveEvent(TrainId, FromTrackId, TrackOutId, TimeStart, TimeEnd, TrainMoveEvent),
    buildSignalEvent(TrainId, InNodeId, stop, TimeStart, TimeEnd, RevertSignalEvent),
    append(SwitchEvent, SignalEvent, ControlEvents),
    append(ControlEvents, TrainMoveEvent, ControlMoveEvents),
    append(ControlMoveEvents, RevertSignalEvent, CurrEvents),
    append(EventsAcc, CurrEvents, NewEvents),
    buildPath(TrainId, TrackOutId, NodeOutId, FinishNodeId, TimeStart, TimeEnd, NewEvents, Events).

checkTrackAvailability(TrackId, _, _, _) :-
    \+ trackTrain(_, TrackId, _, _).

checkTrackAvailability(TrackId, TimeStart, TimeEnd, StatTime) :-
    trackTrain(_, TrackId, TrackTimeStart, TrackTimeEnd),
    (
        (TrackTimeEnd < TimeStart);
        (TimeEndWithStat is (TimeEnd + StatTime),
         TrackTimeStart > TimeEndWithStat)
    ).

getStationTrack(StatTrackId, TrainType, TrainLength, TimeStart, TimeEnd, StatTime) :-
    track(StatTrackId, StatTrackLength, StatTrackType),
    member(TrainType, StatTrackType),
    StatTrackLength >= TrainLength,
    checkTrackAvailability(StatTrackId, TimeStart, TimeEnd, StatTime).

processEntry(TrainId, InNodeIn, OutNodeId, TimeStart, TimeEnd, StatTime, TrainLength, TrainType, Events) :-
    getStationTrack(StatTrackId, TrainType, TrainLength, TimeStart, TimeEnd, StatTime),
    nodeTrackIn(StatOutNodeId, StatTrackId),
    buildPath(TrainId, 0, InNodeIn, StatOutNodeId, TimeStart, TimeEnd, [], ToStatEvents),
    TimeStartWithStat is (TimeStart + StatTime),
    TimeEndWithStat is (TimeEnd + StatTime),
    buildPath(TrainId, StatTrackId, StatOutNodeId, OutNodeId, TimeStartWithStat, TimeEndWithStat, [], FromStatEvents),
    append(ToStatEvents, FromStatEvents, Events),
    assert(trackTrain(TrainId, StatTrackId, TimeStart, TimeEndWithStat)).
plan(_, [], EventsAcc, EventsAcc).

plan(PrevTrainId, [[TrainId, InNodeId, OutNodeId, TimeStart, TimeEnd, StatTime, TrainLength, TrainType] | Other], EventsAcc, Events) :-
    %(write(TrainId), write(" "), write(InNodeId), write(" "), write(OutNodeId), write(" "), write(TimeStart), write(" ")),
    %(write(TimeEnd), write(" "), write(StatTime), write(" "), write(TrainLength), write(" "), write(TrainType), writeln(" ")),
    (processEntry(TrainId, InNodeId, OutNodeId, TimeStart, TimeEnd, StatTime, TrainLength, TrainType, EntryEvents) ->
        (append(EventsAcc, EntryEvents, NewEvents), plan(TrainId, Other, NewEvents, Events));
        (retractall(trackTrain(PrevTrainId, _, _, _)), false)).

plan(Entries, Events) :-
    plan(-1, Entries, [], Events).

planAdapter([], [], EntriesAcc, EntriesAcc).

planAdapter([TrainId, InNodeId, OutNodeId, TimeStart, TimeEnd, StatTime, TrainLength | Other], [TrainType | OtherType], EntriesAcc, Entries) :-
    append(EntriesAcc, [[TrainId, InNodeId, OutNodeId, TimeStart, TimeEnd, StatTime, TrainLength, TrainType]], NewEntriesAcc),
    planAdapter(Other, OtherType, NewEntriesAcc, Entries).

planAdapter(LongEntries, TypeEntries, Events) :-
    planAdapter(LongEntries, TypeEntries, [], Entries),
    plan(Entries, Events).









