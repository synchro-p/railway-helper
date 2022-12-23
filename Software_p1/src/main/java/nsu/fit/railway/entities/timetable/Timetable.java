package nsu.fit.railway.entities.timetable;

import nsu.fit.railway.entities.timetable.TimetableEntry;

import java.util.List;

public class Timetable {

    private List<TimetableEntry> entries;

    public Timetable(List<TimetableEntry> entries) {
        this.entries = entries;
    }


    public List<TimetableEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<TimetableEntry> entries) {
        this.entries = entries;
    }

    public void addEntry(TimetableEntry entry) {
        entries.add(entry);
    }

    public void removeEntry(Integer id) {
        entries.set(id, null);
    }

    public void editEntry(Integer id, TimetableEntry newEntry) {
        entries.set(id, newEntry);
    }
}
