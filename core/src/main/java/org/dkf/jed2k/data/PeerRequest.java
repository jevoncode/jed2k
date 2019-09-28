package org.dkf.jed2k.data;

import org.dkf.jed2k.Constants;
import org.dkf.jed2k.Pair;
import org.dkf.jed2k.exception.JED2KException;

import java.util.ArrayList;

import static org.dkf.jed2k.exception.ErrorCode.INVALID_PR_PARAMETER;
import static org.dkf.jed2k.exception.ErrorCode.PEER_REQUEST_OVERFLOW;

public class PeerRequest {
    public int piece;
    public long start;
    public long length;

    public static PeerRequest mk_request(long begin, long end) throws JED2KException {
        if (end <= begin || begin < 0) throw new JED2KException(INVALID_PR_PARAMETER);
        PeerRequest pr = new PeerRequest();
        pr.piece = (int)(begin / Constants.PIECE_SIZE);
        pr.start = begin % Constants.PIECE_SIZE;
        pr.length = end - begin;
        if (pr.length > Constants.PIECE_SIZE) throw new JED2KException(PEER_REQUEST_OVERFLOW);
        return pr;
    }

    public static ArrayList<PeerRequest> mk_requests(long begin, long end, long fsize) throws JED2KException {
        begin = Math.min(begin, fsize);
        end = Math.min(end, fsize);

        ArrayList<PeerRequest> reqs = new ArrayList<PeerRequest>();

        for (long i = begin; i < end;) {
            PeerRequest pr = PeerRequest.mk_request(i, Math.min(i + Constants.PIECE_SIZE - i % Constants.PIECE_SIZE, end));
            reqs.add(pr);
            i += pr.length;
        }

        return reqs;
    }

    public static PeerRequest mk_request(final PieceBlock b, long fsize) throws JED2KException {
        Range r = b.range(fsize);
        return mk_request(r.left, r.right);
    }

    public long inBlockOffset() {
        return start % Constants.BLOCK_SIZE;
    }

    public Range range() {
        long begin = piece * Constants.PIECE_SIZE + start;
        long end = begin + length;
        assert(begin < end);
        return Range.make(begin, end);
    }

    public Pair<PeerRequest, PeerRequest> split() {
        PeerRequest r = this;
        PeerRequest left = this;
        r.length = Math.min(length, Constants.BLOCK_SIZE);
        left.start = r.start + r.length;
        left.length = length - r.length;
        return Pair.make(r, left);
    }

    @Override
    public String toString() {
        return String.format("piece %d start %d length %d", piece, start, length);
    }
}
