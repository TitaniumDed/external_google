package com.google.android.systemui.elmyra;

import android.os.Binder;
import com.google.android.systemui.elmyra.proto.nano.SnapshotProtos;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class SnapshotLogger {
    private final int mSnapshotCapacity;
    private List<Snapshot> mSnapshots;

    public class Snapshot {
        final SnapshotProtos.Snapshot mSnapshot;
        final long mTimestamp;

        Snapshot(SnapshotProtos.Snapshot snapshot, long j) {
            this.mSnapshot = snapshot;
            this.mTimestamp = j;
        }

        public SnapshotProtos.Snapshot getSnapshot() {
            return this.mSnapshot;
        }

        long getTimestamp() {
            return this.mTimestamp;
        }
    }

    public SnapshotLogger(int i) {
        this.mSnapshotCapacity = i;
        this.mSnapshots = new ArrayList(i);
    }

    public void addSnapshot(SnapshotProtos.Snapshot snapshot, long j) {
        if (this.mSnapshots.size() == this.mSnapshotCapacity) {
            this.mSnapshots.remove(0);
        }
        this.mSnapshots.add(new Snapshot(snapshot, j));
    }

    public void didReceiveQuery() {
        if (this.mSnapshots.size() > 0) {
            ((Snapshot) this.mSnapshots.get(this.mSnapshots.size() - 1)).getSnapshot().header.feedback = 1;
        }
    }

    public List<Snapshot> getSnapshots() {
        return this.mSnapshots;
    }
}
