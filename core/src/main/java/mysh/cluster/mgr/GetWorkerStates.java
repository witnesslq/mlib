package mysh.cluster.mgr;

import mysh.cluster.IClusterMgr;
import mysh.cluster.WorkerState;

import java.util.*;

/**
 * @author Mysh
 * @since 2014/10/12 14:23
 */
public class GetWorkerStates<WS extends WorkerState>
				extends IClusterMgr<List<String>, Object, WS, Map<String, WS>> {

	private static final long serialVersionUID = -5307305883762804671L;

	private Map<String, WS> workerStates;
	private Class<WS> wsClass;

	public GetWorkerStates(Class<WS> wsClass) {
		this.wsClass = wsClass;
	}

	@Override
	public SubTasksPack<Object> fork(List<String> task, int workerNodeCount) {
		workerStates = master.getWorkerStates();
		return pack(new Object[0], null);
	}

	@Override
	public Class<WS> getSubResultType() {
		return wsClass;
	}

	@Override
	public WS procSubTask(Object subTask, int timeout) throws InterruptedException {
		return null;
	}

	@Override
	public Map<String, WS> join(WS[] subResults, String[] assignedNodeIds) {
		return workerStates;
	}
}