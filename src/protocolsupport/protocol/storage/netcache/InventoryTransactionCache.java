package protocolsupport.protocol.storage.netcache;

import io.netty.util.internal.ThreadLocalRandom;
import it.unimi.dsi.fastutil.shorts.Short2IntMap;
import it.unimi.dsi.fastutil.shorts.Short2IntOpenHashMap;
import protocolsupport.utils.JavaSystemProperty;

public class InventoryTransactionCache {

	public static final int INVALID_ID = -1;

	protected static final int syncpingClientIdFirst = 1;
	protected static final int syncpingClientIdLast = 16383;
	protected static final int invstateClientIdFirst = 16384;
	protected static final int invstateClientIdLast = 32767;


	protected static final int syncpingLimit = JavaSystemProperty.getValue("syncping.limit", 60 * 20, Integer::parseInt);

	protected short syncpingClientIdCurrent = generateInitialValue(syncpingClientIdFirst);
	protected Short2IntMap syncpingClientServerId = new Short2IntOpenHashMap();
	{
		syncpingClientServerId.defaultReturnValue(INVALID_ID);
	}

	protected static short generateInitialValue(int first) {
		return (short) (first + (((System.currentTimeMillis() & 1023) << 4) + ThreadLocalRandom.current().nextInt(16)));
	}

	public short storeSyncPingServerId(int serverId) {
		if (syncpingClientServerId.size() > syncpingLimit) {
			throw new IllegalStateException("Too many unanswered sync pings");
		}
		if (++syncpingClientIdCurrent > syncpingClientIdLast) {
			syncpingClientIdCurrent = syncpingClientIdFirst;
		}
		syncpingClientServerId.put(syncpingClientIdCurrent, serverId);
		return syncpingClientIdCurrent;
	}

	public int trySyncPingConfirm(short clientId) {
		return syncpingClientServerId.remove(clientId);
	}



	protected InvStateSync playerInventrySyncState = new InvStateSync();
	protected InvStateSync openInventorySyncState = playerInventrySyncState;

	public void openInventory() {
		openInventorySyncState = new InvStateSync();
	}

	public void closeInventory() {
		openInventorySyncState = playerInventrySyncState;
	}

	public short storeInventoryStateServerId(int serverId, boolean player) {
		return (player ? playerInventrySyncState : openInventorySyncState).storeServerId(serverId);
	}

	public void tryInventoryStateSync(short clientId) {
		playerInventrySyncState.trySync(clientId);
		openInventorySyncState.trySync(clientId);
	}

	public int getInventoryStateServerId() {
		return openInventorySyncState.getSyncServerId();
	}

	protected static class InvStateSync {

		protected int serverId = INVALID_ID;
		protected short clientIdCurrent = generateInitialValue(invstateClientIdFirst);
		protected boolean sync = true;

		public short storeServerId(int serverId) {
			this.serverId = serverId;
			this.sync = false;
			if (++clientIdCurrent > invstateClientIdLast) {
				clientIdCurrent = invstateClientIdFirst;
			}
			return clientIdCurrent;
		}

		public void trySync(short clientId) {
			if (clientId == clientIdCurrent) {
				sync = true;
			}
		}

		public int getSyncServerId() {
			return sync ? serverId : INVALID_ID;
		}

	}

}
