package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_15;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV15;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.AbstractLegacyChangeDimension;
import protocolsupport.protocol.typeremapper.legacy.LegacyDimension;

public class ChangeDimension extends AbstractLegacyChangeDimension implements IClientboundMiddlePacketV15 {

	public ChangeDimension(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeChangeDimension(int dimensionId) {
		ClientBoundPacketData changedimension = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_RESPAWN);
		changedimension.writeInt(dimensionId);
		changedimension.writeLong(hashedSeed);
		changedimension.writeByte(gamemodeCurrent.getId());
		StringCodec.writeVarIntUTF8String(changedimension, LegacyDimension.getWorldType(worldFlat));
		io.writeClientbound(changedimension);
	}

}
