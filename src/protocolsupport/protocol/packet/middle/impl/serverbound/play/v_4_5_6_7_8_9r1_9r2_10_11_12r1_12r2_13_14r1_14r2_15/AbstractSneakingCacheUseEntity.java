package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15;

import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleUseEntity;
import protocolsupport.protocol.storage.netcache.ClientCache;

public abstract class AbstractSneakingCacheUseEntity extends MiddleUseEntity {

	protected AbstractSneakingCacheUseEntity(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void handle() {
		sneaking = clientCache.isSneaking();
	}

}
