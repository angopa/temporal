package demos.android.com.craneo.temporal.utils;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.Collection;
import java.util.HashSet;

public class Utils {
    /**
     * Get a list of all wearable nodes that are connected synchronously.
     * Only call this method from a background thread (it should never be
     * called from the main/UI thread as it blocks).
     */
    public static Collection<String> getNodes(GoogleApiClient client) {
        Collection<String> results= new HashSet<String>();
        NodeApi.GetConnectedNodesResult nodes =
                Wearable.NodeApi.getConnectedNodes(client).await();
        for (Node node : nodes.getNodes()) {
            results.add(node.getId());
        }
        return results;
    }

}
