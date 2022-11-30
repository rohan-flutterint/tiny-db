package edu.utdallas.davisbase.server.b_query_engine.impl.basic.a_query_optimizer.plan.impl;

import edu.utdallas.davisbase.server.b_query_engine.impl.basic.a_query_optimizer.plan.Plan;
import edu.utdallas.davisbase.server.b_query_engine.impl.basic.b_stats_manager.domain.StatInfo;
import edu.utdallas.davisbase.server.b_query_engine.common.catalog.MetadataMgr;
import edu.utdallas.davisbase.server.c_key_value_store.Transaction;
import edu.utdallas.davisbase.server.d_storage_engine.impl.data.iterator.heap.HeapRecordScan;
import edu.utdallas.davisbase.server.d_storage_engine.common.scans.RScan;
import edu.utdallas.davisbase.server.d_storage_engine.impl.data.page.heap.RecordValueLayout;
import edu.utdallas.davisbase.server.d_storage_engine.impl.data.page.heap.RecordValueSchema;

/**
 * The Plan class corresponding to a table.
 *
 * @author Edward Sciore
 */
public class TablePlan implements Plan {
    private String tblname;
    private Transaction tx;
    private RecordValueLayout recordValueLayout;
    private StatInfo si;

    public TablePlan(Transaction tx, String tblname, MetadataMgr md) {
        this.tblname = tblname;
        this.tx = tx;
        recordValueLayout = md.getLayout(tblname, tx);
    }

    public RScan open() {
        return new HeapRecordScan(tx, tblname, recordValueLayout);
    }


    public RecordValueSchema schema() {
        return recordValueLayout.schema();
    }

    @Override
    public int blocksAccessed() {
        return si.blocksAccessed();
    }
}
