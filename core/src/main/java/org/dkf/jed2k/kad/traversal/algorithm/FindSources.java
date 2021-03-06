package org.dkf.jed2k.kad.traversal.algorithm;

import org.dkf.jed2k.exception.JED2KException;
import org.dkf.jed2k.kad.Listener;
import org.dkf.jed2k.kad.NodeImpl;
import org.dkf.jed2k.kad.traversal.observer.Observer;
import org.dkf.jed2k.protocol.kad.Kad2Req;
import org.dkf.jed2k.protocol.kad.KadId;
import org.slf4j.Logger;

/**
 * Created by inkpot on 07.12.2016.
 */
public class FindSources extends FindData {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FindSources.class);
    private long size;

    public FindSources(NodeImpl ni, KadId t, long size, final Listener l) {
        super(ni, t, size, l);
    }

    @Override
    protected void update(Kad2Req req) {
        req.setSearchType(FindData.KADEMLIA_FIND_NODE);
    }

    @Override
    protected Direct newTraversal() throws JED2KException {
        return new SearchSources(nodeImpl, target, size, sink);
    }

    @Override
    public void writeFailedObserverToRoutingTable(final Observer o) {
        // do nothing since peer possibly has no information for us
    }
}
